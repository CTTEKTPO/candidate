document.addEventListener('DOMContentLoaded', function () {
        var jobTitles = /*[[${jobTitles}]]*/[];
        var personalCards = /*[[${personalCards}]]*/[];
        console.log("Job Titles in JS: ", jobTitles);
        console.log("Personal Cards in JS: ", personalCards);

        document.getElementById('medianButton').addEventListener('click', function () {
            buildMedianChart(jobTitles, personalCards);
        });

    function buildMedianChart(jobTitles, personalCards) {
        var selectedJobTitleId = document.getElementById('jobTitle').value;
        var dateBefore = document.getElementById('dateBefore').value;
        var dateAfter = document.getElementById('dateAfter').value;

        console.log("dateBefore: ", dateBefore, " dateAfter: ",dateAfter );
        console.log("Job Titles: ", jobTitles);
        console.log("Personal Cards: ", personalCards);
        // Фильтрация карточек по выбранной должности и периоду
        var filteredCards = personalCards.filter(function(card) {
            return card.jobTitle.id == selectedJobTitleId &&
                   card.creationDate >= dateBefore &&
                   card.creationDate <= dateAfter;
        });
        console.log("Filtered Cards: ", filteredCards);

if(filteredCards.length != 0){
        var groupedData = {};
        filteredCards.forEach(function (card) {
        if (!groupedData[card.salary]) {
            groupedData[card.salary] = 0;
        }
        groupedData[card.salary]++;
        });

    // Построение графика
    var ctx = document.getElementById('medianChart').getContext('2d');
    // Проверка, существует ли уже график
    if (window.chart instanceof Chart) {
        // Уничтожение предыдущего графика
        window.chart.destroy();
    }
    window.chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: Object.keys(groupedData),
            datasets: [{
                label: 'Matches and Salary',
                data: Object.keys(groupedData).map(function (salary) {
                    return { x: Number(salary), y: groupedData[salary] };
                }),
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Number of Matches'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Salary'
                    }
                }
            }
        }
    });
}
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
