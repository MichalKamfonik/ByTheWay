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
        let today = new Date().toISOString().slice(0, 10);
        let zeroDate = '1970-01-01';
        const plansDiv = document.querySelector("#plansCalendar");
        const days = plansDiv.querySelectorAll(".cell");
        days.forEach(day => {
            day.classList.remove("active");
            const month = day.parentElement.parentElement.parentElement;
            const date = month.dataset.date.split(" - ")[1]
                + "-"
                + String(parseInt(month.dataset.month) + 1).padStart(2, '0')
                + "-"
                + day.innerText;
            userPlans.forEach(plan => {
                if (checkDate(date, plan.startTime, plan.endTime)) {
                    day.classList.add("active");
                } else if (checkDate(date, zeroDate, today)) {
                    day.classList.add("disable");
                }
            });
        })
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