
// <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    $(document).ready(function() {
    $('.yes').click(function () {
        // Получить выбранный ID соискателя из радиокнопки
        var selectedId = $('input[type="radio"][name="selectedCandidate"]:checked').val();

        // Выполнить действия для удаления пользователя с выбранным ID
        if (selectedId) {
            // Выполнить AJAX-запрос или вызвать функцию удаления пользователя
            // Пример AJAX-запроса:
            $.ajax({
                url: '/delete-personal-card/' + selectedId,
                type: 'DELETE',
                success: function (response) {
                    // Действия после успешного удаления пользователя
                    console.log('Пользователь удален');
                    window.location.href = '/';
                },
                error: function (xhr, status, error) {
                    // Действия в случае ошибки удаления пользователя
                    console.error('Ошибка при удалении пользователя');
                    // Обработать ошибку или выполнить другие действия
                }
            });
        }
    });

    $('.no').click(function() {
    $('.details-modal').removeAttr('open')
})
})

