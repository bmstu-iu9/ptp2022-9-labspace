mark_input = document.querySelector(".final_mark_input"); // получили все поля для ввода оценки
mark_btn = document.querySelector(".final_mark_btn"); // получили все блоки с оценкой
mark = document.querySelector(".final_mark"); // получили все блоки с текстом оценки

// функция показывания/скрывания поля ввода
function click2() {
    mark_input.style.display === 'block' ? mark_input.style.display = 'none' : mark_input.style.display = 'block';
    return 0;
}

// для каждого блока оценки навешиваем функцию с соответствующим числовым параметром
mark_btn.addEventListener('click', function() {
    click2();
});

mark_input.addEventListener("change", function () {

    mark.innerHTML = mark_input.value;

    // скрываем поле
    click2();
});

