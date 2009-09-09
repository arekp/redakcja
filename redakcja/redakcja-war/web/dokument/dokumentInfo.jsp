<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">
<s:actionerror />
<div class="post">
<h2 class="title"><a href="<s:url value="/dokument/dokument_send.action?idDokumentu="/>${dokument.id}"><s:property value="%{dokument.tytul}" /></a></h2>
<div class="entry" >
<p>Dokument napisany przez<strong> <s:property value="%{dokument.kont.nazwiskoImie}"/> </strong> wstawiony <s:date name="%{dokument.data}" format="dd/MM/yyyy" />
</p>
<p>
    Zawiera <s:property value="%{dokument.iloscZnakow}"/> znaków i będzie zajmowało około <s:property value="%{dokument.powierzchnia}"/> strony
    i będzie kosztowało <s:property value="%{dokument.cena}"/></br>
</p>
<s:form action="/dokument/dokument_strona" method="post">
    <s:textfield name="nrStrony" label="Numer Strony" value="%{dokument.nrStrony}" size="3"/>
    
    <s:select label="Grupa Artykułów" name="idGrupy" list="listaGrup"
    listKey="id" listValue="nazwa" multiple="false"  required="true" headerValue="%{dokument.grupa.nazwa}"/>
    <s:hidden name="idDokumentu" value="%{dokument.id}" />
    <s:submit value="Zmień parametry" />
</s:form>

<s:if test="dokument.status=='Nowy'">
    <fieldset>
        <legend><span style="color:#0000FF;font-weight:bold;">Przypisz redktora</span></legend>
        <s:form action="/dokument/dokument_redaguj" method="post" label="przypisanie do redagowania">
            <s:select label="Redaktor" name="id_kontrah" list="listaRedaktorow"
                      listKey="id" listValue="nazwiskoImie" multiple="false"  required="true"/>
            <s:hidden name="idDokumentu" value="%{dokument.id}"   />
            <s:submit value="Przypisz do redagowania" />
        </s:form>
    </fieldset>
</s:if>



</div>
<p class="meta">
    <span class="posted">Status <a href="#"><s:property value="%{dokument.status}"/></a> </span>
    <a href="#"  class="permalink">Typ <s:property value="%{dokument.typ}"/></a>
</p>
</div>


<s:if test="idDokumentReda>0">
    
    <s:if test="dokument.status!='Zamkniete'">
        <fieldset>
            <legend><span style="color:#0000FF;fonkontrahent_addt-weight:bold;"> Możesz zlecić </span></legend>
            
            <s:include value="/dokument/dokumentRedagowanie.jsp"/>
            
        </fieldset>
    </s:if>
</s:if>

<p> </p>
<form name="presidentsForm" action="<s:url value="/dokument/dokument_info.action"/>" >
    <s:hidden name="idDokumentu" value="%{dokument.id}"/>
    <jmesa:struts2TableFacade
        id="daneDokumentyDzieci"
        items="${listaDokumentowDzieci}"
        maxRows="8"
        maxRowsIncrements="8,16,24"
        var="bean"
        >
    <jmesa:htmlTable>
        <jmesa:htmlRow>
            
            <jmesa:htmlColumn property="status" title="Status"/>
            <!--jmesa:htmlColumn property="powierzchania" title="powierzchnia"/-->
            <jmesa:htmlColumn property="cena" title="Koszt"/>
            <jmesa:htmlColumn property="data" title="Data"/>
            <jmesa:htmlColumn property="redaktor.nazwiskoImie" title="Redagowane przez"/>
            <jmesa:htmlColumn  title="Akcja" >
                <a href="<s:url value="/dokument/dokument_send.action?idDokumentu="/>${bean.id}">Pobierz wersje</a>
                
            </jmesa:htmlColumn>
        </jmesa:htmlRow>
    </jmesa:htmlTable>
    </jmesa:struts2TableFacade>
</form>

