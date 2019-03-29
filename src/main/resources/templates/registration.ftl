<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
    <div>Add new user</div>
    <#if message??>${message}<#else></#if>
    <@login.login "/registration" />
</@common.page>