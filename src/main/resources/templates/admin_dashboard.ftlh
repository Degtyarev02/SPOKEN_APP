<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <div>
        <h2>Hello, ${user.username}</h2>
        <form action="/admin/filter" method="post">
            <input type="text" name="filter" placeholder="Search by username">
        </form>
        <#list userlist as user_from_list>
            <div class="user">
                <div class="header">
                    <span>${user_from_list.id}</span>
                    <span>${user_from_list.username}</span>
                </div>
                <div class="role">
                    <#list user_from_list.roles as role>
                    <span>${role} </span>
                    </#list>
                    <a href="/admin/edit/${user_from_list.id}">EDIT</a>
                </div>
            </div>
        <#else>
            <h3>No users</h3>
        </#list>
    </div>
</@c.mainpage>