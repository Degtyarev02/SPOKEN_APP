<#import "parts/footer.ftlh" as f>
<#import "parts/common.ftlh" as c>

<@c.mainpage>
<form action="/main/edit/${user.id}" method="post">
    <input type="text" name="username" placeholder="Username" value="${user.username}">
    <textarea type="text" name="status" placeholder="Status">${user.status}</textarea>
    <button type="submit">Save</button>
</form>
<@f.footer></@f.footer>
</@c.mainpage>