var acceptButton = document.getElementById('accept-button');
acceptButton.addEventListener('click', function (ev) {
    window.sendRecord('/add-record');
});

var cancelButton = document.getElementById('back-to-index');
cancelButton.addEventListener('click', function () {
    window.location.replace('/index');
});