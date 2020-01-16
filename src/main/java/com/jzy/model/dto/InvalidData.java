package com.jzy.model.dto;

import com.jzy.manager.exception.InvalidDataAddException;
import lombok.Data;

import java.math.BigInteger;
import java.util.*;

/**
 * @ClassName InvalidData
 * @Author JinZhiyun
 * @Description 描述不合法的数据。
 * 如，不合法的用户user('用户名'='!@#','身份证'='000'),user('用户名'='《》','身份证'='000')，
 * 描述：invalidKeywordsAndValues((key='用户名', value=['!@#','《》']), (key='身份证',value=['000','000']))
 * @Date 2020/1/16 9:04
 * @Version 1.0
 **/
@Data
public class InvalidData {
    /**
     * 字段名下对应依序的不合法数据
     */
    private Map<String, List<Object>> invalidKeywordsAndValues = new HashMap<>();

    public InvalidData() {
    }

    /**
     * 根据输入的字段名，初始化不合法数据的字段名，对应map的键。
     *
     * @param keywords 不合法的数据的字段名
     */
    public InvalidData(String... keywords) {
        for (String keyword : keywords) {
            invalidKeywordsAndValues.put(keyword, new ArrayList<>());
        }
    }

    /**
     * 添加一个新的不合法字段
     *
     * @param keyword 不合法的数据的字段名
     * @return 添加成功？ 是否有重复的关键字？
     */
    public boolean putKeyword(String keyword) {
        if (hasValue(keyword)) {
            return false;
        }
        invalidKeywordsAndValues.put(keyword, new ArrayList<>());
        return true;
    }

    /**
     * 添加多个新的不合法字段
     *
     * @param keywords 不合法的数据的字段名集合
     * @return 是否有重复的关键字？
     */
    public boolean putAllKeywords(String... keywords) {
        boolean result = true;
        for (String keyword : keywords) {
            result = result && putKeyword(keyword);
        }
        return result;
    }

    /**
     * 在指定字段下添加一个不合法的值
     *
     * @param keyword      不合法字段名
     * @param invalidValue 不合法的值
     * @return 添加成功？ 是否有重复的关键字？
     */
    public boolean putValue(String keyword, Object invalidValue) {
        if (!hasValue(keyword)) {
            return false;
        }

        List<Object> invalidValues = invalidKeywordsAndValues.get(keyword);
        if (invalidValues != null) {
            invalidValues.add(invalidValue);
        }
        return true;
    }

    /**
     * 在指定字段下添加多个不合法的值
     *
     * @param keyword       不合法字段名
     * @param invalidValues 不合法的值集合
     * @return 添加成功？ 是否有重复的关键字？
     */
    public boolean putAllValues(String keyword, List<Object> invalidValues) {
        if (!hasValue(keyword)) {
            return false;
        }
        List<Object> originalInvalidValues = invalidKeywordsAndValues.get(keyword);
        if (originalInvalidValues != null) {
            originalInvalidValues.addAll(invalidValues);
        }
        return true;
    }

    /**
     * 获取指定字段名下不合法的值
     *
     * @param keyword 不合法字段名
     * @return 指定字段名下不合法的值集合
     */
    public List<Object> getValue(String keyword) {
        if (!hasValue(keyword)) {
            return new ArrayList<>();
        }
        return invalidKeywordsAndValues.get(keyword);
    }

    /**
     * 将两个不合法数据对象相加合并。判断o的字段是否在当前字段中存在，否则抛出InvalidDataAddException（使用的时候一般会设计好不出现这种情况）。
     * 相同字段名下的数据添加到list尾部。
     *
     * @param o 另一个不合法数据对象
     * @return 相加后的对象
     * @throws InvalidDataAddException 两个对象的关键字不相同是无法相加
     */
    public InvalidData add(InvalidData o) throws InvalidDataAddException {
        if (o == null) {
            return this;
        }
        Map<String, List<Object>> oMap = o.getInvalidKeywordsAndValues();
        for (String key : oMap.keySet()) {
            //判断o的所有key中是否有当前对象没有的
            if (!hasValue(key)) {
                throw new InvalidDataAddException("add的InvalidData中有当前对象不存在的字段");
            }
        }
        for (Map.Entry<String, List<Object>> entry : oMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();
            List<Object> originalInvalidValues = invalidKeywordsAndValues.get(key);
            if (originalInvalidValues != null) {
                originalInvalidValues.addAll(value);
            }
        }
        return this;
    }

    /**
     * 获取所有不合法字段下，不合法数据条数最多的个数。
     * 通常情况下，设置的多个字段的list长度都是相同的。
     *
     * @return 条数最多不合法字段的条数
     */
    public int getMaxInvalidCount() {
        int max = 0;
        for (List<Object> list : invalidKeywordsAndValues.values()) {
            if (list != null && list.size() > max) {
                max = list.size();
            }
        }
        return max;
    }

    /**
     * 指定关键字段下是否有不合法数值
     *
     * @param keyword 不合法字段名
     * @return 是否有不合法数值
     */
    public boolean hasValue(String keyword) {
        return invalidKeywordsAndValues.containsKey(keyword);
    }

    /**
     * 当前对象是否有不合法值，即遍历所有的字段判断
     *
     * @return 是否有不合法值
     */
    public boolean hasValue() {
        for (String key : invalidKeywordsAndValues.keySet()) {
            if (hasValue(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以可读的形式输出不合法的数据
     *
     * @return 可读性较高的字符串形式
     */
    public String show() {
        StringBuilder r = new StringBuilder();
        r.append("[");
        int maxCount = getMaxInvalidCount();
        for (int i = 0; i < maxCount; i++) {
            r.append("(");
            Iterator<String> keyIterator = invalidKeywordsAndValues.keySet().iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                r.append(key).append("=");
                List<Object> data = invalidKeywordsAndValues.get(key);
                if (i < data.size()) {
                    r.append(data.get(i));
                }
                if (keyIterator.hasNext()) {
                    r.append(", ");
                }
            }
            r.append(")");
            if (i != maxCount - 1) {
                r.append(", ");
            }
        }
        r.append("]");
        return r.toString();
    }

    public static void main(String[] args) throws InvalidDataAddException {
        InvalidData data = new InvalidData("姓名", "密码");
        List<Object> names = new ArrayList<>();
        names.add("1");
        data.putAllValues("姓名", names);

        InvalidData data2 = new InvalidData("姓名");
        List<Object> names2 = new ArrayList<>();
        names2.add("2");
        names2.add(BigInteger.ONE);
        data2.putAllValues("姓名", names2);

        System.out.println(data.add(data2));
        System.out.println(data.show());
    }

}
