<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <h1>Already logged in!</h1>
    <form method="get" action="<c:url value="/app"/>">
        <input type="submit" value="Back to user page">
    </form>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <form method="post">
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <div><input type="submit" value="Sign In"/></div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <div>Don't have account?<br></div>
    <form method="get" action="<c:url value="/register"/>">
        <div>
            <input type="submit" value="Register"/>
        </div>
    </form>
</sec:authorize>
</body>
</html>
