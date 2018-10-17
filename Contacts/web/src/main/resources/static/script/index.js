function makeRequest(from, range, currentPage) {
    var requestString = "/get-contacts?from=" + from + "&range=" + range;

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function (ev) {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {

            document.getElementById('input-for-current-page').value = currentPage;

            var result = JSON.parse(xmlhttp.responseText);
            window.fillTable(result, currentPage);
        }
    };

    xmlhttp.open("GET", requestString, true);
    xmlhttp.send();
}

var previous = document.getElementById('previous');
var next = document.getElementById('next');

previous.addEventListener('click', prevFunc);
next.addEventListener('click', nextFunc);

var range = 10;

function prevFunc() {
    var currentPage = document.getElementById('input-for-current-page').value;

    if (currentPage > 1) {
        makeRequest((currentPage - 2) * range, range, currentPage - 1);
    }
}

function nextFunc() {
    var currentPage = +(document.getElementById('input-for-current-page').value);
    makeRequest(currentPage * range, range, 1 + currentPage);
}

makeRequest(0, 2, 1);