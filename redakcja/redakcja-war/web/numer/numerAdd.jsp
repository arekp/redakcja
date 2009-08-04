<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Nowy numer </span></legend>
      <form action="<s:url value="/numer_add.action"/>" method="post">
        <table  style="color:#000000; font-size: 13px;">
            <tr>
                <td> <s:textfield name="nazwa" label="Numer" value="%{nazwa}" size="10"/></td>
            </tr>
                        <tr>
                <td>  <sx:datetimepicker label="Data" value="%{data}"  toggleType="explode" toggleDuration="500" name="data" displayFormat="MM-yyyy" /></td>
            </tr>
                      
                        <tr>
                <td><s:textarea name="info" label="Informacje" value="%{info}" cols="50" rows="10" /></td>
            </tr>
        </table>
        <input type="submit" value="Dodaj numer" />
</form>
</fieldset>