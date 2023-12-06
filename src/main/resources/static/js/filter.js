document.addEventListener('DOMContentLoaded', function () {
    // Переменные для хранения доступных полей и условий
    var availableFields = ["ID", "ФИО", "Дата рождения", "Возраст", "Пол", "Город", "Телефон", "Желаемая должность", "Желаемая зарплата", "Опыт работы", "Образование", "Ключевые навыки", "Комментарии", "Статус", "Дата добавления в базу"];
    var availableConditions = ["Содержит", "Больше", "Меньше", "Равно"];
    var filterCounter = 1

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
        checkboxInput.id = "filterCheckbox_" + filterCounter; // уникальный идентификатор
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
                }else{
                    alert('Выберите фильтр для удаления.');
                }
    }

    window.updateTable = async function updateTable() {
        // Получение всех чекбоксов
        var checkboxes = document.querySelectorAll('input[type=checkbox]');

        // Создание объекта для хранения параметров фильтрации
        var filters = {};

        // Перебор чекбоксов
        for (let i = 0; i < checkboxes.length; i++) {
        console.log("checkboxes.item(i).checked: " + checkboxes.item(i).checked)
                    if (checkboxes.item(i).checked) {
                        // Получение уникального идентификатора фильтра
                        var filterId = checkboxes.item(i).parentNode.parentNode.rowIndex;

                        // Получение значения поля, условия и значения
                        var field = document.getElementById("filterField_" + filterId).value;
                        var condition = document.getElementById("filterCondition_" + filterId).value;
                        var value = document.getElementById("filterValue_" + filterId).value;
                        // Добавляем проверку на пустое значение поля value
                        if (!value.trim() && checkboxes.item(i).checked) {
                           alert("Пожалуйста, заполните значение для фильтра с чекбоксом");
                           return; // Останавливаем выполнение функции
                       }

                        // Проверка, есть ли уже такой фильтр
                        if (!filters[field]) {
                            filters[field] = {};
                        }

                        // Добавление параметров в объект фильтрации
                        filters[field].condition = condition;
                        filters[field].value = value;
                    }
                }
        // Проверка наличия активных чекбоксов
        var hasActiveFilters = Object.keys(filters).length > 0;
        // Формирование строки запроса из объекта параметров
        var queryString = hasActiveFilters
        ?Object.keys(filters).map(function (key) {
            var condition;
            console.log('filters[key].condition: ', filters[key].condition);
            switch (filters[key].condition) {

                case 'содержит':
                    condition = '*' + filters[key].value;
                    break;
                case 'больше':
                    condition = '>' + filters[key].value;
                    break;
                case 'меньше':
                    condition = '<' + filters[key].value;
                    break;
                case 'равно':
                    condition = '=' + filters[key].value;
                    break;
                default:
                    condition = filters[key].value;
            }
            return key + '=' + condition;
        }).join('&')
        : '';
//        console.log('queryString ', queryString);
        // Сохранение параметров фильтрации в localStorage
        try {
                // Выполнение GET запроса
                const response = await fetch('/filteredPersonalCards?' + queryString);

                if (!response.ok) {
                    throw new Error('Ошибка запроса');
                }

               // Получение HTML контента и обновление тела таблицы
               const data = await response.text();

               // Создание временного элемента для парсинга HTML
               const tempElement = document.createElement('div');
               tempElement.innerHTML = data;

               // Получение тела таблицы
               const tableBodyContent = tempElement.querySelector('#tableBody').innerHTML;

               // Обновление тела таблицы на странице
               document.getElementById('tableBody').innerHTML = tableBodyContent;
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
    //addFilter();
    var savedFilters = localStorage.getItem('filters');
        if (savedFilters) {
            // Преобразуем сохраненные данные в объект
            savedFilters = JSON.parse(savedFilters);

            // Восстанавливаем фильтры из сохраненных данных
            for (var i = 0; i < savedFilters.length; i++) {
                addFilter(); // Добавляем новый фильтр
                var filterId = filterCounter - 1; // Получаем ID нового фильтра

                // Заполняем значения фильтра из сохраненных данных
                document.getElementById("filterField_" + filterId).value = savedFilters[i].field;
                document.getElementById("filterCondition_" + filterId).value = savedFilters[i].condition;
                document.getElementById("filterValue_" + filterId).value = savedFilters[i].value;

                // Устанавливаем состояние чекбокса из сохраненных данных
                document.getElementById("filterCheckbox_" + filterId).checked = savedFilters[i].isChecked;
            }
        }else {
                // Если сохраненных фильтров нет, добавляем один фильтр по умолчанию
                addFilter();
        }

        // Обновляем фильтры в localStorage при изменении
        function updateLocalStorage() {
            var filtersToSave = [];

            // Перебираем все строки фильтров и сохраняем их параметры
            for (var i = 1; i < filterCounter; i++) {
                        if (document.getElementById("filterField_" + i)) {
                            var filter = {
                                field: document.getElementById("filterField_" + i).value,
                                condition: document.getElementById("filterCondition_" + i).value,
                                value: document.getElementById("filterValue_" + i).value,
                                isChecked: document.getElementById("filterCheckbox_" + i).checked,
                            };
                            filtersToSave.push(filter);
                        }
                    }

            // Сохраняем параметры фильтров в localStorage
            localStorage.setItem('filters', JSON.stringify(filtersToSave));
        }

        // Добавляем слушатель события на изменение фильтров
        document.getElementById("filterTable").addEventListener('change', updateLocalStorage);

        // Добавляем слушатель события на удаление фильтра
        document.getElementById("filterTable").addEventListener('click', function (event) {
            if (event.target && (event.target.type === 'radio' || event.target.type === 'checkbox')) {
                updateLocalStorage();
            }
        });
        // Добавляем слушатель события на удаление фильтра
        document.getElementById("removeFilterBtn").addEventListener('click', function () {
                updateLocalStorage();
        });
        // Добавляем слушатель события на удаление всех фильтров
        document.getElementById("removeAllFiltersBtn").addEventListener('click', function () {
            updateLocalStorage();
        });

});
