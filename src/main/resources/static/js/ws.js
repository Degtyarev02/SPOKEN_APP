
let stompClient = null;

function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (response) {
            showGreeting(JSON.parse(response.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
document.getElementById('chat_send_button').onclick = function sendName() {
    let message = document.getElementById('chat_text').value;
    stompClient.send("/app/"+document.getElementById('receiverId').value, {}, JSON.stringify(message));

}

function showGreeting(chatMessage) {
    let container = document.getElementById('all_chat_messages')
    let innerDiv = document.createElement('div');
    let text = document.createTextNode(chatMessage);
    document.getElementById('chat_text').value = '';
    innerDiv.appendChild(text);
    container.appendChild(innerDiv);
}

connect();