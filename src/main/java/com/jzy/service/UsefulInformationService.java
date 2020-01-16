package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidFileInputException;
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
     * @return 对应常用信息
     */
    UsefulInformation getUsefulInformationById(Long id);

    /**
     * 根据所属类别和序号查询
     *
     * @param belongTo 所属类别
     * @param sequence 序号
     * @return 指定所属类别和序号的常用信息
     */
    UsefulInformation getUsefulInformationByBelongToAndSequence(String belongTo, Long sequence);

    /**
     * 根据所有者查询当前所有常用信息（含公共）。做redis缓存
     * <p>
     * if  所有者=空 {
     * 返回空列表
     * }
     * if 所有者=公共 {
     * 返回公共的信息
     * }
     * if 所有者=对应校区{
     * 返回公共+对应校区的信息
     * }
     *
     * @param belongTo 所有者
     * @return 所有的信息
     */
    List<UsefulInformation> listUsefulInformationWithPublicByBelongTo(String belongTo);

    /**
     * 根据所有者查询当前所有常用信息（不含公共）。不做redis缓存
     * <p>
     * if  所有者=空 || 所有者=公共{
     * 返回空列表
     * }
     * if 所有者=对应校区{
     * 仅返回对应校区的信息
     * }
     *
     * @param belongTo 所有者
     * @return 所有的信息
     */
    List<UsefulInformation> listUsefulInformationWithoutPublicByBelongTo(String belongTo);

    /**
     * 查询归属者为‘公共’的全部信息
     *
     * @return 信息
     */
    List<UsefulInformation> listDefaultPublicUsefulInformation();

    /**
     * 获得所有所属类别
     *
     * @return 所有类别
     */
    List<String> listAllBelongTo();

    /**
     * 条件查询常用信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<UsefulInformation> listUsefulInformation(MyPage myPage, UsefulInformationSearchCondition condition);

    /**
     * 根据当前的所属类别获得推荐的序号值。
     * 如果当前类别没有任何信息，推荐值为0；
     * 如果当前类别已有信息，在序号值最大的记录基础上+20作为推荐值
     *
     * @param belongTo 所属类别
     * @return 推荐的序号值
     */
    Long getRecommendedSequence(String belongTo);

    /**
     * 上传配图。从session中获取用户id，把文件名保存为”image_"+id的值的形式
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端，用以之后修改信息中的配图路径
     * @throws InvalidFileInputException 不合法的入参
     */
    String uploadImage(MultipartFile file) throws InvalidFileInputException;

    /**
     * 上传配图。如果id为空，配图的文件名保存为uuid; 如果id非空保存为”image_"+id的值的形式
     *
     * @param file 上传得到的文件
     * @param id   用户id
     * @return 保存文件的名称，用来传回前端，用以之后修改信息中的配图路径
     * @throws InvalidFileInputException 不合法的入参
     */
    String uploadImage(MultipartFile file, String id) throws InvalidFileInputException;

    /**
     * 常用信息管理中的编辑常用信息请求，由id修改。
     * 注意更新时如果有更新配图，需要先删除原配图
     *
     * @param information 修改后的常用信息
     * @return 1."failure"：错误入参等异常
     * 2."belongToAndSequenceRepeat"：归属和序号值组合冲突
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateUsefulInformationInfo(UsefulInformation information);

    /**
     * 常用信息管理中的添加常用信息
     *
     * @param information 新添加常用信息
     * @return 1."failure"：错误入参等异常
     * 2."belongToAndSequenceRepeat"：归属和序号值组合冲突
     * 3."success": 更新成功
     */
    String insertOneUsefulInformation(UsefulInformation information);

    /**
     * 删除一个常用信息。删除消息的同时删除配图文件。
     *
     * @param id 被删除常用信息的id
     * @return 更新记录数
     */
    long deleteOneUsefulInformationById(Long id);

    /**
     * 删除多个常用信息。删除消息的同时删除配图文件
     *
     * @param ids 被删除常用信息的id的列表
     * @return 更新记录数
     */
    long deleteManyUsefulInformationByIds(List<Long> ids);
}
