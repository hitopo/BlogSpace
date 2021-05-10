<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发表文章</title>
    <jsp:include page="baseStyle.jsp"/>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap4.0.min.css"/> ">
    <script type="text/javascript" src="<c:url value="/js/jquery.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/wangEditor.js"/> "></script>
</head>
<style>
    .myBtn {
        float: right;
        color: #fff;
        background-color: #428bca;
        border-color: #357ebd;
        width: 80px;
        height: 40px;
        margin: 0;
        text-align: center;
    }
</style>
<body>
<article>
    <div class="container" style="margin: 15px">
        <form action="<c:url value="/actions/article/insert"/> " method="post">
            <div class="form-group">
                <label for="title">文章标题</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="文章标题" required>
            </div>
            <div class="form-group">
                <label for="brief">文章摘要</label>
                <input type="text" class="form-control" id="brief" name="brief" placeholder="文章摘要" required>
            </div>
            <div class="form-group">
                <label for="categoryId">文章分类</label>
                <select class="form-control" id="categoryId" name="categoryId">
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group" id="temp">
                <label for="brief">标签1</label>
                <input type="text" id="tag1" name="tag1" placeholder="请输入文章关键字1" required>
                <label for="brief">标签2</label>
                <input type="text" id="tag2" name="tag2" placeholder="请输入文章关键字2" required>
                <label for="brief">标签3</label>
                <input type="text" id="tag3" name="tag3" placeholder="请输入文章关键字3" required>
                <label for="brief">标签4</label>
                <input type="text" id="tag4" name="tag4" placeholder="请输入文章关键字4" required>
            </div>
            <div class="form-group">
                <label for="div1">内容</label>

                <div id="div1">
                </div>
                <%--富文本编辑器--%>
                <textarea id="content" name="content" style="display: none"></textarea>
            </div>
            <input type="submit" class="myBtn" value="发表博客"/>
        </form>

        <script type="text/javascript">
            var E = window.wangEditor;
            var editor = new E('#div1');
            var $text1 = $('#content');
            editor.customConfig.onchange = function (html) {
                // 监控变化，同步更新到 textarea
                $text1.val(html)
            };
            editor.create();
            // 初始化 textarea 的值
            $text1.val(editor.txt.html())
        </script>
    </div>
</article>
<footer>
    <p>Design by <a href="#">长安大学 黄腾</a></p>
</footer>
</body>
</html>

