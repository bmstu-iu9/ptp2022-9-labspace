send_revision = document.querySelector(".send-revision"); // блок с галкой
revision = document.querySelector(".revision"); // блок комментария
send_revision_input = document.querySelector(".send-revision_input"); // блок комментария

send_revision.addEventListener('click', function () {
    send_revision_input.checked === true ? revision.style.display = 'block' : revision.style.display = 'none';
})