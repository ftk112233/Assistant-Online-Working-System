<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>问题收集</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>


<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12">
            <div class="layui-card">
                <div class="layui-card-header">近30天内站点各功能区访客统计</div>
                <div class="layui-card-body">
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container" style="height: 100%"></div>
                    </div>
                </div>
            </div>
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-card-header">近30天内站点各时间段访客统计</div>
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container2" style="height: 100%"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/custom/js/external/echarts.min.js"></script>

<script src="${ctx}/custom/js/external/jquery-3.3.1.min.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user', 'upload', 'laydate'], function () {
        var $ = layui.$
                , admin = layui.admin
                , form = layui.form
                , table = layui.table
                , laypage = layui.laypage
                , laytpl = layui.laytpl
                , upload = layui.upload
                , laydate = layui.laydate;

        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);

        var dom2 = document.getElementById("container2");
        var myChart2 = echarts.init(dom2);
        $.ajax({
            type: "get",
            data: {},
            url: "${ctx}/getRecentVisitorStatistics",
            success: function (res) {
                var app = {};
                option = null;
                option = {
                    title: {
                        text: '访客(人次)统计曲线图'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: res.legendData
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: res.xAxisData
                    },
                    yAxis: {
                        type: 'value',
                        name: '人次'
                    },
                    series: res.series
                };
                ;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }

                option2 = null;
                option2 = {
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: res.xAxisData2
                    },
                    yAxis: {
                        type: 'value',
                        name: '人次'
                    },
                    series: [{
                        data: res.seriesData2,
                        type: 'line',
                        smooth: true,
                        areaStyle: {}
                    }]
                };
                ;
                if (option2 && typeof option2 === "object") {
                    myChart2.setOption(option2, true);
                }
            }
        });


    });
</script>
</body>
</html>

