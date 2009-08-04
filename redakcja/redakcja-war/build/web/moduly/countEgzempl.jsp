<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:property value="count"/>
<h2>Do Wysłania</h2>
<p><strong>W tym miesiącu <s:date name="sysdate" format="MM-yyyy" /></strong>
mamy zamówionych <b><s:param name="count"/></b> egzemplarzy.
<!--a href="#">More&hellip;</a-->
</p>