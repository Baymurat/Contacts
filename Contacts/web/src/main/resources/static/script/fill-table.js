window.fillTable = function fillIndex(result, currentPage) {
    var contacts = result.contactList;

    if (contacts) {
        var new_body = document.createElement('tbody');

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

            //new rows
            var link = document.createElement('a');
            link.href = "edit?id=" + currentElement.id;

            //new row
            link.innerHTML = currentElement.name;
            userSurname.innerHTML = currentElement.surName;
            userEmail.innerHTML = currentElement.email;
            dataAttachment.innerHTML = currentElement.webSite;

            //new row
            userName.appendChild(link);

            tableRow.id = currentElement.id;
            tableRow.appendChild(checkBox);
            tableRow.appendChild(userName);
            tableRow.appendChild(userSurname);
            tableRow.appendChild(userEmail);
            tableRow.appendChild(dataAttachment);

            new_body.appendChild(tableRow);
        }

        var old_body = document.querySelector('.table tbody');
        old_body.parentNode.replaceChild(new_body, old_body);

        var range = 10;
        if (previous && next) {
            previous.disabled = currentPage <= 1;
            next.disabled = currentPage * range >= result.allElementsCount;
        }
    }
};

var previous = document.getElementById('previous');
var next = document.getElementById('next');