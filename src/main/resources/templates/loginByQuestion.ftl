<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>小问题登入-新东方优能中学助教工作平台</title>
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
            <div class="layui-form-item" id="question">
                Q: ${question}
            </div>
            <div class="layui-form-item">
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-rate"
                               for="LAY-user-login-smscode"></label>
                        <input type="text" name="answer" lay-verify="required" lay-verType="tips" placeholder="请输入答案"
                               id="LAY-user-login-smscode" class="layui-input" autocomplete="off">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;">
                            <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                    id="change-question" name="change-question">换一个问题
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        <#--<div class="layui-form-item">-->
        <#--<div class="layui-row">-->
        <#--<div class="layui-col-xs7">-->
        <#--<label class="layadmin-user-login-icon layui-icon layui-icon-vercode"-->
        <#--for="LAY-user-login-vercode"></label>-->
        <#--<input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required"-->
        <#--placeholder="图形验证码" class="layui-input">-->
        <#--</div>-->
        <#--<div class="layui-col-xs5">-->
        <#--<div style="margin-left: 20px;">-->
        <#--<img id="captcha_img" alt="点击更换" title="点击更换"-->
        <#--onclick="refresh()" src="${ctx}/kaptcha"/>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-login-submit" id="my_button">登
                    入
                </button>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019-2020 <a href="http://blog.kurochan.top/about/" target="_blank">kurochan.top</a> 版权所有 <a href="http://www.beian.miit.gov.cn" target="_blank">沪ICP备19043725号</a></p>
    </div>
</div>

<script src="${ctx}/custom/js/external/jquery-3.3.1.min.js"></script>
<script src="${ctx}/custom/js/myButton.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
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
        // 换一个问题
        $('#change-question').click(function () {
            // 设置button效果，开始计时
            disabledSubmitButtonWithTime('change-question', '换一个问题',3);

            $.ajax({
                type: "get",
                url: "${ctx}/resetLoginQuestion",
                success: function (res) {
                    if (res.msg === "noMoreQuestions") {
                        return layer.msg("没有更多问题了!")
                    } else {
                        $("#question").html('Q: ' + res.question);
                    }
                },
                dataType: "json"
            });
        });
        /*****************************************************/


        $('#LAY-user-login-smscode').on('keydown', function (event) {
            if (event.keyCode == 13) {
                $("#my_button").trigger("click");

                return false
            }
        });
        //验证码的正确与否决定登入？
        form.on('submit(LAY-user-login-submit)', function (obj) {

            var field = obj.field;
            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '登 入', 5);
            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            //请求接口
            $.ajax({
                url: '${ctx}/loginTestByQuestion' //实际使用请改成服务端真实接口
                , data: {"answer": field.answer}
                , success: function (res) {
                    layer.closeAll('loading'); //关闭loading
                    if (res.data === "success") {
                        //登入成功的提示与跳转
                        return layer.msg('登入成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/index'; //后台主页
                        });
                    } else if (res.data === "failure") {
                        layer.msg('回答错误', {
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

    });
</script>
</body>
</html>