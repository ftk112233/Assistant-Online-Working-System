package com.jzy.model.dto;

import com.jzy.model.SeasonEnum;
import com.jzy.model.SubSeasonEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ClassSeasonDto
 * @description 当前开课的年份-季度-分期
 * @date 2019/12/10 13:04
 **/
@Data
public class ClassSeasonDto implements Serializable, Comparable<ClassSeasonDto> {
    private static final long serialVersionUID = 5324664841478612380L;

    /**
     * 班级开设的年份，可以为空，2019版的班级编码中没有能标识年份的字段
     */
    private String classYear;

    /**
     * 班级开设的季度，如春季，暑假等，可以为空，长度不超过50
     */
    private String classSeason;

    /**
     * 班级开设的季度的分期，如（暑假）一期、二期等等
     */
    private String classSubSeason;

    public ClassSeasonDto() {
    }

    public ClassSeasonDto(String classYear, String classSeason, String classSubSeason) {
        this.classYear = classYear;
        this.classSeason = classSeason;
        this.classSubSeason = classSubSeason;
    }

    /**
     * 季度的排序规则，先按年份从低到高排序，后按季度由低到高排序，再按分期由低到高排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(ClassSeasonDto o) {
        if (this.getClassYear() != null && o.getClassYear() != null) {
            int i =  String.CASE_INSENSITIVE_ORDER.compare(this.getClassYear(), o.getClassYear());
            if (i != 0) {
                return i;
            }
        }

        if (this.getClassSeason() != null && o.getClassSeason() != null) {
            int i = SeasonEnum.SEASON_COMPARATOR.compare(this.getClassSeason(), o.getClassSeason());
            if (i != 0) {
                return i;
            }
        }

        if (this.getClassSubSeason() != null && o.getClassSubSeason() != null) {
            int i = SubSeasonEnum.SUB_SEASON_COMPARATOR.compare(this.getClassSubSeason(), o.getClassSubSeason());
            if (i != 0) {
                return i;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        ClassSeasonDto dto=new ClassSeasonDto("2019", "秋下", null);
        ClassSeasonDto dto1=new ClassSeasonDto("2019", "秋下", null);

        System.out.println(dto.compareTo(dto1));
    }
}
