<%@ tag import="com.bayareasoftware.tag.*" %>
<%@ attribute name="tag" required="true" type="com.bayareasoftware.tag.ITagDoc" %><%
 ITagDoc it = (ITagDoc) jspContext.getAttribute("tag");
 //out.println("itag: " + it);
%>
<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<%
 java.util.List<AttInfo> l = it.getAttributes();
 for (AttInfo ai : l) {
  out.println("<tr><td><code>" + ai.name + "</code></td><td>" + ai.description + "</td><td>");
  String yn = ai.required ? "yes" : "no";
  out.println(yn  + "</td><td>");
  for (String alias : ai.aliases)
    out.println("<code>" + alias + "</code><br/>");
  out.println("</td></tr>");
 }
%>
</table>
