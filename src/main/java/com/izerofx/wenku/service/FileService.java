package com.izerofx.wenku.service;

import org.springframework.web.multipart.MultipartFile;

import com.izerofx.wenku.domain.DocFileInfo;

/**
 * 
 * 类名称：FileService<br>
 * 类描述：文件处理服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午8:56:35<br>
 * @version v1.0
 *
 */
public interface FileService {

    /**
     * 上传头像
     * @param file
     * @param maxWidth
     * @param maxHeight
     * @return
     * @throws Exception
     */
    public String uploadHead(MultipartFile file, int maxWidth, int maxHeight) throws Exception;

    /**
     * 上传文档
     * @param file
     * @return
     * @throws Exception
     */
    public DocFileInfo uploadDoc(MultipartFile file) throws Exception;
}
