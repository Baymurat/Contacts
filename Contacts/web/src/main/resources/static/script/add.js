console.log("Add.js file start");

function addRecordFunction() {
    let contact = {};
    let phone = {};
    let attachment = {};
    
    //phone.persons_id = 0;
    phone.phoneNumber = 2579640;
    phone.codeOfCountry = 375;
    phone.codeOfOperator = 29;
    phone.type = 'mobile';
    phone.comments = 'this is a test mobile phone';
    
    attachment.fileName = 'Test File name';
    attachment.comments = 'This is a test file name';
    //attachment.loadDate = new Date();
    //attachment.persons_id = document.getElementById('');;
    
    contact.name = document.getElementById('name').value;
    contact.surName = document.getElementById('surname').value;
    contact.middleName = document.getElementById('middlename').value;

    let elementG = document.getElementById('gender');
    contact.gender = elementG.options[elementG.selectedIndex].text;
    contact.citizenship  = document.getElementById('citizenship').value;

    let elementFS = document.getElementById('family-status');
    contact.familyStatus  = elementFS.options[elementFS.selectedIndex].text;
    contact.webSite  = document.getElementById('web-site').value;
    contact.email  = document.getElementById('email').value;
    contact.currentJob  = document.getElementById('currentjob').value;
    
    contact.country  = document.getElementById('country').value;
    contact.city  = document.getElementById('city').value;
    contact.steetHouseApart  = document.getElementById('street-house-apart').value;
    contact.index  = document.getElementById('index').value;
    //contact.birthDate  = new Date();
    contact.phones  = [];
    contact.attachments  = [];
    //contact.id = 4;
    contact.phones.push(phone) ;
    contact.attachments.push(attachment);
    
    let objectSend = JSON.stringify(contact);
    
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/add-record", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(objectSend);
}

let acceptButton = document.getElementById('accept-button');
acceptButton.addEventListener('click', addRecordFunction);