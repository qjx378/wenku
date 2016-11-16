package com.izerofx.wenku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.izerofx.framework.basic.common.service.BaseServiceImpl;
import com.izerofx.wenku.dao.VisitorDao;
import com.izerofx.wenku.domain.Visitor;
import com.izerofx.wenku.service.VisitorService;

/**
 * 
 * 类名称：VisitorServiceImpl<br>
 * 类描述：访客服务接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午2:00:32<br>
 * @version v1.0
 *
 */
@Service
public class VisitorServiceImpl extends BaseServiceImpl<Visitor, String> implements VisitorService {

    @Autowired
    private VisitorDao visitorDao;

    @Override
    public PagingAndSortingRepository<Visitor, String> getDao() {
        return visitorDao;
    }

    @Override
    public List<Visitor> findByUserId(String userId, Integer count) {
        return visitorDao.findByUserId(userId, count);
    }

}
