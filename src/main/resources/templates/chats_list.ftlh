<#import "parts/common.ftlh" as c>
<#import "parts/footer.ftlh" as f>
<#import "parts/navbar.ftlh" as n>

<@c.mainpage>
    <@n.navbar></@n.navbar>
    <section class="chat-list-container">
        <div class="chat-list-alert-message">Вы можете написать сообщения только пользователям, на которых подписаны!
        </div>
        <section class="all-users-list">
            <#list subscriptions as user>
                <#assign isSubscriber = user.subscribers?seq_contains(currentUser)>
                <#assign userhasicon = user.iconname??>
                <div class="container-profile">
                    <div class="profile-info user-from-users">
                        <div class="icon-rewards">
                            <img class="icon"
                                 src="${userhasicon?then("https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/profile/${user.iconname}", "/static/img/serverimg/profile.png")}"
                                 alt="">
                        </div>
                        <div class="main-info">
                            <h3><a class="go-to-user" href="/main/user/${user.id}">${user.username}</a></h3>
                            <#if isSubscriber>
                                <form action="/main/unsubscribe/${user.id}" method="post">
                                    <button class="unsubscribe" type="submit">Отписаться</button>
                                </form>
                            <#else>
                                <form action="/main/subscribe/${user.id}" method="post">
                                    <button class="subscribe" type="submit">Подписаться</button>
                                </form>
                            </#if>
                            <form action="/chat/${user.id}" method="get">
                                <button class="go-to-chat" type="submit">Написать сообщение</button>
                            </form>
                        </div>
                    </div>
                </div>
            <#else>
                No subscriptions!
            </#list>
        </section>
    </section>
</@c.mainpage>