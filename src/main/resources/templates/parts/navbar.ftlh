<#macro navbar>
    <header>
        <div class="navbar">
            <ul class="menu">
                <li><a href="/main">Домой</a></li>
                <li><a href="/main/user/${user.id}">Профиль</a></li>
                <#if user.isAdmin()>
                    <li><a href="/admin/">Панель админа</a></li>
                </#if>

            </ul>
            <form action="/logout" method="post" class="logout">
                <button type="submit">X</button></form>
        </div>
    </header>
</#macro>