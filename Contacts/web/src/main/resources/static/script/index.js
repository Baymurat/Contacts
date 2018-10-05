fetch('/get-contacts?from=0&range=10').
    then(function (res) {
        return res.json();
    }).
    then(fillIndex);

function fillIndex(result) {
    setButtonsStatus(false);
    var contacts = result.contactList;
    window.allContacts = contacts;

    console.log(allContacts);
    for (var i = 0; i < contacts.length; i++) {

        var currentElement = contacts[i];

        var tableRow = document.createElement('tr');

        var userName = document.createElement('td');
        var userSurname = document.createElement('td');
        var userPhone = document.createElement('td');
        var dataAttachment = document.createElement('td');
        var checkBox = document.createElement('input');
        checkBox.type = 'checkbox';

        userName.innerHTML = currentElement.name;
        userSurname.innerHTML = currentElement.surName;
        userPhone.innerHTML = currentElement.email;
        dataAttachment.innerHTML = currentElement.webSite;

        tableRow.id = currentElement.id;
        tableRow.appendChild(checkBox);
        tableRow.appendChild(userName);
        tableRow.appendChild(userSurname);
        tableRow.appendChild(userPhone);
        tableRow.appendChild(dataAttachment);

        document.querySelector('#myTable>tbody').appendChild(tableRow);
    }
    toggleCheckBoxes();
}

var myTable = document.getElementById('myTable');
myTable.addEventListener('click', function (e) {
    var target = e.target;
    var parentNode = target.parentNode;
    if (target && target.tagName == 'TD') {
        if (!parentNode.classList.contains('selected')) {
            var selectedRow = myTable.querySelector('.selected');
            if (selectedRow) {
                selectedRow.classList.toggle('selected');
            }
        }
        parentNode.classList.toggle('selected');
    }

    setButtonsStatus(parentNode.classList.contains('selected'));
})

document.querySelector('#check-boxes-button').addEventListener('click', function() {
    toggleButton();
    toggleCheckBoxes();
});

function setButtonsStatus(status) {
    document.querySelector('#edit-button').disabled = !status;
    document.querySelector('#delete-button').disabled = !status;
    document.querySelector('#about-button').disabled = !status;
}

function toggleCheckBoxes() {
    var checkBoxes = document.querySelectorAll('input[type=checkbox]');

    for (var i = 0; i < checkBoxes.length; i++) {
        checkBoxes[i].classList.toggle('disable');
    }
    document.querySelector('#boxes').classList.toggle('disable');
}

function toggleButton() {
    var button = document.querySelector('#check-boxes-button');

    if (button.state === 'disabled') {
        button.innerHTML = "Disable Check Boxes";
        button.state = 'enabled';
    } else {
        button.innerHTML = "Enable Check Boxes";
        button.state = 'disabled';
    }

}