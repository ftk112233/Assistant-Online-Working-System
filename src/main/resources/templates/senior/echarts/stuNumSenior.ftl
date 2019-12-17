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
                                    <input autocomplete="off" type="text" class="layui-input" placeholder="yyyy" id="year" name="year">
                                </div>
                                <div class="layui-input-inline">
                                    <select name="season" id="season">
                                        <option value="">请选择季度</option>
                                    </select>
                                </div>
                                <div class="layui-input-inline">
                                    <select name="subSeason" id="subSeason">
                                        <option value="">请选择分期</option>
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
                        <div id="container" style="height: 100%"></div>
                    </div>
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container2" style="height: 100%"></div>
                    </div>
                    <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                         lay-filter="LAY-index-plat">
                        <div id="container3" style="height: 100%"></div>
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
            , value: '${currentClassSeason.classYear!""}'
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

        var subSeasons = eval('(' + '${subSeasons}' + ')');
        for (var i = 0; i < subSeasons.length; i++) {
            var json = subSeasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subSeason").append(str);
        }

        $("#season").val('${currentClassSeason.classSeason!""}');
        $("#subSeason").val('${currentClassSeason.classSubSeason!""}');

        form.render();


        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        $.ajax({
            type: "get",
            data: {
                classYear: $("#year").val()
                ,classSeason:  $("#season").val()
                ,classSubSeason:  $("#subSeason").val()
                ,classCampus:  $("#campus").val()
            },
            url: "${ctx}/senior/getStudentTotalGroupByClassGradeAndType",
            success: function (res) {
                var app = {};
                option = null;
                app.title = '各年级对应班型人数分析';

                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    toolbox: {
                        feature: {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        data:res.legendData
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: res.xAxisData,
                            axisPointer: {
                                type: 'shadow'
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '各班型人数',
                            min: 0,
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        {
                            type: 'value',
                            name: '总学生数',
                            min: 0,
                            axisLabel: {
                                formatter: '{value}'
                            }
                        }
                    ],
                    series: res.series
                };
                ;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            }
        });

        var dom2 = document.getElementById("container2");
        var myChart2 = echarts.init(dom2,'light');
        $.ajax({
            type: "get",
            data: {
                classYear: $("#year").val()
                ,classSeason:  $("#season").val()
                ,classSubSeason:  $("#subSeason").val()
                ,classCampus:  $("#campus").val()
            },
            url: "${ctx}/senior/getStudentTotalGroupByClassSubjectAndType",
            success: function (res) {
                var app2 = {};
                option = null;
                app2.title = '各年级对应班型人数分析';

                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    toolbox: {
                        feature: {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        data:res.legendData
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: res.xAxisData,
                            axisPointer: {
                                type: 'shadow'
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '各班型人数',
                            min: 0,
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        {
                            type: 'value',
                            name: '总学生数',
                            min: 0,
                            axisLabel: {
                                formatter: '{value}'
                            }
                        }
                    ],
                    series: res.series
                };
                ;
                if (option && typeof option === "object") {
                    myChart2.setOption(option, true);
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
                    ,classSubSeason:  field.subSeason
                    ,classCampus:  field.campus
                },
                url: "${ctx}/senior/getStudentTotalGroupByClassGradeAndType",
                success: function (res) {
                    var option = myChart.getOption();
                    option.legend.data=res.legendData;
                    option.series = res.series;
                    option.xAxis[0].data = res.xAxisData;

                    myChart.setOption(option, true);
                }
            });

            $.ajax({
                type: "get",
                data:{
                    classYear: field.year
                    ,classSeason:  field.season
                    ,classSubSeason:  field.subSeason
                    ,classCampus:  field.campus
                },
                url: "${ctx}/senior/getStudentTotalGroupByClassSubjectAndType",
                success: function (res) {
                    var option2 = myChart2.getOption();
                    option2.legend.data=res.legendData;
                    option2.series = res.series;
                    option2.xAxis[0].data = res.xAxisData;

                    myChart2.setOption(option2, true);
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

