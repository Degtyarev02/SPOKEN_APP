<#import "parts/common.ftlh" as c>
<#import "parts/footer.ftlh" as f>
<#import "parts/navbar.ftlh" as n>
<#assign userhasicon = user.iconname??>
<@c.mainpage>
    <@n.navbar></@n.navbar>

    <div class="container-profile">
        <div class="profile-info">
            <div class="icon-rewards">
                <img class="icon"
                     src="${userhasicon?then("https://firebasestorage.googleapis.com/v0/b/spoken-3c30d.appspot.com/o/${user.iconname}?alt=media&token=5e395e22-0dfd-464b-8653-d6dd58593417", "/static/img/serverimg/profile.png")}"
                     alt="">
                <div class="rewards">
                    <#if messages?size gte 5>
                        <img title="Вы уже опубликовали более 5 постов!" src="/static/img/trophy.png" width="64"
                             height="64" alt="">
                    </#if>

                    <#if subscribersSize gt 10>
                        <img title="У Вас уже более 10 подписчиков!" src="/static/img/medal.png" width="64" height="64"
                             alt="">
                    </#if>

                    <#if user.likedPosts?size gte 100>
                        <img title="Вы оценили более 100 постов!" src="/static/img/subreward.png" width="64" height="64"
                             alt="">
                    </#if>
                </div>
            </div>
            <div class="main-info">
                <h3>${user.username}</h3>
                <div class="subscriptions">
                    <span> <b>Читает:</b> ${subscriptionsSize}</span>
                    <span> <b>Читателей:</b> ${subscribersSize} </span>
                </div>
                <span>
                    <#if user.status??>
                        ${user.status}
                    <#else>
                        Без статуса
                    </#if>
                </span>
                <#if user?? && currentUser.id == user.id>
                    <a class="user_page_button" href="/main/edit/${user.id}">Редактировать</a>
                <#else>
                    <#if user.subscribers?seq_contains(currentUser)>
                        <form action="/main/unsubscribe/${user.id}" method="post">
                            <button class="unsubscribe" type="submit">Отписаться</button>
                        </form>
                    <#else>
                        <form action="/main/subscribe/${user.id}" method="post">
                            <button class="subscribe" type="submit">Подписаться</button>
                        </form>
                    </#if>
                </#if>
            </div>
        </div>
    </div>

    <div class="main-message-page">
        <#list messages as message>
            <div class="message">
                <span class="message-author"><a href="/main/user/${message.author.id}">${message.author.username}</a>
                <#if user?? && user.id == currentUser.id>
                    <form class="delete" action="/main/${message.id}" method="post">
                        <button type="submit">X</button>
                    </form>
                </#if>
                    </span>
                <div class="content-container">
                    <#if  message.filename??>
                        <img src="https://firebasestorage.googleapis.com/v0/b/spoken-3c30d.appspot.com/o/${message.filename}?alt=media&token=5e395e22-0dfd-464b-8653-d6dd58593417">
                    </#if>
                </div>
                <span class="message-text">${message.text}</span>
                <span class="message-tag">#${message.tag}
                    <#if message.getDate()??>
                        <span class="message-time">${message.returnReformatDate()}</span>
                    </#if>
                </span>
                <form class="send_comment" action="/main/comment/${message.id}" method="post">
                    <input class="input_comment" name="text" type="text" placeholder="Комментарий">
                    <button class="submit_comment" type="submit">Отправить</button>
                </form>
                <#if message.usersWhoLiked?seq_contains(currentUser)>
                    <form class="like_post" action="/main/unlike/${message.id}" method="post">
                        <button class="submit_like" type="submit"><img src="/static/img/like.png" alt=""></button>
                        <span class="number_of_likes">${message.usersWhoLiked?size}</span>
                    </form>
                <#else>
                    <form class="like_post" action="/main/like/${message.id}" method="post">
                        <button class="submit_like" type="submit"><img src="/static/img/unlike.png" alt=""></button>
                        <span class="number_of_likes">${message.usersWhoLiked?size}</span>
                    </form>
                </#if>
                <div class="comments">
                    <h2 class="show-hide-comments">Развернуть комментарии</h2>
                    <div class="comments-list">
                        <#list message.getComments() as comment>
                            <div class="comment_one">
                                <span class="comment_author">${comment.author.username}</span>
                                <span class="comment_text">${comment.text}</span>
                                <span class="comment_time">${comment.returnReformatDate()}</span>
                            </div>
                        <#else>
                            <h2>No comments</h2>
                        </#list>
                    </div>
                </div>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
    <@f.footer></@f.footer>
</@c.mainpage>