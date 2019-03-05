<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <c:set var="path" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF8">
    <title>Index</title>
    <script src="${path}/static/jquery-3.1.1.min.js"></script>
</head>
<body>
<p>
    Hello Spring MVC!
</p>
<p>
    服务器时间：<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>


</p>
<input type="button" onclick="intosystem()" value="进入字典管理系统">
<p>

</p>
</body>




<script >



    function intosystem() {
        location.href = '${path}/intosystem';
    }


</script>
</html>