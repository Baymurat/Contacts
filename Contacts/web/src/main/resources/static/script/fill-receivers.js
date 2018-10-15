var url = new URL(window.location.href);
var params = url.searchParams.getAll("to");
var rec2 = '';

for (var i = 0; i < params.length; i++) {
    rec2 += params[i] + ' ';
}
var receivers = document.querySelector('#receivers');
receivers.value = rec2;

