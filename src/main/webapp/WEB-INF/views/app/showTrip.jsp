<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.time.Duration" %>
<%@page import="java.lang.Math" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Show trip</title>
</head>
<body>
<div>Name: ${trip.name}</div>
<div>Duration: ${trip.duration} days</div>
<div>Start time: ${trip.departure}</div>
<div>End time: ${trip.arrival}</div>
<div>Activities:
    <table id="trip" border="1">
        <tr>
            <th>Nr.</th>
            <th>Hour</th>
            <th>Name</th>
            <th>Duration</th>
            <th>Description</th>
        </tr>
        <c:forEach items="${trip.activities}" var="activity" varStatus="s">
            <tr>
                <td>${s.index*2+1}</td>
                <td>${activity.arrival} - ${activity.departure}</td>
                <td>${activity.place.name}</td>
                <td>
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(activity.duration/(24*60))}"/>d
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor((activity.duration%(24*60))/60)}"/>h
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(activity.duration%60)}"/>m
                </td>
                <td>${activity.description}</td>
            </tr>
            <c:if test="${not s.last}">
                <tr>
                    <c:set var="duration"
                           value="${Duration.between(activity.departure,trip.activities[s.index+1].arrival).toMinutes()}"/>
                    <td>${s.index*2+2}</td>
                    <td>${activity.departure} - ${activity.departure.plusMinutes(duration)}</td>
                    <td>travel from ${activity.place.name} to ${trip.activities[s.index+1].place.name}</td>
                    <td>
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(duration/(24*60))}"/>d
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor((duration%(24*60))/60)}"/>h
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(duration%60)}"/>m
                    </td>
                    <td></td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>

<form action="<c:url value="/app"/>">
    <input type="submit" value="Back to dashboard">
</form>
</body>
</html>
