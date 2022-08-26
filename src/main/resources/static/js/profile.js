var savebutton = document.getElementById('savebutton');
var readonly = true;
var inputs = document.querySelectorAll('input[type="text"]');
savebutton.addEventListener('click',function(){

    for (var i=0; i<inputs.length; i++) {
        inputs[i].toggleAttribute('readonly');
    };

    if (savebutton.innerHTML === "edit") {
        savebutton.innerHTML = "save";
        savebutton.setAttribute('type','submit')
    } else {
        savebutton.innerHTML = "edit";
        savebutton.toggleAttribute("type")
    }




});