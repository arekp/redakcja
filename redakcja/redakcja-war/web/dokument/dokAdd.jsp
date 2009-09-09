<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;fonkontrahent_addt-weight:bold;"> Nowy dokument </span></legend>
    <s:form action="/dokument/dokument_add" method="post" enctype="multipart/form-data">
        <s:hidden name="id_kontrah" value="%{kontrahent.id}" />
        <s:textfield name="tytul" label="Tytuł " value="%{tytul}" size="80" />
        <s:if test="kontrahent.typ=='Reklama'">
            <s:textfield name="powierzchnia" label="Powierzchnia " value="%{powierzchnia}" size="20"/>
            <s:textfield name="cena" label="cena" value="%{cena}" size="20"/>
            <s:textfield name="typ" value="Reklama" readonly="true"/>

        </s:if><s:else>
            <s:textfield name="iloscZnakow" label="Ilość znaków" value="%{iloscZnakow}" size="20"/>
            <s:select label="Typ" name="typ"
                      value="%{typ}"
                      headerKey="1" list="{'Artykuł','Michałki'}" />
                         <s:select label="Grupa Artykułów" name="idGrupy" list="listaGrup"
    listKey="id" listValue="nazwa" multiple="false"  required="true" headerValue="%{dokument.grupa.nazwa}"/>
        </s:else>
        <s:file  name="file"  label="File" required="true"/>
        <s:submit value="Dodaj Artukuł"/>

    </s:form>

</fieldset>