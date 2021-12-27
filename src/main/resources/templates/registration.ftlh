<#import "parts/common.ftlh" as c>
<@c.mainpage>
    <div class="container">
        <h2 class="sign-in-label">Please sign up</h2>

        <div class="init-form">
            <form action="/registration" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <#if usernameError??>
                    <div class="error_text">
                        ${usernameError}
                    </div>
                </#if>
                <input type="password" name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div class="error_text">
                        ${passwordError}
                    </div>
                </#if>
                <div><input type="password" name="password2" placeholder="Confirm password"/></div>
                <#if passwordEqualsError??>
                    <div class="error_text">
                        ${passwordEqualsError}
                    </div>
                </#if>
                <#if password2Error??>
                    <div class="error_text">
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