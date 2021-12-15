<#import "parts/common.ftlh" as c>
<#import "parts/footer.ftlh" as f>
<#import "parts/navbar.ftlh" as n>
<@c.mainpage>

    <@n.navbar></@n.navbar>
    <section class="users-filter">
        <form action="/main/users" method="get">
            <input type="text" name="findingName" placeholder="Search by username">
            <button type="submit">Search</button>
        </form>
    </section>

    <section class="all-users-list">
        <#list users as user>
            <#assign isSubscriber = user.subscribers?seq_contains(currentUser)>
            <#assign userhasicon = user.iconname??>
            <div class="container-profile">
                <div class="profile-info user-from-users">
                    <img class="icon"
                         src="${userhasicon?then("https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/profile/${user.iconname}", "/static/img/serverimg/profile.png")}"
                         alt="">
                    <div class="main-info">
                        <h3><a class="go-to-user" href="/main/user/${user.id}">${user.username}</a></h3>
                        <div class="subscriptions">
                            <span> <b>Читает:</b> ${user.getSubscriptionsSize()}</span>
                            <span> <b>Читателей:</b> ${user.getSubscribersSize()} </span>
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
                            <#if isSubscriber>
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
        </#list>
    </section>
    <@f.footer></@f.footer>
</@c.mainpage>