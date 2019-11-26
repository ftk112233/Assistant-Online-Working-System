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
                <div class="layui-card-header">制作开班电话表多件套</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">校区</label>
                            <div class="layui-input-inline">
                                <select name="campus" id="campus" lay-filter="campus" lay-verType="tips"
                                        lay-verify="required" lay-search>
                                    <option value="">请选择校区</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">班级编码</label>
                            <div class="layui-input-inline">
                                <input name="classId" lay-verify="classId" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="U6MCFC020001">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">在线解析&nbsp; <i class="layui-icon layui-icon-tips"
                                                                          lay-tips="开启'在线解析'将自动从数据库中通过查询上面班号来解析班级的教师、助教、上课教室、上课时间等信息，且查询的学员按进班时间排序。如果系统上名单信息与最新名单有出入，请关闭此选项。"></i></label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="parseClassId" lay-skin="switch" lay-text="ON|OFF"
                                       lay-filter="parseClassId" checked>
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-classroom" hidden="hidden">
                            <label class="layui-form-label">上课教室</label>
                            <div class="layui-input-inline">
                                <select name="classroom" id="classroom" lay-verType="tips" lay-verify="required"
                                        lay-search>
                                    <option value="">请选择教室</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">上课时间</label>
                            <div class="layui-input-inline">
                                <input name="assistantName" lay-verify="classTime" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="8:00-10:00">
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-name" hidden="hidden">
                            <label class="layui-form-label">教师姓名</label>
                            <div class="layui-input-inline">
                                <input name="teacherName" lay-verify="realNamePermNull" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="魔仙女王">
                            </div>
                            <label class="layui-form-label">助教姓名</label>
                            <div class="layui-input-inline">
                                <input name="assistantName" lay-verify="realNamePermNull" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="游乐王子">
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-classroom">
                            <label class="layui-form-label">任课教师要求</label>
                            <div class="layui-input-inline">
                                <textarea name="remark" style="width: 400px; height: 100px;" class="layui-textarea"
                                          lay-verType="tips" lay-verify="teacherRequirement"
                                          placeholder="带好笔记本和魔法棒"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-button-upload" hidden="hidden">
                            <label class="layui-form-label">上传&nbsp;<i class="layui-icon layui-icon-tips"
                                                                    lay-tips="开启'自动解析'将自动从数据库中通过查询上面班号来解析班级的教师、助教、上课教室、上课时间等信息，且查询的学员按进班时间排序。如果系统上名单信息与最新名单有出入，请关闭此选项。"></i></label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-filter="upload" id="my_button_upload">上传花名册
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">使用巴啦啦能量做表</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="downloadWithSeatTable"
                                        style="background-color: #1E9FFF" id="my_button_download_wst">输出多件套(含座位表)
                                </button>
                                <button class="layui-btn" lay-submit lay-filter="downloadWithoutSeatTable"
                                        style="background-color: #01AAED"
                                        id="my_button_download_wost">输出多件套(不含座位表)
                                </button>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="layui-card">
                <div class="layui-card-header">制作座位表</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">校区</label>
                            <div class="layui-input-inline">
                                <div class="layui-input-inline">
                                    <select name="campus" id="campus2" lay-filter="campus2" lay-verType="tips"
                                            lay-verify="required" lay-search>
                                        <option value="">请选择校区</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">上课教室</label>
                            <div class="layui-input-inline">
                                <select name="classroom" id="classroom2" lay-verType="tips" lay-verify="required"
                                        lay-search>
                                    <option value="">请选择教室</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item" id="div-button-upload">
                            <label class="layui-form-label">上传&nbsp;<i class="layui-icon layui-icon-tips"
                                                                     lay-tips="开启'自动解析'将自动从数据库中通过查询上面班号来解析班级的教师、助教、上课教室、上课时间等信息，且查询的学员按进班时间排序。如果系统上名单信息与最新名单有出入，请关闭此选项。"></i>
                            </label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-filter="upload2" style="background-color: #FFB800" id="my_button_upload2">上传学生名单
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">使用巴啦啦能量做表</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="downloadSeatTable"
                                        id="my_button_download_st">输出座位表
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
    }).use(['index', 'set', 'element'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload;

        //=======================第一区域===========================//
        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }
        form.render('select');


        //监听自动解析开关
        form.on('switch(parseClassId)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-classroom").hide();
                $("#classroom").attr('lay-verify', '');
                $("#div-name").hide();
                $("#div-button-upload").hide();
            } else {
                $("#div-classroom").show();
                $("#classroom").attr('lay-verify', 'required');
                $("#div-name").show();
                $("#div-button-upload").show();
            }
        });

        upload.render({
            elem: '#my_button_upload'
            , url: '${ctx}/user/admin/import'
            , data: {
                //上传用户和助教
                type: 2
            }
            , accept: 'file' //普通文件
            , exts: 'xls|xlsx' //允许上传的文件后缀
            , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(1, {shade: [0.1, '#fff']}); //上传loading
            }
            , done: function (res) {//返回值接收
                layer.closeAll('loading'); //关闭loading
                if (res.msg === "success") {
                    return layer.msg('导入成功', {
                        icon: 1
                        , time: 1000
                    });
                } else {
                    return layer.msg('导入失败', {
                        offset: '15px'
                        , icon: 2
                        , time: 2000
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.msg('导入失败', {
                    offset: '15px'
                    , icon: 2
                    , time: 2000
                });
            }
        });

        //提交
        form.on('submit(downloadWithoutSeatTable)', function (obj) {
            var field = obj.field;


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
        //=========================================================//

        //=======================第二区域===========================//
        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus2").append(str);
        }
        form.render('select');

        //联动监听select
        form.on('select(campus2)', function (data) {
            $("#classroom2").empty();
            $("#classroom2").append('<option value="">请选择教室</option>');
            var campus_name = $(this).attr("lay-value");
            $.ajax({
                type: "get",
                data: {campusName: campus_name},
                url: "${ctx}/class/getClassroomsByCampus",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var json = data[i];
                        $("#classroom2").append('<option value="' + json + '">' + json + '</option>');
                    }
                    form.render('select');
                }
            });
        });
    });
</script>
</body>
</html>

