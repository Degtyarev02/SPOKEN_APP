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


document.getElementById('chat_send_button').onclick = function sendName() {

    let message = document.getElementById('chat_text').value;
    let container = document.getElementById('all_chat_messages')
    let innerDiv = document.createElement('div');

    if (!message || 0 === message.length) {
        return;
    }

    if (noMessages) {
        noMessages.style.display = 'none';
    }

    document.getElementById('chat_text').value = '';
    innerDiv.appendChild(document.createTextNode(message));
    innerDiv.classList.add("sender-message-block");
    if (senderUser === selectedUser) {
        innerDiv.classList.add("receiver-message");
    }
    container.appendChild(innerDiv);
    chatWindow.scrollTop = chatWindow.scrollHeight;
    sendMessage(senderUser, selectedUser, message);
}

function sendMessage(from, to, text) {
    stompClient.send("/app/" + selectedUser, {}, JSON.stringify({
        from: from,
        to: to,
        message: text
    }));
}

function showGreeting(chatMessage) {

    let container = document.getElementById('all_chat_messages')
    let innerDiv = document.createElement('div');
    innerDiv.classList.add("sender-message-block");
    if (senderUser !== selectedUser) {
        innerDiv.classList.add("receiver-message");
    }
    innerDiv.appendChild(document.createTextNode(chatMessage));
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