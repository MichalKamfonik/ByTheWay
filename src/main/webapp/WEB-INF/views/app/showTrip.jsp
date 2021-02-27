<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../header.jspf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.time.Duration" %>
<%@page import="java.lang.Math" %>

<link rel='stylesheet' type='text/css' href='https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps.css'>
<script src="https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps-web.min.js"></script>

<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <div class="row">
            <h1 class="h3 mb-0 text-gray-800">Show trip</h1>
        </div>
        <div class="row">
            <a href="#" data-toggle="modal" data-target="#deleteTripModal"
               class="d-none d-inline-block btn btn-danger shadow"
               onclick="updateDeleteTrip('${trip.id}','${trip.name}')">
                <i class="fas fa-trash text-white-50"></i> Delete trip
            </a>
        </div>
    </div>
    <!-- Content Row -->
    <div class="row">

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Name</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${trip.name}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-file-signature fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Duration</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${trip.duration} days</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-hourglass-half fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Start time</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${trip.departure}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-plane-departure fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                End time</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${trip.arrival}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-plane-arrival fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="row ">
        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#activities" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="activities">
                    <h6 class="m-0 font-weight-bold text-primary">Activities</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="activities">
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
                            <c:forEach items="${trip.activities}" var="activity" varStatus="s">
                                <tr>
                                    <td>${s.index*2+1}</td>
                                    <td>${activity.arrival} - ${activity.departure}</td>
                                    <td>${activity.place.name}</td>
                                    <td>
                                        <fmt:formatNumber type="number" maxFractionDigits="0"
                                                          value="${Math.floor(activity.duration/(24*60))}"/>d
                                        <fmt:formatNumber type="number" maxFractionDigits="0"
                                                          value="${Math.floor((activity.duration%(24*60))/60)}"/>h
                                        <fmt:formatNumber type="number" maxFractionDigits="0"
                                                          value="${Math.floor(activity.duration%60)}"/>m
                                    </td>
                                    <td>${activity.description}</td>
                                    <c:if test="${activity.description.equals('DESTINATION')}">
                                        <c:set var="destinationIndex" value="${s.index}"/>
                                    </c:if>
                                </tr>
                                <c:if test="${not s.last}">
                                    <tr>
                                        <c:set var="duration"
                                               value="${Duration.between(activity.departure,trip.activities[s.index+1].arrival).toMinutes()}"/>
                                        <td>${s.index*2+2}</td>
                                        <td>${activity.departure} - ${activity.departure.plusMinutes(duration)}</td>
                                        <td>travel from ${activity.place.name}
                                            to ${trip.activities[s.index+1].place.name}</td>
                                        <td>
                                            <fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${Math.floor(duration/(24*60))}"/>d
                                            <fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${Math.floor((duration%(24*60))/60)}"/>h
                                            <fmt:formatNumber type="number" maxFractionDigits="0"
                                                              value="${Math.floor(duration%60)}"/>m
                                        </td>
                                        <td></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div> <!-- card body -->
                </div> <!-- collapse show plans -->
            </div> <!-- card shadow -->
        </div>
        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#mapCard" class="d-block card-header py-3" data-toggle="collapse"
                   role="button" aria-expanded="true" aria-controls="mapCard">
                    <h6 class="m-0 font-weight-bold text-primary">Map</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="mapCard">
                    <div class="card-body">
                        <div id="map" style="min-height: 500px">
                        </div>
                    </div> <!-- card body -->
                </div> <!-- collapse show plans -->
            </div> <!-- card shadow -->
        </div>
    </div>
</div>
<!-- /.container-fluid -->

<script>
    function createMarkerElement(type) {
        const element = document.createElement('div');
        const innerElement = document.createElement('i');

        element.className = 'btn btn-info btn-circle';

        if(type==='start'){
            innerElement.className = 'fas fa-location-arrow text-white-50';
        } else if(type === 'end'){
            innerElement.className = 'fas fa-flag-checkered text-white-50';
        }
        element.appendChild(innerElement);
        return element;
    }

    const map = tt.map({
        key: '${mappingApiKey}',
        container: 'map'
    });
    map.on('load', function () {
        map.addLayer({
            'id': 'overlay',
            'type': 'line',
            'source': {
                'type': 'geojson',
                'data': ${mapDataObject.json()}
            },
            'layout': {},
            'paint': {
                'line-width': 1
            }
        });
        let startPoint = ["${trip.activities[0].place.lon}", "${trip.activities[0].place.lat}"];
        let endPoint = ["${trip.activities[destinationIndex].place.lon}", "${trip.activities[destinationIndex].place.lat}"];
        new tt.Marker({element: createMarkerElement('start')}).setLngLat(startPoint).addTo(map);
        new tt.Marker({element: createMarkerElement('end')}).setLngLat(endPoint).addTo(map);
        let bounds = new tt.LngLatBounds();
        let data = ${mapDataObject.json()};
        data.features[0].geometry.coordinates.forEach(function (point) {
            bounds.extend(tt.LngLat.convert(point));
        });
        map.fitBounds(bounds, {duration: 0, padding: 50});
    });
</script>

<%@ include file="../footer.jspf" %>
