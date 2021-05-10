<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404错误，您所访问的页面不存在！</title>
</head>
<style>
    body {
        font-size: 14px;
        font-family: 'helvetica neue', tahoma, arial, 'hiragino sans gb', 'microsoft yahei', 'Simsun', sans-serif;
        background-color: #fff;
        color: #808080;
    }

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
            <td rowspan="5"><img src="<c:url value="/images/error.jpg"/>" alt="又一个极简的错误页面"></td>
            <td class="header">很抱歉，您所访问的页面不存在！</td>
        </tr>
        <tr>
            <td>如果刷新页面没能解决问题，你可以<a href="<c:url value="/"/>">返回首页</a></td>
        </tr>
    </table>
</div>
</body>
</html>
