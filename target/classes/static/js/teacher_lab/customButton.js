const file = document.getElementById('file');
const fileChosen = document.getElementById('filename');
file.addEventListener('change',
    function (){
        fileChosen.textContent = this.files[0].name
    })

const file2 = document.getElementById('file2');
const fileChosen2 = document.getElementById('filename2');
file2.addEventListener('change',
    function (){
        fileChosen2.textContent = this.files[0].name
    })