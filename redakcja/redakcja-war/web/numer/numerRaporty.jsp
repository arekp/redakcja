<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Lista raportów dla numeru <s:property value="%{#session.numer.nazwa}" /></span></legend>
    <ul>
        <li>
            <a href="<s:url value="/numer_spis.action?id="/><s:property value="%{#session.numer.id}" />" target="_blank">Spis Treści</a>
        </li>
        <li>
            <a href="<s:url value="/numer_honoraria.action?id="/><s:property value="%{#session.numer.id}" />" target="_blank">Honoraria</a>
        </li>
        <li>
            <a href="<s:url value="/numer_honorariaPlace.action?id="/><s:property value="%{#session.numer.id}" />" target="_blank">Honoraria dla płac</a>
        </li>
    </ul>
</fieldset>