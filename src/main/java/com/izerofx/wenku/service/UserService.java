package com.izerofx.wenku.service;

import com.izerofx.framework.basic.common.service.BaseService;
import com.izerofx.wenku.domain.User;

/**
 * 
 * 类名称：UserService<br>
 * 类描述：用户服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午3:12:38<br>
 * @version v1.0
 *
 */
public interface UserService extends BaseService<User, String> {

    /**
     * 判断电子邮箱是否存在
     * @param Email 电子邮箱
     * @return
     */
    public boolean isExistByEmail(String email);

    /**
     * 判断用户昵称是否存在
     * @param nickName 用户昵称
     * @return
     */
    public boolean isExistByNickName(String nickName);

    /**
     * 通过电子邮箱查找用户信息
     * @param email 用户名
     * @return
     */
    public User findByEmail(String email);
}
