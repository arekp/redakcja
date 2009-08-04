<%@page  contentType="text/html; charset=UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Dane konta </span></legend>
    <table border="0" cellpadding="5" style="color:#000000;">
        <tbody>
            <tr>
                <td><b>Nazwa: </b></td>
                <td ><s:property value="klient.nazwa"/></td>
                <td><b> Prenumerata DO: </b></td>
                <td><s:property value="klient.prenDo"/></td>
            </tr><input type="hidden" name="id" value="" />
        </tbody>
    </table><form action="sfgbnsf" method="GET">
    </form><input type="submit" value="Złuż Zamówienie" />
    <table border="1">
        <s:set name="dzis" var="<%= new java.util.Date() %>" />
        <tbody>
            <tr>
                <td>Prenumerata Od</td>
                <td><sx:datetimepicker label="toggleType='explode'" value="" toggleType="explode" toggleDuration="500" id="dataOd"/></td>
                <td>Prenumerata Do</td>
                <td><sx:datetimepicker label="toggleType='explode'" value="" toggleType="explode" toggleDuration="500" id="dataDo"/></td>
                <td>ilosc</td>
                <td><input type="text" name="ilosc" value="" size="3"/></td>
            </tr>
            <tr>
                <td>Data wniosku</td>
                <td><sx:datetimepicker label="toggleType='explode'" value="%{= new java.util.Date()}" toggleType="explode" toggleDuration="500" id="dataPrzedl"/></td>
                <td>Typ</td>
                <td><select name="typ">
                        <option>Nowa</option>
                        <option>Przedłużenie</option>
                </select></td>
            </tr>
        </tbody>
    </table>

</fieldset>