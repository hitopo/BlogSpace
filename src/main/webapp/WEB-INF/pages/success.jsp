<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>操作成功！</title>
</head>
<style>
    @charset "utf-8";
    .wrap {
        margin: 200px auto;
        width: 510px;
    }

    td {
        text-align: left;
        padding: 2px 10px;
    }

    td.header {
        font-size: 22px;
        padding-bottom: 10px;
        color: #000;
    }

    td.check-info {
        padding-top: 20px;
    }

    a {
        color: #328ce5;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }
</style>

<body>
<div class="wrap">
    <table>
        <tr>
            <td rowspan="5"><img src="<c:url value="/images/yes.jpg"/>" alt="又一个极简的错误页面"></td>
            <td class="header">编写文章成功！</td>
        </tr>
        <tr>
            <td><a href="<c:url value="/"/>">返回首页</a></td>
        </tr>
    </table>
</div>

</body>
</html>
