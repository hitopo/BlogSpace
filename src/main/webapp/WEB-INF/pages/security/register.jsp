<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- 重置文件 -->
    <link rel="stylesheet" href="<c:url value="/css/normalize.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/style2.css"/> ">
    <title>用户注册</title>
</head>
<body>
<div class="reg_div">
    <p>注册</p>
    <ul class="reg_ul">
        <li>
            <span>用户昵称：</span>
            <input type="text" name="nickname" placeholder="2-10个汉字加0-3位字母数字" class="reg_nickname">
            <span class="tip nickname_hint"></span>
        </li>
        <li>
            <span>登录帐号：</span>
            <input type="text" name="username" placeholder="4-16位用户名" class="reg_username">
            <span class="tip username_hint"></span>
        </li>
        <li>
            <span>密码：</span>
            <input type="password" name="password" placeholder="6-16位密码" class="reg_password">
            <span class="tip password_hint"></span>
        </li>
        <li>
            <span>确认密码：</span>
            <input type="password" name="passwordConfirm" placeholder="确认密码" class="reg_confirm">
            <span class="tip confirm_hint"></span>
        </li>
        <li>
            <span>身份证：</span>
            <input type="text" name="idCard" placeholder="身份证" class="reg_idCard">
            <span class="tip idCard_hint"></span>
        </li>
        <li>
            <span>手机号码：</span>
            <input type="text" name="mobile" placeholder="手机号" class="reg_mobile">
            <span class="tip mobile_hint"></span>
        </li>
        <li>
            <button type="button" name="button" class="red_button">注册</button>
        </li>
    </ul>
</div>

<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/> "></script>
<script type="text/javascript">
    // 用户注册前端验证
    var isNicknameOk = false;
    var isUsernameOk = false;
    var isPasswordOk = false;
    var isConfirmOk = false;
    var isIdCardOk = false;
    var isMobileOk = false;

    $('.reg_nickname').blur(function () {
        //汉字正则,2到10位汉字 + 0-3为字母数字
        if ((/^[\u4e00-\u9fa5]{2,10}[a-zA-Z0-9]{0,3}$/).test($(".reg_nickname").val())) {
            // 格式符合要求再发送检查是否重复
            //ajax 检查昵称是否重复
            $.ajax({
                type: "POST",
                url: "<c:url value="/actions/security/nameCheck"/>",
                data: {"nickname": $(".reg_nickname").val()},
                success: function (data) {
                    if (data.success) {
                        $('.nickname_hint').html("✔").css("color", "green");
                        isNicknameOk = true;
                    } else if (data.reason == "用户昵称已存在") {
                        $('.nickname_hint').html("×用户昵称已存在").css("color", "red");
                        isNicknameOk = false;
                    }
                },
                error: function () {
                    alert('服务器错误');
                }
            });
        } else {
            $('.nickname_hint').html("×昵称格式不符合要求").css("color", "red");
            isNicknameOk = false;
        }

    });

    //username
    $('.reg_username').blur(function () {
        if ((/^[a-zA-Z0-9_-]{4,16}$/).test($(".reg_username").val())) {
            //格式符合要求在发送用户名检测
            //ajax 检查昵称是否重复
            $.ajax({
                type: "POST",
                url: "<c:url value="/actions/security/nameCheck"/>",
                data: {"username": $(".reg_username").val()},
                success: function (data) {
                    if (data.success) {
                        $('.username_hint').html("✔").css("color", "green");
                        isUsernameOk = true;
                    } else if (data.reason == "用户名已存在") {
                        $('.username_hint').html("×用户名已存在").css("color", "red");
                        isUsernameOk = false;
                    }
                },
                error: function () {
                    alert('服务器错误');
                }
            });

        } else {
            $('.username_hint').html("×用户名格式不符合要求").css("color", "red");
            isUsernameOk = false;
        }
    });

    // password
    $('.reg_password').blur(function () {
        if ((/^[a-z0-9_-]{6,16}$/).test($(".reg_password").val())) {
            $('.password_hint').html("✔").css("color", "green");
            isPasswordOk = true;
        } else {
            $('.password_hint').html("×密码不符合格式要求").css("color", "red");
            isPasswordOk = false;
        }
    });

    // password_confirm
    $('.reg_confirm').blur(function () {
        if (($(".reg_password").val()) == ($(".reg_confirm").val())) {
            $('.confirm_hint').html("✔").css("color", "green");
            isConfirmOk = true;
        } else {
            $('.confirm_hint').html("×两次密码不一致").css("color", "red");
            isConfirmOk = false;
        }
    });

    // IdCard
    $('.reg_idCard').blur(function () {
        if ((/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/).test($(".reg_idCard").val())) {
            $('.idCard_hint').html("✔").css("color", "green");
            isIdCardOk = true;
        } else {
            $('.idCard_hint').html("×身份证格式不符合要求").css("color", "red");
            isIdCardOk = false;
        }
    });

    // Mobile
    $('.reg_mobile').blur(function () {
        if ((/^1[34578]\d{9}$/).test($(".reg_mobile").val())) {
            $('.mobile_hint').html("✔").css("color", "green");
            isMobileOk = true;
        } else {
            $('.mobile_hint').html("×手机号格式不符合要求").css("color", "red");
            isMobileOk = false;
        }
    });

    // 点击注册按钮
    $('.red_button').click(function () {
        var nickname = $('.reg_nickname').val();
        var username = $('.reg_username').val();
        var password = $('.reg_password').val();
        var idCard = $('.reg_idCard').val();
        var mobile = $('.reg_mobile').val();
        if (!(isNicknameOk && isUsernameOk && isPasswordOk && isConfirmOk && isIdCardOk && isMobileOk)) {
            alert("请完善相关注册信息");
            return;
        }
        $.ajax(
            {
                type: "post",
                url: "<c:url value="/actions/security/registerSubmit"/>",
                dataType: 'json',
                data: {
                    "nickname": nickname, "loginName": username,
                    "idCard": idCard, "cellPhone": mobile,
                    "password": password, "enabled": true
                },
                success: function (data) {
                    if (data.success) {
                        alert("注册成功!");
                        window.location.href = "<c:url value="/"/>";
                    } else {
                        alert("注册失败，" + data.reason);
                    }
                },
                error: function () {
                    window.alert("系统内部出错！");
                }
            });
    });
</script>

</body>
</html>
