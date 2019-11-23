<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>忘记密码-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/login.css" media="all">
</head>
<body>
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>优能中学助教工作平台</h2>
            <p>让助教工作更便捷，更炫酷！</p>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">

            <script type="text/html" template>
                {{# if(layui.router().search.type === 'resetpass'){ }}
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                           for="LAY-user-login-password"></label>
                    <input type="password" name="password" id="LAY-user-login-password" lay-verify="mypass" lay-verType="tips"
                           placeholder="新密码" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                           for="LAY-user-login-repass"></label>
                    <input type="password" name="repass" id="LAY-user-login-repass" lay-verify="required" lay-verType="tips"
                           placeholder="确认密码" class="layui-input">
                </div>
                <input name="token" type="hidden" value="${token}"/>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-forget-resetpass">重置新密码
                    </button>
                </div>
                {{# } else { }}
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-list"
                           for="LAY-user-login-email"></label>
                    <input type="text" name="email" id="LAY-user-login-email" lay-verify="email" lay-verType="tips" placeholder="请输入您绑定的邮箱"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                                   for="LAY-user-login-smscode"></label>
                            <input type="text" name="emailcode" lay-verify="required" lay-verType="tips" placeholder="请输入邮箱验证码"
                                   id="LAY-user-login-smscode" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                        id="send-email-code" name="send-email-code">获取验证码
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-forget-submit">找回密码
                    </button>
                </div>
                {{# } }}
            </script>

        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019 <a href="http://kurochan1998.github.io/about/" target="_blank">blog.kurochan.top</a></p>
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
    }).use(['index', 'user'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , router = layui.router();


        /*****************************************************/
        //向邮箱发送验证码
        $('#send-email-code').click(function () {
            //校验邮箱
            var email = $("#LAY-user-login-email").val();
            if (!email.match(/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/)) {
                return layer.msg('请输入正确的邮箱格式');
            }

            // 设置button效果，开始计时
            disabledSubmitButtonWithTime('send-email-code', '获取验证码', 60);

            layer.msg('验证码已发送至您的邮箱，请注意查收', {
                icon: 1
                , shade: 0
            });
            //请求发送验证码
            $.ajax({
                type: "get",
                url: "${ctx}/sendVerifyCodeToEmail",
                data: {"userEmail": email},
                success: function (obj) {
                },
                dataType: "json"
            });
        });
        /*****************************************************/


        form.render();
        //找回密码下一步
        form.on('submit(LAY-user-forget-submit)', function (obj) {
            var field = obj.field;

            //请求接口
            $.ajax({
                url: '${ctx}/emailVerifyCodeTest' //实际使用请改成服务端真实接口
                , data: {"emailVerifyCode": field.emailcode, "userEmail": field.email}
                , success: function (res) {
                    if (res.data === "verifyCodeCorrect") {
                        location.hash = '/type=resetpass';
                        location.reload();
                    } else if (res.data === "emailUnregistered") {
                        layer.msg('该邮箱尚未注册', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res.data === "verifyCodeWrong") {
                        layer.msg('验证码错误', {
                            icon: 5,
                            anim: 6
                        });
                    } else {
                        layer.msg('未知错误', {
                            icon: 5,
                            anim: 6
                        });
                    }
                }
            });

            return false;

        });


        //重置密码
        form.on('submit(LAY-user-forget-resetpass)', function (obj) {
            var field = obj.field;

            //确认密码
            if (field.password !== field.repass) {
                return layer.msg('两次密码输入不一致');
            }

            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '重置新密码', 5);

            //请求接口
            $.ajax({
                url: '${ctx}/resetPassword?token=' + field.token //实际使用请改成服务端真实接口
                ,
                type: 'post'
                , data: {"userPassword": field.password}
                , success: function (res) {
                    if (res.data === "success") {
                        layer.msg('密码已成功重置', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/login'; //跳转到登入页
                        });
                    } else {
                        layer.msg('未知错误', {
                            icon: 5,
                            anim: 6
                        });
                    }
                }
            });

            return false;
        });

    });
</script>
</body>
</html>