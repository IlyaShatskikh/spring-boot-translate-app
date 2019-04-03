<#import "parts/common.ftl" as common>
<@common.page>
<h2 class="mb-1">User list</h2>
<table class="table">
    <thead class="thead-light">
    <tr>
        <th scope="col">Name</th>
        <th scope="col">Role</th>
        <th scope="col">Action</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <th scope="row">${user.username}</th>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td><a href="/user/${user.id}">edit</a></td>
    </tr>
    </#list>
    </tbody>
</table>
</@common.page>



