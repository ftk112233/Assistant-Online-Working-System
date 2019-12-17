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
                                <input name="classId" id="classId" lay-verify="classId" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="U6MCFC020001">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">黑魔法&nbsp; <i class="layui-icon layui-icon-tips"
                                                                          lay-tips="开启'黑魔法'将自动从数据库中通过查询上面班号来解析班级的教师、助教、上课教室、上课时间等信息，且查询的学员按进班时间排序。如果系统上名单信息与最新名单有出入，请关闭此选项。"></i></label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="magic" lay-skin="switch" lay-text="ON|OFF"
                                       lay-filter="magic" checked>
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-classroom" hidden="hidden">
                            <label class="layui-form-label">上课教室</label>
                            <div class="layui-input-inline">
                                <select name="classroom" id="classroom" lay-verType="tips"
                                        lay-search>
                                    <option value="">请选择教室</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">上课时间</label>
                            <div class="layui-input-inline">
                                <input name="classTime" lay-verify="classTime" lay-verType="tips"
                                       class="layui-input"
                                       placeholder="8:00-10:00">
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-name" hidden="hidden">
                            <label class="layui-form-label">教师姓名</label>
                            <div class="layui-input-inline">
                                <input name="teacherName" lay-verify="realNamePermNull" lay-verType="tips"
                                       class="layui-input"
                                       placeholder="魔仙女王">
                            </div>
                            <label class="layui-form-label">助教姓名</label>
                            <div class="layui-input-inline">
                                <input name="assistantName" lay-verify="realNamePermNull" lay-verType="tips"
                                       class="layui-input"
                                       placeholder="游乐王子">
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-classroom">
                            <label class="layui-form-label">任课教师要求</label>
                            <div class="layui-input-inline">
                                <textarea name="classTeacherRequirement" style="width: 400px; height: 100px;" class="layui-textarea"
                                          lay-verType="tips" lay-verify="classTeacherRequirement"
                                          placeholder="带好笔记本和魔法棒"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item" id="div-button-upload" hidden="hidden">
                            <label class="layui-form-label">上传&nbsp;<i class="layui-icon layui-icon-tips"
                                                                    lay-tips="上传学生花名册要求说明：<br>
                                                                1、第1行所有列名属性中必须有以下列，其列名称必须与要求相符（如下所示）！！<br>
                                                                    ====班级编码 | 班级名称 | 学员编号 | 姓名 | 手机==== <br>
                                                                2、名单中的学生应该是按进班时间排序的，这样在输出座位表时系统将会按表格中行的顺序填充座位"></i></label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-filter="upload" id="upload_student_list">上传名单
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">使用巴啦啦能量做表</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="downloadWithoutSeatTable"
                                        style="background-color: #1E9FFF"
                                        id="my_button_download_wost">输出多件套(不含座位表)
                                </button>
                                <button class="layui-btn" lay-submit lay-filter="downloadWithSeatTable"
                                        style="background-color: #01AAED" id="my_button_download_wst">输出座位表
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
                                    <select name="campus2" id="campus2" lay-filter="campus2" lay-verType="tips"
                                            lay-verify="required" lay-search>
                                        <option value="">请选择校区</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">上课教室</label>
                            <div class="layui-input-inline">
                                <select name="classroom2" id="classroom2" lay-verType="tips" lay-verify="required"
                                        lay-search>
                                    <option value="">请选择教室</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item" id="div-button-upload">
                            <label class="layui-form-label">上传&nbsp;<i class="layui-icon layui-icon-tips"
                                                                     lay-tips="上传学生名单要求说明：<br>
                                                                1、第1行所有列名属性中必须有名叫“学员姓名”的列，系统将按行的顺序填充座位表。"></i>
                            </label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-filter="upload_student_list_for_seat_table" style="background-color: #FFB800" id="upload_student_list_for_seat_table">上传学生名单
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
    }).use(['index', 'set', 'element', 'autocomplete'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload
                , autocomplete = layui.autocomplete;

        //=======================第一区域===========================//
        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }

        layui.link('${ctx}/custom/css/autocomplete.css');
        autocomplete.render({
            elem: $('#classId')[0],
            cache: true,
            url: '${ctx}/class/getClassesLikeClassId',
            response: {code: 'code', data: 'data'},
            template_val: '{{d.classId}}',
            template_txt: '{{d.classId}} <span class=\'layui-badge layui-bg-gray\'>{{d.classGeneralName}}</span>',
            onselect: function (resp) {

            }
        });

        $("#campus").val('${classCampus!""}');
        $("#classId").val('${classId!""}');

        form.render();


        //联动监听select
        form.on('select(campus)', function (data) {
            $("#classroom").empty();
            $("#classroom").append('<option value="">请选择教室</option>');
            var campus_name = $(this).attr("lay-value");
            $.ajax({
                type: "get",
                data: {campusName: campus_name},
                url: "${ctx}/class/listClassroomsByCampus",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var json = data[i];
                        $("#classroom").append('<option value="' + json + '">' + json + '</option>');
                    }
                    form.render('select');
                }
            });
        });

        //监听黑魔法开关
        form.on('switch(magic)', function (data) {
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
            elem: '#upload_student_list'
            , url: '${ctx}/toolbox/assistant/uploadStudentList'
            , data: {
            }
            , accept: 'file' //普通文件
            , exts: 'xls|xlsx' //允许上传的文件后缀
            , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(1, {shade: [0.1, '#fff']}); //上传loading
            }
            , done: function (res) {//返回值接收
                layer.closeAll('loading'); //关闭loading
                if (res.msg === "success") {
                    return layer.msg('上传成功', {
                        icon: 1
                        , time: 1000
                    });
                } else if (res.msg === "excelColumnNotFound") {
                    return layer.alert('表格中有列属性名不符合规范!', {
                        skin: 'layui-layer-lan'
                        ,closeBtn: 0
                    });
                }else {
                    return layer.msg('上传失败', {
                        offset: '15px'
                        , icon: 2
                        , time: 2000
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.msg('上传失败', {
                    offset: '15px'
                    , icon: 2
                    , time: 2000
                });
            }
        });

        form.on('submit(downloadWithSeatTable)', function (obj) {
            var field = obj.field;
            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            location.href='${ctx}/toolbox/assistant/exportAssistantTutorialAndSeatTable?magic='+field.magic + '&classId='+field.classId+'&classCampus='+field.campus
                    +'&classroom='+ field.classroom
            ;
            layer.closeAll('loading'); //关闭loading
        });


        //提交
        form.on('submit(downloadWithoutSeatTable)', function (obj) {
            var field = obj.field;
            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            location.href='${ctx}/toolbox/assistant/exportAssistantTutorialWithoutSeatTable?magic='+field.magic+'&classCampus='+field.campus
                + '&classId='+field.classId+'&classroom='+ field.classroom+'&classTime='+field.classTime+'&teacherName='+field.teacherName
                +'&assistantName='+field.assistantName+'&classTeacherRequirement='+field.classTeacherRequirement;

            layer.closeAll('loading'); //关闭loading
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
                url: "${ctx}/class/listClassroomsByCampus",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var json = data[i];
                        $("#classroom2").append('<option value="' + json + '">' + json + '</option>');
                    }
                    form.render('select');
                }
            });
        });

        upload.render({
            elem: '#upload_student_list_for_seat_table'
            , url: '${ctx}/toolbox/assistant/uploadStudentListForSeatTable'
            , data: {
            }
            , accept: 'file' //普通文件
            , exts: 'xls|xlsx' //允许上传的文件后缀
            , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(1, {shade: [0.1, '#fff']}); //上传loading
            }
            , done: function (res) {//返回值接收
                layer.closeAll('loading'); //关闭loading
                if (res.msg === "success") {
                    return layer.msg('上传成功', {
                        icon: 1
                        , time: 1000
                    });
                } else {
                    return layer.msg('上传失败', {
                        offset: '15px'
                        , icon: 2
                        , time: 2000
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.msg('上传失败', {
                    offset: '15px'
                    , icon: 2
                    , time: 2000
                });
            }
        });


        form.on('submit(downloadSeatTable)', function (obj) {
            var field = obj.field;
            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            location.href='${ctx}/toolbox/assistant/exportSeatTable?classCampus='+field.campus2
                    +'&classroom='+ field.classroom2
            ;

            layer.closeAll('loading'); //关闭loading
        });
    });
</script>
</body>
</html>

