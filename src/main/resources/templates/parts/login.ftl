<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label for="inputUsername" class="col-sm-2 col-form-label">User name</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" id="inputUsername" placeholder="User name">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="text" name="password" class="form-control" id="inputPassword" placeholder="Password">
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="form-group row">
            <div class="col-sm-6">
                <button type="submit" class="btn btn-primary"><#if isRegisterForm>Submit<#else>Log in</#if></button>
                <#if !isRegisterForm><a href="/registration">Sign Up</a></#if>
            </div>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-secondary">Log out</button>
    </form>
</#macro>