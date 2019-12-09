package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UsefulInformationSearchCondition;
import com.jzy.model.entity.UsefulInformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName UsefulInformationService
 * @description 常用信息的业务接口
 * @date 2019/12/5 21:49
 **/
public interface UsefulInformationService {
    /**
     * 根据id查询当前常用信息
     *
     * @param id 主键id
     * @return
     */
    UsefulInformation getUsefulInformationById(Long id);

    /**
     * 根据所属类别和序号查询
     *
     * @param belongTo 所属类别
     * @param sequence 序号
     * @return
     */
    UsefulInformation getUsefulInformationByBelongToAndSequence(String belongTo, Long sequence);

    /**
     * 根据所有者查询当前所有常用信息（含公共）。做redis缓存
     *
     * @param belongTo 所有者
     *                 if 空
     *                      空列表
     *                 if 公共
     *                      公共
     *                 if 对应校区
     *                      公共+对应校区
     * @return
     */
    List<UsefulInformation> listUsefulInformationWithPublicByBelongTo(String belongTo);

    /**
     * 根据所有者查询当前所有常用信息（不含公共）。不做redis缓存
     *
     * @param belongTo 所有者
     * @return
     */
    List<UsefulInformation> listUsefulInformationWithoutPublicByBelongTo(String belongTo);

    /**
     * 查询当前所有公共的信息
     *
     * @return
     */
    List<UsefulInformation> listDefaultPublicUsefulInformation();

    /**
     * 获得所有所属类别
     *
     * @return
     */
    List<String> listAllBelongTo();

    /**
     * 条件查询常用信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<UsefulInformation> listUsefulInformation(MyPage myPage, UsefulInformationSearchCondition condition);

    /**
     * 根据当前的所属类别获得推荐的序号值
     *
     * @param belongTo 所属类别
     * @return
     */
    Long getRecommendedSequence(String belongTo);

    /**
     * 上传配图
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端，用以之后修改信息中的配图路径
     */
    String uploadImage(MultipartFile file) throws InvalidParameterException;

    /**
     * 上传配图
     *
     * @param file 上传得到的文件
     * @param id 用户id
     * @return 保存文件的名称，用来传回前端，用以之后修改信息中的配图路径
     */
    String uploadImage(MultipartFile file,String id) throws InvalidParameterException;

    /**
     * 常用信息管理中的编辑常用信息请求，由id修改
     *
     * @param information 修改后的常用信息
     * @return
     */
    String updateUsefulInformationInfo(UsefulInformation information);

    /**
     * 常用信息管理中的添加常用信息
     *
     * @param information 新添加常用信息
     * @return
     */
    String insertUsefulInformation(UsefulInformation information);

    /**
     * 删除一个常用信息
     *
     * @param id 被删除常用信息的id
     * @return
     */
    long deleteOneUsefulInformationById(Long id);

    /**
     * 删除多个常用信息
     *
     * @param ids 被删除常用信息的id的列表
     * @return
     */
    long deleteManyUsefulInformationByIds(List<Long> ids);
}
