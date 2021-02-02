<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<H1>Welcome to ByTheWay App - never miss anything on your way!</H1>
<form action="<c:url value="/app"/>" method="get">
    <input type="submit" value="Try out!"/>
</form>
<%@include file="login/loggedAs.jspf"%>
</body>
</html>
