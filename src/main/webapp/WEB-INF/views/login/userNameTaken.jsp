<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- 404 Error Text -->
    <div class="text-center">
        <div class="error mx-auto" data-text="Taken">Taken</div>
        <p class="lead text-gray-800 mb-5">User name taken</p>
        <p class="text-gray-500 mb-0">This user name is already taken.
            Please try again with another name.</p>
        <a href="<c:url value="/app"/> ">&larr; Back to Login</a>
    </div>

</div>
<!-- /.container-fluid -->
<%@ include file="../footer.jspf" %>
