const apihost = window.location.origin;
const csrfToken = document.querySelector("#_CSRF_").value;
const activities = [];
let alongRouteThere = [];
let alongRouteBack = [];
const DAY = 24 * 60;

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

document.addEventListener('DOMContentLoaded', function () {
    const x = document.getElementsByClassName("tab");
    x[0].style.display = "block";

    const tripForm = document.querySelector('form');

    tripForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        x[0].style.display = "none";    // hide current tab
        x[1].style.display = "block";   // show next tab

        const departureTime = this.departure.value.split(":");
        const comebackTime = this.comeback.value.split(":");

        const tripStartInMinutes = departureTime[0] * 60 + parseInt(departureTime[1]);
        const tripEndInMinutes = comebackTime[0] * 60 + parseInt(comebackTime[1]);
        const tripDuration = (this.days.value - 1) * DAY + parseInt(tripEndInMinutes) - tripStartInMinutes;

        const placeOrigin = await apiGetPlace(this.origin.value).then(place => place);
        const placeDestination = await apiGetPlace(this.destination.value).then(place => place);

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
            timeBeforeMinutes(this.comeback.value, travelTimeBack),
            1,
            "DESTINATION"
        );
        const end = new Activity(
            placeOrigin,
            0,
            this.comeback.value,
            this.comeback.value,
            2,
            "ORIGIN"
        );

        activities.push(start);
        activities.push(destination);
        activities.push(end);

        renderActivities(activities);

        alongRouteThere = await apiGetAlongRoute(placeOrigin, placeDestination, travelTimeThere * 60);
        alongRouteBack = await apiGetAlongRoute(placeDestination, placeOrigin, travelTimeBack * 60);

        renderAlongRoute(alongRouteThere, "there");
        renderAlongRoute(alongRouteBack, "back")
    });
});

function apiGetPlace(query) {
    return fetch(
        apihost + '/rest/find-place',
        {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                "X-CSRF-Token": csrfToken
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

function timeAfterMinutes(time, minutes) {
    const timeH = parseInt(time.split(":")[0]);
    const timeM = parseInt(time.split(":")[1]);

    console.log("timeH",timeH);
    console.log("timeM",timeM);

    const minutesH = Math.floor(minutes / 60);
    const minutesM = minutes % 60;

    console.log("minutesH",minutesH);
    console.log("minutesM",minutesM);

    const summaryM = timeM + minutesM;
    const summaryH = timeH + minutesH + Math.floor(summaryM / 60);

    console.log("summaryM",summaryM);
    console.log("summaryH",summaryH);

    let result = String(summaryH % 24).padStart(2, '0') + ":" + String(summaryM % 60).padStart(2, '0');

    console.log("timeAfterMinutes(",time,",",minutes,") = ",result);

    return result;
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

function apiGetRouteTime(origin, destination) {
    return fetch(
        apihost + '/rest/calculate-route',
        {
            headers: {
                'Content-Type': 'application/json',
                "X-CSRF-Token": csrfToken
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

function apiGetAlongRoute(origin, destination, travelTime) {
    return fetch(
        apihost + '/rest/find-along-route/' + travelTime,
        {
            headers: {
                'Content-Type': 'application/json',
                "X-CSRF-Token": csrfToken
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
    for (let i = 0; i < activities.length - 1; i++) {
        const current = activities[i];
        const next = activities[i + 1];

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
    renderActivity(activities[activities.length - 1], activities.length * 2 - 1);
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
        const addTd = document.createElement("td");
        const addButton = document.createElement("button");
        addTd.appendChild(addButton);
        tr.appendChild(addTd);
        addButton.value = "add";
        if ("there" === direction) {
            addButton.addEventListener("click", addButtonListenerThere);
        } else {
            addButton.addEventListener("click", addButtonListenerBack);
        }

        alongRoute.appendChild(tr);
    })
}

function addButtonListenerThere(e) {
    e.preventDefault();
    showDurationAndDescription(e, "there");
}

function addButtonListenerBack(e) {
    e.preventDefault();
    showDurationAndDescription(e, "back");
}

function showDurationAndDescription(e, direction) {
    e.preventDefault();
    let button = e.target;
    let tr = e.target.parentElement.parentElement;
    let durationTd = document.createElement("td");
    durationTd.innerText = "Duration: ";
    tr.insertBefore(durationTd, button.parentElement);
    let durationInput = document.createElement("input");
    durationTd.type = "number";
    durationTd.appendChild(durationInput);
    let descriptionTd = document.createElement("td");
    descriptionTd.innerText = "Description: ";
    tr.insertBefore(descriptionTd, button.parentElement);
    let descriptionInput = document.createElement("input");
    descriptionTd.type = "text";
    descriptionTd.appendChild(descriptionInput);
    if ("there" === direction) {
        button.removeEventListener("click", addButtonListenerThere);
        button.addEventListener("click", addToThere);
    } else {
        button.removeEventListener("click", addButtonListenerBack);
        button.addEventListener("click", addToBack);
    }
}

async function addToThere(e) {
    e.preventDefault();
    const alongThere = document.querySelector("#alongThere");
    const button = e.target;
    const tr = button.parentElement.parentElement;
    const tds = tr.children;
    const name = tds[0].innerText;
    const placeIndex = alongRouteThere.findIndex(p => p.name === name);
    const place = alongRouteThere[placeIndex];
    const duration = tds[3].lastChild.value;
    const description = tds[4].lastChild.value;

    let added = false;
    let difference = 0;
    for (let i = 1; i < activities.length-1; i++) {
        let current = activities[i];
        if (!added && (current.description === "DESTINATION" || current.place.detourOffset > place.detourOffset)) {
            let previous = activities[i-1];
            let next = activities[i];
            const arrival = timeAfterMinutes(
                previous.departure,
                (await apiGetRouteTime(previous.place, place).then(time => time) / 60).toFixed(0)
            );
            const departure = timeAfterMinutes(arrival, duration);
            activities.splice(i, 0, new Activity(
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
            current.arrival = timeAfterMinutes(current.arrival,difference);
            current.number++;
            if(i<activities.length-2){
                current.departure = timeAfterMinutes(current.departure,difference);
            } else {
                current.duration -= difference;
            }
        }
    }
    activities[activities.length-1].number++;
    console.log(activities);

    alongRouteThere.splice(placeIndex,1);
    alongThere.removeChild(tr);
    renderActivities();
}

async function addToBack(e) {
    e.preventDefault();
}