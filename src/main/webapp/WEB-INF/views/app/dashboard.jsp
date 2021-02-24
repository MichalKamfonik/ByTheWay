<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <div class="row">
            <h1 class="h3 mb-0 text-gray-800">ByTheWay</h1>
        </div>
        <div class="row">
            <a href="<c:url value="/app/add-trip"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="fas fa-map-signs fa-sm text-white-50"></i> Create new trip </a>
            <a href="<c:url value="/app/add-plan"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="fas fa-calendar-plus fa-sm text-white-50"></i> Create new plan </a>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <!-- Collapsable Card for plans -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#plansCalendar" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="collapseCardExample">
                    <h6 class="m-0 font-weight-bold text-primary">User plans calendar</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="plansCalendar">
                    <div class="card-body calendar">
                        <div class="header">
                            <a data-action="prev-month" href="javascript:void(0)" title="Previous Month" id="prevMonth"><i></i></a>
                            <div class="text" data-render="month-year"></div>
                            <a data-action="next-month" href="javascript:void(0)" title="Next Month"
                               id="nextMonth"><i></i></a>
                        </div>
                        <div class="months" data-flow="left">
                            <div class="month month-a">
                                <div class="render render-a"></div>
                            </div>
                            <div class="month month-b">
                                <div class="render render-b"></div>
                            </div>
                        </div>
                        <script>
                            const userPlans = ${plans.stream().map(p->p.json()).toList()};
                        </script>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <!-- Collapsable Card for plans -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#plans" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="collapseCardExample">
                    <h6 class="m-0 font-weight-bold text-primary">User plans table</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="plans">
                    <div class="card-body">
                        <table border="1">
                            <tr>
                                <th>Position</th>
                                <th>Trip</th>
                                <th>Start date</th>
                                <th>End date</th>
                                <th>Action</th>
                            </tr>
                            <c:forEach items="${plans}" var="plan" varStatus="index">
                                <tr>
                                    <td>${index.count}</td>
                                    <td>${plan.trip.name}</td>
                                    <td>${plan.startTime}</td>
                                    <td>${plan.endTime}</td>
                                    <td>
                                        <form action="<c:url value="/app/delete/plan/${plan.id}"/>" method="get">
                                            <input type="submit" value="Delete">
                                        </form>
                                        <form action="<c:url value="/app/show/plan/${plan.id}"/>" method="get">
                                            <input type="submit" value="Show">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.container-fluid -->

<div><h2>Your trips</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Name</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${trips}" var="trip" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${trip.name}</td>
                <td>
                    <form action="<c:url value="/app/delete/trip/${trip.id}"/>" method="get">
                        <input type="submit" value="Delete">
                    </form>
                    <form action="<c:url value="/app/show/trip/${trip.id}"/>" method="get">
                        <input type="submit" value="Show">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
<div><h2>Your chosen categories</h2>
    <table border="1">
        <tr>
            <th>Position</th>
            <th>Name</th>
        </tr>
        <c:forEach items="${categories}" var="category" varStatus="index">
            <tr>
                <td>${index.count}</td>
                <td>${category.name}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <form action="<c:url value="/app/categories"/>" method="get">
                    <input type="submit" value="Manage categories">
                </form>
            </td>
        </tr>
    </table>
</div>

<sec:authorize access="hasRole('ADMIN')">
    <form action="<c:url value="/app/initialize"/>" method="get">
        <input class="fa fa-id-badge" type="submit" value="Initialize Categories">
    </form>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <form action="<c:url value="/app/clear-places"/>" method="get">
        <input class="fa fa-id-badge" type="submit" value="Clear places">
    </form>
</sec:authorize>

<%--<script src="<c:url value="/templates/calendar-06/js/popper.js"/>"></script>--%>
<script src="<c:url value="/templates/calendar-06/js/main.js"/>"></script>

<script src="<c:url value="/plans.js"/>"></script>
<%@ include file="footer.jspf" %>