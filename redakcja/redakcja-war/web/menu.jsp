<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<authz:authorize ifNotGranted="ROLE_USER">
    <s:actionerror />
    <form method="POST" action="<s:url value="/j_acegi_security_check"/>" >
        Username: <br/><input type="text" name="j_username"><br/>
        Password: <br/><input type="password" name="j_password"><br/>

        <!--td>Remember me:</td>
  <td><input type="checkbox" name="_acegi_security_remember_me"/></td-->
        <input type="submit" value="Zaloguj">
    </form>
</authz:authorize>

<authz:authorize ifAllGranted="ROLE_USER">
    <ul>
        <s:include value="/moduly/szukaj.jsp"/>

        <li>  <h2>Numer</h2>
            <ul>
                <li><a href="<s:url value="/klient/klient_newKlient.action"/>">Nowy Klient</a></li>
                <!--li><a href="<!--s:url value="/numer_addFor.action"/>">Nowy Numer</a></li>
                <li><a href="<!--s:url value="/numer_lista.action"/>">Lista numerów</a></li-->

            </ul>
        </li>
    </authz:authorize>

    <authz:authorize ifAnyGranted="ROLE_MARKETING">
        <li>         <h2>Ilość egzemplarzy </h2>

            Do wysyłki mamy
            <s:if test="#session.numer neq null">
                <s:bean name="moduly.dane" id="uid">
                    <s:param name="test" value="%{#session.numer}"/>
                   <h1  align="center"><s:property value="%{iloscPren}" /></h1>
                    dla numeru <s:date name="#session.numer" format="MM/yyyy" />
                </s:bean>
            </s:if>
            <s:else>
                <s:bean name="moduly.dane" id="uid" >
                    <h1  align="center"><s:property value="%{iloscPren}" /></h1>
                </s:bean>
            </s:else>
        </li>
        <li>
            <h2>Wysyłka</h2>
            <ul>
                <li><a href="<s:url value="/wysylka/wysylka_ostatni.action"/>">Ostatni Miesiąc</a></li>
                <li><a href="<s:url value="/wysylka/wysylka_przed.action"/>">Przed Ostatni Miesiąc</a></li>
                <li><a href="<s:url value="/wysylka/wysylka_reszta.action"/>">Pozostali</a></li>
                <!--li><a href="<s:url value="/wysylka/wysylka_faktura.action"/>">Do wysłania faktur</a></li-->
                <li><a href="<s:url value="/wysylka/wysylka_brak.action"/>">Nie Przedłużyli</a></li>
            </ul>
            <h2>Marketing </h2>
            <ul>
                <li><a href="<s:url value="/importy/zarzadca_import.action"/>">Import Zarządców</a></li>
                <li><a href="<s:url value="/importy/zarzadca_wysylka.action"/>">Lista Zarządców do wysłania</a></li>
                <li>    <form action="<s:url value="/klient/klient_numer.action"/>" method="post">
                        <sx:datetimepicker label="Ustaw numer nad którym pracujesz" value="%{#session.numer}"  toggleType="explode" toggleDuration="500" name="numer" displayFormat="MM-yyyy" />
                        <input type="submit" value="ustaw miesiac" />
                    </form>
                </li>
            </ul>
        </li>
    </authz:authorize>

    <authz:authorize ifAnyGranted="ROLE_ADMIN">
        <li>
            <h2>Administracja</h2>
            <ul>
                <li><a href="<s:url value="witam.action"/>">WITAM zaczynasz</a></li>
                <li><a href="<s:url value="/admin/form_in.action"/>">Import danych</a></li>
            </ul>
        </li>

    </authz:authorize>
</ul>
