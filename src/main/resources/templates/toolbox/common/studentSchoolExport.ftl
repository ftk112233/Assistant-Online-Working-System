<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学校统计</title>
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
                <div class="layui-card-header">学校统计</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">导出学校统计</h3>
                            <p>上传excel要求说明：<a
                                    href="${ctx}/toolbox/assistantAdministrator/downloadExample/7">查看范例</a></p>
                            <ul>
                                <li>系统只关心表中两列：学员编号、在读学校（这两列的名称必须一致！）。其中学员号不为空作为查询条件，在读学校为空作为填充结果。</li>
                                <li>您只需要上传空的学校统计表（在读学校一栏为空），系统将根据学员号查询其学校（如果系统中有的话）填充在读学校列。</li>
                                <li>注意："在读学校"列，你可以放置一些默认值复制下拉拷贝，如"111"。如果不赋值，也可以输出正确的学校，但是该列原先的字体边框等样式会丢失。</li>
                                <li>上传完成后，如果没有错误，会自动下载输出表格。</li>
                            </ul>
                        </div>
                        <div class="layui-timeline-content layui-text">
                            <div>
                                <button class="layui-btn" lay-submit lay-filter="upload-school"
                                        id="upload-school">上传
                                </button>
                            </div>
                        </div>
                        <br>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">分享学生学校统计</h3>
                            <p><em>大家做完开班工作后，可以将你们统计好的学生学校分享到网站上，下次导出表格时这些学生就都有学校， 不用再问家长啦。</em></p>
                            <p>上传excel要求说明：<a
                                    href="${ctx}/toolbox/assistantAdministrator/downloadExample/6">查看范例</a></p>
                            <ul>
                                <li>
                                    要读取的表格必须放在第一张sheet。第1行为列名属性。所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！（可以将你们做好的开班电话表中的列名做适当修改后上传）<br>
                                    __________________<br>
                                    | 学员编号 | 在读学校 |<br>
                                    __________________
                                </li>
                            </ul>
                        </div>
                        <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                            <input type="checkbox" name="read_step45" id="read_step45" lay-skin="primary"
                                   lay-filter="read_step45" title=""><b style="color: red;">我已认真阅读以上内容</b>
                        </div>
                        <div class="layui-timeline-content layui-text" id="div-import-student-school"
                             hidden="hidden">
                            <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel"
                                    style="background-color: #FFB800"
                                    id="import-student-school" lay-filter="import-student-school"><i
                                    class="layui-icon">&#xe67c;</i>导入学校统计
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
    }).use(['index', 'set', 'element', 'laydate', 'autocomplete'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload
                , laydate = layui.laydate
                , autocomplete = layui.autocomplete;


        upload.render({
            elem: '#upload-school'
            , url: '${ctx}/toolbox/common/uploadStudentSchool'
            , data: {
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
                    return layer.msg('上传成功，即将自动建立下载', {
                        icon: 1
                        , time: 1500
                    }, function () {
                        location.href = '${ctx}/toolbox/common/downloadStudentSchool';
                    });
                } else if (res.msg === "tooManyRows") {
                    return layer.alert('输入表格的行数过多。最大行数限制：' + res.rowCountThreshold + '，实际行数：' + res.actualRowCount + '。尝试删除最后多余的空白行？“ctrl+shift+↓”，“右键”，“删除”，“整行”', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "excelColumnNotFound") {
                    return layer.alert('未找到名称为"' + res.whatWrong + '"的列!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else {
                    return layer.alert('导入失败!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.alert('导入失败!', {
                    skin: 'layui-layer-lan'
                    , closeBtn: 0
                });
            }
        });
        //=========================================================//

        form.on('checkbox(read_step45)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-import-student-school").show();
            } else {
                $("#div-import-student-school").hide();
            }
        });

        upload.render({
            elem: '#import-student-school'
            , url: '${ctx}/student/admin/importSchool'
            , data: {}
            , accept: 'file' //普通文件
            , exts: 'xls|xlsx' //允许上传的文件后缀
            , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(1, {shade: [0.1, '#fff']}); //上传loading
            }
            , done: function (res) {//返回值接收
                layer.closeAll('loading'); //关闭loading
                if (res.msg === "success") {
                    return layer.alert('导入成功！' + '<br>' +
                            '总耗时：' + res.excelSpeed.parsedTime + '<br>' +
                            '导入表格记录数：' + res.excelSpeed.count + '条；平均速度：' + res.excelSpeed.parsedSpeed + '。<br>' +
                            '变更数据库记录数：删除' + res.databaseSpeed.deleteCount + '条；插入' + res.databaseSpeed.insertCount
                            + '条；更新' + res.databaseSpeed.updateCount + '条；平均速度：' + res.databaseSpeed.parsedSpeed + '。', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    });
                } else if (res.msg === "tooManyRows") {
                    return layer.alert('输入表格的行数过多。最大行数限制：' + res.rowCountThreshold + '，实际行数：' + res.actualRowCount + '。尝试删除最后多余的空白行？“ctrl+shift+↓”，“右键”，“删除”，“整行”', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "excelColumnNotFound") {
                    return layer.alert('未找到名称为"' + res.whatWrong + '"的列!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "invalidData") {
                    return layer.alert('需要引起注意！' + '<br>' +
                            '总耗时：' + res.excelSpeed.parsedTime + '<br>' +
                            '导入表格记录数：' + res.excelSpeed.count + '条；平均速度：' + res.excelSpeed.parsedSpeed + '。<br>' +
                            '变更数据库记录数：删除' + res.databaseSpeed.deleteCount + '条；插入' + res.databaseSpeed.insertCount
                            + '条；更新' + res.databaseSpeed.updateCount + '条；平均速度：' + res.databaseSpeed.parsedSpeed + '。' + '<br>' +
                            '不合法而未被更新的记录：' + res.invalidCount + '条。所在位置[(列名=值),...]：' + res.whatInvalid, {
                        skin: 'layui-layer-lan' //样式类名
                        , closeBtn: 0
                    });
                } else {
                    return layer.alert('导入失败!检查数据准确性？', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.alert('导入失败!', {
                    skin: 'layui-layer-lan'
                    , closeBtn: 0
                });
            }
        });

    });
</script>
</body>
</html>

