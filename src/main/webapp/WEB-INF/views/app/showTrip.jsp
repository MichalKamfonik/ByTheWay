<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.time.Duration" %>
<%@page import="java.lang.Math" %>
<%@page import="java.util.List" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Show trip</title>
    <link rel='stylesheet' type='text/css' href='https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps.css'>
    <link rel='stylesheet' type='text/css' href='/tripFormStyle.css'>
    <script src="https://api.tomtom.com/maps-sdk-for-web/cdn/6.x/6.6.0/maps/maps-web.min.js"></script>
</head>
<body>
<div>Name: ${trip.name}</div>
<div>Duration: ${trip.duration} days</div>
<div>Start time: ${trip.departure}</div>
<div>End time: ${trip.arrival}</div>
<div>Activities:
    <table id="trip" border="1">
        <tr>
            <th>Nr.</th>
            <th>Hour</th>
            <th>Name</th>
            <th>Duration</th>
            <th>Description</th>
        </tr>
        <c:forEach items="${trip.activities}" var="activity" varStatus="s">
            <tr>
                <td>${s.index*2+1}</td>
                <td>${activity.arrival} - ${activity.departure}</td>
                <td>${activity.place.name}</td>
                <td>
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(activity.duration/(24*60))}"/>d
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor((activity.duration%(24*60))/60)}"/>h
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(activity.duration%60)}"/>m
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
                    <td>travel from ${activity.place.name} to ${trip.activities[s.index+1].place.name}</td>
                    <td>
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(duration/(24*60))}"/>d
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor((duration%(24*60))/60)}"/>h
                        <fmt:formatNumber type="number" maxFractionDigits="0" value="${Math.floor(duration%60)}"/>m
                    </td>
                    <td></td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>

<div id="map" style="width: 100%; height: 100%;"></div>
<script>
    function createMarkerElement(type) {
        const element = document.createElement('div');
        const innerElement = document.createElement('div');

        element.className = 'route-marker';
        if(type==='start'){
            innerElement.className = 'icon gg-arrow-right-o';
        } else if(type === 'end'){
            innerElement.className = 'icon gg-flag-alt';
        }
        element.appendChild(innerElement);
        return element;
    }
    const map = tt.map({
        key: '${mappingApiKey}',
        container: 'map'
    });
    map.on('load',function() {
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
        let startPoint = ["${trip.activities[0].place.lon}","${trip.activities[0].place.lat}"];
        let endPoint = ["${trip.activities[destinationIndex].place.lon}","${trip.activities[destinationIndex].place.lat}"];
        new tt.Marker({ element: createMarkerElement('start') }).setLngLat(startPoint).addTo(map);
        new tt.Marker({ element: createMarkerElement('end') }).setLngLat(endPoint).addTo(map);
        let bounds = new tt.LngLatBounds();
        let data = ${mapDataObject.json()};
        data.features[0].geometry.coordinates.forEach(function(point) {
            bounds.extend(tt.LngLat.convert(point));
        });
        map.fitBounds(bounds, { duration: 0, padding: 50 });
    });
</script>
<form action="<c:url value="/app"/>">
    <input type="submit" value="Back to dashboard">
</form>
</body>
</html>
