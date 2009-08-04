<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">

<h3>Lista zarządców kturym będziemy wysyłać zaproszenia od numeru licencji <s:property value="id_licencji"/></h3>
<form action="<s:url  value="/importy/zarzadca_nrLicencji.action"/>" method="POST">
    <input type="text" name="id_licencji" value="${id_licencji}" size="10" />
    <input type="submit" value="Zmień" />
</form>
<p>
<a href="<s:url  value="/importy/zarzadca_zaproszenie.action"/>" target="_blank" > Wygeneruj zaproszenia </a>
 < -------- >
<a href="<s:url  value="/importy/zarzadca_etykiety.action"/>" target="_blank" > Wygeneruj etykiety </a>
</p>

<form name="presidentsForm" action="<s:url value="/importy/zarzadca_wysylka.action"/>" >
    <jmesa:struts2TableFacade
        id="daneZarzadca"
        items="${daneZarzadca}"
        maxRows="8"
        maxRowsIncrements="8,16,24"
        var="bean"
        >
    <jmesa:htmlTable>
        <jmesa:htmlRow>
            <jmesa:htmlColumn property="numerLicencji"  title="Numer Licencji" />
            <jmesa:htmlColumn property="imie" title="Imie"/>
            <jmesa:htmlColumn property="nazwisko"  title="Nazwisko"/>
            <jmesa:htmlColumn property="dataPrzyznania" title="Data Przyznania"/>
            <jmesa:htmlColumn property="adres"/>
            <jmesa:htmlColumn property="miasto"/>
            <jmesa:htmlColumn property="wojew"/>
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