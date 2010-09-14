



<html>
<head>
<title>ChartMechanic Tag Library Demo</title>
<link href="demo.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="header">
 <ul><li><a href="index.jsp">Home</a></li>
<li><a href="data.jsp">Data</a></li>
<li><a href="charts.jsp">Charts</a></li>
<li><a href="samples.jsp">Samples</a></li>
<li id="selected">Tag Reference</li>
<li><a href="property-reference.jsp">Property Reference</a></li>
<li><a href="faq.jsp">FAQ</a></li>

</ul>
</div>


<div id="content">
<p>
<ul>
 <li><a href="#data"><code>&lt;data&gt;</code></a></li>
 <li><a href="#props"><code>&lt;props&gt;</code></a></li>
 <li><a href="#chart"><code>&lt;chart&gt;</code></a></li>
 <li><a href="#series"><code>&lt;series&gt;</code></a></li>
 <li><a href="#series-function"><code>&lt;series-function&gt;</code></a></li>
 <li><a href="#marker"><code>&lt;marker&gt;</code></a></li>
 <li><a href="#data-table"><code>&lt;data-table&gt;</code></a></li>
</ul>
</p>

<a name="data"></a><h2><code>data</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>id</code></td><td>Identifier for this data source, so that other tags can refer to it.  The ID should be unique within a JSP page.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>type</code></td><td>Type of the data source.  Currently only CSV or SQL are supported.  Default assumes CSV</td><td>
no</td><td>
</td></tr>
<tr><td><code>jndiName</code></td><td>JNDI name of a SQL data source. Use either this or 'driver'/'url' are required</td><td>
no</td><td>
<code>jndi</code><br/>
</td></tr>
<tr><td><code>driver</code></td><td>JDBC driver class for when type='SQL'</td><td>
no</td><td>
</td></tr>
<tr><td><code>url</code></td><td>JDBC driver URL for when type='SQL'</td><td>
no</td><td>
</td></tr>
<tr><td><code>username</code></td><td>resource username for data URL</td><td>
no</td><td>
<code>user</code><br/>
</td></tr>
<tr><td><code>password</code></td><td>resource password for data URL</td><td>
no</td><td>
<code>passwd</code><br/>
</td></tr>

</table>

</p>

<a name="props"></a><h2><code>props</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>template</code></td><td>Names this bag of properties as a <b>template</b>, which lets these properties be shared across multiple charts.</td><td>
no</td><td>
</td></tr>
<tr><td><code>prefix</code></td><td>Provides a shorthand for targeting this bag of properties at one particular element of a chart. See the <a href="charts.jsp#properties">chart properties</a> section for details on how the shorhand works.  Valid values for prefix are: <code>chart plot renderer title legend domain-axis range-axis-0 range-axis-1 range-axis-2 range-axis-3 </code></td><td>
no</td><td>
</td></tr>

</table>

</p>

<a name="chart"></a><h2><code>chart</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>width</code></td><td>The width of the chart, in pixels</td><td>
no</td><td>
</td></tr>
<tr><td><code>height</code></td><td>The height of the chart, in pixels</td><td>
no</td><td>
</td></tr>
<tr><td><code>plotType</code></td><td>Specifies which kind of plot to use for the chart.  Note that only certain data types for the X and Y axes are valid for a given type of plot; see <a href="charts.jsp#plot-types">the  charts document</a> for compatibility.<br/>Valid values are<code> PLOT_XY PLOT_TIME PLOT_PIE PLOT_PIE3D PLOT_CATEGORY PLOT_RING PLOT_HISTOGRAM PLOT_GANTT</code>.<br/>If <code>plotType</code> is not set, the chart tag will try to infer your desired plot type, based on the <a href="data.jsp#data-types">data type</a> of the X columns of your series.</td><td>
no</td><td>
</td></tr>
<tr><td><code>graphType</code></td><td>Sets the default graph type (renderer) to be used for series of this chart (e.g., lines, bars, dots, etc). Individual <code>&lt;series&gt;</code> tags may override the default graphType.  See <a href="charts.jsp#graph-types">graph types</a> section for a list of valid graph types.</td><td>
no</td><td>
<code>renderType</code><br/>
</td></tr>
<tr><td><code>ttl</code></td><td>Specifies a time-to-live, in seconds, for the cache entry of this chart.  Charts with cache entries younger than the TTL will not be re-drawn.  A ttl of <code>0</code> will never use the cache; a ttl of <code>1</code> will always use the cache.  The default is <code>120</code>.</td><td>
no</td><td>
</td></tr>
<tr><td><code>title</code></td><td>Sets the title text of the chart.  Shorthand for setting chart property <code>title.text</code></td><td>
no</td><td>
</td></tr>
<tr><td><code>template</code></td><td>Provides a bag of pre-set chart properties (a <a href="charts.jsp#templates">template</a>).  If no template is set for a chart, then a template called <code>default</code> will be used, if oneis defined.</td><td>
no</td><td>
</td></tr>
<tr><td><code>&lt;standard HTML img tag attributes&gt;</code></td><td>All of the standard attributes of the HTML <code>img</code> tag are passed through as-is from the <code>chart</code> tag to the generated <code>img</code> tag.  The supported attributes are: <code>alt align border hspace longdesc vspace class dir id lang style onabort onclick ondblclick onmousedown onmousemove onmouseout onmouseover onmouseup onkeydown onkeypress onkeyup</code></td><td>
no</td><td>
</td></tr>

