<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>开班电话表格制作</title>
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
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">导出学生联系方式</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">年份-季度</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year" lay-verify="required" lay-verType="tips">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <div class="layui-input-inline">
                                <select name="season" id="season" lay-verify="required" lay-verType="tips">
                                    <option value="">请选择季度</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <div class="layui-input-inline">
                                <select name="subSeason" id="subSeason">
                                    <option value="">请选择分期</option>
                                </select>
                            </div>
                            <label class="layui-form-label">助教</label>
                            <div class="layui-input-inline">
                                <input name="assistantName" id="assistantName" lay-verify="realName" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="游乐王子">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">姓名前缀</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="新东方-" id="namePrefix"
                                       name="namePrefix" autocomplete="off">
                            </div>
                            <label class="layui-form-label">姓名后缀</label>
                            <div class="layui-input-inline">
                                <input name="nameSuffix" id="nameSuffix"
                                       autocomplete="off" class="layui-input"
                                       placeholder="家长">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">操作</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="export_to_form"
                                        style="background-color: #1E9FFF"
                                        id="export_to_form">导出到下方表单
                                </button>
                                <button class="layui-btn" lay-submit lay-filter="export_to_excel"
                                        style="background-color: #01AAED" id="export_to_excel">导出到excel表格
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-inline">
                                <textarea id="name" name="name" style="width: 200px; height: 300px;"
                                          class="layui-textarea" autocomplete="off"></textarea>
                                <button class="layui-btn" id="copy_name">复制所有姓名</button>
                            </div>
                            <label class="layui-form-label">联系方式</label>
                            <div class="layui-input-inline">
                                <textarea id="phone" name="phone" style="width: 150px; height: 300px;"
                                          class="layui-textarea" autocomplete="off"></textarea>
                                <button class="layui-btn" id="copy_phone">复制所有联系方式</button>
                            </div>
                        </div>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">说明</h3>
                            <ul>
                                <li>该功能主要用于开班时将学生联系方式批量导入手机通讯录，完整的导入“联系方式批量导入手机通讯录”教程参见<a style="color: blue"
                                                                                          href="http://blog.kurochan.top/2020/01/01/%E8%A1%A8%E6%A0%BC%E5%AF%BC%E5%85%A5%E6%89%8B%E6%9C%BA%E9%80%9A%E8%AE%AF%E5%BD%95%E6%96%B9%E6%B3%95/"
                                                                                          target="_blank">这里</a>。
                                </li>
                                <li>前缀和后缀的作用是修饰所有导出的姓名。如前缀填“新东方-”，后缀填“家长”，输出的所有姓名都是“新东方-XXX家长”形式。</li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<script src="${ctx}/custom/js/external/jquery-3.3.1.min.js"></script>
<script src="${ctx}/custom/js/myButton.js"></script>
<script src="${ctx}/custom/js/user.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script src="${ctx}/custom/js/myLayVerify.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'set', 'element', 'laydate'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload
                , laydate = layui.laydate;

        //=======================第一区域===========================//

        laydate.render({
            elem: '#year'
            , type: 'year'
            , value: '${currentClassSeason.classYear!""}'
        });

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

        $("#assistantName").val('${assistantName!""}');


        form.render();

        function copy(id){
            //获取input对象
            var obj = document.getElementById(id);
            //选择当前对象
            obj.select();
            try {
                //进行复制到剪切板
                if (document.execCommand("Copy", "false", null)) {
                    //如果复制成功
                    layer.msg('复制成功', {
                        icon: 1
                        , time: 1000
                    });
                } else {
                    //如果复制失败
                    layer.msg('复制失败', {
                        icon: 2
                        , time: 2000
                    });
                }
            } catch (err) {
                //如果报错
                layer.msg('复制失败', {
                    icon: 2
                    , time: 2000
                });
            }
        }

        $("#copy_name").click(function () {
            copy("name");
        });

        $("#copy_phone").click(function () {
            copy("phone");
        });

        form.on('submit(export_to_form)', function (obj) {
            var field = obj.field;
            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            $.ajax({
                type: 'get'
                ,
                url: '${ctx}/toolbox/assistant/exportStudentPhoneToForm' //实际使用请改成服务端真实接口
                ,
                data: {
                    'classYear': field.year,
                    'classSeason': field.season,
                    'classSubSeason': field.subSeason,
                    'assistantName': field.assistantName,
                    'namePrefix': field.namePrefix,
                    'nameSuffix': field.nameSuffix,
                }
                ,
                success: function (res) {
                    layer.closeAll('loading'); //关闭loading
                    $("#name").val(res.names);
                    $("#phone").val(res.phones);
                }
            });
        });

        form.on('submit(export_to_excel)', function (obj) {
            var field = obj.field;

            location.href = '${ctx}/toolbox/assistant/exportStudentPhoneToExcel?classYear=' + field.year
                    + '&classSeason=' + field.season+ '&classSubSeason=' + field.subSeason+ '&assistantName=' + field.assistantName
                    + '&namePrefix=' + field.namePrefix + '&nameSuffix=' + field.nameSuffix;
        });


    });
</script>
</body>
</html>

