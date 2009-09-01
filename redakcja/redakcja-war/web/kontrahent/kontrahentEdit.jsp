<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Edycja Kontrahenta </span></legend>
    <s:form action="/kontrahent/kontrahent_edit.action" method="post" >
        <s:hidden name="id" value="%{kontrahent.id}" />
        <s:textfield name="imie" label="Imie " value="%{kontrahent.imie}" size="50" />
        <s:textfield name="nazwisko" label="Nazwisko " value="%{kontrahent.nazwisko}" size="50"/>
        <s:textfield name="ulica" label="Ulica " value="%{kontrahent.ulica}" size="50"/>
        <s:textfield name="miasto" label="Miasto" value="%{kontrahent.miasto}" size="50"/>
        <s:textfield name="email" label="Email" value="%{kontrahent.email}" size="50"/>
        <s:textfield name="tel" label="Telefon" value="%{kontrahent.tel}" size="50"/>
        <s:textfield name="nip" label="Nip" value="%{kontrahent.nip}" size="50"/>
        <s:textfield name="pesel" label="Pesel" value="%{kontrahent.pesel}" size="50"/>
        <s:textfield name="urzadSkarbowy" label="Urzad Skarbowy" value="%{kontrahent.urzadSkarbowy}" size="50"/>
        <s:textfield name="numerKonta" label="Numer Konta " value="%{kontrahent.numerKonta}" size="50"/>
        <s:textfield name="stawka" label="Stawka" value="%{kontrahent.stawka}" size="10" />
        <s:select label="Typ" name="typ"
                  value="%{kontrahent.typ}"
                  headerKey="1" list="{'Autor','Reklama','Redaktor','Barter'}" />
        <s:textarea name="info" label="Informacje" value="%{kontrahent.info}" cols="50" rows="10" />
        <s:submit value="Zmień"/>
    </s:form>

</fieldset>
