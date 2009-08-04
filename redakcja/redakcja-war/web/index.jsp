<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>


<!--%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %-->

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Administrator</title>
<!--s:head theme="ajax" /-->
<meta name="keywords" content="nieruchomości, nieruchomościami, zarząd, zarządzanie, zarządcy, budynkami, dom, domów, wspólnota, mieszkalnymi, mieszkaniowa, mieszkańców, komunalny, administracja, administrowanie, spółdzielnia, zarządca, właściciel, remont, gospodarowanie, modernizacja, prawo, utrzymanie, obiektów, obiektu, budowlany, rewitalizacja, lokator, najemca, wynajem, sprzedaż, powierzchnia, kubatura, tbs, wam, osiedle" />
<meta name="description" content="ADMINISTRATOR - miesięcznik dla zarządców nieruchomości; porady prawne dla właścicieli, wspólnot mieszkańców, administacji i spółdzielni mieszkaniowych, wojskowych agencji mieszkaniowych, towarzystw budownictwa społecznego i innych zarządców zasobów mieszkaniowych" />
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
    <sx:head debug="true" cache="false" compressed="false" extraLocales="pl-PL" />
<div id="menu">
	<ul>
		<li><a href="<s:url value="/"/>">Start</a></li>
		<li class="current_page_item"><a href="#">Klienci</a></li>
        <li><a href="<s:url value="/klient/klient_numer"/>">NUMER <s:date name="#session.numer" format="MM/yyyy" /></a></li>
		<li><a href="#">Raporty</a></li>
		<li><a href="#">Skład</a></li>
	</ul>
</div>
<div id="logo">
	<h1><a href="<s:url value="/"/>">ADMINISTRATOR</a></h1>
	<h2>miesięcznik dla zarządców nieruchomości</h2>
</div>
<div id="splash">
	<img src="<s:url value="/images/img05.jpg"/>" alt="" />
</div>
<hr />
<div id="page">
	<div id="content">
    
  <h1>
  <s:actionmessage />
  </h1>
<!-- Start body -->
<s:if test="#session.body neq null">
    <s:include value="%{#session.body}"/>
</s:if>
<s:if test="#body neq null" >
    <s:include value="%{#body}"/>
</s:if>


</div>
	<!-- end #content -->
	<div id="sidebar">
        <s:include value="/menu.jsp"/>
	</div>
	<!-- end #sidebar -->
</div>
<!-- end #page -->
<div id="footer">
    <authz:authorize ifAllGranted="ROLE_USER">
    <p><a href="<s:url value="/j_acegi_logout"/>">Logout</a></p>
    </authz:authorize>
	<p>(c) 2009 Administrator. Designed by Arkadiusz Ptak <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
</div>
</body>
</html>


