<d:header/>
<div id="content">
<p>
 <ul>
  <li><a href="#anatomy">Anatomy of a Chart</a></li>
  <li><a href="#properties">Properties</a></li>
  <li><a href="#property-types">Property Types</a></li>
  <li><a href="#plot-types">Plot Types</a></li>
  <li><a href="#series">Series</a></li>
  <li><a href="#graph-types">Graph Types</a></li>
  <li><a href="#series-functions">Series Functions</a></li>
  <li><a href="#markers">Markers</a></li>
  <li><a href="#marker-functions">Marker Functions</a></li>
  <li><a href="#templates">Templates</a></li>
 </ul>
</p>
<a name="anatomy"></a><h2>Anatomy of a Chart</h2>
<p>
The ChartMechanic tag library lets you customize many aspects of the charts it generates. Charts are organized into various pieces, identified in the diagram below:
<img src="images/anatomy-1.png"/>
<ul>
<li><b>Title</b> - a single line of text at the top of the chart</li>

<li><b>Description</b> - a multi line block of text describing a chart</li>
<li><b>X Axis</b> - also called the <b>domain</b> axis.  The line along the plot expressing the first column of a series.</li>
<li><b>Y Axis (0 - 3)</b> - also called a <b>range</b> axis, which expresses the second column of one or more series.  A chart may have between 1 and 4 Y axes.</li>

<li><b>Plot</b> - the rendering area for data values, where series are drawn.  The plot area is contained by the axes.</li>
<li><b>Series</b> - the graphical representation of a data set having both <b>X</b>, <b>Y</b>,
 (and optionally <b>Z</b>) values.  You can represent a series using various lines, shapes
 and bars as determined by the <a href="#graph-types">graph type</a> for the series. In the
 anatomy diagram above, the green bars, and the red and blue lines, represent series with
 different series.</li>

<li><b>Markers</b> - a horizontal or vertical line across the plot area, tied to a point on an X or Y axis, with an optional descriptive label.  You can specify a marker's value as a specific NUMBER or DATE value, or its value can be from a <a href="#marker-functions">FUNCTION</a> of a series.
</li>
<li><b>Legend</b> - a block showing a color and shape coded key for each series in the chart.</li>

<li><b>Chart</b> - the background, or bottom layer of a chart.  Basically, everything that is behind the other chart components.</li>
</ul>

</p>

<a name="properties"></a><h2>Chart Properties</h2>
<p>
The attributes of a chart are expressed with a collection of name/value pairs, known as the chart's <b>properties</b>.  These name/value pairs are converted into <code>setXXX(...)</code> calls in the <a href="http://www.jfree.org/jfreechart/api/javadoc/index.html">JFreeChart</a> API.  The prefix of a property name indicate which part of the chart the property is setting.  For example, the following <code>&lt;c:props&gt;</code> tag:
</p>
<p>
<code><pre>
&lt;c:props&gt;
chart.backgroundPaint=white
plot.outlineVisible=false
title.paint=black
title.font=Arial-bold-16
&lt;/c:props&gt;
</code></pre>
</p>
<p>
Will have a white background, a black title with 16pt Arial bold font, and no outline visible around the plot area.  If a set of properties share the same prefix, the prefix may be specified in the tag, and then won't need to be repeated for each name/value pair:
</p>
<p>
<code><pre>
&lt;c:props prefix="title"&gt;
paint=black
font=Arial-bold-16
&lt;/c:props&gt;
</code></pre>
</p>
<p>
Is equivalent to the previous tag usage, for the chart title.
</p>
<a name="property-types"></a><h2>Property Types</h2>
<p>
Properties are converted into <code>set(...) </code> calls in the <a href="http://www.jfree.org/jfreechart/api/javadoc/index.html">JFreeChart API</a>.  This requires certain conventions for the format of property values to b e converted from strings.  Basic types (numeric, boolean, etc) are simply represted as their literal values.  Other supported types from the <code>java.awt.*</code> and JFreeChart API are represented as follows:
</p>
<p>
<table class="doc-table">
<tr><th>Type</th><th>Encoding</th><th>Example(s)</th></tr>
<tr><td><b>java.awt.Image</b></td><td>publicly accessible URL to an image<br/>Resource path of an image within your webapp</td>
 <td><code>http://example.org/images/my-background.jpg<br/>/resources/images/background.png</code></td></tr>

<tr><td><b>java.awt.Font</b></td>
    <td>standard <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/awt/Font.html#decode(java.lang.String)">java font specification string</a>:
    <i>fontname-style-pointsize</i></td>
    <td><code>Arial-BOLD-12</code></td></tr>
