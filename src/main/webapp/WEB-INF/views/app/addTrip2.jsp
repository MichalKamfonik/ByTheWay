<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
<h2>Schedule</h2>
<table border="1">
    <tr>
        <th>Position</th>
        <th>Hour</th>
        <th>Name</th>
    </tr>
    <c:forEach items="${trip.activities}" var="activity" varStatus="s">
        <tr>
            <td>${s.index * 2 + 1}</td>
            <td>${activity.arrival} - ${activity.departure}</td>
            <td>"${activity.place.name}
                <c:if test="${not empty activity.description}">
                    - ${activity.description}
                </c:if>"</td>
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
<h2>Places along route</h2>
<form:form modelAttribute="trip" method="post" action="/app/add-trip2">
    <form:checkboxes path="activities" items="${alongRoute}" itemLabel="name" itemValue="id" delimiter="<br>"/>
    <div><input type="submit"></div>
    <form:hidden path="id"/>
    <form:hidden path="duration"/>
    <form:hidden path="name"/>
    <form:hidden path="departure"/>
    <form:hidden path="arrival"/>
</form:form>
</body>
</html>
