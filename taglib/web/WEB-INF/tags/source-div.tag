<%@ attribute name="file" required="true" %><%@ taglib tagdir="/WEB-INF/tags" prefix="d" %>
 <div class="source-div"><div id="title"><a href="/source.jsp?source=${file}"><b>source</b></a></div>
  <div id="source-content"><d:include-source file="${file}"/></div>
 </div><%--

<div style="position: block; left: 100px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/source.jsp?source=${file}"><b>source</b></a></div>
 <div class="source-div"><d:include-source file="${file}"/></div>
--%>