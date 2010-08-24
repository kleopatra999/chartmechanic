<%@ attribute name="file" required="true" %><%@ taglib tagdir="/WEB-INF/tags" prefix="d" %>
 <div class="source-div"><div id="title"><a href="source/${file}"><b>source</b></a></div>
  <div id="source-content"><d:include-source file="${file}"/></div>
 </div>