package com.izerofx.wenku.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.framework.core.util.FileUtils;
import com.izerofx.framework.core.util.ImageUtil;
import com.izerofx.wenku.domain.DocFileInfo;
import com.izerofx.wenku.domain.UploadProperties;
import com.izerofx.wenku.service.FileService;
import com.izerofx.wenku.util.FileExtConstant;

/**
 * 
 * 类名称：LocalFileServiceImpl<br>
 * 类描述：本地文件处理服务接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午8:57:05<br>
 * @version v1.0
 *
 */
@Service
public class LocalFileServiceImpl implements FileService {

    @Resource
    private UploadProperties uploadProperties;

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.FileService#uploadHead(org.springframework.web.multipart.MultipartFile, int, int)
     */
    @Override
    public String uploadHead(MultipartFile file, int maxWidth, int maxHeight) throws Exception {
        String folderAndName = FileUtils.getFileUriGeneratedPart(file, StringUtils.getUUIDString());
        String path = uploadProperties.getPath() + folderAndName;

        File realFile = new File(path);
        if (!realFile.getParentFile().exists()) {
            if (!realFile.getParentFile().mkdirs()) {
                throw new Exception("创建文件上传目录失败");
            }
        }
        try {
            //保存指定大小的图片
            ImageUtil.scale(file.getInputStream(), new FileOutputStream(realFile), maxWidth, maxHeight);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return uploadProperties.getPublicPath() + folderAndName;
    }

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.FileService#uploadDoc(org.springframework.web.multipart.MultipartFile)
     */
    @Override
    public DocFileInfo uploadDoc(MultipartFile file) throws Exception {
        DocFileInfo fileInfo = new DocFileInfo();

        String folderAndName = FileUtils.getFileUriGeneratedPart(file, StringUtils.getUUIDString());
        String path = uploadProperties.getPath() + folderAndName;

        File realFile = new File(path);
        if (!realFile.getParentFile().exists()) {
            if (!realFile.getParentFile().mkdirs()) {
                throw new Exception("创建文件上传目录失败");
            }
        }
        fileInfo.setName(FilenameUtils.getBaseName(file.getOriginalFilename()));
        fileInfo.setPath(uploadProperties.getPublicPath() + folderAndName);
        fileInfo.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));

        //如果为pdf文件，设置pdf路径，后面也不用转换pdf了
        if (FileExtConstant.FILETYPE_PDF.equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            fileInfo.setPdfPath(uploadProperties.getPublicPath() + folderAndName);
        }
        fileInfo.setHash(DigestUtils.md5Hex(file.getInputStream()));
        fileInfo.setSize(file.getSize());
        fileInfo.setConState(0);
        fileInfo.setCreateTime(new Date());

        //转存文件
        file.transferTo(realFile);

        return fileInfo;
    }
}
