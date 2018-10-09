const form = document.querySelector('#upload-file');

form.addEventListener('submit', e => {
    e.preventDefault();

    const files = document.querySelector('[type=file]').files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
        let file = files[i];

        console.log(file);
        formData.append('files[]', file);
        
    }
});
