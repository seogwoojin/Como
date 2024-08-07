let timerInterval;
let remainingTime = 0;

function formatTime(seconds) {
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
}

function startTimer() {
    if (timerInterval) return; // Timer is already running
    timerInterval = setInterval(() => {
        if (remainingTime > 0) {
            remainingTime--;
            document.getElementById('timerDisplay').textContent = formatTime(remainingTime);
        } else {
            clearInterval(timerInterval);
            timerInterval = null;
        }
    }, 1000);
}

function stopTimer() {
    clearInterval(timerInterval);
    timerInterval = null;
}

function resetTimer() {
    clearInterval(timerInterval);
    timerInterval = null;
    document.getElementById('timerDisplay').textContent = formatTime(remainingTime);
}

function setTimer() {
    const minutes = parseInt(document.getElementById('timerMinutes').value, 10);
    remainingTime = isNaN(minutes) ? 0 : minutes * 60;
    document.getElementById('timerDisplay').textContent = formatTime(remainingTime);
}

document.addEventListener('DOMContentLoaded', () => {
    setTimer(); // Initialize timer display
});