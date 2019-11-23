<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
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
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">当前密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="oldPassword" lay-verify="required" lay-verType="tips"
                                       class="layui-input" placeholder="请输入当前密码">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="password" lay-verify="mypass" lay-verType="tips"
                                       autocomplete="off" id="LAY_password" class="layui-input" placeholder="请输入新密码">
                            </div>
                            <div class="layui-form-mid layui-word-aux">6~20个字符(数字、字母、下划线)</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">确认新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="repassword" lay-verify="repass" lay-verType="tips"
                                       autocomplete="off" class="layui-input"  placeholder="请确认新密码">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmypass" id="my_button">确认修改</button>
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
    }).use(['index', 'set', 'element'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router();

        //提交
        form.on('submit(setmypass)', function (obj) {
            var field = obj.field;

            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '确认修改', 5);

            $.ajax({
                url: '${ctx}/user/updateOwnPassword' //实际使用请改成服务端真实接口
                , type: 'post'
                ,
                data: {
                    "oldPassword": field.oldPassword,
                    "newPassword": field.repassword
                }
                ,
                success: function (res) {
                    if (res.data === "oldPasswordWrong") {
                        return layer.msg('原始密码错误', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res.data === "success") {
                        layer.msg('修改已完成，请F5刷新页面', {
                            icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/user/setPassword';
                        });
                    } else {
                        return layer.msg('未知错误', {
                            icon: 5,
                            anim: 6
                        });
                    }

                }
            });

        });
    });
</script>
</body>
</html>

