package com.izerofx.wenku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.izerofx.framework.basic.common.service.BaseServiceImpl;
import com.izerofx.wenku.dao.UserDao;
import com.izerofx.wenku.domain.User;
import com.izerofx.wenku.service.UserService;

/**
 * 
 * 类名称：UserServiceImpl<br>
 * 类描述：用户服务接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午3:14:41<br>
 * @version v1.0
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PagingAndSortingRepository<User, String> getDao() {
        return userDao;
    }

    /*
     * (non-Javadoc)    
     * @see com.izerofx.wenku.service.UserService#isExistByEmail(java.lang.String)
     */
    @Override
    public boolean isExistByEmail(String email) {
        return userDao.isExistByEmail(email) > 0;
    }

    /*
     * (non-Javadoc)    
     * @see com.izerofx.wenku.service.UserService#isExistByNickName(java.lang.String)
     */
    @Override
    public boolean isExistByNickName(String nickName) {
        return userDao.isExistByNickName(nickName) > 0;
    }

    /*
     * (non-Javadoc)    
     * @see com.izerofx.wenku.service.UserService#findByEmail(java.lang.String)
     */
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}
