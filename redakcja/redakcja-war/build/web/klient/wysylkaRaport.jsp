<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">
<a href="<s:url  value="/wysylka/wysylka_"/><s:property value="%{b}" />.action?akcja=etykieta" target="_blank" >Wydrukuj etykiety</a>

<h3>Dane przygotowane dla numeru <s:date name="#session.numer" format="MM/yyyy" /></h3>

<form name="presidentsForm" action="<s:url value="/wysylka/wysylka_%{b}.action"/>" >
    <s:hidden name="b" value="%{b}"/>
    ${tabelkaHTML}
</form>

<script type="text/javascript">
    function onInvokeAction(id) {
        setExportToLimit(id, '');
        $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
    }
    function onInvokeExportAction(id) {
        var parameterString = createParameterStringForLimit(id);
        location.href = '<s:url value="/wysylka/wysylka_"/><s:property value="%{b}" />.action?' + parameterString;
    }
</script>