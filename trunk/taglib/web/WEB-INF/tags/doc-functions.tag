<%@ tag import="com.bayareasoftware.chartengine.functions.*,com.bayareasoftware.chartengine.model.*" %>
<%@ attribute name="reducers" required="true" type="java.lang.Boolean" %><%
  java.util.List<FunctionDescriptor> funcs;
  boolean reducers = ((Boolean)jspContext.getAttribute("reducers")).booleanValue();
  if (reducers)
    funcs = BuiltInFunctions.getReducerFunctions();
  else
    funcs = BuiltInFunctions.getSeriesFunctions();
%>
<table class="doc-table">
 <tr><th>Function</th><th>Description</th><th>Argument(s)</th></tr>
<%
  for (FunctionDescriptor fd : funcs) {
    out.println("<tr><td><b>" + fd.getDisplayName() + "</b></td>");
    out.println(" <td>" + fd.getDescription() + "</td><td>");
    java.util.List<FunctionDescriptor.ArgDescriptor> ads = fd.getArgs();
    for (com.bayareasoftware.chartengine.model.FunctionDescriptor.ArgDescriptor ad : fd.getArgs()) {
      out.println("<b>" + ad.getName() + "</b> (" + ad.getType() + ") - "
              + ad.getDescription() + "<br/>");
    }
    out.println("</td></tr>");
  }
%>
</table>