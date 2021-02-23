<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>ByTheWay App - never miss anything on your way!</title>
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/templates/the-big-picture/vendor/bootstrap/css/bootstrap.css"/>" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<c:url value="/templates/the-big-picture/css/the-big-picture.css"/>" rel="stylesheet">
</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-bottom">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/app"/>">Start ByTheWay</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Services</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Content -->
<section>
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <h1 class="mt-5">ByTheWay App</h1>
                <p>Never miss anything by your way</p>
            </div>
        </div>
    </div>
</section>

<!-- Bootstrap core JavaScript -->
<script src="<c:url value="/templates/the-big-picture/vendor/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/templates/the-big-picture/vendor/bootstrap/js/bootstrap.bundle.min.js"/>"></script>

</body>

</html>
