<%@ attribute name="file" required="true" %><%
String file = (String) jspContext.getAttribute("file");
out.println("<div class=\"text-right\"> <a href=\"source/" + file + "\">View Source</a></div>");
%>