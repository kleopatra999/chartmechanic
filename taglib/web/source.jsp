<html>
<%@ page import="demo.DemoUtil,java.util.*,java.io.*,java.net.URL" %>
<head>
<%
   String src = request.getParameter("source");
   if (src == null) src = request.getPathInfo();
%>
 <title>Source of <%= src %></title>
</head>
<body>
<%
  URL u = getClass().getResource(src);
  if (u == null) {
     u = getClass().getResource("/web/" + src);
  }
  if (u == null) {
    out.println("source '" + src + "' not found");
    return;
  }
  Reader rdr = new InputStreamReader(u.openStream());
  out.println("<pre>");
  DemoUtil.escapeHtmlStream(rdr, out);
  out.println("</pre>");
  rdr.close();
%>
</body>
</html>
