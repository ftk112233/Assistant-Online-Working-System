package com.jzy.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName Test
 * @Author JinZhiyun
 * @Description //TODO
 * @Date 2019/12/11 21:30
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        String str=new String("abcd");
        List<String> list=new ArrayList<>();
        list.add("a");
        list.add("v");
        list.add("Ddd");
        list.add("c1");
        list.add("111");
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        System.out.println(str.toLowerCase(Locale.CHINA));
    }
}
