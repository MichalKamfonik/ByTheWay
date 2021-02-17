<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add trip</title>
</head>
<body>
<form method="post" id="tripForm">
    <div class="tab" id="basic">
        <p>Days: <input type="number" name="days"></p>
        <p>Name: <input type="text" name="name"></p>
        <p>Origin: <input type="text" name="origin"></p>
        <p>Destination: <input type="text" name="destination"></p>
        <p>Departure time: <input type="time" name="departure"></p>
        <p>Comeback time: <input type="time" name="comeback"></p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="_CSRF_"/>
        <p><input type="submit"></p>
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
            <table id="alongThere" border="1">
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Categories</th>
                    <th>Action</th>
                </tr>
            </table>
        </div>
    </div>
</form>
<script src="<c:url value="/TripForm.js"/>"></script>
</body>
</html>
