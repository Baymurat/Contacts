var searchButton = document.getElementById('search-button');
searchButton.addEventListener('click', function (ev) {
   var searchField = document.getElementById('search-field');
   if (searchField.value) {
       var xmlhttp = new XMLHttpRequest();
       var params = '?from=0&range=10&like=' + searchField.value;
       xmlhttp.open('GET', '/search-contact' + params, true);
       xmlhttp.send();
       xmlhttp.onreadystatechange = function (ev1) {
           if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
               document.querySelector('#myTable>tbody').remove();
               var tbody = document.createElement('tbody');
               document.getElementById('myTable').appendChild(tbody);

               var result = JSON.parse(xmlhttp.responseText);
               window.fillTable(result);
           }
       }
   }
});

var cancelButton = document.getElementById('back-to-index');
cancelButton.addEventListener('click', function () {
    window.location.replace('/index');
});

var advancedSearch = document.getElementById('advanced-search');
advancedSearch.addEventListener('click', function (ev) {
    window.location.replace('/advanced-search');
})