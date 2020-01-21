package com.jzy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.manager.util.MyTimeUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MissManyDaysLessonStudentDetailedDto
 * @Author JinZhiyun
 * @Description 缺课很多天的补课学生的dto，父类中date起始日期，本类中如果endDate为null，则默认补课日期仅为父类date一天，否则为date~endDate之间
 * @Date 2020/1/16 20:53
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MissManyDaysLessonStudentDetailedDto extends MissLessonStudentDetailedDto {
    private static final long serialVersionUID = -927707040686239126L;

    /**
     * 补课的日期的区间的最后一天
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 获得当前补课学生补课日期区间中的所有日期
     *
     * @return 补课日期区间中的所有日期
     */
    public List<Date> getDaysBetween() {
        return MyTimeUtils.getDaysBetween(getDate(), getEndDate());
    }


    /**
     * 根据起始日期date，结束日期endDate，输出字符串形式的时间区间表示。
     * 如2019/1/1~2019/1/2
     *
     * @return 字符串形式的时间区间表示
     */
    public String getDaysBetweenToString() {
        List<Date> days = getDaysBetween();
        if (days.size() == 0) {
            return "";
        }
        if (days.size() == 1) {
            //一天
            return MyTimeUtils.dateToStringYMD(days.get(0)).replace("-", "/");
        }

        //多天，输出区间形式
        String start = MyTimeUtils.dateToStringYMD(days.get(0)).replace("-", "/");
        String end = MyTimeUtils.dateToStringYMD(days.get(days.size() - 1)).replace("-", "/");
        return start + "~" + end;
    }

//    public List<MissLessonStudentDetailedDto> getMissLessonStudentDetailedDtos() throws CloneNotSupportedException {
//        List<MissLessonStudentDetailedDto> result=new ArrayList<>();
//        List<Date> days=getDaysBetween();
//        for (Date day:days){
//            this.setDate(day);
//            MissLessonStudentDetailedDto dto= (MissLessonStudentDetailedDto) this.clone();
//            result.add(dto);
//        }
//        return result;
//    }

    public static void main(String[] args) throws CloneNotSupportedException {
        MissManyDaysLessonStudentDetailedDto d = new MissManyDaysLessonStudentDetailedDto();
        d.setDate(MyTimeUtils.stringToDateYMD("2019-1-1"));
        d.setEndDate(MyTimeUtils.stringToDateYMD("2019-1-3"));
//        List<MissLessonStudentDetailedDto> dtos=d.getMissLessonStudentDetailedDtos();
//        for (MissLessonStudentDetailedDto dto:dtos){
//            System.out.println(dto);
//        }
    }
}
