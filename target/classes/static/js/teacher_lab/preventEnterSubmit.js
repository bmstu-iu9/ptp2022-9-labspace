$(document).ready(function () {
    $('#upload').keydown(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            return false;
        }
    });
})