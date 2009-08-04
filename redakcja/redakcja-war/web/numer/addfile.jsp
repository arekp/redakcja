<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

 <s:form name="form" enctype="multipart/form-data" method="post" action="/numer_addFile.action">
     <input type="hidden" name="id" value="${id}" />
       <s:file  name="upload"  label="File" />
      <s:submit/>
  </s:form>