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
    <title>用户登录</title>
</head>
<body>
<div class="reg_div" style="width: 500px;">
    <p>用户登录</p>
    <ul class="reg_ul">
        <li>
            <span>登录帐号：</span>
            <input type="text" name="username" class="reg_username">
            <span class="tip username_hint"></span>
        </li>
        <li>
            <span>密码：</span>
            <input type="password" name="password" class="reg_password">
            <span class="tip password_hint"></span>
        </li>
        <li>
            <button type="button" id="loginBtn" name="button" class="red_button">登录</button>
            <button type="button" id="regBtn" name="button" class="red_button">注册</button>
        </li>
    </ul>
</div>

<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/> "></script>
<script type="text/javascript">

    // 点击登录按钮
    $('#loginBtn').click(function () {
        var username = $('.reg_username').val();
        var password = $('.reg_password').val();
        if ((username == null || password == null)) {
            alert("请填入相关信息！");
            return;
        }
        if (username == '' || password == '') {
            alert("请填入相关信息！");
            return;
        }
        // 将用户名和密码转换成json对象传递给后端
        var varJson = {
            "loginName": username,
            "password": password
        };
        //这里是登录页面，上面已经获取到了用户名与密码。
        //然后通过ajax方式发送给后端
        //在AccountSecurityController中的accountLogin方法上
        $.ajax(
            {
                type: "post",
                url: "<c:url value="/actions/security/accountLogin"/>",
                dataType: 'json',
                data: varJson,
                success: function (data) {
                    if (data.success) {
                        window.location.href = "<c:url value="/actions/article/list"/>";
                    } else {
                        alert("登录失败,原因：" + data.reason);
                    }
                },
                //如果ajax错误，那么提示系统出错
                error: function () {
                    window.alert("系统内部出错！");
                }
            });

    });

    //点击注册按钮，转到注册页面
    $('#regBtn').click(function () {
        window.location.href = '<c:url value="/actions/security/register"/>';
    });
</script>

</body>
</html>
