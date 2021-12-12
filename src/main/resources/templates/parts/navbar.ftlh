<#macro navbar>
    <header>
        <div class="navbar">
            <ul class="menu">
                <li><a class="navbar_elements" href="/main">Домой</a></li>
                <li><a class="navbar_elements" href="/main/user/${currentUser.id}">Профиль</a></li>
                <li><a class="navbar_elements" href="/main/users">Пользователи</a></li>
                <#if currentUser.isAdmin()>
                    <li><a class="navbar_elements" href="/admin/">Панель админа</a></li>
                </#if>

            </ul>
            <form action="/logout" method="post" class="logout">
                <button type="submit">X</button>
            </form>
        </div>
        <div class="open-navbar">
            <img class="arrow" src="/static/img/serverimg/rightarrow.png" width="16" height="16" alt="">
        </div>
    </header>
</#macro>