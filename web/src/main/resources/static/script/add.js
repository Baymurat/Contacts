var acceptButton = document.getElementById('accept-button');
acceptButton.addEventListener('click', function (ev) {
    window.sendRecord('addRecord');
});

var cancelButton = document.getElementById('back-to-index');
cancelButton.addEventListener('click', function () {
    window.location.href = 'index';
});