</table>

</p>

<a name="series"></a><h2><code>series</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>name</code></td><td>The name of this series as it will appear on the chart.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>graphType</code></td><td>Specifies the <a href="charts.jsp#graph-types">graph type</a> for this series.  This will override the default graph type set for the entire chart, if any.</td><td>
no</td><td>
<code>renderer</code><br/>
<code>render</code><br/>
<code>type</code><br/>
<code>graph-type</code><br/>
</td></tr>
<tr><td><code>color</code></td><td>Sets the color (or gradient paint) for this series.</td><td>
no</td><td>
<code>paint</code><br/>
</td></tr>
<tr><td><code>timeperiod</code></td><td>Sets the time frequency of data points for this series, which in turn controls the "width" of each point for the series. Most relevant when using certain graphTypes (e.g., <code>bar</code>).</td><td>
no</td><td>
<code>time</code><br/>
<code>period</code><br/>
</td></tr>
<tr><td><code>yaxis</code></td><td>Specifies the Y (or <code>range</code>) axis that this series will bind to.  Valid values are <code>0 1 2 3</code>.  Defaults to <code>0</code>.</td><td>
no</td><td>
</td></tr>
<tr><td><code>visible</code></td><td><code>boolean</code> to toggle if this series is shown on the chart or not.</td><td>
no</td><td>
</td></tr>
<tr><td><code>linkExpression</code></td><td>Sets an expression which will be used to generate URL links for this series in an HTML image map of the chart.  The expression may contain arbitrarytext, and tokens that will be expanded at runtime.  The tokens can specifycolumns of data from the same row in a data set that generated each point ofthe series.  The series name may also be specified.  The tokens are surroundedby braces '{' and '}', and contain either the (1-based) index of a column, orelse the name of the series, denoted by <code>{series}</code>.For example:
<pre>
linkExpression="http://server/my-page?user={series}&date={1}&param={3}"
</pre></td><td>
no</td><td>
</td></tr>
<tr><td><code>datasource</code></td><td>The datasource ID that this series will pull data from.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>x</code></td><td>The (1-based) column index on the series' datasource for the X axis values of this series.  Defaults to <code>1</code></td><td>
no</td><td>
</td></tr>
<tr><td><code>y</code></td><td>The (1-based) column index on the series' datasource for the Y axis values of this series.  Defaults to <code>2</code></td><td>
no</td><td>
</td></tr>
<tr><td><code>z</code></td><td>The (1-based) column index on the series' datasource for the Z values of this series, if needed.  Note that only a few graph-type renderers (e.g., <code>bubble</code>) even use the Z value for anything.  Defaults to <code>3</code></td><td>
no</td><td>
</td></tr>
<tr><td><code>dynamicNameColumn</code></td><td>Specifies a (1-based) column index whose value(s) will determine the names of more than one series on this chart.  You will get one series created for each distinct value in the specified column.  As a sanity check, no more than 30 series will be created.</td><td>
no</td><td>
</td></tr>

</table>

</p>

