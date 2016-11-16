package com.izerofx.framework.basic.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;

/**
 * 
 * 类名称：BaseService<br>
 * 类描述：对Spring-Data-JPA Dao的简单封装，Service实现类可从该类继承<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月26日 下午1:57:34<br>
 * @version v1.0
 * @param <T> 实体
 * @param <ID> 主键
 */
public interface BaseService<T, ID extends Serializable> {

    /**
     * add或者update，如需分开，子类覆盖该方法
     * 
     * @param t
     */
    public T save(T t);

    /**
     * 
     * @param t
     */
    public void save(List<T> t);

    /**
     * 根据id删除
     * 
     * @param id
     */
    public void delete(ID id);

    /**
     * 删除实体
     * 
     * @param t
     */
    public void delete(T t);

    /**
     * 根据id查询实体
     * 
     * @param id
     * @return
     */
    public T getById(ID id);

    /**
     * 查询所有
     * 
     * @return
     */
    List<T> findAll();

    /**
     * 查询所有
     * 
     * @param sort 排序条件
     * @return
     */
    List<T> findAll(Sort sort);
}
