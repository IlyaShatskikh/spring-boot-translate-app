<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
    <@login.logout/>
    <span><a href="/user">User list</a></span>
    <div>
        <form method="post" action="post">
            <input type="text" name="text" placeholder="Введите текст">
            <input type="text" name="lang" placeholder="Введите направление перевода">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Перевести</button>
        </form>
    </div>
    <div>Translations</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}}">
        <button type="submit">Найти</button>
    </form>

    <#list translations as translation>
    <div>
        <b>${translation.id}</b>
        <span>${translation.text}</span>
        <i>${translation.lang}</i>
        <strong>${translation.user.username}</strong>
    </div>
    <#else>
    No translations
    </#list>

</@common.page>
