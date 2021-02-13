<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.Duration" %>
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
                <td>${activity.place.name}<br><small>${activity.place.address}</small></td>
                <td>
                    <c:choose>
                        <c:when test="${activity.duration>1440}">
                            <fmt:parseNumber type="number" integerOnly="true" value="${(activity.duration- 1440) / 60}" var="h"/>
                            1d ${h}h ${activity.duration % 60}m
                        </c:when>
                        <c:when test="${activity.duration>60}">
                            <fmt:parseNumber type="number" integerOnly="true" value="${activity.duration / 60}" var="h"/>
                            ${h}h ${activity.duration % 60}m
                        </c:when>
                        <c:otherwise>
                            ${activity.duration}m
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${activity.description}</td>
            </tr>
            <c:if test="${not s.last}">
                <tr>
                    <td>${s.index * 2 + 2}</td>
                    <td>${activity.departure} - ${trip.activities[s.index+1].arrival}</td>
                    <td>Travel from ${activity.place.name} to ${trip.activities[s.index+1].place.name}</td>
                    <td>
                        <c:set var="travelTime"
                               value="${Duration.between(activity.departure, trip.activities[s.index+1].arrival).toMinutes()}"/>
                        <c:choose>
                            <c:when test="${travelTime>1440}">
                                <fmt:parseNumber type="number" integerOnly="true" value="${(travelTime - 1440) / 60}" var="h"/>
                                1d ${h}h ${travelTime % 60}m
                            </c:when>
                            <c:when test="${travelTime>60}">
                                <fmt:parseNumber type="number" integerOnly="true" value="${travelTime / 60}" var="h"/>
                                ${h}h ${travelTime % 60}m
                            </c:when>
                            <c:otherwise>
                                ${travelTime}m
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
<form method="get" action="<c:url value="/app"/>">
    <input type="submit" value="Back to dashboard">
</form>
</body>
</html>