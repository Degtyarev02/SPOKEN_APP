<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <h2>edit ${user.username}</h2>
    <form action="/admin/edit/${user.id}" method="post">
        <input type="text" name="username" value="${user.username}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <button type="submit">Edit</button>
    </form>
</@c.mainpage>