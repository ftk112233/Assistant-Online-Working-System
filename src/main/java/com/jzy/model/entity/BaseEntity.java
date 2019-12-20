package com.jzy.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName BaseEntity
 * @description 基本实体类，id，createTime，updateTime三个公有必备字段的映射
 * @date 2019/11/13 12:32
 **/
@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 9146032389051551666L;

    /**
     * 主键id，自增
     */
    protected Long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateTime;

    /**
     * 判断一个BaseEntity的子类对象，除了BaseEntity中的字段外，对象的其他信息是否相同
     *
     * @param o
     * @return
     */
    public boolean equalsExceptBaseParams(BaseEntity o){
        Long tmpId = null;
        Date tmpCreateTime=null;
        Date tmpUpdateTime=null;
        if (o != null){
            tmpId=o.getId();
            tmpCreateTime=o.getCreateTime();
            tmpUpdateTime=o.getUpdateTime();
            o.setId(this.getId());
            o.setCreateTime(this.getCreateTime());
            o.setUpdateTime(this.getUpdateTime());
        }
        boolean result=this.equals(o);
        if (o != null){
            o.setId(tmpId);
            o.setCreateTime(tmpCreateTime);
            o.setUpdateTime(tmpUpdateTime);
        }
        return result;
    }
}
