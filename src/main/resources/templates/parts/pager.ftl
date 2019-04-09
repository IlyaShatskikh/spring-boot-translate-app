<#macro pager url page>


<ul class="pagination">
    <#if page.isMultiPage()>
        <li class="page-item">
            <a class="page-link" href="${url}?page=${page.getFirstPage()}">
                First
            </a>
        </li>
    </#if>

    <#if page.isSinglePage()>
        <li class="page-item active">
            <a class="page-link" href="#">1</a>
        </li>
    </#if>

    <#if page.isDoublePage()>
        <li class="page-item <#if page.getCurrPage() == 1>active</#if>">
            <a class="page-link" href="${url}?page=1">1</a>
        </li>
        <li class="page-item <#if page.getCurrPage() == 2>active</#if>">
            <a class="page-link" href="${url}?page=2">2</a>
        </li>
    </#if>

    <#if page.isMultiPage()>
        <#assign range = page.getRange()/>

        <li class="page-item <#if page.getCurrPage() == range[0]>active</#if>">
            <a class="page-link" href="${url}?page=${range[0]}">${range[0]}</a>
        </li>
        <li class="page-item <#if page.getCurrPage() == range[1]>active</#if>">
            <a class="page-link" href="${url}?page=${range[1]}">${range[1]}</a>
        </li>
        <li class="page-item <#if page.getCurrPage() == range[2]>active</#if>">
            <a class="page-link" href="${url}?page=${range[2]}">${range[2]}</a>
        </li>
    </#if>

    <#if page.isMultiPage()>
        <li class="page-item">
            <a class="page-link" href="${url}?page=${page.getLastPage()}">
                Last
            </a>
        </li>
    </#if>
</ul>

</#macro>