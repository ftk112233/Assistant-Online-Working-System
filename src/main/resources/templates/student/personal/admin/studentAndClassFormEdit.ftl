<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生上课信息管理-新东方优能中学助教工作平台</title>
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
        <label class="layui-form-label">学员号</label>
        <div class="layui-input-inline">
            <input type="text" name="id" value="" style="display:none;" class="layui-input">
            <input type="text" name="studentId" value="" class="layui-input" lay-verify="studentId" lay-verType="tips"
                   placeholder="请输入" readonly>
        </div>
        <div class="layui-form-mid " style="color:red">不可修改</div>
        <label class="layui-form-label">学员姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="studentName" value="" class="layui-input" lay-verType="tips" lay-verify="studentName"
                   placeholder="请输入" readonly>
        </div>
        <div class="layui-form-mid " style="color:red">不可修改</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改所读班级班号</label>
        <div class="layui-input-inline">
            <select name="classId" id="classId"  lay-verify="classId" lay-verType="tips" lay-search>
                <option value="">请输入或选择班级编码</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
        <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #1E9FFF"
                id="preview-class">预览班级信息
        </button>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">使用当前时间作为进班时间 </label>
        <div class="layui-input-inline">
            <input type="checkbox" name="currentTime" lay-skin="switch" lay-text="是|否"
                   lay-filter="currentTime" checked>
        </div>
    </div>
    <div class="layui-form-item" id="div-registerTime" hidden="hidden">
        <label class="layui-form-label">修改进班时间</label>
        <div class="layui-input-inline">
            <input name="registerTime" id="registerTime" lay-verify="registerTime" lay-verType="tips"
                   autocomplete="off" class="layui-input" placeholder="yyyy-MM-dd HH:mm:ss">
        </div>
        <div class="layui-form-mid layui-word-aux">此为原班进班时间</div>
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
            elem: '#registerTime'
            ,type: 'datetime'
        });

        var classIds = eval('(' + '${classIds}' + ')');
        for (var i = 0; i < classIds.length; i++) {
            var json = classIds[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#classId").append(str);
        }
        $("#classId").val('${studentAndClassEdit.classId!""}');

        form.render();

        //监听自动解析开关
        form.on('switch(currentTime)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-registerTime").hide();
            } else {
                $("#div-registerTime").show();
            }
        });

        //解析班级编码
        $("#preview-class").click(function () {
            var othis = $(this)
                    , href = '/class/admin/getPreviewClassInfo?classId=' + $("#classId").val()
                    , text = "预览班级信息"
                    , router = layui.router();


            var topLayui = parent === self ? layui : top.layui;
            topLayui.index.openTabsPage(href, text || othis.text());
        });
    });
</script>
</body>
</html>