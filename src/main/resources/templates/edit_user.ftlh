<#import "parts/common.ftlh" as c>
<@c.mainpage>

    <div class="section-admin-edit">
        <div class="admin-edit-container">
            <h2>Welcome to edit ${user.username} page!</h2>
            <form class="admin-edit-form" action="/admin/edit/${user.id}" method="post">
                <input class="username" type="text" name="username" value="${user.username}">

                <div class="container-of-roles">
                    <#list roles as role>
                        <div class="check-boxes">
                            <input type="checkbox"
                                   name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                            <p class="roles-names">${role}</p>
                        </div>
                    </#list>
                </div>

                <button type="submit">Подтвердить</button>
            </form>
        </div>
    </div>
</@c.mainpage>