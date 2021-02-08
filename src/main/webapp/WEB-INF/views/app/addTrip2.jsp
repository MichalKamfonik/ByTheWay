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
    <tr>
        <td>1</td>
        <td>${trip.departure}</td>
        <td>"${trip.origin.name}"</td>
    </tr>
    <tr>
        <td>2</td>
        <td>${trip.departure} - ${trip.departure.plusMinutes(travelTime)}</td>
        <td>Travel from "${trip.origin.name}" to "${trip.destination.name}"</td>
    </tr>
    <tr>
        <td>3</td>
        <td>${trip.departure.plusMinutes(travelTime)} - ${trip.arrival.minusMinutes(travelTime)}</td>
        <td>"${trip.destination.name}"</td>
    </tr>
    <tr>
        <td>4</td>
        <td>${trip.arrival.minusMinutes(travelTime)} - ${trip.arrival}</td>
        <td>Travel from "${trip.destination.name}" to "${trip.origin.name}"</td>
    </tr>
</table>
<h2>Places along route</h2>
<form:form modelAttribute="trip" method="post" action="/app/add-trip2">
    <form:checkboxes path="activities" items="${alongRoute}" itemLabel="name" itemValue="id" delimiter="<br>"/>
    <div><input type="submit"></div>
    <form:hidden path="duration"/>
    <form:hidden path="name"/>
    <form:hidden path="origin" value="${trip.origin.id}"/>
    <form:hidden path="destination" value="${trip.destination.id}"/>
    <form:hidden path="departure"/>
    <form:hidden path="arrival"/>
</form:form>
</body>
</html>
