<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>常用信息管理-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="weabkit">
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
        <label class="layui-form-label">主题</label>
        <div class="layui-input-inline">
            <input type="text" name="id" id="id" value="" style="display:none;" class="layui-input">
            <textarea style="width: 400px; height: 100px;" class="layui-textarea"
                      name="title" lay-verType="tips" lay-verify="titleOfUsefulInformation"
                      placeholder="前台电话"></textarea>
            <div class="layui-form-mid " style="color:red">*必填项</div>

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-inline">
            <textarea style="width: 400px; height: 100px;" class="layui-textarea"
                      name="content" lay-verType="tips" lay-verify="contentOfUsefulInformation"
                      placeholder="10086"></textarea>
            <div class="layui-form-mid " style="color:red">*必填项</div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改配图</label>
        <div class="layui-input-inline uploadHeadImage">
            <div class="layui-upload-drag" id="headImg">
                <i class="layui-icon"></i>
                <p>点击上传图片，或将图片拖拽到此处。</p>
            </div>
        </div>
        <div class="layui-input-inline">
            <div class="layui-upload-list">
                <img class="layui-upload-img headImage"
                     src="${ctx}/usefulInformation/admin/getImage?image=${usefulInformationEdit.image!""}" id="demo1"
                     height="110">
                <p id="demoText">当前配图↑</p>
            </div>
        </div>
        <div><input type="text" id="hiddenIconUrl" style="display: none" name="hiddenIconUrl"
                    value=""/></div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属</label>
        <div class="layui-input-inline">
            <select name="belongTo" id="belongTo" lay-verType="tips" lay-verify="required" lay-filter="belongTo" lay-search>
                <option value="">请选择信息归属类别</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:red">*必填项&nbsp;
            <i class="layui-icon layui-icon-tips" style="color:green" lay-tips="选择所属类别可以为自己校区定制个性化的常用信息，信息将会在主页控制台中可查。注，'公共'指的是该类别的信息在主页不论选择什么类别，会始终被显示和选择的类别下的信息一起显示，即所有校区都可见，常用于定制全市统一的信息。"></i>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">序号</label>
        <div class="layui-input-inline">
            <input type="text" name="sequence" id="sequence" value="" class="layui-input" lay-verify="sequence" lay-verType="tips"
                   placeholder="请输入">
        </div>
        <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #1E9FFF"
                id="setRecommendedSequence">使用推荐值
        </button>
        <div class="layui-form-mid " style="color:red">*必填项&nbsp;
            <i class="layui-icon layui-icon-tips" style="color:green" lay-tips="序号的作用是标定了该条信息在其类别下显示的先后顺序，值越小显示越在前。因此可以通过更改该值来更改当前类别各条信息的显示先后顺序。"></i>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <textarea name="remark" style="width: 400px; height: 100px;" class="layui-textarea"
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
<script src="${ctx}/custom/js/user.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script src="${ctx}/custom/js/myLayVerify.js"></script>
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


        var allBelongTo = eval('(' + '${belongTo}' + ')');
        for (var i = 0; i < allBelongTo.length; i++) {
            var json = allBelongTo[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#belongTo").append(str);
        }
        $("#belongTo").val('${usefulInformationEdit.belongTo!""}');

        form.render('select');


        /***************************************************/
        //拖拽上传
        var uploadInst = upload.render({
            elem: '#headImg'
            , data: {
                id: function () {
                    return $("#id").val();
                }
            }
            , url: '${ctx}/usefulInformation/admin/updateUploadImage'
            , size: 2048 //KB
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
                $('#demoText').html('<span style="color: #8f8f8f;">上传成功!!!请点击确认修改即可完成最终更改!</span>');
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

        $("#setRecommendedSequence").click(function () {
            $.ajax({
                type: "get",
                data: {belongTo: $("#belongTo").val()},
                url: "${ctx}/usefulInformation/admin/getRecommendedSequence",
                success: function (data) {
                    $("#sequence").val(data.data);
                }
            });
        });
    });
</script>
</body>
</html>