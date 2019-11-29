package com.jzy.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName ProblemCollection
 * @Author JinZhiyun
 * @Description 问题收集页面提交表单的封装
 * @Date 2019/11/29 10:28
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ProblemCollection extends TitleAndContent {
    private static final long serialVersionUID = -2378548797275505149L;

    private String hide;

    private String realName;

    private String email;
}
