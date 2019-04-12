<#import "parts/common.ftl" as common>
<#import "parts/pager.ftl" as pages>

<@common.page>
<h2 class="mb-1">Translations</h2>
<div class="container border rounded">
    <form method="post" action="post" class="mt-4" onsubmit="return concatLangs()">
        <div class="form-group row">
            <label for="textInput" class="col col-form-label">Text</label>
            <div class="col-sm-10">
                <input type="text" name="origText" class="form-control ${(origText??)?string('is-invalid','')}" value="<#if translation??>${translation.origText}</#if>" id="textInput" placeholder="Enter text"/>
                <#if origText??>
                    <small class="form-text text-muted">${origText}</small>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Lang</label>
            <div class="col-sm-10">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text" for="langFromSelect">From</label>
                    </div>
                    <select class="custom-select" id="langFromSelect" name="langFrom">
                        <!--<option selected>Choose...</option>-->
                        <#if langs??>
                            <#list langs?keys as key>
                                <option value="${key}">${langs[key]}</option>
                            </#list>
                        </#if>
                    </select>
                    <#if langFromError??>
                        <small class="form-text text-muted">${langFromError}</small>
                    </#if>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text" for="langToSelect">To</label>
                    </div>
                    <select class="custom-select" id="langToSelect" name="langTo">
                        <!--<option selected>Choose...</option>-->
                        <#if langs??>
                            <#list langs?keys as key>
                                <option value="${key}">${langs[key]}</option>
                            </#list>
                        </#if>
                    </select>
                    <#if langToError??>
                        <small class="form-text text-muted">${langToError}</small>
                    </#if>
                </div>
            </div>
        </div>
        <input type="hidden" name="lang" id="lang" value="<#if langTo??>${langTo}</#if>" />
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
            <th scope="col">Original text</th>
            <th scope="col">Translated text</th>
            <th scope="col">Lang</th>
            <th scope="col">User</th>
        </tr>
        </thead>
        <tbody>
            <#list page.page.content as translation>
                <tr>
                    <td>${translation.origText}</td>
                    <td><#if translation.resultText??>${translation.resultText}</#if></td>
                    <td>${translation.lang}</td>
                    <td>${translation.user.username}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>

<@pages.pager url page />

<script type="text/javascript">
    function concatLangs(){
            document.getElementById("lang").value = document.getElementById("langFromSelect").value
             + '-'
             + document.getElementById("langToSelect").value
    }
</script>

</@common.page>