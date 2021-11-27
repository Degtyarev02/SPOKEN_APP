<#import "parts/common.ftlh" as c>
<#assign userhasicon = user.iconname??>
<@c.mainpage>

    <div class="profile-info">
        <img class="icon" src="/img/${userhasicon?then(user.iconname, "profile.png")}" alt="">
        <div class="main-info">
            <h3>${user.username}</h3>
            <span>
                <#if user.status??>
                    ${user.status}
                <#else>
                    Без статуса
                </#if>
            </span>
            <#if user?? && currentuser.id == user.id>
            <a href="/main/edit/${user.id}">Редактировать</a>
            </#if>
        </div>
    </div>


    <div class="main-message-page">
        <#list messages as message>
            <div class="message">
                <span class="message-author"><a href="/main/user/${message.author.id}">${message.author.username}</a>
                <#if user?? && message.author.id == user.id>
                    <form class="delete" action="/main/${message.id}" method="post">
                        <button type="submit">X</button>
                    </form>
                </#if>
                    </span>
                <div class="content-container">
                    <#if  message.filename??>
                        <img src="/img/${message.filename}">
                    </#if>
                </div>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}</span>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
</@c.mainpage>