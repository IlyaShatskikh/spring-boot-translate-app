<#import "parts/common.ftl" as common>

<@common.page>
<h2 class="mb-1">Translations</h2>

<div class="form-row">
    <div class="form-group col-md-6">
        <form method="post" action="post" class="form-inline">
            <input type="text" name="text" class="form-control" placeholder="Input text">
            <input type="text" name="lang" class="form-control ml-2" placeholder="Input lang">
            <button type="submit" class="btn btn-primary ml-2">Translate</button>
        </form>
    </div>
</div>

<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/translation" class="form-inline">
            <input type="text" name="filter" value="${filter}" class="form-control" placeholder="Search by lang">
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
</@common.page>
