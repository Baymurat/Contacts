function fillInputs(contact) {
    if (document.getElementById('user_id')) {
        document.getElementById('user_id').value = contact.id;
    }

    document.getElementById('name').value = contact.name;
    document.getElementById('surname').value = contact.surName;
    document.getElementById('middlename').value = contact.middleName;
    document.getElementById('datebirth').value = contact.birthDate;
    document.getElementById('citizenship').value = contact.citizenship;
    document.getElementById('web-site').value = contact.webSite;
    document.getElementById('email').value = contact.email;
    document.getElementById('currentjob').value = contact.currentJob;
    document.getElementById('country').value = contact.country;
    document.getElementById('city').value = contact.city;
    document.getElementById('street-house-apart').value = contact.streetHouseApart;
    document.getElementById('index').value = contact.index;

    var gender = document.getElementById('gender');
    if (contact.gender === 'male') {
        gender.selectedIndex = 0;
    } else {
        gender.selectedIndex = 1;
    }

    var familyStatus = document.getElementById('family-status');
    if (contact.gender === 'single') {
        familyStatus.selectedIndex = 0;
    } else {
        familyStatus.selectedIndex = 1;
    }

    fillPhoneTable(contact.phones);
    fillAttachmentsTable(contact.attachments);
}

function fillPhoneTable(list) {
    if(list) {
        for(var i = 0; i < list.length; i++) {
            var currentElement = list[i];
            var tableRow = document.createElement('tr');

            tableRow.phoneId = list[i].id;

            var number = document.createElement('td');
            var type = document.createElement('td');
            var comments = document.createElement('td');

            var countryCode = currentElement.codeOfCountry;
            var operatorCode = currentElement.codeOfOperator;
            var phoneNumber = currentElement.phoneNumber;

            number.innerHTML = countryCode + " " + operatorCode + " " + phoneNumber;
            type.innerHTML = currentElement.type;
            comments.innerHTML = currentElement.comments;

            tableRow.appendChild(number);
            tableRow.appendChild(type);
            tableRow.appendChild(comments);

            document.querySelector('#phone-table>tbody').appendChild(tableRow);
        }
    }
}

function fillAttachmentsTable(list) {
    if(list) {
        for(var i = 0; i < list.length; i++) {
            var currentElement = list[i];
            var tableRow = document.createElement('tr');

            tableRow.attachId = list[i].id;

            var fileName = document.createElement('td');
            var loadDate = document.createElement('td');
            var comments = document.createElement('td');
            var idHolder = document.createElement('td');
            idHolder.style.display = 'none';

            idHolder.innerHTML = currentElement.id;
            fileName.innerHTML = currentElement.fileName;
            loadDate.innerHTML = currentElement.loadDate;
            comments.innerHTML = currentElement.comments;

            tableRow.appendChild(idHolder);
            tableRow.appendChild(fileName);
            tableRow.appendChild(loadDate);
            tableRow.appendChild(comments);

            document.querySelector('#attachment-table>tbody').appendChild(tableRow);
        }
    }
}

var cancelButton = document.getElementById('cancel-button');
cancelButton.addEventListener('click', function () {
    window.location.replace('/index');
});

var url = new URL(window.location.href);
var id = url.searchParams.get("id");

var xmlhttp = new XMLHttpRequest();
xmlhttp.open("GET", '/get-contact?id=' + id, true);
xmlhttp.send();

xmlhttp.onreadystatechange = function (ev) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var contact = JSON.parse(xmlhttp.responseText);
        fillInputs(contact);
    }
}