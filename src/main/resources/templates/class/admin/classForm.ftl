<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>助教管理-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="weabkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-app-form-list" id="layuiadmin-app-form-list"
     style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">班级编码</label>
        <div class="layui-input-inline">
            <input type="text" name="id" value="" style="display:none;" class="layui-input">
            <input type="text" name="classId" value="" class="layui-input" lay-verify="classId" lay-verType="tips"
                   placeholder="请输入">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
        <label class="layui-form-label">班级名称</label>
        <div class="layui-input-inline">
            <input type="text" name="className" value="" class="layui-input" lay-verType="tips" lay-verify="className"
                   placeholder="请输入">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年份</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year">
            </div>
        </div>
        <label class="layui-form-label">季度</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <select name="season" id="season">
                    <option value="">请选择季度</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">曹杨</label>
        <div class="layui-input-inline">
            <select name="campus" id="campus" lay-filter="campus" lay-search>
                <option value="">请选择校区</option>
            </select>
        </div>
        <label class="layui-form-label">教室</label>
        <div class="layui-input-inline">
            <select name="classroom" id="classroom"
                    lay-search>
                <option value="">请选择教室</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-inline">
            <input type="text" name="depart" value="" class="layui-input" lay-verType="tips" lay-verify="depart"
                   placeholder="请输入">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">校区</label>
        <div class="layui-input-inline">
            <select name="campus" id="campus" lay-verType="tips" lay-verify="campus" lay-search>
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系方式</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" value="" class="layui-input" lay-verify="myphone" lay-verType="tips" placeholder="请输入">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <textarea name="remark" style="width: 400px; height: 150px;" class="layui-textarea"
                      lay-verType="tips" lay-verify="remark" placeholder="请输入"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-submit" id="layuiadmin-app-form-submit"
               value="确认添加">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-edit" id="layuiadmin-app-form-edit"
               value="确认编辑">
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
    }).use(['index', 'user', 'laydate'], function () {
        var $ = layui.$
                , form = layui.form
                , laydate = layui.laydate;

        laydate.render({
            elem: '#year'
            ,type: 'year'
        });

        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }
        $("#campus").val('${classEdit.classCampus!""}');

        var seasons = eval('(' + '${seasons}' + ')');
        for (var i = 0; i < seasons.length; i++) {
            var json = seasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#season").append(str);
        }
        $("#season").val('${classEdit.classSeason!""}');

        var grades = eval('(' + '${grades}' + ')');
        for (var i = 0; i < grades.length; i++) {
            var json = grades[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#grade").append(str);
        }

        var subjects = eval('(' + '${subjects}' + ')');
        for (var i = 0; i < subjects.length; i++) {
            var json = subjects[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subject").append(str);
        }

        var types = eval('(' + '${types}' + ')');
        for (var i = 0; i < types.length; i++) {
            var json = types[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#type").append(str);
        }

        $.ajax({
            type: "get",
            data: {campusName: '${classEdit.classCampus!""}'},
            url: "${ctx}/class/getClassroomsByCampus",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var json = data[i];
                    $("#classroom").append('<option value="' + json + '">' + json + '</option>');
                }
                $("#classroom").val('${classEdit.classroom!""}');
                form.render('select');
            }
        });

        form.render();

        //联动监听select
        form.on('select(campus)', function (data) {
            $("#classroom").empty();
            $("#classroom").append('<option value="">请选择教室</option>');
            var campus_name = $(this).attr("lay-value");
            $.ajax({
                type: "get",
                data: {campusName: campus_name},
                url: "${ctx}/class/getClassroomsByCampus",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var json = data[i];
                        $("#classroom").append('<option value="' + json + '">' + json + '</option>');
                    }
                    form.render('select');
                }
            });
        });
    });
</script>
</body>
</html>