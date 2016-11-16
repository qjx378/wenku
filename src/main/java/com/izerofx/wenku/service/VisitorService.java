package com.izerofx.wenku.service;

import java.util.List;

import com.izerofx.framework.basic.common.service.BaseService;
import com.izerofx.wenku.domain.Visitor;

/**
 * 
 * 类名称：VisitorService<br>
 * 类描述：访客服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午1:59:21<br>
 * @version v1.0
 *
 */
public interface VisitorService extends BaseService<Visitor, String> {

    /**
     * 通过用户id获取访客,默认返回最近10条
     * @param userId
     * @param count
     * @return
     */
    public List<Visitor> findByUserId(String userId, Integer count);
}
