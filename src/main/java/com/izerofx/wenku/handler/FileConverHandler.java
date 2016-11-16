package com.izerofx.wenku.handler;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.izerofx.framework.basic.util.PropertiesUtil;
import com.izerofx.framework.core.util.FileUtils;
import com.izerofx.wenku.service.OfficeConService;
import com.izerofx.wenku.util.FileExtConstant;

/**
 * 
 * 类名称：FileConverHandler<br>
 * 类描述：文件转换处理<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月10日 上午11:16:42<br>
 * @version v1.0
 *
 */
@Component
public class FileConverHandler {

    private static Logger logger = LoggerFactory.getLogger(FileConverHandler.class);

    /**
     * swftools目录
     */
    private static final String SWFTOOLS_PATH = PropertiesUtil.getValue("izerofx.config.swftools.home");

    /**
     * swftools目录
     */
    private static final String LANGUAGEDIR_PATH = PropertiesUtil.getValue("izerofx.config.swftools.languagedir");

    @Resource
    private OfficeConService officeConService;

    /**
     * office文档转pdf
     * @param inputFilePath 原文件路径，例如 d:/demo.doc
     * @param outFilePath 转换文件路径，例如 d:/demo.pdg；为空默认与原文件同一目录
     * @throws IOException
     */
    public void office2PDF(String inputFilePath, String outFilePath) throws IOException {

        //源文件
        File inputFile = new File(inputFilePath);
        //获取文件名称
        String inputBaseName = FilenameUtils.getBaseName(inputFile.getName());
        String inputExtension = FilenameUtils.getExtension(inputFile.getName());
        String inputPath = FilenameUtils.getFullPath(inputFilePath);

        //解决非utf-8的txt转换出现乱码的问题
        if (inputFile.exists() && FileExtConstant.FILETYPE_TXT.equalsIgnoreCase(inputExtension) && !CharEncoding.UTF_8.equals(FileUtils.getCharset(inputFilePath))) {

            //创建utf8文件
            FileUtils.writeFile(inputPath + inputBaseName + "_utf8" + FileExtConstant.SUFF_TXT, FileUtils.getFileContent(inputFilePath));

            //重新指定输入的文件路径
            inputFile = new File(inputPath + inputBaseName + "_utf8" + FileExtConstant.SUFF_TXT);
        }

        //转换后的pdf文件
        File pdfFile;

        if (StringUtils.isBlank(outFilePath)) {
            pdfFile = new File(inputPath + inputBaseName + File.separator + FileExtConstant.SUFF_PDF);
        } else {
            pdfFile = new File(outFilePath);
        }

        //文件存在不再转换
        if (pdfFile.exists()) {
            logger.warn("转换文件存在");
        } else {
            //获取office文档转换服务
            OfficeDocumentConverter converter = officeConService.getDocumentConverter();

            //转换
            converter.convert(inputFile, pdfFile);
        }

    }

    /**
     * 
     * 将指定pdf文件的首页转换为指定路径的缩略图
     * @param pdfFilePath 原文件路径，例如d:/test.pdf
     * @param imagePath 图片生成路径，例如 d:/test-1.jpg
     * @param zoom 缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     */
    public void tranfer(String pdfFilePath, String imagePath, float zoom) throws PDFException, PDFSecurityException, IOException {
        float rotation = 0f;

        Document document = new Document();
        document.setFile(pdfFilePath);

        BufferedImage img = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);

        Iterator<?> iter = ImageIO.getImageWritersBySuffix(FileExtConstant.FILETYPE_PNG);
        ImageWriter writer = (ImageWriter) iter.next();
        File outFile = new File(imagePath);

        if (!new File(FilenameUtils.getFullPath(imagePath)).exists()) {
            new File(FilenameUtils.getFullPath(imagePath)).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(outFile);
        ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
        writer.setOutput(outImage);
        writer.write(new IIOImage(img, null, null));

        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(outImage);
    }

    /**
     * 获取pdf总页数
     * @param pdfFilepath 原文件路径，例如d:/test.pdf
     * @return
     * @throws PDFException
     * @throws PDFSecurityException
     * @throws IOException
     */
    public int getPdfPages(String pdfFilePath) throws PDFException, PDFSecurityException, IOException {
        Document document = new Document();
        document.setFile(pdfFilePath);
        return document.getPageTree().getNumberOfPages();
    }

    /**
     * 
     * 利用SWFTools工具将pdf转换成swf，转换完后的swf文件名按页码命名
     * @param pdfFilePath PDF文件存放路径（包括文件名）
     * @param outDirectory 输出目录
     * @throws IOException
     */
    public void pdf2swf(String pdfFilePath, String outDirectory) throws IOException {

        //文件路径
        String filePath = (StringUtils.isBlank(outDirectory)) ? FilenameUtils.getFullPath(pdfFilePath) + FileExtConstant.FILETYPE_SWF : outDirectory;

        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }

