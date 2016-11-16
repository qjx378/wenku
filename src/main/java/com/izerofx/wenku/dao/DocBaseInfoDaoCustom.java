package com.izerofx.wenku.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * 类名称：DocBaseInfoDaoCustom<br>
 * 类描述：文档基本信息数据自定义访问接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 下午2:30:48<br>
 * @version v1.0
 *
 * @param <T>
 * @param <ID>
 */
public interface DocBaseInfoDaoCustom<T, ID> {

    /**
     * 分页查询所有
     * @param t
     * @param pageable
     * @return
     */
    public Page<T> findAll(T t, Pageable pageable);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public T findById(ID id);
}
