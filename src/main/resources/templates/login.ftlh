<#import "parts/common.ftlh" as c>
<@c.mainpage>

    <form action="/login" method="post">
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <div><input type="submit" value="Sign In"/></div>
    </form>
    <a href="/registration">Sign up</a>
</@c.mainpage>