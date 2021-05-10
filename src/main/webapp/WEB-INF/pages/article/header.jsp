<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<header>
    <nav>
        <ul>
            <li><a href="<c:url value="/"/> ">网站首页</a></li>

            <c:if test="${account!=null}">
                <li>
                    <a href="<c:url value="/actions/article/myBlogSpace"/> ">个人中心</a>
                </li>
            </c:if>

            <c:choose>
                <c:when test="${account==null}">
                    <li>
                        <a href="<c:url value="/actions/security/login"/> ">登录</a>
                    </li>
                </c:when>
            </c:choose>

            <li>
                <c:choose>
                    <c:when test="${account==null}">
                        <a href="<c:url value="/actions/security/register"/> ">注册</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/actions/article/postedit"/> ">发表博客</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <c:if test="${account!=null}">
                <li><a href="<c:url value="/actions/home/logout"/> ">退出</a></li>
            </c:if>
            <li><a href="<c:url value="/actions/home"/> ">后台管理中心</a></li>
        </ul>
    </nav>
</header>
<body>

</body>
</html>
