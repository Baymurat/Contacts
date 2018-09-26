let addFunc = function() {
    console.log("Add.js file start");
    
    let contact = {};
    let phone = {};
    let attachment = {};
    
    phone.persons_id = 4;
    phone.phoneNumber = 2579640;
    phone.codeOfCountry = 375;
    phone.codeOfOperator = 29;
    phone.type = "home";
    phone.comments = "Test phone number";
    
    attachment.fileName = "Test file";
    attachment.comments = "Test file attachment";
    attachment.loadDate = new Date();
    attachment.persons_id = 4;
    
    contact.name = "TestName";
    contact.surName = "TestSurname";
    contact.middleName = "TestMiddleName";
    contact.gender = "TestGender";
    contact.citizenship  = "Testcitizenship";
    contact.familyStatus  = "TestfamilyStatus";
    contact.webSite  = "TestwebSite";
    contact.email  = "Testemail";
    contact.currentJob  = "TestcurrentJob";
    contact.country  = "Testcountry";
    contact.city  = "Testcity";
    contact.steetHouseApart  = "TeststeetHouseApart";
    contact.index  = 99;
    contact.birthDate  = new Date();
    contact.phones  = [];
    contact.attachments  = [];
    contact.id = 4;
    contact.phones.push(phone) ;
    contact.attachments.push(attachment);
    
    let objectSend = JSON.stringify(contact);
    
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/add-record", true);
    xmlhttp.send(contact);
}

module.exports = addFunc;