package com.jzy.model.dto;

import lombok.Data;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UpdateResult
 * @description 事务更新结果封装
 * @date 2019/12/3 10:00
 **/
@Data
public class UpdateResult {
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
     * @param r
     * @return
     */
    public UpdateResult add(UpdateResult r){
        this.updateCount+=r.getUpdateCount();
        this.insertCount+=r.getInsertCount();
        this.deleteCount+=r.getDeleteCount();
        return this;
    }
}
