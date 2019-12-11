<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>助教管理-新东方优能中学助教工作平台</title>
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
        <label class="layui-form-label">班级编码</label>
        <div class="layui-input-inline">
            <input type="text" name="id" value="" style="display:none;" class="layui-input">
            <input type="text" name="classId" id="classId" value="" class="layui-input" lay-verify="classId" lay-verType="tips"
                   placeholder="请输入">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
        <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #1E9FFF"
                id="parse-classId">解析班号
        </button>
        <i class="layui-icon layui-icon-tips" lay-tips="解析班号：2019年新东方优能中学部的班级编码为12位，如U6MCFB020001，其中U表示优能中学部，6表示六年级，M学科，C为班型：志高，F为班级规模：25人，B表示季度，02表示曹杨校区，'0001'为序号。以下标注绿色'可解析'的字段都可点击此按钮根据班号解析填充。"></i>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">班级名称</label>
        <div class="layui-input-inline">
            <input type="text" name="className" value="" class="layui-input" lay-verType="tips" lay-verify="className"
                   placeholder="请输入">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年份</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year">
        </div>
        <label class="layui-form-label">季度</label>
        <div class="layui-input-inline">
            <select name="season" id="season">
                <option value="">请选择季度</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">分期</label>
        <div class="layui-input-inline">
            <select name="subSeason" id="subSeason">
                <option value="">请选择分期</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">曹杨</label>
        <div class="layui-input-inline">
            <select name="campus" id="campus" lay-filter="campus" lay-search>
                <option value="">请选择校区</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">教室</label>
        <div class="layui-input-inline">
            <select name="classroom" id="classroom"
                    lay-search>
                <option value="">请选择教室</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年级</label>
        <div class="layui-input-inline">
            <select name="grade" id="grade">
                <option value="">请选择年级</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">学科</label>
        <div class="layui-input-inline">
            <select name="subject" id="subject">
                <option value="">请选择学科</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">班型</label>
        <div class="layui-input-inline">
            <select name="type" id="type">
                <option value="">请选择班型</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上课时间</label>
        <div class="layui-input-inline">
            <input type="text" name="classTime" lay-verType="tips" lay-verify="classTime" placeholder="周六8:00-10:00" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">上课次数</label>
        <div class="layui-input-inline">
            <input type="text" name="classTimes"  lay-verType="tips" lay-verify="classTimes" placeholder="16" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上课教师</label>
        <div class="layui-input-inline">
            <input type="text" name="teacherName" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">助教</label>
        <div class="layui-input-inline">
            <input type="text" name="assistantName" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">任课老师要求</label>
        <div class="layui-input-inline">
            <textarea name="classTeacherRequirement" style="width: 400px; height: 100px;" class="layui-textarea"
                      lay-verType="tips" lay-verify="classTeacherRequirement" placeholder="请输入"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <textarea name="remark" style="width: 400px; height: 150px;" class="layui-textarea"
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
    }).use(['index', 'user', 'laydate'], function () {
        var $ = layui.$
                , form = layui.form
                , laydate = layui.laydate;

        laydate.render({
            elem: '#year'
            , type: 'year'
        });

        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }
        $("#campus").val('${classEdit.classCampus!""}');

        var seasons = eval('(' + '${seasons}' + ')');
        for (var i = 0; i < seasons.length; i++) {
            var json = seasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#season").append(str);
        }
        $("#season").val('${classEdit.classSeason!""}');


        var subSeasons = eval('(' + '${subSeasons}' + ')');
        for (var i = 0; i < subSeasons.length; i++) {
            var json = subSeasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subSeason").append(str);
        }
        $("#subSeason").val('${classEdit.classSubSeason!""}');

        var grades = eval('(' + '${grades}' + ')');
        for (var i = 0; i < grades.length; i++) {
            var json = grades[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#grade").append(str);
        }
        $("#grade").val('${classEdit.classGrade!""}');


        var subjects = eval('(' + '${subjects}' + ')');
        for (var i = 0; i < subjects.length; i++) {
            var json = subjects[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subject").append(str);
        }
        $("#subject").val('${classEdit.classSubject!""}');

        var types = eval('(' + '${types}' + ')');
        for (var i = 0; i < types.length; i++) {
            var json = types[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#type").append(str);
        }
        $("#type").val('${classEdit.classType!""}');

        $.ajax({
            type: "get",
            data: {campusName: '${classEdit.classCampus!""}'},
            url: "${ctx}/class/listClassroomsByCampus",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var json = data[i];
                    $("#classroom").append('<option value="' + json + '">' + json + '</option>');
                }
                $("#classroom").val('${classEdit.classroom!""}');
                form.render('select');
            }
        });

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

        //解析班级编码
        $("#parse-classId").click(function () {
            $.ajax({
                type: "get",
                data: {classId: $("#classId").val()},
                url: "${ctx}/class/getParsedClassByParsingClassId",
                success: function (data) {
                    if (data.classCampus !==''){
                        $("#campus").val(data.classCampus);


                        $("#classroom").empty();
                        $("#classroom").append('<option value="">请选择教室</option>');
                        var campus_name = data.classCampus;
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
                    }
                    if (data.classSeason !==''){
                        $("#season").val(data.classSeason);
                    }
                    if (data.classGrade !==''){
                        $("#grade").val(data.classGrade);
                    }
                    if (data.classSubject !==''){
                        $("#subject").val(data.classSubject);
                    }
                    if (data.classType !==''){
                        $("#type").val(data.classType);
                    }

                    form.render();
                }
            });
        });
    });
</script>
</body>
</html>