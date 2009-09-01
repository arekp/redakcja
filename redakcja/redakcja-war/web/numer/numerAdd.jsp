<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<fieldset>
    <legend><span style="color:#0000FF;font-weight:bold;"> Nowy numer </span></legend>
       <s:form action="/numer_add" method="post">
              <s:textfield name="nazwa" label="Nazwa " value="%{nazwa}" size="10" />
              <s:textarea name="info" label="Informacje" value="%{info}" cols="20" rows="5" />
              <sx:datetimepicker label="Miesiąc wydania numeru"  toggleDuration="300" name="data" toggleDuration="500" displayFormat="MM/yyyy" />
             <p>Pamiętaj data musi odpowiadać miesiącu w którym jest wydawany numer !!</p>
             <s:submit value="Rozpocznij Numer"/>
    </s:form>

</fieldset>