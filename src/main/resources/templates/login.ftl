<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
<h2 class="mb-1">Login</h2>
<@login.login "/login" false/>
</@common.page>