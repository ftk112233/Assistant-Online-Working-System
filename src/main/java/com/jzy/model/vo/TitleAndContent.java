package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TitleAndContent
 * @Author JinZhiyun
 * @Description 标题和内容的封装
 * @Date 2019/11/29 10:32
 * @Version 1.0
 **/
@Data
public class TitleAndContent implements Serializable {
    private static final long serialVersionUID = 2839519085279137455L;

    private String title;

    private String parsedTitle;

    private String content;

    private String parsedContent;

    /**
     * 换行符替换为<br/>
     */
    public void parse(){
        parsedTitle = title.replaceAll("(\\r\\n|\\n|\\n\\r)","");
        parsedContent = content.replaceAll("(\\r\\n|\\n|\\n\\r)","");
    }
}
