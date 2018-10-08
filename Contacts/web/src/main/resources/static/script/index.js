var xmlhttp = new XMLHttpRequest();
xmlhttp.open("GET", "/get-contacts?from=0&range=10", true);
xmlhttp.send();
xmlhttp.onreadystatechange = function (ev) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var result = JSON.parse(xmlhttp.responseText);
        window.fillTable(result);
    }
}