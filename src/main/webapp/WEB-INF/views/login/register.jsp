<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Register user</title>
</head>
<body>
<form:form method="post" modelAttribute="user">
    <div><label> Username : <form:input path="username"/> </label></div>
    <div><label> Password: <from:password path="password" /> </label></div>
    <div><input type="submit" value="Register"/></div>
</form:form>
</body>
</html>
