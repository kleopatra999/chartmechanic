<%@ tag import="com.bayareasoftware.chartengine.ds.DateRecognizer" %>
<table class="doc-table" style="width:600px;">
<tr><th>Format</th><th>Example</th><th>Accepts time format?</th></tr>
<%
final long tmillis = 1165349805680l; // dec 5, 2006, 12:16 pm
java.util.Date d = new java.util.Date(tmillis);
DateRecognizer dr = new DateRecognizer();
DateRecognizer.Format f;
java.util.List l = dr.getFormats();

for (int i = 0; i < l.size(); i++) {
    String cl = (i % 2 == 0) ? "even" : "odd";
    f = (DateRecognizer.Format) l.get(i);
    out.print("<tr class='" + cl + "'><td>" + f.getPattern() + "</td><td>" +
            f.getDateFormat().format(d) + "</td><td>");
    out.print(f.hasTime() ? "yes" : "no");
    out.println("</td></tr>");
}
%>
</table>