        Process pro = null;
        if (isWindowsSystem()) {
            //如果是windows系统
            //命令行命令
            String cmd = SWFTOOLS_PATH + "pdf2swf.exe" + " \"" + pdfFilePath + "\" -t -T 9 -o \"" + filePath + "/" + "%.swf\"";
            //Runtime执行后返回创建的进程对象
            pro = Runtime.getRuntime().exec(cmd);
        } else {
            //如果是linux系统,路径不能有空格，而且一定不能用双引号，否则无法创建进程
            String[] cmd = new String[6];
            cmd[0] = SWFTOOLS_PATH + "pdf2swf";
            cmd[1] = pdfFilePath;
            cmd[2] = "-s" + LANGUAGEDIR_PATH;
            cmd[3] = "-t";
            cmd[4] = "-T 9";
            cmd[5] = "-o" + filePath + "/" + "%" + FileExtConstant.SUFF_SWF;
            //Runtime执行后返回创建的进程对象
            pro = Runtime.getRuntime().exec(cmd);
        }

        //非要读取一遍cmd的输出，要不不会flush生成文件（多线程）
        new DoOutput(pro.getInputStream()).start();
        new DoOutput(pro.getErrorStream()).start();
        try {

            //调用waitFor方法，是为了阻塞当前进程，直到cmd执行完
            pro.waitFor();
        } catch (InterruptedException e) {
            logger.error("转换swf异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 
     * 利用SWFTools工具将pdf转换成swf
     * @param pdfFilePath PDF文件存放路径（包括文件名）
     * @param outDirectory 输出目录
     * @param swfFileName swf文件名(无需后缀)，为空时取pdf文件名
     * @throws IOException
     */
    public void pdf2swf(String pdfFilePath, String outDirectory, String swfFileName) throws IOException {

        //文件路径
        String filePath = (StringUtils.isBlank(outDirectory)) ? FilenameUtils.getFullPath(pdfFilePath) + FileExtConstant.FILETYPE_SWF : outDirectory;

        if (StringUtils.isBlank(swfFileName)) {
            swfFileName = FilenameUtils.getBaseName(pdfFilePath);
        }

        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }

        Process pro = null;

        if (isWindowsSystem()) {
            //如果是windows系统
            //命令行命令
            String cmd = SWFTOOLS_PATH + "pdf2swf.exe" + " \"" + pdfFilePath + "\" -t -T 9 -o \"" + filePath + "/" + swfFileName + ".swf\"";
            //Runtime执行后返回创建的进程对象
            pro = Runtime.getRuntime().exec(cmd);
        } else {
            //如果是linux系统,路径不能有空格，而且一定不能用双引号，否则无法创建进程
            String[] cmd = new String[6];
            cmd[0] = SWFTOOLS_PATH + "pdf2swf";
            cmd[1] = pdfFilePath;
            cmd[2] = "-s" + LANGUAGEDIR_PATH;
            cmd[3] = "-t";
            cmd[4] = "-T 9";
            cmd[5] = "-o" + filePath + "/" + swfFileName + FileExtConstant.SUFF_SWF;
            //Runtime执行后返回创建的进程对象
            pro = Runtime.getRuntime().exec(cmd);
        }

        //非要读取一遍cmd的输出，要不不会flush生成文件（多线程）
        new DoOutput(pro.getInputStream()).start();
        new DoOutput(pro.getErrorStream()).start();
        try {

            //调用waitFor方法，是为了阻塞当前进程，直到cmd执行完
            pro.waitFor();
        } catch (InterruptedException e) {
            logger.error("转换swf异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是windows操作系统
     * @return
     */
    private static boolean isWindowsSystem() {
        String p = System.getProperty("os.name");
        return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
    }

    /**
     * 
     * 类名称：DoOutput<br>
     * 类描述：多线程内部类 读取转换时cmd进程的标准输出流和错误输出流，这样做是因为如果不读取流，进程将死锁<br>
     * 创建人：qinjiaxue<br>
     * 创建时间：2016年11月10日 下午1:32:51<br>
     * @version v1.0
     *
     */
    private static class DoOutput extends Thread {
        public InputStream is;

        //构造方法
        public DoOutput(InputStream is) {
            this.is = is;
        }

        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
            String str = null;
            try {
                //这里并没有对流的内容进行处理，只是读了一遍
                while ((str = br.readLine()) != null) {
                    logger.info(str);
                }
            } catch (IOException e) {
                logger.error("执行转换命令异常", e);
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
