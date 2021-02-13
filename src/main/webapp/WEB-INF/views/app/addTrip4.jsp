<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip summary</title>
</head>
<body>
<h2>Schedule</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Hour</th>
            <th>Name</th>
            <th>Duration</th>
            <th>Description</th>
        </tr>
        <c:forEach items="${trip.activities}" var="activity" varStatus="s">
            <tr>
                <td>${s.index * 2 + 1}</td>
                <td>${activity.arrival} - ${activity.departure}</td>
                <td>${activity.place.name}</td>
                <td>${activity.duration}</td>
                <td>${activity.description}</td>
            </tr>
            <c:if test="${not s.last}">
                <tr>
                    <td>${s.index * 2 + 2}</td>
                    <td>${activity.departure} - ${trip.activities[s.index+1].arrival}</td>
                    <td>Travel from ${activity.place.name} to ${trip.activities[s.index+1].place.name}</td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
<form method="get" action="<c:url value="/app"/>">
    <input type="submit" value="Back to dashboard">
</form>
</body>
</html>
