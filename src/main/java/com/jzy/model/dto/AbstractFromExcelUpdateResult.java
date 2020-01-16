package com.jzy.model.dto;

import com.jzy.manager.exception.InvalidDataAddException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName AbstractFromExcelUpdateResult
 * @Author JinZhiyun
 * @Description 从表格中读取的数据等执行更新数据后的结果封装，其中不合法的数据用InvalidData封装
 * @Date 2020/1/16 10:15
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class AbstractFromExcelUpdateResult extends UpdateResult {
    private static final long serialVersionUID = -5804356279561774819L;

    /**
     * 不合法的数据集合
     */
    protected InvalidData invalidData = new InvalidData();

    public AbstractFromExcelUpdateResult() {
    }

    public AbstractFromExcelUpdateResult(String result) {
        super(result);
    }

    /**
     * 获取指定关键字段下不合法的数据值
     *
     * @param keyword 指定字段
     * @return 所有符合条件的不合法的数据值
     */
    public List<Object> getInvalidValue(String keyword) {
        return invalidData.getValue(keyword);
    }

    /**
     * 获取所有不合法字段下，不合法数据条数最多的个数。
     * 通常情况下，设置的多个字段的list长度都是相同的。
     *
     * @return 条数最多不合法字段的条数
     */
    public int getMaxInvalidCount() {
        return invalidData.getMaxInvalidCount();
    }

    /**
     * 以可读的形式输出不合法的数据
     *
     * @return 可读性较高的字符串形式
     */
    public String showValidData() {
        return invalidData.show();
    }

    /**
     * 将两个表格更新结果合并，注意这里与父类UpdateResult的区别是：后者只对增删改条数求和，后者还合并非法数据和result。
     *
     * @param r 另一个更新结果
     * @return 合并后的结果
     * @throws InvalidDataAddException 两个对象的关键字不相同是无法相加合并的异常
     */
    public abstract AbstractFromExcelUpdateResult merge(AbstractFromExcelUpdateResult r) throws InvalidDataAddException;
}
