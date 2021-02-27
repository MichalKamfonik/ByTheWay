class Activity {
    constructor(place, duration, arrival, departure, number, description) {
        this.place = place;
        this.duration = duration;
        this.arrival = arrival;
        this.departure = departure;
        this.number = number;
        this.description = description;
    }
}

const API_HOST = window.location.origin;
const ACTIVITIES = [];
let ALONG_ROUTE_THERE = [];
let ALONG_ROUTE_BACK = [];
const DAY = 24 * 60;
let map;
let placeOrigin;
let placeDestination;

document.addEventListener('DOMContentLoaded', function () {
    document.querySelector("#basic").style.display = "block";

    const tripForm = document.querySelector("#tripForm");

    tripForm.addEventListener('submit', submitListener1);
});

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

async function addMap(){
    const mapData = await apiGetMapData().then(mapData=>mapData);
    if (!map){
        map = tt.map({
            key: mappingApiKey,
            container: 'map'
        });
        map.on('load', function () {
            map.addLayer({
                'id': 'overlay',
                'type': 'line',
                'source': {
                    'type': 'geojson',
                    'data': mapData
                },
                'layout': {},
                'paint': {
                    'line-width': 1
                }
            });
            let startPoint = [placeOrigin.lon,placeOrigin.lat];
            let endPoint = [placeDestination.lon,placeDestination.lat];
            new tt.Marker({ element: createMarkerElement('start') }).setLngLat(startPoint).addTo(map);
            new tt.Marker({ element: createMarkerElement('end') }).setLngLat(endPoint).addTo(map);
            let bounds = new tt.LngLatBounds();
            mapData.features[0].geometry.coordinates.forEach(function(point) {
                bounds.extend(tt.LngLat.convert(point));
            });
            map.fitBounds(bounds, { duration: 0, padding: 50 });
        });
    } else {
        if(map.getLayer('overlay')){
            map.removeLayer('overlay');
            map.removeSource('overlay');
        }
        map.addLayer({
            'id': 'overlay',
            'type': 'line',
            'source': {
                'type': 'geojson',
                'data': mapData
            },
            'layout': {},
            'paint': {
                'line-width': 1
            }
        });
        let startPoint = [placeOrigin.lon,placeOrigin.lat];
        let endPoint = [placeDestination.lon,placeDestination.lat];
        new tt.Marker({ element: createMarkerElement('start') }).setLngLat(startPoint).addTo(map);
        new tt.Marker({ element: createMarkerElement('end') }).setLngLat(endPoint).addTo(map);
        let bounds = new tt.LngLatBounds();
        mapData.features[0].geometry.coordinates.forEach(function(point) {
            bounds.extend(tt.LngLat.convert(point));
        });
        map.fitBounds(bounds, { duration: 0, padding: 50 });
    }
}

async function submitListener1(e) {
    e.preventDefault();

    document.querySelector("#basic").style.display = "none";
    document.querySelector("#secondRow").style.display = "flex";
    document.querySelector("#thirdRow").style.display = "flex";

    const departureTime = this.departure.value.split(":");
    const arrivalTime = this.arrival.value.split(":");

    const tripStartInMinutes = departureTime[0] * 60 + parseInt(departureTime[1]);
    const tripEndInMinutes = arrivalTime[0] * 60 + parseInt(arrivalTime[1]);
    const tripDuration = (this.duration.value - 1) * DAY + tripEndInMinutes - tripStartInMinutes;

    placeOrigin = await apiGetPlace(this.origin.value).then(place => place);
    placeDestination = await apiGetPlace(this.destination.value).then(place => place);

    const travelTimeThere = (await apiGetRouteTime(placeOrigin, placeDestination).then(time => time) / 60).toFixed(0);
    const travelTimeBack = (await apiGetRouteTime(placeDestination, placeOrigin).then(time => time) / 60).toFixed(0);

    const start = new Activity(
        placeOrigin,
        0,
        this.departure.value,
        this.departure.value,
        0,
        "ORIGIN"
    );
    const destination = new Activity(
        placeDestination,
        tripDuration - travelTimeThere - travelTimeBack,
        timeAfterMinutes(this.departure.value, travelTimeThere),
        timeBeforeMinutes(this.arrival.value, travelTimeBack),
        1,
        "DESTINATION"
    );
    const end = new Activity(
        placeOrigin,
        0,
        this.arrival.value,
        this.arrival.value,
        2,
        "ORIGIN"
    );

    ACTIVITIES.push(start);
    ACTIVITIES.push(destination);
    ACTIVITIES.push(end);

    renderActivities();
    addMap();

    ALONG_ROUTE_THERE = await apiGetAlongRoute(placeOrigin, placeDestination, travelTimeThere * 60);
    ALONG_ROUTE_BACK = await apiGetAlongRoute(placeDestination, placeOrigin, travelTimeBack * 60);

    renderAlongRoute(ALONG_ROUTE_THERE, "there");
    renderAlongRoute(ALONG_ROUTE_BACK, "back");

    this.removeEventListener("submit", submitListener1);
    this.addEventListener("submit", submitListener2);
}

