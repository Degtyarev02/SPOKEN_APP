let stompClient = null;
let selectedUser = document.getElementById('receiverId').value;
let senderUser = document.getElementById('senderId').value;
let noMessages = document.getElementById('no-messages');

function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + senderUser, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.from) {
                showGreeting(data.message)
            }
        });
    });
}


document.getElementById('chat_send_button').onclick = function sendName() {

    if (noMessages) {
        noMessages.style.display = 'none';
    }

    let message = document.getElementById('chat_text').value;
    let container = document.getElementById('all_chat_messages')
    let innerDiv = document.createElement('div');
    let text = document.createTextNode(message);
    document.getElementById('chat_text').value = '';
    innerDiv.appendChild(text);
    innerDiv.classList.add("sender-message-block");
    if (senderUser === selectedUser) {
        innerDiv.classList.add("receiver-message");
    }
    container.appendChild(innerDiv);

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
    let text = document.createTextNode(chatMessage);
    innerDiv.classList.add("sender-message-block");
    if (senderUser !== selectedUser) {
        innerDiv.classList.add("receiver-message");
    }
    innerDiv.appendChild(text);
    container.appendChild(innerDiv);
}

connect();