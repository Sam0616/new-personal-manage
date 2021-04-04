var message1 = 1;
var message2 = 1;


function checkLoginname() {
    //先用js代码做校验,如果校验通过再ajax向服务器提交数据
    var loginname = document.getElementById("loginname").value;
    $.ajax({
        url: "checkLoginName",
        type: "post",
        data: {"loginname": loginname},
        dataType: "text",
        beforeSend: function () {
        },
        success: function (message) {
            message1 = message;
            if (message != "") {
                document.getElementById("loginname").focus();
                $('#loginname')[0].style.border = "1px solid rgb(229,58,49)";
            } else {
                $('#loginname')[0].style.border = "1px solid #ccc";
            }
            document.getElementById("loginname_message").value = message;
        }
    });
}


function checkUsername() {
    //先用js代码做校验,如果校验通过再进行ajax想服务器发送数据
    //其实可以通过用户名去查询手机号码，如果相等，就返回空消息，否则就提示用户用户名和密码不匹配
    var username = document.getElementById("username").value;
    var loginname = document.getElementById("loginname").value;
    $.ajax({
        url: "checkUsername",
        type: "post",
        data: {"username": username, "loginname": loginname},
        dataType: "text",
        beforeSend: function () {
        },
        success: function (message) {
            message2 = message;
            if (message != "") {
                document.getElementById("username").focus();
                $('#username')[0].style.border = "1px solid rgb(229,58,49)";
            } else {
                $('#username')[0].style.border = "1px solid #ccc";
            }
            document.getElementById("username_message").value = message;
        }
    });
}


function tofind() {


    // var message3=document.getElementById("user_input_verifyCode").value;
    // if(message1==""&& message2==""&& message3!=""){
    // 	 $("#toPasswordForm").submit();
    // }else if(message1!=""&& message2!="" && message3==""){
    // 	  document.getElementById("loginname").focus();
    // 	  $('#loginname')[0].style.border="1px solid rgb(229,58,49)";
    // 	  $('#username')[0].style.border="1px solid rgb(229,58,49)";
    // 	  $('#user_input_verifyCode')[0].style.border="1px solid rgb(229,58,49)";
    // 	document.getElementById("find_message").value = "请输入您的相关信息";
    // }else if(message1==""&& message2!="" && message3!=""){
    // 	  document.getElementById("username").focus();
    // 	  $('#username')[0].style.border="1px solid rgb(229,58,49)";
    // 	  $('#user_input_verifyCode')[0].style.border="1px solid rgb(229,58,49)";
    // 	  document.getElementById("find_message").value = "请输入您的用户名";
    // }else if(message1==""&& message2=="" && message3==""){
    // 	  document.getElementById("user_input_verifyCode").focus();
    // 	  $('#user_input_verifyCode')[0].style.border="1px solid rgb(229,58,49)";
    // 	  document.getElementById("find_message").value = "请输入校验码";
    // }
}


function toUpdata() {

    // $("form").submit();

    var password = document.getElementById("password").value;
    var repassword = document.getElementById("repassword").value;

    if (password ==repassword ) {
//验证密码是不是6到16位的数字密码组合
        var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/
        var re = new RegExp(reg)
        if (re.test(password)) {
            $("#rePasswordForm").submit();
        } else {
            document.getElementById("password").focus();
            $('#password')[0].style.border = "1px solid rgb(229,58,49)";
            $('#repassword')[0].style.border = "1px solid rgb(229,58,49)";
            document.getElementById("repassword_message").value = "密码不符合规范！";
        }
    } else {
        document.getElementById("password").focus();
        $('#password')[0].style.border = "1px solid rgb(229,58,49)";
        $('#repassword')[0].style.border = "1px solid rgb(229,58,49)";
        document.getElementById("repassword_message").value = "两次密码输入不一致";
    }
}

