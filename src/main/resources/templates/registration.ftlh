<#import "parts/common.ftlh" as c>
<@c.mainpage>
Add new user
${message!""}
<form action="/registration" method="post">
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
    <div><label> Confirm password: <input type="password" name="password2"/> </label></div>
    <#if passwordEqualsError??>
        <div>
            ${passwordEqualsError}
        </div>
    </#if>
    <#if password2Error??>
        <div>
            ${password2Error}
        </div>
    </#if>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="/login">Sign in</a>
</@c.mainpage>