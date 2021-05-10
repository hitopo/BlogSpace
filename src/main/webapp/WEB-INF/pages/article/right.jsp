<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="rightbox">
    <br>
    <div class="paihang">
        <h2 class="ab_title"><a href="/">热门文章</a></h2>
        <ul>
            <c:forEach var="hotArticle" items="${hotArticles}">
                <li>
                    <p><a style="font-size:18px"
                          href="<c:url value="/actions/article/readArticle/${hotArticle.id}"/> ">${hotArticle.title}</a>
                    </p>
                </li>
            </c:forEach>
        </ul>
        <div class="ad"><img width="300" height="200" src="<c:url value="/images/shuai.JPG"/> "></div>
    </div>
    <div class="paihang">
        <h2 class="ab_title"><a>本栏推荐</a></h2>
        <ul>
            <c:forEach items="${recommandArticles}" var="recommandArticle">
                <li>
                    <b><a href="<c:url value="/actions/article/readArticle/${recommandArticle.id}"/> "
                          target="_blank">${recommandArticle.title}</a></b>
                    <p>${fn:substring(recommandArticle.brief,0,50)}...</p>
                </li>
            </c:forEach>
        </ul>
        <div class="ad"><img width="300" height="200" src="<c:url value="/images/jiaban.jpg"/> "></div>
    </div>

</div>
</body>
</html>
