package com.izerofx.wenku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.izerofx.wenku.domain.User;

/**
 * 
 * 类名称：UserDao<br>
 * 类描述：用户数据访问接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午3:07:35<br>
 * @version v1.0
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

    /**
     * 判断电子邮箱是否存在
     * @param email 用户名
     * @return
     */
    @Query(nativeQuery = true, value = "select count(1) from user_info where email = ?1")
    public int isExistByEmail(String email);

    /**
     * 判断用户昵称是否存在
     * @param nickName 用户昵称
     * @return
     */
    @Query(nativeQuery = true, value = "select count(1) from user_info where nick_name = ?1")
    public int isExistByNickName(String nickName);

    /**
     * 通过电子邮箱查找用户信息
     * @param email 用户名
     * @return
     */
    public User findByEmail(String email);
}
