document.addEventListener("DOMContentLoaded", () => {
    const doctorId = getDoctorId();
    fetchDaysAndSlots(doctorId);

    const slotContainer = document.getElementById("slot-container");
    const timeSlotInput = document.getElementById("timeSlotId");
    const bookbtn = document.getElementById("bookButton");
    const errorMsg = document.getElementById("error-message");

    slotContainer.addEventListener("click",(event)=>{
        if(event.target.classList.contains("slot-button")){
            document.querySelectorAll(".slot-button").forEach(slot=>slot.classList.remove("active"));
            event.target.classList.add("active");
            timeSlotInput.value=event.target.dataset.slotId;
            errorMsg.style.display="none";
        }
    });
    bookbtn.addEventListener("click",(event)=>{
        if(!timeSlotInput.value){
            event.preventDefault();
            errorMsg.style.display = "block";
            return;
        }
        showNotification();
    });
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
    slotsContainer.innerHTML = ""; // Clear previous slots

    slots.forEach((slot) => {
        const slotButton = document.createElement("button");
        slotButton.textContent = `${slot.startTime} - ${slot.endTime}`;
        slotButton.className = "slot-button";
        slotButton.dataset.slotId = slot.slotId;


        if (slot.is_booked===1||slot.is_booked==="1"||slot.is_booked===true) {
            slotButton.classList.add("booked");
            slotButton.disabled = true;
        } else {

            slotButton.addEventListener("click", () => {
                document.querySelectorAll(".slot-button").forEach((s) => s.classList.remove("active"));
                slotButton.classList.add("active");
                selectSlot(slot);
            });
        }
        slotsContainer.appendChild(slotButton);
    });
}


function selectSlot(slot) {
    if(!slot.slot_id){
        console.error("Invalid slot selected: ",slot);
    }
    const hiddenInput = document.getElementById("timeSlotId");
    hiddenInput.value = slot.slot_id;
    console.log(`Selected slot: ${slot.startTime} - ${slot.endTime}`);
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
function showNotification(){
    const notification = document.getElementById("notification");
    notification.classList.remove("hidden");
    setTimeout(()=>{
        notification.classList.add("hidden");
    },3000);
}
