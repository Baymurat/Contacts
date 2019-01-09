var selectElement = document.getElementById('patterns');
selectElement.addEventListener('change', changeFunction);

var patterns = {};

function changeFunction() {
    var selectedIndex = selectElement.selectedIndex;

    var themeElement = document.getElementById('theme');
    var textArea = document.getElementById('content');

    themeElement.value = selectElement.options[selectedIndex].text;
    textArea.value = patterns[themeElement.value];
}

function addOption(text) {
    var option = document.createElement("option");
    option.text = text;
    selectElement.add(option);
}



var xmlhttp = new XMLHttpRequest();

xmlhttp.onreadystatechange = function (ev) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var result = JSON.parse(xmlhttp.responseText);

        console.log("ANSWERED");
        console.log(result);

        if (result) {
            for (var i = 0; i < result.length; i++) {
                patterns[result[i].theme] = result[i].content;
                addOption(result[i].theme);
            }
        }
    }
};

xmlhttp.open("GET", "/message-patterns", true);
xmlhttp.send();