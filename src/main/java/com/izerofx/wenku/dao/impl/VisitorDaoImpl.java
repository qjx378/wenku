package com.izerofx.wenku.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.izerofx.framework.core.persistence.PagingHibernateJdbcDao;
import com.izerofx.wenku.dao.VisitorDaoCustom;
import com.izerofx.wenku.domain.Visitor;

/**
 * 
 * 类名称：VisitorDaoImpl<br>
 * 类描述：访客数据访问自定义接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午1:48:14<br>
 * @version v1.0
 *
 */
public class VisitorDaoImpl implements VisitorDaoCustom<Visitor, String> {

    @Autowired
    private PagingHibernateJdbcDao dao;

    @Override
    public List<Visitor> findByUserId(String userId, Integer count) {

        StringBuilder sql = new StringBuilder();

        sql.append(" select a.*, b.nick_name, b.avatar");
        sql.append(" from user_visitor a");
        sql.append(" left join user_info b on b.id = a.visitor_user_id");
        sql.append(" where a.user_id = ?");
        sql.append(" order by a.create_time desc limit ?");

        return dao.findList(sql.toString(), new Object[] { userId, count < 1 ? 10 : count }, Visitor.class);
    }
}
