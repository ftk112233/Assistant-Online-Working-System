layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form;

    form.verify({
        workId: function (value) {
            if (value.length > 32) {
                return '工号必须不能超过32个字符';
            }
        }
        , mypass: function (value) {
            if (!isPasswd(value)) {
                return '密码必须是6-20个字符(字母、数字、下划线)';
            }
        }, myemail: function (value) {
            if (value!=='' && !isEmail(value)) {
                return '不正确的邮箱格式';
            }
        }
        , myphone: function (value) {
            if (value!=='' && !isPhone(value)) {
                return '不正确的手机格式';
            }
        }
        , realName: function (value) {
            if (value === '' || value.length > 50) {
                return '真实姓名不能为空，且不能超过50个字符！';
            }
        }
        , userName: function (value) {
            if (!isUserName(value)) {
                return '用户名必须是6~20位(数字、字母、下划线)以字母开头';
            }
        }
        , idCard: function (value) {
            if (value!=='' && !isUserIdCard(value)) {
                return '不合法的身份证号！';
            }
        }, remark: function (value) {
            if (value.length > 500) {
                return '备注长度不能超过500个字符';
            }
        }
    });

    exports('myform', {})
});
