<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- 404 Error Text -->
    <div class="text-center">
        <div class="error mx-auto" data-text="Locked">Locked</div>
        <p class="lead text-gray-800 mb-5">Registry forbidden</p>
        <p class="text-gray-500 mb-0">New user registration is disabled!</p>
        <a href="<c:url value="/app"/> ">&larr; Back to Login</a>
    </div>

</div>
<!-- /.container-fluid -->
<%@ include file="../footer.jspf" %>
