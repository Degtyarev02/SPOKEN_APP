<#import "parts/common.ftlh" as c>
<#import "parts/navbar.ftlh" as n>
<@c.mainpage>
    <@n.navbar></@n.navbar>
    <div class="admin-header">
        <h2>Hello, ${user.username}</h2>
        <form action="/admin/filter" method="post">
            <input type="text" name="filter" placeholder="Search by username">
        </form>
    </div>

    <div class="user-list-container">
        <div class="users-list">
            <#list userlist as user_from_list>
                <div class="user">
                    <div class="user-header">
                        <span class="id">ID: ${user_from_list.id}</span>
                        <span>Имя: ${user_from_list.username}</span>
                    </div>
                    <div class="user-role">
                        <#list user_from_list.roles as role>
                            <span>Roles: ${role} </span>
                        </#list>
                    </div>
                    <form class="delete-edit-user" action="/admin/${user_from_list.id}" method="post">
                        <a href="/admin/edit/${user_from_list.id}">EDIT</a>
                        <input type="submit" value="DELETE">
                    </form>
                </div>
            <#else>
                <h3>No users</h3>
            </#list>
        </div>
    </div>
</@c.mainpage>