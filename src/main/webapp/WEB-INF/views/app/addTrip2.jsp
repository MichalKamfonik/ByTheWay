<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
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
<form:form modelAttribute="trip" method="post" action="/app/addTrip2">
    <div><input type="submit"></div>
</form:form>
</body>
</html>