function submitListener2(e) {
    for (let i = 0; i < ACTIVITIES.length; i++) {
        const input1 = document.createElement("input");
        input1.type="hidden";
        input1.name="activities["+i+"].duration";
        input1.value = ACTIVITIES[i].duration;
        this.appendChild(input1);

        const input2 = document.createElement("input");
        input2.type="hidden";
        input2.name="activities["+i+"].description";
        input2.value = ACTIVITIES[i].description;
        this.appendChild(input2);

        const input3 = document.createElement("input");
        input3.type="hidden";
        input3.name="activities["+i+"].place";
        input3.value = ACTIVITIES[i].place.id;
        this.appendChild(input3);

        const input4 = document.createElement("input");
        input4.type="hidden";
        input4.name="activities["+i+"].departure";
        input4.value = ACTIVITIES[i].departure;
        this.appendChild(input4);

        const input5 = document.createElement("input");
        input5.type="hidden";
        input5.name="activities["+i+"].arrival";
        input5.value = ACTIVITIES[i].arrival;
        this.appendChild(input5);

        const input6 = document.createElement("input");
        input6.type="hidden";
        input6.name="activities["+i+"].number";
        input6.value = ACTIVITIES[i].number;
        this.appendChild(input6);
    }
}

function renderActivity(activity, number) {
    const trip = document.querySelector("#trip");

    const tr = document.createElement("tr");
    trip.appendChild(tr);
    const nrTd = document.createElement("td");
    nrTd.innerText = number;
    tr.appendChild(nrTd);
    const hourTd = document.createElement("td");
    hourTd.innerText = activity.arrival + " - " + activity.departure;
    tr.appendChild(hourTd);
    const nameTd = document.createElement("td");
    nameTd.innerText = activity.place.name;
    tr.appendChild(nameTd);
    const durationTd = document.createElement("td");
    durationTd.innerText = Math.floor(activity.duration / DAY)
        + "d "
        + Math.floor((activity.duration % DAY) / 60)
        + "h "
        + activity.duration % 60
        + "m";
    tr.appendChild(durationTd);
    const descriptionTd = document.createElement("td");
    descriptionTd.innerText = activity.description;
    tr.appendChild(descriptionTd);
}

function renderActivities() {
    const trip = document.querySelector("#trip");
    while (trip.children.length > 1) {
        trip.removeChild(trip.lastChild);
    }
    for (let i = 0; i < ACTIVITIES.length - 1; i++) {
        const current = ACTIVITIES[i];
        const next = ACTIVITIES[i + 1];

        renderActivity(current, i * 2 + 1);
        const travel = new Activity(
            {name: "travel from " + current.place.name + " to " + next.place.name},
            minutesBetweenTimes(current.departure, next.arrival),
            current.departure,
            next.arrival,
            -1,
            ""
        );
        renderActivity(travel, i * 2 + 2);
    }
    renderActivity(ACTIVITIES[ACTIVITIES.length - 1], ACTIVITIES.length * 2 - 1);
    addMap();
}

