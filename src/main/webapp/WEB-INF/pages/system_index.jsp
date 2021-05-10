<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="commons/header.jsp"/>
    <title>博客管理系统</title>
</head>
<body>
<%--后台主登录页面--%>
<!--主体内容-->
<section class="publicMian">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <c:forEach items="${menus}" var="menu">
                    <li>
                        <a href="<c:url value="${menu.uri}"/>">${menu.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
    <div class="right">
        <img class="wColck" src="<c:url value="/img/clock.jpg"/>" alt=""/>
        <div class="wFont">
            <h2>${account.nickname}</h2>
            <p>欢迎来到博客后台管理系统!</p>
        </div>
    </div>
</section>
<jsp:include page="commons/footer.jsp"/>

</body>
</html>
