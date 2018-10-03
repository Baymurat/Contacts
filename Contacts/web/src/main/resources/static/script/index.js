function fillIndex(contacts) {
    window.allContacts = contacts;
    console.log(allContacts);
    for (var i = 0; i < contacts.length; i++) {

        var currentElement = contacts[i];

        var tableRow = document.createElement('tr');

        var userName = document.createElement('td');
        var userSurname = document.createElement('td');
        var userPhone = document.createElement('td');
        var dataAttachment = document.createElement('td');

        userName.innerHTML = currentElement.name;
        userSurname.innerHTML = currentElement.surName;
        userPhone.innerHTML = currentElement.email;
        dataAttachment.innerHTML = currentElement.webSite;

        tableRow.id = currentElement.id;
        tableRow.appendChild(userName);
        tableRow.appendChild(userSurname);
        tableRow.appendChild(userPhone);
        tableRow.appendChild(dataAttachment);

        document.querySelector('#myTable>tbody').appendChild(tableRow);
    }
}

fetch('/fill-index').
    then(function (res) {
        return res.json();
    }).
    then(fillIndex);

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
})