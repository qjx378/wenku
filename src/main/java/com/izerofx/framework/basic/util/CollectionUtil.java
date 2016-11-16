package com.izerofx.framework.basic.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.thymeleaf.util.MapUtils;

/**
 * 
 * 类名称：CollectionUtil<br>
 * 类描述：集合工具类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月27日 上午11:18:05<br>  
 * @version v1.0
 *
 */
public final class CollectionUtil {

    /**
     * 判断 Collection 是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断 Collection 是否非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断 Map 是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断 Map 是否非空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Collection 转 Array
     * @param collection
     * @param array
     * @return
     */
    public static <T> T[] toArray(Collection<T> collection, T[] array) {
        if (isEmpty(collection)) {
            return array;
        }
        return collection.toArray(array);
    }
}
