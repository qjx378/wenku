package com.izerofx.framework.core.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类名称：EntityResultTransformer<br>
 * 类描述：数据库查询结果集映射，将ResultSet转换为Entity
 * <p>
 * Hibernate自带的Transformers.aliasToBean(class)方法不能识别继承属性<br>
 * 本类改用BeanUtils来实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月29日 上午10:12:57<br>
 * @version v1.0
 *
 */
public class EntityResultTransformer implements ResultTransformer {

    private static final long serialVersionUID = 2362750655253391124L;

    private static final Logger logger = LoggerFactory.getLogger(EntityResultTransformer.class);

    private Class<?> targetClass;

    public EntityResultTransformer(Class<?> targetClass) {
        if (targetClass == null) { throw new IllegalArgumentException("targetClass参数不能为空"); }
        this.targetClass = targetClass;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        try {
            Object result = targetClass.newInstance();
            for (int i = 0; i < aliases.length; i++) {
                String alias = camelPropertyName(aliases[i]);
                try {
                    BeanUtils.copyProperty(result, alias, tuple[i]);
                } catch (InvocationTargetException e) {
                    logger.warn(targetClass.getSimpleName() + "类中没有" + alias + "属性");
                }
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(targetClass.getSimpleName() + "不能实例化");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List transformList(List list) {
        return list;
    }

    // 将数据库字段名转换为JavaBean中的属性名，遵循驼峰命名法
    private String camelPropertyName(String propName) {
        String[] arr = propName.toLowerCase().split("_");
        String chr = arr[0];
        for (int i = 1; i < arr.length; i++) {
            chr += StringUtils.capitalize(arr[i]);
        }
        return chr;
    }
}
