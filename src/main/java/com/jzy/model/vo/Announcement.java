package com.jzy.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName Announcement
 * @Author JinZhiyun
 * @Description 发布公告的封装
 * @Date 2019/11/28 22:54
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Announcement extends TitleAndContent {
    private static final long serialVersionUID = 2027814142477789626L;

    private int width=800;

    private int height=600;

    /**
     * 是否已读，即redis缓存是否被过期
     */
    boolean read;

    /**
     * 是否是永久有效的公告
     */
    boolean permanent;
}
