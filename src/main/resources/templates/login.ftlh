<#import "parts/common.ftlh" as c>
<@c.mainpage>
<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <#if usernameError??>
        <div>
            ${usernameError}
        </div>
    </#if>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <#if passwordError??>
        <div>
            ${passwordError}
        </div>
    </#if>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="/registration">Sign up</a>
</@c.mainpage>