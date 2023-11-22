document.addEventListener('DOMContentLoaded', function () {

    var jobTitles = /*[[${jobTitles}]]*/ [];
    var personalCards = /*[[${personalCards}]]*/ [];

    document.getElementById('medianButton').addEventListener('click', function () {
        buildMedianChart(jobTitles, personalCards);
    });

    function buildMedianChart(jobTitles, personalCards) {

        var groupedData = {};
        personalCards.forEach(function (card) {
            if (!groupedData[card.jobTitle.title]) {
                groupedData[card.jobTitle.title] = [];
            }
            groupedData[card.jobTitle.title].push(card.salary);
        });

        var medianData = {};
        Object.keys(groupedData).forEach(function (title) {
            medianData[title] = calculateMedian(groupedData[title]);
        });

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

        function calculateMedian(values) {
            values.sort(function (a, b) {
                return moment(a, 'YYYY-MM-DD HH:mm:ss') - moment(b, 'YYYY-MM-DD HH:mm:ss');
            });

            var half = Math.floor(values.length / 2);

            if (values.length % 2 === 0) {
                var date1 = moment(values[half - 1], 'YYYY-MM-DD HH:mm:ss');
                var date2 = moment(values[half], 'YYYY-MM-DD HH:mm:ss');
                return moment.duration(date2.diff(date1)).asSeconds() / 2.0;
            } else {
                return moment(values[half], 'YYYY-MM-DD HH:mm:ss').toDate();
            }
        }
    }
});
