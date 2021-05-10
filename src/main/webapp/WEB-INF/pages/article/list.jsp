<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>网络博客</title>
    <jsp:include page="baseStyle.jsp"/>
    <jsp:include page="header.jsp"/>
    <link href="<c:url value="/css/mod.css"/>" rel="stylesheet">

    <style>
        .pagination {
            display: inline-block;
            padding-left: 0;
            margin: 20px 0;
            border-radius: 4px
        }

        .pagination > li {
            display: inline
        }

        .pagination > li > a, .pagination > li > span {
            position: relative;
            float: left;
            padding: 6px 12px;
            margin-left: -1px;
            line-height: 1.42857143;
            color: #337ab7;
            text-decoration: none;
            background-color: #fff;
            border: 1px solid #ddd
        }

        .pagination > li:first-child > a, .pagination > li:first-child > span {
            margin-left: 0;
            border-top-left-radius: 4px;
            border-bottom-left-radius: 4px
        }

        .pagination > li:last-child > a, .pagination > li:last-child > span {
            border-top-right-radius: 4px;
            border-bottom-right-radius: 4px
        }

        .pagination > li > a:focus, .pagination > li > a:hover, .pagination > li > span:focus, .pagination > li > span:hover {
            z-index: 2;
            color: #23527c;
            background-color: #eee;
            border-color: #ddd
        }

        .pagination > .active > a, .pagination > .active > a:focus, .pagination > .active > a:hover, .pagination > .active >
        span, .pagination > .active > span:focus, .pagination > .active > span:hover {
            z-index: 3;
            color: #fff;
            cursor: default;
            background-color: #337ab7;
            border-color: #337ab7
        }

        .pagination > .disabled > a, .pagination > .disabled > a:focus, .pagination > .disabled > a:hover, .pagination >
        .disabled > span, .pagination > .disabled > span:focus, .pagination > .disabled > span:hover {
            color: #777;
            cursor: not-allowed;
            background-color: #fff;
            border-color: #ddd
        }
    </style>
</head>

<body>
<article>
    <div class="blogs">
        <c:forEach var="article" items="${articles}" varStatus="s">
            <div class="bloglist">
                <!-- 文章标题 -->
                <h2><a href="<c:url value="/actions/article/readArticle/${article.id}"/>"
                       target="_blank">${article.title}</a></h2>
                <!-- 文章摘要 -->
                <p>${article.brief}</p>
                <div class="autor"><span class="lm f_l"><a href="#">${article.author}</a></span><span
                        class="dtime f_l">${article.time}</span><span class="viewnum f_l">浏览（<a
                        href="#">${article.click}</a>）</span><span class="pingl f_l">评论（<a
                        href="#">${article.comments.size()}</a>）</span>
                </div>
            </div>
        </c:forEach>

        <%-- 有搜索结果显示出来，没有就显示提示用户没有搜索结果--%>
        <c:choose>
            <c:when test="${not empty articles}">
                <%-- 分页代码 --%>
                <%--这里使用的是bootstrap分页插件--%>
                <div style="text-align: center">
                    <ul class="pagination">
                        <li
                                <c:if test="${pageInfo.pageNum==1}">
                                    class="disabled"
                                </c:if>>
                            <a href="<c:url value="/actions/article/list?pageNo=1"/> ">&laquo;</a>
                        </li>
                        <li>
                            <!--如果当前页数是第一页，那么点上一页仍然是当前页。 -->
                            <a href="<c:url value="/actions/article/list?pageNo=${pageInfo.pageNum==1?pageInfo.pageNum:pageInfo.pageNum-1}"/> ">上一页</a>
                        </li>
                        <!--判断最大页数是否超过X，如果超过X则是X，否则是最大页数。防止分页信息过长 -->
                        <c:forEach begin="1" end="${pageInfo.pages>8?8:pageInfo.pages}" step="1" var="pageNo">
                            <li
                                    <c:if test="${pageInfo.pageNum==pageNo}">
                                        class="active"
                                    </c:if>>
                                <a href="<c:url value="/actions/article/list?pageNo=${pageNo}"/> ">${pageNo}</a>
                            </li>
                        </c:forEach>
                        <li>
                            <!--如果当前页数是最后一页，那么点击下一页仍然是当前页。 -->
                            <a href="<c:url value="/actions/article/list?pageNo=${pageInfo.pageNum==pageInfo.pages?pageInfo.pageNum:pageInfo.pageNum+1}"/> ">下一页</a>
                        </li>
                        <li
                                <c:if test="${pageInfo.pageNum==pageInfo.pages}">
                                    class="disabled"
                                </c:if>>
                            <a href="<c:url value="/actions/article/list?pageNo=${pageInfo.pages}"/> ">&raquo;</a>
                        </li>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <p style="font-size: 20px">查询结果为空，没有能够显示的文章！</p>
            </c:otherwise>
        </c:choose>


    </div>
    <jsp:include page="listRight.jsp"/>
</article>
<footer>
    <p>Design by <a href="#">长安大学 黄腾</a></p>
</footer>
</body>

</html>
