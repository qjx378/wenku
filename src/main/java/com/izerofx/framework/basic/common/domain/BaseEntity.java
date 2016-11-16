package com.izerofx.framework.basic.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 类名称：BaseEntity<br>
 * 类描述： 实体类，所有的实体对象都需要继承该基类<br>
 * 重要：使用@MappedSupperClass注解取代原来的@Entity和@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)注解<br>
 * 原因是：@Entity注解会导致所有子类都无法使用二级缓存，而且@MappedSupperClass不需要对应数据库table<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年12月3日 下午1:48:22<br>
 * @version v1.0
 *
 */
@MappedSuperclass
public abstract class BaseEntity extends PersistentEntity {

    private static final long serialVersionUID = 1534056643006312407L;

    /*
     * 主键
     */
    private String id;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    public BaseEntity() {

    }

    @Id
    @GeneratedValue(generator = "uuid") //指定生成器名称
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator") //生成器名称，uuid生成类  
    @Column(length = 64, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(insertable = false, updatable = false)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
