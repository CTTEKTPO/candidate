document.addEventListener('DOMContentLoaded', function () {
    // Переменные для хранения доступных полей и условий
    var availableFields = ["ID", "ФИО", "Дата рождения", "Возраст", "Пол", "Город", "Телефон", "Желаемая должность", "Желаемая зарплата", "Опыт работы", "Образование", "Ключевые навыки", "Комментарии", "Статус", "Дата добавления в базу"];
    var availableConditions = ["Содержит", "Больше", "Меньше", "Равно"];
    var filterCounter = 0

    // Функция для создания опций <select>
    function populateSelectOptions(selectElement, options) {
        for (var i = 0; i < options.length; i++) {
            var option = document.createElement("option");
            option.value = options[i].toLowerCase().replace(/ /g, '_'); // Приводим к формату для использования в коде
            option.text = options[i];
            selectElement.appendChild(option);
        }
    }

    // Функция для добавления фильтра
    window.addFilter = function addFilter() {
        var filterTableBody = document.getElementById("filterTable").getElementsByTagName('tbody')[0];

        // Создаем новую строку
        var newRow = filterTableBody.insertRow();

        // В каждую строку добавляем ячейки
        var radioCell = newRow.insertCell(0);
        var checkboxCell = newRow.insertCell(1);
        var fieldCell = newRow.insertCell(2);
        var conditionCell = newRow.insertCell(3);
        var valueCell = newRow.insertCell(4);

        // Заполняем ячейки
        var radioInput = document.createElement("input");
        radioInput.type = "radio";
        radioInput.name = "selectedFilter";
        radioCell.appendChild(radioInput);

        var checkboxInput = document.createElement("input");
        checkboxInput.type = "checkbox";
        checkboxInput.checked = true;
        checkboxCell.appendChild(checkboxInput);

        var fieldSelect = document.createElement("select");
        fieldSelect.id = "filterField_" + filterCounter; // уникальный идентификатор
        populateSelectOptions(fieldSelect, availableFields);
        fieldCell.appendChild(fieldSelect);

        var conditionSelect = document.createElement("select");
        conditionSelect.id = "filterCondition_" + filterCounter; // уникальный идентификатор
        populateSelectOptions(conditionSelect, availableConditions);
        conditionCell.appendChild(conditionSelect);

        var valueInput = document.createElement("input");
        valueInput.type = "text";
        valueInput.id = "filterValue_" + filterCounter; // уникальный идентификатор
        valueCell.appendChild(valueInput);

        // Увеличиваем счетчик для следующего фильтра
        filterCounter++;
    }



    window.removeFilter = function removeFilter() {
        const selectedFilter = document.querySelector('input[name="selectedFilter"]:checked');
                if (selectedFilter) {
                    const row = selectedFilter.closest('tr');
                    row.parentNode.removeChild(row);
                }
    }

    window.updateTable = async function updateTable() {
        // Получение всех чекбоксов
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');

        // Создание объекта для хранения параметров фильтрации
        var filters = {};

        // Перебор чекбоксов
        checkboxes.forEach(function (checkbox) {
            // Проверка, является ли чекбокс чекнутым
            if (checkbox.checked) {
                        // Получение уникального идентификатора фильтра
                        var filterId = checkbox.parentNode.parentNode.rowIndex;

                        // Получение значения поля, условия и значения
                        var field = document.getElementById("filterField_" + filterId).value;
                        var condition = document.getElementById("filterCondition_" + filterId).value;
                        var value = document.getElementById("filterValue_" + filterId).value;

                        // Проверка, есть ли уже такой фильтр
                        if (!filters[field]) {
                            filters[field] = {};
                        }

                        // Добавление параметров в объект фильтрации
                        filters[field].condition = condition;
                        filters[field].value = value;
            }
        });

        // Формирование строки запроса из объекта параметров
        var queryString = Object.keys(filters).map(function (key) {
            var condition;
            switch (filters[key].condition) {
                case 'Содержит':
                    condition = '*' + filters[key].value + '*';
                    break;
                case 'Больше':
                    condition = '>' + filters[key].value;
                    break;
                case 'Меньше':
                    condition = '<' + filters[key].value;
                    break;
                case 'Равно':
                    condition = '=' + filters[key].value;
                    break;
                default:
                    condition = filters[key].value;
            }
            return key + '=' + condition;
        }).join('&');

        try {
                // Выполнение GET запроса
                const response = await fetch('/filteredPersonalCards?' + queryString);

                if (!response.ok) {
                    throw new Error('Ошибка запроса');
                }

                // Получение HTML контента и обновление тела таблицы
                const data = await response.text();
                document.getElementById('tableBody').innerHTML = data;
            } catch (error) {
                console.error('Error:', error);
            }
    };


    window.removeAllFilters = function removeAllFilters() {
        const table = document.querySelector('.filter');
                while (table.rows.length > 1) {
                    table.deleteRow(1);
                }
                filterCounter = 1;
    }

    // Вызываем addFilter() при загрузке страницы, чтобы изначально добавить пустую строку фильтра
    addFilter();
});
