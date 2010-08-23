<%@ tag import="javax.servlet.http.HttpServletRequest"%>
<div id="header">
 <ul><%
 String[] pages = { "index.jsp", "data.jsp", "charts.jsp", "samples.jsp",
              "tag-reference.jsp", "property-reference.jsp", "faq.jsp" };
 String[] labels = { "Home", "Data", "Charts", "Samples", "Tag Reference",
              "Property Reference", "FAQ" };
 String uri = ((HttpServletRequest)request).getRequestURI();
 for (int i = 0; i < pages.length; i++) {
   if (uri.endsWith(pages[i]) || (i == 0 && "/".equals(uri))) {
     out.println("<li id=\"selected\">" + labels[i] + "</li>");
   } else {
     out.println("<li><a href=\"" + pages[i] + "\">" + labels[i] + "</a></li>");
   }
 }
%>
</ul>
</div>
