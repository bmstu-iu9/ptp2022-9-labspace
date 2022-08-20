mark_inputs = document.querySelectorAll(".teacher_mark_input"); // получили все поля для ввода оценки
mark_btns = document.querySelectorAll(".teacher_mark_btn"); // получили все блоки с оценкой
mark = document.querySelectorAll(".teacher_mark"); // получили все блоки с текстом оценки

// функция показывания/скрывания поля ввода
function click2 (numberOfBlock) {
    mark_inputs[numberOfBlock].style.display === 'block' ? mark_inputs[numberOfBlock].style.display = 'none' : mark_inputs[numberOfBlock].style.display = 'block';
    return 0;
}

// для каждого блока оценки навешиваем функцию с соответствующим числовым параметром
for (let i = 0; i < mark_btns.length; i++) {
    mark_btns[i].addEventListener('click', function() {
        click2(i);
    })
}

for (let i = 0; i < mark_inputs.length; i++) {
    mark_inputs[i].addEventListener("change", function () {

        mark[i].innerHTML = mark_inputs[i].value;

        // скрываем поле
        click2(i);
    })
}

