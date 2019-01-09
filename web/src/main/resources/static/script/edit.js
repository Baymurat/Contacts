var acceptButton = document.getElementById('accept-button');
acceptButton.addEventListener('click', function (ev) {
    window.sendRecord('update-record');
});