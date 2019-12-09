<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>消息中心-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="weabkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>
<div class="layui-fluid" id="LAY-app-message-detail">
    <div class="layui-card layuiAdmin-msg-detail">
        <div class="layui-card-header">
            <h1>${userMessageDtoDetail.messageTitle!""}</h1>
            <p>
                <span>${userMessageDtoDetail.messageTime?string('yyyy-MM-dd HH:mm:ss')!""}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx}/user/showIcon?userIcon=${userMessageDtoDetail.userFromIcon!""}" class="layui-nav-img">&nbsp;${userMessageDtoDetail.userFrom!""}</span>
            </p>
        </div>
        <div class="layui-card-body layui-text">
            <div class="layadmin-text">
            ${userMessageDtoDetail.messageContent!""}
            <#--<blockquote class="layui-elem-quote">-->
            <#--注：这里读取的是静态的模拟接口，实际应用时，您可以在该页面源代码中，修改成以下任意一种方式-->
            <#--<ul>-->
            <#--<li>将 <em>lay-url=""</em> 改成你的真实接口，系统会自动识别该动态模板，直接前端渲染。</li>-->
            <#--<li>剔除 script 动态模板标签，改成服务端渲染。</li>-->
            <#--</ul>-->
            <#--</blockquote>-->
                      <#if userMessageDtoDetail.messagePicture?exists>
                        <div style="cursor: pointer;" onclick="show_img(this)">
                            <img height="300px"
                                 src="${ctx}/user/message/getPicture?messagePicture=${userMessageDtoDetail.messagePicture}">
                        </div>
                      </#if>


            </div>

            <div style="padding-top: 30px;">
                <a href="${ctx}/user/message/page" layadmin-event="back"
                   class="layui-btn layui-btn-primary layui-btn-sm">返回上级</a>
                <a href="javascript:void(0);" id="reply"
                   class="layui-btn layui-btn-primary layui-btn-sm">回复</a>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/custom/js/external/jquery-3.3.1.min.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user', 'upload'], function () {
        var $ = layui.$
                , form = layui.form
                , laydate = layui.laydate
                , upload = layui.upload;

        show_img = function (t) {
            var t = $(t).find("img");
            //页面层
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: ['auto'],
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: '<div style="text-align:center"><img style="height:600px;" src="' + $(t).attr('src') + '" /></div>'
            });
        };

        $("#reply").click(function () {
            if ('${userMessageDtoDetail.userFromId!""}'===''){
                layer.msg("你不能回复匿名用户！")
            } else {
                location.href='${ctx}/user/message/replyForm?id=${userMessageDtoDetail.userFromId!""}'+'&title='+'${userMessageDtoDetail.messageTitle!""}'
            }
        });

    });
</script>
</body>
</html>