function renderAlongRoute(places, direction) {
    let alongRoute;
    if ("there" === direction) {
        alongRoute = document.querySelector("#alongThere");
    } else {
        alongRoute = document.querySelector("#alongBack");
    }

    places.forEach(place => {
        const tr = document.createElement("tr");
        const nameTd = document.createElement("td");
        nameTd.innerText = place.name;
        tr.appendChild(nameTd);
        const addressTd = document.createElement("td");
        addressTd.innerText = place.address;
        tr.appendChild(addressTd);
        const categoriesTd = document.createElement("td");
        categoriesTd.innerText = place.categories.map(c => c.name).reduce((acc, curr) => acc + " " + curr);
        tr.appendChild(categoriesTd);
        //
        let durationTd = document.createElement("td");
        tr.appendChild(durationTd);
        let durationInput = document.createElement("input");
        durationInput.type = "number";
        durationTd.appendChild(durationInput);
        let descriptionTd = document.createElement("td");
        tr.appendChild(descriptionTd);
        let descriptionInput = document.createElement("input");
        descriptionInput.type = "text";
        descriptionTd.appendChild(descriptionInput);
        //
        const addTd = document.createElement("td");
        const addButton = document.createElement("button");
        addTd.appendChild(addButton);
        tr.appendChild(addTd);
        addButton.className = "btn btn-info btn-icon-split btn-sm";
        addButton.innerHTML =
            "<span class=\"icon text-white-50\">\n" +
            "<i class=\"fas fa-plus fa-sm\"></i>\n" +
            "</span>\n" +
            "<span class=\"text\">add</span>";
        if ("there" === direction) {
            addButton.addEventListener("click", addToThere);
        } else {
            addButton.addEventListener("click", addToBack);
        }

        alongRoute.appendChild(tr);
    })
}

async function addToThere(e) {
    e.preventDefault();
    const alongThere = document.querySelector("#alongThere");
    const button = e.target.parentElement;
    const tr = button.parentElement.parentElement;
    const tds = tr.children;
    const name = tds[0].innerText;
    const placeIndex = ALONG_ROUTE_THERE.findIndex(p => p.name === name);
    const place = ALONG_ROUTE_THERE[placeIndex];
    const duration = tds[3].lastChild.value;
    const description = tds[4].lastChild.value;

    let added = false;
    let difference = 0;
    let destinationIndex = -1;
    for (let i = 1; i < ACTIVITIES.length - 1; i++) {
        let current = ACTIVITIES[i];
        if (!added && (current.description === "DESTINATION" || current.place.detourOffset > place.detourOffset)) {
            let previous = ACTIVITIES[i - 1];
            let next = ACTIVITIES[i];
            const arrival = timeAfterMinutes(
                previous.departure,
                (await apiGetRouteTime(previous.place, place).then(time => time) / 60).toFixed(0)
            );
            const departure = timeAfterMinutes(arrival, duration);
            ACTIVITIES.splice(i, 0, new Activity(
                place,
                duration,
                arrival,
                departure,
                i,
                description
            ));
            difference = minutesBetweenTimes(next.arrival,
                timeAfterMinutes(
                    departure,
                    (await apiGetRouteTime(place, next.place).then(time => time) / 60).toFixed(0)
                )
            );
            added = true;
        } else if (added) {
            current.arrival = timeAfterMinutes(current.arrival, difference);
            current.number++;
            if (current.description !== "DESTINATION") {
                current.departure = timeAfterMinutes(current.departure, difference);
            } else {
                current.duration -= difference;
                destinationIndex = i;
                break;
            }
        }
    }
    for (let i = destinationIndex + 1; i < ACTIVITIES.length; i++) {
        ACTIVITIES[i].number++;
    }
    ALONG_ROUTE_THERE.splice(placeIndex, 1);
    alongThere.removeChild(tr);
    renderActivities();
}

