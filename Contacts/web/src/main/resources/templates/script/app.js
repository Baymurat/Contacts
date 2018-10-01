/** global: widgets*/
window.onload = function () {
    "use strict";

    //var phoneTable = document.getElementById('phone-table');
    var phoneTable = document.querySelector('#phone-table>tbody');
    var phoneElement = document.querySelector("#add-phone-modal");
    var phoneModal = new Modal(phoneElement, null, phoneTable, addPhoneFunction);
    var button = document.querySelector("#phone-add");
    button.addEventListener("click", function () {
        phoneModal.show();
    });
    button = document.querySelector('#phone-edit');
    button.addEventListener("click", function() {
        phoneModal.edit();
    });

    var attachmentTable = document.querySelector('#attachment-table>tbody');
    var attachmentsElement = document.querySelector("#add-attachment-modal");
    var attachmentsModal = new Modal(attachmentsElement, null, attachmentTable, addAttachmentFunction);
    button = document.querySelector("#attachment-add");
    button.addEventListener("click", function () {
        attachmentsModal.show();
    });
    button = document.querySelector('#attachment-edit');
    button.addEventListener("click", function() {
        attachmentsModal.edit();
    });

    var submit = document.querySelector(".submit");
};

function addPhoneFunction(element, table) {
    var tableRow = document.createElement('tr');

    var phoneNumber = document.createElement('td');
    var phoneType = document.createElement('td');
    var comments = document.createElement('td');

    var countryCode = element.querySelector('#country-code').value;
    var operatorCode = element.querySelector('#operator-code').value;
    var number = element.querySelector('#phone-number').value;

    phoneNumber.innerHTML = countryCode + " " + operatorCode + " " + number;
    comments.innerHTML = element.querySelector('#phone-comments').value;
    var select = element.querySelector('#phone-number-type');
    phoneType.innerHTML = select.options[select.selectedIndex].text;

    tableRow.appendChild(phoneNumber);
    tableRow.appendChild(phoneType);
    tableRow.appendChild(comments);
    
    table.appendChild(tableRow);
}

function addAttachmentFunction(element, table) {
    var tableRow = document.createElement('tr');

    var flieName = document.createElement('td');
    var comments = document.createElement('td');
    var dateColumn = document.createElement('td');
    var date = new Date();
    var fullDate = date.getDay() + "." + date.getMonth() + "." + date.getFullYear();

    flieName.innerHTML = element.querySelector('#file-name').value;
    comments.innerHTML = element.querySelector('#attachment-comment').value;
    dateColumn.innerHTML = fullDate;

    tableRow.appendChild(flieName);
    tableRow.appendChild(dateColumn);
    tableRow.appendChild(comments);

    
    table.appendChild(tableRow);
}