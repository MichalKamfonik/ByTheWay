<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>ByTheWay</title>
</head>
<body>
<div><h2>Your plans</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Trip</th>
            <th>Start date</th>
            <th>End date</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${plans}" var="plan" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${plan.trip.name}</td>
                <td>${plan.startTime}</td>
                <td>${plan.endTime}</td>
                <td><form action="<c:url value="/app/delete/plan/${plan.id}"/>" method="get">
                    <input type="submit" value="Delete">
                </form></td>
            </tr>
        </c:forEach>
    </table>
    <h2></h2>
    <form method="get" action="<c:url value="/app/add-plan"/>">
        <div><input type="submit" value="Add plan"></div>
    </form>
</div>
<div><h2>Your trips</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Name</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${trips}" var="trip" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${trip.name}</td>
                <td><form action="<c:url value="/app/delete/trip/${trip.id}"/>" method="get">
                    <input type="submit" value="Delete">
                </form></td>
            </tr>
        </c:forEach>
    </table>
    <h2></h2>
    <form method="get" action="<c:url value="/app/add-trip"/>">
        <div><input type="submit" value="Add trip"></div>
    </form>
</div>
<div><h2>Your chosen categories</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Name</th>
        </tr>
        <c:forEach items="${categories}" var="category" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${category.name}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <form action="<c:url value="/app/categories"/>" method="get">
                    <input type="submit" value="Manage categories">
                </form>
            </td>
        </tr>
    </table>
</div>
<%@include file="../login/loggedAs.jspf" %>

<sec:authorize access="hasRole('ADMIN')">
    <form action="<c:url value="/app/initialize"/>" method="get">
        <input class="fa fa-id-badge" type="submit" value="Initialize Categories">
    </form>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <form action="<c:url value="/app/clear-places"/>" method="get">
        <input class="fa fa-id-badge" type="submit" value="Clear places">
    </form>
</sec:authorize>
</body>
</html>
