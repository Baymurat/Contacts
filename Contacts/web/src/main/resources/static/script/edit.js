var shareContact;

function fillInputs(contact) {
    shareContact = contact;
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

//UPDATE 
function editRecordFunction(contact) {
    contact.name = document.getElementById('name').value;
    contact.surName = document.getElementById('surname').value;
    contact.middleName = document.getElementById('middlename').value;

    let elementG = document.getElementById('gender');
    contact.gender = elementG.options[elementG.selectedIndex].text;
    contact.citizenship = document.getElementById('citizenship').value;

    let elementFS = document.getElementById('family-status');
    contact.familyStatus = elementFS.options[elementFS.selectedIndex].text;
    contact.webSite = document.getElementById('web-site').value;
    contact.email = document.getElementById('email').value;
    contact.currentJob = document.getElementById('currentjob').value;

    contact.country = document.getElementById('country').value;
    contact.city = document.getElementById('city').value;
    contact.steetHouseApart = document.getElementById('street-house-apart').value;
    contact.index = document.getElementById('index').value;
    //contact.birthDate  = new Date();
    contact.phones = getPhones();
    contact.attachments = getAttachments();

    let objectSend = JSON.stringify(contact);

    if (contact.name && contact.surName && contact.middleName) {
        console.log(objectSend);
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/update-record", true);
        xmlhttp.setRequestHeader('Content-Type', 'application/json');
        xmlhttp.send(objectSend);

        return true;
    } else {
        alert("POPULATE FIELDS");
        return false;
    }
}

function getPhones() {
    var phones = [];
    var phone = {};

    var table = document.getElementById('phone-table');
    var rowLength = table.rows.length;

    for (var i = 1; i < rowLength; i++) {
        var cells = table.rows.item(i).cells;

        var fullPhone = cells.item(0).innerHTML.split(' ');
        phone.codeOfCountry = fullPhone[0];
        phone.codeOfOperator = fullPhone[1];
        phone.phoneNumber = fullPhone[2];
        phone.type = cells.item(1).innerHTML;
        phone.comments = cells.item(2).innerHTML;
        phones.push(phone);
    }

    return phones;
}

function getAttachments() {
    var attachments = [];
    var attachment = {};

    var table = document.getElementById('attachment-table');
    var rowLength = table.rows.length;

    for (var i = 1; i < rowLength; i++) {
        var cells = table.rows.item(i).cells;

        attachment.fileName = cells.item(0).innerHTML;
        attachment.loadDate = new Date().getTime();
        attachment.comments = cells.item(2).innerHTML;

        attachments.push(attachment);
    }

    return attachments;
}

let acceptButton = document.getElementById('accept-button');
acceptButton.addEventListener('click', function () {
    if (editRecordFunction(shareContact)) {
        window.location.replace('/index');
    }

});

let cancelButton = document.getElementById('cancel-button');
cancelButton.addEventListener('click', function () {
    window.location.replace('/index');
});