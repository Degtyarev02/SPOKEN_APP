<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <form action="/admin/edit/${user.id}" method="post">
        <input type="text" name="username" value="${user.username}">
    </form>
    <h2>edit ${user.username}</h2>
</@c.mainpage>