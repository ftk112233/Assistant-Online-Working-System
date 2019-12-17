<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发送信息</title>
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
                <div class="layui-card-header">发送信息</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <input type="text" name="id" id="id" value="${userSendTo.id!""}" style="display:none;"
                               class="layui-input">
                        <div class="layui-form-item">
                            <label class="layui-form-label">匿名发送</label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="hideName" id="hideName" lay-skin="switch"
                                       lay-text="是|否"
                                       lay-filter="hideName">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">发送给</label>
                            <div class="layui-input-block">
                                <input type="text" value="${userSendToShow!""}" placeholder="张三"
                                       autocomplete="off" class="layui-input" lay-verType="tips" lay-verify="required"
                                       readonly>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">主题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" id="title" value="" placeholder="关于代班"
                                        class="layui-input" lay-verType="tips"
                                       lay-verify="titleOfUsefulInformation">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">内容</label>
                            <div class="layui-input-block">
                                <textarea name="content" id="content" style="width: 1000px; height: 400px;"
                                          class="layui-textarea"
                                          lay-verType="tips" lay-verify="contentOfMessage"
                                          placeholder="我是张三。请问xx月xx日代班有无兴趣?老师超好事少，包奶茶。"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">附图片</label>
                            <div class="layui-input-inline uploadHeadImage">
                                <div class="layui-upload-drag" id="headImg">
                                    <i class="layui-icon"></i>
                                    <p>点击上传图片，或将图片拖拽到此处。</p>
                                </div>
                            </div>
                            <div class="layui-input-inline">
                                <div class="layui-upload-list">
                                    <img class="layui-upload-img headImage"
                                         id="demo1"
                                         height="110">
                                    <p id="demoText">当前配图↑</p>
                                </div>
                            </div>
                            <div><input type="text" id="hiddenIconUrl" style="display: none" name="hiddenIconUrl"
                                        value=""/></div>
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

        <#if replyTitle??  && replyTitle != "">
            $("#title").val('${replyTitle}');
        </#if>

        /***************************************************/
        //拖拽上传
        var uploadInst = upload.render({
            elem: '#headImg'
            , data: {
                id: function () {
                    return $("#id").val();
                }
            }
            , url: '${ctx}/user/message/uploadPicture'
            , size: 1024 //KB
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
                layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            }
            , done: function (res) {
                layer.closeAll('loading'); //关闭loading
                // 如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
                //打印后台传回的地址: 把地址放入一个隐藏的input中, 和表单一起提交到后台,
                // console.log(res.data.src);
                $("#hiddenIconUrl").val(res.data.src);
                $('#demoText').html('<span style="color: #8f8f8f;">上传成功!!!</span>');
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败!</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
        /*************************************************************/


        //提交
        form.on('submit(send)', function (obj) {
            var field = obj.field;

            //执行 Ajax 后重载
            $.ajax({
                type: 'post',
                data: {
                    hide: field.hideName
                    , userId: field.id
                    , messageTitle: field.title
                    , messageContent: field.content
                    , messagePicture: field.hiddenIconUrl
                },
                url: "${ctx}/user/message/insertSendMessage",
                beforeSend: function (data) {
                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                }
                , success: function (data) {
                    layer.closeAll('loading'); //关闭loading
                    if (data.data === "success") {
                        return layer.msg('发送成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${ctx}/user/message/page';
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

