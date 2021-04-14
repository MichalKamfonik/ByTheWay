<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Confirm removal</title>
</head>
<body>
<h2>Are you sure you want to delete this Plan?</h2>
Start: ${plan.startTime} <br>
End: ${plan.endTime} <br>
Trip: ${plan.trip.name} <br>
<div>
    <form method="post">
        <input type="submit" name="choice" value="Yes">
        <input type="submit" name="choice"  value="No">
    </form>
</div>
</body>
</html>
