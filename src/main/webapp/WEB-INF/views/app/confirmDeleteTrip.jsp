<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Confirm removal</title>
</head>
<body>
<h2>Are you sure you want to delete this Trip?</h2>
Name: ${trip.name} <br>
<div>
    <form:form method="post">
        <input type="submit" name="choice" value="Yes">
        <input type="submit" name="choice"  value="No">
    </form:form>
</div>
</body>
</html>
