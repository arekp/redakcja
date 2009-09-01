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
    <h2 class="title"><a href="#"><s:property value="kontrahent.imie" /> <s:property value="kontrahent.nazwisko" /></a></h2>
    <div class="entry" >
        <p>Zamieszkały w <strong><s:property value="kontrahent.miasto" /> </strong>na ulicy <strong><s:property value="kontrahent.ulica" /></strong></p>
        <p> <strong>Dane kontaktowe </strong> </br>
            Email <strong><s:property value="kontrahent.email" /> </strong> </br>
        Telefon <s:property value="kontrahent.tel" /></p>
        <p> <strong>Dane informacyjne </strong> </br>
            NIP <strong><s:property value="kontrahent.nip" /></strong>  </br>
            Pesel: <strong><s:property value="kontrahent.pesel" /></strong> </br>
            Urząd Skarbowy <strong><s:property value="kontrahent.urzadSkarbowy" /> </strong> </br>
        Numer konta bankowego <strong><s:property value="kontrahent.numerKonta" /></strong></p>
        <p><s:property value="kontrahent.info" /></p>
        <p> Stawka <strong><s:property value="kontrahent.stawka" /> PLN</strong></p>
    </div>
    <p class="meta">
        <span class="posted"><s:property value="kontrahent.typ" /></span>
        <a href="#" class="permalink">Stawka <strong><s:property value="kontrahent.stawka" /> PLN</a>
        <a href="<s:url value="/kontrahent/kontrahent_editForm.action?id="/><s:property value="kontrahent.id" />" class="comments">Edit</a>
           <s:if test="kontrahent.typ=='Autor'">
        <a href="<s:url value="/dokument/dokument_addFor.action?id_kontrah="/><s:property value="kontrahent.id" />" class="comments">Dodaj dokument</a> .
        </s:if>
</p>
</div>

<form name="presidentsForm" action="<s:url value="/dokument/dokument_lista.action"/>" >
    <jmesa:struts2TableFacade
        id="daneKontrahent"
        items="${listaDokumentow}"
        maxRows="8"
        maxRowsIncrements="8,16,24"
        var="bean"
        >
    <jmesa:htmlTable>
        <jmesa:htmlRow>
            <jmesa:htmlColumn property="tytul" title="Tyluł"/>
            <jmesa:htmlColumn property="typ"  title="Typ"/>
            <jmesa:htmlColumn property="status"  title="Status"/>
            <s:if test="kontrahent.typ=='Reklama'">
                <jmesa:htmlColumn property="powierzchania" title="powierzchnia"/>
            </s:if>
            <jmesa:htmlColumn property="cena" title="cena"/>
            <jmesa:htmlColumn property="data" title="Data"/>
            <jmesa:htmlColumn  title="Akcja" >
                           <s:if test="kontrahent.typ=='Autor'">
                <a href="<s:url value="/dokument/dokument_info.action?idDokumentu="/>${bean.id}">Redakuj dokument</a>
                </s:if>
                <s:if test="kontrahent.typ=='Redaktor'">
                     <a href="<s:url value="/dokument/dokument_info.action?idDokumentu="/>${bean.idRodzica}">Redakuj dokument</a>
                </s:if>
            </jmesa:htmlColumn>
        </jmesa:htmlRow>
    </jmesa:htmlTable>
    </jmesa:struts2TableFacade>
</form>


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
