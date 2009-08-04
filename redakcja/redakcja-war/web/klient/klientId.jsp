<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<script type="text/javascript" src="<s:url value="/js/jquery-1.3.min.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.bgiframe.pack.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.jmesa.js" />"></script>
<script type="text/javascript" src="<s:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<s:url value="/css/jmesa.css" />" type="text/css">
<br/>
<fieldset>
<legend><span style="color:#0000FF;font-weight:bold;"> Dane konta -- <a href="<s:url value="/klient/klient_EditkIN.action?id="/><s:property value="klient.id"/>"> Edycja Konta</a></span></legend>
<table border="0" cellpadding="5" style="color:#000000;font-size: 13px;" >
    <tbody>
        <tr>
            <td><b>Nazwa: </b></td>
            <td ><s:property value="klient.nazwa"/></td>
            <td width="100"><b> Prenumerata DO: </b></td>
            <td width="90"><s:date name="klient.prenDo" format="yyyy-MM-dd" /></td>
        </tr>
        <tr>
            <td><b>Informacje: </b></td>
            <td width="500"><s:property value="klient.info"/></td>
            <td><b>Typ: </b></td>
            <td><s:property value="klient.typ"/></td>
        </tr>
        <tr>
            <td><b>Klasa Klienta: </b></td>
            <td><s:property value="klient.klasaKlienta"/></td>
            <td><b>Status prenumeraty: </b></td>
            <td><s:property value="klient.statusPren"/></td>
        </tr>
        <tr>
            <td><b>NIP</b></td>
            <td><s:property value="klient.nip"/></td>
            <td><b>Ilość Egzemblarzy </b></td>
            <td><s:property value="klient.ilosc"/></td>
        </tr>
    </tbody>
</table>

<br/><br/>
<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Adresy </span></legend>
    <form name="presidentsForm" action="<s:url value="/klient/klient_id.action?id="/><s:property value="klient.id"/>" >
        <jmesa:struts2TableFacade
            id="adresy_id"
            items="${klient.adresy}"
            maxRows="8"
            maxRowsIncrements="8,16,24"
            var="bean"
            >
        <jmesa:htmlTable>
            <jmesa:htmlRow>
                <jmesa:htmlColumn property="adress"  title="Adres" >
                    <a href="<s:url value="/klient/klient_adressEdit.action?idadr="/>${bean.id}">${bean.adress}</a>
                </jmesa:htmlColumn>
                <jmesa:htmlColumn property="miasto" title="Miasto"/>
                <jmesa:htmlColumn property="wojew"  title="Wojewodztwo"/>
                <jmesa:htmlColumn property="email"/>
                <jmesa:htmlColumn property="tel"/>
                <jmesa:htmlColumn property="info"/>
                <jmesa:htmlColumn property="typ"/>
            </jmesa:htmlRow>
        </jmesa:htmlTable>
        </jmesa:struts2TableFacade>
    </form>
</fieldset>
<br/>
<fieldset>
<legend><span style="color:#0000FF;font-weight:bold;"> Zamowienia </span></legend>
<table cellpadding="5">
    <tr>
        <td>
            <form name="presidentsForm" action="<s:url value="/klient/klient_id.action?id="/><s:property value="klient.id"/>" >
                <jmesa:struts2TableFacade
                    id="zamowienia_id"
                    items="${klient.zamowienia}"
                    maxRows="8"
                    maxRowsIncrements="8,16,24"
                    var="bean"
                    >
                <jmesa:htmlTable>
                    <jmesa:htmlRow>
                        <jmesa:htmlColumn property="dataPrzedl"  title="Data Wplyniecia" />
                        <jmesa:htmlColumn property="prenOd" title="Prenumerata OD"/>
                        <jmesa:htmlColumn property="prenDo"  title="Prenumerata DO"/>
                        <jmesa:htmlColumn property="ilosc"/>
                        <jmesa:htmlColumn property="typPren" title="Nowe/Przedł"/>
                        <jmesa:htmlColumn property="typ" title="Typ"/>
                    </jmesa:htmlRow>
                </jmesa:htmlTable>
                </jmesa:struts2TableFacade>
            </form>
        </td>
        <td>
        <fieldset>
        <legend><span style="color:#0000FF;font-weight:bold;"> Nowe zamówienie </span></legend>
        <form action="<s:url value="/klient/klient_addZ.action"/>" method="post">
        <table border="0" style="color:#000000; font-size: 11px;">
        <tbody>
        <tr>
            <td> <sx:datetimepicker label="Prenumerata Od"  toggleDuration="300" name="prenOd" toggleDuration="500" displayFormat="dd/MM/yyyy" /> </td>
            <td>Prenumerata na miesiecy</td><td><select name="okres">
                    <option>1</option>
                    <option>6</option>
                    <option>12</option>
            </select></td>
            <td></td>
        </tr><tr>
            <td>Ilosc</td>
            <td><input type="text" name="ilosc" value="" size="3"/></td>
        </tr>
        <tr>

            <td> <sx:datetimepicker label="Data wniosku" value="%{'today'}" toggleType="explode" toggleDuration="500" name="dataPrzedl" displayFormat="dd/MM/yyyy" /> </td>
            <td>Nowe/Przedł</td>
            <td><select name="typ">
                    <option>Nowa</option>
                    <option>Przed&#322;u&#380;enie</option>

            </select></td>
        </tr>
        <tr>
            <td>Typ</td>
            <td><select name="klasa">
                    <option>Pełna</option>
                    <option>Ulgowa</option>
                    <option>Kolporterzy</option>
            </select></td>
        </tr>
    </tr>
    <tr>
        <td><input type="hidden" name="id" value="<s:property value="klient.id"/>" />
            <input type="submit" value="Złóż Zamówienie" />
        </td>
    </tr>
    </tbody>
</table>

</form>
</fieldset>
</td>
</tr>
</table>
</fieldset>
</fieldset>

