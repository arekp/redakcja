<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">

<form name="presidentsForm" action="<s:url value="/numer_lista.action"/>" >
    <jmesa:struts2TableFacade
        id="daneNumer"
        items="${daneNumer}"
        maxRows="8"
        maxRowsIncrements="8,16,24"
        var="bean"
        >
    <jmesa:htmlTable>
        <jmesa:htmlRow>
            <jmesa:htmlColumn property="nazwa"  title="Nazwa Numeru" >
                   <a href="<s:url value="/numer_set.action?id="/>${bean.id}" >${bean.nazwa}</a>
            </jmesa:htmlColumn>
            <jmesa:htmlColumn property="data" title="Data"/>
            <jmesa:htmlColumn property="naklad"  title="Nakład"/>
            <jmesa:htmlColumn property="status" title="Status"/>
            <jmesa:htmlColumn property="info"/>
            <s:if test="%{#bean.url neq ''}">
            <jmesa:htmlColumn  title="Pobierz numer" property="url" >
                <a href="<s:url value="/numer_sendFile.action?id="/>${bean.id}" >${bean.url}</a>
            </jmesa:htmlColumn>
            </s:if>
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