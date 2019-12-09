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
                <div class="layui-card-header">年份-季度-校区下的学生人数分析</div>
                <div class="layui-card-body">
                    <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                        <div class="layui-form-item" id="form">
                            <div class="layui-inline">
                                <label class="layui-form-label">年份-季度</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year">
                                </div>
                                <div class="layui-input-inline">
                                    <select name="season" id="season">
                                        <option value="">请选择季度</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">校区</label>
                                <div class="layui-input-inline">
                                    <select name="campus" id="campus" lay-filter="campus" lay-search>
                                        <option value="">请选择校区</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="reload" lay-submit
                                        lay-filter="LAY-app-contcomm-search">
                                    <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                                </button>
                                <button class="layui-btn layuiadmin-btn-comm" data-type="reload"
                                        id="clear">
                                    清空
                                </button>
                                <#--<button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" id="allCollege"-->
                                        <#--style="background-color: #FF5722">所有学院人数比-->
                                <#--</button>-->
                                <#--<button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" id="allMajor"-->
                                        <#--style="background-color: #FFB800">所有专业人数比-->
                                <#--</button>-->
                                <#--<button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" id="allClass"-->
                                        <#--style="background-color: #01AAED">所有班级人数比-->
                                <#--</button>-->
                            </div>
                        </div>
                    </div>
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container3" style="height: 100%"></div>
                    </div>
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container" style="height: 100%"></div>
                    </div>
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

        laydate.render({
            elem: '#year'
            , type: 'year'
            , value: '${currentYear!""}'
        });


        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }

        var seasons = eval('(' + '${seasons}' + ')');
        for (var i = 0; i < seasons.length; i++) {
            var json = seasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#season").append(str);
        }
        $("#season").val('${currentSeason!""}');

        form.render();


        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        $.ajax({
            type: "get",
            data: {
                classYear: $("#year").val()
                ,classSeason:  $("#season").val()
                ,classCampus:  $("#campus").val()
            },
            url: "${ctx}/senior/getStudentTotalGroupByClassGrade",
            success: function (res) {
                var app = {};
                option = null;
                app.title = '各年级在读学生人数比';

                option = {
                    color: ['#3398DB'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : res.names,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'人数',
                            type:'bar',
                            barWidth: '60%',
                            data: res.values
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }


            }
        });

        var dom2 = document.getElementById("container2");
        var myChart2 = echarts.init(dom2);
        $.ajax({
            type: "get",
            data: {
                classYear: $("#year").val()
                ,classSeason:  $("#season").val()
                ,classCampus:  $("#campus").val()
            },
            url: "${ctx}/senior/getStudentTotalGroupByClassSubject",
            success: function (res) {
                var app2 = {};
                option = null;
                app2.title = '各学科在读人数比';

                option = {
                    color: ['#FFB800'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : res.names,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'人数',
                            type:'bar',
                            barWidth: '60%',
                            data: res.values
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart2.setOption(option, true);
                }
            }
        });

        var dom3 = document.getElementById("container3");
        var myChart3 = echarts.init(dom3);
        $.ajax({
            type: "get",
            data: {
                classYear: $("#year").val()
                ,classSeason:  $("#season").val()
                ,classCampus:  $("#campus").val()
            },
            url: "${ctx}/senior/getStudentTotalGroupByClassType",
            success: function (res) {
                var app3 = {};
                option = null;
                option = {
                    title : {
                        text: '各班型人数分布',
                        subtext: '所有班型',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: res.names
                    },
                    series : [
                        {
                            name: '人数',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:res.objects,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart3.setOption(option, true);
                }
            }
        });

        //监听搜索
        form.on('submit(LAY-app-contcomm-search)', function (data) {
            var field = data.field;


            $.ajax({
                type: "get",
                data:{
                    classYear: field.year
                    ,classSeason:  field.season
                    ,classCampus:  field.campus
                },
                url: "${ctx}/senior/getStudentTotalGroupByClassGrade",
                success: function (res) {
                    var option = myChart.getOption();

                    option.series[0].data = res.values;
                    option.xAxis[0].data = res.names;

                    myChart.setOption(option, true);
                }
            });

            $.ajax({
                type: "get",
                data:{
                    classYear: field.year
                    ,classSeason:  field.season
                    ,classCampus:  field.campus
                },
                url: "${ctx}/senior/getStudentTotalGroupByClassSubject",
                success: function (res) {
                    var option2 = myChart2.getOption();

                    option2.series[0].data = res.values;
                    option2.xAxis[0].data = res.names;

                    myChart2.setOption(option2, true);
                }
            });

            $.ajax({
                type: "get",
                data:{
                    classYear: field.year
                    ,classSeason:  field.season
                    ,classCampus:  field.campus
                },
                url: "${ctx}/senior/getStudentTotalGroupByClassType",
                success: function (res) {
                    var option3 = myChart3.getOption();

                    option3.series[0].data = res.objects;
                    option3.legend.data = res.names;

                    myChart3.setOption(option3, true);
                }
            });
        });


        $("#clear").click(function () {
            $("#form input").val("");
            $("#form select").val("");
        });
    });
</script>
</body>
</html>

