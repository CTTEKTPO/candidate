/*function redirectToDeletePage() {
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
        // Обновляем текст в параграфе
        var confirmationText = document.getElementById('confirmationText');
        confirmationText.textContent = `Вы действительно хотите удалить кандидата с ID = ${selectedCandidateId} ?`;

        // Показываем подтверждение удаления
        var detailsModal = document.querySelector('.details-modal');
        detailsModal.setAttribute('open', 'open');

        // Подготавливаем ссылку для удаления
        var deleteURL = '/delete-personal-card/' + selectedCandidateId;

//        // При клике на "Да" вызывается функция удаления
//        var yesButton = document.querySelector('.yes');
//        yesButton.addEventListener('click', function () {
//            window.location.href = deleteURL;
//        });
        // Присваиваем ссылку кнопке "Да"
        var yesButton = document.querySelector('.yes');
        yesButton.setAttribute('href', deleteURL);

        // При клике на "Нет" закрываем подтверждение
        var noButton = document.querySelector('.no');
        noButton.addEventListener('click', function () {
            detailsModal.removeAttribute('open');
        });
    } else {
        alert('Выберите кандидата для удаления.');
    }
}

// Присвоение функции обработчика событий на изображение после загрузки страницы
document.addEventListener('DOMContentLoaded', function () {
    var deleteLink = document.getElementById('deleteLink');

    // При клике на изображение вызывается функция redirectToDeletePage
    deleteLink.addEventListener('click', redirectToDeletePage);
});
*/

document.addEventListener('DOMContentLoaded', function () {
    var detailsModal = document.querySelector('.details-modal');
    var confirmationText = document.querySelector('#confirmationText');
    var yesButton = document.querySelector('.yes');
    var noButton = document.querySelector('.no');
    var deleteLink = document.querySelector('#deleteLink');

    detailsModal.addEventListener('click', function (event) {
        event.preventDefault();

        var deleteLink = event.target.closest('.css-modal-details');

        if (deleteLink) {
            var radioButtons = document.getElementsByName('selectedCandidate');
            var selectedCandidateId = null;

            for (var i = 0; i < radioButtons.length; i++) {
                if (radioButtons[i].checked) {
                    selectedCandidateId = radioButtons[i].value;
                    break;
                }
            }

            if (selectedCandidateId !== null) {
                confirmationText.textContent = `Вы действительно хотите удалить кандидата с ID = ${selectedCandidateId} ?`;

                detailsModal.setAttribute('open', 'open');

                var deleteURL = '/delete-personal-card/' + selectedCandidateId;
                yesButton.addEventListener('click', function (event) {
                    event.stopPropagation(); // Предотвращаем всплытие события
                    window.location.href = deleteURL;
                });

                noButton.addEventListener('click', function (event) {
                    event.stopPropagation(); // Предотвращаем всплытие события
                    detailsModal.removeAttribute('open');
                    window.location.href = '/';
                });
            } else {
                alert('Выберите кандидата для удаления.');
            }
        }
    });
});

/*
function openModal() {
  var radioButtons = document.getElementsByName('selectedCandidate');
              var selectedCandidateId = null;

              for (var i = 0; i < radioButtons.length; i++) {
                  if (radioButtons[i].checked) {
                      selectedCandidateId = radioButtons[i].value;
                      break;
                  }
              }
  if (selectedCandidateId !== null) {
    document.getElementById('message').innerHTML = "Вы действительно хотите удалить кандидата с ID = " + selectedCandidateId;
    document.getElementById('modal').style.display = "";
  }
  else {
     alert('Выберите кандидата для удаления.');
  }
}

function closeModal() {
  document.getElementById('modal').style.display = "none";
}

function confirmDelete() {
  var radioButtons = document.getElementsByName('selectedCandidate');
                var selectedCandidateId = null;

                for (var i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].checked) {
                        selectedCandidateId = radioButtons[i].value;
                        break;
                    }
                }
  window.location.href = '/delete-personal-card/' + selectedCandidateId;
}*/
