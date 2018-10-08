var addButton = document.getElementById('add-button');
var editButton = document.getElementById('edit-button');
var deleteButton = document.getElementById('delete-button');
var searchButton = document.getElementById('go-over-search-button');
var sendButton = document.getElementById('send-button');
var aboutButton = document.getElementById('about-button');
var checkBoxesButton = document.querySelector('#check-boxes-button');
var myTable = document.getElementById('myTable');

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
    var selectedRows = document.querySelectorAll('.selected');
    if (selectedRows.length > 0) {
        var deleteContactsId = [];

        for (var i = 0; i < selectedRows.length; i++) {
            deleteContactsId.push(selectedRows[i].id);
        }


        var xmlhhtp = new XMLHttpRequest();
        xmlhhtp.open('POST', '/delete-record', true);
        xmlhhtp.setRequestHeader('Content-Type', 'application/json');
        xmlhhtp.send(JSON.stringify(deleteContactsId));
        xmlhhtp.onreadystatechange = function (ev) {
            if (xmlhhtp.readyState === 4 && xmlhhtp.status === 200) {
                window.location.reload();
            }
        }
    }
}

function searchFunction() {
    window.location.replace('/search');
}

function sendFunction() {
    var selectedRows = document.querySelectorAll('.selected');
    var params = '?';

    for (var i = 0; i < selectedRows.length; i++) {
        params += i + 1 + '=' + selectedRows[i].cells[2].innerHTML + '&';
    }

    window.location.replace('/email' + params);
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
        checkBoxes[i].checked = false;
    }
    document.querySelector('#boxes').classList.toggle('disable');

    var button = document.querySelector('#check-boxes-button');

    setButtonsStatus(false);

    if (button.state === 'disabled') {
        button.innerHTML = "Disable Check Boxes";
        button.state = 'enabled';
        myTable.removeEventListener('click', singleChoice);
        myTable.addEventListener('click', multipleChoice);
        setButtonsStatus(true);
    } else {
        button.innerHTML = "Enable Check Boxes";
        button.state = 'disabled';
        myTable.removeEventListener('click', multipleChoice);
        myTable.addEventListener('click', singleChoice);
    }

    var selectedRows = document.getElementsByClassName('selected');
    if (selectedRows) {
        while (selectedRows.length > 0) {
            selectedRows[0].classList.toggle('selected');
        }
    }
}

function setButtonsStatus(status) {
    document.querySelector('#edit-button').disabled = !status;
    document.querySelector('#delete-button').disabled = !status;
    document.querySelector('#about-button').disabled = !status;
    document.querySelector('#send-button').disabled = !status;
}

function singleChoice(e) {
    var target = e.target;
    var parentNode = target.parentNode;

    if (target && target.tagName === 'TD') {
        if (!parentNode.classList.contains('selected')) {
            var selectedRow = myTable.querySelector('.selected');
            if (selectedRow) {
                selectedRow.classList.toggle('selected');
            }
        }
        parentNode.classList.toggle('selected');
    }

    setButtonsStatus(parentNode.classList.contains('selected'));
}

function multipleChoice(e) {
    var target = e.target;
    var parentNode = target.parentNode;
    var checkBox = parentNode.firstChild;

    if (target && (target.tagName === 'TD' || target.tagName === 'INPUT')) {
        parentNode.classList.toggle('selected');
        checkBox.checked = checkBox.checked !== true;
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



