<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
<form:form modelAttribute="newTrip" method="post">
    <div>
        Duration [days]:
        <form:select path="duration">
            <form:option value="1" label="1"/>
            <form:option value="1" label="2"/>
        </form:select>
    </div>
    <div>
        Name:
        <form:input path="name"/>
    </div>
    <div>
        Origin:
        <form:input path="origin"/>
    </div>
    <div>
        Destination:
        <form:input path="destination"/>
    </div>
    <div><input type="submit"></div>
</form:form>
</body>
</html>
