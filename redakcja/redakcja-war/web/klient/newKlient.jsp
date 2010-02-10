<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<s:fielderror/>
<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Nowe Konto </span></legend>
    <s:form action="/klient/klient_newKlientZapisz.action" method="post">
  
        <table  style="color:#000000; font-size: 13px;">
            <tr>
                <td>    <s:textfield  required="true" name="nazwa" label="Nazwa Firmy" value="%{nazwa}" size="50"/>
                </td><td></td>
            </tr>
            <tr>
                <!--td-->
                <!--s:textfield name="typ" label="Typ Pren" value="%{klient.typ}"/-->
                <!-- /td-->
                <td>
                        <s:textfield name="statusPren" label="Status prenumeraty:" value="%{statusPren}"/>

                </td>
            </tr>
            <tr><td>
                    <s:select label="Klasa Klienta:"
    name="klasaKlienta"
    headerKey="1"
    headerValue="-- Wybierz typ klienta --"
    list="CollectionklasaKlienta"
    />
                
                </td>
            </tr>
            <tr>
                <td><s:textfield name="nip" label="NIP:" value="%{nip}"/>
                </td>
            </tr>
            <tr>
                <td><s:textarea name="info" label="Informacje" value="%{info}" cols="50" rows="10" />
                </td>
            </tr>
            <tr>
                <td>ADERSY
                </td>
            </tr>
            <tr>
                <td><s:textfield required="true" name="adress" label="Ulica :" value="%{adress}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield required="true" name="miasto" label="Miasto :" value="%{miasto}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="wojew" label="Województwo:" value="%{wojew}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="tel" label="Telefon :" value="%{tel}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="email" label="email :" value="%{email}"/>
                </td>
            </tr>            <tr>
                <td><s:textfield name="typAdres" label="Typ adresu:" value="%{typAdres}"/>
                </td>
            </tr>            <tr>
                 <td><s:textarea name="infoAdres" label="Informacje" value="%{infoAdres}" cols="50" rows="10" />
                </td>
            </tr>
        </table>


        <!--input type="hidden" name="id" value="<s:property value="klient.id"/>" /-->

        <input type="submit" value="Wprowadz klienta" />
    </s:form>
</fieldset>
<br/>