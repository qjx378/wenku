package com.izerofx.framework.basic.common.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 类名称：PersistentEntity<br>
 * 类描述：持久化实体<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 上午10:39:15<br>  
 * @version v1.0
 *
 */
public abstract class PersistentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
