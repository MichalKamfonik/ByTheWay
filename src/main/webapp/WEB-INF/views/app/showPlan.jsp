<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Show plan</title>
</head>
<body>
    <div>Trip: ${plan.trip.name}</div>
    <div>Date: ${plan.startTime} - ${plan.endTime}</div>
    <form action="<c:url value="/app"/>">
        <input type="submit" value="Back to dashboard">
    </form>
</body>
</html>
