var addButton = document.getElementById('add-button');
var editButton = document.getElementById('edit-button');
var deleteButton = document.getElementById('delete-button');
var searchButton = document.getElementById('search-button');
var sendButton = document.getElementById('send-button');
var aboutButton = document.getElementById('about-button');

function addFunction() {
    window.location.replace('/add');
}

function editFunction() {
    var selectedRow = document.querySelector('.selected');
    var contacts = window.allContacts;
    var editContact;

    for (var i = 0; i < contacts.length; i++) {
        if (selectedRow.id == contacts[i].id) {
            editContact = contacts[i];
            break;
        }
    }

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', '/set-contact-for-edit', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(editContact));
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4) {
            window.location.replace('/edit');
        }
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
    var contacts = window.allContacts;
    var editContact;

    if (selectedRow) {
        for (var i = 0; i < contacts.length; i++) {
            if (selectedRow.id == contacts[i].id) {
                editContact = contacts[i];
                break;
            }
        }

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('POST', '/set-contact-for-edit', true);
        xmlhttp.setRequestHeader('Content-Type', 'application/json');
        xmlhttp.send(JSON.stringify(editContact));
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4) {
                window.location.replace('/about');
            }
        }
    }
}

addButton.addEventListener('click', addFunction);
editButton.addEventListener('click', editFunction);
deleteButton.addEventListener('click', deleteFunction);
searchButton.addEventListener('click', searchFunction);
sendButton.addEventListener('click', sendFunction);
aboutButton.addEventListener('click', aboutFunction);