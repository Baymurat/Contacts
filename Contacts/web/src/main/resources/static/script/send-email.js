var sendEmailButton = document.getElementById('send-email-button');

function sendMail() {
    console.log('send email');
    var receivers = [];
    receivers = document.getElementById('receivers').value.split(' ');
    var messageTheme = document.getElementById('theme').value;
    var messageContent = document.getElementById('content').value;

    var message = {};

    message.receivers = receivers;
    message.messageText = messageContent;
    message.messageTheme = messageTheme;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', '/send-email', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json')
    xmlhttp.send(JSON.stringify(message));

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            window.location.replace('/index');
        }
    }
}

sendEmailButton.addEventListener('click', sendMail);

var cancelButton = document.getElementById('cancel-button');
cancelButton.addEventListener('click', function() {
    window.location.replace('/index');
});