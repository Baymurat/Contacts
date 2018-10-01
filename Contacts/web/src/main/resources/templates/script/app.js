/** global: widgets*/
window.onload = function () {
    "use strict";

    var phoneElement = document.querySelector("#add-phone-modal");
    var phoneModal = new Modal(phoneElement);
    var button = document.querySelector("#phone-add");
    button.addEventListener("click", function () {
        phoneModal.show();
    });

    var attachmentsElement = document.querySelector("#add-attachment-modal");
    var attachmentsModal = new Modal(attachmentsElement);
    var button = document.querySelector("#attachment-add");
    button.addEventListener("click", function () {
        attachmentsModal.show();
    });

    var submit = document.querySelector(".submit");
};
