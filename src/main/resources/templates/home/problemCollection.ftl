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
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">提交反馈</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">匿名</label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="hideName" id="hideName" lay-skin="switch"
                                       lay-text="是|否"
                                       lay-filter="hideName">
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-realName">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="realName" id="realName" value="" class="layui-input"
                                       lay-verType="tips" lay-verify="required"
                                       placeholder="推荐真实姓名">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="email" id="email" value="" class="layui-input"
                                       lay-verType="tips" lay-verify="myemail"
                                       placeholder="邮箱">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">标题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" id="title" value="" placeholder="一个短语概括下您遇到问题"
                                       autocomplete="off" class="layui-input" lay-verType="tips" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">内容</label>
                            <div class="layui-input-block">
                                <textarea name="content" id="content" style="width: 1000px; height: 400px;"
                                          class="layui-textarea"
                                          lay-verType="tips" lay-verify="required"
                                          placeholder="问题描述: 在使用XXX功能模块时，执行XXX操作，遇到了XXX问题。"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">操作</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="send"
                                        id="send">提交
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
    }).use(['index', 'set', 'element', 'code'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload;

        //监听自动解析开关
        form.on('switch(hideName)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#realName").attr('lay-verify', '');
                $("#div-realName").hide();
            } else {
                $("#realName").attr('lay-verify', 'required');
                $("#div-realName").show();
            }
        });


        //提交
        form.on('submit(send)', function (obj) {
            var field = obj.field;

            //执行 Ajax 后重载
            $.ajax({
                type: 'post',
                data: {
                    hide: field.hideName
                    , realName: field.realName
                    , email: field.email
                    , title: field.title
                    , content: field.content
                },
                url: "${ctx}/sendProblem",
                beforeSend: function (data) {
                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                }
                , success: function (data) {
                    layer.closeAll('loading'); //关闭loading
                    if (data.data === "success") {
                        //登入成功的提示与跳转
                        return layer.msg('发送成功', {
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
</script>
</body>
</html>

