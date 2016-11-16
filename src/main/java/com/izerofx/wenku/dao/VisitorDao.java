package com.izerofx.wenku.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izerofx.wenku.domain.Visitor;

/**
 * 
 * 类名称：VisitorDao<br>
 * 类描述：访客数据访问接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午1:43:05<br>
 * @version v1.0
 *
 */
public interface VisitorDao extends JpaRepository<Visitor, String>, VisitorDaoCustom<Visitor, String> {

}
