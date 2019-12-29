package com.jzy.dao;

import com.jzy.model.dto.UsefulInformationSearchCondition;
import com.jzy.model.entity.UsefulInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName UsefulInformationMapper
 * @description 常用信息的dao接口
 * @date 2019/12/5 21:50
 **/
public interface UsefulInformationMapper {
    /**
     * 根据id查询当前常用信息
     *
     * @param id 主键id
     * @return 对应常用信息
     */
    UsefulInformation getUsefulInformationById(@Param("id") Long id);

    /**
     * 根据所有者查询当前所有常用信息
     *
     * @param belongTo 所有者
     * @return 所有常用信息
     */
    List<UsefulInformation> listUsefulInformationByBelongTo(@Param("belongTo") String belongTo);

    /**
     * 条件查询常用信息
     *
     * @param condition 查询条件入参
     * @return 符合条件的常用信息
     */
    List<UsefulInformation> listUsefulInformation(UsefulInformationSearchCondition condition);

    /**
     * 根据当前的所属类别获得推荐的序号值
     *
     * @param belongTo 所属类别
     * @return 序号值
     */
    Long getRecommendedSequence(@Param("belongTo") String belongTo);

    /**
     * 根据所属类别和序号查询常用信息
     *
     * @param belongTo 所属类别
     * @param sequence 序号
     * @return 指定类别和序号对应的常用信息
     */
    UsefulInformation getUsefulInformationByBelongToAndSequence(@Param("belongTo") String belongTo, @Param("sequence") Long sequence);

    /**
     * 常用信息管理中的编辑常用信息请求，由id修改
     *
     * @param information 修改后的常用信息
     * @return 更新记录数
     */
    long updateUsefulInformationInfo(UsefulInformation information);

    /**
     * 常用信息管理中的添加常用信息
     *
     * @param information 新添加常用信息
     * @return 更新记录数
     */
    long insertUsefulInformation(UsefulInformation information);

    /**
     * 删除一个常用信息
     *
     * @param id 被删除常用信息的id
     * @return 更新记录数
     */
    long deleteOneUsefulInformationById(Long id);

    /**
     * 删除多个常用信息
     *
     * @param ids 被删除常用信息的id的列表
     * @return 更新记录数
     */
    long deleteManyUsefulInformationByIds(List<Long> ids);
}
