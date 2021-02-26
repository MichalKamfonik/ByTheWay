<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <div class="row">
            <h1 class="h3 mb-0 text-gray-800">ByTheWay</h1>
        </div>
        <div class="row">
            <a href="<c:url value="/app/add-trip"/>" class="d-none d-inline-block btn btn-primary shadow">
                <i class="fas fa-map-signs text-white-50"></i> Create new trip </a>
            <a href="<c:url value="/app/add-plan"/>" class="d-none d-inline-block btn btn-primary shadow">
                <i class="fas fa-calendar-plus text-white-50"></i> Create new plan </a>
            <a href="<c:url value="/app/categories"/>"
               class="d-none d-inline-block btn btn-primary shadow">
                <i class="far fa-star text-white-50"></i> Manage categories </a>
            <sec:authorize access="hasRole('ADMIN')">
                <a href="<c:url value="/app/initialize"/>"
                   class="d-none d-inline-block btn btn-primary shadow">
                    <i class="fas fa-star text-white-50"></i> Initialize Categories </a>
                <a href="<c:url value="/app/clear-places"/>"
                   class="d-none d-inline-block btn btn-primary shadow">
                    <i class="fas fa-broom text-white-50"></i> Clear places </a>
            </sec:authorize>
        </div>

    </div>
    <div class="row">
        <div class="col-lg-6">
            <!-- Collapsable Card for plans calendar -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#plansCalendar" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="plansCalendar">
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
                    </div> <!-- card body -->
                </div> <!-- collapse show -->
            </div> <!-- card shadow -->
        </div> <!-- col -->

        <div class="col-lg-6">
            <!-- Collapsable Card for plans -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#plans" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="plans">
                    <h6 class="m-0 font-weight-bold text-primary">User plans table</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="plans">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTablePlans" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Position</th>
                                    <th>Trip</th>
                                    <th>Start date</th>
                                    <th>End date</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Position</th>
                                    <th>Trip</th>
                                    <th>Start date</th>
                                    <th>End date</th>
                                    <th>Action</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <c:forEach items="${plans}" var="plan" varStatus="index">
                                    <tr>
                                        <td>${index.count}</td>
                                        <td>${plan.trip.name}</td>
                                        <td>${plan.startTime}</td>
                                        <td>${plan.endTime}</td>
                                        <td>
                                            <a href="<c:url value="/app/show/plan/${plan.id}"/>"
                                               class="btn btn-info btn-icon-split btn-sm">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-info-circle fa-sm"></i>
                                        </span>
                                                <span class="text">Show</span>
                                            </a>
                                            <div class="my-2"></div>
                                            <a href="<c:url value="/app/delete/plan/${plan.id}"/>"
                                               class="btn btn-danger btn-icon-split btn-sm">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-trash fa-sm"></i>
                                        </span>
                                                <span class="text">Delete</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div> <!-- card body -->
                </div> <!-- collapse show plans -->
            </div> <!-- card shadow -->
        </div> <!-- col -->
    </div> <!-- row -->

    <div class="row">
        <div class="col-lg-6">
            <!-- Collapsable Card for plans -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#trips" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="trips">
                    <h6 class="m-0 font-weight-bold text-primary">User trips table</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="trips">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTableTrip" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Position</th>
                                    <th>Name</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Position</th>
                                    <th>Name</th>
                                    <th>Action</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <c:forEach items="${trips}" var="trip" varStatus="index">
                                    <tr>
                                        <td>${index.count}</td>
                                        <td>${trip.name}</td>
                                        <td>
                                            <a href="<c:url value="/app/show/trip/${trip.id}"/>"
                                               class="btn btn-info btn-icon-split btn-sm">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-info-circle fa-sm"></i>
                                        </span>
                                                <span class="text">Show</span>
                                            </a>
                                            <div class="my-2"></div>
                                            <a href="<c:url value="/app/delete/trip/${trip.id}"/>"
                                               class="btn btn-danger btn-icon-split btn-sm">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-trash fa-sm"></i>
                                        </span>
                                                <span class="text">Delete</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div> <!-- card body -->
                </div> <!-- collapse show plans -->
            </div> <!-- card shadow -->
        </div> <!-- col -->

        <div class="col-lg-6">
            <!-- Collapsable Card for plans -->
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#categories" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="categories">
                    <h6 class="m-0 font-weight-bold text-primary">User categories table</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="categories">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTableCategories" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Position</th>
                                    <th>Name</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Position</th>
                                    <th>Name</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <c:forEach items="${categories}" var="category" varStatus="index">
                                    <tr>
                                        <td>${index.count}</td>
                                        <td>${category.name}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div> <!-- card body -->
                </div> <!-- collapse show plans -->
            </div> <!-- card shadow -->
        </div> <!-- col -->
    </div> <!-- row -->
</div>
<!-- /.container-fluid -->

<script src="<c:url value="/templates/calendar-06/js/main.js"/>"></script>
<script src="<c:url value="/plans.js"/>"></script>
<%@ include file="../footer.jspf" %>