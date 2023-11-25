document.addEventListener('DOMContentLoaded', function () {
    var jobTitles = /*[[${jobTitles}]]*/ [];
    var personalCards = /*[[${personalCards}]]*/ [];
    console.log("Job Titles in JS: ", jobTitles);
    console.log("Personal Cards in JS: ", personalCards);

    document.getElementById('medianButton').addEventListener('click', function () {
        buildMedianChart(jobTitles, personalCards);
    });

    function buildMedianChart(jobTitles, personalCards) {
        var selectedJobTitleId = document.getElementById('jobTitle').value;
        var datGeBefore = document.getElementById('dateBefore').value;
        var dateAfter = document.getElementById('dateAfter').value;


        console.log("Job Titles: ", jobTitles);
        console.log("Personal Cards: ", personalCards);
        // Фильтрация карточек по выбранной должности и периоду
        var filteredCards = personalCards.filter(function(card) {
            return card.jobTitle.id === selectedJobTitleId &&
                   card.creationDate >= dateBefore &&
                   card.creationDate <= dateAfter;
        });
        console.log("Filtered Cards: ", filteredCards);
        // Группировка данных по зарплатам
        var groupedData = {};
        filteredCards.forEach(function(card) {
            if (!groupedData[card.salary]) {
                groupedData[card.salary] = [];
            }
            groupedData[card.salary].push(card);
        });

        // Вычисление медианы для каждой зарплаты
        var medianData = {};
        Object.keys(groupedData).forEach(function(salary) {
            medianData[salary] = calculateMedian(groupedData[salary].map(function(card) {
                return card.salary;
            }));
        });

        // Построение графика
        var ctx = document.getElementById('medianChart').getContext('2d');
        var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Object.keys(medianData),
                datasets: [{
                    label: 'Median Salary',
                    data: Object.values(medianData),
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Функция вычисления медианы
        function calculateMedian(values) {
        console.log("Sorted Values: ", values);
            values.sort(function(a, b) {
                return a - b;
            });
            var half = Math.floor(values.length / 2);
            if (values.length % 2 === 0) {
                return (values[half - 1] + values[half]) / 2.0;
            } else {
                return values[half];
            }
        }
    }
});
