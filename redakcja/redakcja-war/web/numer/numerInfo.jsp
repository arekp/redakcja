<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">

<div class="post">
    <h2 class="title"><a href="#">Numer <s:property value="#session.numer.nazwa" /></a></h2>
    <div class="entry" >
        <p><s:property value="#session.numer.info" /></p>
        <table>
            <tr>
                <td>
                    <fieldset>
                        <legend><span style="color:#0000FF;font-weight:bold;"> Zmień parametry </span></legend>
                        <s:form name="form" method="post" action="/numer_zmien">
                            <s:textfield name="naklad" label="Nakład" value="%{#session.numer.naklad}" size="10"/>
                            <s:select label="Status" name="status"
                                      value="%{#session.numer.status}"
                                      headerKey="1" list="{'Otwarty','Zamknięty'}" />
                            <s:hidden name="id" value="%{#session.numer.id}" />
                            <s:submit value="Zmień"/>
                        </s:form>
                    </fieldset>
                </td>
                <td>
                    <fieldset>
                        <legend><span style="color:#0000FF;font-weight:bold;"> Dodaj pliki </span></legend>
                        <s:form name="form" enctype="multipart/form-data" method="post" action="/numer_addFile">
                            <s:hidden name="id" value="%{#session.numer.id}" />
                            <s:file  name="upload"  label="File" />
                            <s:submit value="Zapisz"/>
                        </s:form>
                    </fieldset>
                </td>
            </tr>
        </table>
    </div>

    <s:iterator value="danePliki" id="plik" status="a">
        <p class="meta">
            <span class="posted">Nazwa pliku <a href="#"><s:property value="%{filename}"/></a> </span>
            <a href="#" class="comments">Wstawione <s:date name="data" format="dd/MM/yyyy" /></a>
            <a href="<s:url value="/numer_sendFile.action?id="/>${id}" target="_blank" class="permalink">Pobierz</a>
        </p>
    </s:iterator>

</div>
<table>
    <tr>

        <td valign="top">
            <fieldset>
                <legend><span style="color:#0000FF;font-weight:bold;"> Lista Dokumetnów w numerze</span></legend>
                <form name="presidentsForm" action="<s:url value="/numer_set.action"/>" >
                    <s:hidden name="id" value="%{#session.numer.id}"/>
                    <jmesa:struts2TableFacade
                        id="daneDokumenty"
                        items="${daneDokumenty}"
                        maxRows="8"
                        maxRowsIncrements="8,16,24"
                        var="beandaneDokumenty"
                        >
                    <jmesa:htmlTable>
                        <jmesa:htmlRow>
                            <jmesa:htmlColumn property="tytul" title="Tyluł"/>
                            <jmesa:htmlColumn property="typ"  title="Typ"/>
                            <!--jmesa:htmlColumn property="powierzchania" title="powierzchnia"/-->
                            <jmesa:htmlColumn property="cena" title="cena"/>
                            <jmesa:htmlColumn property="status" title="Status"/>
                            <s:if test="#session.numer.status == 'Otwarty'">
                                <jmesa:htmlColumn  title="Akcja" >
                                    <a href="<s:url value="/numer_remDok.action?idDokumentu="/>${beandaneDokumenty.id}">Usuń z numeru</a>
                                   <a href="<s:url value="/dokument/dokument_info.action?idDokumentu="/>${beandaneDokumenty.id}">Redakuj dokument</a>
                                </jmesa:htmlColumn>
                            </s:if>
                        </jmesa:htmlRow>
                    </jmesa:htmlTable>
                    </jmesa:struts2TableFacade>
                </form>
            </fieldset>
        </td>
        <td valign="top">
            <s:if test="#session.numer.status == 'Otwarty'">
                <fieldset>
                    <legend><span style="color:#0000FF;font-weight:bold;"> Wolne dokumenty </span></legend>
                    <form name="presidentsForm" action="<s:url value="/numer_set.action"/>" >
                        <s:hidden name="id" value="%{#session.numer.id}"/>
                        <jmesa:struts2TableFacade
                            id="daneDokumentyWolne"
                            items="${daneDokumentyWolne}"
                            maxRows="8"
                            maxRowsIncrements="8,16,24"
                            var="beandaneDokumentyWolne"
                            >
                        <jmesa:htmlTable>
                            <jmesa:htmlRow>
                                <jmesa:htmlColumn property="tytul" title="Tyluł"/>
                                <jmesa:htmlColumn property="typ"  title="Typ"/>
                                <jmesa:htmlColumn property="status" title="Status"/>

                                <jmesa:htmlColumn  title="Akcja" >
                                    <a href="<s:url value="/numer_addDok.action?idDokumentu="/>${beandaneDokumentyWolne.id}">Dodaj do numeru</a>
                                    <a href="<s:url value="/dokument/dokument_info.action?idDokumentu="/>${beandaneDokumentyWolne.id}">Redakuj dokument</a>
                               </jmesa:htmlColumn>

                            </jmesa:htmlRow>
                        </jmesa:htmlTable>
                        </jmesa:struts2TableFacade>
                    </form>
                </fieldset>
            </s:if>
        </td>
    </tr>
</table>



<script type="text/javascript">
    function onInvokeAction(id) {
        setExportToLimit(id, '');
        $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
    }
    function onInvokeExportAction(id) {
        var parameterString = createParameterStringForLimit(id);
        location.href = '<s:url value="/importy/zarzadca_wysylka.action?"/> + parameterString';
    }
</script>