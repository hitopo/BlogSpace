<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/public.css"/>"/>
    <script>
        <%--显示当前时间--%>
        //这里代码多了几行，但是不会延迟显示，速度比较好，格式可以自定义，是理想的时间显示
        setInterval("fun(time2)", 1);

        function fun(timeID) {
            var date = new Date();  //创建对象  
            var y = date.getFullYear();     //获取年份  
            var m = date.getMonth() + 1;   //获取月份  返回0-11  
            var d = date.getDate(); // 获取日  
            var w = date.getDay();   //获取星期几  返回0-6   (0=星期天) 
            var ww = ' 星期' + '日一二三四五六'.charAt(new Date().getDay());//星期几
            var h = date.getHours();  //时
            var minute = date.getMinutes(); //分
            var s = date.getSeconds(); //秒
            if (m < 10) {
                m = "0" + m;
            }
            if (d < 10) {
                d = "0" + d;
            }
            if (h < 10) {
                h = "0" + h;
            }
            if (minute < 10) {
                minute = "0" + minute;
            }

            if (s < 10) {
                s = "0" + s;
            }
            document.getElementById(timeID.id).innerHTML = "当前时间：" + y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + s + " " + ww;
        }
    </script>
      
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>博客后台管理系统</h1>
    <div class="publicHeaderR">
        <p><span>你好！</span><span style="color: #fff21b">${account.nickname}</span> , 欢迎你！</p>
        <a href="<c:url value="/actions/home/logout"/> ">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time2"></span>
    <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！</a>
</section>
</body>

</html>
