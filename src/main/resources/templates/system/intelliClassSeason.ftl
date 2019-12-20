<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>智能校历</title>
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
                <div class="layui-card-header">智能校历</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">年份-季度</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year">
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
                        <div class="layui-form-item">
                            <label class="layui-form-label">操作</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="set"
                                        style="background-color: #1E9FFF"
                                        id="set">确认修改
                                </button>
                                <button class="layui-btn" lay-submit lay-filter="clear"
                                        style="background-color: #01AAED" id="clear">清除当前日历
                                </button>
                            </div>
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
    }).use(['index', 'set', 'element', 'code', 'laydate'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , laydate = layui.laydate
                , upload = layui.upload;

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

        form.render();


        form.on('submit(clear)', function (obj) {
            var field = obj.field;

            layer.confirm('确定要清除当前校历吗？', function () {
                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {
                    },
                    url: "${ctx}/system/intelliClassSeason/delete",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            //登入成功的提示与跳转
                            return layer.msg('清除成功', {
                                offset: '15px'
                                , icon: 1
                                , time: 1000
                            });
                        } else {
                            layer.msg('未知错误');
                        }
                    }

                });
            });
        });


        //提交
        form.on('submit(set)', function (obj) {
            var field = obj.field;

            layer.confirm('确定要将校历修改成"' + field.year + field.season + field.subSeason + '"吗？', function () {
                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {
                        classYear: field.year,
                        classSeason: field.season,
                        classSubSeason: field.subSeason
                    },
                    url: "${ctx}/system/intelliClassSeason/update",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            //登入成功的提示与跳转
                            return layer.msg('修改成功', {
                                offset: '15px'
                                , icon: 1
                                , time: 1000
                            });
                        } else {
                            layer.msg('未知错误');
                        }
                    }

                });
            });
        });
    });
</script>
</body>
</html>

