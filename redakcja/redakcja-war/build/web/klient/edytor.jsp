 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

 <html>
 <head>
     <title>Ajax Widgets</title>
     <!--jsp:include page="/ajax/commonInclude.jsp"/-->
      <sx:head debug="true" cache="false" compressed="false" extraLocales="pl-PL" />
 </head>

 <body>

 <br/>
 NOTES:
 <ul>
     <li>Make sure that there is a 'value' attribute in the textarea with the content for the editor</li>
     <li>This is experimental</li>
 </ul>


 Default Editor configuration:<br/>
 <s:form id="form1" action="AjaxRemoteForm" method="post">
     <sx:textarea name="data" cols="50" rows="10" value="Test Data 1" />
     <s:submit value="Submit"/>
 </s:form>
 <br/>

 Configured Editor configuration:<br/>
 <s:form id="form2" action="AjaxRemoteForm" method="post">
     <sx:textarea id="editor2" name="data" cols="50" rows="10" value="Test Data 2">
         <s:param name="editorControls">textGroup;|;justifyGroup;|;listGroup;|;indentGroup</s:param>
     </sx:textarea>
     <s:submit value="Submit"/>
 </s:form>
 <br/>
<table>
 <sx:datetimepicker label="toggleType='wipe'" value="%{'2006-10-31'}" toggleType="wipe" toggleDuration="300" name="test"/>
 <sx:datetimepicker label="toggleType='explode'" value="%{'2006-07-22'}" toggleType="explode" toggleDuration="500" id="dp2"/>
 <sx:datetimepicker label="toggleType='fade'" value="%{'2006-06-30'}" toggleType="fade" toggleDuration="500"/>
 <sx:datetimepicker label="With value='today'"  name="dddp1" value="%{'today'}" />
 <sx:datetimepicker label="US format, empty" name="dddp2" language="en-us" />
 <sx:datetimepicker label="US format with initial date of 2006-06-26" name="dddp3" value="%{'2006-06-26'}" language="en-us" />
 <sx:datetimepicker label="With initial date of 1969-04-25 and a custom format dd/MM/yyyy" name="dddp5" value="%{'25/04/1969'}" displayFormat="dd/MM/yyyy" />
 <sx:datetimepicker label="In German" name="dddp7" value="%{'2006-06-28'}" language="de-de" />
 <sx:datetimepicker label="In Dutch"  name="dddp8" value="%{'2006-06-28'}" language="nl-nl" />
 <sx:datetimepicker label="US format with initial date of 2006-06-26 and long formatting (parse not supported)" name="dddp12" value="%{'2006-06-26'}" formatLength="long" language="en-us" />
 <sx:datetimepicker label="German format with initial date of 2006-06-26 and long formatting (parse not supported)" name="dddp13" value="%{'2006-06-26'}" formatLength="long" language="de" />
 </table>



 </body>
 </html>
