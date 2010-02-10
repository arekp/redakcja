<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Edycja Konta </span></legend>

    <form action="<s:url value="/klient/klient_editK.action"/>" method="post">
        <table  style="color:#000000; font-size: 13px;">
            <tr>
                <td>    <s:textfield name="nazwa" label="Nazwa Firmy" value="%{klient.nazwa}" size="50"/>
                </td><td></td>
            </tr>
            <tr>
                <!--td><s:textfield name="typ" label="Typ Pren" value="%{klient.typ}"/>
                </td-->
                <td><s:textfield name="statusPren" label="Status prenumeraty:" value="%{klient.statusPren}"/>
                </td>
            </tr>
            <tr>
                <td>
                                        <s:select label="Klasa Klienta:"
    name="klasaKlienta"
    headerKey="1"
    headerValue="%{klient.klasaKlienta}"
    list="CollectionklasaKlienta"
    />

                </td>
            </tr>
            <tr>
                <td><s:textfield name="nip" label="NIP:" value="%{klient.nip}"/>
                </td>
            </tr>
            <tr>
                <td><s:textarea name="info" label="Informacje" value="%{klient.info}" cols="50" rows="10" />
                </td>
            </tr>
        </table>


        <input type="hidden" name="id" value="<s:property value="klient.id"/>" />

        <input type="submit" value="Zmien Dane" />
    </form>
</fieldset>
<br/>