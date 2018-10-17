/** global: widgets*/
window.onload = function () {
    "use strict";

    var phoneTable = document.querySelector('#phone-table>tbody');
    var phoneElement = document.querySelector("#add-phone-modal");
    var phoneModal = new Modal(phoneElement, null, phoneTable);
    phoneModal.setAddFunction(addPhone);
    phoneModal.setUpdateFunction(updatePhone);

    var buttonPhoneAdd = document.querySelector("#phone-add");
    buttonPhoneAdd.addEventListener("click", function () {
        phoneModal.add();
    });

    var buttonPhoneEdit = document.querySelector('#phone-edit');
    buttonPhoneEdit.addEventListener("click", function () {
        phoneModal.edit(phoneInputsFill);
    });

    var buttonPhoneDelete = document.querySelector('#phone-delete');
    buttonPhoneDelete.addEventListener("click", function () {
        phoneModal.delete(deleteRow);
    });

    var attachmentTable = document.querySelector('#attachment-table>tbody');
    var attachmentsElement = document.querySelector("#add-attachment-modal");
    var attachmentsModal = new Modal(attachmentsElement, null, attachmentTable);
    attachmentsModal.setAddFunction(addAttachment);
    attachmentsModal.setUpdateFunction(updateAttachment);

    var buttonAttachmentAdd = document.querySelector("#attachment-add");
    buttonAttachmentAdd.addEventListener("click", function () {
        attachmentsModal.add();
    });

    var buttonAttachmentEdit = document.querySelector('#attachment-edit');
    buttonAttachmentEdit.addEventListener("click", function () {
        attachmentsModal.edit(attachmentInputsFill);
    });

    var buttonAttachmentDelete = document.querySelector('#attachment-delete');
    buttonAttachmentDelete.addEventListener("click", function () {
        attachmentsModal.delete(deleteRow);
    });
};

function addPhone(context) {
    var tableRow = document.createElement('tr');

    tableRow.phoneId = -1;

    var phoneNumber = document.createElement('td');
    var phoneType = document.createElement('td');
    var comments = document.createElement('td');

    var countryCode = context.element.querySelector('#country-code').value;
    var operatorCode = context.element.querySelector('#operator-code').value;
    var number = context.element.querySelector('#phone-number').value;

    var resultObject = {};

    if (!countryCode) {
        resultObject.status = false;
        resultObject.textOfError = "Enter country code";
        return resultObject;
    }

    if (!operatorCode) {
        resultObject.status = false;
        resultObject.textOfError = "Enter operator code";
        return resultObject;
    }

    if (!number) {
        resultObject.status = false;
        resultObject.textOfError = "Enter number of phone";
        return resultObject;
    }

    phoneNumber.innerHTML = countryCode + " " + operatorCode + " " + number;
    comments.innerHTML = context.element.querySelector('#phone-comments').value;
    var select = context.element.querySelector('#phone-number-type');
    phoneType.innerHTML = select.options[select.selectedIndex].text;

    tableRow.appendChild(phoneNumber);
    tableRow.appendChild(phoneType);
    tableRow.appendChild(comments);

    context.table.appendChild(tableRow);

    resultObject.status = true;

    return resultObject;
}

function addAttachment(context) {
    var tableRow = document.createElement('tr');

    tableRow.attachId = -1;

    var fileName = document.createElement('td');
    var comments = document.createElement('td');
    var dateColumn = document.createElement('td');

    var attachmentInput = document.createElement('input');
    attachmentInput.type = 'file';
    attachmentInput.style.display = 'none';
    attachmentInput.name = 'users-attach';

    var date = new Date();
    var fullDate = date.getDay() + "." + date.getMonth() + "." + date.getFullYear();

    fileName.innerHTML = context.element.querySelector('#file-name').value;
    comments.innerHTML = context.element.querySelector('#attachment-comment').value;
    attachmentInput.files = context.element.querySelector('input[name = files').files;

    dateColumn.innerHTML = fullDate;

    var resultObject = {};

    if (!fileName.innerHTML) {
        resultObject.status = false;
        resultObject.textOfError = "Enter file name";
        return resultObject;
    }

    if (!context.element.querySelector('input[name = files').value) {
        resultObject.status = false;
        resultObject.textOfError = "Specify file";
        return resultObject;
    }
    tableRow.appendChild(fileName);
    tableRow.appendChild(dateColumn);
    tableRow.appendChild(comments);
    tableRow.appendChild(attachmentInput);

    context.table.appendChild(tableRow);

    resultObject.status = true;
    return resultObject;
}

