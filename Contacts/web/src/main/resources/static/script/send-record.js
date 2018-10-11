window.sendRecord = function (url) {

    var contact = {};

    contact.name = document.getElementById('name').value;
    contact.surName = document.getElementById('surname').value;
    contact.middleName = document.getElementById('middlename').value;

    var elementG = document.getElementById('gender');
    contact.gender = elementG.options[elementG.selectedIndex].text;
    contact.citizenship = document.getElementById('citizenship').value;

    var elementFS = document.getElementById('family-status');
    contact.familyStatus = elementFS.options[elementFS.selectedIndex].text;
    contact.webSite = document.getElementById('web-site').value;
    contact.email = document.getElementById('email').value;
    contact.currentJob = document.getElementById('currentjob').value;

    contact.country = document.getElementById('country').value;
    contact.city = document.getElementById('city').value;
    contact.steetHouseApart = document.getElementById('street-house-apart').value;
    contact.index = document.getElementById('index').value;
    contact.phones = getPhones();
    contact.attachments = getAttachments();

    contact.deletePhonesList = window.deletePhonesList;
    contact.deleteAttachmentsList = window.deleteAttachmentsList;

    if (document.getElementById('user_id')) {
        contact.id = document.getElementById('user_id').value;
    }


    if (contact.name && contact.surName && contact.middleName) {
        /* show(contact.phones);
        show(contact.attachments);
        show(contact.deletePhonesList);
        show(contact.deleteAttachmentsList); */

        var formData = new FormData();
        var objectSend = JSON.stringify(contact);

        getFiles(formData);
        formData.append("contact", objectSend);

        var xmlhttp = new XMLHttpRequest();

        xmlhttp.open("POST", url, true);
        xmlhttp.send(formData);

        xmlhttp.onreadystatechange = function (ev) {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                window.location.replace('/index');
            } else {
                //Handle errors.
            }
        }
    } else {
        alert("POPULATE FIELDS");
    }
};

function getPhones() {
    var phones = [];
    var phone = {};

    var table = document.getElementById('phone-table');
    var rowLength = table.rows.length;

    for (var i = 1; i < rowLength; i++) {
        var cells = table.rows.item(i).cells;

        phone.id =  table.rows.item(i).phoneId;
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

        attachment.id = table.rows.item(i).attachId;
        attachment.fileName = cells.item(0).innerHTML;
        attachment.loadDate = new Date().getTime();
        attachment.comments = cells.item(2).innerHTML;

        attachments.push(attachment);
    }

    return attachments;
}

function getFiles(formData) {
    var usersAttachment = document.querySelectorAll('input[name = users-attach]');

    for (var i = 0; i < usersAttachment.length; i++) {
        formData.append('files', usersAttachment[i].files[0]);
    }
}

function show(list) {
    console.log("SHOW LIST");
    for (var i = 0; i < list.length; i++) {
        console.log(list[i]);
    }
}