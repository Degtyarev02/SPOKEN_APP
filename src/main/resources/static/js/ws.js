let stompClient = null;
let selectedUser = document.getElementById('receiverId').value;
let senderUser = document.getElementById('senderId').value;
let noMessages = document.getElementById('no-messages');
let chatWindow = document.getElementById('chat-window');

function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/' + senderUser, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.from) {
                showGreeting(data.message)
            }
        });
    });
}

//Функция срабатывает при нажатии кнопки отправки сообщения, для отображения сообщения на экране отправителя
document.getElementById('chat_send_button').onclick = function sendName() {

    //Получаем введенное сообщение
    let message = document.getElementById('chat_text').value;
    //Получаем контейнер, куда будет вставляться блок с сообщением
    let container = document.getElementById('all_chat_messages')
    //Создаем div
    let innerDiv = document.createElement('div');

    //Функция не выполнится, если сообщение пустое
    if (!message || 0 === message.length) {
        return;
    }

    //Если это первое сообщение, то надпись no message убирается
    if (noMessages) {
        noMessages.style.display = 'none';
    }

    //Очищаем поле ввода
    document.getElementById('chat_text').value = '';
    //Добавляем текст в созданный div
    innerDiv.appendChild(document.createTextNode(message));
    //Добавляем к созданному div класс
    innerDiv.classList.add("sender-message-block");

    //Настраиваем отображение сообщения в левом угле
    if (senderUser === selectedUser) {
        innerDiv.classList.add("receiver-message");
    }

    //Создаем блок для отображения времени отправки
    let timeDiv = document.createElement('div');
    timeDiv.classList.add('chat-message-time');
    //Добавляем значение работы функции showTime()
    timeDiv.appendChild(document.createTextNode(showTime()));
    innerDiv.appendChild(timeDiv)
    container.appendChild(innerDiv);
    //Прокручиваем страницу до конца вниз
    chatWindow.scrollTop = chatWindow.scrollHeight;
    //Отправляем сообщение в топик-контроллер
    sendMessage(senderUser, selectedUser, message);
}

//Функция для отправки сообщения в контроллер
//Данные отправляются в формате JSON
function sendMessage(from, to, text) {
    stompClient.send("/app/" + selectedUser, {}, JSON.stringify({
        from: from,
        to: to,
        message: text
    }));
}

//Функция отображает сообщения на стороне принимающего
function showGreeting(chatMessage) {

    let container = document.getElementById('all_chat_messages')
    let innerDiv = document.createElement('div');

    innerDiv.classList.add("sender-message-block");

    if (senderUser !== selectedUser) {
        innerDiv.classList.add("receiver-message");
    }
    innerDiv.appendChild(document.createTextNode(chatMessage));

    let timeDiv = document.createElement('div');
    timeDiv.classList.add('chat-message-time');
    timeDiv.appendChild(document.createTextNode(showTime()));
    innerDiv.appendChild(timeDiv)

    container.appendChild(innerDiv);

    chatWindow.scrollTop = chatWindow.scrollHeight;
}

function showTime() {

    let date = new Date();
    let returningDate = "";


    if (date.getHours() < 10) {
        returningDate += '0' + date.getHours();
    } else {
        returningDate += date.getHours();
    }

    returningDate += ":"

    if (date.getMinutes() < 10) {
        returningDate += '0' + date.getMinutes();
    } else {
        returningDate += date.getMinutes();
    }

    return returningDate;

}

connect();