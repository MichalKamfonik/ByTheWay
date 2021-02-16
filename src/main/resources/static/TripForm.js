const apihost = window.location.origin;
const csrfToken = document.querySelector("#_CSRF_").value;

document.addEventListener('DOMContentLoaded', function () {
    const tripForm = document.querySelector('form');

    tripForm.addEventListener('submit', async function (e) {
        e.preventDefault()
        let placeOrigin      = await apiGetPlace(this.origin.value     ).then(place => place);
        let placeDestination = await apiGetPlace(this.destination.value).then(place => place);

        let travelTimeThere = await apiGetRouteTime(placeOrigin, placeDestination).then(time => time);
        let travelTimeBack  = await apiGetRouteTime(placeDestination, placeOrigin).then(time => time);

        let alongRouteThere = await apiGetAlongRoute(placeOrigin, placeDestination, travelTimeThere);
        let alongRouteBack  = await apiGetAlongRoute(placeDestination, placeOrigin, travelTimeBack );

        console.log(alongRouteThere);
        console.log(alongRouteBack);
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
        apihost + '/rest/find-along-route/'+travelTime,
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

function renderActivity(name) {
    const trip = document.querySelector("#trip");

    const tr = document.createElement("tr");
    trip.appendChild(tr);
    const nrTd = document.createElement("td");
    nrTd.innerText = "1";
    tr.appendChild(nrTd);
    const hourTd = document.createElement("td");
    hourTd.innerText = "1";
    tr.appendChild(hourTd);
    const nameTd = document.createElement("td");
    nameTd.innerText = name;
    tr.appendChild(nameTd);
    const durationTd = document.createElement("td");
    durationTd.innerText = "1";
    tr.appendChild(durationTd);
    const descriptionTd = document.createElement("td");
    descriptionTd.innerText = "1";
    tr.appendChild(descriptionTd);
}