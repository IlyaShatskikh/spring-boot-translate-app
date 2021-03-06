<#import "parts/common.ftl" as common>
<@common.page>
<h2 class="mb-1">User edit</h2>
<form action="/user" method="post">
    <input type="text" value="${user.username}" name="username">
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">save</button>
</form>


</@common.page>