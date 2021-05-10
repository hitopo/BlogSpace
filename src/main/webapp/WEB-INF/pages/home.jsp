<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员登陆</title>
    <link rel="stylesheet" media="screen" href="<c:url value="/css/loginstyle.css"/>">
    <link rel="stylesheet" media="screen" href="<c:url value="/css/reset.css"/>">
</head>
<body>
<div id="particles-js">
    <form id="loginForm" action="<c:url value="/actions/login/admin"/>" method="post">
        <div class="login">
            <div class="login-top">
                博客后台管理系统登录
            </div>
            <div class="login-center clearfix">
                <div class="login-center-img"><img src="<c:url value="/images/name.png"/>"/></div>
                <div class="login-center-input">
                    <input type="text" name="loginName" placeholder="请输入您的用户名" onfocus="this.placeholder=''"
                           onblur="this.placeholder='请输入您的用户名'"/>
                    <div class="login-center-input-text">用户名</div>
                </div>
            </div>
            <div class="login-center clearfix">
                <div class="login-center-img"><img src="<c:url value="/images/password.png"/> "></div>
                <div class="login-center-input">
                    <input type="password" name="password" placeholder="请输入您的密码" onfocus="this.placeholder=''"
                           onblur="this.placeholder='请输入您的密码'"/>
                    <div class="login-center-input-text">密码</div>
                </div>
            </div>
            <div class="login-button">
                登录
            </div>
            <div class="sk-rotating-plane"></div>
        </div>
    </form>
</div>

<!-- scripts -->
<script src="<c:url value="/js/particles.min.js"/>"></script>
<script src="<c:url value="/js/app.js"/>"></script>
<script type="text/javascript">
    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }

    function addClass(ele, cls) {
        if (!hasClass(ele, cls)) {
            ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
        }
    }

    function removeClass(ele, cls) {
        if (hasClass(ele, cls)) {
            var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            ele.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }

    //点击登录按钮
    document.querySelector(".login-button").onclick = function () {
        addClass(document.querySelector(".login"), "active");
        setTimeout(function () {
            addClass(document.querySelector(".sk-rotating-plane"), "active");
            document.querySelector(".login").style.display = "none"
        }, 500);
        setTimeout(function () {
            removeClass(document.querySelector(".login"), "active");
            removeClass(document.querySelector(".sk-rotating-plane"), "active")
            document.querySelector(".login").style.display = "block";
        }, 3000);
        document.getElementById("loginForm").submit();
    }

</script>
</body>
</html>
