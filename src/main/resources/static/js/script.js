let show_hide_comments = document.querySelectorAll('.show-hide-comments');
let comment_list = document.querySelectorAll('.comments-list');
let flag_to_show_comments = false;

let thumbnailStockSrc = null;

//Запоминаем исходную миниатюру, для загрузчика картинок
if (document.getElementById("thumbnail")) {
    thumbnailStockSrc = document.getElementById("thumbnail").src;
}

//Функция для отображения комментариев
function show_comments() {
    for (let i = 0; i < comment_list.length; i++) {
        show_hide_comments[i].onclick = function () {
            if (!flag_to_show_comments) {
                comment_list[i].style.cssText = "height: auto;";
                flag_to_show_comments = true;
                show_hide_comments[i].innerText = "Скрыть комментарии";
            } else {
                show_hide_comments[i].innerText = "Развернуть комментарии"
                comment_list[i].style.cssText = "height: 0;";
                flag_to_show_comments = false;
            }
        }
    }

}

show_comments();

let open_navbar = document.querySelector('.open-navbar');
let navbar = document.querySelector('.navbar');
let arrow = document.querySelector('.arrow')
let open_navbar_flag = false;

//Функция для адаптивности мобильной версии, показ навигационной панели
function show_navbar() {
    if (open_navbar) {
        open_navbar.onclick = function () {
            if (!open_navbar_flag) {
                navbar.classList.add("open");
                arrow.style.cssText = "transform: rotate(90deg);"
                open_navbar_flag = true;
            } else {
                navbar.classList.remove("open");
                arrow.style.cssText = "transform: rotate(0);"
                open_navbar_flag = false;
            }
        }
    }
}

show_navbar();


//Для всех input с загрузкой картинок - показывать миниатюры
$(document).ready(function () {
    $('#file').change(function () {
        showImageThumbnail(this);
    });
});


//Функция, которая показывает миниатюру загружаемого файла
function showImageThumbnail(fileInput) {

    const file = fileInput.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        $('#thumbnail').attr('src', e.target.result);
    };


    if (file) {
        reader.readAsDataURL(file);
    } else {
        $('#thumbnail').attr('src', thumbnailStockSrc);
    }
}
