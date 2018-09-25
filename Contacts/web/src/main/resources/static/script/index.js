    fetch('/fill-index').
    then(function(res) {
        return res.json();
    }).
    then(function(contacts) {
        

        console.log(contacts);
        /* for(let i = 0; i < contacts.length; i++) {
        
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
        } */
    });
    
    /* let addButton = document.getElementById('add-button');
    
    addButton.addEventListener('click', function() {
        console.log("Pressed Add button")
    }) */