function renderImage(file) {
    var reader = new FileReader();

    reader.onload = function (ev) {
        //the_url = ev.target.result;
        var image = document.createElement("img");
        image.src = ev.target.result;
        document.getElementById("").appendChild(image);
    };

    reader.readAsDataURL(file);
}