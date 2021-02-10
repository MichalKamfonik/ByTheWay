<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
<h2>Schedule</h2>
<form:form modelAttribute="trip" method="post" action="/app/add-trip3">
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
                <td>"${activity.place.name}
                    <c:if test="${not empty activity.description
                && not activity.description == '__ORIGIN__'
                && not activity.description == '__DESTINATION__'}">
                        - ${activity.description}
                    </c:if>"
                </td>
                <c:if test="${not activity.description.equals('__ORIGIN__') && not activity.description.equals('__DESTINATION__')}">
                    <td><form:input path="activities[${s.index}].duration"/></td>
                    <td><form:input path="activities[${s.index}].description"/></td>
                </c:if>
                <form:hidden path="activities[${s.index}].id"/>
                <form:hidden path="activities[${s.index}].place"/>
                <form:hidden path="activities[${s.index}].description"/>
                <form:hidden path="activities[${s.index}].departure"/>
                <form:hidden path="activities[${s.index}].arrival"/>
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
    <form:hidden path="id"/>
    <input type="submit">
</form:form>
</body>
</html>
