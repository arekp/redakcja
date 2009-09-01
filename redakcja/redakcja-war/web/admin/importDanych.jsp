<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
Wczytaj dane
 <s:form name="form" enctype="multipart/form-data" method="post" action="/admin/form_wczytaj.action">
       <s:file  name="upload"  label="File" />
      <s:submit/>
  </s:form>
  Wczytaj kontrahentow
   <s:form name="form" enctype="multipart/form-data" method="post" action="/admin/form_kontrahent.action">
       <s:file  name="upload"  label="File" />
      <s:submit/>
  </s:form>

