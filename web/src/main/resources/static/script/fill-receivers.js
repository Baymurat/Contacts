var url = new URL(window.location.href);
var params = url.searchParams.getAll("to");
var sendObject = JSON.stringify(params);
var rec2 = '';

var xmlhttp = new XMLHttpRequest();

xmlhttp.onreadystatechange = function (ev) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var response = JSON.parse(xmlhttp.responseText);

        if (response) {
            for (var i = 0; i < response.length; i++) {
                rec2 += response[i].email + " ";
                var receivers = document.querySelector('#receivers');
                receivers.value = rec2;
            }
        }
    }
};

xmlhttp.open('POST', 'get-receivers', true);
xmlhttp.setRequestHeader('Content-Type', 'application/json');
xmlhttp.send(sendObject);



