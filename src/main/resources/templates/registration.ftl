<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
    <h2 class="mb-1">Registration</h2>
    <#if message??>${message}<#else></#if>
    <@login.login "/registration" true/>
</@common.page>