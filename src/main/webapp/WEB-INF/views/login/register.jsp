<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ByTheWay - Register</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value='/templates/sb-admin2/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value='/templates/sb-admin2/css/sb-admin-2.min.css'/>" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                        </div>
                        <form:form method="post" modelAttribute="user" class="user">
                            <div class="form-group">
                                <form:input class="form-control form-control-user" id="exampleInputEmail"
                                       placeholder="User Name" path="username"/>
                                <div class="text-center">
                                    <form:errors path="username" cssClass="lead text-danger mb-5" element="small" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <form:password class="form-control form-control-user"
                                           id="exampleInputPassword" placeholder="Password" path="password"/>
                                    <div class="text-center">
                                        <form:errors path="password" cssClass="lead text-danger mb-5" element="small" />
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <form:password class="form-control form-control-user"
                                           id="exampleRepeatPassword" placeholder="Repeat Password"
                                                path="repeatedPassword"/>
                                    <div class="text-center">
                                        <form:errors path="repeatedPassword" cssClass="lead text-danger mb-5" element="small" />
                                    </div>
                                </div>
                            </div>
                            <input type="submit" class="btn btn-primary btn-user btn-block" value="Register Account">
                        </form:form>
                        <hr>
                        <div class="text-center">
                            <a class="small" href="<c:url value="/login"/>">Already have an account? Login!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="/templates/sb-admin2/vendor/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/templates/sb-admin2/vendor/bootstrap/js/bootstrap.bundle.min.js"/>"></script>

<!-- Core plugin JavaScript-->
<script src="<c:url value="/templates/sb-admin2/vendor/jquery-easing/jquery.easing.min.js"/>"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value="/templates/sb-admin2/js/sb-admin-2.min.js"/>"></script>

</body>

</html>