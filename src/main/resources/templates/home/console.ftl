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
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md6">
                    <div class="layui-card">
                        <div class="layui-card-header">信息管理</div>
                        <div class="layui-card-body">

                            <div class="layui-carousel layadmin-carousel layadmin-shortcut">
                                <div carousel-item>
                                    <ul class="layui-row layui-col-space10">
                                    <#--<li class="layui-col-xs3">-->
                                    <#--<a lay-href="home/homepage1.html">-->
                                    <#--<i class="layui-icon layui-icon-console"></i>-->
                                    <#--<cite>主页一</cite>-->
                                    <#--</a>-->
                                    <#--</li>-->
                                    <#--<li class="layui-col-xs3">-->
                                    <#--<a lay-href="home/homepage2.html">-->
                                    <#--<i class="layui-icon layui-icon-chart"></i>-->
                                    <#--<cite>主页二</cite>-->
                                    <#--</a>-->
                                    <#--</li>-->
                                    <#--<li class="layui-col-xs3">-->
                                    <#--<a lay-href="component/layer/list.html">-->
                                    <#--<i class="layui-icon layui-icon-template-1"></i>-->
                                    <#--<cite>弹层</cite>-->
                                    <#--</a>-->
                                    <#--</li>-->
                                    <#--<li class="layui-col-xs3">-->
                                    <#--<a layadmin-event="im">-->
                                    <#--<i class="layui-icon layui-icon-chat"></i>-->
                                    <#--<cite>聊天</cite>-->
                                    <#--</a>-->
                                    <#--</li>-->
                                    <#--<li class="layui-col-xs3">-->
                                    <#--<a lay-href="component/progress/index.html">-->
                                    <#--<i class="layui-icon layui-icon-find-fill"></i>-->
                                    <#--<cite>进度条</cite>-->
                                    <#--</a>-->
                                    <#--</li>-->
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/studentAndClass/admin/page">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>学员上课信息</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/missLessonStudent/admin/page">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>学员补课信息</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/studentAndClass/admin/page">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>班级信息</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/student/admin/page">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>学员个人信息</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/user/setInfo">
                                                <i class="layui-icon layui-icon-user"></i>
                                                <cite>我的资料</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/user/setPassword">
                                                <i class="layui-icon layui-icon-user"></i>
                                                <cite>修改密码</cite>
                                            </a>
                                        </li>
                                    </ul>
                                <#--<ul class="layui-row layui-col-space10">-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--<li class="layui-col-xs3">-->
                                <#--<a lay-href="set/user/info.html">-->
                                <#--<i class="layui-icon layui-icon-set"></i>-->
                                <#--<cite>我的资料</cite>-->
                                <#--</a>-->
                                <#--</li>-->
                                <#--</ul>-->

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <div class="layui-card">
                        <div class="layui-card-header">百宝箱</div>
                        <div class="layui-card-body">
                            <div class="layui-carousel layadmin-carousel layadmin-shortcut">
                                <div carousel-item>
                                    <ul class="layui-row layui-col-space10">
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/toolbox/assistant/startClassExcel">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>开班表格制作</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/toolbox/assistant/missLessonStudentExcel">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>开补课单</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/toolbox/assistantAdministrator/infoImport">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>信息导入</cite>
                                            </a>
                                        </li>
                                        <li class="layui-col-xs3">
                                            <a lay-href="${ctx}/toolbox/assistantAdministrator/templateImport">
                                                <i class="layui-icon layui-icon-survey"></i>
                                                <cite>模板导入</cite>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-header">特别鸣谢</div>
                        <div class="layui-card-body">
                            <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                                 lay-filter="LAY-index-dataview" style=" display:block;">
                                <!--<div carousel-item id="LAY-index-dataview">-->
                                <!--<div><i class="layui-icon layui-icon-loading1 layadmin-loading"></i></div>-->
                                <!--<div></div>-->
                                <!--<div></div>-->
                                <!--</div>-->
                                <div style="position:relative; left:5%; top:10%; height:80%; width:30%; margin: 0; padding: 0; background-image:url('${ctx}/custom/img/developers/1.png'); background-size: 100% 100%; display:inline-block;">

                                </div>
                                <div style="position:relative; left:10%; top:10%; height:80%; width:55%; margin: 0; padding: 0; display:inline-block; vertical-align: top; overflow-y:auto;">
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <div class="layui-timeline-content layui-text">
                                        <h3 class="layui-timeline-title">金之贇</h3>
                                         <ul>
                                            <li>学校&专业：上海交通大学，电子信息与电气工程学院，信息安全</li>
                                             <li>职责：项目的业务需求分析、架构设计、数据库设计、前后端衔接、前后端主体代码的开发、表格导入导出核心算法的开发。
                                             </li>
                                             <li>擅长：java后台开发，mysql数据库，spring、springboot、mybatis等框架的应用，对代码的规范和质量有着极致的追求。</li>

                                         </ul>
                                        <p>&nbsp;&nbsp;&nbsp;&nbsp;首先，作为曹杨校区的一名助教，我对助教工作的辛苦深有体会。我也一直在寻找减轻大家工作负担的方式，尽到我最微薄之力。</p>
                                        <p>&nbsp;&nbsp;&nbsp;&nbsp;一开始的想法还是源于室友（非计算机专业） 在实习时嫌做excel麻烦，他说他自己用python写了个处理表格的脚本。
                                            我就说我助教也要做表，尤其是座位表特别费时，名单一变整个班的表就得重做，能不能也搞个程序处理一下? 讨论一下，理论可行，那就干呗！于是经过几周的努力，就有了第一版客户端的<a href="http://blog.kurochan.top/2019/11/02/%E6%96%B0%E4%B8%9C%E6%96%B9-%E5%8A%A9%E6%95%99%E7%99%BE%E5%AE%9D%E7%AE%B1%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/" target="_blank">助教百宝箱</a> 。
                                            后来由于开补课单的需求急切，但助教们许多使用的是mac电脑，客户端只兼容windows。所以我就想着干都干了，不如干一票大的！做个服务齐全且高度集成的网站，界面也会更好看，大家使用起来也方便......
                                        </p>
                                        <p>&nbsp;&nbsp;&nbsp;&nbsp;在这里非常感谢曹杨两位学管提供的数据和对需求分析做出的贡献，也感谢所有参与到开发的技术人员！你们的肯定，就是我前进的动力，是让我继续开发出令你们“卧槽”的功能的动力！</p>

                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="layui-card">
                        <div class="layui-card-header">特别鸣谢</div>
                        <div class="layui-card-body">
                            <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                                 lay-filter="LAY-index-dataview" style=" display:block;">
                                <!--<div carousel-item id="LAY-index-dataview">-->
                                <!--<div><i class="layui-icon layui-icon-loading1 layadmin-loading"></i></div>-->
                                <!--<div></div>-->
                                <!--<div></div>-->
                                <!--</div>-->
                                <div style="position:relative; left:5%; top:10%; height:80%; width:30%; margin: 0; padding: 0; background-image:url('${ctx}/custom/img/developers/1.png'); background-size: 100% 100%; display:inline-block;">

                                </div>
                                <div style="position:relative; left:10%; top:10%; height:80%; width:55%; margin: 0; padding: 0; display:inline-block; vertical-align: top; overflow-y:auto;">
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <!--前面的图片要换的话直接替换掉background-image中url的地址就行。这个地方用来放文字，如果文字没有溢出不会显示滚动条，如果溢出了就会显示滚动条<br>-->
                                    <div class="layui-timeline-content layui-text">
                                        <h3 class="layui-timeline-title">前言</h3>
                                        <p>导入顺序的原则是：<em>"有"即依序，"无"即跳过</em>
                                            指的是按如下的STEP顺序，如果在某一STEP有需要导入的内容，就执行这一STEP；如果没有，就跳过这一STEP</p>
                                        <p>系统读取的原则是：<em>"有"即更新，"无"即插入</em>
                                            指的是您导入的信息中，如果导入的某一条信息在数据库中已存在就覆盖更新这一记录；如果不存在，就插入这条新的记录。</p>
                                        <p>完成导入最多需要四步。这些步骤的先后次序不能颠倒。</p>
                                        <ul>
                                            <li>STEP1: 导入助教信息表。这一步的目的是导入从表中读取的助教信息到数据库，并同时为这些助教初始化网站新账号。</li>
                                            <li>STEP2: 导入排班表。这一步的目的是导入从表中读取的教师、班级信息到数据库，以及建立(班级、助教、教师)之间的关联。</li>
                                            <li>STEP3: 导入学生花名册（从总部软件中导出的花名册）。这一步的目的是导入从表中读取的学生信息到数据库，以及建立(班级、学生)之间的关联。
                                            </li>
                                            <li>STEP4: 导入学生花名册（每季开课前带学生联系方式等详细信息的学生名单）。<b style="color: red;">为什么第STEP3中已导入了学生还需要这一步？</b>
                                                这一步主要是读取学生的手机，如果执行了这一步，在助教制作开班电话时，系统能自动读取并填充学生联系方式一列。
                                            </li>
                                        </ul>
                                        <p>常见场景:</p>
                                        <ul>
                                            <li>场景1：春季马上要开课了，校区新招了一些助教，新的学生名单和排班表都已经有了。执行：STEP1 -> STEP2 -> STEP3 ->
                                                STEP4
                                            </li>
                                            <li>场景2：春季已经开课几周了，校区新招了几个助教，他们没有固定的属于自己的班级带，主要是负责代班，即只有助教信息变了。执行：STEP1</
                                            </li>
                                            <li>场景3：春季已经开课几周了，校区有几个助教走了，因此新招了几个助教顶替原来的助教的班级，即助教信息和排班表变了。执行：STEP1 ->
                                                STEP2
                                            </
                                            </li>
                                            <li>场景4：春季已经开课几周了，最近有一些班级的上课教室和任课教师有变动，即只有排班信息变了。执行：STEP2</li>
                                            <li>场景5：春季已经开课几周了，最近有一些新学生进班还有一些转班的，但助教没有人员变动，即只有学生和学生上课信息变了。执行：STEP3</
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="layui-card">
                        <div class="layui-tab layui-tab-brief layadmin-latestData">
                            <ul class="layui-tab-title">
                                <li class="layui-this">今日热搜</li>
                                <li>今日热帖</li>
                            </ul>
                            <div class="layui-tab-content">
                                <div class="layui-tab-item layui-show">
                                    <table id="LAY-index-topSearch"></table>
                                </div>
                                <div class="layui-tab-item">
                                    <table id="LAY-index-topCard"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">版本信息</div>
                <div class="layui-card-body layui-text">
                    <table class="layui-table">
                        <colgroup>
                            <col width="100">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>当前版本</td>
                            <td>
                                <script type="text/html" template>
                                    v{{ layui.admin.v }}
                                    <a href="http://fly.layui.com/docs/3/" target="_blank" style="padding-left: 15px;">更新日志</a>
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td>基于框架</td>
                            <td>
                                <script type="text/html" template>
                                    layui-v{{ layui.v }}
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td>主要特色</td>
                            <td>零门槛 / 响应式 / 清爽 / 极简</td>
                        </tr>
                        <tr>
                            <td>获取渠道</td>
                            <td style="padding-bottom: 0;">
                                <div class="layui-btn-container">
                                    <a href="http://www.layui.com/admin/" target="_blank"
                                       class="layui-btn layui-btn-danger">获取授权</a>
                                    <a href="http://fly.layui.com/download/layuiAdmin/" target="_blank"
                                       class="layui-btn">立即下载</a>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        <#--<div class="layui-card">-->
        <#--<div class="layui-card-header">效果报告</div>-->
        <#--<div class="layui-card-body layadmin-takerates">-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>转化率（日同比 28% <span class="layui-edge layui-edge-top" lay-tips="增长" lay-offset="-15"></span>）</h3>-->
        <#--<div class="layui-progress-bar" lay-percent="65%"></div>-->
        <#--</div>-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>签到率（日同比 11% <span class="layui-edge layui-edge-bottom" lay-tips="下降" lay-offset="-15"></span>）</h3>-->
        <#--<div class="layui-progress-bar" lay-percent="32%"></div>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->

        <#--<div class="layui-card">-->
        <#--<div class="layui-card-header">实时监控</div>-->
        <#--<div class="layui-card-body layadmin-takerates">-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>CPU使用率</h3>-->
        <#--<div class="layui-progress-bar" lay-percent="58%"></div>-->
        <#--</div>-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>内存占用率</h3>-->
        <#--<div class="layui-progress-bar layui-bg-red" lay-percent="90%"></div>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->

            <div class="layui-card">
                <div class="layui-card-header">帮助文档</div>
                <div class="layui-card-body">
                    <div class="layui-carousel layadmin-carousel layadmin-news" data-autoplay="true" data-anim="fade"
                         lay-filter="news">
                        <div carousel-item>
                            <div><a href="http://fly.layui.com/docs/2/" target="_blank" class="layui-bg-red">layuiAdmin
                                快速上手文档</a></div>
                            <div><a href="http://fly.layui.com/vipclub/list/layuiadmin/" target="_blank"
                                    class="layui-bg-green">layuiAdmin 会员讨论专区</a></div>
                            <div><a href="http://www.layui.com/admin/#get" target="_blank" class="layui-bg-blue">获得
                                layui 官方后台模板系统</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">
                    作者心语
                    <i class="layui-icon layui-icon-tips" lay-tips="要支持的噢" lay-offset="5"></i>
                </div>
                <div class="layui-card-body layui-text layadmin-text">
                    <p>一直以来，layui 秉承无偿开源的初心，虔诚致力于服务各层次前后端 Web
                        开发者，在商业横飞的当今时代，这一信念从未动摇。即便身单力薄，仍然重拾决心，埋头造轮，以尽可能地填补产品本身的缺口。</p>
                    <p>在过去的一段的时间，我一直在寻求持久之道，已维持你眼前所见的一切。而 layuiAdmin 是我们尝试解决的手段之一。我相信真正有爱于 layui 生态的你，定然不会错过这一拥抱吧。</p>
                    <p>子曰：君子不用防，小人防不住。请务必通过官网正规渠道，获得 <a href="http://www.layui.com/admin/"
                                                        target="_blank">layuiAdmin</a>！</p>
                    <p>—— 贤心（<a href="http://www.layui.com/" target="_blank">layui.com</a>）</p>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'console']);
</script>
</body>
</html>


