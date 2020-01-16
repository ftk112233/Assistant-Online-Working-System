package com.jzy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UpdateResult
 * @description 事务更新结果封装
 * @date 2019/12/3 10:00
 **/
@Data
public class UpdateResult implements Serializable {
    private static final long serialVersionUID = 4565844466696122953L;

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

    /**
     * 更新结果，SUCCESS?
     */
    private String result;

    public UpdateResult() {
    }

    public UpdateResult(String result) {
        this.result = result;
    }

    /**
     * 把当前更新结果和r相加，即对更新、插入、删除的数量分别相加
     *
     * @param r 另一个更新结果
     * @return 相加合并后的更新结果
     */
    public UpdateResult add(UpdateResult r){
        if (r == null){
            return this;
        }
        this.updateCount+=r.getUpdateCount();
        this.insertCount+=r.getInsertCount();
        this.deleteCount+=r.getDeleteCount();
        return this;
    }
}
