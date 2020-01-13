<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理-新东方优能中学助教工作平台</title>
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
                    <label class="layui-form-label">工号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userWorkId" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">身份证</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userIdCard" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userName" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">真实姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userRealName" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">角色</label>
                    <div class="layui-input-inline">
                        <select name="userRole" id="userRole">
                            <option value="">请选择角色</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userEmail" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">联系方式</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userPhone" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">筛选排序</label>
                    <div class="layui-input-inline">
                        <select id="condition1" name="condition1" lay-filter="condition1">
                            <option value="">请选择要排序的类别</option>
                            <option value="userWorkId">按工号排序</option>
                            <option value="userIdCard">按身份证排序</option>
                            <option value="userRealName">按真实姓名排序</option>
                            <option value="userRole">按角色排序</option>
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
                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #FFB800"
                        id="import-user"><i class="layui-icon">&#xe67c;</i>仅导入用户
                </button>
                <i class="layui-icon layui-icon-tips" lay-tips="上传excel要求说明：<br>
                                                                1、第1行、第2行与导入无关。有效内容从第3行开始，第3行为列名属性：序号、部门、校区、姓名、员工号等......<br>
                                                                2、第3行所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                                                    ====部门、校区、姓名、员工号、身份证号、手机号码、备注====<br>
                                                                3、第四行开始是数据:<br>
                                                                    1)员工号必须唯一且准确<br>
                                                                    2)身份证号必须唯一且准确<br>
                                                                    3)手机号必须唯一且准确<br>
                                                                4、其他：<br>
                                                                    1)导入用户后，密码默认初始化为身份证，用以初次登陆<br>
                                                                                        "></i>
                &nbsp;
                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #01AAED"
                        id="import-user-and-assistant"><i class="layui-icon">&#xe67c;</i>导入用户和助教
                </button>
                <i class="layui-icon layui-icon-tips" lay-tips="上传excel要求说明：<br>
                                                                1、第1行、第2行与导入无关。有效内容从第3行开始，第3行为列名属性：序号、部门、校区、姓名、员工号等......<br>
                                                                2、第3行所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                                                    ====部门、校区、姓名、员工号、身份证号、手机号码、备注====<br>
                                                                3、第四行开始是数据:<br>
                                                                    1)员工号必须唯一且准确<br>
                                                                    2)身份证号必须唯一且准确<br>
                                                                    3)手机号必须唯一且准确<br>
                                                                    4)确保您当前要导入校区的助教信息表中没有姓名相同的助教，否则请联系系统管理员<br>
                                                                4、其他：<br>
                                                                    1)姓名导入助教表中时，对于重名助教会在重名的名字后面加'1'以区别<br>
                                                                    2)导入用户后，密码默认初始化为身份证，用以初次登陆<br>
                                                                                        "></i>
            </div>
            <table id="userTable" lay-filter="LAY-app-content-comm"></table>
            <script type="text/html" id="table-content-list1">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
        </div>
        <script type="text/html" id="imgTpl">
                <div style="cursor: pointer;" onclick="show_img(this)">
                    <img src="${ctx}/user/showIcon?userIcon={{ d.userIcon }}" style="height:50px;" id="icon">
                </div>

        </script>
    </div>
</div>

<!--layui.table图片显示不全，需重新定义CSS  -->
<style type="text/css">
    .layui-table-cell {
        height: auto !important;
        white-space: normal;
    }

