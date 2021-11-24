<#import "parts/common.ftlh" as c>
<@c.mainpage>

<#--<form action="/registration" method="post">
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
    <div><input type="password" name="password2" placeholder="Confirm password"/></div>
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
</form>-->


    <div class="container">
        <h2 class="sign-in-label">Please sign up</h2>

        <div class="init-form">
            <form action="/registration" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <#if usernameError??>
                    <div>
                        ${usernameError}
                    </div>
                </#if>
                <input type="password" name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div>
                        ${passwordError}
                    </div>
                </#if>
                <div><input type="password" name="password2" placeholder="Confirm password"/></div>
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
                <input type="submit" value="Confirm" class="submit"/>
                <a href="/login" class="registration">Sign in</a>
                ${message!""}
            </form>
            <img src="../static/img/share.png" alt="" width="850" height="540">
        </div>
    </div>
</@c.mainpage>