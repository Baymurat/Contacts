function search() {
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
    contact.birthDate = document.getElementById('datebirth').value;
    contact.country = document.getElementById('country').value;
    contact.city = document.getElementById('city').value;
    contact.streetHouseApart = document.getElementById('street-house-apart').value;
    contact.index = document.getElementById('index').value;

    var objectSend = JSON.stringify(contact);

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function (ev) {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.querySelector('#myTable>tbody').remove();
            var tbody = document.createElement('tbody');
            document.getElementById('myTable').appendChild(tbody);

            var result = JSON.parse(xmlhttp.responseText);
            window.fillTable(result);
        } else {
            //Handle errors.
        }
    };

    xmlhttp.open("POST", 'search-contact-advanced', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(objectSend);
}

var searchButton = document.getElementById('search-button');
searchButton.addEventListener('click', search);

var backToIndex = document.getElementById('back-to-index');
backToIndex.addEventListener('click', function (ev) {
    window.location.href = 'index';
});