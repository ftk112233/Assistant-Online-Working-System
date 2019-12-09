<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登入-新东方优能中学助教工作平台</title>
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
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username"
                       for="LAY-user-login-username"></label>
                <input type="text" name="username" id="LAY-user-login-username" lay-verify="required"
                       placeholder="用户名/身份证/邮箱/手机号" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-password"></label>
                <input type="password" name="password" id="LAY-user-login-password" lay-verify="required" lay-verType="tips"
                       placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                               for="LAY-user-login-vercode"></label>
                        <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required" lay-verType="tips"
                               placeholder="图形验证码" class="layui-input">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 20px;">
                            <img id="captcha_img" alt="点击更换" title="点击更换"
                                 onclick="refresh()" src="${ctx}/kaptcha"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 20px;">
                <input type="checkbox" name="rememberMe" id="rememberMe" lay-skin="primary" title="记住密码">
                <a href="${ctx}/forget" class="layadmin-user-jump-change layadmin-link"
                   style="margin-top: 7px;">忘记密码？</a>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-login-submit" id="my_button">登
                    入
                </button>
            </div>
            <div class="layui-trans layui-form-item layadmin-user-login-other">
                <label>验证码登入</label>
                <a href="${ctx}/loginByEmailCode"><i class="layui-icon layui-icon-cellphone"></i></a>

                <a href="${ctx}/guestLogin" class="layadmin-user-jump-change layadmin-link">懒癌患者登录方式?</a>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019 <a href="http://blog.kurochan.top/about/" target="_blank">kurochan.top</a></p>
    </div>
</div>

<script src="${ctx}/custom/js/external/jquery-3.3.1.min.js"></script>
<script src="${ctx}/custom/js/myButton.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script>
    // kaptcha验证码的更新
    function refresh() {
        document.getElementById('captcha_img').src = "${ctx}/kaptcha?" + Math.random();
    }

    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , router = layui.router()
                , search = router.search;


        //提交
        form.on('submit(LAY-user-login-submit)', function (obj) {
            var field = obj.field;

            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '登 入', 5);

            //请求登入接口
            $.ajax({
                type: 'post'
                ,
                url: '${ctx}/loginTest' //实际使用请改成服务端真实接口
                ,
                data: {
                    'userName': field.username,
                    'userPassword': field.password,
                    'imgCode': field.vercode,
                    'rememberMe': field.rememberMe
                }
                ,
                success: function (res) {
                    // //请求成功后，写入 access_token
                    // layui.data(setter.tableName, {
                    //     key: setter.request.tokenName
                    //     ,value: res.data.access_token
                    // });
                    if (res.data.success) {
                        //登入成功的提示与跳转
                        return layer.msg('登入成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/index'; //后台主页
                        });
                    } else {
                        refresh();

                        if (res.data.imgCodeWrong) {
                            return layer.msg("验证码错误！");
                        }

                        if (res.data.unknownAccount) {
                            return layer.msg('账号不存在', {
                                offset: '15px'
                                , icon: 2
                                , time: 2000
                            });
                        }

                        if (res.data.passwordWrong) {
                            return layer.msg('密码错误', {
                                offset: '15px'
                                , icon: 2
                                , time: 2000
                            });
                        }
                        // verifyCode = new GVerify("v_container");
                        if (res.data.locked) {
                            return layer.msg('当前用户名连续输入错误' + res.data.wrongTimes + '次，已冻结，请' + res.data.timeRemaining + '分钟后重试！尝试其他登录方式或找回密码', {
                                offset: '15px'
                                , icon: 2
                                , time: 5000
                            });

                        }

                        layer.msg('未知错误', {
                            offset: '15px'
                            , icon: 2
                            , time: 2000
                        });
                    }
                }
            });

        });
    });
</script>
</body>
</html>