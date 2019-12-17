<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>补课单制作</title>
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
                <div class="layui-card-header">制作补课单</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <#--<div class="layui-form-item">-->
                            <#--<label class="layui-form-label">学员号</label>-->
                            <#--<div class="layui-input-inline">-->
                                <#--<input name="studentId" id="studentId"-->
                                       <#--autocomplete="off" class="layui-input"-->
                                       <#--placeholder="SH01001">-->
                            <#--</div>-->
                        <#--</div>-->
                        <div class="layui-form-item">
                            <label class="layui-form-label">学员姓名</label>
                            <div class="layui-input-inline">
                                <input name="studentName" id="studentName" lay-verify="realName" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="魔仙小蓝">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">学员号</label>
                            <div class="layui-input-inline">
                                <input name="studentId" id="studentId" lay-verify="missLessonStudentId" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="SH2179540">
                            </div>
                            <label class="layui-form-label">学员电话</label>
                            <div class="layui-input-inline">
                                <input name="studentPhone" id="studentPhone"
                                       autocomplete="off" class="layui-input"
                                       placeholder="10086" lay-verify="studentPhone" lay-verType="tips">
                            </div>
                        </div>
                        <#--<div class="layui-form-item">-->
                            <#--<label class="layui-form-label">学员电话</label>-->
                            <#--<div class="layui-input-inline">-->
                                <#--<input name="studentPhone" id="studentPhone"-->
                                       <#--autocomplete="off" class="layui-input"-->
                                       <#--placeholder="10086">-->
                            <#--</div>-->
                        <#--</div>-->
                        <div class="layui-form-item">
                            <label class="layui-form-label">原班校区</label>
                            <div class="layui-input-inline">
                                <select name="originalCampus" id="originalCampus" lay-filter="originalCampus" lay-verType="tips"
                                        lay-verify="required" lay-search>
                                    <option value="">请选择校区</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">原班号</label>
                            <div class="layui-input-inline">
                                    <input name="originalClassId" id="originalClassId" lay-verify="classId" lay-verType="tips"
                                           autocomplete="off" class="layui-input"
                                           placeholder="U6MCFC020001">
                            </div>
                            <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #1E9FFF"
                                    id="preview-class">预览班级信息
                            </button>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">补课班校区</label>
                            <div class="layui-input-inline">
                                <select name="currentCampus" id="currentCampus" lay-filter="currentCampus" lay-verType="tips"
                                        lay-verify="required" lay-search>
                                    <option value="">请选择校区</option>
                                </select>
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <label class="layui-form-label">补课班号</label>
                            <div class="layui-input-inline">
                                <input name="currentClassId" id="currentClassId" lay-verify="classId" lay-verType="tips"
                                       autocomplete="off" class="layui-input"
                                       placeholder="U6MCFC020001">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #1E9FFF"
                                        id="preview-class2">预览班级信息
                                </button>
                            </div>
                            <label class="layui-form-label">补课日期</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="yyyy-MM-dd" id="date" name="date" lay-verify="required" lay-verType="tips" autocomplete="off">
                            </div>
                            <div class="layui-form-mid " style="color:red">*必填项</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">自动同步&nbsp; <i class="layui-icon layui-icon-tips"
                                                                          lay-tips="开启'自动同步'后开的补课单将录入后台数据库，补课学生的原班助教和补课班级助教都将能看到该学生的补课信息！"></i></label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="sync" lay-skin="switch" lay-text="ON|OFF"
                                       lay-filter="sync" checked>
                            </div>
                            <label class="layui-form-label">邮件提醒&nbsp; <i class="layui-icon layui-icon-tips"
                                                                          lay-tips="开启'邮件提醒'后补课信息将以邮件形式发送到原班助教和补课班助教邮箱，如果助教的账号绑定了邮箱的话！"></i></label>
                            <div class="layui-input-inline">
                                <input type="checkbox" name="emailTip" lay-skin="switch" lay-text="ON|OFF"
                                       lay-filter="emailTip" checked>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">使用巴啦啦能量做表</label>
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="download"
                                         id="my_button_download_mt">输出补课单
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

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script src="${ctx}/custom/js/myLayVerify.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'set', 'element','laydate', 'autocomplete'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload
                , laydate = layui.laydate
                , autocomplete = layui.autocomplete;

        layui.link('${ctx}/custom/css/autocomplete.css');
        autocomplete.render({
            elem: $('#originalClassId')[0],
            cache: true,
            url: '${ctx}/class/getClassesLikeClassId',
            response: {code: 'code', data: 'data'},
            template_val: '{{d.classId}}',
            template_txt: '{{d.classId}} <span class=\'layui-badge layui-bg-gray\'>{{d.classGeneralName}}</span>',
            onselect: function (resp) {

            }
        });
        autocomplete.render({
            elem: $('#currentClassId')[0],
            cache: true,
            url: '${ctx}/class/getClassesLikeClassId',
            response: {code: 'code', data: 'data'},
            template_val: '{{d.classId}}',
            template_txt: '{{d.classId}} <span class=\'layui-badge layui-bg-gray\'>{{d.classGeneralName}}</span>',
            onselect: function (resp) {

            }
        });

        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#originalCampus").append(str);
            $("#currentCampus").append(str);
        }

        //联动监听select
        form.on('select(originalCampus)', function (data) {
            var campus_name = $(this).attr("lay-value");
            $("#currentCampus").val(campus_name);
            form.render('select');
        });

        laydate.render({
            elem: '#date' //指定元素
        });

        $("#originalCampus").val('${studentAndClass.classCampus!""}');
        $("#currentCampus").val('${studentAndClass.classCampus!""}');
        $("#studentId").val('${studentAndClass.studentId!""}');
        $("#studentName").val('${studentAndClass.studentName!""}');
        $("#studentPhone").val('${studentAndClass.studentPhone!""}');
        $("#originalClassId").val('${studentAndClass.classId!""}');

        form.render();


        //解析班级编码
        $("#preview-class").click(function () {
            var othis = $(this)
                    , href = '${ctx}/class/admin/getPreviewClassInfo?classId=' + $("#originalClassId").val()
                    , text = "预览班级信息"
                    , router = layui.router();


            var topLayui = parent === self ? layui : top.layui;
            topLayui.index.openTabsPage(href, text || othis.text());
        });

        $("#preview-class2").click(function () {
            var othis = $(this)
                    , href = '${ctx}/class/admin/getPreviewClassInfo?classId=' + $("#currentClassId").val()
                    , text = "预览班级信息"
                    , router = layui.router();


            var topLayui = parent === self ? layui : top.layui;
            topLayui.index.openTabsPage(href, text || othis.text());
        });



        //提交
        form.on('submit(download)', function (obj) {
            var field = obj.field;

            layer.load(1, {shade: [0.1, '#fff']}); //上传loading

            location.href='${ctx}/toolbox/assistant/exportAssistantMissLessonTable?sync='+field.sync +'&emailTip='+field.emailTip
                    + '&studentId='+field.studentId+ '&studentPhone='+field.studentPhone+ '&studentName='+field.studentName+'&originalCampus='+field.originalCampus
                    +'&currentCampus='+ field.currentCampus+'&originalClassId='+ field.originalClassId+'&currentClassId='+ field.currentClassId+'&date='+ field.date;
            layer.closeAll('loading'); //关闭loading

        });
        //=========================================================//
    });
</script>
</body>
</html>

