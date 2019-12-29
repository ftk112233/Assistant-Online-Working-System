package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UsefulInformationService
 * @description 常用的信息
 * @date 2019/12/5 21:41
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UsefulInformation extends BaseEntity {
    private static final long serialVersionUID = -4867788217567163358L;

    /**
     * 默认的信息所有者，所有校区公有
     */
    public static final String DEFAULT_BELONG_TO = "公共";

    /**
     * 默认的信息配图名
     */
    public static final String DEFAULT_IMAGE = "image_default.png";

    /**
     * 每个类别的记录顺序相邻sequence的默认差值
     */
    public static final long DEFAULT_SEQUENCE_INTERVAL = 20;

    /**
     * 信息主题，非空，不超过100个字符
     */
    private String title;

    /**
     * 信息内容，非空，不超过200个字符
     */
    private String content;

    /**
     * 图片的路径，可以为空，不超过200个字符
     */
    private String image;

    /**
     * 该信息的所有者，通常是校区，非空，不超过50个字符
     */
    private String belongTo;

    /**
     * 该信息的顺序，不为空，和校区组合唯一
     */
    private Long sequence;

    /**
     * 备注，可以为空，不超过500个字符
     */
    private String remark;

    /**
     * 设置默认配图
     */
    public void setDefaultImage() {
        this.image = DEFAULT_IMAGE;
    }

    /**
     * 当前信息的配图是否是默认配图
     *
     * @return
     */
    public boolean isDefaultImage() {
        return DEFAULT_IMAGE.equals(this.image);
    }

    /**
     * 设置默认类别
     */
    public void setDefaultBelongTo() {
        this.belongTo = DEFAULT_BELONG_TO;
    }

    /**
     * 当前信息的类别是否是默认类别
     *
     * @return
     */
    public boolean isDefaultBelongTo() {
        return DEFAULT_BELONG_TO.equals(this.belongTo);
    }
}
