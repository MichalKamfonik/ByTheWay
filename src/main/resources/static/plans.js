document.addEventListener('DOMContentLoaded', function () {
    const nextMonth = document.querySelector("#nextMonth");
    const prevMonth = document.querySelector("#prevMonth");

    setTimeout(() => {
            updateDays();
            prevMonth.addEventListener("click", updateDays);
            nextMonth.addEventListener("click", updateDays);
        },
        1000);


    function updateDays() {
        const formDiv = document.querySelector("#planForm");
        formDiv.style.display = "none";
        let dayChosen = false;

        let today = new Date().toISOString().slice(0, 10);
        let zeroDate = '1970-01-01';
        const plansDiv = document.querySelector("#plansCalendar");
        const days = plansDiv.querySelectorAll(".cell");
        days.forEach(day => {
            const month = day.parentElement.parentElement.parentElement;
            const date = month.dataset.date.split(" - ")[1]
                + "-"
                + String(parseInt(month.dataset.month) + 1).padStart(2, '0')
                + "-"
                + day.innerText.padStart(2,"0");
            if (checkDate(date, zeroDate, today)) {
                day.classList.remove("active");
                day.classList.add("disable");
            }
            userPlans.forEach(plan => {
                if (checkDate(date, plan.startTime, plan.endTime)) {
                    day.classList.add("active");
                    day.classList.remove("disable");
                    day.addEventListener("mouseover",function (e){
                        e.stopPropagation();
                        const planDetails = document.createElement("div");
                        this.appendChild(planDetails);
                        planDetails.classList.add("planDetails");
                        planDetails.innerHTML = "<b>"+plan.trip +"</b><br>from "+ plan.startTime + " to " + plan.endTime;
                    });
                    day.addEventListener("mouseout",function (e){
                        e.stopPropagation();
                        this.removeChild(this.querySelector(".planDetails"));
                    });
                }
            });
            if(!day.classList.contains("active") && !day.classList.contains("disable")){
                day.addEventListener("click",function(e){
                    if(!dayChosen){
                        formDiv.style.display = "flex";
                        this.firstChild.classList.add("chosen");
                        formDiv.querySelector("#startTime").value = date.split("-").reverse().join("\.");
                        dayChosen = true;
                    } else if(this.firstChild.classList.contains("chosen")){
                        formDiv.style.display = "none";
                        this.firstChild.classList.remove("chosen");
                        formDiv.querySelector("#startTime").value = "";
                        dayChosen = false;
                    }
                });
            }
        });
    }

    function checkDate(dateCheck, dateFrom, dateTo) {

        let d1 = dateFrom.split("-");
        let d2 = dateTo.split("-");
        let c = dateCheck.split("-");

        let from = new Date(d1[0], parseInt(d1[1]) - 1, d1[2]);  // -1 because months are from 0 to 11
        let to = new Date(d2[0], parseInt(d2[1]) - 1, d2[2]);
        let check = new Date(c[0], parseInt(c[1]) - 1, c[2]);

        return check >= from && check <= to;
    }
});