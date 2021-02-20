<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
<form:form method="post" id="tripForm" modelAttribute="trip">
    <div class="tab" id="basic">
        <p>Days: <form:input path="duration"/></p>
        <p>Name: <form:input path="name"/></p>
        <p>Origin: <input type="text" name="origin"></p>
        <p>Destination: <input type="text" name="destination"></p>
        <p>Departure time: <form:input path="departure"/></p>
        <p>Comeback time: <form:input path="arrival"/></p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="_CSRF_"/>
    </div>
    <div class="tab">
        <div>
            <table id="trip" border="1">
                <tr>
                    <th>Nr.</th>
                    <th>Hour</th>
                    <th>Name</th>
                    <th>Duration</th>
                    <th>Description</th>
                </tr>
            </table>
        </div>
        <div>
            <table id="alongThere">
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Categories</th>
                    <th colspan="3">Action</th>
                </tr>
            </table>
        </div>
        <div>
            <table id="alongBack">
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Categories</th>
                    <th colspan="3">Action</th>
                </tr>
            </table>
        </div>
    </div>
    <p><input type="submit"></p>
</form:form>
<script src="<c:url value="/TripForm.js"/>"></script>
</body>
</html>
