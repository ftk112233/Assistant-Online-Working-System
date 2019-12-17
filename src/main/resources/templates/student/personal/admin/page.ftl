<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生个人信息管理-新东方优能中学助教工作平台</title>
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
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item" id="form">
                <div class="layui-inline">
                    <label class="layui-form-label">学员号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" placeholder="请输入" name="studentId" autocomplete="off">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学员姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" placeholder="请输入" name="studentName" autocomplete="off">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <select name="sex" id="sex">
                            <option value="">请选择性别</option>
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">联系方式</label>
                    <div class="layui-input-inline">
                        <input type="text" name="studentPhone" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学校</label>
                    <div class="layui-input-inline">
                        <input type="text" name="school" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">筛选排序</label>
                    <div class="layui-input-inline">
                        <select id="condition1" name="condition1" lay-filter="condition1">
                            <option value="">请选择要排序的类别</option>
                            <option value="studentId">按学员号排序</option>
                            <option value="studentName">按学员姓名排序</option>
                            <option value="studentSchool">按学校排序</option>
                        </select>
                    </div>
                    <div class="layui-input-inline">
                        <select id="condition2" name="condition2" lay-filter="condition2">
                            <option value="">请选择要排序方式</option>
                            <option value="asc">从低到高</option>
                            <option value="desc">从高到低</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload" lay-submit id="my_button"
                            lay-filter="LAY-app-contcomm-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload"
                            id="clear">
                        清空
                    </button>
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload" lay-submit
                            lay-filter="deleteByCondition"
                            id="deleteByCondition" lay-tips="'条件删除'将批量删除根据前面的查询条件查询出的所有记录，使用前请先查询预览这些记录是否正确！">
                        条件删除
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div style="padding-bottom: 10px;">
                <button class="layui-btn layuiadmin-btn-list" data-type="batchdel">删除</button>
                <button class="layui-btn layuiadmin-btn-list" data-type="add">添加</button>
            </div>
            <table id="studentTable" lay-filter="LAY-app-content-comm"></table>
            <script type="text/html" id="table-content-list1">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
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
    }).use(['index', 'user', 'upload', 'laydate'], function () {
        var $ = layui.$
                , admin = layui.admin
                , form = layui.form
                , table = layui.table
                , laypage = layui.laypage
                , laytpl = layui.laytpl
                , upload = layui.upload
                , laydate = layui.laydate;


        //方法级渲染
        table.render({
            elem: '#studentTable'
            , url: '${ctx}/student/admin/getStudentInfo' //向后端默认传page和limit
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                , layEvent: 'LAYTABLE_TIPS'
                , icon: 'layui-icon-tips'
            }]
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'id', sort: true, hide: true}
                , {field: 'createTime', title: '创建时间', sort: true, hide: true}
                , {field: 'updateTime', title: '更新时间', sort: true, hide: true}
                , {field: 'studentId', title: '学员号', width: 180, sort: true}
                , {field: 'studentName', title: '学员姓名', width: 180, sort: true}
                , {field: 'studentSex', title: '性别', width: 80, sort: true}
                , {field: 'studentPhone', title: '手机', width: 120}
                , {field: 'studentPhoneBackup', title: '备用手机', width: 120}
                , {field: 'studentSchool', title: '学校', sort: true}
                , {field: 'studentRemark', title: '备注'}
                , {title: '操作', minWidth: 150, align: 'center', toolbar: '#table-content-list1'}
            ]]
            , where: {
            }
            , page: true
            , limit: 10
            , limits: [5, 10, 15, 20, 50]
            , request: {
                pageName: 'pageNum',
                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
            }
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);

                //得到当前页码
                //console.log(curr);

                //得到数据总量
                //console.log(count);
            }

        });

        $("#clear").click(function () {
            $("#form input").val("");
            $("#form select").val("");
        });

        $('#form input').on('keydown', function (event) {
            if (event.keyCode == 13) {
                $("#my_button").trigger("click");

                return false
            }
        });

        //监听查询我的班级
        form.on('submit(LAY-app-contcomm-search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('studentTable', {
                url: '${ctx}/student/admin/getStudentInfo' //向后端默认传page和limit
                , where: { //设定异步数据接口的额外参数，任意设
                    studentId : field.studentId
                    , studentName : field.studentName
                    , studentSex : field.sex
                    , studentPhone : field.studentPhone
                    , studentSchool : field.school
                    , condition1: field.condition1
                    , condition2: field.condition2
                }
                , request: {
                    pageName: 'pageNum',
                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });

        //监听搜索
        form.on('submit(deleteByCondition)', function (data) {
            var field = data.field;
            console.log(field)
            layer.confirm('确定要根据上述条件删除数据吗？', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {
                        studentId : field.studentId
                        , studentName : field.studentName
                        , studentSex : field.sex
                        , studentPhone : field.studentPhone
                        , studentSchool : field.school
                    },
                    url: "${ctx}/student/admin/deleteByCondition",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            layer.msg('已删除');
                            table.reload('studentTable', {
                                url: '${ctx}/student/admin/getStudentInfo' //向后端默认传page和limit); //重载表格
                                , request: {
                                    pageName: 'pageNum',
                                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                }
                                , where: {
                                }
                                , page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            });
                        } else {
                            layer.msg('无法完成操作');
                        }
                    }

                });

            });
        });

        var $ = layui.$, active = {
            batchdel: function () {
                var checkStatus = table.checkStatus('studentTable')
                        , checkData = checkStatus.data; //得到选中的数据

                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                // console.log(JSON.stringify(checkData));
                // console.log(checkStatus);
                // console.log(checkData);
                layer.confirm('确定要删除' + checkData.length + '条数据吗？', function (index) {
                    //执行 Ajax 后重载
                    $.ajax({
                        type: 'post',
                        data: {students: JSON.stringify(checkData)},
                        url: "${ctx}/student/admin/deleteMany",
                        beforeSend: function (data) {
                            layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                        }
                        , success: function (data) {
                            layer.closeAll('loading'); //关闭loading
                            if (data.data === "success") {
                                layer.msg('已删除');
                                table.reload('studentTable', {
                                    url: '${ctx}/student/admin/getStudentInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , where: {
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else {
                                return layer.msg('无法完成操作');
                            }
                        }

                    });

                });
            },
            add: function () {
                var index = layer.open({
                    type: 2
                    , title: '添加学生'
                    , content: '${ctx}/student/admin/updateForm'
                    , maxmin: true
                    , btn: ['确定', '取消']
                    , yes: function (index, layero) {
                        //点击确认触发 iframe 内容中的按钮提交
                        var iframeWindow = window['layui-layer-iframe' + index]
                                , submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");

                        iframeWindow.layui.form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            // var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            var json = {
                                studentId: field.studentId
                                , studentName: field.studentName
                                , studentSex: field.sex
                                , studentPhone: field.studentPhone
                                , studentPhoneBackup: field.studentPhoneBackup
                                , studentSchool: field.school
                                , studentRemark: field.remark
                            };

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${ctx}/student/admin/insert",
                                beforeSend: function (data) {
                                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                                }
                                , success: function (data) {
                                    layer.closeAll('loading'); //关闭loading
                                    if (data.data === "success") {
                                        layer.msg('添加成功', {
                                            icon: 1
                                            , time: 1000
                                        });

                                        layer.close(index); //再执行关闭
                                    }  else if (data.data === "studentIdRepeat") {
                                        return layer.msg('该学员号已存在！');
                                    } else {
                                        return layer.msg('无法完成操作');
                                    }
                                }
                            });

                        });
                        submit.trigger('click');

                    }
                });
                layer.full(index);
            }
        };

        $('.layui-btn.layuiadmin-btn-list').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        //监听工具条
        table.on('tool(LAY-app-content-comm)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('确定删除此学员记录吗？删除此学生将会删除学生的所有上课记录', function (index) {
                    //提交删除ajax
                    $.ajax({
                        data: data,
                        type: 'post',
                        url: "${ctx}/student/admin/deleteOne",
                        beforeSend: function (data) {
                            layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                        }
                        , success: function (data) {
                            layer.closeAll('loading'); //关闭loading
                            if (data.data === "success") {
                                layer.msg('删除成功', {
                                    icon: 1
                                    , time: 1000
                                });

                                obj.del();

                                layer.close(index); //关闭弹层
                            } else {
                                return layer.msg('无法完成操作');
                            }
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                var index = layer.open({
                    type: 2
                    ,
                    title: '编辑学生'
                    ,
                    content: '${ctx}/student/admin/updateForm?id=' + data.id
                    ,
                    maxmin: true
                    ,
                    btn: ['确定', '取消']
                    ,
                    yes: function (index, layero) {
                        var iframeWindow = window['layui-layer-iframe' + index]
                                , submit = layero.find('iframe').contents().find("#layuiadmin-app-form-edit");

                        //监听提交
                        iframeWindow.layui.form.on('submit(layuiadmin-app-form-edit)', function (data) {
                            var field = data.field; //获取提交的字段
                            var json = {
                                id: field.id
                                , studentId: field.studentId
                                , studentName: field.studentName
                                , studentSex: field.sex
                                , studentPhone: field.studentPhone
                                , studentPhoneBackup: field.studentPhoneBackup
                                , studentSchool: field.school
                                , studentRemark: field.remark
                            };


                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${ctx}/student/admin/updateById",
                                beforeSend: function (data) {
                                    layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                                }
                                , success: function (data) {
                                    layer.closeAll('loading'); //关闭loading
                                    if (data.data === "success") {
                                        layer.msg('修改成功', {
                                            icon: 1
                                            , time: 1000
                                        });

                                        obj.update(json); //数据更新

                                        form.render();

                                        layer.close(index); //关闭弹层
                                    } else if (data.data === "studentIdRepeat") {
                                        return layer.msg('该学员号已存在！');
                                    } else {
                                        return layer.msg('无法完成操作');
                                    }
                                }
                            });

                        });

                        submit.trigger('click');
                    }
                    ,
                    success: function (layero, index) {
                        console.log(data.assistantSex)
                        //给iframe元素赋值
                        var othis = layero.find('iframe').contents().find("#layuiadmin-app-form-list").click();
                        othis.find('input[name="id"]').val(data.id);
                        othis.find('input[name="studentId"]').val(data.studentId);
                        othis.find('input[name="studentName"]').val(data.studentName);
                        othis.find('input[name="sex"][value="男"]').attr("checked", data.studentSex === '男');
                        othis.find('input[name="sex"][value="女"]').attr("checked", data.studentSex === '女');
                        othis.find('input[name="studentPhone"]').val(data.studentPhone);
                        othis.find('input[name="studentPhoneBackup"]').val(data.studentPhoneBackup);
                        othis.find('input[name="school"]').val(data.studentSchool);
                        othis.find('textarea[name="remark"]').val(data.studentRemark);
                    }
                });
                layer.full(index);
            }
        });

    });
</script>
</body>
</html>