</style>

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
                , admin = layui.admin
                , form = layui.form
                , table = layui.table
                , laypage = layui.laypage
                , laytpl = layui.laytpl
                , upload = layui.upload;

        show_img =function(t) {
            var t = $(t).find("img");
            //页面层
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: ['auto'],
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: '<div style="text-align:center"><img style="height:600px;" src="' + $(t).attr('src') + '" /></div>'
            });
        };

        upload.render({
            elem: '#import-user-and-assistant'
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
                    return layer.alert('导入成功！' + '<br>' +
                            '总耗时：' + res.excelSpeed.parsedTime + '<br>' +
                            '导入表格记录数：' + res.excelSpeed.count + '条；平均速度：' + res.excelSpeed.parsedSpeed + '。<br>' +
                            '变更数据库记录数：删除' + res.databaseSpeed.deleteCount + '条；插入' + res.databaseSpeed.insertCount
                            + '条；更新' + res.databaseSpeed.updateCount + '条；平均速度：' + res.databaseSpeed.parsedSpeed + '。', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    });
                }else if (res.msg === "tooManyRows") {
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


        upload.render({
            elem: '#import-user'
            , url: '${ctx}/user/admin/import'
            , data: {
                //仅上传用户
                type: 1
            }
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
                }else if (res.msg === "tooManyRows") {
                    return layer.alert('输入表格的行数过多。最大行数限制：' + res.rowCountThreshold + '，实际行数：' + res.actualRowCount + '。尝试删除最后多余的空白行？“ctrl+shift+↓”，“右键”，“删除”，“整行”', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "excelColumnNotFound") {
                    return layer.alert('未找到名称为"' + res.whatWrong + '"的列!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                }else {
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


        //填充角色列表
        var roles = eval('(' + '${roles}' + ')');
        for (var i = 0; i < roles.length; i++) {
            var json = roles[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#userRole").append(str);
        }
        form.render('select');

        //方法级渲染
        table.render({
            elem: '#userTable'
            , url: '${ctx}/user/admin/getUserInfo' //向后端默认传page和limit
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                , layEvent: 'LAYTABLE_TIPS'
                , icon: 'layui-icon-tips'
            }]
            , cols: [[
                {type: 'checkbox', fixed: 'left', style: "height:70px;"}
                , {field: 'id', title: 'id', sort: true, hide: true}
                , {field: 'createTime', title: '创建时间', sort: true, hide: true}
                , {field: 'updateTime', title: '更新时间', sort: true, hide: true}
                , {field: 'userWorkId', title: '工号', sort: true}
                , {field: 'userIdCard', title: '身份证', width: 180, sort: true}
                , {field: 'userName', title: '用户名', sort: true}
                , {field: 'userRealName', title: '真实姓名', width: 120, sort: true}
                , {field: 'userRole', title: '角色', width: 80, sort: true}
                , {field: 'userIcon', title: '头像', width: 100, templet: '#imgTpl'}
                , {field: 'userEmail', title: '邮箱'}
                , {field: 'userPhone', title: '联系方式', width: 120}
                , {field: 'userRemark', title: '备注', width: 150}
                , {title: '操作', minWidth: 150, align: 'center', toolbar: '#table-content-list1'}
            ]]
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

        //监听搜索
        form.on('submit(LAY-app-contcomm-search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('userTable', {
                url: '${ctx}/user/admin/getUserInfo' //向后端默认传page和limit
                , where: { //设定异步数据接口的额外参数，任意设
                    userWorkId: field.userWorkId
                    , userIdCard: field.userIdCard
                    , userName: field.userName
                    , userRealName: field.userRealName
                    , userRole: field.userRole
                    , userEmail: field.userEmail
                    , userPhone: field.userPhone
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
                        userWorkId: field.userWorkId
                        , userIdCard: field.userIdCard
                        , userName: field.userName
                        , userRealName: field.userRealName
                        , userRole: field.userRole
                        , userEmail: field.userEmail
                        , userPhone: field.userPhone
                    },
                    url: "${ctx}/user/admin/deleteByCondition",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            layer.msg('已删除');
                            table.reload('userTable', {
                                url: '${ctx}/user/admin/getUserInfo' //向后端默认传page和limit); //重载表格
                                , request: {
                                    pageName: 'pageNum',
                                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                }
                                , page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            });
                        } else if (data.data === "noPermissionsToDeleteYourself") {
                            return layer.msg('您不能删除自己!', {
                                offset: '15px'
                                , icon: 2
                                , time: 2000
                            });
                        } else if (data.data === "noPermissions") {
                            return layer.msg('对不起，您没有权限删除高级别用户!', {
                                offset: '15px'
                                , icon: 2
                                , time: 2000
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
                var checkStatus = table.checkStatus('userTable')
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
                        data: {users: JSON.stringify(checkData)},
                        url: "${ctx}/user/admin/deleteMany",
                        beforeSend: function (data) {
                            layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                        }
                        , success: function (data) {
                            layer.closeAll('loading'); //关闭loading
                            if (data.data === "success") {
                                layer.msg('已删除');
                                table.reload('userTable', {
                                    url: '${ctx}/user/admin/getUserInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else if (data.data === "noPermissionsToDeleteYourself") {
                                return layer.msg('您不能删除自己!', {
                                    offset: '15px'
                                    , icon: 2
                                    , time: 2000
                                });
                            } else if (data.data === "noPermissions") {
                                return layer.msg('对不起，您没有权限删除高级别用户!', {
                                    offset: '15px'
                                    , icon: 2
                                    , time: 2000
                                });
                            } else {
                                layer.msg('未知错误');
                            }
                        }

                    });

                });
            },
            add: function () {
                var index = layer.open({
                    type: 2
                    , title: '添加用户'
                    , content: '${ctx}/user/admin/insertForm'
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
                                userWorkId: field.workId
                                , userIdCard: field.idCard
                                , userName: field.userName
                                , userRealName: field.userRealName
                                , userRole: field.userRole
                                , userEmail: field.userEmail
                                , userPhone: field.userPhone
                                , userRemark: field.remark
                            };

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${ctx}/user/admin/insert",
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
                                    } else if (data.data === "workIdRepeat") {
                                        return layer.msg('对不起，该工号已存在！');
                                    } else if (data.data === "idCardRepeat") {
                                        return layer.msg('对不起，该身份证已存在！');
                                    } else if (data.data === "nameRepeat") {
                                        return layer.msg('对不起，该用户名已存在！');
                                    } else if (data.data === "emailRepeat") {
                                        return layer.msg('对不起，该邮箱已存在！');
                                    } else if (data.data === "phoneRepeat") {
                                        return layer.msg('对不起，该电话已存在！');
                                    } else {
                                        return layer.msg('未知错误');
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
                layer.confirm('确定删除此用户吗？', function (index) {
                    //提交删除ajax
                    $.ajax({
                        data: data,
                        type: 'post',
                        url: "${ctx}/user/admin/deleteOne",
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
                            } else if (data.data === "noPermissions") {
                                return layer.msg('对不起，您没有权限删除高级别用户!', {
                                    offset: '15px'
                                    , icon: 2
                                    , time: 2000
                                });
                            } else if (data.data === "noPermissionsToDeleteYourself") {
                                return layer.msg('您不能删除自己!', {
                                    offset: '15px'
                                    , icon: 2
                                    , time: 2000
                                });
                            } else {
                                return layer.msg('未知错误');
                            }
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                var index = layer.open({
                    type: 2
                    ,
                    title: '编辑用户'
                    ,
                    content: '${ctx}/user/admin/updateForm?id=' + data.id + '&userRole=' + data.userRole+'&userIcon=' + data.userIcon
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
                                , userWorkId: field.workId
                                , userIdCard: field.idCard
                                , userName: field.userName
                                , userRealName: field.userRealName
                                , userIcon :field.hiddenIconUrl
                                , userRole: field.userRole
                                , userEmail: field.userEmail
                                , userPhone: field.userPhone
                                , userRemark: field.remark
                            };

                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${ctx}/user/admin/updateById",
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
                                    } else if (data.data === "workIdRepeat") {
                                        return layer.msg('对不起，该工号已存在！');
                                    } else if (data.data === "idCardRepeat") {
                                        return layer.msg('对不起，该身份证已存在！');
                                    } else if (data.data === "nameRepeat") {
                                        return layer.msg('对不起，该用户名已存在！');
                                    } else if (data.data === "emailRepeat") {
                                        return layer.msg('对不起，该邮箱已存在！');
                                    } else if (data.data === "phoneRepeat") {
                                        return layer.msg('对不起，该电话已存在！');
                                    } else if (data.data === "unchanged") {
                                        return layer.msg('未做任何修改');
                                    }else {
                                        return layer.msg('未知错误');
                                    }
                                }
                            });

                        });

                        submit.trigger('click');
                    }
                    ,
                    success: function (layero, index) {
                        //给iframe元素赋值
                        var othis = layero.find('iframe').contents().find("#layuiadmin-app-form-list").click();
                        othis.find('input[name="id"]').val(data.id);
                        othis.find('input[name="workId"]').val(data.userWorkId);
                        othis.find('input[name="idCard"]').val(data.userIdCard);
                        othis.find('input[name="userName"]').val(data.userName);
                        othis.find('input[name="userRealName"]').val(data.userRealName);
                        othis.find('input[name="userEmail"]').val(data.userEmail);
                        othis.find('input[name="userPhone"]').val(data.userPhone);
                        //用户角色通过url传值给后端，后端存于model，如果通过iframe传值，无法在子页面启动数据库查询所有学院名称填充select的ajax
                        othis.find('textarea[name="remark"]').val(data.userRemark);
                    }
                });
                layer.full(index);
            }
        });

    });
</script>
</body>
</html>