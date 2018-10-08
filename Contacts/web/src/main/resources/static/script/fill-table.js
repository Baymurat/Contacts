window.fillTable = function fillIndex(result) {
    var contacts = result.contactList;

    console.log(contacts);
    for (var i = 0; i < contacts.length; i++) {

        var currentElement = contacts[i];

        var tableRow = document.createElement('tr');

        var userName = document.createElement('td');
        var userSurname = document.createElement('td');
        var userPhone = document.createElement('td');
        var dataAttachment = document.createElement('td');
        var checkBox = document.createElement('input');
        checkBox.type = 'checkbox';
        checkBox.classList.add('disable');

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

    setButtonsStatus(false);
};

function setButtonsStatus(status) {
    document.querySelector('#edit-button').disabled = !status;
    document.querySelector('#delete-button').disabled = !status;
    document.querySelector('#about-button').disabled = !status;
}

var myTable = document.getElementById('myTable');
myTable.addEventListener('click', function (e) {
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
});