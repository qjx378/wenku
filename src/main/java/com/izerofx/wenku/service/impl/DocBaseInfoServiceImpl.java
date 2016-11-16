package com.izerofx.wenku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.izerofx.framework.basic.common.service.BaseServiceImpl;
import com.izerofx.wenku.dao.DocBaseInfoDao;
import com.izerofx.wenku.domain.DocBaseInfo;
import com.izerofx.wenku.service.DocBaseInfoService;

/**
 * 
 * 类名称：DocBaseInfoServiceImpl<br>
 * 类描述：<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 下午2:34:30<br>
 * @version v1.0
 *
 */
@Service
public class DocBaseInfoServiceImpl extends BaseServiceImpl<DocBaseInfo, String> implements DocBaseInfoService {

    @Autowired
    private DocBaseInfoDao docBaseInfoDao;

    @Override
    public PagingAndSortingRepository<DocBaseInfo, String> getDao() {
        return docBaseInfoDao;
    }

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.DocBaseInfoService#findAll(com.izerofx.wenku.domain.DocBaseInfo, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<DocBaseInfo> findAll(DocBaseInfo docBaseInfo, Pageable pageable) {
        return docBaseInfoDao.findAll(docBaseInfo, pageable);
    }

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.DocBaseInfoService#findAllByUserId(java.lang.String, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<DocBaseInfo> findAllByUserId(String userId, Pageable pageable) {
        DocBaseInfo docBaseInfo = new DocBaseInfo();
        docBaseInfo.setUserId(userId);
        docBaseInfo.setConState(-2);
        return docBaseInfoDao.findAll(docBaseInfo, pageable);
    }

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.service.DocBaseInfoService#findById(java.lang.String)
     */
    @Override
    public DocBaseInfo findById(String id) {
        return docBaseInfoDao.findById(id);
    }

}
