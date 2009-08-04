<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">

<form name="presidentsForm" action="<s:url value="/klient/klient_find.action"/>" >
    <s:hidden name="ciag" value="%{ciag}"/>
    ${tabelkaHTML}

</form>

<script type="text/javascript">
    function onInvokeAction(id) {
        setExportToLimit(id, '');
        $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
    }
    function onInvokeExportAction(id) {
        var parameterString = createParameterStringForLimit(id);
        location.href = '<s:url value="/klient/klient_find.action?ciag="/><s:property value="%{ciag}" />&' + parameterString;
    }
</script>	