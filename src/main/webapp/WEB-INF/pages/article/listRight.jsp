<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title></title>
    <style>
        .searchinput {
            font-size: 16px;
        }

        .searchsubmit {
            font-size: 16px;
            padding: 0 10px;
        }
    </style>
</head>

<body>
<aside>
    <%--搜索框--%>
    <div class="search">
        <form id="searchForm" name="searchForm" class="form-inline" role="form"
              action="<c:url value="/actions/article/list"/>" method="post">
            <label>
                <input id="searchContent" name="searchContent" type="text" placeholder="请输入关键字" class="searchinput">
            </label>
            <input type="submit" class="searchsubmit" value="查询">
        </form>
    </div>
    <div class="paihang">
        <h2>点击排行</h2>
        <ul>
            <c:forEach var="hotArticle" items="${hotArticles}">
                <li>
                    <a href="<c:url value="/actions/article/readArticle/${hotArticle.id}"/>"
                       target="_blank">${hotArticle.title}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="paihang">
        <h2>站长推荐</h2>
        <ul>
            <c:forEach items="${recommandArticles}" var="recommandArticle">
                <li>
                    <a href="<c:url value="/actions/article/readArticle/${recommandArticle.id}"/>"
                       target="_blank">${recommandArticle.title}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="paihang">
        <h2>友情链接</h2>
        <ul>
            <li><a href="http://blog.163.com/">网易博客</a></li>
            <li><a href="http://blog.sina.com.cn/">新浪博客</a></li>
            <li><a href="http://blog.sohu.com/">搜狐博客</a></li>
        </ul>
    </div>
</aside>
</body>

</html>