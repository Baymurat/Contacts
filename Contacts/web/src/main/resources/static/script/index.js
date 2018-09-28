   console.log(document.getElementById('edit-button'));

   function fillIndex(contacts) {
    console.log(contacts);
    for(let i = 0; i < contacts.length; i++) {
    
        let currentElement = contacts[i];
        
        let tableRow = document.createElement('tr');

        let userName = document.createElement('td');
        let userSurname = document.createElement('td');
        let userPhone = document.createElement('td');
        let dataAttachment = document.createElement('td');

        userName.innerHTML = currentElement.name;
        userSurname.innerHTML = currentElement.surName;
        userPhone.innerHTML = currentElement.email;
        dataAttachment.innerHTML = currentElement.webSite;

        tableRow.appendChild(userName);
        tableRow.appendChild(userSurname);
        tableRow.appendChild(userPhone);
        tableRow.appendChild(dataAttachment);

        document.getElementById('myTable').appendChild(tableRow);
    }
}

fetch('/fill-index').
then(function(res) {
    return res.json();
}).
then(fillIndex);


    function addFuncOne() {
        console.log("Add.js file start");
        
        let contact = {};
        let phone = {};
        let attachment = {};
        
        //phone.persons_id = 3;
        phone.phoneNumber = 2579640;
        phone.codeOfCountry = 375;
        phone.codeOfOperator = 29;
        phone.type = "home";
        phone.comments = "Test phone number";
        
        attachment.fileName = "Test file";
        attachment.comments = "Test file attachment";
        //attachment.loadDate = new Date();
        //attachment.persons_id = 3;
        
        contact.name = "TestName";
        contact.surName = "TestSurname";
        contact.middleName = "TestMiddleName";
        contact.gender = "male";
        contact.citizenship  = "Testcitizenship";
        contact.familyStatus  = "single";
        contact.webSite  = "TestwebSite";
        contact.email  = "Testemail";
        contact.currentJob  = "TestcurrentJob";
        contact.country  = "Testcountry";
        contact.city  = "Testcity";
        contact.steetHouseApart  = "TeststeetHouseApart";
        contact.index  = 99;
        //contact.birthDate  = new Date();
        contact.phones  = [];
        contact.attachments  = [];
        //contact.id = 3;
        contact.phones.push(phone) ;
        contact.attachments.push(attachment);
        
        let objectSend = JSON.stringify(contact);
        
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/add-record", true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify(contact));
    }


    /* function deleteFunc() {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/add-record", true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify(contact));
    } */

    function editFunc() {
        console.log("Edit.js file start");
        
        let contact = {};
        let phone = {};
        let attachment = {};
        let attachment1 = {};
        let attachment2 = {};
        
        //phone.persons_id = 3;
        phone.phoneNumber = 2579640;
        phone.codeOfCountry = 375;
        phone.codeOfOperator = 29;
        phone.type = "mobile";
        phone.comments = "Update phone number";
        
        attachment.fileName = "Update file DEFAULT";
        attachment.comments = "Update file attachment DEFAULT";

        attachment1.fileName = "Update file ONE";
        attachment1.comments = "Update file attachment ONE";

        attachment2.fileName = "Update file TWO";
        attachment2.comments = "Update file attachment TWO";
        //attachment.loadDate = new Date();
        //attachment.persons_id = 3;
        
        contact.name = "UpdatedtName";
        contact.surName = "UpdatedSurname";
        contact.middleName = "UpdatedMiddleName";
        contact.gender = "female";
        contact.citizenship  = "Updatedcitizenship";
        contact.familyStatus  = "married";
        contact.webSite  = "UpdatedwebSite";
        contact.email  = "Updatedemail";
        contact.currentJob  = "UpdatedcurrentJob";
        contact.country  = "Updatedcountry";
        contact.city  = "Updatecity";
        contact.steetHouseApart  = "UpdaUpdateeetHouseApart";
        contact.index  = 99;
        //contact.birthDate  = new Date();
        contact.phones  = [];
        contact.attachments  = [];
        contact.id = 3;
        contact.phones.push(phone) ;
        contact.attachments.push(attachment);
        /* contact.attachments.push(attachment1);
        contact.attachments.push(attachment2); */
        
        let objectSend = JSON.stringify(contact);
        
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/update-record", true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify(contact));
    }

    /* let addButton = document.getElementById('add-button');
    addButton.addEventListener('click', addFuncOne); */

    /* let deleteButton = document.getElementById('delete-button');
    deleteButton.addEventListener('click', deleteFunc); */

    /* let editButton = document.getElementById('edit-button');
    editButton.addEventListener('click', editFunc); */