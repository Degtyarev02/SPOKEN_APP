<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <header>
        <form action="/logout" method="post" class="logout">
            <button type="submit">X</button>
        </form>
        <div class="header">
            <form action="filter" method="post" class="filter">
                <input type="text" name="filter" placeholder="Search by tag">
                <button type="submit">Search</button>
            </form>
        </div>
    </header>


    <div class="section">
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
    </div>
</@c.mainpage>