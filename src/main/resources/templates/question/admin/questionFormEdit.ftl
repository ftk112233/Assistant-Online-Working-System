<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录问题管理-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="webkit">
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
        <input type="text" name="id" value="" style="display:none;" class="layui-input">
        <label class="layui-form-label">问题内容</label>
        <div class="layui-input-inline">
            <textarea style="width: 300px; height: 150px;" class="layui-textarea"
                      name="content" lay-verType="tips" lay-verify="content" placeholder="请输入"></textarea>
            <div class="layui-form-mid " style="color:red">*必填项</div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">答案1</label>
        <div class="layui-input-inline">
            <textarea style="width: 300px; height: 80px;" class="layui-textarea"
                      name="answer" lay-verType="tips" lay-verify="answer" placeholder="请输入"></textarea>
            <div class="layui-form-mid " style="color:red">*必填项</div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">答案2</label>
        <div class="layui-input-inline">
            <textarea style="width: 300px; height: 80px;" class="layui-textarea"
                      name="answer2" lay-verType="tips" lay-verify="answer2" placeholder="请输入"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <textarea name="remark" style="width: 300px; height: 150px;" class="layui-textarea"
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

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script src="${ctx}/custom/js/myLayVerify.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function () {
        var $ = layui.$
                , form = layui.form
                , laydate = layui.laydate;




    });
</script>
</body>
</html>