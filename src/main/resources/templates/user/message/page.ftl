<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>消息中心-新东方优能中学助教工作平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>
<div class="layui-fluid" id="LAY-app-message">
    <div class="layui-card">
        <div class="layui-tab layui-tab-brief">
            <ul class="layui-tab-title">
                <li class="layui-this">全部消息</li>
                <li>未读消息
                <#if countUnread != 0>
                    <span class="layui-badge">${countUnread}</span>
                </#if>
                </li>
            <#--<li>私信</li>-->
            </ul>
            <div class="layui-tab-content">

                <div class="layui-tab-item layui-show">
                    <div class="LAY-app-message-btns" style="margin-bottom: 10px;">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="del">删除
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="ready">
                            标记已读
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="readyAll">
                            全部已读
                        </button>
                    </div>

                    <table id="LAY-app-message-all" lay-filter="LAY-app-message-all"></table>
                </div>
                <div class="layui-tab-item">

                    <div class="LAY-app-message-btns" style="margin-bottom: 10px;">
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice" data-events="del">
                            删除
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice" data-events="ready">
                            标记已读
                        </button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="notice"
                                data-events="readyAll">全部已读
                        </button>
                    </div>

                    <table id="LAY-app-message-notice" lay-filter="LAY-app-message-notice"></table>
                </div>
            <#--<div class="layui-tab-item">-->

            <#--<div class="LAY-app-message-btns" style="margin-bottom: 10px;">-->
            <#--<button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="del">删除</button>-->
            <#--<button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="ready">标记已读</button>-->
            <#--<button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="readyAll">全部已读</button>-->
            <#--</div>-->

            <#--<table id="LAY-app-message-direct" lay-filter="LAY-app-message-direct"></table>-->
            <#--</div>-->
            </div>
        </div>
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

        //标题内容模板
        var tplTitle = function (d) {
            var dot = '';
            if (!d.read) {
                dot = '<span class="layui-badge-dot"></span>';
            }

            return '<a href="${ctx}/user/message/detail?id=' + d.id + '">' + d.messageTitle + '&nbsp;' + dot;
        };

        //全部消息
        table.render({
            elem: '#LAY-app-message-notice'
            , url: '${ctx}/user/message/getMessageUnreadInfo'
            , page: true
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'messageTitle', title: '标题', minWidth: 300, templet: tplTitle}
                , {field: 'userFrom', title: '发送人', width: 170}
                , {field: 'messageTime', title: '时间', width: 170}
            ]]
            , limit: 10
            , request: {
                pageName: 'pageNum',
                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
            }
            , skin: 'line'
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

        table.render({
            elem: '#LAY-app-message-all'
            , url: '${ctx}/user/message/getAllMessageInfo'
            , page: true
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'messageTitle', title: '标题', minWidth: 300, templet: tplTitle}
                , {field: 'userFrom', title: '发送人', width: 170}
                , {field: 'messageTime', title: '时间', width: 170}
            ]]
            , limit: 10
            , request: {
                pageName: 'pageNum',
                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
            }
            , skin: 'line'
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

        var DISABLED = 'layui-btn-disabled'
                //区分各选项卡中的表格
                , tabs = {
                    all: {
                        text: '全部消息'
                        , id: 'LAY-app-message-all'
                    }
                    , notice: {
                        text: '未读消息'
                        , id: 'LAY-app-message-notice'
                    }
                    , direct: {
                        text: '私信'
                        , id: 'LAY-app-message-direct'
                    }
                };
        //事件处理
        var events = {
            del: function (othis, type) {
                var thisTabs = tabs[type]
                        , checkStatus = table.checkStatus(thisTabs.id)
                        , data = checkStatus.data; //获得选中的数据
                if (data.length === 0) return layer.msg('未选中行');

                layer.confirm('确定删除选中的数据吗？', function () {
                    //执行 Ajax 后重载
                    $.ajax({
                        type: 'post',
                        data: {messages: JSON.stringify(data)},
                        url: "${ctx}/user/message/deleteMany",
                        beforeSend: function (data) {
                            layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                        }
                        , success: function (data) {
                            layer.closeAll('loading'); //关闭loading
                            if (data.data === "success") {
                                layer.msg('删除成功', {
                                    icon: 1
                                });
                                if (thisTabs.id === 'LAY-app-message-all') {
                                    table.reload(thisTabs.id, {
                                        url: '${ctx}/user/message/getAllMessageInfo' //向后端默认传page和limit); //重载表格
                                        , request: {
                                            pageName: 'pageNum',
                                            limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                        }
                                        , page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    });
                                } else  if (thisTabs.id === 'LAY-app-message-notice') {
                                    table.reload(thisTabs.id, {
                                        url: '${ctx}/user/message/getMessageUnreadInfo' //向后端默认传page和limit); //重载表格
                                        , request: {
                                            pageName: 'pageNum',
                                            limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                        }
                                        , page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    });
                                }
                            } else {
                                layer.msg('未知错误');
                            }
                        }

                    });
                });
            }
            , ready: function (othis, type) {
                var thisTabs = tabs[type]
                        , checkStatus = table.checkStatus(thisTabs.id)
                        , data = checkStatus.data; //获得选中的数据
                if (data.length === 0) return layer.msg('未选中行');

                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {messages: JSON.stringify(data)},
                    url: "${ctx}/user/message/readMany",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            layer.msg('标记已读成功', {
                                icon: 1
                            });
                            if (thisTabs.id === 'LAY-app-message-all') {
                                table.reload(thisTabs.id, {
                                    url: '${ctx}/user/message/getAllMessageInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else  if (thisTabs.id === 'LAY-app-message-notice') {
                                table.reload(thisTabs.id, {
                                    url: '${ctx}/user/message/getMessageUnreadInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            }
                        } else {
                            layer.msg('未知错误');
                        }
                    }

                });

            }
            , readyAll: function (othis, type) {
                var thisTabs = tabs[type];
                //执行 Ajax 后重载
                $.ajax({
                    type: 'post',
                    data: {},
                    url: "${ctx}/user/message/readAll",
                    beforeSend: function (data) {
                        layer.load(1, {shade: [0.1, '#fff']}); //上传loading
                    }
                    , success: function (data) {
                        layer.closeAll('loading'); //关闭loading
                        if (data.data === "success") {
                            layer.msg(thisTabs.text + '：全部已读', {
                                icon: 1
                            });
                            if (thisTabs.id === 'LAY-app-message-all') {
                                table.reload(thisTabs.id, {
                                    url: '${ctx}/user/message/getAllMessageInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else  if (thisTabs.id === 'LAY-app-message-notice') {
                                table.reload(thisTabs.id, {
                                    url: '${ctx}/user/message/getMessageUnreadInfo' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            }
                        } else {
                            layer.msg('未知错误');
                        }
                    }

                });
            }
        };

        $('.LAY-app-message-btns .layui-btn').on('click', function () {
            var othis = $(this)
                    , thisEvent = othis.data('events')
                    , type = othis.data('type');
            events[thisEvent] && events[thisEvent].call(this, othis, type);
        });

    });
</script>
</body>
</html>