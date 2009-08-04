<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Edycja Konta </span></legend>

    <form action="<s:url value="/klient/klient_editA.action"/>" method="post">
        <table  style="color:#000000; font-size: 13px;">
            <tr>
                <td><s:textfield name="adress" label="Adres :" value="%{aadres.adress}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="miasto" label="Miasto :" value="%{aadres.miasto}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="wojew" label="Województwo:" value="%{aadres.wojew}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="tel" label="Telefon :" value="%{aadres.tel}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="email" label="email :" value="%{aadres.email}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="typAdres" label="Typ adresu:" value="%{aadres.typ}"/>
                </td>
            </tr>            <tr>
                 <td><s:textarea name="infoAdres" label="Informacje" value="%{aadres.info}" cols="50" rows="10" />
                </td>
            </tr>
        </table>

        <input type="hidden" name="idadr" value="<s:property value="aadres.id"/>" />
        <input type="hidden" name="id" value="<s:property value="klient.id"/>" />

        <input type="submit" value="Zmien Dane" />
    </form>
</fieldset>
<br/>