package com.jzy.model.dto;

import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidDataAddException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName DefaultFromExcelUpdateResult
 * @Author JinZhiyun
 * @Description 从表格中读取的数据等执行更新数据后的结果封装，默认对象，继承AbstractFromExcelUpdateResult
 * @Date 2020/1/16 9:02
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class DefaultFromExcelUpdateResult extends AbstractFromExcelUpdateResult {
    private static final long serialVersionUID = 6074122891536791042L;

    public DefaultFromExcelUpdateResult() {
    }

    public DefaultFromExcelUpdateResult(String result) {
        super(result);
    }

    @Override
    public DefaultFromExcelUpdateResult merge(AbstractFromExcelUpdateResult r) throws InvalidDataAddException {
        if (r == null) {
            return this;
        }
        //更新记录数相加
        super.add(r);
        //不合法数据合并
        if (invalidData != null) {
            invalidData.add(r.getInvalidData());
        }

        if (Constants.SUCCESS.equals(this.getResult())) {
            if (!Constants.SUCCESS.equals(r.getResult())) {
                //如果r更新结果不是success，返回的更新结果中result置为r中的result
                this.setResult(r.getResult());
            }
        }
        return this;
    }
}
