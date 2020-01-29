package com.jzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.UsefulInformationMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.exception.InvalidFileInputException;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.FileUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UsefulInformationSearchCondition;
import com.jzy.model.entity.UsefulInformation;
import com.jzy.model.entity.User;
import com.jzy.service.UsefulInformationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UsefulInformationServiceImpl
 * @description 常用信息业务实现
 * @date 2019/12/5 21:49
 **/
@Service
public class UsefulInformationServiceImpl extends AbstractServiceImpl implements UsefulInformationService {
    private final static Logger logger = LogManager.getLogger(UsefulInformationServiceImpl.class);

    /**
     * 归属和序号值组合冲突
     */
    private final static String BELONG_TO_AND_SEQUENCE_REPEAT = "belongToAndSequenceRepeat";

    @Autowired
    private UsefulInformationMapper usefulInformationMapper;

    @Override
    public boolean isRepeatedBelongToAndSequence(UsefulInformation usefulInformation) {
        if (usefulInformation == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUsefulInformationByBelongToAndSequence(usefulInformation.getBelongTo(), usefulInformation.getSequence()) != null;
    }

    @Override
    public UsefulInformation getUsefulInformationById(Long id) {
        return id == null ? null : usefulInformationMapper.getUsefulInformationById(id);
    }

    @Override
    public UsefulInformation getUsefulInformationByBelongToAndSequence(String belongTo, Long sequence) {
        return StringUtils.isEmpty(belongTo) || sequence == null ? null : usefulInformationMapper.getUsefulInformationByBelongToAndSequence(belongTo, sequence);
    }

    @Override
    public List<UsefulInformation> listUsefulInformationWithPublicByBelongTo(String belongTo) {
        if (StringUtils.isEmpty(belongTo)) {
            return new ArrayList<>();
        }

        String key = RedisConstants.USEFUL_INFORMATION_KEY;
        if (hashOps.hasKey(key, belongTo)) {
            //缓存中有
            String allInformation = (String) hashOps.get(key, belongTo);
            return JSONArray.parseArray(allInformation, UsefulInformation.class);
        }
        //缓存中无，从数据库查

        List<UsefulInformation> information = new ArrayList<>(listDefaultPublicUsefulInformation());
        information.addAll(listUsefulInformationWithoutPublicByBelongTo(belongTo));
        //添加缓存，过期时间21天
        hashOps.put(key, belongTo, JSON.toJSONString(information));
        redisTemplate.expire(key, RedisConstants.USEFUL_INFORMATION_EXPIRE, TimeUnit.DAYS);
        return information;
    }

    @Override
    public List<UsefulInformation> listUsefulInformationWithoutPublicByBelongTo(String belongTo) {
        if (StringUtils.isEmpty(belongTo) || UsefulInformation.DEFAULT_BELONG_TO.equals(belongTo)) {
            //空或'公共'，返回空列表
            return new ArrayList<>();
        } else {
            return usefulInformationMapper.listUsefulInformationByBelongTo(belongTo);
        }
    }

    @Override
    public List<UsefulInformation> listDefaultPublicUsefulInformation() {
        return usefulInformationMapper.listUsefulInformationByBelongTo(UsefulInformation.DEFAULT_BELONG_TO);
    }

    @Override
    public List<String> listAllBelongTo() {
        List<String> allBelongTo = new ArrayList<>();
        allBelongTo.add(UsefulInformation.DEFAULT_BELONG_TO);
        allBelongTo.addAll(CampusEnum.getCampusNamesList());
        return allBelongTo;
    }

    @Override
    public PageInfo<UsefulInformation> listUsefulInformation(MyPage myPage, UsefulInformationSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<UsefulInformation> allUsefulInformation = usefulInformationMapper.listUsefulInformation(condition);
        return new PageInfo<>(allUsefulInformation);
    }

    @Override
    public Long getRecommendedSequence(String belongTo) {
        if (StringUtils.isEmpty(belongTo)) {
            return null;
        }
        Long currentMaxSequence = usefulInformationMapper.getRecommendedSequence(belongTo);
        if (currentMaxSequence == null) {
            //如果当前类别没有任何记录
            currentMaxSequence = -20L;
        }
        return currentMaxSequence + UsefulInformation.DEFAULT_SEQUENCE_INTERVAL;
    }

    @Override
    public String uploadImage(MultipartFile file) throws InvalidFileInputException {
        User user = userService.getSessionUserInfo();
        return uploadImage(file, user.getId().toString());
    }

    @Override
    public String uploadImage(MultipartFile file, String id) throws InvalidFileInputException {
        if (file == null || file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }


        String originalFilename = file.getOriginalFilename();
        String idStr;
        if (StringUtils.isEmpty(id)) {
            //新用户设uuid作为头像名后缀
            idStr = UUID.randomUUID().toString().replace("-", "_");
        } else {
            idStr = id;
        }
        String fileName = "image_" + idStr + originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = filePathProperties.getUsefulInformationImageDirectory();
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            logger.error("id:" + id + "——常用信息配图上传失败");
        }
        return fileName;
    }

