


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
<li><a href="tag-reference.jsp">Tag Reference</a></li>
<li id="selected">Property Reference</li>
<li><a href="faq.jsp">FAQ</a></li>

</ul>
</div>


<div id="content">
<ul>
<li><a href="#chart">chart</a></li>
<li><a href="#title">title</a></li>
<li><a href="#legend">legend</a></li>
<li><a href="#marker">marker</a></li>
<li><a href="#plot">Plot Types</a></li>
<ul>
<li><a href="#org.jfree.chart.plot.PiePlot3D">PiePlot3D</a></li>
<li><a href="#org.jfree.chart.plot.CategoryPlot">CategoryPlot</a></li>
<li><a href="#org.jfree.chart.plot.PiePlot">PiePlot</a></li>
<li><a href="#org.jfree.chart.plot.XYPlot">XYPlot</a></li>
</ul>
<li><a href="#axis">Axis Types</a></li>
<ul>
<li><a href="#number">number</a></li>
<li><a href="#period">period</a></li>
<li><a href="#date">date</a></li>
<li><a href="#log">log</a></li>
</ul>
<li><a href="#xyrenderers">XY/timeseries Graph Types</a></li>
<ul>
<li><a href="#org.jfree.chart.renderer.xy.XYAreaRenderer2">Area</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYBarRenderer">Bar</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYBubbleRenderer">Bubble</a></li>
<li><a href="#org.jfree.chart.renderer.xy.CandlestickRenderer">Candlestick</a></li>
<li><a href="#org.jfree.chart.renderer.xy.ClusteredXYBarRenderer">Clustered Bar</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYDifferenceRenderer">Difference</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYDotRenderer">Dot</a></li>
<li><a href="#org.jfree.chart.renderer.xy.DefaultXYItemRenderer">Line</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYLine3DRenderer">Line 3D</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYLineAndShapeRenderer">Line And Shape</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYSplineRenderer">Spline</a></li>
<li><a href="#org.jfree.chart.renderer.xy.StackedXYAreaRenderer2">Stacked Area</a></li>
<li><a href="#org.jfree.chart.renderer.xy.StackedXYBarRenderer">Stacked Bar</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYStepRenderer">Step</a></li>
<li><a href="#org.jfree.chart.renderer.xy.XYStepAreaRenderer">Step Area</a></li>
</ul>
<li><a href="#categoryrenderers">Category Graph Types</a></li>
<ul>
<li><a href="#org.jfree.chart.renderer.category.AreaRenderer">Area</a></li>
<li><a href="#org.jfree.chart.renderer.category.BarRenderer">Bar</a></li>
<li><a href="#org.jfree.chart.renderer.category.BarRenderer3D">Bar 3D</a></li>
<li><a href="#org.jfree.chart.renderer.category.GanttRenderer">Gantt</a></li>
<li><a href="#org.jfree.chart.renderer.category.GroupedStackedBarRenderer">Grouped Stacked Bar</a></li>
<li><a href="#org.jfree.chart.renderer.category.LayeredBarRenderer">Layered Bar</a></li>
<li><a href="#org.jfree.chart.renderer.category.LevelRenderer">Level</a></li>
<li><a href="#org.jfree.chart.renderer.category.LineRenderer3D">Line 3D</a></li>
<li><a href="#org.jfree.chart.renderer.category.LineAndShapeRenderer">Line And Shape</a></li>
<li><a href="#org.jfree.chart.renderer.category.MinMaxCategoryRenderer">Min Max</a></li>
<li><a href="#org.jfree.chart.renderer.category.StackedAreaRenderer">Stacked Area</a></li>
<li><a href="#org.jfree.chart.renderer.category.StackedBarRenderer">Stacked Bar</a></li>
<li><a href="#org.jfree.chart.renderer.category.StackedBarRenderer3D">Stacked Bar 3D</a></li>
<li><a href="#org.jfree.chart.renderer.category.CategoryStepRenderer">Step</a></li>
<li><a href="#org.jfree.chart.renderer.category.WaterfallBarRenderer">Waterfall</a></li>
</ul>
</ul>
<a name="chart"></a><h1>chart</h1>
<p>A chart class implemented using the Java 2D APIs.  The current version
 supports bar charts, line charts, pie charts and xy plots (including time
 series data).
 <P>
 JFreeChart coordinates several objects to achieve its aim of being able to
 draw a chart on a Java 2D graphics device: a list of {@link Title} objects
 (which often includes the chart's legend), a {@link Plot} and a
 {@link org.jfree.data.general.Dataset} (the plot in turn manages a
 domain axis and a range axis).
 <P>
 You should use a {@link ChartPanel} to display a chart in a GUI.
 <P>
 The {@link ChartFactory} class contains static methods for creating
 'ready-made' charts.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/JFreeChart.html">org.jfree.chart.JFreeChart</a></b></td></tr>
<tr><td>antiAlias</td><td>boolean</td><td>Sets a flag that indicates whether or not anti-aliasing is used when the
 chart is drawn.
 <br/><br/>
 Anti-aliasing usually improves the appearance of charts, but is slower.</td><td><code>true</code></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the chart and sends a
 {@link ChartChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the background alignment.  Alignment options are defined by the
 {@link org.jfree.ui.Align} class.</td><td><code>15</code></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha-transparency for the chart's background image.
 Registered listeners are notified that the chart has been changed.</td><td><code>0.5</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the paint used to fill the chart background and sends a
 {@link ChartChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>borderPaint</td><td>Paint</td><td>Sets the paint used to draw the chart border (if visible).</td><td><code>#000000</code></td></tr>
<tr><td>borderStroke</td><td>Stroke</td><td>Sets the stroke used to draw the chart border (if visible).</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>borderVisible</td><td>boolean</td><td>Sets a flag that controls whether or not a border is drawn around the
 outside of the chart.</td><td><code>false</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive
 {@link ChartChangeEvent} notifications.</td><td><code>true</code></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding between the chart border and the chart drawing area,
 and sends a {@link ChartChangeEvent} to all registered listeners.</td><td><code>0.0,0.0,0.0,0.0</code></td></tr>
</table>
<a name="title"></a><h1>title</h1>
<p>A chart title that displays a text string with automatic wrapping as
 required.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/title/TextTitle.html">org.jfree.chart.title.TextTitle</a></b></td></tr>
<tr><td>URLText</td><td>String</td><td>Sets the URL text to the specified text and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background paint and sends a {@link TitleChangeEvent} to all
 registered listeners.  If you set this attribute to <code>null</code>,
 no background is painted (which makes the title background transparent).</td><td><code>&nbsp;</code></td></tr>
<tr><td>expandToFitSpace</td><td>boolean</td><td>Sets the flag that controls whether the title expands to fit the
 available space, and sends a {@link TitleChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>font</td><td>Font</td><td>Sets the font used to display the title string.  Registered listeners
 are notified that the title has been modified.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>maximumLinesToDisplay</td><td>int</td><td>Sets the maximum number of lines to display and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>2147483647</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint used to display the title string.  Registered listeners
 are notified that the title has been modified.</td><td><code>#000000</code></td></tr>
<tr><td>text</td><td>String</td><td>Sets the title to the specified text and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>textAlignment</td><td>HorizontalAlignment</td><td>Sets the text alignment and sends a {@link TitleChangeEvent} to
 all registered listeners.</td><td><code>CENTER</code></td></tr>
<tr><td>toolTipText</td><td>String</td><td>Sets the tool tip text to the specified text and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>horizontalAlignment</td><td>HorizontalAlignment</td><td>Sets the horizontal alignment for the title and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>CENTER</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets the flag that indicates whether or not the notification mechanism
 is enabled.  There are certain situations (such as cloning) where you
 want to turn notification off temporarily.</td><td><code>true</code></td></tr>
<tr><td>position</td><td>RectangleEdge</td><td>Sets the position for the title and sends a {@link TitleChangeEvent} to
 all registered listeners.</td><td><code>TOP</code></td></tr>
<tr><td>verticalAlignment</td><td>VerticalAlignment</td><td>Sets the vertical alignment for the title, and notifies any registered
 listeners of the change.</td><td><code>CENTER</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the title should be drawn, and
 sends a {@link TitleChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>ID</td><td>String</td><td>Sets the id for the block.</td><td><code>&nbsp;</code></td></tr>
<tr><td>height</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><code>0.0</code></td></tr>
<tr><td>margin</td><td>RectangleInsets</td><td>Sets the margin (use {@link RectangleInsets#ZERO_INSETS} for no
 padding).</td><td><code>0.0,0.0,0.0,0.0</code></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding (use {@link RectangleInsets#ZERO_INSETS} for no
 padding).</td><td><code>1.0,1.0,1.0,1.0</code></td></tr>
<tr><td>width</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><code>0.0</code></td></tr>
</table>
<a name="legend"></a><h1>legend</h1>
<p>A chart title that displays a legend for the data in the chart.
 <P>
 The title can be populated with legend items manually, or you can assign a
 reference to the plot, in which case the legend items will be automatically
 created to match the dataset(s).</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/title/LegendTitle.html">org.jfree.chart.title.LegendTitle</a></b></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background paint for the legend and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>itemFont</td><td>Font</td><td>Sets the item font and sends a {@link TitleChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>itemLabelPadding</td><td>RectangleInsets</td><td>Sets the padding used for the item labels in the legend.</td><td><code>2.0,2.0,2.0,2.0</code></td></tr>
<tr><td>itemPaint</td><td>Paint</td><td>Sets the item paint.</td><td><code>#000000</code></td></tr>
<tr><td>legendItemGraphicAnchor</td><td>RectangleAnchor</td><td>Sets the anchor point used for the graphic in each legend item.</td><td><code>&nbsp;</code></td></tr>
<tr><td>legendItemGraphicEdge</td><td>RectangleEdge</td><td>Sets the location of the shape within each legend item.</td><td><code>LEFT</code></td></tr>
<tr><td>legendItemGraphicLocation</td><td>RectangleAnchor</td><td>Sets the legend item graphic location.</td><td><code>&nbsp;</code></td></tr>
<tr><td>legendItemGraphicPadding</td><td>RectangleInsets</td><td>Sets the padding that will be applied to each item graphic in the
 legend and sends a {@link TitleChangeEvent} to all registered listeners.</td><td><code>2.0,2.0,2.0,2.0</code></td></tr>
<tr><td>horizontalAlignment</td><td>HorizontalAlignment</td><td>Sets the horizontal alignment for the title and sends a
 {@link TitleChangeEvent} to all registered listeners.</td><td><code>CENTER</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets the flag that indicates whether or not the notification mechanism
 is enabled.  There are certain situations (such as cloning) where you
 want to turn notification off temporarily.</td><td><code>true</code></td></tr>
<tr><td>position</td><td>RectangleEdge</td><td>Sets the position for the title and sends a {@link TitleChangeEvent} to
 all registered listeners.</td><td><code>BOTTOM</code></td></tr>
<tr><td>verticalAlignment</td><td>VerticalAlignment</td><td>Sets the vertical alignment for the title, and notifies any registered
 listeners of the change.</td><td><code>CENTER</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the title should be drawn, and
 sends a {@link TitleChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>ID</td><td>String</td><td>Sets the id for the block.</td><td><code>&nbsp;</code></td></tr>
<tr><td>height</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><code>0.0</code></td></tr>
<tr><td>margin</td><td>RectangleInsets</td><td>Sets the margin (use {@link RectangleInsets#ZERO_INSETS} for no
 padding).</td><td><code>1.0,1.0,1.0,1.0</code></td></tr>
<tr><td>padding</td><td>RectangleInsets</td><td>Sets the padding (use {@link RectangleInsets#ZERO_INSETS} for no
 padding).</td><td><code>1.0,1.0,1.0,1.0</code></td></tr>
<tr><td>width</td><td>double</td><td>Sets the natural width of the block, if this is known in advance.</td><td><code>0.0</code></td></tr>
</table>
<a name="marker"></a><h1>marker</h1>
<p>The base class for markers that can be added to plots to highlight a value
 or range of values.
 <br><br>
 An event notification mechanism was added to this class in JFreeChart
 version 1.0.3.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/Marker.html">org.jfree.chart.plot.Marker</a></b></td></tr>
<tr><td>alpha</td><td>float</td><td>Sets the alpha transparency that should be used when drawing the
 marker, and sends a {@link MarkerChangeEvent} to all registered
 listeners.  The alpha transparency is a value in the range 0.0f
 (completely transparent) to 1.0f (completely opaque).</td><td><code>0.8</code></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label (if <code>null</code> no label is displayed) and sends a
 {@link MarkerChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelAnchor</td><td>RectangleAnchor</td><td>Sets the label anchor and sends a {@link MarkerChangeEvent} to all
 registered listeners.  The anchor defines the position of the label
 anchor, relative to the bounds of the marker.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the label font and sends a {@link MarkerChangeEvent} to all
 registered listeners.</td><td><code>SansSerif-9</code></td></tr>
<tr><td>labelOffset</td><td>RectangleInsets</td><td>Sets the label offset and sends a {@link MarkerChangeEvent} to all
 registered listeners.</td><td><code>3.0,3.0,3.0,3.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the label paint and sends a {@link MarkerChangeEvent} to all
 registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelTextAnchor</td><td>TextAnchor</td><td>Sets the label text anchor and sends a {@link MarkerChangeEvent} to
 all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint and sends a {@link MarkerChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke and sends a {@link MarkerChangeEvent} to all
 registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint and sends a {@link MarkerChangeEvent} to all registered
 listeners.</td><td><code>#808080</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke and sends a {@link MarkerChangeEvent} to all registered
 listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
</table>
<a name="plot"></a><h1>plot</h1>
<a name="org.jfree.chart.plot.PiePlot3D"></a><h2>PiePlot3D</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A plot that displays data in the form of a 3D pie chart, using data from
 any class that implements the {@link PieDataset} interface.
 <P>
 Although this class extends {@link PiePlot}, it does not currently support
 exploded sections.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/PiePlot3D.html">org.jfree.chart.plot.PiePlot3D</a></b></td></tr>
<tr><td>darkerSides</td><td>boolean</td><td>Sets a flag that controls whether or not the sides of the pie chart
 are rendered using a darker colour, and sends a {@link PlotChangeEvent}
 to all registered listeners.  This is only applied if the
 section colour is an instance of {@link java.awt.Color}.</td><td><code>false</code></td></tr>
<tr><td>depthFactor</td><td>double</td><td>Sets the pie depth as a percentage of the height of the plot area, and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.12</code></td></tr>
<tr><td>autoPopulateSectionOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline paint is
 auto-populated by the {@link #lookupSectionOutlinePaint(Comparable)}
 method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSectionOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline stroke is
 auto-populated by the {@link #lookupSectionOutlineStroke(Comparable)}
 method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSectionPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section paint is
 auto-populated by the {@link #lookupSectionPaint(Comparable)} method,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSectionOutlinePaint</td><td>Paint</td><td>Sets the base section paint.</td><td><code>#808080</code></td></tr>
<tr><td>baseSectionOutlineStroke</td><td>Stroke</td><td>Sets the base section stroke.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>baseSectionPaint</td><td>Paint</td><td>Sets the base section paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>circular</td><td>boolean</td><td>A flag indicating whether the pie chart is circular, or stretched into
 an elliptical shape.</td><td><code>true</code></td></tr>
<tr><td>ignoreNullValues</td><td>boolean</td><td>Sets a flag that controls whether <code>null</code> values are ignored,
 and sends a {@link PlotChangeEvent} to all registered listeners.  At
 present, this only affects whether or not the key is presented in the
 legend.</td><td><code>false</code></td></tr>
<tr><td>ignoreZeroValues</td><td>boolean</td><td>Sets a flag that controls whether zero values are ignored,
 and sends a {@link PlotChangeEvent} to all registered listeners.  This
 only affects whether or not a label appears for the non-visible
 pie section.</td><td><code>false</code></td></tr>
<tr><td>interiorGap</td><td>double</td><td>Sets the interior gap and sends a {@link PlotChangeEvent} to all
 registered listeners.  This controls the space between the edges of the
 pie plot and the plot area itself (the region where the section labels
 appear).</td><td><code>0.08</code></td></tr>
<tr><td>labelBackgroundPaint</td><td>Paint</td><td>Sets the section label background paint and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffc0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the section label font and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>labelGap</td><td>double</td><td>Sets the gap between the edge of the pie and the labels (expressed as a
 percentage of the plot width) and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0.025</code></td></tr>
<tr><td>labelLinkMargin</td><td>double</td><td>Sets the link margin and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0.025</code></td></tr>
<tr><td>labelLinkPaint</td><td>Paint</td><td>Sets the paint used for the lines that connect pie sections to their
 corresponding labels, and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelLinkStroke</td><td>Stroke</td><td>Sets the link stroke and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>labelLinksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not label linking lines are
 visible and sends a {@link PlotChangeEvent} to all registered listeners.
 Please take care when hiding the linking lines - depending on the data
 values, the labels can be displayed some distance away from the
 corresponding pie section.</td><td><code>true</code></td></tr>
<tr><td>labelOutlinePaint</td><td>Paint</td><td>Sets the section label outline paint and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelOutlineStroke</td><td>Stroke</td><td>Sets the section label outline stroke and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>labelPadding</td><td>RectangleInsets</td><td>Sets the padding between each label and its outline and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>2.0,2.0,2.0,2.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the section label paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelShadowPaint</td><td>Paint</td><td>Sets the section label shadow paint and sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>#979797</code></td></tr>
<tr><td>legendItemShape</td><td>Shape</td><td>Sets the shape used for legend items and sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>maximumLabelWidth</td><td>double</td><td>Sets the maximum label width as a percentage of the plot width and sends
 a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.14</code></td></tr>
<tr><td>minimumArcAngleToDraw</td><td>double</td><td>Sets the minimum arc angle that will be drawn.  Pie sections for an
 angle smaller than this are not drawn, to avoid a JDK bug.  See this
 link for details:
 <br><br>
 <a href="http://www.jfree.org/phpBB2/viewtopic.php?t=2707">
 http://www.jfree.org/phpBB2/viewtopic.php?t=2707</a>
 <br><br>
 ...and this bug report in the Java Bug Parade:
 <br><br>
 <a href=
 "http://developer.java.sun.com/developer/bugParade/bugs/4836495.html">
 http://developer.java.sun.com/developer/bugParade/bugs/4836495.html</a></td><td><code>1.0E-5</code></td></tr>
<tr><td>pieIndex</td><td>int</td><td>Sets the pie index (this is used by the {@link MultiplePiePlot} class to
 track subplots).</td><td><code>0</code></td></tr>
<tr><td>sectionOutlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the outline is drawn for
 each pie section, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the shadow effect and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the shadow effect and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>simpleLabelOffset</td><td>RectangleInsets</td><td>Sets the offset for the simple labels and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.18,0.18,0.18,0.18,RELATIVE</code></td></tr>
<tr><td>simpleLabels</td><td>boolean</td><td>Sets the flag that controls whether simple or extended labels are
 displayed on the plot, and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>startAngle</td><td>double</td><td>Sets the starting angle and sends a {@link PlotChangeEvent} to all
 registered listeners.  The initial default value is 90 degrees, which
 corresponds to 12 o'clock.  A value of zero corresponds to 3 o'clock...
 this is the encoding used by Java's Arc2D class.</td><td><code>90.0</code></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies
 registered listeners that the plot has been modified.</td><td><code>1.0</code></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a
 {@link PlotChangeEvent} to all registered listeners.  Alignment options
 are defined by the {@link org.jfree.ui.Align} class in the JCommon
 class library.</td><td><code>15</code></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><code>0.5</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>4.0,8.0,4.0,8.0</code></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or
 <code>null</code>, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive
 {@link PlotChangeEvent} notifications.</td><td><code>true</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.  If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>#808080</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners. If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is
 drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="org.jfree.chart.plot.CategoryPlot"></a><h2>CategoryPlot</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A general plotting class that uses data from a {@link CategoryDataset} and
 renders each data item using a {@link CategoryItemRenderer}.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/CategoryPlot.html">org.jfree.chart.plot.CategoryPlot</a></b></td></tr>
<tr><td>anchorValue</td><td>double</td><td>Sets the anchor value and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>axisOffset</td><td>RectangleInsets</td><td>Sets the axis offsets (gap between the data area and the axes) and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.0,0.0,0.0,0.0</code></td></tr>
<tr><td>crosshairDatasetIndex</td><td>int</td><td>Sets the dataset index for the crosshair and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>0</code></td></tr>
<tr><td>domainCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the domain crosshair.</td><td><code>#0000ff</code></td></tr>
<tr><td>domainCrosshairStroke</td><td>Stroke</td><td>Sets the stroke used to draw the domain crosshair, and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>domainCrosshairVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain crosshair is
 displayed by the plot, and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>domainGridlinePaint</td><td>Paint</td><td>Sets the paint used to draw the grid-lines (if any) against the domain
 axis and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#c0c0c0</code></td></tr>
<tr><td>domainGridlineStroke</td><td>Stroke</td><td>Sets the stroke used to draw grid-lines against the domain axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>domainGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not grid-lines are drawn against
 the domain axis.
 <br/><br/>
 If the flag value changes, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>drawSharedDomainAxis</td><td>boolean</td><td>Sets the flag that controls whether the shared domain axis is drawn when
 this plot is being used as a subplot.</td><td><code>false</code></td></tr>
<tr><td>orientation</td><td>PlotOrientation</td><td>Sets the orientation for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>VERTICAL</code></td></tr>
<tr><td>rangeCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair should
 "lock-on" to actual data values, and sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>rangeCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the range crosshair (if visible) and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>rangeCrosshairStroke</td><td>Stroke</td><td>Sets the pen-style (<code>Stroke</code>) used to draw the range
 crosshair (if visible), and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeCrosshairValue</td><td>double</td><td>Sets the range crosshair value and, if the crosshair is visible, sends
 a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>rangeCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair is visible.</td><td><code>false</code></td></tr>
<tr><td>rangeGridlinePaint</td><td>Paint</td><td>Sets the paint used to draw the grid lines against the range axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#c0c0c0</code></td></tr>
<tr><td>rangeGridlineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the grid-lines against the range axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not grid-lines are drawn against
 the range axis.  If the flag changes value, a {@link PlotChangeEvent} is
 sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>rangeMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the range axis
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>rangeMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the range axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis minor grid
 lines are visible.
 <br/><br/>
 If the flag value is changed, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rangePannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along
 the range axes.</td><td><code>false</code></td></tr>
<tr><td>rangeZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the range axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>rangeZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the range axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>rangeZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is
 displayed for the range axis, and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>weight</td><td>int</td><td>Sets the weight for the plot and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0</code></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies
 registered listeners that the plot has been modified.</td><td><code>1.0</code></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a
 {@link PlotChangeEvent} to all registered listeners.  Alignment options
 are defined by the {@link org.jfree.ui.Align} class in the JCommon
 class library.</td><td><code>15</code></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><code>0.5</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>4.0,8.0,4.0,8.0</code></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or
 <code>null</code>, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive
 {@link PlotChangeEvent} notifications.</td><td><code>true</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.  If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>#808080</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners. If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is
 drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="org.jfree.chart.plot.PiePlot"></a><h2>PiePlot</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A plot that displays data in the form of a pie chart, using data from any
 class that implements the {@link PieDataset} interface.
 The example shown here is generated by the <code>PieChartDemo2.java</code>
 program included in the JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../images/PiePlotSample.png"
 alt="PiePlotSample.png" />
 <P>
 Special notes:
 <ol>
 <li>the default starting point is 12 o'clock and the pie sections proceed
 in a clockwise direction, but these settings can be changed;</li>
 <li>negative values in the dataset are ignored;</li>
 <li>there are utility methods for creating a {@link PieDataset} from a
 {@link org.jfree.data.category.CategoryDataset};</li>
 </ol></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/PiePlot.html">org.jfree.chart.plot.PiePlot</a></b></td></tr>
<tr><td>autoPopulateSectionOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline paint is
 auto-populated by the {@link #lookupSectionOutlinePaint(Comparable)}
 method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSectionOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the section outline stroke is
 auto-populated by the {@link #lookupSectionOutlineStroke(Comparable)}
 method, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSectionPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the section paint is
 auto-populated by the {@link #lookupSectionPaint(Comparable)} method,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSectionOutlinePaint</td><td>Paint</td><td>Sets the base section paint.</td><td><code>#808080</code></td></tr>
<tr><td>baseSectionOutlineStroke</td><td>Stroke</td><td>Sets the base section stroke.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>baseSectionPaint</td><td>Paint</td><td>Sets the base section paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>circular</td><td>boolean</td><td>A flag indicating whether the pie chart is circular, or stretched into
 an elliptical shape.</td><td><code>true</code></td></tr>
<tr><td>ignoreNullValues</td><td>boolean</td><td>Sets a flag that controls whether <code>null</code> values are ignored,
 and sends a {@link PlotChangeEvent} to all registered listeners.  At
 present, this only affects whether or not the key is presented in the
 legend.</td><td><code>false</code></td></tr>
<tr><td>ignoreZeroValues</td><td>boolean</td><td>Sets a flag that controls whether zero values are ignored,
 and sends a {@link PlotChangeEvent} to all registered listeners.  This
 only affects whether or not a label appears for the non-visible
 pie section.</td><td><code>false</code></td></tr>
<tr><td>interiorGap</td><td>double</td><td>Sets the interior gap and sends a {@link PlotChangeEvent} to all
 registered listeners.  This controls the space between the edges of the
 pie plot and the plot area itself (the region where the section labels
 appear).</td><td><code>0.08</code></td></tr>
<tr><td>labelBackgroundPaint</td><td>Paint</td><td>Sets the section label background paint and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffc0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the section label font and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>labelGap</td><td>double</td><td>Sets the gap between the edge of the pie and the labels (expressed as a
 percentage of the plot width) and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0.025</code></td></tr>
<tr><td>labelLinkMargin</td><td>double</td><td>Sets the link margin and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>0.025</code></td></tr>
<tr><td>labelLinkPaint</td><td>Paint</td><td>Sets the paint used for the lines that connect pie sections to their
 corresponding labels, and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelLinkStroke</td><td>Stroke</td><td>Sets the link stroke and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>labelLinksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not label linking lines are
 visible and sends a {@link PlotChangeEvent} to all registered listeners.
 Please take care when hiding the linking lines - depending on the data
 values, the labels can be displayed some distance away from the
 corresponding pie section.</td><td><code>true</code></td></tr>
<tr><td>labelOutlinePaint</td><td>Paint</td><td>Sets the section label outline paint and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelOutlineStroke</td><td>Stroke</td><td>Sets the section label outline stroke and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>labelPadding</td><td>RectangleInsets</td><td>Sets the padding between each label and its outline and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>2.0,2.0,2.0,2.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the section label paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>labelShadowPaint</td><td>Paint</td><td>Sets the section label shadow paint and sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>#979797</code></td></tr>
<tr><td>legendItemShape</td><td>Shape</td><td>Sets the shape used for legend items and sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>maximumLabelWidth</td><td>double</td><td>Sets the maximum label width as a percentage of the plot width and sends
 a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.14</code></td></tr>
<tr><td>minimumArcAngleToDraw</td><td>double</td><td>Sets the minimum arc angle that will be drawn.  Pie sections for an
 angle smaller than this are not drawn, to avoid a JDK bug.  See this
 link for details:
 <br><br>
 <a href="http://www.jfree.org/phpBB2/viewtopic.php?t=2707">
 http://www.jfree.org/phpBB2/viewtopic.php?t=2707</a>
 <br><br>
 ...and this bug report in the Java Bug Parade:
 <br><br>
 <a href=
 "http://developer.java.sun.com/developer/bugParade/bugs/4836495.html">
 http://developer.java.sun.com/developer/bugParade/bugs/4836495.html</a></td><td><code>1.0E-5</code></td></tr>
<tr><td>pieIndex</td><td>int</td><td>Sets the pie index (this is used by the {@link MultiplePiePlot} class to
 track subplots).</td><td><code>0</code></td></tr>
<tr><td>sectionOutlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the outline is drawn for
 each pie section, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the shadow effect and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the shadow effect and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>simpleLabelOffset</td><td>RectangleInsets</td><td>Sets the offset for the simple labels and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.18,0.18,0.18,0.18,RELATIVE</code></td></tr>
<tr><td>simpleLabels</td><td>boolean</td><td>Sets the flag that controls whether simple or extended labels are
 displayed on the plot, and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>startAngle</td><td>double</td><td>Sets the starting angle and sends a {@link PlotChangeEvent} to all
 registered listeners.  The initial default value is 90 degrees, which
 corresponds to 12 o'clock.  A value of zero corresponds to 3 o'clock...
 this is the encoding used by Java's Arc2D class.</td><td><code>90.0</code></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies
 registered listeners that the plot has been modified.</td><td><code>1.0</code></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a
 {@link PlotChangeEvent} to all registered listeners.  Alignment options
 are defined by the {@link org.jfree.ui.Align} class in the JCommon
 class library.</td><td><code>15</code></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><code>0.5</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>4.0,8.0,4.0,8.0</code></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or
 <code>null</code>, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive
 {@link PlotChangeEvent} notifications.</td><td><code>true</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.  If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>#808080</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners. If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is
 drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="org.jfree.chart.plot.XYPlot"></a><h2>XYPlot</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A general class for plotting data in the form of (x, y) pairs.  This plot can
 use data from any class that implements the {@link XYDataset} interface.
 <P>
 <code>XYPlot</code> makes use of an {@link XYItemRenderer} to draw each point
 on the plot.  By using different renderers, various chart types can be
 produced.
 <p>
 The {@link org.jfree.chart.ChartFactory} class contains static methods for
 creating pre-configured charts.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/plot/XYPlot.html">org.jfree.chart.plot.XYPlot</a></b></td></tr>
<tr><td>axisOffset</td><td>RectangleInsets</td><td>Sets the axis offsets (gap between the data area and the axes) and sends
 a {@link PlotChangeEvent} to all registered listeners.</td><td><code>0.0,0.0,0.0,0.0</code></td></tr>
<tr><td>domainCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the domain crosshair should
 "lock-on" to actual data values.  If the flag value changes, this
 method sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>domainCrosshairPaint</td><td>Paint</td><td>Sets the paint used to draw the crosshairs (if visible) and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>domainCrosshairStroke</td><td>Stroke</td><td>Sets the Stroke used to draw the crosshairs (if visible) and notifies
 registered listeners that the axis has been modified.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>domainCrosshairValue</td><td>double</td><td>Sets the domain crosshair value and sends a {@link PlotChangeEvent} to
 all registered listeners (provided that the domain crosshair is visible).</td><td><code>0.0</code></td></tr>
<tr><td>domainCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the domain crosshair is visible
 and, if the flag changes, sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>domainGridlinePaint</td><td>Paint</td><td>Sets the paint for the grid lines plotted against the domain axis, and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#c0c0c0</code></td></tr>
<tr><td>domainGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the grid lines plotted against the domain axis, and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>domainGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain grid-lines are
 visible.
 <br/><br/>
 If the flag value is changed, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>true</code></td></tr>
<tr><td>domainMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the domain axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>domainMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the domain
 axis, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>domainMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the domain minor grid-lines
 are visible.
 <br/><br/>
 If the flag value is changed, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>domainPannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along the
 domain axes.</td><td><code>false</code></td></tr>
<tr><td>domainTickBandPaint</td><td>Paint</td><td>Sets the paint for the domain tick bands.</td><td><code>&nbsp;</code></td></tr>
<tr><td>domainZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the domain axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>domainZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the domain axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>domainZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is
 displayed for the domain axis, and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>orientation</td><td>PlotOrientation</td><td>Sets the orientation for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>VERTICAL</code></td></tr>
<tr><td>rangeCrosshairLockedOnData</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair should
 "lock-on" to actual data values.  If the flag value changes, this method
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>rangeCrosshairPaint</td><td>Paint</td><td>Sets the paint used to color the crosshairs (if visible) and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>rangeCrosshairStroke</td><td>Stroke</td><td>Sets the stroke used to draw the crosshairs (if visible) and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeCrosshairValue</td><td>double</td><td>Sets the range crosshair value.
 <br/><br/>
 Registered listeners are notified that the plot has been modified, but
 only if the crosshair is visible.</td><td><code>0.0</code></td></tr>
<tr><td>rangeCrosshairVisible</td><td>boolean</td><td>Sets the flag indicating whether or not the range crosshair is visible.
 If the flag value changes, this method sends a {@link PlotChangeEvent}
 to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rangeGridlinePaint</td><td>Paint</td><td>Sets the paint for the grid lines plotted against the range axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#c0c0c0</code></td></tr>
<tr><td>rangeGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the grid lines plotted against the range axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis grid lines
 are visible.
 <br/><br/>
 If the flag value is changed, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>true</code></td></tr>
<tr><td>rangeMinorGridlinePaint</td><td>Paint</td><td>Sets the paint for the minor grid lines plotted against the range axis
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>rangeMinorGridlineStroke</td><td>Stroke</td><td>Sets the stroke for the minor grid lines plotted against the range axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=1</code></td></tr>
<tr><td>rangeMinorGridlinesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the range axis minor grid
 lines are visible.
 <br/><br/>
 If the flag value is changed, a {@link PlotChangeEvent} is sent to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rangePannable</td><td>boolean</td><td>Sets the flag that enables or disables panning of the plot along
 the range axes.</td><td><code>false</code></td></tr>
<tr><td>rangeTickBandPaint</td><td>Paint</td><td>Sets the paint for the range tick bands.</td><td><code>&nbsp;</code></td></tr>
<tr><td>rangeZeroBaselinePaint</td><td>Paint</td><td>Sets the paint for the zero baseline plotted against the range axis and
 sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>rangeZeroBaselineStroke</td><td>Stroke</td><td>Sets the stroke for the zero baseline for the range axis,
 and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>rangeZeroBaselineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the zero baseline is
 displayed for the range axis, and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>weight</td><td>int</td><td>Sets the weight for the plot and sends a {@link PlotChangeEvent} to all
 registered listeners.</td><td><code>1</code></td></tr>
<tr><td>backgroundAlpha</td><td>float</td><td>Sets the alpha transparency of the plot area background, and notifies
 registered listeners that the plot has been modified.</td><td><code>1.0</code></td></tr>
<tr><td>backgroundImage</td><td>Image</td><td>Sets the background image for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>backgroundImageAlignment</td><td>int</td><td>Sets the alignment for the background image and sends a
 {@link PlotChangeEvent} to all registered listeners.  Alignment options
 are defined by the {@link org.jfree.ui.Align} class in the JCommon
 class library.</td><td><code>15</code></td></tr>
<tr><td>backgroundImageAlpha</td><td>float</td><td>Sets the alpha transparency used when drawing the background image.</td><td><code>0.5</code></td></tr>
<tr><td>backgroundPaint</td><td>Paint</td><td>Sets the background color of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>foregroundAlpha</td><td>float</td><td>Sets the alpha-transparency for the plot and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>insets</td><td>RectangleInsets</td><td>Sets the insets for the plot and sends a {@link PlotChangeEvent} to
 all registered listeners.</td><td><code>4.0,8.0,4.0,8.0</code></td></tr>
<tr><td>noDataMessage</td><td>String</td><td>Sets the message that is displayed when the dataset is empty or
 <code>null</code>, and sends a {@link PlotChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>noDataMessageFont</td><td>Font</td><td>Sets the font used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>noDataMessagePaint</td><td>Paint</td><td>Sets the paint used to display the 'no data' message and sends a
 {@link PlotChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>notify</td><td>boolean</td><td>Sets a flag that controls whether or not listeners receive
 {@link PlotChangeEvent} notifications.</td><td><code>true</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the paint used to draw the outline of the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners.  If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>#808080</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the stroke used to outline the plot area and sends a
 {@link PlotChangeEvent} to all registered listeners. If you set this
 attribute to <code>null</code>, no outline will be drawn.</td><td><code>line=0.5|dash=0</code></td></tr>
<tr><td>outlineVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the plot's outline is
 drawn, and sends a {@link PlotChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="axis"></a><h1>Axis Types</h1>
<a name="number"></a><h2>NumberAxis</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>An axis for displaying numerical data.
 <P>
 If the axis is set up to automatically determine its range to fit the data,
 you can ensure that the range includes zero (statisticians usually prefer
 this) by setting the <code>autoRangeIncludesZero</code> flag to
 <code>true</code>.
 <P>
 The <code>NumberAxis</code> class has a mechanism for automatically
 selecting a tick unit that is appropriate for the current axis range.  This
 mechanism is an adaptation of code suggested by Laurence Vanhelsuwe.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/NumberAxis.html">org.jfree.chart.axis.NumberAxis</a></b></td></tr>
<tr><td>autoRangeIncludesZero</td><td>boolean</td><td>Sets the flag that indicates whether or not the axis range, if
 automatically calculated, is forced to include zero.
 <br/><br/>
 If the flag is changed to <code>true</code>, the axis range is
 recalculated.
 <br/><br/>
 Any change to the flag will trigger an {@link AxisChangeEvent}.</td><td><code>true</code></td></tr>
<tr><td>autoRangeStickyZero</td><td>boolean</td><td>Sets a flag that affects the auto-range when zero falls outside the data
 range but inside the margins defined for the axis.</td><td><code>true</code></td></tr>
<tr><td>numberFormatOverride</td><td>NumberFormat</td><td>Sets the number format override.  If this is non-null, then it will be
 used to format the numbers on the axis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>tickUnit</td><td>NumberTickUnit</td><td>Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to
 all registered listeners.  A side effect of calling this method is that
 the "auto-select" feature for tick units is switched off (you can
 restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}
 method).</td><td><code>1.0</code></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is
 automatically adjusted to fit the data, and notifies registered
 listeners that the axis has been modified.</td><td><code>true</code></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically
 selected from a range of standard tick units.  If the flag is changed,
 registered listeners are notified that the chart has changed.</td><td><code>true</code></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><code>0.0</code></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and
 notifies registered listeners that the axis has changed.</td><td><code>false</code></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the
 end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is
 sent to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>0</code></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the negative direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the positive direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed
 vertically (that is, rotated 90 degrees from horizontal).  If the flag
 is changed, an {@link AxisChangeEvent} is sent to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.
 <br/><br/>
 This is used when combining more than one plot on a chart.  In this case,
 there may be several axes that need to have the same height or width so
 that they are aligned.  This method is used to fix a dimension for the
 axis (the context determines whether the dimension is horizontal or
 vertical).</td><td><code>0.0</code></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>3.0,3.0,3.0,3.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0,4.0,2.0,4.0</code></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are
 visible and sends an {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="period"></a><h2>PeriodAxis</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>An axis that displays a date scale based on a
 {@link org.jfree.data.time.RegularTimePeriod}.  This axis works when
 displayed across the bottom or top of a plot, but is broken for display at
 the left or right of charts.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/PeriodAxis.html">org.jfree.chart.axis.PeriodAxis</a></b></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>minorTickMarkPaint</td><td>Paint</td><td>Sets the paint used to display minor tick marks, if they are
 visible, and sends a {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>minorTickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to display minor tick marks, if they are
 visible, and sends a {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that controls whether or not minor tick marks
 are displayed for the axis, and sends a {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is
 automatically adjusted to fit the data, and notifies registered
 listeners that the axis has been modified.</td><td><code>true</code></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically
 selected from a range of standard tick units.  If the flag is changed,
 registered listeners are notified that the chart has changed.</td><td><code>true</code></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><code>0.0</code></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and
 notifies registered listeners that the axis has changed.</td><td><code>false</code></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the
 end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is
 sent to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>0</code></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the negative direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the positive direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed
 vertically (that is, rotated 90 degrees from horizontal).  If the flag
 is changed, an {@link AxisChangeEvent} is sent to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.
 <br/><br/>
 This is used when combining more than one plot on a chart.  In this case,
 there may be several axes that need to have the same height or width so
 that they are aligned.  This method is used to fix a dimension for the
 axis (the context determines whether the dimension is horizontal or
 vertical).</td><td><code>0.0</code></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>3.0,3.0,3.0,3.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0,4.0,2.0,4.0</code></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are
 visible and sends an {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="date"></a><h2>DateAxis</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>The base class for axes that display dates.  You will find it easier to
 understand how this axis works if you bear in mind that it really
 displays/measures integer (or long) data, where the integers are
 milliseconds since midnight, 1-Jan-1970.  When displaying tick labels, the
 millisecond values are converted back to dates using a
 <code>DateFormat</code> instance.
 <P>
 You can also create a {@link org.jfree.chart.axis.Timeline} and supply in
 the constructor to create an axis that only contains certain domain values.
 For example, this allows you to create a date axis that only contains
 working days.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/DateAxis.html">org.jfree.chart.axis.DateAxis</a></b></td></tr>
<tr><td>dateFormatOverride</td><td>DateFormat</td><td>Sets the date format override.  If this is non-null, then it will be
 used to format the dates on the axis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>maximumDate</td><td>Date</td><td>Sets the maximum date visible on the axis and sends an
 {@link AxisChangeEvent} to all registered listeners.  If
 <code>maximumDate</code> is on or before the current minimum date for
 the axis, the minimum date will be shifted to preserve the current
 length of the axis.</td><td><code>1969-12-31</code></td></tr>
<tr><td>minimumDate</td><td>Date</td><td>Sets the minimum date visible on the axis and sends an
 {@link AxisChangeEvent} to all registered listeners.  If
 <code>date</code> is on or after the current maximum date for
 the axis, the maximum date will be shifted to preserve the current
 length of the axis.</td><td><code>1969-12-31</code></td></tr>
<tr><td>tickMarkPosition</td><td>DateTickMarkPosition</td><td>Sets the tick mark position (start, middle or end of the time period)
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>START</code></td></tr>
<tr><td>tickUnit</td><td>DateTickUnit</td><td>Sets the tick unit for the axis.  The auto-tick-unit-selection flag is
 set to <code>false</code>, and registered listeners are notified that
 the axis has been changed.</td><td><code>&nbsp;</code></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is
 automatically adjusted to fit the data, and notifies registered
 listeners that the axis has been modified.</td><td><code>true</code></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically
 selected from a range of standard tick units.  If the flag is changed,
 registered listeners are notified that the chart has changed.</td><td><code>true</code></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><code>0.0</code></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and
 notifies registered listeners that the axis has changed.</td><td><code>false</code></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the
 end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is
 sent to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>0</code></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the negative direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the positive direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed
 vertically (that is, rotated 90 degrees from horizontal).  If the flag
 is changed, an {@link AxisChangeEvent} is sent to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.
 <br/><br/>
 This is used when combining more than one plot on a chart.  In this case,
 there may be several axes that need to have the same height or width so
 that they are aligned.  This method is used to fix a dimension for the
 axis (the context determines whether the dimension is horizontal or
 vertical).</td><td><code>0.0</code></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>3.0,3.0,3.0,3.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0,4.0,2.0,4.0</code></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are
 visible and sends an {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="log"></a><h2>LogarithmicAxis</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A numerical axis that uses a logarithmic scale.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/LogarithmicAxis.html">org.jfree.chart.axis.LogarithmicAxis</a></b></td></tr>
<tr><td>allowNegativesFlag</td><td>boolean</td><td>Sets the 'allowNegativesFlag' flag; true to allow negative values
 in data, false to be able to plot positive values arbitrarily close to
 zero.</td><td><code>false</code></td></tr>
<tr><td>autoRangeNextLogFlag</td><td>boolean</td><td>Sets the 'autoRangeNextLogFlag' flag.  This determines whether or
 not the 'autoAdjustRange()' method will select the next "10^n"
 values when determining the upper and lower bounds.  The default
 value is false.</td><td><code>false</code></td></tr>
<tr><td>expTickLabelsFlag</td><td>boolean</td><td>Sets the 'expTickLabelsFlag' flag.  If the 'log10TickLabelsFlag'
 is false then this will set whether or not "1e#"-style tick labels
 are used.  The default is to use regular numeric tick labels.</td><td><code>false</code></td></tr>
<tr><td>log10TickLabelsFlag</td><td>boolean</td><td>Sets the 'log10TickLabelsFlag' flag.  The default value is false.</td><td><code>false</code></td></tr>
<tr><td>strictValuesFlag</td><td>boolean</td><td>Sets the 'strictValuesFlag' flag; if true and 'allowNegativesFlag'
 is false then this axis will throw a runtime exception if any of its
 values are less than or equal to zero; if false then the axis will
 adjust for values less than or equal to zero as needed.</td><td><code>true</code></td></tr>
<tr><td>autoRangeIncludesZero</td><td>boolean</td><td>Sets the flag that indicates whether or not the axis range, if
 automatically calculated, is forced to include zero.
 <br/><br/>
 If the flag is changed to <code>true</code>, the axis range is
 recalculated.
 <br/><br/>
 Any change to the flag will trigger an {@link AxisChangeEvent}.</td><td><code>true</code></td></tr>
<tr><td>autoRangeStickyZero</td><td>boolean</td><td>Sets a flag that affects the auto-range when zero falls outside the data
 range but inside the margins defined for the axis.</td><td><code>true</code></td></tr>
<tr><td>numberFormatOverride</td><td>NumberFormat</td><td>Sets the number format override.  If this is non-null, then it will be
 used to format the numbers on the axis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>tickUnit</td><td>NumberTickUnit</td><td>Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to
 all registered listeners.  A side effect of calling this method is that
 the "auto-select" feature for tick units is switched off (you can
 restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}
 method).</td><td><code>1.0</code></td></tr>
<tr><td>autoRange</td><td>boolean</td><td>Sets a flag that determines whether or not the axis range is
 automatically adjusted to fit the data, and notifies registered
 listeners that the axis has been modified.</td><td><code>true</code></td></tr>
<tr><td>autoRangeMinimumSize</td><td>double</td><td>Sets the auto range minimum size and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>autoTickUnitSelection</td><td>boolean</td><td>Sets a flag indicating whether or not the tick unit is automatically
 selected from a range of standard tick units.  If the flag is changed,
 registered listeners are notified that the chart has changed.</td><td><code>true</code></td></tr>
<tr><td>downArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing downwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>fixedAutoRange</td><td>double</td><td>Sets the fixed auto range for the axis.</td><td><code>0.0</code></td></tr>
<tr><td>inverted</td><td>boolean</td><td>Sets a flag that controls the direction of values on the axis, and
 notifies registered listeners that the axis has changed.</td><td><code>false</code></td></tr>
<tr><td>leftArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing left at the
 end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>lowerBound</td><td>double</td><td>Sets the lower bound for the axis range.  An {@link AxisChangeEvent} is
 sent to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>lowerMargin</td><td>double</td><td>Sets the lower margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>minorTickCount</td><td>int</td><td>Sets the number of minor tick marks to display, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>0</code></td></tr>
<tr><td>negativeArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the negative direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>positiveArrowVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis lines has an arrow
 drawn that points in the positive direction for the axis, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>rightArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing rightwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upArrow</td><td>Shape</td><td>Sets the shape that can be displayed as an arrow pointing upwards at
 the end of an axis line and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>upperBound</td><td>double</td><td>Sets the upper bound for the axis range, and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>upperMargin</td><td>double</td><td>Sets the upper margin for the axis (as a percentage of the axis range)
 and sends an {@link AxisChangeEvent} to all registered listeners.  This
 margin is added only when the axis range is auto-calculated - if you set
 the axis range manually, the margin is ignored.</td><td><code>0.05</code></td></tr>
<tr><td>verticalTickLabels</td><td>boolean</td><td>Sets the flag that controls whether the tick labels are displayed
 vertically (that is, rotated 90 degrees from horizontal).  If the flag
 is changed, an {@link AxisChangeEvent} is sent to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>axisLinePaint</td><td>Paint</td><td>Sets the paint used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>axisLineStroke</td><td>Stroke</td><td>Sets the stroke used to draw the axis line and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>axisLineVisible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis line is visible and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>fixedDimension</td><td>double</td><td>Sets the fixed dimension for the axis.
 <br/><br/>
 This is used when combining more than one plot on a chart.  In this case,
 there may be several axes that need to have the same height or width so
 that they are aligned.  This method is used to fix a dimension for the
 axis (the context determines whether the dimension is horizontal or
 vertical).</td><td><code>0.0</code></td></tr>
<tr><td>label</td><td>String</td><td>Sets the label for the axis and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>labelAngle</td><td>double</td><td>Sets the angle for the label and sends an {@link AxisChangeEvent} to all
 registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>labelFont</td><td>Font</td><td>Sets the font for the axis label and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-12</code></td></tr>
<tr><td>labelInsets</td><td>RectangleInsets</td><td>Sets the insets for the axis label, and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>3.0,3.0,3.0,3.0</code></td></tr>
<tr><td>labelPaint</td><td>Paint</td><td>Sets the paint used to draw the axis label and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>minorTickMarkInsideLength</td><td>float</td><td>Sets the inside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>minorTickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the minor tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>minorTickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the minor tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>tickLabelFont</td><td>Font</td><td>Sets the font for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>tickLabelInsets</td><td>RectangleInsets</td><td>Sets the insets for the tick labels and sends an {@link AxisChangeEvent}
 to all registered listeners.</td><td><code>2.0,4.0,2.0,4.0</code></td></tr>
<tr><td>tickLabelPaint</td><td>Paint</td><td>Sets the paint used to draw tick labels (if they are showing) and
 sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>tickLabelsVisible</td><td>boolean</td><td>Sets the flag that determines whether or not the tick labels are
 visible and sends an {@link AxisChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>tickMarkInsideLength</td><td>float</td><td>Sets the inside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>tickMarkOutsideLength</td><td>float</td><td>Sets the outside length of the tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>2.0</code></td></tr>
<tr><td>tickMarkPaint</td><td>Paint</td><td>Sets the paint used to draw tick marks and sends an
 {@link AxisChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>tickMarkStroke</td><td>Stroke</td><td>Sets the stroke used to draw tick marks and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>tickMarksVisible</td><td>boolean</td><td>Sets the flag that indicates whether or not the tick marks are showing
 and sends an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>visible</td><td>boolean</td><td>Sets a flag that controls whether or not the axis is visible and sends
 an {@link AxisChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
</table>
<a name="xyrenderers"></a><h1>XY Graph Types</h1>
<a name="org.jfree.chart.renderer.xy.XYAreaRenderer2"></a><h2>Area</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>Area item renderer for an {@link XYPlot}. The example shown here is
 generated by the <code>XYAreaRenderer2Demo1.java</code> program included in
 the JFreeChart demo collection:
 <br><br>
 <img src="../../../../../images/XYAreaRenderer2Sample.png"
 alt="XYAreaRenderer2Sample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYAreaRenderer2.html">org.jfree.chart.renderer.xy.XYAreaRenderer2</a></b></td></tr>
<tr><td>legendArea</td><td>Shape</td><td>Sets the shape used as an area in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outline</td><td>boolean</td><td>Sets a flag that controls whether or not outlines of the areas are
 drawn, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYBarRenderer"></a><h2>Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that draws bars on an {@link XYPlot} (requires an
 {@link IntervalXYDataset}).  The example shown here is generated by the
 <code>XYBarChartDemo1.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYBarRendererSample.png"
 alt="XYBarRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYBarRenderer.html">org.jfree.chart.renderer.xy.XYBarRenderer</a></b></td></tr>
<tr><td>barAlignmentFactor</td><td>double</td><td>Sets the bar alignment factor and sends a {@link RendererChangeEvent}
 to all registered listeners.  If the alignment factor is outside the
 range 0.0 to 1.0, no alignment will be performed by the renderer.</td><td><code>-1.0</code></td></tr>
<tr><td>barPainter</td><td>XYBarPainter</td><td>Sets the bar painter and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.  The base value is not used if the dataset's
 y-interval is being used to determine the bar length.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>legendBar</td><td>Shape</td><td>Sets the shape used to represent bars in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>margin</td><td>double</td><td>Sets the percentage amount by which the bars are trimmed and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the renderer
 draws shadows for the bars, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>useYInterval</td><td>boolean</td><td>Sets the flag that determines whether the y-interval from the dataset is
 used to calculate the length of each bar, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYBubbleRenderer"></a><h2>Bubble</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYBubbleRenderer.html">org.jfree.chart.renderer.xy.XYBubbleRenderer</a></b></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.CandlestickRenderer"></a><h2>Candlestick</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that draws candlesticks on an {@link XYPlot} (requires a
 {@link OHLCDataset}).  The example shown here is generated
 by the <code>CandlestickChartDemo1.java</code> program included in the
 JFreeChart demo collection:
 <br><br>
 <img src="../../../../../images/CandlestickRendererSample.png"
 alt="CandlestickRendererSample.png" />
 <P>
 This renderer does not include code to calculate the crosshair point for the
 plot.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/CandlestickRenderer.html">org.jfree.chart.renderer.xy.CandlestickRenderer</a></b></td></tr>
<tr><td>autoWidthFactor</td><td>double</td><td>Sets the factor by which the available space automatically calculated
 for the candles will be multiplied to determine the actual width to use.</td><td><code>0.6428571428571429</code></td></tr>
<tr><td>autoWidthGap</td><td>double</td><td>Sets the amount of space to leave on the left and right of each candle
 when automatically calculating widths and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>autoWidthMethod</td><td>int</td><td>Sets the method of automatically calculating the candle width and
 sends a {@link RendererChangeEvent} to all registered listeners.
 <br/><br/>
 <code>WIDTHMETHOD_AVERAGE</code>: Divides the entire display (ignoring
 scale factor) by the number of items, and uses this as the available
 width.<br>
 <code>WIDTHMETHOD_SMALLEST</code>: Checks the interval between each
 item, and uses the smallest as the available width.<br>
 <code>WIDTHMETHOD_INTERVALDATA</code>: Assumes that the dataset supports
 the IntervalXYDataset interface, and uses the startXValue - endXValue as
 the available width.
 <br></td><td><code>0</code></td></tr>
<tr><td>candleWidth</td><td>double</td><td>Sets the candle width and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 If you set the width to a negative value, the renderer will calculate
 the candle width automatically based on the space available on the chart.</td><td><code>-1.0</code></td></tr>
<tr><td>downPaint</td><td>Paint</td><td>Sets the paint used to fill candles when the price moves down from open
 to close and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>#ff0000</code></td></tr>
<tr><td>drawVolume</td><td>boolean</td><td>Sets a flag that controls whether or not volume bars are drawn in the
 background and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>maxCandleWidthInMilliseconds</td><td>double</td><td>Sets the maximum candle width (in milliseconds) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>7.2E7</code></td></tr>
<tr><td>upPaint</td><td>Paint</td><td>Sets the paint used to fill candles when the price moves up from open
 to close and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>#00ff00</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the renderer's outline
 paint is used to draw the candlestick outline, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>volumePaint</td><td>Paint</td><td>Sets the paint used to fill the volume bars, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.ClusteredXYBarRenderer"></a><h2>Clustered Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/ClusteredXYBarRenderer.html">org.jfree.chart.renderer.xy.ClusteredXYBarRenderer</a></b></td></tr>
<tr><td>barAlignmentFactor</td><td>double</td><td>Sets the bar alignment factor and sends a {@link RendererChangeEvent}
 to all registered listeners.  If the alignment factor is outside the
 range 0.0 to 1.0, no alignment will be performed by the renderer.</td><td><code>-1.0</code></td></tr>
<tr><td>barPainter</td><td>XYBarPainter</td><td>Sets the bar painter and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.  The base value is not used if the dataset's
 y-interval is being used to determine the bar length.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>legendBar</td><td>Shape</td><td>Sets the shape used to represent bars in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>margin</td><td>double</td><td>Sets the percentage amount by which the bars are trimmed and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the renderer
 draws shadows for the bars, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>useYInterval</td><td>boolean</td><td>Sets the flag that determines whether the y-interval from the dataset is
 used to calculate the length of each bar, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYDifferenceRenderer"></a><h2>Difference</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer for an {@link XYPlot} that highlights the differences between two
 series.  The example shown here is generated by the
 <code>DifferenceChartDemo1.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYDifferenceRendererSample.png"
 alt="XYDifferenceRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYDifferenceRenderer.html">org.jfree.chart.renderer.xy.XYDifferenceRenderer</a></b></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>negativePaint</td><td>Paint</td><td>Sets the paint used to highlight negative differences.</td><td><code>#ff0000</code></td></tr>
<tr><td>positivePaint</td><td>Paint</td><td>Sets the paint used to highlight positive differences and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>#00ff00</code></td></tr>
<tr><td>roundXCoordinates</td><td>boolean</td><td>Sets the flag that controls whether or not the x-coordinates (in
 Java2D space) are rounded to integer values, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>shapesVisible</td><td>boolean</td><td>Sets a flag that controls whether or not shapes are drawn for each
 data value, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYDotRenderer"></a><h2>Dot</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that draws a small dot at each data point for an {@link XYPlot}.
 The example shown here is generated by the
 <code>ScatterPlotDemo4.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYDotRendererSample.png"
 alt="XYDotRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYDotRenderer.html">org.jfree.chart.renderer.xy.XYDotRenderer</a></b></td></tr>
<tr><td>dotHeight</td><td>int</td><td>Sets the dot height and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>1</code></td></tr>
<tr><td>dotWidth</td><td>int</td><td>Sets the dot width and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>1</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.DefaultXYItemRenderer"></a><h2>Line</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/DefaultXYItemRenderer.html">org.jfree.chart.renderer.xy.DefaultXYItemRenderer</a></b></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>drawSeriesLineAsPath</td><td>boolean</td><td>Sets the flag that controls whether or not each series is drawn as a
 single path and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used to draw
 shape outlines, and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 Refer to <code>XYLineAndShapeRendererDemo2.java</code> to see the
 effect of this flag.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYLine3DRenderer"></a><h2>Line 3D</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A XYLineAndShapeRenderer that adds a shadow line to the graph
 to emulate a 3D-effect.</p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYLine3DRenderer.html">org.jfree.chart.renderer.xy.XYLine3DRenderer</a></b></td></tr>
<tr><td>XOffset</td><td>double</td><td>Sets the x-offset and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>12.0</code></td></tr>
<tr><td>YOffset</td><td>double</td><td>Sets the y-offset and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>8.0</code></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot
 background and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>#dddddd</code></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>drawSeriesLineAsPath</td><td>boolean</td><td>Sets the flag that controls whether or not each series is drawn as a
 single path and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used to draw
 shape outlines, and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 Refer to <code>XYLineAndShapeRendererDemo2.java</code> to see the
 effect of this flag.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYLineAndShapeRenderer"></a><h2>Line And Shape</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that connects data points with lines and/or draws shapes at each
 data point.  This renderer is designed for use with the {@link XYPlot}
 class.  The example shown here is generated by
 the <code>XYLineAndShapeRendererDemo2.java</code> program included in the
 JFreeChart demo collection:
 <br><br>
 <img src="../../../../../images/XYLineAndShapeRendererSample.png"
 alt="XYLineAndShapeRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYLineAndShapeRenderer.html">org.jfree.chart.renderer.xy.XYLineAndShapeRenderer</a></b></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>drawSeriesLineAsPath</td><td>boolean</td><td>Sets the flag that controls whether or not each series is drawn as a
 single path and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used to draw
 shape outlines, and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 Refer to <code>XYLineAndShapeRendererDemo2.java</code> to see the
 effect of this flag.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYSplineRenderer"></a><h2>Spline</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that connects data points with natural cubic splines and/or
 draws shapes at each data point.  This renderer is designed for use with
 the {@link XYPlot} class. The example shown here is generated by the
 <code>XYSplineRendererDemo1.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYSplineRendererSample.png"
 alt="XYSplineRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYSplineRenderer.html">org.jfree.chart.renderer.xy.XYSplineRenderer</a></b></td></tr>
<tr><td>precision</td><td>int</td><td>Set the resolution of splines and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>5</code></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>drawSeriesLineAsPath</td><td>boolean</td><td>Sets the flag that controls whether or not each series is drawn as a
 single path and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used to draw
 shape outlines, and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 Refer to <code>XYLineAndShapeRendererDemo2.java</code> to see the
 effect of this flag.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.StackedXYAreaRenderer2"></a><h2>Stacked Area</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A stacked area renderer for the {@link XYPlot} class.
 The example shown here is generated by the
 <code>StackedXYAreaChartDemo2.java</code> program included in the
 JFreeChart demo collection:
 <br><br>
 <img src="../../../../../images/StackedXYAreaRenderer2Sample.png"
 alt="StackedXYAreaRenderer2Sample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/StackedXYAreaRenderer2.html">org.jfree.chart.renderer.xy.StackedXYAreaRenderer2</a></b></td></tr>
<tr><td>roundXCoordinates</td><td>boolean</td><td>Sets the flag that controls whether or not the x-coordinates (in
 Java2D space) are rounded to integer values, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>legendArea</td><td>Shape</td><td>Sets the shape used as an area in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outline</td><td>boolean</td><td>Sets a flag that controls whether or not outlines of the areas are
 drawn, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.StackedXYBarRenderer"></a><h2>Stacked Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A bar renderer that displays the series items stacked.
 The dataset used together with this renderer must be a
 {@link org.jfree.data.xy.IntervalXYDataset} and a
 {@link org.jfree.data.xy.TableXYDataset}. For example, the
 dataset class {@link org.jfree.data.xy.CategoryTableXYDataset}
 implements both interfaces.

 The example shown here is generated by the
 <code>StackedXYBarChartDemo2.java</code> program included in the
 JFreeChart demo collection:
 <br><br>
 <img src="../../../../../images/StackedXYBarRendererSample.png"
 alt="StackedXYBarRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/StackedXYBarRenderer.html">org.jfree.chart.renderer.xy.StackedXYBarRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item
 value as a percentage (so that the stacked bars add to 100%), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>barAlignmentFactor</td><td>double</td><td>Sets the bar alignment factor and sends a {@link RendererChangeEvent}
 to all registered listeners.  If the alignment factor is outside the
 range 0.0 to 1.0, no alignment will be performed by the renderer.</td><td><code>-1.0</code></td></tr>
<tr><td>barPainter</td><td>XYBarPainter</td><td>Sets the bar painter and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.  The base value is not used if the dataset's
 y-interval is being used to determine the bar length.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>legendBar</td><td>Shape</td><td>Sets the shape used to represent bars in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>margin</td><td>double</td><td>Sets the percentage amount by which the bars are trimmed and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not the renderer
 draws shadows for the bars, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>useYInterval</td><td>boolean</td><td>Sets the flag that determines whether the y-interval from the dataset is
 used to calculate the length of each bar, and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYStepRenderer"></a><h2>Step</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>Line/Step item renderer for an {@link XYPlot}.  This class draws lines
 between data points, only allowing horizontal or vertical lines (steps).
 The example shown here is generated by the
 <code>XYStepRendererDemo1.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYStepRendererSample.png"
 alt="XYStepRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYStepRenderer.html">org.jfree.chart.renderer.xy.XYStepRenderer</a></b></td></tr>
<tr><td>stepPoint</td><td>double</td><td>Sets the step point and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>drawSeriesLineAsPath</td><td>boolean</td><td>Sets the flag that controls whether or not each series is drawn as a
 single path and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>legendLine</td><td>Shape</td><td>Sets the shape used as a line in each legend item and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used to draw
 shape outlines, and sends a {@link RendererChangeEvent} to all
 registered listeners.
 <br/><br/>
 Refer to <code>XYLineAndShapeRendererDemo2.java</code> to see the
 effect of this flag.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.xy.XYStepAreaRenderer"></a><h2>Step Area</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A step chart renderer that fills the area between the step and the x-axis.
 The example shown here is generated by the
 <code>XYStepAreaRendererDemo1.java</code> program included in the JFreeChart
 demo collection:
 <br><br>
 <img src="../../../../../images/XYStepAreaRendererSample.png"
 alt="XYStepAreaRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/xy/XYStepAreaRenderer.html">org.jfree.chart.renderer.xy.XYStepAreaRenderer</a></b></td></tr>
<tr><td>outline</td><td>boolean</td><td>Sets a flag that controls whether or not outlines of the areas are
 drawn, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>plotArea</td><td>boolean</td><td>Sets a flag that controls whether or not areas are drawn for each data
 item and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>true</code></td></tr>
<tr><td>rangeBase</td><td>double</td><td>Sets the value on the range axis which defines the default border of the
 area, and sends a {@link RendererChangeEvent} to all registered
 listeners.  E.g. setRangeBase(Double.NEGATIVE_INFINITY) lets areas always
 reach the lower border of the plotArea.</td><td><code>0.0</code></td></tr>
<tr><td>shapesFilled</td><td>boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>shapesVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shapes are displayed for each
 data item, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="categoryrenderers"></a><h1>Category Graph Types</h1>
<a name="org.jfree.chart.renderer.category.AreaRenderer"></a><h2>Area</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/AreaRenderer.html">org.jfree.chart.renderer.category.AreaRenderer</a></b></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.BarRenderer"></a><h2>Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A {@link CategoryItemRenderer} that draws individual data items as bars.
 The example shown here is generated by the <code>BarChartDemo1.java</code>
 program included in the JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/BarRendererSample.png"
 alt="BarRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/BarRenderer.html">org.jfree.chart.renderer.category.BarRenderer</a></b></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.BarRenderer3D"></a><h2>Bar 3D</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer for bars with a 3D effect, for use with the
 {@link CategoryPlot} class.  The example shown here is generated
 by the <code>BarChart3DDemo1.java</code> program included in the JFreeChart
 Demo Collection:
 <br><br>
 <img src="../../../../../images/BarRenderer3DSample.png"
 alt="BarRenderer3DSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/BarRenderer3D.html">org.jfree.chart.renderer.category.BarRenderer3D</a></b></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot
 background, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>#dddddd</code></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.GanttRenderer"></a><h2>Gantt</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer for simple Gantt charts.  The example shown
 here is generated by the <code>GanttDemo1.java</code> program
 included in the JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/GanttRendererSample.png"
 alt="GanttRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/GanttRenderer.html">org.jfree.chart.renderer.category.GanttRenderer</a></b></td></tr>
<tr><td>completePaint</td><td>Paint</td><td>Sets the paint used to show the percentage complete and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>#00ff00</code></td></tr>
<tr><td>endPercent</td><td>double</td><td>Sets the position of the end of the progress indicator, as a percentage
 of the bar width, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>0.65</code></td></tr>
<tr><td>incompletePaint</td><td>Paint</td><td>Sets the paint used to show the percentage incomplete and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>#ff0000</code></td></tr>
<tr><td>startPercent</td><td>double</td><td>Sets the position of the start of the progress indicator, as a
 percentage of the bar width, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>0.35</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.GroupedStackedBarRenderer"></a><h2>Grouped Stacked Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/GroupedStackedBarRenderer.html">org.jfree.chart.renderer.category.GroupedStackedBarRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item
 value as a percentage (so that the stacked bars add to 100%), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.LayeredBarRenderer"></a><h2>Layered Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LayeredBarRenderer.html">org.jfree.chart.renderer.category.LayeredBarRenderer</a></b></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.LevelRenderer"></a><h2>Level</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A {@link CategoryItemRenderer} that draws individual data items as
 horizontal lines, spaced in the same way as bars in a bar chart.  The
 example shown here is generated by the
 <code>OverlaidBarChartDemo2.java</code> program included in the JFreeChart
 Demo Collection:
 <br><br>
 <img src="../../../../../images/LevelRendererSample.png"
 alt="LevelRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LevelRenderer.html">org.jfree.chart.renderer.category.LevelRenderer</a></b></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maxItemWidth</td><td>double</td><td>Sets the maximum item width, which is specified as a percentage of the
 available space for all items, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>maximumItemWidth</td><td>double</td><td>Sets the maximum item width, which is specified as a percentage of the
 available space for all items, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.LineRenderer3D"></a><h2>Line 3D</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A line renderer with a 3D effect.  The example shown here is generated by
 the <code>LineChart3DDemo1.java</code> program included in the JFreeChart
 Demo Collection:
 <br><br>
 <img src="../../../../../images/LineRenderer3DSample.png"
 alt="LineRenderer3DSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LineRenderer3D.html">org.jfree.chart.renderer.category.LineRenderer3D</a></b></td></tr>
<tr><td>XOffset</td><td>double</td><td>Sets the x-offset and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>12.0</code></td></tr>
<tr><td>YOffset</td><td>double</td><td>Sets the y-offset and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>8.0</code></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot
 background, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#dddddd</code></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin, which is the gap between items within a category
 (expressed as a percentage of the overall category width), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used for shape
 outlines, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>useSeriesOffset</td><td>boolean</td><td>Sets the flag that controls whether or not the x-position for each
 data item is offset within its category according to the series, and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.LineAndShapeRenderer"></a><h2>Line And Shape</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that draws shapes for each data item, and lines between data
 items (for use with the {@link CategoryPlot} class).
 The example shown here is generated by the <code>LineChartDemo1.java</code>
 program included in the JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/LineAndShapeRendererSample.png"
 alt="LineAndShapeRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LineAndShapeRenderer.html">org.jfree.chart.renderer.category.LineAndShapeRenderer</a></b></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for
 shapes, and sends a {@link RendererChangeEvent} to all registered
 listeners.
 <br/><br/>
 In some cases, shapes look better if they do NOT have an outline, but
 this flag allows you to set your own preference.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin, which is the gap between items within a category
 (expressed as a percentage of the overall category width), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the
 items in ALL series, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill
 shapes, and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>false</code></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used for shape
 outlines, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>useSeriesOffset</td><td>boolean</td><td>Sets the flag that controls whether or not the x-position for each
 data item is offset within its category according to the series, and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.MinMaxCategoryRenderer"></a><h2>Min Max</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>Renderer for drawing min max plot. This renderer draws all the series under
 the same category in the same x position using <code>objectIcon</code> and
 a line from the maximum value to the minimum value. For use with the
 {@link CategoryPlot} class. The example shown here is generated by
 the <code>MinMaxCategoryPlotDemo1.java</code> program included in the
 JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/MinMaxCategoryRendererSample.png"
 alt="MinMaxCategoryRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/MinMaxCategoryRenderer.html">org.jfree.chart.renderer.category.MinMaxCategoryRenderer</a></b></td></tr>
<tr><td>drawLines</td><td>boolean</td><td>Sets the flag that controls whether or not lines are drawn to connect
 the items within a series and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>groupPaint</td><td>Paint</td><td>Sets the paint used to draw the line between the minimum and maximum
 value items in each category and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>groupStroke</td><td>Stroke</td><td>Sets the stroke of the line between the minimum value and the maximum
 value and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.StackedAreaRenderer"></a><h2>Stacked Area</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that draws stacked area charts for a {@link CategoryPlot}.
 The example shown here is generated by the
 <code>StackedAreaChartDemo1.java</code> program included in the
 JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/StackedAreaRendererSample.png"
 alt="StackedAreaRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedAreaRenderer.html">org.jfree.chart.renderer.category.StackedAreaRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item
 value as a percentage (so that the stacked areas add to 100%), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.StackedBarRenderer"></a><h2>Stacked Bar</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A stacked bar renderer for use with the {@link CategoryPlot} class.
 The example shown here is generated by the
 <code>StackedBarChartDemo1.java</code> program included in the
 JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/StackedBarRendererSample.png"
 alt="StackedBarRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedBarRenderer.html">org.jfree.chart.renderer.category.StackedBarRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item
 value as a percentage (so that the stacked bars add to 100%), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.StackedBarRenderer3D"></a><h2>Stacked Bar 3D</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>Renders stacked bars with 3D-effect, for use with the {@link CategoryPlot}
 class.  The example shown here is generated by the
 <code>StackedBarChart3DDemo1.java</code> program included in the
 JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/StackedBarRenderer3DSample.png"
 alt="StackedBarRenderer3DSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedBarRenderer3D.html">org.jfree.chart.renderer.category.StackedBarRenderer3D</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item
 value as a percentage (so that the stacked bars add to 100%), and sends
 a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot
 background, and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>#dddddd</code></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.CategoryStepRenderer"></a><h2>Step</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A "step" renderer similar to {@link XYStepRenderer} but
 that can be used with the {@link CategoryPlot} class.  The example shown
 here is generated by the <code>CategoryStepChartDemo1.java</code> program
 included in the JFreeChart Demo Collection:
 <br><br>
 <img src="../../../../../images/CategoryStepRendererSample.png"
 alt="CategoryStepRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/CategoryStepRenderer.html">org.jfree.chart.renderer.category.CategoryStepRenderer</a></b></td></tr>
<tr><td>stagger</td><td>boolean</td><td>Sets the flag that controls whether or not the series steps are
 staggered and sends a {@link RendererChangeEvent} to all registered
 listeners.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>
<a name="org.jfree.chart.renderer.category.WaterfallBarRenderer"></a><h2>Waterfall</h2>
 &nbsp;&nbsp;&nbsp;<a href="">top</a>
<p>A renderer that handles the drawing of waterfall bar charts, for use with
 the {@link CategoryPlot} class.  Some quirks to note:
 <ul>
 <li>the value in the last category of the dataset should be (redundantly)
   specified as the sum of the items in the preceding categories - otherwise
   the final bar in the plot will be incorrectly plotted;</li>
 <li>the bar colors are defined using special methods in this class - the
   inherited methods (for example,
   {@link AbstractRenderer#setSeriesPaint(int, Paint)}) are ignored;</li>
 </ul>
 The example shown here is generated by the
 <code>WaterfallChartDemo1.java</code> program included in the JFreeChart
 Demo Collection:
 <br><br>
 <img src="../../../../../images/WaterfallBarRendererSample.png"
 alt="WaterfallBarRendererSample.png" /></p>
<table class="doc-table">
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td colspan="4" align="center"><b><a href="http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/WaterfallBarRenderer.html">org.jfree.chart.renderer.category.WaterfallBarRenderer</a></b></td></tr>
<tr><td>firstBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw the first bar and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>gradient:#2222ff,#6666ff,0.0,0.0,0.0,0.0,true</code></td></tr>
<tr><td>lastBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw the last bar and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>gradient:#ffff22,#ffff66,0.0,0.0,0.0,0.0,true</code></td></tr>
<tr><td>negativeBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw bars having negative values,
 and sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>gradient:#ff2222,#ff6666,0.0,0.0,0.0,0.0,true</code></td></tr>
<tr><td>positiveBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw bars having positive values.</td><td><code>gradient:#22ff22,#66ff66,0.0,0.0,0.0,0.0,true</code></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>SOLID</code></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>0.0</code></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and
 sends a {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars
 is included in the range calculated by
 {@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,
 a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all
 registered listeners.  The value is expressed as a percentage of the
 available width for plotting all the bars, with the resulting amount to
 be distributed between all the bars evenly.</td><td><code>0.2</code></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the
 available space for all bars, and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>1.0</code></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to
 all registered listeners.  The minimum bar length is specified in Java2D
 units, and can be used to prevent bars that represent very small data
 values from disappearing when drawn on the screen.  Typically you would
 set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with
 caution, however, because setting it to a non-zero value will
 artificially increase the length of bars representing small values,
 which may misrepresent your data.</td><td><code>0.0</code></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are
 drawn by the renderer.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>4.0</code></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is
 automatically populated when {@link #lookupSeriesFillPaint(int)} is
 called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list
 is automatically populated when {@link #lookupSeriesOutlinePaint(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list
 is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
 is called.</td><td><code>false</code></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is
 automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is
 automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is
 automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><code>true</code></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created
 for a series, and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#ffffff</code></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>SansSerif-10</code></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>#000000</code></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>false</code></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>#808080</code></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to
 all registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>#0000ff</code></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all
 registered listeners.</td><td><code>line=1.0|dash=0</code></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created
 for the items in ALL series, and sends a {@link RendererChangeEvent} to
 all registered listeners.  This flag overrides the per series and
 default settings - you must set it to <code>null</code> if you want the
 other settings to apply.</td><td><code>&nbsp;</code></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported
 by this renderer will exclude non-visible series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>true</code></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area
 when no area is specified.</td><td><code>3</code></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><code>2.0</code></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.  You can set
 this to <code>null</code> if you prefer to set the font on a per series
 basis.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a
 {@link RendererChangeEvent} to all registered listeners.  If this is
 <code>null</code>, the renderer will use the paint for the series.</td><td><code>&nbsp;</code></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a
 {@link RendererChangeEvent} to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}
 to all registered listeners.</td><td><code>&nbsp;</code></td></tr>
</table>

</div>
</body>
</html>
