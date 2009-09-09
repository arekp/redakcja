<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!-- Nowy(przyśle autor), Redagowany (w trakcie poprawiania), Skończony , Do Składu(przekazany do składu) -->

<s:if test="dokumentred.status=='Nowy'">
    <s:form action="/dokument/dokument_redaguj" method="post" label="przypisanie do redagowania" name="redagowanie">
        <s:select label="Redaktor" name="id_kontrah" list="listaRedaktorow"
                  listKey="id" listValue="nazwiskoImie" multiple="false"  required="true"/>
                  <!--s:hidden name="idDokument" value="%{dokumentred.idRodzica}"/-->
                  <s:hidden name="idDokumentReda" value="%{idDokumentReda}"/>
        <s:submit value="Zleć redagowanie"  />
    </s:form>
    <!-- Zmienia status na złożony -->
</s:if>
<s:elseif test="dokumentred.status=='Redagowany'">
    <ul>
<li>
    <a href="<s:url value="/dokument/dokument_wyslij.action?idDokumentReda="/><s:property value="dokumentred.id"/>" >Wyślij dokument do redaktora </a> <s:property value="%{dokumentred.redaktor.nazwiskoImie}"/>
    na adres <s:property value="%{dokumentred.redaktor.email}"/>
   </li>
   <li>
       Przyjmij poprawiony dokument i zamknij zlecenie redagowania
   <s:form action="/dokument/dokument_zamknij" method="post"  enctype="multipart/form-data">
        <s:file  name="file"  label="File" required="true"/>
                   <s:hidden name="idDokumentReda" value="%{idDokumentReda}"/>
        <s:submit value="Zamknij" />
    </s:form>
    </li>
    </ul>
    <!-- -->
</s:elseif>
<s:if test="dokumentred.status=='Zamkniete'">
    <ul>
        <li> Zleć sprawdzenie przez kolejnego redaktor
    <s:form action="/dokument/dokument_redaguj" method="post" label="przypisanie do redagowania" name="redagowanie">
        <s:select label="Redaktor" name="id_kontrah" list="listaRedaktorow"
                  listKey="id" listValue="nazwiskoImie" multiple="false"  required="true"/>
                  <!--s:hidden name="idDokument" value="%{dokumentred.idRodzica}"/-->
                  <s:hidden name="idDokumentReda" value="%{idDokumentReda}"/>
        <s:submit value="Zleć redagowanie"  />
    </s:form>
        </li>
        <li>
     <a href="<s:url value="/dokument/dokument_sklad.action?idDokumentReda="/>${idDokumentReda}">Wyślij do składu w celu złożenia do druku</a>
        </li>
    </ul>
    <!-- Zmienia status na złożony -->
</s:if>
<s:elseif test="dokumentred.status=='W składzie'">
      Przyjęcie dokumentu po złożeniu spowoduje zamknięcie możliwości modyfikacji dokumentu
   <s:form action="/dokument/dokument_zamknijDok" method="post"  enctype="multipart/form-data">
        <s:file  name="file"  label="File" required="true"/>
       <s:textfield name="iloscZnakow" label="Ilość znaków po składzie" value="%{iloscZnakow}" size="20"/>
                   <s:hidden name="idDokumentReda" value="%{idDokumentReda}"/>
        <s:submit value="Zamknij redagowanie dokumentu" />
    </s:form>
</s:elseif>


