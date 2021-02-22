<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Categories</title>
</head>
<body>
<h2>Select categories that interest you</h2>
<div>
    <form:form modelAttribute="user" method="post">
        <form:checkboxes path="favoriteCategories" items="${categories}"
                         itemValue="id" itemLabel="name" delimiter="<br>"/>
        <br>
        <form:errors path="favoriteCategories" cssClass="error"/>
        <br>
        <input type="submit">
    </form:form>
</div>
</body>
</html>
