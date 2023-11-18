
function redirectToEditPage() {

    // Предотвращаем стандартное действие по клику на ссылке
    event.preventDefault();

    var radioButtons = document.getElementsByName('selectedCandidate');
    var selectedCandidateId = null;

    for (var i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            selectedCandidateId = radioButtons[i].value;
            break;
        }
    }

    if (selectedCandidateId !== null) {
        var editUrl = '/edit-personal-card/' + selectedCandidateId;
        window.location.href = editUrl;
    } else {
        alert('Выберите кандидата для редактирования.');
    }
}

// Присвоение функции обработчика событий на изображение после загрузки страницы
document.addEventListener('DOMContentLoaded', function () {
    var editLink = document.getElementById('editLink');

    // При клике на изображение вызывается функция redirectToEditPage
    editLink.addEventListener('click', redirectToEditPage);
});