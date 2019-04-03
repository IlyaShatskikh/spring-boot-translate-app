<#macro login path isRegisterForm>
    <style>
        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }
        .form-signin .checkbox {
            font-weight: 400;
        }
        .form-signin .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>

<div class="text-center">
    <form action="${path}" method="post" class="form-signin">
        <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION?? && RequestParameters.error??>
            <div class="alert alert-danger" role="alert">
                ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
            </div>
        </#if>
        <label for="inputUsername" class="sr-only">Username</label>
        <input type="text" id="inputUsername" name="username" class="form-control ${(usernameError??)?string('is-invalid','')}" value="<#if user??>${user.username}</#if>" placeholder="Username" required autofocus/>
        <#if usernameError??>
            <small class="form-text text-muted">${usernameError}</small>
        </#if>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control ${(passwordError??)?string('is-invalid','')}" placeholder="Password" required/>
        <#if passwordError??>
            <small class="form-text text-muted">${passwordError}</small>
        </#if>

        <#if isRegisterForm>
            <label for="inputPasswordConfirm" class="sr-only">Password confirm</label>
            <input type="password" id="inputPasswordConfirm" name="passwordConfirm" class="form-control ${(passwordConfirmError??)?string('is-invalid','')}" placeholder="Confirm password" required/>
            <#if passwordConfirmError??>
                <small class="form-text text-muted">${passwordConfirmError}</small>
            </#if>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button class="btn btn-lg btn-primary btn-block" type="submit"><#if isRegisterForm>Submit<#else>Sign in</#if></button>
        <div class="mt-2">
            <#if !isRegisterForm><a href="/registration">Register new user</a></#if>
        </div>

        <p class="mt-5 mb-3 text-muted">&copy; 2019</p>
    </form>
</div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-secondary">Log out</button>
    </form>
</#macro>