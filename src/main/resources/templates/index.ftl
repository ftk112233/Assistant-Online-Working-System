<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>AOWS-优能助教在线工作平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" type="image/x-icon" href="${ctx}/custom/img/favicon/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugins/layuiadmin/style/admin.css" media="all">

    <script>
        /^http(s*):\/\//.test(location.href) || alert('请先部署到 localhost 下再访问');
    </script>
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item layadmin-flexible" lay-unselect>
                    <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="http://blog.kurochan.top/" target="_blank" title="开发者博客">
                        <i class="layui-icon layui-icon-website"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search"
                           layadmin-event="serach" lay-action="">
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
                <li class="layui-nav-item" lay-unselect>
                    <a lay-href="${ctx}/user/message/page" layadmin-event="message" lay-text="我的消息">
                        <i class="layui-icon layui-icon-notice"></i>
                        <#if (countUnread>0) >
                        <!-- 如果有新消息，则显示小圆点 -->
                        <span class="layui-badge-dot"></span>
                        </#if>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="theme">
                        <i class="layui-icon layui-icon-theme"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="fullscreen">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;">
                        <img src="${ctx}/user/showIcon?userIcon=${userInfo.userIcon!""}" class="layui-nav-img">
                    ${userInfo.userRole}-${userInfo.userRealName}
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a lay-href="${ctx}/user/setInfo">基本资料</a></dd>
                        <dd><a lay-href="${ctx}/user/setPassword">修改密码</a></dd>
                        <dd><a lay-href="${ctx}/user/setEmail">修改绑定邮箱</a></dd>
                        <dd><a lay-href="${ctx}/user/setPhone">修改绑定手机</a></dd>
                        <dd><a lay-href="${ctx}/user/message/page">消息中心</a></dd>
                        <dd style="text-align: center;"><a href="${ctx}/logout">退出</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="about"><i
                            class="layui-icon layui-icon-more-vertical"></i></a>
                </li>
            </ul>
        </div>

        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo" lay-href="${ctx}/console">
                    <span>优能助教在线工作平台</span>
                </div>

                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu"
                    lay-filter="layadmin-system-side-menu">
                    <li data-name="home" class="layui-nav-item layui-nav-itemed">
                        <a href="javascript:;" lay-tips="主页" lay-direction="2">
                            <i class="layui-icon layui-icon-home"></i>
                            <cite>主页</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd data-name="console" class="layui-this">
                                <a lay-href="${ctx}/console">控制台</a>
                            </dd>
                            <dd data-name="console">
                                <a lay-href="${ctx}/problemCollection">问题收集</a>
                            </dd>
                        </dl>
                    </li>
                    <li data-name="app" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="应用" lay-direction="2">
                            <i class="layui-icon layui-icon-app"></i>
                            <cite>信息管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a lay-href="${ctx}/usefulInformation/admin/page">常用信息</a>
                            </dd>
                            <dd data-name="content">
                                <a href="javascript:;">学员信息</a>
                                <dl class="layui-nav-child">
                                    <dd data-name="list"><a lay-href="${ctx}/studentAndClass/admin/page">上课信息</a></dd>
                                    <dd data-name="tags"><a lay-href="${ctx}/missLessonStudent/admin/page">补课信息</a></dd>
                                    <dd data-name="tags"><a lay-href="${ctx}/student/admin/page">个人信息</a></dd>
                                </dl>
                            </dd>
                            <dd>
                                <a lay-href="${ctx}/class/admin/page">班级信息</a>
                            </dd>
                            <dd>
                                <a lay-href="${ctx}/assistant/admin/page">助教信息</a>
                            </dd>
                            <dd>
                                <a lay-href="${ctx}/teacher/admin/page">教师信息</a>
                            </dd>
                            <dd>
                                <a lay-href="${ctx}/user/admin/page">用户信息</a>
                            </dd>
                            <dd>
                                <a lay-href="${ctx}/question/admin/page">懒癌登录问题一览</a>
                            </dd>
                        </dl>
                    </li>
                    <li data-name="component" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="组件" lay-direction="2">
                            <i class="layui-icon layui-icon-component"></i>
                            <cite>百宝箱</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd data-name="grid">
                                <a href="javascript:;">学管</a>
                                <dl class="layui-nav-child">
                                    <dd data-name="mobile"><a
                                            lay-href="${ctx}/toolbox/assistantAdministrator/infoImport">信息导入</a></dd>
                                    <dd data-name="mobile"><a
                                            lay-href="${ctx}/toolbox/assistantAdministrator/templateImport">表格模板导入</a>
                                    </dd>
                                </dl>
                            </dd>
                            <dd data-name="laydate">
                                <a href="javascript:;">助教</a>
                                <dl class="layui-nav-child">
                                    <dd data-name="demo1">
                                        <a lay-href="${ctx}/toolbox/assistant/startClassExcel"
                                           lay-text="开班做表魔法">开班做表魔法</a>
                                    </dd>
                                    <dd data-name="demo2">
                                        <a lay-href="${ctx}/toolbox/assistant/missLessonStudentExcel" lay-text="开补课单魔法">开补课单魔法</a>
                                    </dd>
                                </dl>
                            </dd>
                            <dd data-name="grid">
                                <a href="javascript:;">通用</a>
                                <dl class="layui-nav-child">
                                    <dd data-name="mobile"><a
                                            lay-href="${ctx}/toolbox/common/studentSchoolExport">学校统计</a></dd>
                                </dl>
                            </dd>
                        </dl>
                    </li>
                    <li data-name="senior" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="高级应用" lay-direction="2">
                            <i class="layui-icon layui-icon-senior"></i>
                            <cite>高级应用</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd data-name="echarts">
                                <a href="javascript:;">数据可视化</a>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/senior/echarts/stuNumPage">学生人数分析</a></dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/senior/echarts/stuNumSeniorPage">学生人数分析(高级)</a></dd>
                                </dl>
                            </dd>
                            <#--<dd data-name="echarts">-->
                                <#--<a href="javascript:;">排行榜</a>-->
                                <#--<dl class="layui-nav-child">-->
                                    <#--<dd><a lay-href="${ctx}/comingSoon">吃苦耐劳助教排行</a></dd>-->
                                <#--</dl>-->
                                <#--<dl class="layui-nav-child">-->
                                    <#--<dd><a lay-href="${ctx}/comingSoon">上课人数之最</a></dd>-->
                                <#--</dl>-->
                            <#--</dd>-->
                        </dl>
                    </li>
                    <li data-name="user" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="用户" lay-direction="2">
                            <i class="layui-icon layui-icon-user"></i>
                            <cite>用户</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd class="layui-nav-itemed">
                                <a href="javascript:;">个人信息</a>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/user/setInfo">基本资料</a></dd>
                                    <dd><a lay-href="${ctx}/user/setPassword">修改密码</a></dd>
                                    <dd><a lay-href="${ctx}/user/setEmail">修改绑定邮箱</a></dd>
                                    <dd><a lay-href="${ctx}/user/setPhone">修改绑定手机</a></dd>
                                </dl>
                            </dd>
                            <dd class="layui-nav-itemed">
                                <a href="javascript:;">消息中心</a>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/user/message/page">我的消息</a></dd>
                                    <dd><a lay-href="${ctx}/user/message/send">发送消息</a></dd>
                                </dl>
                            </dd>
                        </dl>
                    </li>
                    <li data-name="set" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="设置" lay-direction="2">
                            <i class="layui-icon layui-icon-set"></i>
                            <cite>设置</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd class="layui-nav-itemed">
                                <a href="javascript:;">系统设置</a>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/system/intelliClassSeason/page">智能校历</a></dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/permission/admin/page">权限管理</a></dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd><a lay-href="${ctx}/system/announcement">公告推送</a></dd>
                                </dl>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>

        <!-- 页面标签 -->
        <div class="layadmin-pagetabs" id="LAY_app_tabs">
            <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-down">
                <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                    <li class="layui-nav-item" lay-unselect>
                        <a href="javascript:;"></a>
                        <dl class="layui-nav-child layui-anim-fadein">
                            <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                            <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                            <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i
                            class="layui-icon layui-icon-home"></i></li>
                </ul>
            </div>
        </div>


        <!-- 主体内容 -->
        <div class="layui-body" id="LAY_app_body">
            <div class="layadmin-tabsbody-item layui-show">
                <iframe src="${ctx}/console" frameborder="0" class="layadmin-iframe"></iframe>
            </div>
        </div>

        <!-- 辅助元素，一般用于移动设备下遮罩 -->
        <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
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

        <#--console.log('${announcement}')-->
        <#if announcement.read == false>
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['${announcement.width!""}', '${announcement.height!""}'], //宽高
                title: ['${announcement.parsedTitle!""}', 'color:#393D49;background-color:#01AAED;'],
                content: '${announcement.parsedContent!""}'
            });
        </#if>

    });

</script>

</body>
</html>


