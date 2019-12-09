package com.jzy.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SqlProceedSpeed
 * @description sql事务处理的速度
 * @date 2019/12/3 10:11
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SqlProceedSpeed extends Speed {
    private static final long serialVersionUID = 6467399358952481748L;

    /**
     * 更新条数
     */
    private long updateCount;

    /**
     * 插入条数
     */
    private long insertCount;

    /**
     * 删除条数
     */
    private long deleteCount;

    public SqlProceedSpeed(long count, long milliSecond) {
        super(count, milliSecond);
    }

    public SqlProceedSpeed(long updateCount, long insertCount, long deleteCount, long milliSecond) {
        super(updateCount + insertCount + deleteCount, milliSecond);
        this.updateCount = updateCount;
        this.insertCount = insertCount;
        this.deleteCount = deleteCount;
    }
}
