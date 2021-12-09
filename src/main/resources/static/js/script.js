let show_hide_comments = document.querySelectorAll('.show-hide-comments');
let comment_list = document.querySelectorAll('.comments-list');
let flag_to_show_comments = false;
function show_comments() {
    for(let i = 0; i < comment_list.length; i++){
        show_hide_comments[i].onclick = function () {
            if(!flag_to_show_comments) {
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
