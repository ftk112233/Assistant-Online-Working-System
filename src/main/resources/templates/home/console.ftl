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
                                            <a lay-href="${ctx}/class/admin/page">
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
                        <div class="layui-card">
                            <div class="layui-carousel" id="test-carousel-img">
                                <div carousel-item="">
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d1.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d2.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d3.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d4.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d5.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d6.jpg"></div>
                                    <div><img width="100%" src="${ctx}/custom/img/carousel/d7.jpg"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-card">
                        <div class="layui-card-header">特别鸣谢</div>
                        <div class="layui-card-body">
                            <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade"
                                 lay-filter="LAY-index-dataview" style=" display:block;">
                                <div carousel-item>
                                    <div>
                                        <div style="position:relative; left:5%; top:10%; height:80%; width:25%; margin: 0; padding: 0; background-image:url('${ctx}/custom/img/developers/jzy.png'); background-size: 100% 100%; display:inline-block;">

                                        </div>
                                        <div style="position:relative; left:10%; top:10%; height:80%; width:62%; margin: 0; padding: 0; display:inline-block; vertical-align: top; overflow-y:auto;">
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
                                                    <li>职责：java全栈。项目的需求分析、架构设计、数据库设计、主体代码的开发、表格导入导出核心算法的开发。
                                                    </li>
                                                    <li>
                                                        擅长：java后台开发，mysql数据库，spring、springboot、mybatis等框架的应用，对代码的规范和质量有着较高的追求。
                                                    </li>

                                                </ul>
                                                <p>&nbsp;&nbsp;&nbsp;&nbsp;首先，作为曹杨校区的一名助教，我对助教工作的辛苦深有体会（特别是开班阶段做表，不难但特烦）。我也一直在寻找减轻大家工作负担的方式，尽到我最微薄之力。</p>
                                                <p>&nbsp;&nbsp;&nbsp;&nbsp;一开始的想法还是源于室友（非计算机相关专业）
                                                    说实习时嫌做excel麻烦，他自己用python写了个处理表格的脚本。
                                                    我就说我助教也要做表，尤其是座位表特别费时，得一个个序号对着填，名单一变整个班的表就得重做，能不能也搞个程序处理一下?
                                                    讨论一下，理论可行，那就干呗！于是经过几周的努力，就有了第一版客户端的<a
                                                            href="http://blog.kurochan.top/2019/11/02/%E6%96%B0%E4%B8%9C%E6%96%B9-%E5%8A%A9%E6%95%99%E7%99%BE%E5%AE%9D%E7%AE%B1%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/"
                                                            target="_blank">助教百宝箱</a> 。
                                                    后来由于开补课单的需求急切，且客户端的兼容性较差。所以想着干都干了，不如干一票大的！做个服务齐全且高度集成的网站，界面也会更好看，大家使用起来也方便......
                                                </p>
                                                <p>&nbsp;&nbsp;&nbsp;&nbsp;在这里非常感谢两位学管提供的数据和对需求分析做出的贡献，也感谢所有对这个项目给予过各种支持的人！你们的肯定，就是我前进的动力，是让我继续开发出令你们“卧槽”的功能的动力！</p>

                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <div style="position:relative; left:5%; top:10%; height:80%; width:25%; margin: 0; padding: 0; background-image:url('${ctx}/custom/img/developers/xf.png'); background-size: 100% 100%; display:inline-block;">

                                        </div>
                                        <div style="position:relative; left:10%; top:10%; height:80%; width:62%; margin: 0; padding: 0; display:inline-block; vertical-align: top; overflow-y:auto;">
                                            <div class="layui-timeline-content layui-text">
                                                <h3 class="layui-timeline-title">谢飞</h3>
                                                <ul>
                                                    <li>学校&专业：上海交通大学，电子信息与电气工程学院，信息安全</li>
                                                    <li>职责：信息安全工程师。项目的安全评估，漏洞检测及渗透测试，保障网站的核心。
                                                    </li>
                                                    <li>
                                                        擅长：漏洞挖掘，渗透测试，安全评估，对常见的web安全隐患有着独到的见解，擅长从各种小地方挖掘可能的安全漏洞。
                                                    </li>

                                                </ul>
                                                <p>&nbsp;&nbsp;&nbsp;&nbsp;非常高兴能帮jzy做这个项目的安全测试（其实我基本没什么工作量）。
                                                    我觉得他是一个非常认真的人，对于每一个功能每一行代码都有着极高的要求。作为好朋友，在学校和他一起做了很多大作业课程设计（划水），他总是能让人惊艳。
                                                    同时他也是一个非常有自己想法的人。虽然作为信安专业，他却对开发的热爱执着，他的一点一滴的努力让他真正融入这个领域，这也使他在2020秋招中斩获了不错的结果。</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <div style="position:relative; left:5%; top:10%; height:80%; width:25%; margin: 0; padding: 0; background-image:url('${ctx}/custom/img/developers/zwc.png'); background-size: 100% 100%; display:inline-block;">

                                        </div>
                                        <div style="position:relative; left:10%; top:10%; height:80%; width:62%; margin: 0; padding: 0; display:inline-block; vertical-align: top; overflow-y:auto;">
                                            <div class="layui-timeline-content layui-text">
                                                <h3 class="layui-timeline-title">张崴城</h3>
                                                <ul>
                                                    <li>学校&专业：上海交通大学，电子信息与电气工程学院，信息安全</li>
                                                    <li>职责：前端开发&设计。项目部分前台页面的设计、布局和编码，也为部署后服务端的运维提供了部分技术支持。
                                                    </li>
                                                    <li>
                                                        擅长：熟悉layui前端框架，擅长前端设计开发。喜欢计算机视觉，了解相关python深度学习算法（这与本项目无关）。
                                                    </li>

                                                </ul>
                                                <p>&nbsp;&nbsp;&nbsp;&nbsp;很高兴能一起开发这个项目~从入校就和jzy是好朋友，之前还和他一起参加全国大学生信息安全大赛，参赛项目是计算机视觉相关的，但系统前台用的layui框架，所以我这次帮他一起参与基于layui的前端设计开发。因此我的领域其实不是（前端）开发emmm。
                                                    他对项目有着自己的想法，就像他对自己未来的领域和道路一样。就应该做自己喜欢做的嘛！
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">常用信息<a lay-href="${ctx}/usefulInformation/admin/page" style="color: blue"
                                                      lay-text="常用信息">>>>更多</a></div>
                <div class="layui-card-body layui-text">
                    <div class="layui-form">
                        <div class="layui-inline">
                            <label class="layui-form-label">类别</label>
                            <div class="layui-input-inline">
                                <select name="belongTo" id="belongTo" lay-filter="belongTo" lay-search>
                                    <option value="">请选择信息归属类别</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <table class="layui-table">
                        <colgroup>
                            <col width="120">
                            <col>
                        </colgroup>
                        <tbody id="info-body">
                        </tbody>
                    </table>
                </div>
            </div>

        <#--<div class="layui-card" id="systemLoad">-->
        <#--<div class="layui-card-header">服务器负载实时监控</div>-->
        <#--<div class="layui-card-body layadmin-takerates">-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>CPU使用率</h3>-->
        <#--<div class="layui-progress-bar" id="cpu" lay-percent="0%"></div>-->
        <#--</div>-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>内存占用率</h3>-->
        <#--<div class="layui-progress-bar layui-bg-red" id="memory" lay-percent="0%"></div>-->
        <#--</div>-->
        <#--<div class="layui-progress" lay-showPercent="yes">-->
        <#--<h3>磁盘占用率</h3>-->
        <#--<div class="layui-progress-bar layui-bg-red" id="disk" lay-percent="0%"></div>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->

            <div class="layui-card">
                <div class="layui-card-header">帮助文档</div>
                <div class="layui-card-body">
                    <div class="layui-carousel layadmin-carousel layadmin-news" data-autoplay="true"
                         data-anim="fade"
                         lay-filter="news">
                        <div carousel-item>
                            <div><a href="${ctx}/comingSoon" target="_blank" class="layui-bg-red">表格导入手机通讯录</a></div>
                            <div><a href="http://blog.kurochan.top/2020/01/01/%E4%BC%98%E8%83%BD%E5%8A%A9%E6%95%99%E5%9C%A8%E7%BA%BF%E5%B7%A5%E4%BD%9C%E5%B9%B3%E5%8F%B0%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/" target="_blank"
                                    class="layui-bg-green">平台详细使用指南</a></div>
                            <div><a href="http://blog.kurochan.top/2020/01/01/%E4%BC%98%E8%83%BD%E5%8A%A9%E6%95%99%E5%9C%A8%E7%BA%BF%E5%B7%A5%E4%BD%9C%E5%B9%B3%E5%8F%B0%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/#%E5%BC%80%E7%8F%AD%E5%81%9A%E8%A1%A8%E6%8C%87%E5%8D%97" target="_blank"
                                    class="layui-bg-blue">开班做表指南</a></div>
                        <#--<div><a href="http://www.layui.com/admin/#get" target="_blank" class="layui-bg-blue">获得-->
                        <#--layui 官方后台模板系统</a></div>-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-card">
                <div class="layui-card-header">幸福倒计时</div>
                <div class="layui-card-body">
                    距离下次计算工资 <em id="calcul"></em> 还有：
                <#--<div class="layui-inline">-->
                <#--<input type="text" class="layui-input" id="test-util-countdown" value="2099-01-01 00:00:00">-->
                <#--</div>-->
                    <blockquote class="layui-elem-quote" style="margin-top: 10px;">
                        <div id="test-util-countdown-ret-calcul"></div>
                    </blockquote>
                    距离下次发工资 <em id="pay"></em> 还有：
                    <blockquote class="layui-elem-quote" style="margin-top: 10px;">
                        <div id="test-util-countdown-ret-pay"></div>
                    </blockquote>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">
                    加入我们
                    <i class="layui-icon layui-icon-tips" lay-tips="要支持的噢" lay-offset="5"></i>
                </div>
                <div class="layui-card-body layui-text layadmin-text">
                    <p>如果你追求优质的产品和服务，或你的强项是码字，或是擅长设计、有独特的审美，或有着超乎常人的细心和耐心，那么我们需要你！</p>
                    <p>如果你热爱写代码，如果擅长java，或是熟悉数据库，或是对前端开发有经验，或是在学校做过相关的大作业，那么我们需要你！</p>
                    <p>如果你是助教，而且喜欢这个平台，想和我们一起收获经验、技术和知识，那么优先考虑！</p>
                    <p>我们需要↓↓↓</p>
                    <p>非技术：负责用户的需求采集，整理用户的反馈；‘使用指南’等相关文档的撰写；用户界面的设计等。xdf助教优先。</p>
                    <p>技术：参与系统核心业务的维护、研发与优化，哪怕一行代码的优化，给助教工作带来几毫秒的便捷都值得！xdf助教优先。</p>
                    <p>我们期待你的加入，一起为助教工作献出一份力！请砸此邮箱：929703621@qq.com</p>
                    <p>—— 酷乐（<a href="http://blog.kurochan.top/about/" target="_blank">kurochan.top</a>）</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctx}/custom/js/img.js"></script>