<tr><td><b>java.awt.Paint<br/>java.awt.Color</b></td>
 <td>Either a hex RGB color for a solid color, or a string specifying a color gradient.  A gradient paint
     specifies, as comma-separated values, two colors, four numbers that denote a rectangle, and a boolean.
     The two colors are the colors that the paint will shift between, and the rectangle denotes the direction
     and speed of the gradient shift.  The first two numbers specify the XY coordinates for the upper left
     of the rectangle, and the second two numbers specify the width and height of the rectangle.  The boolean
     specifies whether or not the gradient should be cyclical (repeating).
</td>
 <td><code>00ff00</code> - light green solid color<br/>

     <code>gradient:d0d0d0,ffffff,0,0,200,200,true</code> - a gradient paint, shifting diagonally from light gray to white,
           in a 200 x 200 square, cyclically
 </td></tr>
<tr><td><b>java.awt.Stroke</b></td>
 <td>A Stroke describes the width and style of a line, which can be solid or have some dashed pattern.  It is
     encoded as <code>line=&lt;width&gt;|dash=&lt;dash-style&gt;</code>.  The dash style is a number, 0-7 inclusive,
     where 0 specifies a solid line.  As a shorthand for a solid line, only the width number of the stroke width may be used.</td>
 <td>

 <code>line=0.5|dash=0</code> - A thin solid line<br/>
 <code>line=2.0|dash=4</code> - A thicker solid line</br>
 <code>1.0</code> - solid line, shorthand width
</td>
</tr>
<tr><td><b>java.awt.Shape</b></td>

 <td>Shapes are used for decorations on renderers, and on other locations on a chart.  Valid
     values are <code>square circle diamond triangle-up triangle-down</code></td>
  <td><i>see valid values</i></td>
</tr>
<tr><td><b>PlotOrientation</b></td>
 <td>Specifies if a plot should be laid out horizontally, with the domain axis horizontal (the default), or vertical.</td>
 <td><code>HORIZONTAL VERTICAL</code></td></tr>
<tr><td><b>AxisLocation</b></td>

 <td>Sets the location of an axis relative to a plot.</td>
 <td><code>BOTTOM_OR_LEFT BOTTOM_OR_RIGHT TOP_OR_LEFT TOP_OR_RIGHT</code></td>
</tr>
<tr><td><b>java.text.DateFormat</b></td><td>Any format String supported by <code>SimpleDateFormat</code></td>
    <td><code>MMM-yy</code></td></tr>
<tr><td><b>java.text.NumberFormat</b></td><td>Any format String supported by <code>DecimalFormat</code></td>
    <td><code>###,###</code></td></tr>
<tr><td><b>RectangleAnchor</b></td><td>JFreeChart block alignment constants</td><td><code>
CENTER
TOP
TOP_LEFT
TOP_RIGHT
BOTTOM
BOTTOM_LEFT
BOTTOM_RIGHT
LEFT
RIGHT
</code></td></tr>
<tr><td><b>TextAnchor</b></td><td>JFreeChart text alignment constants</td><td><code>
TOP_LEFT
TOP_CENTER
TOP_RIGHT
HALF_ASCENT_LEFT
HALF_ASCENT_CENTER
HALF_ASCENT_RIGHT
CENTER_LEFT
CENTER
CENTER_RIGHT
BASELINE_LEFT
BASELINE_CENTER
BASELINE_RIGHT
BOTTOM_LEFT
BOTTOM_CENTER
BOTTOM_RIGHT</code></td></tr>
<tr><td><b>RectangleInsets</b></td><td>JFreeChart inset rectangle, format: <code>top,left,bottom,right{,RELATIVE|ABSOLUTE}</td><td><code>4.0,4.0,4.0,2.0,RELATIVE</code></td></tr>
<tr><td><b>HorizontalAlignment</b></td><td>JFreeChart horizontal alignment constant</td><td>
<code>CENTER LEFT RIGHT</code>
</td></tr>
<tr><td><b>VerticalAlignment</b></td><td>JFreeChart vertical alignment constant</td><td>
<code>BOTTOM CENTER TOP</code>
</td></tr>
<tr><td><b>NumberTickUnit</b></td><td>The size/spacing for a JFreeChart
 <a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/NumberTickUnit.html">
NumberTickUnit</a></td><td><code>any positive number</code></td></tr>
<tr><td><b>XYBarPainter<br/>CategoryBarPainter</b></td><td>JFreeChart bar painting styles</td><td>
<code>SOLID GRADIENT</code>
</td></tr>
<tr><td><b>DateTickUnit</b></td>
 <td>encodes a JFreeChart <a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/DateTickUnit.html">DateTickUnit</a><br/>
