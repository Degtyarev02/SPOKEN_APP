<#import "parts/common.ftlh" as c>
<@c.mainpage>
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
        <button id="chat_send_button">Send</button>
    </div>

    <script src="../static/js/ws.js"></script>

</@c.mainpage>