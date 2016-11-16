package com.izerofx.wenku.dao;

import java.util.List;

/**
 * 
 * 类名称：VisitorDaoCustom<br>
 * 类描述：访客数据访问自定义接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午1:43:30<br>
 * @version v1.0
 *
 * @param <T>
 * @param <ID>
 */
public interface VisitorDaoCustom<T, ID> {

    /**
     * 通过用户id获取访客
     * @param userId
     * @param count
     * @return
     */
    public List<T> findByUserId(String userId, Integer count);
}
