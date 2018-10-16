var param = document.getElementById('user_id').value;

var xmlhttp = new XMLHttpRequest();

xmlhttp.open("GET", "/photo?id=" + param, true);
xmlhttp.send();

xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var img = document.getElementById("image");
        img.src = xmlhttp.responseText;
    }
}