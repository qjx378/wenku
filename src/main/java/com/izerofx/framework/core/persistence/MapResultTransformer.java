package com.izerofx.framework.core.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

/**
 * 
 * 类名称：MapResultTransformer<br>
 * 类描述：数据库查询结果集映射，将ResultSet转换为Map<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月29日 上午10:13:13<br>  
 * @version v1.0
 *
 */
public class MapResultTransformer implements ResultTransformer {

    private static final long serialVersionUID = 189347373937529641L;

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < aliases.length; i++) {
            // key为小写
            result.put(aliases[i].toLowerCase(), tuple[i]);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List transformList(List list) {
        return list;
    }

}
