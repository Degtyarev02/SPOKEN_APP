<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <header>
        <form action="/logout" method="post" class="logout">
            <button type="submit">X</button>
        </form>
        <div class="header">
            <h2>SPOKEN</h2>
            <form action="filter" method="post" class="filter">
                <input type="text" name="filter" placeholder="Search by tag">
                <button type="submit">Search</button>
            </form>
        </div>
    </header>


    <div class="main-message-page">
        <form method="post" class="add-message-field">
            <input class="tag" type="text" name="tag" placeholder="Tag">

            <input class="text" type="text" name="text" placeholder="Enter a message">

            <button type="submit">Добавить</button>
            <#if tagError??>
                <div>
                    ${tagError}
                </div>
            </#if>
            <#if textError??>
                <div>
                    ${textError}
                </div>
            </#if>
        </form>

        <#list messages as message>
            <div class="message">
                <span class="message-author"><a href="/main/user/${message.author.id}">${message.author.username}</a>
                <#if user?? && message.author.id == user.id>
                    <form class="delete" action="/main/${message.id}" method="post">
                        <button type="submit">X</button>
                    </form>
                </#if>
                    </span>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}</span>

            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
</@c.mainpage>