/*  fetch('/request').then(function(res) {
    console.log(res.headers.get('Content-Type'));
    console.log(res.status);

    return res.json();
})
.then(function(user) {
    console.log(user.name);
})
.catch(console.log("ERROR")); */

fetch('/request').
then(function(res) {
    return res.json();
}).
then(function(user) {
    console.log(user.name);
    console.log(user.age);
});