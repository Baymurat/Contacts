var selectElement = document.getElementById('patterns');

function changeFunction() {
    var selectedIndex = selectElement.selectedIndex;

    var themeElement = document.getElementById('theme');
    var textArea = document.getElementById('content');

    themeElement.value = selectElement.options[selectedIndex].text;
    textArea.value = patterns[selectedIndex + 1];
}

selectElement.addEventListener('change', changeFunction);

var patterns = {
    1 : "Happy New year, $name$",
    2 : "Happy birthday, $name$",
    3 : "One more pattern, $name$"
}
