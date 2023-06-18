// // Получаем ссылку и картинку по их id
//
// var editImage = document.getElementById('editImage');
// console.log(editImage)
// console.log(editLink)
//
// // Добавляем обработчик события клика на картинку
// editImage.addEventListener('click', function() {
//
//
// });
function editPersonalCard(){
var editLink = document.getElementById('editLink');

// Получаем значение выбранной радиокнопки
var selectedId = document.querySelector('input[name="selectedCandidate"]:checked').value;
console.log((selectedId))

// Заменяем значение переменной {id} в ссылке на выбранное значение
var newHref = editLink.getAttribute('href').replace("{id}", selectedId);
console.log(newHref)

// Выполняем переход по ссылке
window.location.href = newHref;
}