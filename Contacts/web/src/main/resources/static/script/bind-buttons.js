var addButton = document.getElementById('add-button');
var editButton = document.getElementById('edit-button');
var deleteButton = document.getElementById('delete-button');
var searchButton = document.getElementById('go-over-search-button');
var sendButton = document.getElementById('send-button');
var aboutButton = document.getElementById('about-button');
var checkBoxesButton = document.querySelector('#check-boxes-button')

function addFunction() {
    window.location.replace('/add');
}

function editFunction() {
    var selectedRow = document.querySelector('.selected');
    if (selectedRow.id) {
        var url = '/edit?id=' + selectedRow.id;
        window.location.replace(url);
    }
}

function deleteFunction() {
    var selectedRow = document.querySelector('.selected');
    var url = '/delete-record';
    var param = 'id=' + selectedRow.id;

    var xmlhhtp = new XMLHttpRequest();
    xmlhhtp.open('GET', url + '?' + param, true);
    xmlhhtp.send();
    window.location.reload();
}

function searchFunction() {
    window.location.replace('/search');
}

function sendFunction() {

}

function aboutFunction() {
    var selectedRow = document.querySelector('.selected');
    if (selectedRow.id) {
        var url = '/about?id=' + selectedRow.id;
        window.location.replace(url);
    }
}

function toggleCheckBoxes() {
    var checkBoxes = document.querySelectorAll('input[type=checkbox]');

    for (var i = 0; i < checkBoxes.length; i++) {
        checkBoxes[i].classList.toggle('disable');
    }
    document.querySelector('#boxes').classList.toggle('disable');

    var button = document.querySelector('#check-boxes-button');

    if (button.state === 'disabled') {
        button.innerHTML = "Disable Check Boxes";
        button.state = 'enabled';
    } else {
        button.innerHTML = "Enable Check Boxes";
        button.state = 'disabled';
    }
}

if (addButton) {
    addButton.addEventListener('click', addFunction);
}

if (searchButton) {
    searchButton.addEventListener('click', searchFunction);
}
editButton.addEventListener('click', editFunction);
deleteButton.addEventListener('click', deleteFunction);
sendButton.addEventListener('click', sendFunction);
aboutButton.addEventListener('click', aboutFunction);
checkBoxesButton.addEventListener('click', toggleCheckBoxes);

toggleCheckBoxes();



