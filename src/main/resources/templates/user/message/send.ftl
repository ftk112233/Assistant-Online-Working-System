<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发送消息-新东方优能中学助教工作平台</title>
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
                    <label class="layui-form-label">综合搜索</label>
                    <div class="layui-input-block">
                        <input type="text" name="commonSearch" placeholder="搜索你想发送消息的用户：姓名/用户名/工号/身份证/手机号/邮箱" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">校区</label>
                    <div class="layui-input-inline">
                        <select name="campus" id="campus" lay-search>
                            <option value="">请选择校区</option>
                        </select>
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
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload" lay-submit
                            lay-filter="LAY-app-contcomm-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                    <button class="layui-btn layuiadmin-btn-comm" data-type="reload"
                            id="clear">
                        清空
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div style="padding-bottom: 10px;">
                <button class="layui-btn layuiadmin-btn-list" data-type="batchdel">批量发送</button>
                <button class="layui-btn layuiadmin-btn-list" data-type="add">广播发送</button>
            </div>
            <table id="userTable" lay-filter="LAY-app-content-comm"></table>
            <script type="text/html" id="table-content-list1">
                <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="send">发送消息</a>
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

        //填充角色列表
        var roles = eval('(' + '${roles}' + ')');
        for (var i = 0; i < roles.length; i++) {
            var json = roles[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#userRole").append(str);
        }

        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }
        form.render('select');


        //方法级渲染
        table.render({
            elem: '#userTable'
            , url: '${ctx}/user/message/getUserSendTo' //向后端默认传page和limit
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                , layEvent: 'LAYTABLE_TIPS'
                , icon: 'layui-icon-tips'
            }]
            , cols: [[
                {type: 'checkbox', fixed: 'left', style: "height:70px;"}
                , {field: 'userWorkId', title: '工号', sort: true}
                , {field: 'userName', title: '用户名', sort: true, hide: true}
                , {field: 'userRealName', title: '姓名', width: 120, sort: true}
                , {field: 'campus', title: '校区', width: 100, sort: true}
                , {field: 'userRole', title: '角色', width: 80, sort: true}
                , {field: 'userIcon', title: '头像', width: 100, templet: '#imgTpl'}
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

        //监听搜索
        form.on('submit(LAY-app-contcomm-search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('userTable', {
                url: '${ctx}/user/message/getUserSendTo' //向后端默认传page和limit
                , where: { //设定异步数据接口的额外参数，任意设
                    commonSearch: field.commonSearch
                    , campus:field.campus
                    , userRole : field.userRole
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
                layer.confirm('确定要向' + checkData.length + '个用户发送消息吗？', function (index) {
                    document.write("<form action='${ctx}/user/message/many/sendForm' method='post' name='count_form' style='display:none'>");
                    document.write("<input type='hidden' name='users' value='"+JSON.stringify(checkData)+"'>");
                    document.write("</form>");
                    document.count_form.submit();
                });
            },
            add: function () {
                // console.log(JSON.stringify(checkData));
                // console.log(checkStatus);
                // console.log(checkData);
                layer.confirm('确定要发送全体消息吗？', function (index) {
                    location.href='${ctx}/user/message/all/sendForm';
                });
            }
        };

        $('.layui-btn.layuiadmin-btn-list').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        //监听工具条
        table.on('tool(LAY-app-content-comm)', function (obj) {
            var data = obj.data;
            if (obj.event === 'send') {
            <#--location.href = '${ctx}/studentAndClass/admin/page';-->
                //打开一个新页面
                var othis = $(this)
                        , href = '${ctx}/user/message/sendForm?id=' + data.id+'&userRole='+data.userRole+'&userRealName='+data.userRealName
                        , text = "发送消息"
                        , router = layui.router();


                var topLayui = parent === self ? layui : top.layui;
                topLayui.index.openTabsPage(href, text || othis.text());
            }
        });

    });
</script>
</body>
</html>