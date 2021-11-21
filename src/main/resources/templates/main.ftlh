<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <div>
        <div>
            <#if user.isAdmin()>
                <p>Hello admin</p>
            <#else>
                <p>Hello user</p>
            </#if>
        </div>
        <form action="/logout" method="post">
            <button type="submit">Sign out</button>
        </form>

        <form method="post">
            <input type="text" name="text" placeholder="Enter a message">
            <#if textError??>
                <div>
                    ${textError}
                </div>
            </#if>
            <input type="text" name="tag" placeholder="Enter a tag">
            <#if tagError??>
                <div>
                    ${tagError}
                </div>
            </#if>
            <button type="submit">Добавить</button>
        </form>

        <form action="filter" method="post">
            <input type="text" name="filter">
            <button type="submit">Найти</button>
        </form>
    </div>

    <div>Список сообщений</div>
    <#list messages as message>
        <div>
            <i>${message.author.username}</i>
            <b>${message.tag}</b>
            <span>${message.text}</span>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</@c.mainpage>