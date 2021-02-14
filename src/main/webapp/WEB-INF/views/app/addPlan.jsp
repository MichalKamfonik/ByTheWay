<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.time.LocalDate" %>
<html>
<head>
    <title>Add Plan</title>
</head>
<body>
<form:form modelAttribute="plan" method="post">
    <div>Select trip: <form:select path="trip" items="${trips}" itemLabel="name" itemValue="id"/></div>
    <div> Select weekend: <br>
        <table border="1">
            <tr>
                <th colspan="7">${LocalDate.now().getMonth().name()}</th>
            </tr>
            <tr>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
                <th>Sunday</th>
            </tr>
            <c:forEach begin="1" end="${LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue()-1}" varStatus="s">
            <c:if test="${s.first}"><tr></c:if>
            <td></td>
            </c:forEach>
            <c:forEach begin="1" end="${LocalDate.now().lengthOfMonth()}" var="day">
                <c:if test="${(LocalDate.now().getDayOfWeek().getValue()+day)%7 == 1}"><tr></c:if>
                <td>
                    <form:radiobutton path="startTime" label="${day}" value="${LocalDate.now().withDayOfMonth(day)}"/>
                </td>
                <c:if test="${(LocalDate.now().getDayOfWeek().getValue()+day)%7 == 0}"></tr></c:if>
            </c:forEach>
            <c:forEach begin="${LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
                .getDayOfWeek().getValue()+1}" end="7" varStatus="s">
                <td></td>
                <c:if test="${s.last}"></tr></c:if>
            </c:forEach>
        </table>
    </div><br>
    <div><input type="submit"></div>
</form:form>
</body>
</html>
