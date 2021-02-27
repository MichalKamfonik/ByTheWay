<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel='stylesheet' type='text/css' href='https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps.css'>
<link rel='stylesheet' type='text/css' href='<c:url value="/trip.css"/>'>
<script src="https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps-web.min.js"></script>

<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <form:form method="post" id="tripForm" modelAttribute="trip" class="user">
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <div class="row">
                <h1 class="h3 mb-0 text-gray-800">Add trip</h1>
            </div>
            <div class="row">
                <button type="submit" class="d-none d-inline-block btn btn-primary shadow">
                    <i class="fas fa-paper-plane text-white-50"></i> Submit
                </button>
            </div>
        </div>

        <div class="text-center">
            <form:errors path="arrival" cssClass="lead text-danger mb-5" element="div"/>
        </div>
        <div class="row" id="basic">
            <div class="form-group">
                <form:input path="duration" class="form-control form-control-user"
                            placeholder="Enter trip duration..."/>
            </div>
            <div class="form-group">
                <form:input path="name" class="form-control form-control-user"
                            placeholder="Enter trip name..."/>
            </div>
            <div class="form-group">
                <input type="text" class="form-control form-control-user"
                       placeholder="Enter start location..." name="origin"/>
            </div>
            <div class="form-group">
                <input type="text" class="form-control form-control-user"
                       placeholder="Enter destination location..." name="destination"/>
            </div>
            <div class="form-group">
                <form:input path="departure" class="form-control form-control-user"
                            placeholder="Enter departure hour..."/>
            </div>
            <div class="form-group">
                <form:input path="arrival" class="form-control form-control-user"
                            placeholder="Enter comeback hour..."/>
            </div>
        </div>

        <div class="row" hidden id="secondRow">
            <div class="col-lg-6">
                <div class="card shadow mb-4">
                    <!-- Card Header - Accordion -->
                    <a href="#tripTable" class="d-block card-header py-3" data-toggle="collapse"
                       role="button" aria-expanded="true" aria-controls="tripTable">
                        <h6 class="m-0 font-weight-bold text-primary">Trip</h6>
                    </a>
                    <!-- Card Content - Collapse -->
                    <div class="collapse show" id="tripTable">
                        <div class="card-body">
                                <table id="trip" class="table table-bordered" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>Nr.</th>
                                        <th>Hour</th>
                                        <th>Name</th>
                                        <th>Duration</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>
                                    <tfoot>
                                    <tr>
                                        <th>Nr.</th>
                                        <th>Hour</th>
                                        <th>Name</th>
                                        <th>Duration</th>
                                        <th>Description</th>
                                    </tr>
                                    </tfoot>
                                    <tbody>
                                        <%--            --%>
                                    </tbody>
                                </table>
                        </div> <!-- card body -->
                    </div> <!-- collapse show plans -->
                </div> <!-- card shadow -->
            </div>
            <div class="col-lg-6" id="map">
<%--                <div class="card shadow mb-4">--%>
<%--                    <!-- Card Header - Accordion -->--%>
<%--                    <a href="#mapCard" class="d-block card-header py-3" data-toggle="collapse"--%>
<%--                       role="button" aria-expanded="true" aria-controls="mapCard">--%>
<%--                        <h6 class="m-0 font-weight-bold text-primary">Map</h6>--%>
<%--                    </a>--%>
<%--                    <!-- Card Content - Collapse -->--%>
<%--                    <div class="collapse show" id="mapCard">--%>
<%--                        <div class="card-body">--%>
<%--                            <div id="map">--%>
<%--                            </div>--%>
<%--                        </div> <!-- card body -->--%>
<%--                    </div> <!-- collapse show plans -->--%>
<%--                </div> <!-- card shadow -->--%>
            </div>
        </div>

        <div class="row" hidden id="thirdRow">
            <div class="col-lg-6">
                <div class="card shadow mb-4">
                    <!-- Card Header - Accordion -->
                    <a href="#alongThereTable" class="d-block card-header py-3" data-toggle="collapse"
                       role="button" aria-expanded="true" aria-controls="alongThereTable">
                        <h6 class="m-0 font-weight-bold text-primary">Places along route there</h6>
                    </a>
                    <!-- Card Content - Collapse -->
                    <div class="collapse show" id="alongThereTable">
                        <div class="card-body">
                                <table id="alongThere" class="table table-bordered" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Address</th>
                                        <th>Categories</th>
                                        <th colspan="3">Action</th>
                                    </tr>
                                    </thead>
                                    <tfoot>
                                    <tr>
                                        <th>Name</th>
                                        <th>Address</th>
                                        <th>Categories</th>
                                        <th colspan="3">Action</th>
                                    </tr>
                                    </tfoot>
                                    <tbody>
                                        <%--            --%>
                                    </tbody>
                                </table>
                        </div> <!-- card body -->
                    </div> <!-- collapse show plans -->
                </div> <!-- card shadow -->
            </div>

            <div class="col-lg-6">
                <div class="card shadow mb-4">
                    <!-- Card Header - Accordion -->
                    <a href="#alongBackTable" class="d-block card-header py-3" data-toggle="collapse"
                       role="button" aria-expanded="true" aria-controls="alongBackTable">
                        <h6 class="m-0 font-weight-bold text-primary">User trips table</h6>
                    </a>
                    <!-- Card Content - Collapse -->
                    <div class="collapse show" id="alongBackTable">
                        <div class="card-body">
                                <table id="alongBack" class="table table-bordered" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Address</th>
                                        <th>Categories</th>
                                        <th colspan="3">Action</th>
                                    </tr>
                                    </thead>
                                    <tfoot>
                                    <tr>
                                        <th>Name</th>
                                        <th>Address</th>
                                        <th>Categories</th>
                                        <th colspan="3">Action</th>
                                    </tr>
                                    </tfoot>
                                    <tbody>
                                        <%--            --%>
                                    </tbody>
                                </table>
                        </div> <!-- card body -->
                    </div> <!-- collapse show plans -->
                </div> <!-- card shadow -->
            </div>
        </div>
    </form:form>
</div>
<!-- /.container-fluid -->

<script>
    var mappingApiKey = "${mappingApiKey}";
    var CSRF_TOKEN = "${_csrf.token}";
</script>

<script src="<c:url value="/trip.js"/>"></script>
<%@ include file="../footer.jspf" %>