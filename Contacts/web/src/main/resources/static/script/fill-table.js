window.fillTable = function fillIndex(result) {
    var contacts = result.contactList;

    console.log(contacts);
    for (var i = 0; i < contacts.length; i++) {

        var currentElement = contacts[i];

        var tableRow = document.createElement('tr');

        var userName = document.createElement('td');
        var userSurname = document.createElement('td');
        var userEmail = document.createElement('td');
        userEmail.name = 'email';
        var dataAttachment = document.createElement('td');
        var checkBox = document.createElement('input');
        checkBox.type = 'checkbox';
        checkBox.classList.add('disable');

        userName.innerHTML = currentElement.name;
        userSurname.innerHTML = currentElement.surName;
        userEmail.innerHTML = currentElement.email;
        dataAttachment.innerHTML = currentElement.webSite;

        tableRow.id = currentElement.id;
        tableRow.appendChild(checkBox);
        tableRow.appendChild(userName);
        tableRow.appendChild(userSurname);
        tableRow.appendChild(userEmail);
        tableRow.appendChild(dataAttachment);

        document.querySelector('#myTable>tbody').appendChild(tableRow);
    }
};

