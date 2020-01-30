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
        , assistantWorkId: function (value) {
            if (value.length > 32) {
                return '工号不能超过32个字符';
            }
        }
        , mypass: function (value) {
            if (!isPasswd(value)) {
                return '密码必须是6-20个字符(字母、数字、下划线)';
            }
        }, myemail: function (value) {
            if (value !== '' && !isEmail(value)) {
                return '不正确的邮箱格式';
            }
        }
        , myphone: function (value) {
            if (value !== '' && !isPhone(value)) {
                return '不正确的手机格式';
            }
        }
        , realName: function (value) {
            if (!(value !== '' && value.length <= 50)) {
                return '姓名不能为空，且不能超过50个字符！';
            }
        }
        , userName: function (value) {
            if (!isUserName(value)) {
                return '用户名必须是6~20位(数字、字母、下划线)以字母开头';
            }
        }
        , idCard: function (value) {
            if (value !== '' && !isUserIdCard(value)) {
                return '不合法的身份证号！';
            }
        }, remark: function (value) {
            if (value.length > 500) {
                return '备注长度不能超过500个字符';
            }
        }, perm: function (value) {
            if (!(value !== '' && value.length <= 100)) {
                return '权限不能为空，且长度不能超过100';
            }
        }, depart: function (value) {
            if (value.length > 20) {
                return '部门长度不能超过20个字符';
            }
        }, campus: function (value) {
            if (value.length > 20) {
                return '校区长度不能超过20个字符';
            }
        }
        , realNamePermNull: function (value) {
            if (value.length > 50) {
                return '姓名不能不能超过50个字符！';
            }
        }
        , classId: function (value) {
            if (!(value !== '' && value.length <= 32)) {
                return '班级编码不能为空，且不能超过32个字符！';
            }
        }
        , className: function (value) {
            if (value.length > 50) {
                return '班级名称不能不能超过50个字符！';
            }
        }
        , classTime: function (value) {
            if (value.length > 50) {
                return '上课时间不能超过50个字符！';
            }
        }
        , classTeacherRequirement: function (value) {
            if (value.length > 100) {
                return '任课教师要求长度不能超过200个字符';
            }
        }
        , classTimes: function (value) {
            if (!(value === '' || isDigit(value) && value <= 100)) {
                return '班级上课次数不合法';
            }
        }
        , studentId: function (value) {
            if (!(value !== '' && value.length <= 32)) {
                return '学员号不能为空，且不能超过32个字符！';
            }
        }
        , studentName: function (value) {
            if (!(value !== '' && value.length <= 50)) {
                return '学员姓名不能为空，且不能超过50个字符！';
            }
        }
        , studentPhone: function (value) {
            //学员联系方式不做手机正则校验
            if (value !== '' && value.length !== 11) {
                return '不正确的手机格式';
            }
        }
        , school: function (value) {
            if (value.length > 50) {
                return '学校不能超过50个字符';
            }
        }, missLessonStudentId: function (value) {
            if (value.length > 32) {
                return '补课学生学员号不能超过32个字符';
            }
        }, content: function (value) {
            if (!(value !== '' && value.length <= 500)) {
                return '问题内容不能为空，且不能超过500个字符';
            }
        }
        , answer: function (value) {
            if (!(value !== '' && value.length <= 100)) {
                return '问题答案1不能为空，且不能超过100个字符';
            }
        }
        , answer2: function (value) {
            if (value.length > 100) {
                return '问题答案2不能超过100个字符';
            }
        }
        , titleOfUsefulInformation: function (value) {
            if (!(value !== '' && value.length <= 100)) {
                return '主题不能为空，且不能超过100个字符';
            }
        }
        , contentOfUsefulInformation: function (value) {
            if (!(value !== '' && value.length <= 200)) {
                return '内容不能为空，且不能超过200个字符';
            }
        }
        , sequence: function (value) {
            if (!(value !== '' && isDigit(value) && value.length <= 10)) {
                return '序号值不能为空，且必须是数字';
            }
        }
        , contentOfMessage: function (value) {
            if (!(value !== '' && value.length <= 1000)) {
                return '内容不能为空，且不能超过1000个字符';
            }
        }
    });

    exports('myform', {})
});
