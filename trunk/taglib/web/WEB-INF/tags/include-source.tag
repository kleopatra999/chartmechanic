<%@ attribute name="file" required="true" %><%
String file = (String) jspContext.getAttribute("file");
java.net.URL u = getClass().getResource("/web/" + file);
if (u == null) {
  out.println("ERROR: '" + file + "' not found as resource.");
  return;
}
java.io.Reader rdr = new java.io.InputStreamReader(u.openStream());
out.println("<code><pre>");
demo.DemoUtil.escapeHtmlStream(rdr, out);
out.println("</pre></code>");
rdr.close();
%>