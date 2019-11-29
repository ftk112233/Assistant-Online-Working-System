<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>开班电话表格制作</title>
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
                <div class="layui-card-header">公告管理</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">宽度</label>
                            <div class="layui-input-inline">
                                <input type="text" name="width" id="width" value="${announcementEdit.width!""}" class="layui-input" lay-verType="tips" lay-verify="required"
                                       placeholder="请输入">
                            </div>
                            <label class="layui-form-label">高度</label>
                            <div class="layui-input-inline">
                                <input type="text" name="height" id="height" value="${announcementEdit.height!""}" class="layui-input" lay-verType="tips" lay-verify="required"
                                       placeholder="请输入">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">标题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" id="title" value="${announcementEdit.title!""}" placeholder="请输入" autocomplete="off" class="layui-input" lay-verType="tips" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item" id="div-classroom">
                            <label class="layui-form-label">内容</label>
                            <div class="layui-input-block">
                                <textarea name="content" id="content" style="width: 1000px; height: 600px;" class="layui-textarea"
                                          lay-verType="tips" lay-verify="required"
                                          placeholder="请输入">${announcementEdit.content!""}</textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">阅后即焚</label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="clearIfRead" id="clearIfRead" lay-skin="switch"
                                       lay-text="是|否"
                                       lay-filter="clearIfRead">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">预览</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-filter="preview"
                                        id="preview">预览
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">操作</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="set"
                                        style="background-color: #1E9FFF"
                                        id="set">推送
                                </button>
                                <button class="layui-btn" lay-submit lay-filter="clear"
                                        style="background-color: #01AAED" id="clear">清除已有推送
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
    }).use(['index', 'set', 'element','code'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload;

        <#if announcementEdit.permanent == false>
            $('#clearIfRead').attr("checked", 'checked');
        </#if>

        form.render();

        $("#preview").click(function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: [$("#width").val()+'px', $("#height").val()+'px'], //宽高
                title: [$("#title").val(),'color:#393D49;background-color:#01AAED;'],
                content: $("#content").val()
            });
        });

        form.on('submit(clear)', function (obj) {
            var field = obj.field;

            //执行 Ajax 后重载
            $.ajax({
                type: 'post',
                data: {
                },
                url: "${ctx}/system/deleteAnnouncement",
                beforeSend: function (data) {
                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                }
                , success: function (data) {
                    layer.closeAll('loading'); //关闭loading
                    if (data.data === "success") {
                        //登入成功的提示与跳转
                        return layer.msg('清除成功', {
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


        //提交
        form.on('submit(set)', function (obj) {
            var field = obj.field;

            console.log(field)
            //执行 Ajax 后重载
            $.ajax({
                type: 'post',
                data: {
                    width:field.width
                    ,height:field.height
                    ,title: field.title
                    ,content: field.content
                    ,clearIfRead: field.clearIfRead
                },
                url: "${ctx}/system/pushAnnouncement",
                beforeSend: function (data) {
                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                }
                , success: function (data) {
                    layer.closeAll('loading'); //关闭loading
                    if (data.data === "success") {
                        //登入成功的提示与跳转
                        return layer.msg('推送成功', {
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

