<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Nowy Kontrahent </span></legend>
    <s:form action="kontrahent/kontrahent_add" method="post">
        <s:textfield name="imie" label="Imie " value="%{imie}" size="50" />
        <s:textfield name="nazwisko" label="Nazwisko " value="%{nazwisko}" size="50"/>
        <s:textfield name="ulica" label="Ulica " value="%{ulica}" size="50"/>
        <s:textfield name="miasto" label="Miasto" value="%{miasto}" size="50"/>
        <s:textfield name="email" label="Email" value="%{email}" size="50"/>
        <s:textfield name="tel" label="Telefon" value="%{tel}" size="50"/>
        <s:textfield name="nip" label="Nip" value="%{nip}" size="50"/>
        <s:textfield name="pesel" label="Pesel" value="%{pesel}" size="50"/>
        <s:textfield name="urzadSkarbowy" label="Urzad Skarbowy" value="%{urzadSkarbowy}" size="50"/>
        <s:textfield name="numerKonta" label="Numer Konta " value="%{numerKonta}" size="50"/>
        <s:textfield name="stawka" label="Stawka" value="0" size="50"/>
        <s:select label="Typ" name="typ"
                  value="%{typ}"
                  headerKey="1" list="{'Autor','Reklama','Redaktor','Barter'}" />
        <s:submit value="Zapisz"/>
    </s:form>

</fieldset>
