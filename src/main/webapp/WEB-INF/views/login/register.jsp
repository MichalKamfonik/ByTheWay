<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register user</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <h1>Already logged in!</h1>
    <form method="get" action="<c:url value="/app"/>">
        <input type="submit" value="Back to user page">
    </form>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <form:form method="post" modelAttribute="user">
        <div><label> Username : <form:input path="username"/> </label></div>
        <div><label> Password: <form:password path="password"/> </label></div>
        <div>
            <input type="submit" value="Register" name="selection"/>
            <input type="submit" value="Cancel" name="selection"/>
        </div>
    </form:form>
</sec:authorize>
</body>
</html>
