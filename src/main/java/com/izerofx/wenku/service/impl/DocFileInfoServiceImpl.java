package com.izerofx.wenku.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izerofx.framework.basic.common.service.BaseServiceImpl;
import com.izerofx.framework.core.util.FileUtils;
import com.izerofx.wenku.dao.DocBaseInfoDao;
import com.izerofx.wenku.dao.DocFileInfoDao;
import com.izerofx.wenku.domain.DocFileInfo;
import com.izerofx.wenku.domain.UploadProperties;
import com.izerofx.wenku.service.DocFileInfoService;

/**
 * 
 * 类名称：DocFileInfoServiceImpl<br>
 * 类描述：文档文件信息服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午10:55:57<br>
 * @version v1.0
 *
 */
@Service("docFileInfoService")
@Transactional
public class DocFileInfoServiceImpl extends BaseServiceImpl<DocFileInfo, String> implements DocFileInfoService {

    @Autowired
    private DocFileInfoDao docFileInfoDao;

    @Autowired
    private DocBaseInfoDao docBaseInfoDao;

    @Resource
    private UploadProperties uploadProperties;

    @Override
    public PagingAndSortingRepository<DocFileInfo, String> getDao() {
        return docFileInfoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistByHash(String hash) {
        return docFileInfoDao.isExistByHash(hash) > 0;
    }

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.DocFileInfoService#deleteByUserIdAndId(java.lang.String, java.lang.String)
     */
    @Override
    public void deleteByUserIdAndId(String userId, String id) {
        DocFileInfo docFileInfo = docFileInfoDao.findByUserIdAndId(userId, id);
        if (docFileInfo != null) {
            //删除相关文件
            FileUtils.deleteFile(uploadProperties.getPath().replace(uploadProperties.getPublicPath(), "") + docFileInfo.getPath());
            FileUtils.deleteFile(uploadProperties.getPath().replace(uploadProperties.getPublicPath(), "") + docFileInfo.getPdfPath());
            FileUtils.deleteFile(uploadProperties.getPath().replace(uploadProperties.getPublicPath(), "") + docFileInfo.getSwfPath());
            FileUtils.deleteFile(uploadProperties.getPath().replace(uploadProperties.getPublicPath(), "") + docFileInfo.getThumPath());

            //删除数据库文件信息
            docFileInfoDao.delete(docFileInfo);

            //删除数据库对应的文档基本信息
            docBaseInfoDao.deleteByUserIdAndFielId(userId, id);

        }
    }
}
