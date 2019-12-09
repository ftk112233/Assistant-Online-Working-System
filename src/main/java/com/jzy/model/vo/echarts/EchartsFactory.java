package com.jzy.model.vo.echarts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author JinZhiyun
 * @ClassName EchartsFactory
 * @Description 处理Echarts所需传输对象类的工厂类
 * @Date 2019/7/23 21:21
 * @Version 1.0
 **/
public class EchartsFactory implements Serializable {
    private static final long serialVersionUID = -8277175130803043490L;

    @Data
    private static class Series {
        protected String type;

        protected String name;

        protected List<Long> data;

        public Series() {
        }

        public Series(String type, String name, List<Long> data) {
            this.type = type;
            this.name = name;
            this.data = data;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    private static class SeriesWithAxis extends Series {
        private int yAxisIndex;

        public SeriesWithAxis(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public SeriesWithAxis(String type, String name, int yAxisIndex, List<Long> data) {
            this.type = type;
            this.name = name;
            this.yAxisIndex = yAxisIndex;
            this.data = data;
        }
    }


    public static SeriesWithAxis getSeriesWithAxis(String name, List<Long> data) {
        return new SeriesWithAxis("line", name, 1, data);
    }

    public static Series getSeries(String name, List<Long> data) {
        return new Series("bar", name, data);
    }

}
