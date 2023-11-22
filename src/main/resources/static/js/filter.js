document.addEventListener('DOMContentLoaded', function () {
    // Переменные для хранения доступных полей и условий
    var availableFields = ["ID", "ФИО", "Дата рождения", "Возраст", "Пол", "Город", "Телефон", "Желаемая должность", "Желаемая зарплата", "Опыт работы", "Образование", "Ключевые навыки", "Комментарии", "Статус", "Дата добавления в базу"];
    var availableConditions = ["Содержит", "Больше", "Меньше", "Равно"];

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
        fieldSelect.id = "filterField";
        populateSelectOptions(fieldSelect, availableFields);
        fieldCell.appendChild(fieldSelect);

        var conditionSelect = document.createElement("select");
        conditionSelect.id = "filterCondition";
        populateSelectOptions(conditionSelect, availableConditions);
        conditionCell.appendChild(conditionSelect);

        var valueInput = document.createElement("input");
        valueInput.type = "text";
        valueInput.id = "filterValue";
        valueCell.appendChild(valueInput);
    }


    window.removeFilter = function removeFilter() {
        const selectedFilter = document.querySelector('input[name="selectedFilter"]:checked');
                if (selectedFilter) {
                    const row = selectedFilter.closest('tr');
                    row.parentNode.removeChild(row);
                }
    }

    window.updateTable = function updateTable() {

    }

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