    @Override
    public String updateUsefulInformationInfo(UsefulInformation information) {
        if (information == null) {
            return Constants.FAILURE;
        }
        UsefulInformation originalInformation = getUsefulInformationById(information.getId());
        if (originalInformation == null) {
            return Constants.FAILURE;
        }
        if (isModifiedAndRepeatedBelongToAndSequence(originalInformation, information)) {
            //所属类别或序号修改过了，判断是否与已存在的记录冲突
            return BELONG_TO_AND_SEQUENCE_REPEAT;
        }

        /*
         * 用户上传的配图的处理
         */
        dealWithUpdatingUsefulInformationImage(originalInformation, information);

        if (originalInformation.equalsExceptBaseParams(information)) {
            //未修改
            return Constants.UNCHANGED;
        }

        usefulInformationMapper.updateUsefulInformationInfo(information);
        return Constants.SUCCESS;
    }

    /**
     * 判断当前要更新的常用信息是否修改过且重复。
     * 只有相较于原来的常用信息修改过且与数据库中重复才返回false
     *
     * @param originalInformation 用来比较的原来的常用信息
     * @param newInformation      要更新的常用信息
     * @return 常用信息的归属和序号是否修改过且重复
     */
    private boolean isModifiedAndRepeatedBelongToAndSequence(UsefulInformation originalInformation, UsefulInformation newInformation) {
        if (!originalInformation.getBelongTo().equals(newInformation.getBelongTo())
                || !originalInformation.getSequence().equals(newInformation.getSequence())) {
            //所属类别或序号修改过了，判断是否与已存在的记录冲突
            if (isRepeatedBelongToAndSequence(newInformation)) {
                //修改后的类别或序号已存在
                return true;
            }
        }
        return false;
    }

    /**
     * 更新常用信息时对配图的处理。先删除，再重命名
     *
     * @param originalInformation 原来的常用信息
     * @param newInformation      更新后的常用信息
     */
    private void dealWithUpdatingUsefulInformationImage(UsefulInformation originalInformation, UsefulInformation newInformation) {
        if (!StringUtils.isEmpty(newInformation.getImage())) {
            //如果用户上传了新配图
            //删除原信息配图
            deleteInformationImage(originalInformation);
            //重命名新信息的缓存的图片文件名
            renameInformationImage(newInformation);
        } else {
            //仍设置原配图
            newInformation.setImage(originalInformation.getImage());
        }
    }

    /**
     * 对入参information，删除它在硬盘上保存的配图文件（如果有且不是默认图片的话）。
     *
     * @param information 要更新的信息
     */
    private void deleteInformationImage(UsefulInformation information) {
        if (!StringUtils.isEmpty(information.getImage())) {
            if (!information.isDefaultImage()) {
                //如果原来的配图不是默认配图，需要将原来的配图删除
                FileUtils.deleteFile(filePathProperties.getUsefulInformationImageDirectory() + information.getImage());
            }
        }
    }

    /**
     * 对要更新的信息的配图，重命名新信息的缓存的图片文件名。
     *
     * @param information 要更新的信息
     * @return 重命名后的新图片文件名
     */
    private String renameInformationImage(UsefulInformation information) {
        //将上传的新文件重名为含日期时间的newImageName，该新文件名用来保存到数据库
        String newImageName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + "_" + information.getImage();
        FileUtils.renameByName(filePathProperties.getUsefulInformationImageDirectory(), information.getImage(), newImageName);
        information.setImage(newImageName);

        return newImageName;
    }

    @Override
    public String insertOneUsefulInformation(UsefulInformation information) {
        if (information == null) {
            return Constants.FAILURE;
        }

        //所属类别或序号修改过了，判断是否与已存在的记录冲突
        if (isRepeatedBelongToAndSequence(information)) {
            //修改后的类别或序号已存在
            return BELONG_TO_AND_SEQUENCE_REPEAT;
        }

        /*
         * 用户上传的配图的处理
         */
        dealWithInsertingUsefulInformationImage(information);

        usefulInformationMapper.insertOneUsefulInformation(information);
        return Constants.SUCCESS;
    }

    /**
     * 插入常用信息时对配图的处理。重命名
     *
     * @param information 插入的常用信息
     */
    private void dealWithInsertingUsefulInformationImage(UsefulInformation information) {
        if (!StringUtils.isEmpty(information.getImage())) {
            //如果用户上传了新配图
            renameInformationImage(information);
        } else {
            information.setImage(null);
        }

    }

    @Override
    public long deleteOneUsefulInformationById(Long id) {
        if (id == null) {
            return 0;
        }

        //删除本地配图文件
        deleteInformationImageById(id);

        return usefulInformationMapper.deleteOneUsefulInformationById(id);
    }

    @Override
    public long deleteManyUsefulInformationByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }

        for (Long id : ids) {
            //删除本地配图文件
            deleteInformationImageById(id);
        }

        return usefulInformationMapper.deleteManyUsefulInformationByIds(ids);
    }

    /**
     * 根据信息id删除配图。如果配图为空或者是默认配图，不执行删除
     *
     * @param id 常用信息的id
     */
    private void deleteInformationImageById(Long id) {
        UsefulInformation information = getUsefulInformationById(id);
        if (information != null) {
            /*
             * 删除配图
             */
            deleteInformationImage(information);
        }
    }
}
