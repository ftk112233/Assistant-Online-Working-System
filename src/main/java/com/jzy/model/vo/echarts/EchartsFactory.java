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
    public static class MixLineBarSeries extends Series {
        private int yAxisIndex;

        public MixLineBarSeries(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public MixLineBarSeries(String type, String name, int yAxisIndex, List<Long> data) {
            this.type = type;
            this.name = name;
            this.yAxisIndex = yAxisIndex;
            this.data = data;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class LineStackSeries extends Series {
        private boolean smooth;

        public LineStackSeries(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public LineStackSeries(String type, String name, boolean smooth, List<Long> data) {
            this.type = type;
            this.name = name;
            this.smooth = smooth;
            this.data = data;
        }

    }

    public static LineStackSeries getLineStackSeries(String name, boolean smooth, List<Long> data) {
        return new LineStackSeries("line", name, smooth, data);
    }

    public static MixLineBarSeries getMixLineBarSeries(String name, List<Long> data) {
        return new MixLineBarSeries("line", name, 1, data);
    }

    public static Series getSeries(String name, List<Long> data) {
        return new Series("bar", name, data);
    }

}
