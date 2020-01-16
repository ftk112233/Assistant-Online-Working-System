<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>信息导入</title>
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
                <div class="layui-card-header">信息导入四步走。<a style="color:red">助教信息、学员名单、排班信息的导入必须按顺序执行！</a></div>
                <div class="layui-card-body">
                    <ul class="layui-timeline">
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">前言</h3>
                                <p>导入顺序的原则是：<em>"有"即依序，"无"即跳过</em>
                                    指的是按如下的STEP顺序，如果在某一STEP有需要导入的内容，就执行这一STEP；如果没有，就跳过这一STEP</p>
                                <p>系统读取的原则是：<em>"有"即更新，"无"即插入</em>
                                    指的是您导入的信息中，如果导入的某一条信息在数据库中已存在就覆盖更新这一记录；如果不存在，就插入这条新的记录。</p>
                                <p>有以下导入步骤。这些步骤的先后次序不能颠倒。</p>
                                <ul>
                                    <li>STEP1: 导入助教信息表。这一步的目的是导入从表中读取的助教信息到数据库，并同时为这些助教初始化网站新账号。</li>
                                    <li>STEP2: 导入排班表。这一步的目的是导入从表中读取的教师、班级信息到数据库，以及建立(班级、助教、教师)之间的关联。</li>
                                    <li>STEP3: 导入学生花名册（从总部软件中导出的花名册）。这一步的目的是导入从表中读取的学生及其上课信息到数据库，以及建立(班级、学生)之间的关联。</li>
                                    <li>STEP4: 导入学生花名册（每季开课前带学生联系方式等详细信息的学生名单）。<b style="color: red;">为什么第STEP3中已导入了学生还需要这一步？</b>
                                        这一步主要是读取学生的联系方式，如果执行了这一步，在助教制作开班电话时，系统能自动读取并填充学生联系方式一列。
                                    </li>
                                    <li>STEP4.5:
                                        导入学生学校统计。这一步的目的是导入从表中读取的学生学校信息到数据库。这一步的好处是既可以将校区的学校统计存于云端永久保存，也可以便于后续季度学管统计学生学校时信息的复用，在助教制作开班电话时，系统也能自动读取并填充已知学生的学校。
                                    </li>
                                </ul>
                                <p>常见场景:</p>
                                <ul>
                                    <li>场景1：春季马上要开课了，校区新招了一些助教，新的学生名单和排班表都已经出来了。执行：STEP1 -> STEP2 -> STEP3 -> STEP4</li>
                                    <li>场景2：这个季度刚完成总部的任务，做好最新的学校统计，想把已有的学校统计存到云端。执行：STEP4.5</li>

                                    <li>场景3：春季已经开课几周了，校区新招了几个助教，他们没有固定的属于自己的班级带，主要是负责代班，即只有助教信息变了。执行：STEP1
                                    </li>
                                    <li>场景4：春季已经开课几周了，校区有几个助教走了，因此新招了几个助教顶替原来的助教的班级，即助教信息和排班表变了。执行：STEP1 -> STEP2
                                    </li>
                                    <li>场景5：春季已经开课几周了，最近有一些班级的上课教室和任课教师有变动，即只有排班信息变了。执行：STEP2</li>
                                    <li>场景6：春季已经开课几周了，最近有一些学生进班、转班和退班，但助教没有人员变动，即只有学生和学生上课信息变了。执行：STEP3
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP1: 导入助教信息表</h3>
                                <p>上传excel要求说明：<a
                                        href="${ctx}/toolbox/assistantAdministrator/downloadExample/1">查看范例</a></p>
                                <ul>
                                    <li>第1行、第2行与导入无关。有效内容从第3行开始，第3行为列名属性：序号、部门、校区、姓名、员工号等......</li>
                                    <li>第3行所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！其中“部门”和“备注”不是必须的。<br>
                                        _____________________________________________<br>
                                        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        上海新东方劳务明细表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
                                        _____________________________________________<br>
                                        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        日期：XXXX年XX月XX日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
                                        _____________________________________________<br>
                                        | 部门 | 校区 | 姓名 | 员工号 | 身份证号 | 手机号码 | 备注 |<br>
                                        _____________________________________________
                                    </li>

                                    <li>第四行开始是数据:<br>
                                        1)员工号必须唯一且准确<br>
                                        2)身份证号必须唯一且准确<br>
                                        3)手机号必须唯一且准确<br>
                                    </li>
                                </ul>
                                <p>导入成功后：</p>
                                <ul>
                                    <li>姓名导入助教表中时，考虑到各校区助教<b style="color: red;">可能有重名</b>的情况，对于重名助教在“班级信息-查询我的班级”时可能会查不到，他们必须手动地输入查询条件查询，这点应当予以告知助教。
                                    </li>
                                    <li>导入用户后，助教初次登陆使用如下用户名密码：<br>
                                        1)用户名：身份证号或手机号<br>
                                        2)密码：身份证号
                                    </li>
                                </ul>
                                <p  style="color:red">'助教长'角色没有'信息管理>用户信息'查询编辑权限，如果想要查看用户信息，请使用校区公共'学管'账号——用户名/密码都是：校区名拼音(汉字间下划线间隔)。如，曹杨校区公共账号：cao_yang。</p>
                            </div>
                            <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                                <input type="checkbox" name="read_step1" id="read_step1" lay-skin="primary"
                                       lay-filter="read_step1" title=""><b style="color: red;">我已认真阅读以上内容</b>
                            </div>
                            <div class="layui-timeline-content layui-text" id="div-import-user-and-assistant"
                                 hidden="hidden">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel"
                                        style="background-color: #FFB800"
                                        id="import-user-and-assistant" lay-filter="import-user-and-assistant"><i
                                        class="layui-icon">&#xe67c;</i>导入用户和助教
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP2: 导入排班表</h3>
                                <p>这一步中，如果您是在原来的基础上更新排班表且有取消的班级<b
                                        style="color: red;">（根据我的经验一般很少有这种情况，如原班级有4个：a、b、c、d，现在只有a、b、c；如果仅仅是修改或是新增，可以跳过此小步）</b>
                                    ，<b style="color: red;">必须先前往：信息管理>班级信息</b>，查询出当前<b
                                            style="color: red;">年份-季度-校区</b>的班级记录，全部删除后，再执行以下步骤(<b style="color: red;">或勾选'先删后导'选项</b>)。为什么要这样做？
                                    因为系统更新的原则：对于新的班级执行插入；对于修改过的班级执行更新；而对于已经不再有效的班级，系统无法从excel中悉知，因此需要先删除这些特例!<b
                                            style="color: red;">删除了班级后，其下的所有学生上课记录也将删除，因此如果执行了这一带删除的操作，必须再执行STEP3！同时这些班级下的补课记录也会被删除。因此我不推荐你勾选'先删后导'这种会把原来有用的班级也删除的操作，而是去管理页面手动的删除那些个'不再有效的班级'。</b>
                                </p>
                                <p>上传excel要求说明：<a
                                        href="${ctx}/toolbox/assistantAdministrator/downloadExample/2">查看范例</a></p>
                                <ul>
                                    <li>第1行为列名属性。所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                        _________________________________________________<br>
                                        | 班级编码 | 班级名称 | 教师姓名 | 上课教室 | 上课时间 | 助教 | <br>
                                        _________________________________________________
                                    </li>

                                    <li>特别注意:<br>
                                        1)教师姓名列和助教列必须使用<b style="color:red">真实姓名</b>，特别是助教列中的名字必须与助教信息表中的名字一致，不能有错字。不能使用外号昵称小名！<br>
                                        反例：STEP1中导入了一个名叫“金之贇”的助教，现在导入的排班表中该助教的名字是“金之赟”（第三个字不一致，有错字）<br>
                                        反例：STEP1中导入了一个名叫“朱盈玥”的助教，现在导入的排班表中该助教的名字是“蛋蛋”（使用小名外号）<br>
                                        2)上课教室列中程序将对字串从左到右抽取最长的连续数字串、或最长的连续数字串+后一个字母，确保上课教室中没有除教室无关的过长的数字子串。<br>
                                        正例：310 ----> 读取结果：310，正确<br>
                                        正例：YN曹杨310教 ----> 读取结果：310，正确<br>
                                        正例：新闸610A教 ----> 读取结果：610A，正确<br>
                                        反例：曹杨310教，代号1300 ----> 读取结果：1300，错误！（"1300"这个无关数字串的长度还比"310"长，所以系统读到了"1300"）<br>
                                        反例：曹杨路540号曹杨310教 ---->
                                        读取结果：540，错误！（"540"虽然长度和"310"相同，但它出现在"310"前面所以先读到了"540"）<br>
                                        反例：曹杨路540号曹杨310A教 ---->
                                        读取结果：540，错误！（"540"虽然长度和"310"相同，但它出现在"310"前面所以先读到了"540"）<br>
                                        3)上课时间中必须含有子串xx:xx-xx:xx(小时字段可以只有一位，如x:xx-x:xx、x:xx-xx:xx等)，冒号为英文冒号，且除xx:xx-xx:xx的子串外不能再出现冒号。<br>
                                        正例：周六10:15-12:15(11.2,11.9休息,11.3,11.4上课) ----> 读取结果：10:15-12:15，正确<br>
                                        反例：周五:不上课, 周六10:15-12:15(11.2,11.9休息,11.3,11.4上课) ---->
                                        读取结果为空，错误！（因为周五后面出现了无关的冒号）
                                    </li>
                                </ul>
                            </div>
                            <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                                <input type="checkbox" name="read_step2" id="read_step2" lay-skin="primary"
                                       lay-filter="read_step2" title=""><b style="color: red;">我已认真阅读以上内容</b>
                            </div>
                            <div class="layui-form layui-form-item layui-timeline-content" style="margin-bottom: 20px;"
                                 id="div1-import-teacher-and-class" hidden="hidden">
                                <label class="layui-form-label">自动解析&nbsp; <i class="layui-icon layui-icon-tips"
                                                                              lay-tips="开启'自动解析'将自动根据2019年新东方班级编码规则从班级编码中解析出校区、班型等信息。如果班号不符合此规则，可以关闭此选项，手动设置"></i></label>
                                <div class="layui-input-inline">
                                    <input type="checkbox" name="parseClassId" id="parseClassId" lay-skin="switch"
                                           lay-text="ON|OFF"
                                           lay-filter="parseClassId" checked>
                                </div>
                                <label class="layui-form-label">先删后导&nbsp; <i class="layui-icon layui-icon-tips"
                                                                              lay-tips="开启'先删后导'将先删除对应年份-季度-校区的所有班级，再对导入信息执行插入。开启此选项会使导入时间变长。如果更新的排班表中只有更改或增加的班级，而没有删减的班级可以关闭此选项（根据我的经验通常情况是班级信息只有修改的，所以一般不用打开此选项）。"></i></label>
                                <div class="layui-input-inline">
                                    <input type="checkbox" name="deleteFirst1" id="deleteFirst1" lay-skin="switch"
                                           lay-text="ON|OFF"
                                           lay-filter="deleteFirst1">
                                </div>
                                <label class="layui-form-label">智能日历&nbsp; <i class="layui-icon layui-icon-tips"
                                                                              lay-tips="开启'智能日历'将把当前选择导入的'年份-季度-分期'添加到缓存，在学生和班级信息查询时，系统将根据缓存智能勾选合适的'年份-季度-分期'"></i></label>
                                <div class="layui-input-inline">
                                    <input type="checkbox" name="chooseSeason" id="chooseSeason" lay-skin="switch"
                                           lay-text="ON|OFF"
                                           lay-filter="chooseSeason" checked>
                                </div>
                            </div>
                            <div class="layui-form layui-form-item layui-timeline-content" style="margin-bottom: 20px;"
                                 id="div2-import-teacher-and-class" hidden="hidden">
                                <label class="layui-form-label">开课学期所在年份</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" autocomplete="off" placeholder="yyyy"
                                           id="year" name="year"
                                           lay-verify="required">
                                </div>
                                <div class="layui-form-mid " style="color:red">*必填项</div>
                                <label class="layui-form-label" id="season_l">季度</label>
                                <div class="layui-input-inline" id="season_d">
                                    <select name="season" id="season" lay-verify="required">
                                        <option value="">请选择季度</option>
                                    </select>
                                </div>
                                <div class="layui-form-mid " style="color:red">*必填项</div>
                                <label class="layui-form-label" id="sub_season_l">分期</label>
                                <div class="layui-input-inline" id="sub_season_d">
                                    <select name="subSeason" id="subSeason">
                                        <option value="">请选择分期</option>
                                    </select>
                                </div>
                                <label class="layui-form-label" id="campus_l" hidden="hidden">校区</label>
                                <div class="layui-input-inline" id="campus_d" hidden="hidden">
                                    <select name="campus" id="campus" lay-search>
                                        <option value="">请选择校区</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-timeline-content layui-text" id="div-import-teacher-and-class"
                                 hidden="hidden">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel"
                                        style="background-color: #FFB800"
                                        id="import-teacher-and-class" lay-submit lay-filter="import-teacher-and-class">
                                    <i
                                            class="layui-icon">&#xe67c;</i>导入排班信息
                                </button>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP3: 导入学生花名册（从总部软件中导出的花名册）</h3>
                                <p>这一步中，如果您是在原来的基础上更新学生名单，且有学生退班<b
                                        style="color: red;">（根据我的经验一般都会有这种情况——如果有退班的学生必须进行该操作；如果仅仅是转班或是新增，可以不用）</b>，<b
                                        style="color: red;">必须先前往：信息管理>学员信息>上课信息</b>，查询出当前<b
                                        style="color: red;">年份-季度-校区</b>的学生上课记录，全部删除后，再执行以下步骤(<b style="color: red;">或勾选'先删后导'选项，推荐！</b>)。为什么要这样做？
                                    因为系统更新的原则：对于新进班的学生执行插入；对于转班的学生执行更新；而对于退班的学生，系统无法从excel中悉知，因此需要先删除这些退班特例!</p>
                                <p>上传excel要求说明：<a
                                        href="${ctx}/toolbox/assistantAdministrator/downloadExample/3">查看范例</a></p>
                                <ul>
                                    <li>第1行为列名属性。所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                        ________________________________________<br>
                                        | 学员编号 | 姓名 | 班级编码 | 进班日期 | 业务备注 |<br>
                                        ________________________________________
                                    </li>

                                    <li>进班日期、业务备注列不是必须的。<br>
                                        导入进班日期的目的是方便根据时间排序，来制作座位表
                                    </li>
                                </ul>
                            </div>
                            <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                                <input type="checkbox" name="read_step3" id="read_step3" lay-skin="primary"
                                       lay-filter="read_step3" title=""><b style="color: red;">我已认真阅读以上内容</b>
                            </div>
                            <div class="layui-form layui-form-item layui-timeline-content" style="margin-bottom: 20px;"
                                 id="div0-import-student-and-class" hidden="hidden">
                                <label class="layui-form-label">先删后导&nbsp; <i class="layui-icon layui-icon-tips"
                                                                              lay-tips="开启'先删后导'将先删除对应年份-季度-校区的所有学生上课记录，再对导入信息执行插入。开启此选项会使导入时间变长，如果更新的上课记录中只有转班或新报班的学生，而没有退班的学生可以关闭此选项（根据我的经验学生上课情况变动会很频繁，通常会有退班的学生，所以一般都需要打开此选项）。"></i></label>
                                <div class="layui-input-inline">
                                    <input type="checkbox" name="deleteFirst2" id="deleteFirst2" lay-skin="switch"
                                           lay-text="ON|OFF"
                                           lay-filter="deleteFirst2" checked>
                                </div>
                                <label class="layui-form-label" id="manual-delete-l">手动选择删除条件&nbsp; <i
                                        class="layui-icon layui-icon-tips"
                                        lay-tips="如果使用'手动选择删除条件'，'先删'操作会删除你选择的条件下的学生上课记录；如果关闭此选项，默认取表中一条有效记录中班级所在的'年份-季度-分期-校区'作为删除条件。如果要更新的表格中含寒假或暑假多期的名单，你应该手动选择预删除的条件。"></i></label>
                                <div class="layui-input-inline" id="manual-delete-div">
                                    <input type="checkbox" name="deleteFirstCondition" id="deleteFirstCondition"
                                           lay-skin="switch"
                                           lay-text="ON|OFF"
                                           lay-filter="deleteFirstCondition" checked>
                                </div>

                            </div>
                            <div class="layui-form layui-form-item layui-timeline-content" style="margin-bottom: 20px;"
                                 id="div1-import-student-and-class" hidden="hidden">
                                <label class="layui-form-label">开课学期所在年份</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" autocomplete="off" placeholder="yyyy"
                                           id="year2" name="year2"
                                           lay-verify="required">
                                </div>
                                <div class="layui-form-mid " style="color:red">*必填项</div>
                                <label class="layui-form-label">季度</label>
                                <div class="layui-input-inline">
                                    <select name="season2" id="season2" lay-verify="required">
                                        <option value="">请选择季度</option>
                                    </select>
                                </div>
                                <div class="layui-form-mid " style="color:red">*必填项</div>
                                <label class="layui-form-label">分期</label>
                                <div class="layui-input-inline">
                                    <select name="subSeason2" id="subSeason2">
                                        <option value="">请选择分期</option>
                                    </select>
                                </div>
                                <label class="layui-form-label">校区</label>
                                <div class="layui-input-inline">
                                    <select name="campus2" id="campus2" lay-verify="required" lay-search>
                                        <option value="">请选择校区</option>
                                    </select>
                                </div>
                                <div class="layui-form-mid " style="color:red">*必填项</div>
                            </div>
                            <div class="layui-timeline-content layui-text" id="div-import-student-and-class"
                                 hidden="hidden">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel"
                                        style="background-color: #FFB800"
                                        id="import-student-and-class" lay-filter="import-student-and-class"><i
                                        class="layui-icon">&#xe67c;</i>导入学生和上课信息
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP4: 导入学生花名册（每季开课前带学生联系方式等详细信息的学生名单）</h3>
                                <p>上传excel要求说明：<a
                                        href="${ctx}/toolbox/assistantAdministrator/downloadExample/4">查看范例</a></p>
                                <ul>
                                    <li>第1行为列名属性。所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                        _____________________________<br>
                                        | 学员编号 | 姓名 | 手机 | 备用手机1 |<br>
                                        _____________________________
                                    </li>

                                    <li>“备用手机1”列不是必须的。</li>
                                </ul>
                            </div>
                            <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                                <input type="checkbox" name="read_step4" id="read_step4" lay-skin="primary"
                                       lay-filter="read_step4" title=""><b style="color: red;">我已认真阅读以上内容</b>
                            </div>
                            <div class="layui-timeline-content layui-text" id="div-import-student-detailed"
                                 hidden="hidden">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel"
                                        style="background-color: #FFB800"
                                        id="import-student-detailed" lay-filter="import-student-detailed"><i
                                        class="layui-icon">&#xe67c;</i>导入学生详细信息
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP4.5: 导入学生学校统计</h3>
                                <p>上传excel要求说明：<a
                                        href="${ctx}/toolbox/assistantAdministrator/downloadExample/6">查看范例</a></p>
                                <ul>
                                    <li>第1行为列名属性。所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
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
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <div class="layui-timeline-title">大功告成！</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
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
    }).use(['index', 'set', 'element', 'laydate'], function () {
        var $ = layui.$
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , element = layui.element
                , router = layui.router()
                , upload = layui.upload
                , laydate = layui.laydate;

        /*====================STEP1=======================*/
        //监听自动解析开关
        form.on('checkbox(read_step1)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-import-user-and-assistant").show();
            } else {
                $("#div-import-user-and-assistant").hide();
            }
        });

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
                var c = $("#read_step1").prop("checked");
                //是否同意用户协议
                if (!c) {
                    return layer.msg('你必须勾选确认阅读框！');
                }
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
                return layer.alert('导入失败!检查数据准确性？', {
                    skin: 'layui-layer-lan'
                    , closeBtn: 0
                });
            }
        });

        /*====================STEP2=======================*/
        //监听自动解析开关
        form.on('checkbox(read_step2)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div1-import-teacher-and-class").show();
                $("#div2-import-teacher-and-class").show();
                $("#campus_l").hide();
                $("#campus_d").hide();
                $("#div-import-teacher-and-class").show();
            } else {
                $("#div1-import-teacher-and-class").hide();
                $("#div2-import-teacher-and-class").hide();
                $("#div-import-teacher-and-class").hide();
            }
        });

        //监听自动解析开关
        form.on('switch(parseClassId)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#campus_l").hide();
                $("#campus_d").hide();

            } else {
                $("#campus_l").show();
                $("#campus_d").show();
            }
        });

        laydate.render({
            elem: '#year'
            , type: 'year'
        });

        var campusNames = eval('(' + '${campusNames}' + ')');
        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus").append(str);
        }

        var seansons = eval('(' + '${seasons}' + ')');
        for (var i = 0; i < seansons.length; i++) {
            var json = seansons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#season").append(str);
        }

        var subSeasons = eval('(' + '${subSeasons}' + ')');
        for (var i = 0; i < subSeasons.length; i++) {
            var json = subSeasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subSeason").append(str);
        }
        form.render('select');


        upload.render({
            elem: '#import-teacher-and-class'
            , url: '${ctx}/class/admin/import'
            , data: {
                classYear: function () {
                    return $("#year").val();
                }
                , classSeason: function () {
                    return $("#season").val();
                }
                , classSubSeason: function () {
                    return $("#subSeason").val();
                }
                , classCampus: function () {
                    return $("#campus").val();
                }
                , parseClassId: function () {
                    return $("#parseClassId").prop("checked");
                }
                , deleteFirst: function () {
                    return $("#deleteFirst1").prop("checked");
                }
                , chooseSeason: function () {
                    return $("#chooseSeason").prop("checked");
                }
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
                } else if (res.msg === "yearInvalid") {
                    return layer.alert('请选择正确的年份!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "seasonInvalid") {
                    return layer.alert('请选择正确的季度!', {
                        skin: 'layui-layer-lan'
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
                    return layer.alert('导入失败!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.alert('导入失败!检查数据准确性？', {
                    skin: 'layui-layer-lan'
                    , closeBtn: 0
                });
            }
        });

        /*====================STEP3=======================*/
        //监听自动解析开关
        form.on('checkbox(read_step3)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-import-student-and-class").show();
                $("#div0-import-student-and-class").show();
                $("#div1-import-student-and-class").show();
            } else {
                $("#div-import-student-and-class").hide();
                $("#div0-import-student-and-class").hide();
                $("#div1-import-student-and-class").hide();
            }
        });

        //监听自动解析开关
        form.on('switch(deleteFirst2)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#manual-delete-l").show();
                $("#manual-delete-div").show();
                $("#div1-import-student-and-class").show();
            } else {
                $("#manual-delete-l").hide();
                $("#manual-delete-div").hide();
                $("#div1-import-student-and-class").hide();
            }
        });

        //监听自动解析开关
        form.on('switch(deleteFirstCondition)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div1-import-student-and-class").show();
            } else {
                $("#div1-import-student-and-class").hide();
            }
        });

        laydate.render({
            elem: '#year2'
            , type: 'year'
        });


        for (var i = 0; i < campusNames.length; i++) {
            var json = campusNames[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#campus2").append(str);
        }

        for (var i = 0; i < seansons.length; i++) {
            var json = seansons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#season2").append(str);
        }

        for (var i = 0; i < subSeasons.length; i++) {
            var json = subSeasons[i];
            var str = "";
            str += '<option value="' + json + '">' + json + '</option>';
            $("#subSeason2").append(str);
        }
        form.render('select');

        upload.render({
            elem: '#import-student-and-class'
            , url: '${ctx}/student/admin/import'
            , data: {
                type: 1
                , classYear: function () {
                    return $("#year2").val();
                }
                , classSeason: function () {
                    return $("#season2").val();
                }
                , classSubSeason: function () {
                    return $("#subSeason2").val();
                }
                , classCampus: function () {
                    return $("#campus2").val();
                }
                , manualDeleteFirstCondition: function () {
                    return $("#deleteFirstCondition").prop("checked");
                }
                , deleteFirst: function () {
                    return $("#deleteFirst2").prop("checked");
                }
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
                } else if (res.msg === "yearInvalid") {
                    return layer.alert('请选择正确的年份!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "seasonInvalid") {
                    return layer.alert('请选择正确的季度!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                } else if (res.msg === "campusInvalid") {
                    return layer.alert('请选择正确的校区!', {
                        skin: 'layui-layer-lan'
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
                    return layer.alert('导入失败!', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                }
            }
            , error: function () {
                layer.closeAll('loading'); //关闭loading
                return layer.alert('导入失败!检查数据准确性？', {
                    skin: 'layui-layer-lan'
                    , closeBtn: 0
                });
            }
        });

        /*====================STEP4=======================*/
        form.on('checkbox(read_step4)', function (data) {
            //开关是否开启，true或者false
            var checked = data.elem.checked;
            if (checked) {
                $("#div-import-student-detailed").show();
            } else {
                $("#div-import-student-detailed").hide();
            }
        });


        upload.render({
            elem: '#import-student-detailed'
            , url: '${ctx}/student/admin/import'
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


        /*====================STEP4.5=======================*/
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

