package com.izerofx.framework.basic.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/***
 * 
 * 类名称：BaseServiceImpl<br>
 * 类描述： BaseService的抽象实现，供继承<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月26日 下午1:58:26<br>
 * @version v1.0
 * @param <T>
 * @param <ID>
 */
@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    public abstract PagingAndSortingRepository<T, ID> getDao();

    @Override
    public T save(T t) {
        T nt = getDao().save(t);
        return nt;
    }

    @Override
    public void save(List<T> t) {
        getDao().save(t);
    }

    @Override
    public void delete(ID id) {
        getDao().delete(id);
    }

    @Override
    public void delete(T t) {
        getDao().delete(t);
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(ID id) {
        return getDao().findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>) getDao().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(Sort sort) {
        return (List<T>) getDao().findAll(sort);
    }
}
