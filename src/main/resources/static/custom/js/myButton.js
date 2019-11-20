/*按钮禁用?秒*/
function disabledSubmitButton(submitButtonName, time) {
    $("#" + submitButtonName).attr({"disabled": "disabled"});     //控制按钮为禁用
    var timeoutObj = setTimeout(function () {
        $("#" + submitButtonName).removeAttr("disabled");//将按钮可用
        /* 清除已设置的setTimeout对象 */
        clearTimeout(timeoutObj)
    }, time * 1000);
}

/*按钮禁用?秒,并显示倒计时*/
function disabledSubmitButtonWithTime(submitButtonName, submitButtonText, time) {
    $("#" + submitButtonName).attr({"disabled": "disabled"});     //控制按钮为禁用
    var second = time;
    var intervalObj = setInterval(function () {
        $("#" + submitButtonName).text(second + "秒后重试");
        if (second == 0) {
            $("#" + submitButtonName).text(submitButtonText);
            $("#" + submitButtonName).removeAttr("disabled");//将按钮可用
            /* 清除已设置的setInterval对象 */
            clearInterval(intervalObj);
        }
        second--;
    }, 1000);
}