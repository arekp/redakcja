<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">
<h3>Wynik Szukania dla ciągu " <s:property value="ciag"/> "</h3>

<form name="presidentsForm" action="<s:url value="/szukaj.action"/>" >
    <s:hidden name="szuk_akcja" value="kontrahent"/>
    <input type="hidden" name="ciag" value="<s:property value="%{ciag}"/>" />
    <jmesa:struts2TableFacade
        id="daneKontrahent"
        items="${daneKontrahent}"
        maxRows="8"
        maxRowsIncrements="8,16,24"
        var="bean"
        >
    <jmesa:htmlTable>
        <jmesa:htmlRow>
            <jmesa:htmlColumn property="Nazwisko"  title="Nazwisko" >
                <a href="<s:url value="/kontrahent/kontrahent_info.action?id="/>${bean.id}" >${bean.nazwisko}</a>
            </jmesa:htmlColumn>
            <jmesa:htmlColumn property="imie" title="imie"/>
            <jmesa:htmlColumn property="typ"  title="Typ"/>
            <jmesa:htmlColumn property="ulica" title="Ulica"/>
            <jmesa:htmlColumn property="miasto" title="Miasto"/>
            <jmesa:htmlColumn property="stawka" title="Stawka"/>
            <jmesa:htmlColumn property="email" title="Email"/>
            <jmesa:htmlColumn property="tel" title="Telefon"/>
            <jmesa:htmlColumn property="nip" title="Nip"/>

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