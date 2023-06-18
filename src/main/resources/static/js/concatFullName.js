function concatenateFullName() {
    var surname = document.getElementById('surname').value;
    var name = document.getElementById('name').value;
    var patronymic = document.getElementById('patronymic').value;
    var fullName = surname + ' ' + name + ' ' + patronymic;
    document.getElementById('fullName').value = fullName;
}