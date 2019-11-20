<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
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
                <div class="layui-card-header">设置我的基本资料
                </div>
                <div class="layui-card-body" pad15>
                    <div>当前账号安全指数评估
                        <i class="layui-icon layui-icon-tips" lay-tips="通过绑定身份证、安全邮箱、手机可以提升安全指数"></i>
                    </div>
                    <div class="layui-progress layui-progress-big" lay-filter="p0" id="p0" lay-showPercent="yes"
                         style="display:none">
                        <div class="layui-progress-bar layui-bg-red" lay-percent="10%"></div>
                    </div>
                    <div class="layui-progress layui-progress-big" lay-filter="p1" id="p1" lay-showPercent="yes"
                         style="display:none">
                        <div class="layui-progress-bar layui-bg-red" lay-percent="33%"></div>
                    </div>
                    <div class="layui-progress layui-progress-big" lay-filter="p2" id="p2" lay-showPercent="yes"
                         style="display:none">
                        <div class="layui-progress-bar layui-bg-orange" lay-percent="67%"></div>
                    </div>
                    <div class="layui-progress layui-progress-big" lay-filter="p3" id="p3" lay-showPercent="yes"
                         style="display:none">
                        <div class="layui-progress-bar layui-bg-green" lay-percent="100%"></div>
                    </div>
                </div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div><input type="text" name="userId" id="userId" style="display: none"
                                    value="${userInfo.id!""}"/></div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">工号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="workId" value="${userInfo.userWorkId!""}"
                                       class="layui-input" lay-verify="workId" lay-verType="tips" readonly>
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="workId-flag">工号不可修改</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="username" value="${userInfo.userName!""}"
                                       class="layui-input" lay-verify="userName" lay-verType="tips">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="username-flag">必须是6-20个字符(字母、数字、下划线)，且以字母开头
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="realName" value="${userInfo.userRealName!""}"
                                       class="layui-input" lay-verify="realName" lay-verType="tips">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="idCard-flag">推荐使用真实姓名，请谨慎修改哦</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">身份证</label>
                            <div class="layui-input-inline">
                                <input type="text" name="idCard" value="${userInfo.userIdCard!""}"
                                       class="layui-input" lay-verify="idCard" lay-verType="tips">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="idCard-flag">请谨慎修改哦</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">我的角色</label>
                            <div class="layui-input-inline">
                                <input type="text" name="role" value="${userInfo.userRole!""}"
                                       class="layui-input" lay-verify="required" lay-verType="tips"readonly>
                            </div>
                            <div class="layui-form-mid layui-word-aux">
                                <i class="layui-icon layui-icon-tips" lay-tips="用户角色权限解释：<br>
                                                                                 管理员>学管>助教长>助教=教师>游客。<br>
                                                                                1、游客：只有学员上课信息和学员补课信息的查询权限，没有使用百宝箱、编辑资料等权限<br>
                                                                                2、助教&教师：百宝箱助教区的全部功能，除用户管理外的所有信息查询权限，没有编辑权限。<br>
                                                                                3、助教长：百宝箱助教区的全部功能，除用户管理外的所有信息查询、编辑权限<br>
                                                                                4、学管：百宝箱的全部功能，用户管理（不能编辑管理员信息）及所有信息查询、编辑权限<br>
                                                                                5、管理员：系统的全部权限"></i>
                                &nbsp;角色不可修改</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">修改头像</label>
                            <div class="layui-input-inline uploadHeadImage">
                                <div class="layui-upload-drag" id="headImg">
                                    <i class="layui-icon"></i>
                                    <p>点击上传图片，或将图片拖拽到此处</p>
                                </div>
                            </div>
                            <div class="layui-input-inline">
                                <div class="layui-upload-list">
                                    <img class="layui-upload-img headImage"
                                         src="${ctx}/user/showIcon?userIcon=${userInfo.userIcon!""}" id="demo1"
                                         height="110">
                                    <p id="demoText">当前头像↑</p>
                                </div>
                            </div>
                            <div><input type="text" id="hiddenIconUrl" style="display: none" name="hiddenIconUrl"
                                        value=""/></div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="email" id="hiddenUserEmail" value="${userInfo.userEmail!""}"
                                       readonly
                                       class="layui-input" lay-verify="myemail" lay-verType="tips">
                            </div>
                            <div class="layui-form-mid layui-word-aux">
                                <i class="layui-icon layui-icon-ok" style="font-size: 15px; color: #5FB878;" id="email-icon-ok" hidden="hidden"> 已绑定</i>
                                <i class="layui-icon layui-icon-close-fill" style="font-size: 15px; color: #FF5722;" id="email-icon-close" hidden="hidden">  未绑定</i>
                                <a href="${ctx}/user/setEmail">&nbsp;前往绑定/修改邮箱?</a></div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机</label>
                            <div class="layui-input-inline">
                                <input type="text" name="cellphone" value="${userInfo.userPhone!""}" lay-verify="myphone"
                                       lay-verType="tips"   autocomplete="off" class="layui-input" readonly>
                            </div>
                            <div class="layui-form-mid layui-word-aux">
                                <i class="layui-icon layui-icon-ok" style="font-size: 15px; color: #5FB878;" id="phone-icon-ok" hidden="hidden"> 已绑定</i>
                                <i class="layui-icon layui-icon-close-fill" style="font-size: 15px; color: #FF5722;" id="phone-icon-close" hidden="hidden">  未绑定</i>
                                <a href="${ctx}/user/setPhone"
                            >&nbsp;前往绑定/修改手机?</a></div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmyinfo" id="my_button">确认修改</button>
                            </div>
                        </div>
                    </div>
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
    }).use(['index', 'user', 'upload', 'form', 'layer', 'element'], function () {
        var $ = layui.$
                , element = layui.element
                , setter = layui.setter
                , admin = layui.admin
                , form = layui.form
                , router = layui.router()
                , search = router.search
                , upload = layui.upload;

        var secure = ['${userInfo.userIdCard!""}', '${userInfo.userEmail!""}', '${userInfo.userPhone!""}'];
        console.log(secure);
        var secureParam = 0;
        for (var j = 0; j < secure.length; j++) {
            if (secure[j] !== '') {
                secureParam++
            }
        }
        console.log(secureParam);
        if (secureParam === 0) {
            $("#p0").attr("style", "display:block;");
            $("#p1").attr("style", "display:none;");
            $("#p2").attr("style", "display:none;");
            $("#p3").attr("style", "display:none;");
        } else if (secureParam === 1) {
            $("#p0").attr("style", "display:none;");
            $("#p1").attr("style", "display:block;");
            $("#p2").attr("style", "display:none;");
            $("#p3").attr("style", "display:none;");
        } else if (secureParam === 2) {
            $("#p0").attr("style", "display:none;");
            $("#p1").attr("style", "display:none;");
            $("#p2").attr("style", "display:block;");
            $("#p3").attr("style", "display:none;");
        } else if (secureParam === 3) {
            $("#p0").attr("style", "display:none;");
            $("#p1").attr("style", "display:none;");
            $("#p2").attr("style", "display:none;");
            $("#p3").attr("style", "display:block;");
        }


        if ('${userInfo.userEmail!""}' === ''){
            $("#email-icon-ok").hide();
            $("#email-icon-close").show();
        } else {
            $("#email-icon-ok").show();
            $("#email-icon-close").hide();
        }

        if ('${userInfo.userPhone!""}' === ''){
            $("#phone-icon-ok").hide();
            $("#phone-icon-close").show();
        } else {
            $("#phone-icon-ok").show();
            $("#phone-icon-close").hide();
        }

        /***************************************************/
        //拖拽上传
        var uploadInst = upload.render({
            elem: '#headImg'
            , url: '${ctx}/user/uploadUserIcon'
            , size: 2048 //KB
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                console.log(res.code);
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
                //打印后台传回的地址: 把地址放入一个隐藏的input中, 和表单一起提交到后台,
                // console.log(res.data.src);
                $("#hiddenIconUrl").val(res.data.src);
                $('#demoText').html('<span style="color: #8f8f8f;">上传成功!!!请点击确认修改即可完成最终更改!</span>');
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
        /*************************************************************/


        //提交
        form.on('submit(setmyinfo)', function (obj) {
            var field = obj.field;

            //禁用5秒
            disabledSubmitButtonWithTime('my_button', '确认修改', 5);

            $.ajax({
                type: 'post',
                data: {
                    "id": field.userId,
                    "userIdCard": field.idCard,
                    "userName": field.username,
                    "userRealName": field.realName,
                    "userIcon": field.hiddenIconUrl
                },
                url: '${ctx}/user/updateOwnInfo' //实际使用请改成服务端真实接口
                , success: function (res2) {
                    if (res2.data === "updateSuccess") {
                        layer.msg('修改已完成，请F5刷新页面', {
                            icon: 1
                            , time: 1000
                        }, function () {
                            location.href = "${ctx}/user/setInfo"
                        });
                    } else if (res2.data === "userNameExist") {
                        layer.msg('该用户名已存在', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res2.data === "userIdCardExist") {
                        layer.msg('该身份证已被注册', {
                            icon: 5,
                            anim: 6
                        });
                    } else {
                        layer.msg('未知错误', {
                            icon: 5,
                            anim: 6
                        });
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
