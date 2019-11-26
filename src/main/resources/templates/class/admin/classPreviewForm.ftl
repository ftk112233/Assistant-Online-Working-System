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
            <input type="text" name="classId" id="classId" class="layui-input" lay-verify="classId" lay-verType="tips"
                   placeholder="请输入" value="${classPreview.classId!""}" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">班级名称</label>
        <div class="layui-input-inline">
            <input type="text" name="className" class="layui-input" lay-verType="tips" lay-verify="className"
                   placeholder="请输入" value="${classPreview.className!""}" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年份</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="yyyy" id="year" name="year" value="${classPreview.classYear!""}" readonly>
        </div>
        <label class="layui-form-label">季度</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" name="season" autocomplete="off" class="layui-input" value="${classPreview.classSeason!""}" readonly>
            </div>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">曹杨</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" name="campus" autocomplete="off" class="layui-input" value="${classPreview.classCampus!""}" readonly>
            </div>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">教室</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" name="classroom" autocomplete="off" class="layui-input" value="${classPreview.classroom!""}" readonly>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年级</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" name="grade" autocomplete="off" class="layui-input" value="${classPreview.classGrade!""}" readonly>
            </div>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">学科</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <div class="layui-input-inline">
                    <input type="text" name="subject" autocomplete="off" class="layui-input" value="${classPreview.classSubject!""}" readonly>
                </div>
            </div>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
        <label class="layui-form-label">班型</label>
        <div class="layui-input-inline">
            <div class="layui-input-inline">
                <input type="text" name="type" autocomplete="off" class="layui-input" value="${classPreview.classType!""}" readonly>
            </div>
        </div>
        <div class="layui-form-mid " style="color:#5FB878">*可解析</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上课时间</label>
        <div class="layui-input-inline">
            <input type="text" name="classTime" lay-verType="tips" lay-verify="classTime" autocomplete="off" class="layui-input" value="${classPreview.classTime!""}" readonly>
        </div>
        <label class="layui-form-label">上课次数</label>
        <div class="layui-input-inline">
            <input type="text" name="classTimes" lay-verType="tips" lay-verify="classTimes" autocomplete="off" class="layui-input" value="${classPreview.classTimes!""}" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上课教师</label>
        <div class="layui-input-inline">
            <input type="text" name="teacherName" autocomplete="off" class="layui-input" value="${classPreview.teacherName!""}" readonly>
        </div>
        <label class="layui-form-label">助教</label>
        <div class="layui-input-inline">
            <input type="text" name="assistantName" autocomplete="off" class="layui-input" value="${classPreview.assistantName!""}" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">任课老师要求</label>
        <div class="layui-input-inline">
            <textarea name="classTeacherRequirement" style="width: 400px; height: 100px;" class="layui-textarea"
                      lay-verType="tips" lay-verify="classTeacherRequirement" value="${classPreview.classTeacherRequirement!""}" readonly></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <textarea name="remark" style="width: 400px; height: 150px;" class="layui-textarea"
                      lay-verType="tips" lay-verify="remark" value="${classPreview.classRemark!""}" readonly></textarea>
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
    }).use(['index', 'user', 'laydate'], function () {
        var $ = layui.$
                , form = layui.form
                , laydate = layui.laydate;

    });
</script>
</body>
</html>