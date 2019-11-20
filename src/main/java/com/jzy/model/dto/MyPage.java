package com.jzy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName MyPage
 * @Author JinZhiyun
 * @Description 自定义分页类的封装
 * @Date 2019/4/16 11:22
 * @Version 1.0
 **/
@Data
public class MyPage implements Serializable {
    private static final long serialVersionUID = 9072736654996915998L;

    //当前页号
    private Integer pageNum;

    //当前页记录数量
    private Integer pageSize;

    public MyPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public MyPage() {

    }

    /**
     * 判断服务端接收到的mypage对象是否合法
     *
     * @param myPage
     * @return
     */
    public static boolean isValidMyPage(MyPage myPage) {
        return myPage != null && myPage.getPageNum() != null && myPage.getPageSize() != null
                && myPage.getPageNum() > 0 && myPage.getPageSize() > 0;
    }
}
