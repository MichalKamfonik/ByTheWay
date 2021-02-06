<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registry failed</title>
</head>
<body>
    <h1>New user registration is disabled!</h1>
    <form method="get" action="<c:url value="/"/>">
        <input type="submit" value="Back to homepage">
    </form>
</body>
</html>
