time_inputs = document.querySelectorAll(".teacher_time_input"); // получили все календари
time = document.querySelectorAll(".teacher_time"); // получили все текстовые поля (1 строку) в блоках дедлайна


// функция показывания/скрывания времени
function click3 (numberOfBlock) {
    time_inputs[numberOfBlock].style.display === 'block' ? time_inputs[numberOfBlock].style.display = 'none' : time_inputs[numberOfBlock].style.display = 'block';
    return 0;
}

// для каждого блока дедлайна навешиваем функцию с соответствующим числовым параметром
for (let i = 0; i < time.length; i++) {
    time[i].addEventListener('click', function() {
        click3(i);
    })
}


// для каждого календаря навешиваем функцию при которой он возвращает дату после каждого выбора
for (let i = 0; i < time_inputs.length; i++) {
    time_inputs[i].addEventListener("change", function () {
        let input = this.value; // получили строчку из календаря
        console.log(input)

        // собираем строки для блока
        // записываем данные в строки блока
        time[i].innerHTML = input;

    })
}