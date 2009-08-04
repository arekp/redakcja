<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">




<sx:tabbedpanel id="test2" cssStyle="width: 720px; height: 600px;" doLayout="true">
<sx:div label="Ostatni miesiac"  >
    Klienci z ostatniego miesiaca
    <form name="presidentsForm" action="<s:url value="/klient/klient_wysylka.action"/>" >
        <s:date name="prenOd" id="%{prenOd}" format="yyyy-MM-dd"/>
        <s:hidden name="prenOd" value="%{prenOd}"/>
        <jmesa:struts2TableFacade
            id="adresy_id"
            items="${wysylkaMiesiacOst}"
            maxRows="8"
            maxRowsIncrements="8,16,24"
            var="bean1" 
            >
        <jmesa:htmlTable>
            <jmesa:htmlRow>
                <jmesa:htmlColumn property="nazwa"  title="Nazwa" />
                <jmesa:htmlColumn property="info" title="info"/>
                <jmesa:htmlColumn property="adresy[0].adress"  title="adres"/>
                <jmesa:htmlColumn property="adresy[0].miasto" title="miasto"/>
                <jmesa:htmlColumn property="ilosc"/>
                <jmesa:htmlColumn property="prenDo"/>
            </jmesa:htmlRow>
        </jmesa:htmlTable>
        </jmesa:struts2TableFacade>
    </form>
</sx:div >
<sx:div   label="Przed Ostatni"   >
    Przed ostatni miesiąć
    <form name="presidentsForm" action="<s:url value="/klient/klient_wysylka_.action"/>" >
        <s:hidden name="prenOd" value="%{prenOd}"/>
        <jmesa:struts2TableFacade
            id="adresy_id"
            items="${wysylkaMiesiacPrzed}"
            maxRows="8"
            maxRowsIncrements="8,16,24"
            var="bean2"
            >
        <jmesa:htmlTable>
            <jmesa:htmlRow>
                <jmesa:htmlColumn property="nazwa"  title="Nazwa" />
                <jmesa:htmlColumn property="info" title="info"/>
                <jmesa:htmlColumn property="adresy[0].adress"  title="adres"/>
                <jmesa:htmlColumn property="adresy[0].miasto" title="miasto"/>
                <jmesa:htmlColumn property="ilosc"/>
                <jmesa:htmlColumn property="prenDo"/>
            </jmesa:htmlRow>
        </jmesa:htmlTable>
        </jmesa:struts2TableFacade>
    </form>
</sx:div >
<sx:div   label="Pozostali"   >
    Pozostali<a href="<s:url value="/raport/wysylka.action"/>" target="_parent">Wydruk pozostali</a>
    <form action="<s:url value="/raport/wysylka.action"/>">
        <s:hidden name="prenOd" value="%{prenOd}"/><input type="submit" value="raport" />
    </form>
    <form name="presidentsForm" action="<s:url value="/klient/klient_wysylka.action"/>" >
        <s:hidden name="prenOd" value="%{prenOd}"/>
        <jmesa:struts2TableFacade
            id="adresy_id"
            items="${daneKlient}"
            maxRows="8"
            maxRowsIncrements="8,16,24"
            var="bean3"
            >
        <jmesa:htmlTable>
            <jmesa:htmlRow>
                <jmesa:htmlColumn property="nazwa"  title="Nazwa" />
                <jmesa:htmlColumn property="info" title="info"/>
                <jmesa:htmlColumn property="adresy[0].adress"  title="adres"/>
                <jmesa:htmlColumn property="adresy[0].miasto" title="miasto"/>
                <jmesa:htmlColumn property="ilosc"/>
                <jmesa:htmlColumn property="prenDo"/>
            </jmesa:htmlRow>
        </jmesa:htmlTable>
        </jmesa:struts2TableFacade>
    </form>
</sx:div >
<sx:div  label="Nie Przedluzyli"   >
    Nie przedluzyli
    <form name="presidentsForm" action="<s:url value="/klient/klient_wysylka.action"/>" >
        <s:hidden name="prenOd" value="%{prenOd}"/>
        <jmesa:struts2TableFacade
            id="adresy_id"
            items="${wysylkaPrzeter}"
            maxRows="8"
            maxRowsIncrements="8,16,24"
            var="bean4"
            >
        <jmesa:htmlTable>
            <jmesa:htmlRow>
                <jmesa:htmlColumn property="nazwa"  title="Nazwa" />
                <jmesa:htmlColumn property="info" title="info"/>
                <jmesa:htmlColumn property="adresy[0].adress"  title="adres"/>
                <jmesa:htmlColumn property="adresy[0].miasto" title="miasto"/>
                <jmesa:htmlColumn property="ilosc"/>
                <jmesa:htmlColumn property="prenDo"/>
            </jmesa:htmlRow>
        </jmesa:htmlTable>
        </jmesa:struts2TableFacade>
    </form>
</sx:div >
</sx:tabbedpanel>

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