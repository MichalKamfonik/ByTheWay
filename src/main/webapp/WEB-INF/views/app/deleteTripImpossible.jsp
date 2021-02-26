<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- 404 Error Text -->
    <div class="text-center">
        <div class="error mx-auto" data-text="Impossible">Impossible</div>
        <p class="lead text-gray-800 mb-5">Delete trip is not possible</p>
        <p class="text-gray-500 mb-0">
            First delete all plans containing this trip.
            This trip is part of following plans.
        <ul style="list-style: none;">
            <c:forEach items="${plans}" var="plan">
                <li>${plan.trip.name} at ${plan.startTime} - ${plan.endTime}.</li>
            </c:forEach>
        </ul>
        </p>
        <a href="<c:url value="/app"/> ">&larr; Back to Dashboard</a>
    </div>

</div>
<!-- /.container-fluid -->
<%@ include file="../footer.jspf" %>