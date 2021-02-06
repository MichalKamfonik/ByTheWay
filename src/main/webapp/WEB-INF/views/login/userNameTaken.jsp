<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registry failed</title>
</head>
<body>
<h1>Usernam already taken!</h1>
<form method="get" action="<c:url value="/register"/>">
    <input type="submit" value="Back to registration page">
</form>
</body>
</html>
