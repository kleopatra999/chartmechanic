<d:header/>
<div id="content">
<div class="setup-div">
Declare some templates, to setup a common look &amp; feel for all charts.
<%@ include file="charts/templates.jspf" %>
<d:view-source file="charts/templates.jspf"/>
</div>

<div class="setup-div">
Declare some global data sources, if necessary, to be shared by multiple charts.
<%@ include file="charts/data-sources.jspf" %>
<d:view-source file="charts/data-sources.jspf"/>
</div>

<p>
Then start drawing charts:
</p>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/stooge-category.jspf" %></div>
 <d:source-div file="charts/stooge-category.jspf"/>
</div>

<div class="item-div">
<div class="chart-div"><%@ include file="charts/stooge-pie.jspf" %></div>
 <d:source-div file="charts/stooge-pie.jspf"/>
</div>

<div class="item-div">
<div class="chart-div"><%@ include file="charts/timeseries1.jspf" %></div>
 <d:source-div file="charts/timeseries1.jspf"/>
</div>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/weblog.jspf" %></div>
 <d:source-div file="charts/weblog.jspf"/>
</div>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/xy1.jspf" %></div>
 <d:source-div file="charts/xy1.jspf"/>
</div>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/stooge-multi.jspf" %></div>
 <d:source-div file="charts/stooge-multi.jspf"/>
</div>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/timeseries2.jspf" %></div>
 <d:source-div file="charts/timeseries2.jspf"/>
</div>

<div class="item-div">
 <div class="chart-div"><%@ include file="charts/olympics.jspf" %></div>
 <d:source-div file="charts/olympics.jspf"/>
</div>

<p>
<b>&lt;data-table&gt; tag example</b>
<c:data-table datasource="all stooge sales"/>
</p>
</div><%-- end content --%>
<d:footer/>
