<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <td>${plan.start}</td>
                <td>${plan.end}</td>
                <td><form action="<c:url value="/app/delete/${plan.id}"/>" method="post">
                    <input type="submit" value="Delete">
                </form></td>
            </tr>
        </c:forEach>
    </table>
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
<h2></h2>
<form method="get" action="<c:url value="/app/addPlan"/>">
    <div><input type="submit" value="Add plan"></div>
</form>
<h2></h2>
<form method="get" action="<c:url value="/app/addTrip"/>">
    <div><input type="submit" value="Add trip"></div>
</form>

<%@include file="../login/loggedAs.jspf" %>
</body>
</html>
