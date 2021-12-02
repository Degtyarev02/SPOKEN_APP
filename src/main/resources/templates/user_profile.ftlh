<#import "parts/common.ftlh" as c>
<#import "parts/footer.ftlh" as f>
<#assign userhasicon = user.iconname??>
<@c.mainpage>

    <div class="container-profile">
        <div class="profile-info">
            <img class="icon" src="${userhasicon?then("https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/profile/${user.iconname}", "/static/img/serverimg/profile.png")}"
                 alt="">

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
    </div>

    <div class="main-message-page">
        <#list messages as message>
            <div class="message">
                <span class="message-author"><a href="/main/user/${message.author.id}">${message.author.username}</a>
                <#if user?? && user.id == currentuser.id>
                    <form class="delete" action="/main/${message.id}" method="post">
                        <button type="submit">X</button>
                    </form>
                </#if>
                    </span>
                <div class="content-container">
                    <#if  message.filename??>
                        <img src="https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/${message.filename}">
                    </#if>
                </div>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}
                    <#if message.getDate()??>
                        <span class="message-time">${message.returnReformatDate()}</span>
                    </#if></span>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
    <@f.footer></@f.footer>
</@c.mainpage>