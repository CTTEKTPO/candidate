document.addEventListener('DOMContentLoaded', function () {
    var deleteImg = document.querySelector('.img-delete');
    var modal = document.getElementById('myModal');
    var closeBtn = document.querySelector('.modal-close-delete');
    var confirmationText = document.getElementById('confirmationText');
    var confirmButtonYes = document.querySelector('.confirm-button-yes');
    var confirmButtonNo = document.querySelector('.confirm-button-no');

    deleteImg.addEventListener('click', function () {
        var radioButtons = document.getElementsByName('selectedCandidate');
        selectedCandidateId = null;

        for (var i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].checked) {
                selectedCandidateId = radioButtons[i].value;
                break;
            }
        }


        if (selectedCandidateId !== null) {
            confirmationText.textContent = `Вы действительно хотите удалить кандидата с ID = ${selectedCandidateId} ?`;
            modal.style.display = 'block';
        } else {
            alert('Выберите кандидата для удаления.');
        }
    });

    closeBtn.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    confirmButtonNo.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    confirmButtonYes.addEventListener('click', function () {
        // Обработчик на бэкенде
        window.location.href = '/delete-personal-card/' + selectedCandidateId;
    });
});

