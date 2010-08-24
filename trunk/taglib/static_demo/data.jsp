


<html>
<head>
<title>ChartMechanic Tag Library Demo</title>
<link href="demo.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="header">
 <ul><li><a href="index.jsp">Home</a></li>
<li id="selected">Data</li>
<li><a href="charts.jsp">Charts</a></li>
<li><a href="samples.jsp">Samples</a></li>
<li><a href="tag-reference.jsp">Tag Reference</a></li>
<li><a href="property-reference.jsp">Property Reference</a></li>
<li><a href="faq.jsp">FAQ</a></li>

</ul>
</div>


<div id="content">
<p>
 <ul>
  <li><a href="#tabular-data">Tabular Data</a></li>
  <li><a href="#data-types">Data Types</a></li>
  <li><a href="#type-inferencing">Type Inferencing</a></li>
  <li><a href="#number-formats">NUMBER Formats</a></li>
  <li><a href="#date-formats">DATE Formats</a></li>
  <li><a href="#example-csv">CSV Example</a></li>
  <li><a href="#example-sql">SQL Example</a></li>
 </ul>
</p>
<a name="tabular-data"></a><h2>Tabular Data</h2>
<p>

The <code>&lt;data&gt;</code> tag  specifies a <b>data source</b>, which provides a tablular (having columns and rows) source of values that can be represented graphically in a chart.  The raw data, i.e., the actual provider of the data,
can be one of the following formats:

<ul>
<li>CSV: comma separated values</li>
<li>SQL query</li>
</ul>
</p>
<p>
A later release of the tag library will support raw data from the following sources as well:

<ul>
<li>Microsoft Excel Spreadsheet</li>
<li>Google Docs Spreadsheet</li>
<li>HTML: tables from web pages</li>
</ul>

</p>

<a name="data-types"></a><h2>Data Types</h2>
<p>
the <code>&lt;data&gt;</code> tag understands data values in three basic types:
<ul>
<li><b>DATE</b> - Time-based values.  Many charts are time-related and time values are commonly used in the X-axis</li>
<li><b>NUMBER</b> -
Integer or decimal values.   Number typed values are most frequently used as the Y-axis values in charts
</li>
<li><b>TEXT</b> -
String or alphanumeric values. Text typed values are commonly used as categories in pie charts or bar charts.
</li>
</ul>

Each column of a data source has one of these three types.  For example, a table of monthly sales figures would be typed as follows:
</p>
<p>
<table class="text-right" style="width:350px;">
 <tr>
  <th>Month<br/><b>(DATE)</b></th>
  <th>Sales<br/><b>(NUMBER)</b></th>
  <th>Representative<br/><b>(TEXT)</b></th>
 </tr>
  <tr class="even"><td>May 2009</td><td>$500</td><td>Larry</td></tr>
 <tr><td>June 2009</td><td>$800</td><td>Moe</td></tr>
 <tr class="even"><td>July 2009</td><td>$1,300</td><td>Curly</td></tr>
 <tr><td>August 2009</td><td>$900</td><td>Shemp</td></tr>
 <tr class="even"><td>September 2009</td><td>$2,300</td><td>Joe</td></tr>
</table>
</p>
<a name="type-inferencing"></a><h2>Type Inferencing</h2>
<p>
Raw, tabular data must be converted to typed columns in order to be used by the <code>&lt;data&gt;</code>
and <code>&lt;chart&gt;</code> tags.  This is done by looking at each column, and inferring if the
column's type should be <b>TEXT</b> <b>NUMBER</b> or <b>DATE</b>.
</p>
<p>
 Any data values may be considered as TEXT, and this is the default column type.  Columns will be converted to either <b>NUMBER</b> or <b>DATE</b> if enough of their columns "look like" that type.  ChartMechanic tries to be lenient and pragmatic in parsing data.  However, it's possible that not all values from your data can always be understood as <b>NUMBER</b> or <b>DATE</b> values. When a <b>NUMBER</b> or <b>DATE</b> cannot be successfully parsed as such, that cell is treated as if it had been empty; that is, there is no value for that cell.  Such empty values will be omitted from any charts for that data as well.
</p>
<p>
Note that no type inferencing is needed when data is loaded from <b>SQL</b>:  The results from the database
are already typed, and the <code>&lt;data&gt;</code> tag simply maps JDBC types to one of our 3 types.  Inferencing occurs only for raw data sources whose type information is not already known.
</p>
<a name="number-formats"></a><h2>NUMBER Formats</h2>
<p>
Currency symbols, exponential notation, commas, and decimal points are valid as part of a number.  Extraneous alphabetical text, such as units, labels or other markers, generally invalidate a cell's consideration as a number:
</p>
<p>
<table class="text-right" style="width:350px;">
 <tr>
  <th>Example</th>
  <th>Valid Number?</th>
 </tr>
 <tr class="even"><td>12,345</td><td>yes</td></tr>
 <tr><td>1.23E3</td><td>yes</td></tr>
 <tr class="even"><td>$12,345</td><td>yes</td></tr>
 <tr><td>&euro;12,345.00</td><td>yes</td></tr>
 <tr class="even"><td>&yen;12,345</td><td>yes</td></tr>
 <tr><td>12345 dollars</td><td><b>no</b></td></tr>
 <tr class="even"><td>12345 (see footnote)</td><td><b>no</b></td></tr>
</table>

</p>
<a name="date-formats"></a><h2>DATE Formats</h2>
<p>
The current list of supported date formats is shown below:
</p>
<p>

