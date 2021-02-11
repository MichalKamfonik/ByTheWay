<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip step 1</title>
</head>
<body>
<form:form modelAttribute="trip" method="post">
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
        <label>
            Origin:
            <input type="text" name="place1"/>
        </label>
    </div>
    <div>
        <label>
            Destination:
            <input type="text" name="place2"/>
        </label>
    </div>
    <div>
        Departure time (1st day) [HH:MM]:
        <form:input path="departure"/>
    </div>
    <div>
        Comeback time (last day) [HH:MM]:
        <form:input path="arrival"/>
    </div>
    <div><input type="submit"></div>
</form:form>
</body>
</html>
