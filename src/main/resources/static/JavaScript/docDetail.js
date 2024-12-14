document.addEventListener("DOMContentLoaded", () => {
    const doctorId = getDoctorId();
    fetchDaysAndSlots(doctorId);
});

async function fetchDaysAndSlots(doctorId) {
    try {
        const response = await fetch(`/timeSlots/tsByDoc/${doctorId}`);
        if (!response.ok) {
            console.error(`Error fetching time slots: ${response.status}`);
        }
        const timeSlots = await response.json();
        populateDays(timeSlots);
    } catch (error) {
        console.error("Failed to fetch booking slots:", error);
    }
}

function populateDays(timeSlots) {
    const daysContainer = document.getElementById("day-container");
    const slotsContainer = document.getElementById("slot-container");

    daysContainer.innerHTML = "";
    slotsContainer.innerHTML = "";

    const groupedSlots = groupSlotsByDate(timeSlots);

    Object.keys(groupedSlots).forEach((date, index) => {
        const dayButton = document.createElement("button");
        const dayName = getDayName(new Date(date));
        const dayNumber = new Date(date).getDate();
        dayButton.textContent = `${dayName} ${dayNumber}`;
        dayButton.className = "day";
        if (index === 0) {
            dayButton.classList.add("active");
            populateSlots(groupedSlots[date]);
        }
        dayButton.addEventListener("click", () => {
            document.querySelectorAll(".day").forEach((btn) => btn.classList.remove("active"));
            dayButton.classList.add("active");
            populateSlots(groupedSlots[date]);
        });
        daysContainer.appendChild(dayButton);
    });
}

function populateSlots(slots) {
    const slotsContainer = document.getElementById("slot-container");
    slotsContainer.innerHTML = "";

    slots.forEach((slot) => {
        if (!slot.isBooked) {
            const slotButton = document.createElement("button");
            slotButton.textContent = `${slot.startTime} - ${slot.endTime}`;
            slotButton.className = "slot-button";
            slotButton.addEventListener("click", () => {
                document.querySelectorAll(".slot-button").forEach((s) => s.classList.remove("active"));
                slotButton.classList.add("active");
                selectSlot(slot);
            });
            slotsContainer.appendChild(slotButton);
        }
    });
}

function selectSlot(slot) {
    alert(`You selected: ${slot.startTime} - ${slot.endTime}`);
}

function groupSlotsByDate(slots) {
    return slots.reduce((grouped, slot) => {
        if (!grouped[slot.date]) {
            grouped[slot.date] = [];
        }
        grouped[slot.date].push(slot);
        return grouped;
    }, {});
}

function getDayName(date) {
    return date.toLocaleDateString("en-US", { weekday: "short" });
}

function getDoctorIdFromPath(){
    const path = window.location.pathname;
    const parts = path.split("/");
    return parts[parts.length - 1];
}
function getDoctorId(){
    const doctorId = getDoctorIdFromPath();
    if(!doctorId){
        console.error("Doctor ID is missing from the URL path.");
        return null;
    }
    return doctorId;
}