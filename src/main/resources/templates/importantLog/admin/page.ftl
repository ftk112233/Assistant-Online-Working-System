<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>日志管理</title>
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
                <div class="layui-form-item">
                    <label class="layui-form-label">日志内容</label>
                    <div class="layui-input-block">
                        <input type="text" name="message" placeholder="关键字搜索日志内容" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">操作用户</label>
                    <div class="layui-input-block">
                        <input type="text" name="commonSearchUser" placeholder="综合搜索用户：姓名/用户名/工号/身份证/手机号/邮箱"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">日志级别</label>
                    <div class="layui-input-inline">
                        <select name="level" id="level">
                            <option value="">请选择级别</option>
                        </select>
                    </div>
                    <label class="layui-form-label">角色</label>
                    <div class="layui-input-inline">
                        <select name="userRole" id="userRole">
                            <option value="">请选择角色</option>
                        </select>
                    </div>
                    <label class="layui-form-label">ip地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="operatorIp" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">筛选排序</label>
                    <div class="layui-input-inline">
                        <select id="condition1" name="condition1" lay-filter="condition1">
                            <option value="">请选择要排序的类别</option>
                            <option value="level">按日志级别排序</option>
                            <option value="operatorId">按操作用户排序</option>
                            <option value="operatorIp">按ip地址排序</option>
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
            </div>
            <table id="logTable" lay-filter="LAY-app-content-comm"></table>
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
    }).use(['index', 'user', 'upload'], function () {
        var $ = layui.$
                , admin = layui.admin
                , form = layui.form
                , table = layui.table
                , laypage = layui.laypage
                , laytpl = layui.laytpl
                , upload = layui.upload;

        //填充角色列表
        var roles = eval('(' + '${roles}' + ')');
        for (var i = 0; i < roles.length; i++) {
            var json = roles[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#userRole").append(str);
        }

        var levels = eval('(' + '${levels}' + ')');
        for (var i = 0; i < levels.length; i++) {
            var json = levels[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#level").append(str);
        }

        form.render('select');

        //方法级渲染
        table.render({
            elem: '#logTable'
            , url: '${ctx}/importantLog/admin/getImportantLogInfo' //向后端默认传page和limit
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                , layEvent: 'LAYTABLE_TIPS'
                , icon: 'layui-icon-tips'
            }]
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'id', sort: true, hide: true}
                , {field: 'createTime', title: '创建时间', width: 165, sort: true}
                , {field: 'updateTime', title: '更新时间', width: 165, sort: true, hide: true}
                , {field: 'message', title: '日志内容'}
                , {field: 'level', title: '级别', width: 80, sort: true}
                , {field: 'operatorId', title: '用户id', sort: true, hide: true}
                , {field: 'userName', title: '用户名', width: 200, sort: true}
                , {field: 'userRealName', title: '姓名', width: 120, sort: true}
                , {field: 'userRole', title: '角色', width: 80, sort: true}
                , {field: 'operatorIp', title: '客户端ip', width: 180, sort: true}
                , {field: 'remark', title: '备注', hide: true}

                // , {title: '操作', minWidth: 150, align: 'center', toolbar: '#table-content-list1'}
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
                // console.log(res);

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
            table.reload('logTable', {
                url: '${ctx}/importantLog/admin/getImportantLogInfo' //向后端默认传page和limit
                , where: { //设定异步数据接口的额外参数，任意设
                    message: field.message
                    , commonSearchUser: field.commonSearchUser
                    , level: field.level
                    , userRole: field.userRole
                    , operatorIp: field.operatorIp
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
            layer.confirm('确定要根据上述条件删除数据吗？', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {
                        message: field.message
                        , commonSearchUser: field.commonSearchUser
                        , level: field.level
                        , userRole: field.userRole
                        , operatorIp: field.operatorIp
                    },
                    url: "${ctx}/importantLog/admin/deleteByCondition",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            layer.msg('已删除');
                            table.reload('logTable', {
                                url: '${ctx}/importantLog/admin/getImportantLogInfo' //向后端默认传page和limit); //重载表格
                                , request: {
                                    pageName: 'pageNum',
                                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
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
                var checkStatus = table.checkStatus('logTable')
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
                        data: {allLog: JSON.stringify(checkData)},
                        url: "${ctx}/importantLog/admin/deleteMany",
                        beforeSend: function (data) {
                            layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                        }
                        , success: function (data) {
                            layer.closeAll('loading'); //关闭loading
                            if (data.data === "success") {
                                layer.msg('已删除');
                                table.reload('logTable', {
                                    url: '${ctx}/importantLog/admin/getImportantLogInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else {
                                layer.msg('未知错误');
                            }
                        }

                    });

                });
            }
        };

        $('.layui-btn.layuiadmin-btn-list').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>