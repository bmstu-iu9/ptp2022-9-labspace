const file = document.getElementById('file');
const fileChosen = document.getElementById('filename');
file.addEventListener('change',
    function (){
    fileChosen.textContent = this.files[0].name
    })