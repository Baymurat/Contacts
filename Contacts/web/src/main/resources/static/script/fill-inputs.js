function fillInputs(contact) {
    document.getElementById('name').value = contact.name;
    document.getElementById('surname').value = contact.surName;
    document.getElementById('middlename').value = contact.middleName;
    document.getElementById('datebirth').value = contact.birthDate;
    document.getElementById('gender').value = contact.gender;
    document.getElementById('citizenship').value = contact.citizenship;
    document.getElementById('family-status').value = contact.familyStatus;
    document.getElementById('web-site').value = contact.webSite;
    document.getElementById('email').value = contact.email;
    document.getElementById('currentjob').value = contact.currentJob;
    document.getElementById('country').value = contact.country;
    document.getElementById('city').value = contact.city;
    document.getElementById('street-house-apart').value = contact.streetHouseApart;
    document.getElementById('index').value = contact.index;
    
    fillPhoneTable(contact.phones);
    fillAttachmentsTable(contact.attachments);
}

function fillPhoneTable(list) {
    if(list) {
        for(var i = 0; i < list.length; i++) {
            var currentElement = list[i];
            var tableRow = document.createElement('tr');

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

            var fileName = document.createElement('td');
            var loadDate = document.createElement('td');
            var comments = document.createElement('td');

            fileName.innerHTML = currentElement.fileName;
            loadDate.innerHTML = currentElement.loadDate;
            comments.innerHTML = currentElement.comments;

            tableRow.appendChild(fileName);
            tableRow.appendChild(loadDate);
            tableRow.appendChild(comments);

            document.querySelector('#attachment-table>tbody').appendChild(tableRow);
        }
    }
}

fetch('/get-contact-for-edit').
    then(function(res) {
        return res.json();
    }).
    then(fillInputs);