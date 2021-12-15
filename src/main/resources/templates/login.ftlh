<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <div class="container">
        <h2 class="sign-in-label">Please sign in</h2>
        <div class="init-form">
            <form action="/login" method="post" class="login-form">
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
                <input type="submit" value="Sign In" class="submit"/>
                <a href="/registration" class="registration">Sign up</a>
            </form>
            <img src="../static/img/share.png" alt="" width="850" height="540">
        </div>

    </div>
</@c.mainpage>