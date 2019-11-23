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
                <div class="layui-card-header">信息导入四步走。<a style="color:red">助教信息、学员名单、排班信息的导入必须按顺序执行，重要！</a></div>
                <div class="layui-card-body">
                    <ul class="layui-timeline">
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">STEP1: 助教和用户导入</h3>
                                <p>上传excel要求说明：<a href="${ctx}/toolbox/assistantAdministrator/downloadExample/1">查看范例</a> </p>
                                <ul>
                                    <li>第1行、第2行与导入无关。有效内容从第3行开始，第3行为列名属性：序号、部门、校区、姓名、员工号等......</li>
                                    <li>第3行所有列名属性中系统将读取以下名称的列导入数据库，这些列的先后顺序无关，但列名称必须与要求相符（如下所示）！！<br>
                                        ___________________________________________<br>
                                        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        上海新东方劳务明细表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
                                        ___________________________________________<br>
                                        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        日期：XXXX年XX月XX日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
                                        ___________________________________________<br>
                                        | 部门 | 校区 | 姓名 | 员工号 | 身份证号 | 手机号 | 备注 |</li>
                                        ___________________________________________<br>

                                    <li>第四行开始是数据:<br>
                                        1)员工号必须唯一且准确<br>
                                        2)身份证号必须唯一且准确<br>
                                        3)手机号必须唯一且准确<br>
                                    4)<b style="color:red">确保您当前要导入校区的助教信息表中没有姓名相同的助教，否则请联系系统管理员</b></li>
                                    <li>导入成功后：<br>
                                        1)姓名导入助教表中时，对于重名助教会在重名的名字后面加'1'以区别<br>
                                        2)导入用户后，密码默认初始化为身份证，用以初次登陆<br></li>
                                </ul>
                                <p>导入成功后：</p>
                                <ul>
                                    <li>姓名导入助教表中时，考虑到各校区助教可能有重名的情况，对于重名助教会在重名的名字后面加'1'以区别。
                                        应该前往助教管理页面做<a style="color:red" lay-href="${ctx}/assistant/admin/page" lay-text="助教信息">重名检查</a>：搜索条件中在姓名栏输入'1'，点击查询按钮，查看是否有带'1'名字的助教，无即表示无重名助教。</li>
                                    <li>导入用户后，助教初次登陆使用如下用户名密码：<br>
                                        1)用户名：身份证号或手机号<br>
                                        2)密码：身份证号
                                    </li>
                                </ul>
                            </div>
                            <div class="layui-form layui-timeline-content" style="margin-bottom: 20px;">
                                <input type="checkbox" name="read_step1" id="read_step1" lay-skin="primary" lay-filter="read_step1" title=""><b style="color: red;">我已阅读以上内容</b>
                            </div>
                            <div class="layui-timeline-content layui-text" id="div-import-user-and-assistant" hidden="hidden">
                                <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #FFB800"
                                        id="import-user-and-assistant" lay-filter="import-user-and-assistant"><i class="layui-icon">&#xe67c;</i>导入用户和助教
                            </div>

                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">12月25日</h3>
                                <p>又是一年 <em>“圣诞节”</em>，2018 向我们正在走来</p>
                                <ul>
                                    <li>叮叮当，叮叮当，铃儿响叮当</li>
                                    <li>今晚滑雪多快乐，我们坐在雪橇上</li>
                                </ul>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">12月24日</h3>
                                <p>
                                    美丽的夜晚，都是祈祷的人们。<br>他们等待着第二天，收到圣诞老人的礼物。<br><br>
                                </p>
                                <blockquote class="layui-elem-quote">平安夜，天赐平安。愿真善美的事物都能永恒。</blockquote>
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
                        return layer.msg('导入成功', {
                            icon: 1
                            , time: 1000
                        });
                    } else {
                        return layer.msg('导入失败', {
                            offset: '15px'
                            , icon: 2
                            , time: 2000
                        });
                    }
                }
                , error: function () {
                    layer.closeAll('loading'); //关闭loading
                    return layer.msg('导入失败', {
                        offset: '15px'
                        , icon: 2
                        , time: 2000
                    });
                }
            });


    });
</script>
</body>
</html>

