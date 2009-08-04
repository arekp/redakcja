<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        nazwa  <h2><s:property value="MyDocFileName"/></h2> </br>
        space  <h2><s:property value="SizeFile"/></h2> </br>

        <h2>      Mamy w bazie ----- >   <s:property value="IloscKont"/></h2>

    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