<a name="series-function"></a><h2><code>series-function</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>name</code></td><td>The name of this series as it will appear on the chart.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>graphType</code></td><td>Specifies the <a href="charts.jsp#graph-types">graph type</a> for this series.  This will override the default graph type set for the entire chart, if any.</td><td>
no</td><td>
<code>renderer</code><br/>
<code>render</code><br/>
<code>type</code><br/>
<code>graph-type</code><br/>
</td></tr>
<tr><td><code>color</code></td><td>Sets the color (or gradient paint) for this series.</td><td>
no</td><td>
<code>paint</code><br/>
</td></tr>
<tr><td><code>timeperiod</code></td><td>Sets the time frequency of data points for this series, which in turn controls the "width" of each point for the series. Most relevant when using certain graphTypes (e.g., <code>bar</code>).</td><td>
no</td><td>
<code>time</code><br/>
<code>period</code><br/>
</td></tr>
<tr><td><code>yaxis</code></td><td>Specifies the Y (or <code>range</code>) axis that this series will bind to.  Valid values are <code>0 1 2 3</code>.  Defaults to <code>0</code>.</td><td>
no</td><td>
</td></tr>
<tr><td><code>visible</code></td><td><code>boolean</code> to toggle if this series is shown on the chart or not.</td><td>
no</td><td>
</td></tr>
<tr><td><code>linkExpression</code></td><td>Sets an expression which will be used to generate URL links for this series in an HTML image map of the chart.  The expression may contain arbitrarytext, and tokens that will be expanded at runtime.  The tokens can specifycolumns of data from the same row in a data set that generated each point ofthe series.  The series name may also be specified.  The tokens are surroundedby braces '{' and '}', and contain either the (1-based) index of a column, orelse the name of the series, denoted by <code>{series}</code>.For example:
<pre>
linkExpression="http://server/my-page?user={series}&date={1}&param={3}"
</pre></td><td>
no</td><td>
</td></tr>
<tr><td><code>series</code></td><td>The series name (ID) that this functional series will operate on.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>function</code></td><td>Specifies the function that will be applied to another series.  Valid values are: <br/><code>MVAVG<br/>
SCALE<br/>
INFLATION<br/>
TIMECHANGE<br/>
TIMEJOIN<br/>
</code></td><td>
yes</td><td>
</td></tr>
<tr><td><code>args</code></td><td>Sets the arguments for <code>function</code>.  In general, you probably want to use the appropriate function-specific tags, rather than specifying the arguments here.<br/>Args must be separated by vertical bars, '|'</td><td>
yes</td><td>
</td></tr>

</table>

</p>

<a name="marker"></a><h2><code>marker</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>name</code></td><td>name for this marker, as it will appear on the chart.</td><td>
yes</td><td>
</td></tr>
<tr><td><code>yaxis</code></td><td>The Y axis <code>[0-3]</code> this marker is aligned with.  Defaults to 0</td><td>
no</td><td>
</td></tr>
<tr><td><code>color</code></td><td>The color or gradient paint of this marker.  Shorthand for the <code>paint</code> property</td><td>
no</td><td>
</td></tr>
<tr><td><code>visible</code></td><td>boolean that toggles whether or not this marker is displayed.</td><td>
no</td><td>
</td></tr>
<tr><td><code>template</code></td><td>Sets the name of a template property set that should be applied to this marker.</td><td>
no</td><td>
</td></tr>
<tr><td><code>datasource</code></td><td>Datasource for interval/band marker values</td><td>
no</td><td>
</td></tr>
<tr><td><code>value</code></td><td>Specifies the numeric value(s) for this marker, along a numeric axis. A line marker will be drawn for a single value, multiple values will produce an interval (band) marker.  Multiple valuesmust be separated by a vertical bar '|'</td><td>
no</td><td>
<code>bandValues</code><br/>
</td></tr>
<tr><td><code>dateValues</code></td><td>Specifies the date value(s) for this marker, along a date/time axis.  Multiple values will produce an interval (band) marker. Multiple values must be separated by a vertical bar '|'.  The format of the dates must comply with one of <a href="data.jsp#date-formats">the valid date formats</a>for the <code>data</code> tag.</td><td>
no</td><td>
<code>date</code><br/>
</td></tr>
<tr><td><code>xaxis</code></td><td>boolean that specifies if this marker should be on the X (domain) axis</td><td>
no</td><td>
</td></tr>
<tr><td><code>template</code></td><td>Sets the name of a template property set that should be applied to this marker.</td><td>
no</td><td>
</td></tr>

</table>

</p>

<a name="data-table"></a><h2><code>data-table</code> tag</h2>
<p>


<table class="doc-table">
 <tr><th>Attribute</th><th>Description</th><th>Required?</th><th>Aliases</th></tr>
<tr><td><code>datasource</code></td><td>datasource ID to display as an HTML table</td><td>
yes</td><td>
</td></tr>
<tr><td><code>css</code></td><td>Sets the <code>class=...</code> CSS class of the generated table</td><td>
no</td><td>
<code>cssClass</code><br/>
</td></tr>
<tr><td><code>style</code></td><td>Sets the <code>style=...</code> CSS style attribute of the generated table</td><td>
no</td><td>
</td></tr>
<tr><td><code>offset</code></td><td>Specifies the number of rows to skip in the beginning of the data.  Defaults to 0</td><td>
no</td><td>
</td></tr>
<tr><td><code>length</code></td><td>Specifies the maximum number of rows to display.  Defaults to 25</td><td>
no</td><td>
</td></tr>

</table>

</p>

</div>
</body>
</html>
