var sendEmailButton = document.getElementById('send-email2-button');

function sendMail() {
    console.log('send email');

    var url = new URL(window.location.href);
    var params = url.searchParams.getAll("to");

    var messageTheme = document.getElementById('theme').value;
    var messageContent = document.getElementById('content').value;

    var message = {};

    message.receivers = params;
    message.messageText = messageContent;
    message.messageTheme = messageTheme;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', 'send-email', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json')
    xmlhttp.send(JSON.stringify(message));

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            window.location.href = 'index';
        }
    }
}

sendEmailButton.addEventListener('click', sendMail);

var cancelButton = document.getElementById('back-to-index');
cancelButton.addEventListener('click', function() {
    window.location.href = 'index';
});