function updatePhone(context) {
    var selectedRow = context.table.querySelector('.selected');
    var cells = selectedRow.cells;

    var countryCode = context.element.querySelector('#country-code').value;
    var operatorCode = context.element.querySelector('#operator-code').value;
    var number = context.element.querySelector('#phone-number').value;

    var resultObject = {};

    if (!countryCode) {
        resultObject.status = false;
        resultObject.textOfError = "Enter country code";
        return resultObject;
    }

    if (!operatorCode) {
        resultObject.status = false;
        resultObject.textOfError = "Enter operator code";
        return resultObject;
    }

    if (!number) {
        resultObject.status = false;
        resultObject.textOfError = "Enter number of phone";
        return resultObject;
    }
    cells.item(0).innerHTML = countryCode + " " + operatorCode + " " + number;
    var select = context.element.querySelector('#phone-number-type');
    cells.item(1).innerHTML = select.options[select.selectedIndex].text;
    cells.item(2).innerHTML = context.element.querySelector('#phone-comments').value;

    resultObject.status = true;

    return resultObject;
}

function updateAttachment(context) {
    var selectedRow = context.table.querySelector('.selected');
    var cells = selectedRow.cells;

    var fileName = context.element.querySelector('#file-name').value;

    var resultObject = {};

    if (!fileName) {
        resultObject.status = false;
        resultObject.textOfError = "Enter file name";
        return resultObject;
    }

    cells.item(0).innerHTML = fileName;
    cells.item(2).innerHTML = context.element.querySelector('#attachment-comment').value;

    resultObject.status = true;
    return resultObject;
}

function phoneInputsFill(element, selectedRow) {
    var cells = selectedRow.cells;
    var fullPhone = cells.item(0).innerHTML.split(' ');

    var codeOfCountry = fullPhone[0];
    var codeOfOperator = fullPhone[1];
    var number = fullPhone[2];

    element.querySelector('#country-code').value = codeOfCountry;
    element.querySelector('#operator-code').value = codeOfOperator;
    element.querySelector('#phone-number').value = number;
    element.querySelector('#phone-comments').value = cells.item(2).innerHTML;
}

function attachmentInputsFill(element, selectedRow) {
    var cells = selectedRow.cells;

    element.querySelector('#file-name').value = cells.item(0).innerHTML;
    element.querySelector('#attachment-comment').value = cells.item(2).innerHTML;
}


function deleteRow(selectedRow) {
    var parent = selectedRow.parentNode;

    if (selectedRow.phoneId > -1) {
        window.deletePhonesList.push(selectedRow.phoneId);
    } else if (selectedRow.attachId > -1) {
        window.deleteAttachmentsList.push(selectedRow.attachId);
    }
    parent.removeChild(selectedRow);
}

window.deletePhonesList = [];
window.deleteAttachmentsList = [];

/**
 * ******************************************************************
 *
 */



var imageDiv = document.getElementsByClassName('image-holder')[0];
var imageInput = document.querySelector('#person-image');
var image = document.querySelector('#image');

imageDiv.addEventListener('click', function () {
    imageInput.click();
});

imageInput.addEventListener('change', function () {
    renderImage(imageInput.files[0]);
});

function renderImage(file) {
    var reader = new FileReader();

    reader.onload = function (ev) {
        image.src = ev.target.result;
    };

    reader.readAsDataURL(file);
}


/**
 * DATE VALIDATION
 * ***************************************
 */

var el = document.getElementById("datebirth");
var date = new Date();

/* var today = date.getDay();
var month = date.getMonth(); */
var year = date.getFullYear();

function keyUpValidation(evt) {
    if ((evt.keyCode >= 48 && evt.keyCode <= 57) || (evt.keyCode >= 96 &&
        evt.keyCode <= 105)) {
        evt = evt || window.event;

        var value = el.value;
        var size = value.length;

        if ((size == 2 && value > 31) || (size == 5 && Number(value.split('/')[1]) > 12) || (size >= 10 && Number(value.split('/')[2]) > year)) {
            alert('Invalid Date');
            document.getElementById('datebirth').value = '';
            return;
        }

        if ((size == 2 && value < 32) || (size == 5 && Number(value.split('/')[1]) < 13)) {
            document.getElementById('datebirth').value += '/';
        }

    } else {
        alert('Please enter valid date.')
        document.getElementById('datebirth').value = '';
    }
}

function focusOutValidation() {
    var values = el.value.split('/');

    var currentDate = new Date();
    var specifiedDate = new Date(values[2] + "-" + values[1] + "-" + values[0]);

    console.log("SPECIFIED " + specifiedDate);
    console.log("CURRENT " + currentDate);

    if (currentDate < specifiedDate) {
        alert("FUTURE SPECIFIED");
        el.value = '';
    }
}

el.addEventListener('keyup', keyUpValidation);
el.addEventListener('focusout', focusOutValidation);