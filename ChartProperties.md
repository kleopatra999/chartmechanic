


# chart #
<p>A chart class implemented using the Java 2D APIs.  The current version<br>
<blockquote>supports bar charts, line charts, pie charts and xy plots (including time<br>
series data).<br>
<br /><br />
JFreeChart coordinates several objects to achieve its aim of being able to<br>
draw a chart on a Java 2D graphics device: a list of {@link Title} objects<br>
(which often includes the chart's legend), a {@link Plot} and a<br>
{@link org.jfree.data.general.Dataset} (the plot in turn manages a<br>
domain axis and a range axis).<br>
<br /><br />
You should use a {@link ChartPanel} to display a chart in a GUI.<br>
<br /><br />
The {@link ChartFactory} class contains static methods for creating<br>
'ready-made' charts.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/JFreeChart.html'>org.jfree.chart.JFreeChart</a></b></td></tr>
<tr><td>antiAlias</td><td>boolean</td><td>Sets a flag that indicates whether or not anti-aliasing is used when the<br>
chart is drawn.<br>
<br /><br />
Anti-aliasing usually improves the appearance of charts, but is slower.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the chart and sends a<br>
{@link ChartChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the background alignment.  Alignment options are defined by the<br>
{@link org.jfree.ui.Align} class.</td><td><pre><code>15</code></pre></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha-transparency for the chart's background image.<br>
Registered listeners are notified that the chart has been changed.</td><td><pre><code>0.5</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the paint used to fill the chart background and sends a<br>
{@link ChartChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>borderPaint</td><td>Paint</td><td>Sets the paint used to draw the chart border (if visible).</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>borderStroke</td><td>Stroke</td><td>Sets the stroke used to draw the chart border (if visible).</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>borderVisible</td><td>boolean</td><td>Sets a flag that controls whether or not a border is drawn around the<br>
outside of the chart.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive<br>
{@link ChartChangeEvent} notifications.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding between the chart border and the chart drawing area,<br>
and sends a {@link ChartChangeEvent} to all registered listeners.</td><td><pre><code>0.0,0.0,0.0,0.0</code></pre></td></tr>
</table>
<h1>title</h1>
<p>A chart title that displays a text string with automatic wrapping as<br>
required.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/title/TextTitle.html'>org.jfree.chart.title.TextTitle</a></b></td></tr>
<tr><td>URLText</td><td>String</td><td>Sets the URL text to the specified text and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background paint and sends a {@link TitleChangeEvent} to all<br>
registered listeners.  If you set this attribute to <pre><code>null</code></pre>,<br>
no background is painted (which makes the title background transparent).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>expandToFitSpace</td><td>boolean</td><td>Sets the flag that controls whether the title expands to fit the<br>
available space, and sends a {@link TitleChangeEvent} to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>font</td><td>Font</td><td>Sets the font used to display the title string.  Registered listeners<br>
are notified that the title has been modified.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>maximumLinesToDisplay</td><td>int</td><td>Sets the maximum number of lines to display and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>2147483647</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint used to display the title string.  Registered listeners<br>
are notified that the title has been modified.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>text</td><td>String</td><td>Sets the title to the specified text and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>textAlignment</td><td>HorizontalAlignment</td><td>Sets the text alignment and sends a {@link TitleChangeEvent} to<br>
all registered listeners.</td><td><pre><code>CENTER</code></pre></td></tr>
<tr><td>toolTipText</td><td>String</td><td>Sets the tool tip text to the specified text and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>horizontalAlignment</td><td>HorizontalAlignment</td><td>Sets the horizontal alignment for the title and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>CENTER</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets the flag that indicates whether or not the notification mechanism<br>
is enabled.  There are certain situations (such as cloning) where you<br>
want to turn notification off temporarily.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>position</td><td>RectangleEdge</td><td>Sets the position for the title and sends a {@link TitleChangeEvent} to<br>
all registered listeners.</td><td><pre><code>TOP</code></pre></td></tr>
<tr><td>verticalAlignment</td><td>VerticalAlignment</td><td>Sets the vertical alignment for the title, and notifies any registered<br>
listeners of the change.</td><td><pre><code>CENTER</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the title should be drawn, and<br>
sends a {@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>ID</td><td>String</td><td>Sets the id for the block.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>height</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>margin</td><td>RectangleInsets</td><td>Sets the margin (use {@link RectangleInsets#ZERO_INSETS} for no<br>
padding).</td><td><pre><code>0.0,0.0,0.0,0.0</code></pre></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding (use {@link RectangleInsets#ZERO_INSETS} for no<br>
padding).</td><td><pre><code>1.0,1.0,1.0,1.0</code></pre></td></tr>
<tr><td>width</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><pre><code>0.0</code></pre></td></tr>
</table>
<h1>legend</h1>
<p>A chart title that displays a legend for the data in the chart.<br>
<br /><br />
The title can be populated with legend items manually, or you can assign a<br>
reference to the plot, in which case the legend items will be automatically<br>
created to match the dataset(s).</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/title/LegendTitle.html'>org.jfree.chart.title.LegendTitle</a></b></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background paint for the legend and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>itemFont</td><td>Font</td><td>Sets the item font and sends a {@link TitleChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>itemLabelPadding</td><td>RectangleInsets</td><td>Sets the padding used for the item labels in the legend.</td><td><pre><code>2.0,2.0,2.0,2.0</code></pre></td></tr>
<tr><td>itemPaint</td><td>Paint</td><td>Sets the item paint.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>legendItemGraphicAnchor</td><td>RectangleAnchor</td><td>Sets the anchor point used for the graphic in each legend item.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>legendItemGraphicEdge</td><td>RectangleEdge</td><td>Sets the location of the shape within each legend item.</td><td><pre><code>LEFT</code></pre></td></tr>
<tr><td>legendItemGraphicLocation</td><td>RectangleAnchor</td><td>Sets the legend item graphic location.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>legendItemGraphicPadding</td><td>RectangleInsets</td><td>Sets the padding that will be applied to each item graphic in the<br>
legend and sends a {@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>2.0,2.0,2.0,2.0</code></pre></td></tr>
<tr><td>horizontalAlignment</td><td>HorizontalAlignment</td><td>Sets the horizontal alignment for the title and sends a<br>
{@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>CENTER</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets the flag that indicates whether or not the notification mechanism<br>
is enabled.  There are certain situations (such as cloning) where you<br>
want to turn notification off temporarily.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>position</td><td>RectangleEdge</td><td>Sets the position for the title and sends a {@link TitleChangeEvent} to<br>
all registered listeners.</td><td><pre><code>BOTTOM</code></pre></td></tr>
<tr><td>verticalAlignment</td><td>VerticalAlignment</td><td>Sets the vertical alignment for the title, and notifies any registered<br>
listeners of the change.</td><td><pre><code>CENTER</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the title should be drawn, and<br>
sends a {@link TitleChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>ID</td><td>String</td><td>Sets the id for the block.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>height</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>margin</td><td>RectangleInsets</td><td>Sets the margin (use {@link RectangleInsets#ZERO_INSETS} for no<br>
padding).</td><td><pre><code>1.0,1.0,1.0,1.0</code></pre></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding (use {@link RectangleInsets#ZERO_INSETS} for no<br>
padding).</td><td><pre><code>1.0,1.0,1.0,1.0</code></pre></td></tr>
<tr><td>width</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><pre><code>0.0</code></pre></td></tr>
</table>
<h1>marker</h1>
<p>The base class for markers that can be added to plots to highlight a value<br>
or range of values.<br>
<br /><br />
An event notification mechanism was added to this class in JFreeChart<br>
version 1.0.3.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/Marker.html'>org.jfree.chart.plot.Marker</a></b></td></tr>
<tr><td>alpha</td><td>float</td><td>Sets the alpha transparency that should be used when drawing the<br>
marker, and sends a {@link MarkerChangeEvent} to all registered<br>
listeners.  The alpha transparency is a value in the range 0.0f<br>
(completely transparent) to 1.0f (completely opaque).</td><td><pre><code>0.8</code></pre></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label (if <pre><code>null</code></pre> no label is displayed) and sends a<br>
{@link MarkerChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelAnchor</td><td>RectangleAnchor</td><td>Sets the label anchor and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.  The anchor defines the position of the label<br>
anchor, relative to the bounds of the marker.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the label font and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.</td><td><pre><code>SansSerif-9</code></pre></td></tr>
<tr><td>labelOffset</td><td>RectangleInsets</td><td>Sets the label offset and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.</td><td><pre><code>3.0,3.0,3.0,3.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the label paint and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelTextAnchor</td><td>TextAnchor</td><td>Sets the label text anchor and sends a {@link MarkerChangeEvent} to<br>
all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke and sends a {@link MarkerChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint and sends a {@link MarkerChangeEvent} to all registered<br>
listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke and sends a {@link MarkerChangeEvent} to all registered<br>
listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
</table>
<h1>plot</h1>
<h2>PiePlot3D</h2>
<p>A plot that displays data in the form of a 3D pie chart, using data from<br>
any class that implements the {@link PieDataset} interface.<br>
<br /><br />
Although this class extends {@link PiePlot}, it does not currently support<br>
exploded sections.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/PiePlot3D.html'>org.jfree.chart.plot.PiePlot3D</a></b></td></tr>
<tr><td>darkerSides</td><td>boolean</td><td>Sets a flag that controls whether or not the sides of the pie chart<br>
are rendered using a darker colour, and sends a {@link PlotChangeEvent}<br>
to all registered listeners.  This is only applied if the<br>
section colour is an instance of {@link java.awt.Color}.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>depthFactor</td><td>double</td><td>Sets the pie depth as a percentage of the height of the plot area, and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.12</code></pre></td></tr>
<tr><td>autoPopulateSectionOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline paint is<br>
auto-populated by the {@link #lookupSectionOutlinePaint(Comparable)}<br>
method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSectionOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline stroke is<br>
auto-populated by the {@link #lookupSectionOutlineStroke(Comparable)}<br>
method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSectionPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section paint is<br>
auto-populated by the {@link #lookupSectionPaint(Comparable)} method,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSectionOutlinePaint</td><td>Paint</td><td>Sets the base section paint.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseSectionOutlineStroke</td><td>Stroke</td><td>Sets the base section stroke.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>baseSectionPaint</td><td>Paint</td><td>Sets the base section paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>circular</td><td>boolean</td><td>A flag indicating whether the pie chart is circular, or stretched into<br>
an elliptical shape.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>ignoreNullValues</td><td>boolean</td><td>Sets a flag that controls whether <pre><code>null</code></pre> values are ignored,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.  At<br>
present, this only affects whether or not the key is presented in the<br>
legend.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>ignoreZeroValues</td><td>boolean</td><td>Sets a flag that controls whether zero values are ignored,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.  This<br>
only affects whether or not a label appears for the non-visible<br>
pie section.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>interiorGap</td><td>double</td><td>Sets the interior gap and sends a {@link PlotChangeEvent} to all<br>
registered listeners.  This controls the space between the edges of the<br>
pie plot and the plot area itself (the region where the section labels<br>
appear).</td><td><pre><code>0.08</code></pre></td></tr>
<tr><td>labelBackgroundPaint</td><td>Paint</td><td>Sets the section label background paint and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffc0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the section label font and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>labelGap</td><td>double</td><td>Sets the gap between the edge of the pie and the labels (expressed as a<br>
percentage of the plot width) and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.025</code></pre></td></tr>
<tr><td>labelLinkMargin</td><td>double</td><td>Sets the link margin and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.025</code></pre></td></tr>
<tr><td>labelLinkPaint</td><td>Paint</td><td>Sets the paint used for the lines that connect pie sections to their<br>
corresponding labels, and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelLinkStroke</td><td>Stroke</td><td>Sets the link stroke and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>labelLinksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not label linking lines are<br>
visible and sends a {@link PlotChangeEvent} to all registered listeners.<br>
Please take care when hiding the linking lines - depending on the data<br>
values, the labels can be displayed some distance away from the<br>
corresponding pie section.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>labelOutlinePaint</td><td>Paint</td><td>Sets the section label outline paint and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelOutlineStroke</td><td>Stroke</td><td>Sets the section label outline stroke and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>labelPadding</td><td>RectangleInsets</td><td>Sets the padding between each label and its outline and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>2.0,2.0,2.0,2.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the section label paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelShadowPaint</td><td>Paint</td><td>Sets the section label shadow paint and sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#979797</code></pre></td></tr>
<tr><td>legendItemShape</td><td>Shape</td><td>Sets the shape used for legend items and sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>maximumLabelWidth</td><td>double</td><td>Sets the maximum label width as a percentage of the plot width and sends<br>
a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.14</code></pre></td></tr>
<tr><td>minimumArcAngleToDraw</td><td>double</td><td>Sets the minimum arc angle that will be drawn.  Pie sections for an<br>
angle smaller than this are not drawn, to avoid a JDK bug.<br>
</td>
<td><pre><code>1.0E-5</code></pre></td></tr>
<tr><td>pieIndex</td><td>int</td><td>Sets the pie index (this is used by the {@link MultiplePiePlot} class to<br>
track subplots).</td><td><pre><code>0</code></pre></td></tr>
<tr><td>sectionOutlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the outline is drawn for<br>
each pie section, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the shadow effect and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the shadow effect and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>simpleLabelOffset</td><td>RectangleInsets</td><td>Sets the offset for the simple labels and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.18,0.18,0.18,0.18,RELATIVE</code></pre></td></tr>
<tr><td>simpleLabels</td><td>boolean</td><td>Sets the flag that controls whether simple or extended labels are<br>
displayed on the plot, and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>startAngle</td><td>double</td><td>Sets the starting angle and sends a {@link PlotChangeEvent} to all<br>
registered listeners.  The initial default value is 90 degrees, which<br>
corresponds to 12 o'clock.  A value of zero corresponds to 3 o'clock...<br>
this is the encoding used by Java's Arc2D class.</td><td><pre><code>90.0</code></pre></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies<br>
registered listeners that the plot has been modified.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  Alignment options<br>
are defined by the {@link org.jfree.ui.Align} class in the JCommon<br>
class library.</td><td><pre><code>15</code></pre></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><pre><code>0.5</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>4.0,8.0,4.0,8.0</code></pre></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or<br>
<pre><code>null</code></pre>, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive<br>
{@link PlotChangeEvent} notifications.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners. If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is<br>
drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h2>CategoryPlot</h2>
<p>A general plotting class that uses data from a {@link CategoryDataset} and<br>
renders each data item using a {@link CategoryItemRenderer}.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/CategoryPlot.html'>org.jfree.chart.plot.CategoryPlot</a></b></td></tr>
<tr><td>anchorValue</td><td>double</td><td>Sets the anchor value and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>axisOffset</td><td>RectangleInsets</td><td>Sets the axis offsets (gap between the data area and the axes) and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.0,0.0,0.0,0.0</code></pre></td></tr>
<tr><td>crosshairDatasetIndex</td><td>int</td><td>Sets the dataset index for the crosshair and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>domainCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the domain crosshair.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>domainCrosshairStroke</td><td>Stroke</td><td>Sets the stroke used to draw the domain crosshair, and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>domainCrosshairVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain crosshair is<br>
displayed by the plot, and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>domainGridlinePaint</td><td>Paint</td><td>Sets the paint used to draw the grid-lines (if any) against the domain<br>
axis and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#c0c0c0</code></pre></td></tr>
<tr><td>domainGridlineStroke</td><td>Stroke</td><td>Sets the stroke used to draw grid-lines against the domain axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>domainGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not grid-lines are drawn against<br>
the domain axis.<br>
<br /><br />
If the flag value changes, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>drawSharedDomainAxis</td><td>boolean</td><td>Sets the flag that controls whether the shared domain axis is drawn when<br>
this plot is being used as a subplot.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>orientation</td><td>PlotOrientation</td><td>Sets the orientation for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>VERTICAL</code></pre></td></tr>
<tr><td>rangeCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair should<br>
"lock-on" to actual data values, and sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>rangeCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the range crosshair (if visible) and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>rangeCrosshairStroke</td><td>Stroke</td><td>Sets the pen-style (<pre><code>Stroke</code></pre>) used to draw the range<br>
crosshair (if visible), and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeCrosshairValue</td><td>double</td><td>Sets the range crosshair value and, if the crosshair is visible, sends<br>
a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>rangeCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair is visible.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangeGridlinePaint</td><td>Paint</td><td>Sets the paint used to draw the grid lines against the range axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#c0c0c0</code></pre></td></tr>
<tr><td>rangeGridlineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the grid-lines against the range axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not grid-lines are drawn against<br>
the range axis.  If the flag changes value, a {@link PlotChangeEvent} is<br>
sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>rangeMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the range axis<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>rangeMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the range axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis minor grid<br>
lines are visible.<br>
<br /><br />
If the flag value is changed, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangePannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along<br>
the range axes.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangeZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the range axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>rangeZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the range axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>rangeZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is<br>
displayed for the range axis, and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>weight</td><td>int</td><td>Sets the weight for the plot and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies<br>
registered listeners that the plot has been modified.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  Alignment options<br>
are defined by the {@link org.jfree.ui.Align} class in the JCommon<br>
class library.</td><td><pre><code>15</code></pre></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><pre><code>0.5</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>4.0,8.0,4.0,8.0</code></pre></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or<br>
<pre><code>null</code></pre>, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive<br>
{@link PlotChangeEvent} notifications.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners. If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is<br>
drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h2>PiePlot</h2>
<p>A plot that displays data in the form of a pie chart, using data from any<br>
class that implements the {@link PieDataset} interface.<br>
The example shown here is generated by the <pre><code>PieChartDemo2.java</code></pre>
program included in the JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/PiePlotSample.png' />"<br>
alt="PiePlotSample.png" /><br>
<br /><br />
Special notes:<br>
<ol>
<li>the default starting point is 12 o'clock and the pie sections proceed<br>
in a clockwise direction, but these settings can be changed;</li>
<li>negative values in the dataset are ignored;</li>
<li>there are utility methods for creating a {@link PieDataset} from a<br>
{@link org.jfree.data.category.CategoryDataset};</li>
</ol></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/PiePlot.html'>org.jfree.chart.plot.PiePlot</a></b></td></tr>
<tr><td>autoPopulateSectionOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline paint is<br>
auto-populated by the {@link #lookupSectionOutlinePaint(Comparable)}<br>
method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSectionOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline stroke is<br>
auto-populated by the {@link #lookupSectionOutlineStroke(Comparable)}<br>
method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSectionPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section paint is<br>
auto-populated by the {@link #lookupSectionPaint(Comparable)} method,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSectionOutlinePaint</td><td>Paint</td><td>Sets the base section paint.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseSectionOutlineStroke</td><td>Stroke</td><td>Sets the base section stroke.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>baseSectionPaint</td><td>Paint</td><td>Sets the base section paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>circular</td><td>boolean</td><td>A flag indicating whether the pie chart is circular, or stretched into<br>
an elliptical shape.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>ignoreNullValues</td><td>boolean</td><td>Sets a flag that controls whether <pre><code>null</code></pre> values are ignored,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.  At<br>
present, this only affects whether or not the key is presented in the<br>
legend.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>ignoreZeroValues</td><td>boolean</td><td>Sets a flag that controls whether zero values are ignored,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.  This<br>
only affects whether or not a label appears for the non-visible<br>
pie section.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>interiorGap</td><td>double</td><td>Sets the interior gap and sends a {@link PlotChangeEvent} to all<br>
registered listeners.  This controls the space between the edges of the<br>
pie plot and the plot area itself (the region where the section labels<br>
appear).</td><td><pre><code>0.08</code></pre></td></tr>
<tr><td>labelBackgroundPaint</td><td>Paint</td><td>Sets the section label background paint and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffc0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the section label font and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>labelGap</td><td>double</td><td>Sets the gap between the edge of the pie and the labels (expressed as a<br>
percentage of the plot width) and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.025</code></pre></td></tr>
<tr><td>labelLinkMargin</td><td>double</td><td>Sets the link margin and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.025</code></pre></td></tr>
<tr><td>labelLinkPaint</td><td>Paint</td><td>Sets the paint used for the lines that connect pie sections to their<br>
corresponding labels, and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelLinkStroke</td><td>Stroke</td><td>Sets the link stroke and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>labelLinksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not label linking lines are<br>
visible and sends a {@link PlotChangeEvent} to all registered listeners.<br>
Please take care when hiding the linking lines - depending on the data<br>
values, the labels can be displayed some distance away from the<br>
corresponding pie section.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>labelOutlinePaint</td><td>Paint</td><td>Sets the section label outline paint and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelOutlineStroke</td><td>Stroke</td><td>Sets the section label outline stroke and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>labelPadding</td><td>RectangleInsets</td><td>Sets the padding between each label and its outline and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>2.0,2.0,2.0,2.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the section label paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>labelShadowPaint</td><td>Paint</td><td>Sets the section label shadow paint and sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#979797</code></pre></td></tr>
<tr><td>legendItemShape</td><td>Shape</td><td>Sets the shape used for legend items and sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>maximumLabelWidth</td><td>double</td><td>Sets the maximum label width as a percentage of the plot width and sends<br>
a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.14</code></pre></td></tr>
<tr><td>minimumArcAngleToDraw</td><td>double</td><td>Sets the minimum arc angle that will be drawn.  Pie sections for an<br>
angle smaller than this are not drawn, to avoid a JDK bug.  See this<br>
link for details:<br>
<br><br>
<a href='http://www.jfree.org/phpBB2/viewtopic.php?t=2707'>
<a href='http://www.jfree.org/phpBB2/viewtopic.php?t=2707'>http://www.jfree.org/phpBB2/viewtopic.php?t=2707</a></a>
<br><br>
...and this bug report in the Java Bug Parade:<br>
<br><br>
<a href=<br>
"<a href='http://developer.java.sun.com/developer/bugParade/bugs/4836495.html'>http://developer.java.sun.com/developer/bugParade/bugs/4836495.html</a>"><br>
<a href='http://developer.java.sun.com/developer/bugParade/bugs/4836495.html'>http://developer.java.sun.com/developer/bugParade/bugs/4836495.html</a>

Unknown end tag for </a>

<br>
<br>
Unknown end tag for </td><br>
<br>
<td><pre><code>1.0E-5</code></pre></td>

Unknown end tag for </tr>

<br>
<tr><td>pieIndex</td><td>int</td><td>Sets the pie index (this is used by the {@link MultiplePiePlot} class to<br>
track subplots).</td><td><pre><code>0</code></pre></td></tr>
<tr><td>sectionOutlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the outline is drawn for<br>
each pie section, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the shadow effect and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the shadow effect and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>simpleLabelOffset</td><td>RectangleInsets</td><td>Sets the offset for the simple labels and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.18,0.18,0.18,0.18,RELATIVE</code></pre></td></tr>
<tr><td>simpleLabels</td><td>boolean</td><td>Sets the flag that controls whether simple or extended labels are<br>
displayed on the plot, and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>startAngle</td><td>double</td><td>Sets the starting angle and sends a {@link PlotChangeEvent} to all<br>
registered listeners.  The initial default value is 90 degrees, which<br>
corresponds to 12 o'clock.  A value of zero corresponds to 3 o'clock...<br>
this is the encoding used by Java's Arc2D class.</td><td><pre><code>90.0</code></pre></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies<br>
registered listeners that the plot has been modified.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  Alignment options<br>
are defined by the {@link org.jfree.ui.Align} class in the JCommon<br>
class library.</td><td><pre><code>15</code></pre></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><pre><code>0.5</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>4.0,8.0,4.0,8.0</code></pre></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or<br>
<pre><code>null</code></pre>, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive<br>
{@link PlotChangeEvent} notifications.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners. If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is<br>
drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<br>
<br>
Unknown end tag for </table><br>
<br>
<br>
<h2>XYPlot</h2>
<p>A general class for plotting data in the form of (x, y) pairs.  This plot can<br>
use data from any class that implements the {@link XYDataset} interface.<br>
<br /><br />
<pre><code>XYPlot</code></pre> makes use of an {@link XYItemRenderer} to draw each point<br>
on the plot.  By using different renderers, various chart types can be<br>
produced.<br>
<br /><br />
The {@link org.jfree.chart.ChartFactory} class contains static methods for<br>
creating pre-configured charts.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/XYPlot.html'>org.jfree.chart.plot.XYPlot</a></b></td></tr>
<tr><td>axisOffset</td><td>RectangleInsets</td><td>Sets the axis offsets (gap between the data area and the axes) and sends<br>
a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>0.0,0.0,0.0,0.0</code></pre></td></tr>
<tr><td>domainCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the domain crosshair should<br>
"lock-on" to actual data values.  If the flag value changes, this<br>
method sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>domainCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the crosshairs (if visible) and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>domainCrosshairStroke</td><td>Stroke</td><td>Sets the Stroke used to draw the crosshairs (if visible) and notifies<br>
registered listeners that the axis has been modified.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>domainCrosshairValue</td><td>double</td><td>Sets the domain crosshair value and sends a {@link PlotChangeEvent} to<br>
all registered listeners (provided that the domain crosshair is visible).</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>domainCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the domain crosshair is visible<br>
and, if the flag changes, sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>domainGridlinePaint</td><td>Paint</td><td>Sets the paint for the grid lines plotted against the domain axis, and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#c0c0c0</code></pre></td></tr>
<tr><td>domainGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the grid lines plotted against the domain axis, and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>domainGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain grid-lines are<br>
visible.<br>
<br /><br />
If the flag value is changed, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>domainMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the domain axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>domainMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the domain<br>
axis, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>domainMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain minor grid-lines<br>
are visible.<br>
<br /><br />
If the flag value is changed, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>domainPannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along the<br>
domain axes.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>domainTickBandPaint</td><td>Paint</td><td>Sets the paint for the domain tick bands.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>domainZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the domain axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>domainZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the domain axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>domainZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is<br>
displayed for the domain axis, and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>orientation</td><td>PlotOrientation</td><td>Sets the orientation for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>VERTICAL</code></pre></td></tr>
<tr><td>rangeCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair should<br>
"lock-on" to actual data values.  If the flag value changes, this method<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>rangeCrosshairPaint</td><td>Paint</td><td>Sets the paint used to color the crosshairs (if visible) and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>rangeCrosshairStroke</td><td>Stroke</td><td>Sets the stroke used to draw the crosshairs (if visible) and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeCrosshairValue</td><td>double</td><td>Sets the range crosshair value.<br>
<br /><br />
Registered listeners are notified that the plot has been modified, but<br>
only if the crosshair is visible.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>rangeCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair is visible.<br>
If the flag value changes, this method sends a {@link PlotChangeEvent}<br>
to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangeGridlinePaint</td><td>Paint</td><td>Sets the paint for the grid lines plotted against the range axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#c0c0c0</code></pre></td></tr>
<tr><td>rangeGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the grid lines plotted against the range axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis grid lines<br>
are visible.<br>
<br /><br />
If the flag value is changed, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>rangeMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the range axis<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>rangeMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the range axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=1</code></pre></td></tr>
<tr><td>rangeMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis minor grid<br>
lines are visible.<br>
<br /><br />
If the flag value is changed, a {@link PlotChangeEvent} is sent to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangePannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along<br>
the range axes.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rangeTickBandPaint</td><td>Paint</td><td>Sets the paint for the range tick bands.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>rangeZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the range axis and<br>
sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>rangeZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the range axis,<br>
and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>rangeZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is<br>
displayed for the range axis, and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>weight</td><td>int</td><td>Sets the weight for the plot and sends a {@link PlotChangeEvent} to all<br>
registered listeners.</td><td><pre><code>1</code></pre></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies<br>
registered listeners that the plot has been modified.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  Alignment options<br>
are defined by the {@link org.jfree.ui.Align} class in the JCommon<br>
class library.</td><td><pre><code>15</code></pre></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><pre><code>0.5</code></pre></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to<br>
all registered listeners.</td><td><pre><code>4.0,8.0,4.0,8.0</code></pre></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or<br>
<pre><code>null</code></pre>, and sends a {@link PlotChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a<br>
{@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive<br>
{@link PlotChangeEvent} notifications.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners.  If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a<br>
{@link PlotChangeEvent} to all registered listeners. If you set this<br>
attribute to <pre><code>null</code></pre>, no outline will be drawn.</td><td><pre><code>line=0.5|dash=0</code></pre></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is<br>
drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h1>Axis Types</h1>
<h2>NumberAxis</h2>
<p>An axis for displaying numerical data.<br>
<br /><br />
If the axis is set up to automatically determine its range to fit the data,<br>
you can ensure that the range includes zero (statisticians usually prefer<br>
this) by setting the <pre><code>autoRangeIncludesZero</code></pre> flag to<br>
<pre><code>true</code></pre>.<br>
<br /><br />
The <pre><code>NumberAxis</code></pre> class has a mechanism for automatically<br>
selecting a tick unit that is appropriate for the current axis range.  This<br>
mechanism is an adaptation of code suggested by Laurence Vanhelsuwe.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/NumberAxis.html'>org.jfree.chart.axis.NumberAxis</a></b></td></tr>
<tr><td>autoRangeIncludesZero</td><td>boolean</td><td>Sets the flag that indicates whether or not the axis range, if<br>
automatically calculated, is forced to include zero.<br>
<br /><br />
If the flag is changed to <pre><code>true</code></pre>, the axis range is<br>
recalculated.<br>
<br /><br />
Any change to the flag will trigger an {@link AxisChangeEvent}.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeStickyZero</td><td>boolean</td><td>Sets a flag that affects the auto-range when zero falls outside the data<br>
range but inside the margins defined for the axis.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>numberFormatOverride</td><td>NumberFormat</td><td>Sets the number format override.  If this is non-null, then it will be<br>
used to format the numbers on the axis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>tickUnit</td><td>NumberTickUnit</td><td>Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to<br>
all registered listeners.  A side effect of calling this method is that<br>
the "auto-select" feature for tick units is switched off (you can<br>
restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}<br>
method).</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is<br>
automatically adjusted to fit the data, and notifies registered<br>
listeners that the axis has been modified.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically<br>
selected from a range of standard tick units.  If the flag is changed,<br>
registered listeners are notified that the chart has changed.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and<br>
notifies registered listeners that the axis has changed.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the<br>
end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is<br>
sent to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the negative direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the positive direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed<br>
vertically (that is, rotated 90 degrees from horizontal).  If the flag<br>
is changed, an {@link AxisChangeEvent} is sent to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.<br>
<br /><br />
This is used when combining more than one plot on a chart.  In this case,<br>
there may be several axes that need to have the same height or width so<br>
that they are aligned.  This method is used to fix a dimension for the<br>
axis (the context determines whether the dimension is horizontal or<br>
vertical).</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>3.0,3.0,3.0,3.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0,4.0,2.0,4.0</code></pre></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are<br>
visible and sends an {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h2>PeriodAxis</h2>
<p>An axis that displays a date scale based on a<br>
{@link org.jfree.data.time.RegularTimePeriod}.  This axis works when<br>
displayed across the bottom or top of a plot, but is broken for display at<br>
the left or right of charts.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/PeriodAxis.html'>org.jfree.chart.axis.PeriodAxis</a></b></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>minorTickMarkPaint</td><td>Paint</td><td>Sets the paint used to display minor tick marks, if they are<br>
visible, and sends a {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>minorTickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to display minor tick marks, if they are<br>
visible, and sends a {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not minor tick marks<br>
are displayed for the axis, and sends a {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is<br>
automatically adjusted to fit the data, and notifies registered<br>
listeners that the axis has been modified.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically<br>
selected from a range of standard tick units.  If the flag is changed,<br>
registered listeners are notified that the chart has changed.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and<br>
notifies registered listeners that the axis has changed.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the<br>
end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is<br>
sent to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the negative direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the positive direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed<br>
vertically (that is, rotated 90 degrees from horizontal).  If the flag<br>
is changed, an {@link AxisChangeEvent} is sent to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.<br>
<br /><br />
This is used when combining more than one plot on a chart.  In this case,<br>
there may be several axes that need to have the same height or width so<br>
that they are aligned.  This method is used to fix a dimension for the<br>
axis (the context determines whether the dimension is horizontal or<br>
vertical).</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>3.0,3.0,3.0,3.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0,4.0,2.0,4.0</code></pre></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are<br>
visible and sends an {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h2>DateAxis</h2>
<p>The base class for axes that display dates.  You will find it easier to<br>
understand how this axis works if you bear in mind that it really<br>
displays/measures integer (or long) data, where the integers are<br>
milliseconds since midnight, 1-Jan-1970.  When displaying tick labels, the<br>
millisecond values are converted back to dates using a<br>
<pre><code>DateFormat</code></pre> instance.<br>
<br /><br />
You can also create a {@link org.jfree.chart.axis.Timeline} and supply in<br>
the constructor to create an axis that only contains certain domain values.<br>
For example, this allows you to create a date axis that only contains<br>
working days.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/DateAxis.html'>org.jfree.chart.axis.DateAxis</a></b></td></tr>
<tr><td>dateFormatOverride</td><td>DateFormat</td><td>Sets the date format override.  If this is non-null, then it will be<br>
used to format the dates on the axis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>maximumDate</td><td>Date</td><td>Sets the maximum date visible on the axis and sends an<br>
{@link AxisChangeEvent} to all registered listeners.  If<br>
<pre><code>maximumDate</code></pre> is on or before the current minimum date for<br>
the axis, the minimum date will be shifted to preserve the current<br>
length of the axis.</td><td><pre><code>1969-12-31</code></pre></td></tr>
<tr><td>minimumDate</td><td>Date</td><td>Sets the minimum date visible on the axis and sends an<br>
{@link AxisChangeEvent} to all registered listeners.  If<br>
<pre><code>date</code></pre> is on or after the current maximum date for<br>
the axis, the maximum date will be shifted to preserve the current<br>
length of the axis.</td><td><pre><code>1969-12-31</code></pre></td></tr>
<tr><td>tickMarkPosition</td><td>DateTickMarkPosition</td><td>Sets the tick mark position (start, middle or end of the time period)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>START</code></pre></td></tr>
<tr><td>tickUnit</td><td>DateTickUnit</td><td>Sets the tick unit for the axis.  The auto-tick-unit-selection flag is<br>
set to <pre><code>false</code></pre>, and registered listeners are notified that<br>
the axis has been changed.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is<br>
automatically adjusted to fit the data, and notifies registered<br>
listeners that the axis has been modified.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically<br>
selected from a range of standard tick units.  If the flag is changed,<br>
registered listeners are notified that the chart has changed.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and<br>
notifies registered listeners that the axis has changed.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the<br>
end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is<br>
sent to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the negative direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the positive direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed<br>
vertically (that is, rotated 90 degrees from horizontal).  If the flag<br>
is changed, an {@link AxisChangeEvent} is sent to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.<br>
<br /><br />
This is used when combining more than one plot on a chart.  In this case,<br>
there may be several axes that need to have the same height or width so<br>
that they are aligned.  This method is used to fix a dimension for the<br>
axis (the context determines whether the dimension is horizontal or<br>
vertical).</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>3.0,3.0,3.0,3.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0,4.0,2.0,4.0</code></pre></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are<br>
visible and sends an {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table>
<h2>LogarithmicAxis</h2>
<p>A numerical axis that uses a logarithmic scale.</p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/LogarithmicAxis.html'>org.jfree.chart.axis.LogarithmicAxis</a></b></td></tr>
<tr><td>allowNegativesFlag</td><td>boolean</td><td>Sets the 'allowNegativesFlag' flag; true to allow negative values<br>
in data, false to be able to plot positive values arbitrarily close to<br>
zero.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoRangeNextLogFlag</td><td>boolean</td><td>Sets the 'autoRangeNextLogFlag' flag.  This determines whether or<br>
not the 'autoAdjustRange()' method will select the next "10^n"<br>
values when determining the upper and lower bounds.  The default<br>
value is false.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>expTickLabelsFlag</td><td>boolean</td><td>Sets the 'expTickLabelsFlag' flag.  If the 'log10TickLabelsFlag'<br>
is false then this will set whether or not "1e#"-style tick labels<br>
are used.  The default is to use regular numeric tick labels.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>log10TickLabelsFlag</td><td>boolean</td><td>Sets the 'log10TickLabelsFlag' flag.  The default value is false.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>strictValuesFlag</td><td>boolean</td><td>Sets the 'strictValuesFlag' flag; if true and 'allowNegativesFlag'<br>
is false then this axis will throw a runtime exception if any of its<br>
values are less than or equal to zero; if false then the axis will<br>
adjust for values less than or equal to zero as needed.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeIncludesZero</td><td>boolean</td><td>Sets the flag that indicates whether or not the axis range, if<br>
automatically calculated, is forced to include zero.<br>
<br /><br />
If the flag is changed to <pre><code>true</code></pre>, the axis range is<br>
recalculated.<br>
<br /><br />
Any change to the flag will trigger an {@link AxisChangeEvent}.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeStickyZero</td><td>boolean</td><td>Sets a flag that affects the auto-range when zero falls outside the data<br>
range but inside the margins defined for the axis.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>numberFormatOverride</td><td>NumberFormat</td><td>Sets the number format override.  If this is non-null, then it will be<br>
used to format the numbers on the axis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>tickUnit</td><td>NumberTickUnit</td><td>Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to<br>
all registered listeners.  A side effect of calling this method is that<br>
the "auto-select" feature for tick units is switched off (you can<br>
restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}<br>
method).</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is<br>
automatically adjusted to fit the data, and notifies registered<br>
listeners that the axis has been modified.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically<br>
selected from a range of standard tick units.  If the flag is changed,<br>
registered listeners are notified that the chart has changed.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and<br>
notifies registered listeners that the axis has changed.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the<br>
end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is<br>
sent to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0</code></pre></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the negative direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow<br>
drawn that points in the positive direction for the axis, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at<br>
the end of an axis line and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)<br>
and sends an {@link AxisChangeEvent} to all registered listeners.  This<br>
margin is added only when the axis range is auto-calculated - if you set<br>
the axis range manually, the margin is ignored.</td><td><pre><code>0.05</code></pre></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed<br>
vertically (that is, rotated 90 degrees from horizontal).  If the flag<br>
is changed, an {@link AxisChangeEvent} is sent to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.<br>
<br /><br />
This is used when combining more than one plot on a chart.  In this case,<br>
there may be several axes that need to have the same height or width so<br>
that they are aligned.  This method is used to fix a dimension for the<br>
axis (the context determines whether the dimension is horizontal or<br>
vertical).</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-12</code></pre></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>3.0,3.0,3.0,3.0</code></pre></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}<br>
to all registered listeners.</td><td><pre><code>2.0,4.0,2.0,4.0</code></pre></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and<br>
sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are<br>
visible and sends an {@link AxisChangeEvent} to all registered<br>
listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an<br>
{@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing<br>
and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends<br>
an {@link AxisChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
</table></blockquote>


<h1>special property values</h1>
<blockquote><pre><code>none</code></pre> is a special property keyword that indicates that this property should be left unset.     For example, you can set backgroundImage to none instead of a URL.