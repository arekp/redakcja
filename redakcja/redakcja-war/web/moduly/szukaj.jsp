<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<li id="search">
    <h2>Przeszukiwanie zasobów</h2>
    <form id="searchform" method="POST" action="<s:url value="/szukaj.action"/>" >
        <div>
            <table style="font-size: 12px;">
                <tr><td><input type="text" name="ciag" id="s" size="15"  /></td><td></td></tr>
                <tr><td >Klient</td><td><input type="radio" name="szuk_akcja" value="klient" checked="checked" /></td></tr>
                <tr><td>Zarzadca</td><td><input type="radio" name="szuk_akcja" value="zarzadca" /></td></tr>
                <tr><td>Kontrahent</td><td><input type="radio" name="szuk_akcja" value="kontrahent"  /></td></tr>
                <tr><td><input type="submit" value="Szukaj"/></td><td></td></tr>
            </table>
        </div>
    </form>
</li>
