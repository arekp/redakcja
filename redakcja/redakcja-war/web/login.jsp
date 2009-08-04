<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<form method="POST" action="<s:url value="j_acegi_security_check"/>" >
        Username: <input type="text" name="j_username"><br>
        Password: <input type="password" name="j_password"><br>

  <!--td>Remember me:</td>
  <td><input type="checkbox" name="_acegi_security_remember_me"/></td-->
  <input type="submit" >
</form>