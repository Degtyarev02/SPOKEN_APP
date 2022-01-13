<#import "parts/common.ftlh" as c>
<#import "parts/navbar.ftlh" as n>

<#assign userhasicon = receiverUser.iconname??>
<@c.mainpage>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <@n.navbar></@n.navbar>
    <div class="container-chat-pages">
        <div class="receiver-info">
            <img src="${userhasicon?then(
            "https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/profile/${receiverUser.iconname}",
            "/static/img/serverimg/profile.png"
            )}" alt="">
            <a href="/main/user/${receiverUser.id}" class="receiver-name">${receiverUser.username}</a>
        </div>
        <div class="chat-window" id="chat-window">
            <div class="all_chat_messages">
                <div id="all_chat_messages" class="chat-messages-container">
                    <#list messages as message>
                        <div class="sender-message-block <#if message.senderUserId == receiverUser.id>receiver-message</#if>">
                            ${message.text}
                            <div class="chat-message-time"><#if message.getDate()??>${message.returnReformatDate()}</#if></div>
                        </div>
                    <#else>
                        <div id="no-messages">No messages</div>
                    </#list>
                </div>
            </div>
        </div>
        <div class="sendmes">
            <textarea id="chat_text" role="textbox" placeholder="Введите сообщение..."></textarea>
            <input type="hidden" id="receiverId" value="${receiverUser.id}">
            <input type="hidden" id="senderId" value="${senderUser.id}">
            <button id="chat_send_button">Send</button>
        </div>
    </div>
    <script>
        const textarea = document.querySelector('textarea');

        textarea.addEventListener('keydown', autosize);

        function autosize() {
            const el = this;
            setTimeout(function () {
                el.style.cssText = 'height:auto; padding:0';
                el.style.cssText = 'height:' + el.scrollHeight + 'px';
            }, 0);
        }
    </script>
    <script src="../static/js/ws.js"></script>

</@c.mainpage>