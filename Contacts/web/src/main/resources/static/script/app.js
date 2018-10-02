/** global: widgets*/
window.onload = function () {
    "use strict";

    var phoneTable = document.querySelector('#phone-table>tbody');
    var phoneElement = document.querySelector("#add-phone-modal");
    var phoneModal = new Modal(phoneElement, null, phoneTable);
    phoneModal.setAddFunction(addPhone);
    phoneModal.setUpdateFunction(updatePhone);

    var button = document.querySelector("#phone-add");
    button.addEventListener("click", function () {
        phoneModal.add();
    });

    button = document.querySelector('#phone-edit');
    button.addEventListener("click", function() {
        phoneModal.edit(phoneInputsFill);
    });

    button = document.querySelector('#phone-delete');
    button.addEventListener("click", function() {
        phoneModal.delete(deleteRow);
    });

    var attachmentTable = document.querySelector('#attachment-table>tbody');
    var attachmentsElement = document.querySelector("#add-attachment-modal");
    var attachmentsModal = new Modal(attachmentsElement, null, attachmentTable);
    attachmentsModal.setAddFunction(addAttachment);
    attachmentsModal.setUpdateFunction(updateAttachment);
    
    button = document.querySelector("#attachment-add");
    button.addEventListener("click", function () {
        attachmentsModal.add();
    });
    
    button = document.querySelector('#attachment-edit');
    button.addEventListener("click", function() {
        attachmentsModal.edit(attachmentInputsFill);
    });

    button = document.querySelector('#attachment-delete');
    button.addEventListener("click", function() {
        attachmentsModal.delete(deleteRow);
    });
};

function addPhone(context) {
    var tableRow = document.createElement('tr');

    var phoneNumber = document.createElement('td');
    var phoneType = document.createElement('td');
    var comments = document.createElement('td');

    var countryCode = context.element.querySelector('#country-code').value;
    var operatorCode = context.element.querySelector('#operator-code').value;
    var number = context.element.querySelector('#phone-number').value;

    if(countryCode && operatorCode && number) {
        phoneNumber.innerHTML = countryCode + " " + operatorCode + " " + number;
        comments.innerHTML = context.element.querySelector('#phone-comments').value;
        var select = context.element.querySelector('#phone-number-type');
        phoneType.innerHTML = select.options[select.selectedIndex].text;
    
        tableRow.appendChild(phoneNumber);
        tableRow.appendChild(phoneType);
        tableRow.appendChild(comments);
        
        context.table.appendChild(tableRow);

        return true;
    } else {
        return false;
    }
}

function addAttachment(context) {
    var tableRow = document.createElement('tr');

    var fileName = document.createElement('td');
    var comments = document.createElement('td');
    var dateColumn = document.createElement('td');
    var date = new Date();
    var fullDate = date.getDay() + "." + date.getMonth() + "." + date.getFullYear();

    fileName.innerHTML = context.element.querySelector('#file-name').value;
    comments.innerHTML = context.element.querySelector('#attachment-comment').value;
    dateColumn.innerHTML = fullDate;

    if(fileName.innerHTML) {
        tableRow.appendChild(fileName);
        tableRow.appendChild(dateColumn);
        tableRow.appendChild(comments);
    
        context.table.appendChild(tableRow);
        
        return true;
    } else {
        return false;
    }
}

function updatePhone(context) {
    var selectedRow = context.table.querySelector('.selected');
    var cells = selectedRow.cells;

    var countryCode = context.element.querySelector('#country-code').value;
    var operatorCode = context.element.querySelector('#operator-code').value;
    var number = context.element.querySelector('#phone-number').value;

    if(countryCode && operatorCode && number) { 
        cells.item(0).innerHTML = countryCode + " " + operatorCode + " " + number;
        var select = context.element.querySelector('#phone-number-type');
        cells.item(1).innerHTML = select.options[select.selectedIndex].text;
        cells.item(2).innerHTML = context.element.querySelector('#phone-comments').value;
        return true;
    } else {
        return false;
    }
}

function updateAttachment(context) {
    var selectedRow = context.table.querySelector('.selected');
    var cells = selectedRow.cells;

    var fileName = context.element.querySelector('#file-name').value;
    if(fileName) {
        cells.item(0).innerHTML = fileName;
        cells.item(0).innerHTML = context.element.querySelector('#attachment-comment').value;
        return true;
    } else {
        return false;
    }
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
    parent.removeChild(selectedRow);
}