function confirmOpen(index, type) {
    var modal = document.getElementById('confirmModal');
    var confirmButton = document.getElementById('confirmButton');
    confirmButton.setAttribute('data-index', index);
    confirmButton.setAttribute('data-type', type);
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById('confirmModal');
    modal.style.display = "none";
}

function openInfo() {
    var index = document.getElementById('confirmButton').getAttribute('data-index');
    var type = document.getElementById('confirmButton').getAttribute('data-type');
    var infoDiv = document.getElementById(type + 'CloseInfo-' + index);
    var toggleLink = document.getElementById(type + 'ToggleLink-' + index);
    infoDiv.style.display = 'block';
    toggleLink.style.display = 'none';
    closeModal();
}

window.onclick = function(event) {
    var modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function toggleInfo(index, type) {
    confirmOpen(index, type);
}

var timerInterval;
function startTimer(duration, display) {
    var timer = duration, hours, minutes, seconds;
    timerInterval = setInterval(function () {
        hours = parseInt(timer / 3600, 10);
        minutes = parseInt((timer % 3600) / 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = hours + ":" + minutes + ":" + seconds;

        if (--timer < 0) {
            clearInterval(timerInterval);
            display.textContent = "Time's up!";
        }
    }, 1000);
}

function initializeTimer() {
    var twoHours = 60 * 60 * 2;
    var display = document.querySelector('#timer');
    startTimer(twoHours, display);
}