async function addToBack(e) {
    e.preventDefault();
    const alongBack = document.querySelector("#alongBack");
    const button = e.target.parentElement;
    const tr = button.parentElement.parentElement;
    const tds = tr.children;
    const name = tds[0].innerText;
    const placeIndex = ALONG_ROUTE_BACK.findIndex(p => p.name === name);
    const place = ALONG_ROUTE_BACK[placeIndex];
    const duration = tds[3].lastChild.value;
    const description = tds[4].lastChild.value;

    let added = false;
    let difference = 0;
    for (let i = ACTIVITIES.length - 2; i >= 1; i--) {
        let current = ACTIVITIES[i];
        if (!added && (current.description === "DESTINATION" || current.place.detourOffset < place.detourOffset)) {
            let previous = ACTIVITIES[i];
            let next = ACTIVITIES[i + 1];
            const departure = timeBeforeMinutes(
                next.arrival,
                (await apiGetRouteTime(place, next.place).then(time => time) / 60).toFixed(0)
            );
            const arrival = timeBeforeMinutes(departure, duration);
            ACTIVITIES.splice(i + 1, 0, new Activity(
                place,
                duration,
                arrival,
                departure,
                i + 1,
                description
            ));
            difference = minutesBetweenTimes(
                timeBeforeMinutes(
                    arrival,
                    (await apiGetRouteTime(previous.place, place).then(time => time) / 60).toFixed(0)
                ),
                previous.departure
            );
            added = true;
            i++; // step back
        } else if (added) {
            current.departure = timeBeforeMinutes(current.departure, difference);
            if (current.description !== "DESTINATION") {
                current.arrival = timeBeforeMinutes(current.arrival, difference);
            } else {
                current.duration -= difference;
                break;
            }
        } else {
            current.number++;
        }
    }
    ACTIVITIES[ACTIVITIES.length - 1].number++;
    ALONG_ROUTE_BACK.splice(placeIndex, 1);
    alongBack.removeChild(tr);
    renderActivities();
}

function apiGetPlace(query) {
    return fetch(
        API_HOST + '/rest/find-place',
        {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                "X-CSRF-Token": CSRF_TOKEN
            },
            body: "query=" + query,
            method: 'POST'
        }
    ).then(resp => {
            if (!resp.ok) {
                alert(resp.statusText);
            }
            return resp.json();
        }
    )
}

function apiGetAlongRoute(origin, destination, travelTime) {
    return fetch(
        API_HOST + '/rest/find-along-route/' + travelTime,
        {
            headers: {
                'Content-Type': 'application/json',
                "X-CSRF-Token": CSRF_TOKEN
            },
            body: JSON.stringify([origin, destination]),
            method: 'POST'
        }
    ).then(resp => {
            if (!resp.ok) {
                alert(resp.statusText);
            }
            return resp.json();
        }
    )
}

function apiGetRouteTime(origin, destination) {
    return fetch(
        API_HOST + '/rest/calculate-route',
        {
            headers: {
                'Content-Type': 'application/json',
                "X-CSRF-Token": CSRF_TOKEN
            },
            body: JSON.stringify([origin, destination]),
            method: 'POST'
        }
    ).then(resp => {
            if (!resp.ok) {
                alert(resp.statusText);
            }
            return resp.json();
        }
    )
}

function apiGetMapData() {
    return fetch(
        API_HOST + '/rest/find-route',
        {
            headers: {
                'Content-Type': 'application/json',
                "X-CSRF-Token": CSRF_TOKEN
            },
            body: JSON.stringify(ACTIVITIES),
            method: 'POST'
        }
    ).then(resp => {
            if (!resp.ok) {
                alert(resp.statusText);
            }
            return resp.json();
        }
    )
}

function timeAfterMinutes(time, minutes) {
    const timeH = parseInt(time.split(":")[0]);
    const timeM = parseInt(time.split(":")[1]);

    const minutesH = Math.floor(minutes / 60);
    const minutesM = minutes % 60;

    const summaryM = timeM + minutesM;
    const summaryH = timeH + minutesH + Math.floor(summaryM / 60);

    return String(summaryH % 24).padStart(2, '0') + ":" + String(summaryM % 60).padStart(2, '0');
}

function timeBeforeMinutes(time, minutes) {
    const timeH = parseInt(time.split(":")[0]);
    const timeM = parseInt(time.split(":")[1]);

    const minutesH = Math.floor(minutes / (60));
    const minutesM = minutes % 60;

    const summaryM = timeM - minutesM;
    const summaryH = timeH - minutesH - (summaryM < 0 ? 1 : 0);

    return String((24 + summaryH) % 24).padStart(2, '0') + ":" + String((60 + summaryM) % 60).padStart(2, '0');
}

function minutesBetweenTimes(start, end) {
    const startH = parseInt(start.split(":")[0]);
    const startM = parseInt(start.split(":")[1]);

    const endH = parseInt(end.split(":")[0]);
    const endM = parseInt(end.split(":")[1]);
    return ((endH * 60 + endM) - (startH * 60 + startM) + DAY) % (DAY);
}