<script src="${ctx}/plugins/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${ctx}/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user', 'layer', 'console', 'laydate', 'carousel'], function () {
        var $ = layui.$
                , setter = layui.setter
                , layer = layui.layer
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , laydate = layui.laydate
                , util = layui.util
                , upload = layui.upload
                , carousel = layui.carousel;

        /**
         * 获取与当前时间最邻近的nextMonthDay天nextMonthHour时0分0秒的日期
         * 如当前时间2019-12-10, 12:30:00， nextMonthDay=21，nextMonthHour=0。下次的时间点即为2019-12-21, 0:0:0
         * 如当前时间2019-12-10, 12:30:00， nextMonthDay=10，nextMonthHour=13。下次的时间点即为2019-12-21, 13:0:0
         * 如当前时间2019-12-10, 12:30:00， nextMonthDay=10，nextMonthHour=12。下次的时间点即为2020-1-10, 12:0:0
         *
         * @param nextMonthDay
         * @param nextMonthHour
         * @returns {Array} [年, 月, 日, 时, 分, 秒]
         */
        function getNextMonthDate(nextMonthDay, nextMonthHour) {

            var dateNow = new Date();
            var year = dateNow.getFullYear();

            var month = dateNow.getMonth() + 1;
            var day = dateNow.getDate();
            var hour = dateNow.getHours();
            var minute = dateNow.getMinutes();
            var second = dateNow.getSeconds();

            var r = [];
            var nextYear;
            var nextMonth;
            var nextDay = nextMonthDay;
            var nextHour = nextMonthHour;
            var nextMinute = 0;
            var nextSecond = 0;
            if (day < nextMonthDay) {
                //还没到指定的天
                nextMonth = month;
                nextYear = year;
            } else if (day === nextMonthDay) {
                //当天
                if (hour < nextMonthHour) {
                    nextMonth = month;
                    nextYear = year;
                } else {
                    //过了点，下个月当天再见吧
                    if (month === 12) {
                        //进一位，即新一年的1月
                        nextMonth = 1;
                        nextYear = year + 1;
                    } else {
                        nextMonth = month + 1;
                        nextYear = year;
                    }
                }

            } else {
                //过了点，下个月当天再见吧
                if (month === 12) {
                    //进一位，即新一年的1月
                    nextMonth = 1;
                    nextYear = year + 1;

                } else {
                    nextMonth = month + 1;
                    nextYear = year;
                }

            }

            r[0] = nextYear;
            r[1] = nextMonth;
            r[2] = nextDay;
            r[3] = nextHour;
            r[4] = nextMinute;
            r[5] = nextSecond;
            return r;
        }

        //倒计时
        var thisTimer, setCountdown = function (y, M, d, H, m, s) {
            var endTime = new Date(y, M || 0, d || 1, H || 0, m || 0, s || 0) //结束日期
                    , serverTime = new Date(); //假设为当前服务器时间，这里采用的是本地时间，实际使用一般是取服务端的

            clearTimeout(thisTimer);
            util.countdown(endTime, serverTime, function (date, serverTime, timer) {
                var str = date[0] + '天' + date[1] + '时' + date[2] + '分' + date[3] + '秒';
                lay('#test-util-countdown-ret-calcul').html(str);
                thisTimer = timer;
            });
        };

        //每月20号12点计算工资
        var nextCalCulDay = 20;
        var nextCalCulHour = 12;
        var nextCalculateTime = getNextMonthDate(nextCalCulDay, nextCalCulHour);
        setCountdown(nextCalculateTime[0], nextCalculateTime[1] - 1, nextCalculateTime[2], nextCalculateTime[3], nextCalculateTime[4], nextCalculateTime[5]);
        $("#calcul").html(nextCalculateTime[0] + '年' + nextCalculateTime[1] + '月' + nextCalculateTime[2] + '日 ' + nextCalculateTime[3] + '点')

        //倒计时
        var thisTimer2, setCountdown2 = function (y, M, d, H, m, s) {
            var endTime = new Date(y, M || 0, d || 1, H || 0, m || 0, s || 0) //结束日期
                    , serverTime = new Date(); //假设为当前服务器时间，这里采用的是本地时间，实际使用一般是取服务端的

            clearTimeout(thisTimer2);
            util.countdown(endTime, serverTime, function (date, serverTime, timer) {
                var str = date[0] + '天' + date[1] + '时' + date[2] + '分' + date[3] + '秒';
                lay('#test-util-countdown-ret-pay').html(str);
                thisTimer2 = timer;
            });
        };
        //每月6号12点发工资
        var nextPayDay = 6;
        var nextPayHour = 12;
        var nextPayTime = getNextMonthDate(nextPayDay, nextPayHour);
        setCountdown2(nextPayTime[0], nextPayTime[1] - 1, nextPayTime[2], nextPayTime[3], nextPayTime[4], nextPayTime[5]);
        $("#pay").html(nextPayTime[0] + '年' + nextPayTime[1] + '月' + nextPayTime[2] + '日 ' + nextPayTime[3] + '点')


        // laydate.render({
        //     elem: '#test-util-countdown'
        //     ,type: 'datetime'
        //     ,done: function(value, date){
        //         setCountdown(date.year, date.month - 1, date.date, date.hours, date.minutes, date.seconds);
        //     }
        // });



        show_img = function (img) {
            var imgUrl = '${ctx}/usefulInformation/admin/getImage?image=' + img;
            getImageWidth(imgUrl, function (w, h) {
                w = w / (h / 600);
                h = 600;
                //页面层
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: [w + 'px', h + 'px'],
                    skin: 'layui-layer-nobg', //没有背景色
                    shadeClose: true,
                    content: '<div style="text-align:center"><img style="height:600px;" src="${ctx}/usefulInformation/admin/getImage?image=' + img + '" /></div>'
                });
            });
        };


        var allBelongTo = eval('(' + '${belongTo}' + ')');
        for (var i = 0; i < allBelongTo.length; i++) {
            var json = allBelongTo[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#belongTo").append(str);
        }
        $("#belongTo").val(allBelongTo[0]);

        form.render();

        $.ajax({
            type: "get",
            data: {belongTo: $("#belongTo").val()},
            url: "${ctx}/usefulInformation/admin/getByBelongTo",
            success: function (data) {
                $("#info-body").empty();
                for (var i = 0; i < data.length; i++) {
                    var object = data[i];

                    var imgTip = '';
                    if (object.image != '' && object.image != null) {
                        imgTip = '（<a href = "javascript:void(0);" onclick =\'var ss="' + object.image + '";show_img(ss)\'>有图，点击查看</a>）';
                    }

                    $("#info-body").append('<tr><td>' + object.title + '</td><td>' + object.content + imgTip + '</td></tr>');
                }
            }
        });

        //联动监听select
        form.on('select(belongTo)', function (data) {
            var belongTo = $(this).attr("lay-value");
            $.ajax({
                type: "get",
                data: {belongTo: belongTo},
                url: "${ctx}/usefulInformation/admin/getByBelongTo",
                success: function (data) {
                    $("#info-body").empty();
                    for (var i = 0; i < data.length; i++) {
                        var object = data[i];

                        var imgTip = '';
                        if (object.image != '' && object.image != null) {
                            imgTip = '（<a href = "javascript:void(0);" onclick =\'var ss="' + object.image + '";show_img(ss)\'>有图，点击查看</a>）';
                        }
                        $("#info-body").append('<tr><td>' + object.title + '</td><td>' + object.content + imgTip + '</td></tr>');
                    }
                }
            });
        });


    <#--$.ajax({-->
    <#--type: "get",-->
    <#--data: {},-->
    <#--url: "${ctx}/getSystemLoad",-->
    <#--success: function (data) {-->
    <#--$("#cpu").attr('lay-percent', data.data.cpu+'%');-->
    <#--$("#memory").attr('lay-percent', data.data.memory+'%');-->
    <#--$("#disk").attr('lay-percent', data.data.disk+'%');-->
    <#--}-->
    <#--});-->

        // <div class="layui-timeline-content layui-text">                                <p>欢迎使用<em>AOWS-优能助教在线工作平台</em>！</p>                     <p>为了您的账号安全，请尽快修改默认密码。也推荐绑定安全邮箱便于找回密码以及安全验证！</p>                          <p>常用功能： </p>                                <ul>                                    <li>我的班级信息：左边菜单栏>信息管理>班级信息，点击按钮"查询我的班级"。</li>                                    <li>做开班表格：完成1中的操作后，点击表格中的"开班做表"，跳转做表页面后，点击输出相应表格即可。</li>                                    <li>学生上课信息：左边菜单栏>信息管理>学员信息>上课信息，可以查询指定学生的上课记录。</li>                                    <li>登录方式之懒癌登录：懒癌登录方式的答案，可以在"左边菜单栏>懒癌登录问题一览"查询。你也可以将自己喜欢的问题添加到问题的随机池中！</li>                                    <li>用户角色权限说明：管理员>学管>助教长>助教=教师>游客。可在：用户>个人信息>基本资料，查看自己的角色。通过"懒癌登录"的用户角色是游客。</li>                                </ul>                          <p>使用中遇到的任何问题都可以向我<a lay-href="/problemCollection">反馈</a>~</p>                                         <p align="right"><a href="http://blog.kurochan.top/about/" target="_blank">kuro</a>    <p>                                         <p align="right">2019-12-8    <p>                            </div>

        //图片轮播
        var b=1920/1080;
        var w=$("#test-carousel-img").width();
        carousel.render({
            elem: '#test-carousel-img'
            ,width: '100%' //设置容器宽度
            ,height:(w/b).toString()+'px'
            ,interval: 3000
        });

    });


</script>
</body>
</html>


