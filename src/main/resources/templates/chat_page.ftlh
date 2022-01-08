<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <div id = "all_chat_messages" class="all_chat_messages">
        <#list messages as message>
            <div>${message.text}</div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>

    <div class="sendmes">
        <input type="text" id="chat_text">
        <input type="hidden" id="receiverId" value="${receiverUser.id}">
        <input type="hidden" id="senderId" value="${senderUser.id}">
        <button id="chat_send_button">Send</button>
    </div>

    <script src="../static/js/ws.js"></script>

</@c.mainpage>