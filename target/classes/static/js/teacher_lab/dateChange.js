calendars = document.querySelectorAll(".teacher_calendar"); // получили все календари
calendar_btns = document.querySelectorAll(".teacher_date"); // получили все блоки дедлайны
dayMonth = document.querySelectorAll(".teacher_dayMonth"); // получили все текстовые поля (1 строку) в блоках дедлайна
year = document.querySelectorAll(".teacher_year"); // получили все текстовые поля (2 строку) в блоках дедлайна

// функция показывания/скрывания календаря
function click (numberOfBlock) {
    calendars[numberOfBlock].style.display === 'block' ? calendars[numberOfBlock].style.display = 'none' : calendars[numberOfBlock].style.display = 'block';
    return 0;
}

console.log(calendar_btns)
// для каждого блока дедлайна навешиваем функцию с соответствующим числовым параметром
for (let i = 0; i < calendar_btns.length; i++) {
    calendar_btns[i].addEventListener('click', function() {
        click(i);
    })
}
// для каждого календаря навешиваем функцию при которой он возвращает дату после каждого выбора
for (let i = 0; i < calendars.length; i++) {
    calendars[i].addEventListener("change", function () {
        let input = this.value; // получили строчку из календаря
        let dateEntered = new Date(input);  // превратили в объект даты

        // добавляем нули к чиcлам, если они меньше 10
        let day = dateEntered.getDate();
        day < 10 ? day = '0' + day : day;
        let month = dateEntered.getMonth();
        month++;
        month < 10 ? month = '0' + month : month;

        // собираем строки для блока
        let firstRow = day + '.' + month + '.';
        let secondRow = dateEntered.getFullYear().toString();

        // записываем данные в строки блока
        dayMonth[i].innerHTML = firstRow;
        year[i].innerHTML = secondRow;

        // скрываем календарь
        click(i);
    })
}

