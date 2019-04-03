<#import "parts/common.ftl" as common>

<@common.page>
<h2 class="mb-1">Translations</h2>
<div class="container border rounded">
    <form method="post" action="post" class="mt-4">
        <div class="form-group row">
            <label for="textInput" class="col col-form-label">Text</label>
            <div class="col-sm-10">
                <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid','')}" value="<#if translation??>${translation.text}</#if>" id="textInput" aria-describedby="emailHelp" placeholder="Enter text"/>
                <#if textError??>
                    <small class="form-text text-muted">${textError}</small>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputLang" class="col-sm-2 col-form-label">Lang</label>
            <div class="col-sm-10">
                <input type="text" name="lang" class="form-control ${(langError??)?string('is-invalid','')}" value="<#if translation??>${translation.lang}</#if>" id="inputLang" placeholder="Input lang"/>
                <#if langError??>
                    <small class="form-text text-muted">${langError}</small>
                </#if>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Translate</button>
    </form>
</div>

<div class="container mt-5">
    <div class="form-row">
        <div class="form-group col-md-8">
            <form method="get" action="/translation" class="form-inline">
                <input type="text" name="filter" value="${filter?ifExists}" class="form-control" placeholder="Search by lang"/>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Text</th>
            <th scope="col">Lang</th>
            <th scope="col">User</th>
        </tr>
        </thead>
        <tbody>
            <#list translations as translation>
            <tr>
                <td>${translation.text}</td>
                <td>${translation.lang}</td>
                <td>${translation.user.username}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
</@common.page>