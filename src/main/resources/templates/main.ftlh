<#import "parts/common.ftlh" as c>
<#import "parts/footer.ftlh" as f>
<@c.mainpage>
    <header>
        <form action="/logout" method="post" class="logout">
            <button type="submit">X</button>
        </form>
        <div class="header">
            <h2>SPOKEN</h2>
            <form action="filter" method="post" class="filter">
                <input type="text" name="filter" placeholder="Search by tag" value="${filter!}">
                <button type="submit">Search</button>
            </form>
        </div>
    </header>


    <section class="main-message-page">
        <form method="post" class="add-message-field" enctype="multipart/form-data">
            <input class="tag" type="text" name="tag" placeholder="Tag">
            <input class="text" type="text" name="text" placeholder="Enter a message">
            <input id="file" type="file" name="file"> <label for="file"><img width="50" height="50"
                                                                             src="../static/img/serverimg/photo.png"
                                                                             alt=""></label>
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
                <div class="content-container">
                    <#if  message.filename??>
                        <img src="https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/${message.filename}">
                    </#if>
                </div>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}
                    <#if message.getDate()??>
                        <span class="message-time">${message.returnReformatDate()}</span>
                    </#if>
                </span>
                <form class="send_comment" action="main/comment/${message.id}" method="post">
                    <input class="input_comment" name="message" type="text" placeholder="Комментарий">
                    <button class="submit_comment" type="submit">Отправить</button>
                </form>
                <div class="comments">
                    <#list message.getComments() as comment>
                        <div class="comment_one">
                            <span class="comment_author">${comment.author.username}</span>
                            <span class="comment_text">${comment.text}</span>
                        </div>
                    <#else>
                        <span>No comments</span>
                    </#list>
                </div>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </section>

    <@f.footer></@f.footer>
</@c.mainpage>