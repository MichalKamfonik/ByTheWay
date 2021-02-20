<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Delete trip is not possible</title>
</head>
<body>
<h1>Delete trip is not possible</h1>
First delete all plans containing this trip.
This trip is part of following plans.
<ul>
    <c:forEach items="${plans}" var="plan">
        <li>${plan.trip.name} at ${plan.startTime} - ${plan.endTime}.</li>
    </c:forEach>
</ul>
<form action="<c:url value="/app"/>">
    <input type="submit" value="Back to dashboard">
</form>
</body>
</html>
