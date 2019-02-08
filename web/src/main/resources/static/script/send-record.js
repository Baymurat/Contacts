window.sendRecord = function (url) {

    var contact = {};

    contact.name = document.getElementById('name').value.trim();
    contact.surName = document.getElementById('surname').value.trim();
    contact.middleName = document.getElementById('middlename').value.trim();

    var elementG = document.getElementById('gender');
    contact.gender = elementG.options[elementG.selectedIndex].text;
    contact.citizenship = document.getElementById('citizenship').value;

    var elementFS = document.getElementById('family-status');
    contact.familyStatus = elementFS.options[elementFS.selectedIndex].text;
    contact.webSite = document.getElementById('web-site').value;
    contact.email = document.getElementById('email').value;
    contact.currentJob = document.getElementById('currentjob').value;
    contact.birthDate = document.getElementById('datebirth').value;
    contact.country = document.getElementById('country').value;
    contact.city = document.getElementById('city').value;
    contact.streetHouseApart = document.getElementById('street-house-apart').value;
    contact.index = document.getElementById('index').value;
    contact.phones = getPhones();
    contact.attachments = getAttachments();

    /*contact.deletePhonesList = window.deletePhonesList;
    contact.deleteAttachmentsList = window.deleteAttachmentsList;*/

    if (document.getElementById('user_id')) {
        contact.id = document.getElementById('user_id').value;
    }


    var flag = true;
    var message = "";

    if (!contact.name) {
        message = "Populate name field.";
        flag = false;
    }

    if (!contact.surName) {
        message = "Populate surname field.";
        flag = false;
    }

    if (!contact.middleName) {
        message = "Populate middle name field.";
        flag = false;
    }

    if (flag) {
        var formData = new FormData();
        var objectSend = JSON.stringify(contact);

        getFiles(formData);
        formData.append("person", objectSend);

        var xmlhttp = new XMLHttpRequest();

        xmlhttp.open("POST", url, true);
        xmlhttp.send(formData);

        xmlhttp.onreadystatechange = function (ev) {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                window.location.href = 'index';
            } else {
                //Handle errors.
            }
        }
    } else {
        alert(message);
    }
};

function getPhones() {
    var phones = [];


    var table = document.getElementById('phone-table');
    var rowLength = table.rows.length;

    for (var i = 1; i < rowLength; i++) {
        var phone = {};
        var cells = table.rows.item(i).cells;

        phone.id = table.rows.item(i).phoneId;
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

    var table = document.getElementById('attachment-table');
    var rowLength = table.rows.length;

    for (var i = 1; i < rowLength; i++) {
        var attachment = {};
        var cells = table.rows.item(i).cells;
        attachment.id = table.rows.item(i).attachId;

        if (cells.item(0).children.length === 0) {
            attachment.fileName = cells.item(0).innerHTML;
        } else {
            attachment.fileName = cells.item(0).children[0].innerHTML;
        }
        attachment.loadDate = new Date().getTime();
        attachment.comments = cells.item(2).innerHTML;

        attachments.push(attachment);
    }

    return attachments;
}

function getFiles(formData) {
    var usersAttachment = document.querySelectorAll('input[name = users-attach]');
    var userPhoto = document.querySelector('#person-image');

    for (var i = 0; i < usersAttachment.length; i++) {
        formData.append('files', usersAttachment[i].files[0]);
    }

    if (userPhoto.files.length > 0) {
        formData.append('photo', userPhoto.files[0]);
    }
}