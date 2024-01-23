function openUniversalModal(title, entity, onSubmit, itemId, val = '') {
    document.getElementById('modalTitle').innerText = title;
    document.getElementById('modalInput').value = val;
    document.getElementById('universalModal').style.display = 'block';

    // Передаем данные напрямую в функцию обработчик
    document.getElementById('submitBtn').onclick = function () {
        var inputText = document.getElementById('modalInput').value;
        onSubmit(inputText, itemId, entity);
        closeUniversalModal();
    };
}

function closeUniversalModal() {
    document.getElementById('universalModal').style.display = 'none';
}

function openAddUniversalModal(title, entity) {
    openUniversalModal(title, entity, addElement, null);
}

function openEditUniversalModal(title, entity, itemId, val) {
    openUniversalModal(title, entity, editElement, itemId, val);
}

function openDeleteUniversalModal(name, entity, itemId) {
    document.getElementById('deleteText').innerText = `Вы действительно хотите удалить ${name} из таблицы?`;
    // openUniversalModal('Подтвердите удаление', entity, deleteElement, itemId);
    document.getElementById('deleteUniversalModal').style.display = 'block';

    // Передаем данные напрямую в функцию обработчик
    document.getElementById('submitDeleteBtn').onclick = function () {
        deleteElement(itemId, entity);
        closeUniversalDeleteModal()
    };
}

function closeUniversalDeleteModal() {
    document.getElementById('deleteUniversalModal').style.display = 'none';
}

function closeDelete() {
    document.getElementById('deleteUniversalModal').style.display = 'none';
}

async function addElement(value, itemId, entity) {
    // Логика добавления элемента, используя value
    console.log(`Добавить в ${entity}:`, value);

    const data = new FormData()
    if (itemId) {
        data.append('id', itemId)
    }

    if (entity === 'city') {
        // id, name
        data.append('name', value)
    }
    if (entity === 'jobTitle') {
        // id, title
        data.append('title', value)
    }
    if (entity === 'status') {
        // id, field
        data.append('field', value)
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    await fetch('/save/' + entity, {
        body: data,
        method: "post",
        headers: {
                    'X-CSRF-TOKEN': csrfToken,
                    [csrfHeader]: csrfToken,
        },
    })

    window.location.reload()
}

async function editElement(value, itemId, entity) {
    // Логика редактирования элемента, используя value и itemId
    console.log(`Редактировать элемент ${entity} с ID ${itemId}:`, value);

    const data = new FormData()
    if (itemId) {
        data.append('id', itemId)
    }

    if (entity === 'city') {
        // id, name
        data.append('name', value)
    }
    if (entity === 'jobTitle') {
        // id, title
        data.append('title', value)
    }
    if (entity === 'status') {
        // id, field
        data.append('field', value)
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    await fetch('/save/' + entity, {
        body: data,
        method: "post",
        headers: {
                    'X-CSRF-TOKEN': csrfToken,
                    [csrfHeader]: csrfToken,
        },
    })

    window.location.reload()
}

function deleteElement(itemId, entity) {
    // Логика удаления элемента, используя itemId
    console.log(`Удалить элемент ${entity} с ID ${itemId}`);
    window.location.href = '/delete/' + entity + '/' + itemId;
//    fetch('/delete/' + entity + '/' + itemId, {
//        method: "get"
//    })
}

function closeUniversalModalUser(){
    document.getElementById('universalModalUsers').style.display = 'none';
}

function openAddUser(){
    const loginInput = document.getElementById("modalInputLoginUser")
    const passwordInput = document.getElementById("modalInputPasswordUser")
    const accessInput = document.getElementById("modalInputAccessUser")
    const nameInput = document.getElementById("modalInputNameUser")

    document.getElementById("modalTitleUsers").innerHTML = 'Создание пользователя';
    document.getElementById("loginUserLabel").innerHTML = 'Введите логин:';
    loginInput.value = '';
    document.getElementById("passwordUserLabel").innerHTML = 'Введите пароль:';
    passwordInput.value = '';
    document.getElementById("accessUserLabel").innerHTML = 'Доступ:';
    accessInput.value = 'Ограниченный';
    document.getElementById("nameUserLabel").innerHTML = 'Введите ФИО:';
    nameInput.value = '';

    document.getElementById('universalModalUsers').style.display = 'block';

    document.getElementById('submitBtnUsers').onclick = async function () {
        if(loginInput.value === null) alert('Необходимо ввести логин.');
        else{
        const data = new FormData()
        data.append('username', loginInput.value)
        data.append('password', passwordInput.value)
        data.append('authority', accessInput.value)
        data.append('employeeName', nameInput.value)
        data.append('enabled',true)

        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        await fetch('/save/user', {
            body: data,
            method: "post",
            headers: {
                        'X-CSRF-TOKEN': csrfToken,
                        [csrfHeader]: csrfToken,
            },
        })

        window.location.reload()
        // closeUniversalModalUser();
        }
    };
}

function openEditUser(id, login, access, name, enabled){
    const loginInput = document.getElementById("modalInputLoginUser")
    const passwordInput = document.getElementById("modalInputPasswordUser")
    const accessInput = document.getElementById("modalInputAccessUser")
    const nameInput = document.getElementById("modalInputNameUser")

    document.getElementById("modalTitleUsers").innerHTML = 'Редактирование пользователя';
    document.getElementById("loginUserLabel").innerHTML = 'Измените логин:';
    loginInput.value = login;
    document.getElementById("passwordUserLabel").innerHTML = 'Измените пароль:';
    passwordInput.value = '';
    document.getElementById("accessUserLabel").innerHTML = 'Измените доступ:';
    accessInput.value = access;
    document.getElementById("nameUserLabel").innerHTML = 'Измените ФИО:';
    nameInput.value = name;

    document.getElementById('submitBtnUsers').onclick = async function () {
        const data = new FormData()
        data.append('id', id)
        data.append('username', loginInput.value)
        data.append('password', passwordInput.value)
        data.append('authority', accessInput.value)
        data.append('employeeName', nameInput.value)
        data.append('enabled', enabled)
        data.append('old_authority',access)

        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        await fetch('/save/user', {
            body: data,
            method: "post",
            headers: {
                        'X-CSRF-TOKEN': csrfToken,
                        [csrfHeader]: csrfToken,
            },
        })

        window.location.reload()
        // closeUniversalModalUser();
    };

    document.getElementById('universalModalUsers').style.display = 'block';
}

function changeActivity(checkbox, id){
    const data = new FormData()
    data.append('id', id)
    data.append('enabled', !!checkbox.checked)
    // data.append('username', 'Privet')

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch('/save/user/activated', {
         body: data,
         method: "post",
         headers: {
                 'X-CSRF-TOKEN': csrfToken,
                 [csrfHeader]: csrfToken,
         },
    })
    //window.location.reload()
}
