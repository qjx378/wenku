package com.izerofx.framework.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 类名称：FileUtils<br>
 * 类描述：文件操作通用类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午11:11:45<br>
 * @version v1.0
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 时间格式化
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取文件上传的随机路径(包括文件名)
     * @param file
     * @param fileName
     * @return
     */
    public static String getFileUriGeneratedPart(MultipartFile file, String fileName) {
        return "/" + sdf.format(new Date()) + "/" + FilenameUtils.getExtension(file.getOriginalFilename()) + "/" + fileName.toLowerCase() + "/" + fileName.toLowerCase() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }

    /**
     * 通过文件名后缀创建随机目录(不包括文件名)
     * @param fileName
     * @param ext
     * @return
     */
    public static String getFileUriGeneratedPart(String fileName, String ext) {
        return "/" + sdf.format(new Date()) + "/" + fileName.toLowerCase() + "/" + ext + "/" + fileName.toLowerCase() + "." + ext;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        if (returnValue > 1)
            return returnValue + "MB";
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return returnValue + "KB";
    }

    /**
     * 删除指定文件
     * @param fullPath 完整路径包括文件名
     * @return
     */
    public static boolean deleteFile(String fullPath) {
        File file = new File(fullPath);
        if (!file.isDirectory() && file.isFile()) { //如果是 文件夹
            return file.delete();
        }
        return false;
    }

    /**
     * 获取文件编码
     * @param fileName 完整文件路径，包括文件名
     * @return
     * @throws IOException
     */
    public static String getCharset(String fileName) {
        String code = null;
        BufferedInputStream bis = null;
        int p = 0;
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            bis = new BufferedInputStream(inputStream);
            p = (bis.read() << 8) + bis.read();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        switch (p) {
            case 0xefbb :
                code = CharEncoding.UTF_8;
                break;
            case 0xfffe :
                code = "Unicode";
                break;
            case 0xfeff :
                code = CharEncoding.UTF_16BE;
                break;
            default :
                code = "GBK";
        }

        return code;
    }

    /**
     * 获取文件内容
     * @param fileName 完整文件路径，包括文件名
     * @return
     */
    public static String getFileContent(String fileName) {
        String content = "";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            content = IOUtils.toString(fis, getCharset(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 写入内容到文件
     * @param fileName 完整文件路径，包括文件名
     * @param fileContent 写入的内容
     */
    public static void writeFile(String fileName, String fileContent) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            IOUtils.write(fileContent, new FileOutputStream(f), CharEncoding.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