<table class="doc-table" style="width:600px;">
<tr><th>Format</th><th>Example</th><th>Accepts time format?</th></tr>
<tr class='even'><td>MMM d, yyyy</td><td>Dec 5, 2006</td><td>yes</td></tr>
<tr class='odd'><td>MM/dd/yy</td><td>12/05/06</td><td>yes</td></tr>
<tr class='even'><td>MM-dd-yy</td><td>12-05-06</td><td>yes</td></tr>
<tr class='odd'><td>yyyy-MM-dd</td><td>2006-12-05</td><td>yes</td></tr>
<tr class='even'><td>yyyyMMdd</td><td>20061205</td><td>yes</td></tr>
<tr class='odd'><td>yyyy</td><td>2006</td><td>no</td></tr>
<tr class='even'><td>yyyy.'0'</td><td>2006.0</td><td>no</td></tr>
<tr class='odd'><td>'FY' yy</td><td>FY 06</td><td>no</td></tr>
<tr class='even'><td>dd-MMM-yy</td><td>05-Dec-06</td><td>yes</td></tr>
<tr class='odd'><td>dd MMM yy</td><td>05 Dec 06</td><td>yes</td></tr>
<tr class='even'><td>dd-MMM</td><td>05-Dec</td><td>no</td></tr>
<tr class='odd'><td>EE MMM dd yy</td><td>Tue Dec 05 06</td><td>yes</td></tr>
<tr class='even'><td>EE MMM dd HH:mm:ss yy</td><td>Tue Dec 05 12:16:45 06</td><td>no</td></tr>
<tr class='odd'><td>MMM yy</td><td>Dec 06</td><td>no</td></tr>
<tr class='even'><td>MMM-yy</td><td>Dec-06</td><td>no</td></tr>
<tr class='odd'><td>MMM yyyy</td><td>Dec 2006</td><td>no</td></tr>
<tr class='even'><td>MMM-yyyy</td><td>Dec-2006</td><td>no</td></tr>
<tr class='odd'><td>MM/yy</td><td>12/06</td><td>no</td></tr>
<tr class='even'><td>MM/yyyy</td><td>12/2006</td><td>no</td></tr>
<tr class='odd'><td>EE MMM dd HH:mm:ss z yyyy</td><td>Tue Dec 05 12:16:45 PST 2006</td><td>yes</td></tr>
<tr class='even'><td>dd MMM</td><td>05 Dec</td><td>yes</td></tr>
<tr class='odd'><td>yyyy/MM</td><td>2006/12</td><td>no</td></tr>
<tr class='even'><td>yyyy-MM</td><td>2006-12</td><td>no</td></tr>
<tr class='odd'><td>yyyy-MMM</td><td>2006-Dec</td><td>no</td></tr>
<tr class='even'><td>yyyy MMM</td><td>2006 Dec</td><td>no</td></tr>
<tr class='odd'><td>yyyy-MMM'.'</td><td>2006-Dec.</td><td>no</td></tr>
<tr class='even'><td>yyyy-MMM'.' d</td><td>2006-Dec. 5</td><td>no</td></tr>
<tr class='odd'><td>yyyy.MM</td><td>2006.12</td><td>no</td></tr>
<tr class='even'><td>yyyy-MMM d</td><td>2006-Dec 5</td><td>no</td></tr>
<tr class='odd'><td>dd MMM yyyy</td><td>05 Dec 2006</td><td>no</td></tr>
<tr class='even'><td>yyyy-MM-dd'T'HH:mm:ss.SSS</td><td>2006-12-05T12:16:45.680</td><td>no</td></tr>
<tr class='odd'><td>yyyy-MM-dd'T'HH:mm:ss.SSSZ</td><td>2006-12-05T12:16:45.680-0800</td><td>no</td></tr>
<tr class='even'><td>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</td><td>2006-12-05T12:16:45.680Z</td><td>no</td></tr>
<tr class='odd'><td>quarter</td><td>2006-Q4</td><td>no</td></tr>
<tr class='even'><td>MMM. dd, yyyy</td><td>Dec. 05, 2006</td><td>no</td></tr>
<tr class='odd'><td>yyyyMMM</td><td>2006Dec</td><td>no</td></tr>
<tr class='even'><td>yyyy'M'MM</td><td>2006M12</td><td>no</td></tr>
<tr class='odd'><td>dd/MM/yy</td><td>05/12/06</td><td>no</td></tr>
<tr class='even'><td>HH:mm:ss</td><td>12:16:45</td><td>no</td></tr>

</table>
</p>
<a name="example-csv"></a><h2>&lt;data&gt; Tag Example, CSV</h2>
<p>
<pre>
&lt;%-- CSV data of the 3 Stooges sales organization --%&gt;
&lt;%-- The first row of column names is optional, and not part of the "data"  --%&gt;
&lt;c:data id=&quot;stooge sales&quot; type=&quot;CSV&quot;&gt;
 Date,#Sales,Quota,Actual
 05/2010,7,$800,$500
 06/2010,2,$800,$800
 07/2010,8,$1000,$1300
 08/2010,9,$1000,$900
 09/2010,12,$1000,$2400
&lt;/c:data&gt;
</pre>
</p>

<a name="example-sql"></a><h2>&lt;data&gt; Tag Example, SQL</h2>
<p>
<pre>
&lt;%-- data from an SQL query on a JDBC data source --%&gt;
&lt;c:data id="weblog summary" type="SQL" jndiName="jdbc/DemoDS"&gt;
 SELECT rq_date,hits,bandwidth FROM web_log
 WHERE rq_date > '2010-01-01'
 ORDER BY rq_date ASC
&lt;/c:data&gt;
</pre>
</div> 
</body>
</html>
