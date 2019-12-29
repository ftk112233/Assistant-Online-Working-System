package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.model.CampusEnum;
import com.jzy.model.TypeEnum;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import com.jzy.model.dto.echarts.GroupedByGradeAndTypeObjectTotal;
import com.jzy.model.dto.echarts.GroupedBySubjectAndTypeObjectTotal;
import com.jzy.model.dto.echarts.GroupedByTypeObjectTotal;
import com.jzy.model.entity.Class;
import com.jzy.model.vo.echarts.EchartsFactory;
import com.jzy.model.vo.echarts.NamesAndValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SeniorController
 * @description 高级功能的控制器
 * @date 2019/12/4 13:49
 **/
@Controller
@RequestMapping("/senior")
public class SeniorController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(SeniorController.class);

    /**
     * 学生人数分析页面
     *
     * @return
     */
    @RequestMapping("/echarts/stuNumPage")
    public String stuNumPage(Model model) {
        model.addAttribute(ModelConstants.CURRENT_ClASS_SEASON_MODEL_KEY, classService.getCurrentClassSeason());

        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(Class.SEASONS));
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SUB_SEASONS_MODEL_KEY, JSON.toJSONString(Class.SUB_SEASONS));
        return "senior/echarts/stuNum";
    }

    /**
     * 条件查询各年级下的学生人数比
     *
     * @param condition 查询条件封装
     * @return
     */
    @RequestMapping("/getStudentTotalGroupByClassGrade")
    @ResponseBody
    public Map<String, Object> getStudentTotalGroupByClassGrade(StudentAndClassSearchCondition condition) {
        Map<String, Object> map = new HashMap<>(2);
        NamesAndValues namesAndValues = studentAndClassService.countStudentsGroupByClassGrade(condition);

        map.put("names", namesAndValues.getNames());
        map.put("values", namesAndValues.getValues());
        return map;
    }

    /**
     * 条件查询各学科的学生人数比
     *
     * @param condition 查询条件封装
     * @return
     */
    @RequestMapping("/getStudentTotalGroupByClassSubject")
    @ResponseBody
    public Map<String, Object> getStudentTotalGroupByClassSubject(StudentAndClassSearchCondition condition) {
        Map<String, Object> map = new HashMap<>(2);
        NamesAndValues namesAndValues = studentAndClassService.countStudentsGroupByClassSubject(condition);

        map.put("names", namesAndValues.getNames());
        map.put("values", namesAndValues.getValues());
        return map;
    }

    /**
     * 条件查询各班型的学生人数比
     *
     * @param condition 查询条件封装
     * @return
     */
    @RequestMapping("/getStudentTotalGroupByClassType")
    @ResponseBody
    public Map<String, Object> getStudentTotalGroupByClassType(StudentAndClassSearchCondition condition) {
        Map<String, Object> map = new HashMap<>(2);
        List<GroupedByTypeObjectTotal> objectTotals = studentAndClassService.countStudentsGroupByClassType(condition);
        NamesAndValues namesAndValues = new NamesAndValues();
        namesAndValues.addAll(objectTotals);


        map.put("names", namesAndValues.getNames());
        map.put("objects", objectTotals);
        return map;
    }

    /**
     * 学生人数分析（高级）页面
     *
     * @return
     */
    @RequestMapping("/echarts/stuNumSeniorPage")
    public String stuNumSeniorPage(Model model) {
        model.addAttribute(ModelConstants.CURRENT_ClASS_SEASON_MODEL_KEY, classService.getCurrentClassSeason());

        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(Class.SEASONS));
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SUB_SEASONS_MODEL_KEY, JSON.toJSONString(Class.SUB_SEASONS));
        return "senior/echarts/stuNumSenior";
    }

    /**
     * 查询指定年级对应人数，以及该年级下各班型对应人数
     *
     * @param condition 查询条件封装
     * @return
     */
    @RequestMapping("/getStudentTotalGroupByClassGradeAndType")
    @ResponseBody
    public Map<String, Object> getStudentTotalGroupByClassGradeAndType(StudentAndClassSearchCondition condition) {
        Map<String, Object> map = new HashMap<>();
        List<GroupedByGradeAndTypeObjectTotal> objectTotals = studentAndClassService.countStudentsGroupByClassGradeAndType(condition);

        List<Object> series = new ArrayList<>();

        //总人数列表
        List<Long> seriesLastData = new ArrayList<>();

        List<String> legendData = new ArrayList<>();

        List<String> xAxisData = new ArrayList<>();

        //第一次遍历读取取班型和年级名称，还有总人数
        for (GroupedByGradeAndTypeObjectTotal objectTotal : objectTotals) {
            //年级名称，横坐标
            xAxisData.add(objectTotal.getName());
            //总人数
            seriesLastData.add(objectTotal.getValue());

            //取出班型及对应人数
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = objectTotal.getGroupedByTypeObjectTotals();
            for (GroupedByTypeObjectTotal byTypeObjectTotal : byTypeObjectTotals) {
                //遍历班型及对应人数
                if (!legendData.contains(byTypeObjectTotal.getName())) {
                    //如果当前班型结果集合中没有该班型，就添加进去
                    legendData.add(byTypeObjectTotal.getName());
                }
            }
        }

        //班型排序
        legendData.sort(TypeEnum.TYPE_COMPARATOR);

        //为存放series的data列表建立一个Map类型中间变量，方便后面读写处理
        Map<String, List<Long>> seriesDataTmp = new HashMap<>(legendData.size());
        for (String type : legendData) {
            List<Long> init = new ArrayList<>(objectTotals.size());
            for (int i = 0; i < objectTotals.size(); i++) {
                init.add(0L);
            }
            //键为班型，值初始化为全0的list（长度和objectTotals，即年级数量相同）
            seriesDataTmp.put(type, init);
        }

        //第二次遍历取班型对应每个年级人数list
        for (int i = 0; i < objectTotals.size(); i++) {
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = objectTotals.get(i).getGroupedByTypeObjectTotals();
            for (GroupedByTypeObjectTotal byTypeObjectTotal : byTypeObjectTotals) {
                //遍历当前年级的班型及对应人数，添加到中间变量中
                seriesDataTmp.get(byTypeObjectTotal.getName()).set(i, byTypeObjectTotal.getValue());
            }
        }

        for (String type : legendData) {
            //遍历存在的排序过的班型，根据班型、对应series的data列表，封装好传给前端echarts
            series.add(EchartsFactory.getSeries(type, seriesDataTmp.get(type)));
        }


        legendData.add("总学生数");
        map.put("legendData", legendData);
        map.put("xAxisData", xAxisData);

        series.add(EchartsFactory.getSeriesWithAxis("总学生数", seriesLastData));
        map.put("series", series);
        return map;
    }


    /**
     * 查询指定学科对应人数，以及该学科下各班型对应人数
     *
     * @param condition 查询条件封装
     * @return
     */
    @RequestMapping("/getStudentTotalGroupByClassSubjectAndType")
    @ResponseBody
    public Map<String, Object> getStudentTotalGroupByClassSubjectAndType(StudentAndClassSearchCondition condition) {
        Map<String, Object> map = new HashMap<>();
        List<GroupedBySubjectAndTypeObjectTotal> objectTotals = studentAndClassService.countStudentsGroupByClassSubjectAndType(condition);

        List<Object> series = new ArrayList<>();

        //总人数列表
        List<Long> seriesLastData = new ArrayList<>();

        List<String> legendData = new ArrayList<>();

        List<String> xAxisData = new ArrayList<>();

        //第一次遍历读取取班型和学科名称，还有总人数
        for (GroupedBySubjectAndTypeObjectTotal objectTotal : objectTotals) {
            //学科名称，横坐标
            xAxisData.add(objectTotal.getName());
            //总人数
            seriesLastData.add(objectTotal.getValue());

            //取出班型及对应人数
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = objectTotal.getGroupedByTypeObjectTotals();
            for (GroupedByTypeObjectTotal byTypeObjectTotal : byTypeObjectTotals) {
                //遍历班型及对应人数
                if (!legendData.contains(byTypeObjectTotal.getName())) {
                    //如果当前班型结果集合中没有该班型，就添加进去
                    legendData.add(byTypeObjectTotal.getName());
                }
            }
        }

        //班型排序
        legendData.sort(TypeEnum.TYPE_COMPARATOR);

        //为存放series的data列表建立一个Map类型中间变量，方便后面读写处理
        Map<String, List<Long>> seriesDataTmp = new HashMap<>(legendData.size());
        for (String type : legendData) {
            List<Long> init = new ArrayList<>(objectTotals.size());
            for (int i = 0; i < objectTotals.size(); i++) {
                init.add(0L);
            }
            //键为班型，值初始化为全0的list（长度和objectTotals，即年级数量相同）
            seriesDataTmp.put(type, init);
        }

        //第二次遍历取班型对应每个学科人数list
        for (int i = 0; i < objectTotals.size(); i++) {
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = objectTotals.get(i).getGroupedByTypeObjectTotals();
            for (GroupedByTypeObjectTotal byTypeObjectTotal : byTypeObjectTotals) {
                //遍历当前年级的班型及对应人数，添加到中间变量中
                seriesDataTmp.get(byTypeObjectTotal.getName()).set(i, byTypeObjectTotal.getValue());
            }
        }

        for (String type : legendData) {
            //遍历存在的排序过的班型，根据班型、对应series的data列表，封装好传给前端echarts
            series.add(EchartsFactory.getSeries(type, seriesDataTmp.get(type)));
        }


        legendData.add("总学生数");
        map.put("legendData", legendData);
        map.put("xAxisData", xAxisData);

        series.add(EchartsFactory.getSeriesWithAxis("总学生数", seriesLastData));
        map.put("series", series);
        return map;
    }
}
