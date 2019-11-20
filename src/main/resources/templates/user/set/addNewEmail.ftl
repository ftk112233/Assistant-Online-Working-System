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
                <div class="layui-card-header">修改安全邮箱</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">新邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="newEmail" id="newEmail"
                                       lay-verify="email" lay-verType="tips" autocomplete="off" placeholder="请输入新邮箱" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="tip">点击获取验证码，将向您的新邮箱发送验证码，输入正确的验证码即可完成绑定！</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱验证码</label>
                            <div class="layui-input-inline">
                                <div class="layui-row">
                                    <input type="text" name="emailcode" lay-verify="required" lay-verType="tips" placeholder="请输入邮箱验证码"
                                           id="LAY-user-login-smscode" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-input-inline">
                                <div style="margin-left: 10px">
                                    <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                            id="send-email-code" name="send-email-code" style="width: auto">获取验证码
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmyEmail" id="my_button">确认修改</button>
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


        /*************************************************************/
        $('#send-email-code').click(function () {
            //校验邮箱
            var email = $("#newEmail").val();
            if (!email.match(/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/)) {
                return layer.msg('请输入正确的邮箱格式');
            }

            // 设置button效果，开始计时
            disabledSubmitButtonWithTime('send-email-code', '获取验证码', 60);

            layer.msg('验证码已发送至你的邮箱，请注意查收', {
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
        /*************************************************************/


        form.render();

        //提交
        form.on('submit(setmyEmail)', function (obj) {
            var field = obj.field;

            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '确认修改', 5);

            //同时验证码正确后，修改绑定邮箱ajax
            $.ajax({
                url: '${ctx}/user/addNewEmail' //实际使用请改成服务端真实接口
                , type: 'post'
                ,
                data: {
                    "emailVerifyCode": field.emailcode,
                    "newEmail": field.newEmail
                }
                ,
                success: function (res) {
                    if (res.data === "verifyCodeWrong") {
                        layer.msg('验证码错误', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res.data === "newEmailExist") {
                        return layer.msg('此新邮箱已被注册', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res.data === "updateEmailSuccess") {
                        layer.msg('修改已完成，请F5刷新页面', {
                            icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/user/setInfo';
                        });
                    } else {
                        return layer.msg('未知错误', {
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