<code>{unit_count}-{unit-type}</code>
</td>
 <td><code>4-DAY</code> &nbsp;&nbsp;&nbsp;draw a tick every 4 days<br/>
<code>12-HOUR</code> &nbsp;&nbsp;draw a tick every 12 hours
</td></tr>
<tr><td><b>DateTickMarkPosition</b></td><td>Position of a date tick mark within its cell</td>
<td><code>START MIDDLE END</code></td></tr>
<tr><td><b>RectangleEdge</b></td><td>Layout constants to specify the edge of a rectangle</td>
<td><code>BOTTOM LEFT RIGHT TOP</code></td></tr>
<tr><td><b>CategoryLabelPositions</b></td><td>Orientation of the labels on a category axis</td>
<td><code>STANDARD UP_45 UP_90 DOWN_45 DOWN_90</code></td></tr>
<!--
<tr><td><b></b></td><td></td><td></td></tr>
-->
</table>
</p>

<a name="plot-types"></a><h2>Plot Types</h2>
<p>
The plot types supported by the ChartMechanic tag library include:
<ul>
<li><b>Time Series</b>

Time series plots are like XY plots except the X axis is a Date value.
</li>

<li><b>XY</b>
XY plots graph numeric values along a two-dimensional coordinate system.
</li>
<li><b>Category</b>

Category plots show values based on Text-typed
values.  A common example is a bar chart where each bar corresponds to
a string category and the height of the bar corresponds to the numeric
value for that category.
</li> 
<li><b>Gantt</b>
Gantt charts show duration of various categories against time on the X-axis.
</li>
<li><b>Pie</b>

Pie charts are a special kind of category chart that displays each
category as a pie slice.  In this way, the relative values of the
categories can be easily compared.  Pie charts can also be show in 3D
as well as ring plots.  Ring plots are like pie charts except that are
missing the middle section
</li>
<li><b>Histogram</b>

Histogram plots show a frequency distribution of values along a spectrum of possible values
</li>
</ul>
</p>
<p>
<table class="doc-table">
<tr><th colspan="3">Plot &amp; Data Compatibility</th></tr>
<tr><th>Plot Type</th><th>X Axis Type</th><th>Y Axis Type</th></tr>
<tr><td>timeseries</td><td>DATE</td><td>NUMBER</td></tr>
<tr><td>xy</td><td>NUMBER</td><td>NUMBER</td></tr>
<tr><td>category</td><td><i>ANY</i></td><td>NUMBER</td></tr>
<tr><td>pie</td><td><i>ANY</i></td><td>NUMBER</td></tr>
<tr><td>pie3d</td><td><i>ANY</i></td><td>NUMBER</td></tr>
<tr><td>histogram</td><td><i>N/A</i></td><td>NUMBER</td></tr>
<tr><td>gantt</td><td><i>ANY</i></td><td>DATE</td></tr>
</table>
</p>

<a name="series"></a><h2>Series</h2>
<p>
A <code>series</code> is the graphical representation of a data set having both <b>X</b>, <b>Y</b>,
 (and optionally <b>Z</b>) values.  More simply, each line, or set of bars on a chart, are a series.
</p>
<p>
The <code>X, Y (,Z)</code> values for the series typically
 come from <a href="data.jsp">columns on the data source</a> for the series, but the series
 may also be derived by applying a <a href="#series-functions">SERIES FUNCTION</a> to another
 series.
</p>
<p>
Every series is tied to the X axis for a chart/plot, and to one of the 4 Y axes. 
The different series on a chart are rendered using one of the available <a href="#graph-types">
graph types</a> for the <a href="#plot-types">plot type</a> of the chart.
</p>

<p>
See the <a href="tag-reference.jsp#series"> series tag reference</a> for how to set up a series.
</p>

<a name="graph-types"></a><h2>Graph Types</h2>
<p>
<b>Graph types for Time Series, XY, and Histogram Charts</b>
</p>
<p>
<table class="doc-table">
<tr><th>Graph Type</th><th>Description</th></tr>
<tr><td>Area</td><td>render a series as filled Area</td></tr>
<tr><td>Bar</td><td>horizontal/vertical rectagular Bars</td></tr>
<tr><td>Bubble</td><td>circular, filled "bubble", with the diameter determined by a Z column</td></tr>

<tr><td>Candlestick</td><td>Candlestick (Box and Whisker) shows Open/High/Low/Close values, usually for a tradeable financial product.  An optional fifth column renders trading volume.</td></tr>
<tr><td>Clustered Bar</td><td>Bar rendering, where multiple series are clustered next to each other along the X axis.</td></tr>
<tr><td>Difference</td><td>renders a difference between two series, with different colors for positive and negative differences</td></tr>
<tr><td>Dot</td><td>draws values as Dots, a.k.a. a scatter plot</td></tr>
<tr><td>Line</td><td>render a series as simple line</td></tr>
<tr><td>Line 3D</td><td>draws a Line, with an optional shadow behind it</td></tr>

