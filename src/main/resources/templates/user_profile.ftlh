<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <div class="main-message-page">
        <#list messages as message>
            <div class="message">
                <span class="message-author"><a href="/main/user/${message.author.id}">${message.author.username}</a></span>
                <#if message.author.id == currentuser.id>
                    <form action="/main/${message.id}" method="post">
                        <button type="submit">X</button>
                    </form>
                </#if>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}</span>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
</@c.mainpage>