<tr><td>Line And Shape</td><td>render a series as line with a shape at value points.  Shape can be filled/outlined.</td></tr>
<tr><td>Spline</td><td>render a series as a line, with spline iterpolation between points.</td></tr>
<tr><td>Stacked Area</td><td>Area rendering, with multiple series stacked on top of each other</td></tr>
<tr><td>Stacked Bar</td><td>renders values as rectangular bars, with multiple series stacked on top of each other</td></tr>
<tr><td>Step</td><td>line rendering, with discrete vertical steps between values</td></tr>
<tr><td>Step Area</td><td>Step rendering, with the area below the line filled</td></tr>

</table>
</p>
<p>
<b>Graph types for Category plots</b>
</p>
<p>
<table class="doc-table">
<tr><th>Graph Type</th><th>Description</th></tr>
<tr><td>Area</td><td>render a series as a filled Area</td></tr>

<tr><td>Bar</td><td>horizontal/vertical rectagular Bars</td></tr>
<tr><td>Bar 3D</td><td>Bar rendering, with 3D depth effect</td></tr>
<tr><td>Gantt</td><td>Applicable only for Gantt chart type</td></tr>
<tr><td>Grouped Stacked Bar</td><td>stacked bar rendering, with clustering by group</td></tr>
<tr><td>Layered Bar</td><td>render a series as LayeredBar</td></tr>
<tr><td>Level</td><td>draws a single line at the Y value</td></tr>

<tr><td>Line 3D</td><td>draws a Line, with an optional shadow behind it</td></tr>
<tr><td>Line And Shape</td><td>render a series as line with a shape at value points.  Shape can be filled/outlined.</td></tr>
<tr><td>Min Max</td><td>similar to Level rendering, with values at the minimum and maximum</td></tr>
<tr><td>Stacked Area</td><td>Area rendering, with multiple series stacked on top of each other</td></tr>
<tr><td>Stacked Bar</td><td>renders values as rectangular bars, with multiple series stacked on top of each other</td></tr>
<tr><td>Stacked Bar 3D</td><td>stacked Bar rendering, with 3D depth effect</td></tr>

<tr><td>Step</td><td>line rendering, with discrete vertical steps between values</td></tr>
<tr><td>Waterfall</td><td>renders as Waterfall effect, with sorted series values flowing into the next value</td></tr>

</table>

</p>

<a name="series-functions"></a><h2>Series Functions</h2>
<p>
The following functions are available to generate a charting series that is derived from another series:
</p>
<p>
<d:doc-functions reducers="false"/>  
</p>
<p>
All of the series function operate on another series (in the case of TIMEJOIN, two other series).
For example, given a daily time series <code>"stock price"</code>, the
<code>series-function</code> tag:
</p>
<p>
<code>
&lt;c:series-function series="stock price" function="MVAVG" args="20" name="MA-20"/&gt;
</code>
</p>
<p>
will generate the 20 day moving average of the stock price series.
</p>
<a name="markers"></a><h2>Markers</h2>
<p>
A marker is a horizontal or vertical line across the plot area, tied to a point on an X or Y axis, with an optional descriptive label. You can specify a marker's value as a specific NUMBER or DATE value, or its value can be from a <a href="#marker-functions">FUNCTION</a> of a series. 
</p>

<a name="marker-functions"></a><h2>Marker Functions</h2>
<p>
<d:doc-functions reducers="true"/>  
</p>

<a name="templates"></a><h2>Templates</h2>
<p>
Frequently, one wants to have a common look and feel for all the charts in an application.  It grows cumbersome to repeat the same properties over and over again for each usage of the <code>&lt;chart&gt;</code> tag.
</p>
<p>
A template can be set up instead to declare all of the common properties once, in one place, using the <code>&lt;props&gt;</code> tag.  A tag like:

</p>
<p>
<code><pre>
&lt;c:props template="default"&gt;
chart.backgroundPaint=white
plot.outlineVisible=false
title.paint=black
title.font=Arial-bold-16
&lt;/c:props&gt;
</code></pre>
</p>
<p>
will store the specified properties in a template called "default".  The "default" template will be applied to all charts; other template names must be referenced explicitly by each tag.
</p>
<p>
See the <a href="tag-reference.jsp#props">props tag reference</a> for more on setting up and using templates.
</p>
</div>
<d:footer/>
