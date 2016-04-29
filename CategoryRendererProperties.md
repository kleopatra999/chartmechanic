
# Category Graph Types #
## Area ##
<p></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/AreaRenderer.html'>org.jfree.chart.renderer.category.AreaRenderer</a></b></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
<blockquote>automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Bar</h2>
<p>A {@link CategoryItemRenderer} that draws individual data items as bars.<br>
The example shown here is generated by the <pre><code>BarChartDemo1.java</code></pre>
program included in the JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/BarRendererSample.png' />"<br>
alt="BarRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/BarRenderer.html'>org.jfree.chart.renderer.category.BarRenderer</a></b></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Bar 3D</h2>
<p>A renderer for bars with a 3D effect, for use with the<br>
{@link CategoryPlot} class.  The example shown here is generated<br>
by the <pre><code>BarChart3DDemo1.java</code></pre> program included in the JFreeChart<br>
Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/BarRenderer3DSample.png' />"<br>
alt="BarRenderer3DSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/BarRenderer3D.html'>org.jfree.chart.renderer.category.BarRenderer3D</a></b></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot<br>
background, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>#dddddd</code></pre></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Gantt</h2>
<p>A renderer for simple Gantt charts.  The example shown<br>
here is generated by the <pre><code>GanttDemo1.java</code></pre> program<br>
included in the JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/GanttRendererSample.png' />"<br>
alt="GanttRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/GanttRenderer.html'>org.jfree.chart.renderer.category.GanttRenderer</a></b></td></tr>
<tr><td>completePaint</td><td>Paint</td><td>Sets the paint used to show the percentage complete and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>#00ff00</code></pre></td></tr>
<tr><td>endPercent</td><td>double</td><td>Sets the position of the end of the progress indicator, as a percentage<br>
of the bar width, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>0.65</code></pre></td></tr>
<tr><td>incompletePaint</td><td>Paint</td><td>Sets the paint used to show the percentage incomplete and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>#ff0000</code></pre></td></tr>
<tr><td>startPercent</td><td>double</td><td>Sets the position of the start of the progress indicator, as a<br>
percentage of the bar width, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>0.35</code></pre></td></tr>
</table>
<h2>Grouped Stacked Bar</h2>
<p></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/GroupedStackedBarRenderer.html'>org.jfree.chart.renderer.category.GroupedStackedBarRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item<br>
value as a percentage (so that the stacked bars add to 100%), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Layered Bar</h2>
<p></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LayeredBarRenderer.html'>org.jfree.chart.renderer.category.LayeredBarRenderer</a></b></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Level</h2>
<p>A {@link CategoryItemRenderer} that draws individual data items as<br>
horizontal lines, spaced in the same way as bars in a bar chart.  The<br>
example shown here is generated by the<br>
<pre><code>OverlaidBarChartDemo2.java</code></pre> program included in the JFreeChart<br>
Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/LevelRendererSample.png' />"<br>
alt="LevelRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LevelRenderer.html'>org.jfree.chart.renderer.category.LevelRenderer</a></b></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maxItemWidth</td><td>double</td><td>Sets the maximum item width, which is specified as a percentage of the<br>
available space for all items, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>maximumItemWidth</td><td>double</td><td>Sets the maximum item width, which is specified as a percentage of the<br>
available space for all items, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Line 3D</h2>
<p>A line renderer with a 3D effect.  The example shown here is generated by<br>
the <pre><code>LineChart3DDemo1.java</code></pre> program included in the JFreeChart<br>
Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/LineRenderer3DSample.png' />"<br>
alt="LineRenderer3DSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LineRenderer3D.html'>org.jfree.chart.renderer.category.LineRenderer3D</a></b></td></tr>
<tr><td>XOffset</td><td>double</td><td>Sets the x-offset and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>12.0</code></pre></td></tr>
<tr><td>YOffset</td><td>double</td><td>Sets the y-offset and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>8.0</code></pre></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot<br>
background, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#dddddd</code></pre></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for<br>
shapes, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.<br>
<br /><br />
In some cases, shapes look better if they do NOT have an outline, but<br>
this flag allows you to set your own preference.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin, which is the gap between items within a category<br>
(expressed as a percentage of the overall category width), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the<br>
items in ALL series, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill<br>
shapes, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used for shape<br>
outlines, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>useSeriesOffset</td><td>boolean</td><td>Sets the flag that controls whether or not the x-position for each<br>
data item is offset within its category according to the series, and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Line And Shape</h2>
<p>A renderer that draws shapes for each data item, and lines between data<br>
items (for use with the {@link CategoryPlot} class).<br>
The example shown here is generated by the <pre><code>LineChartDemo1.java</code></pre>
program included in the JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/LineAndShapeRendererSample.png' />"<br>
alt="LineAndShapeRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/LineAndShapeRenderer.html'>org.jfree.chart.renderer.category.LineAndShapeRenderer</a></b></td></tr>
<tr><td>baseLinesVisible</td><td>boolean</td><td>Sets the base 'lines visible' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShapesFilled</td><td>boolean</td><td>Sets the base 'shapes filled' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShapesVisible</td><td>boolean</td><td>Sets the base 'shapes visible' flag and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>drawOutlines</td><td>boolean</td><td>Sets the flag that controls whether outlines are drawn for<br>
shapes, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.<br>
<br /><br />
In some cases, shapes look better if they do NOT have an outline, but<br>
this flag allows you to set your own preference.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin, which is the gap between items within a category<br>
(expressed as a percentage of the overall category width), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>linesVisible</td><td>Boolean</td><td>Sets a flag that controls whether or not lines are drawn between the<br>
items in ALL series, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shapesFilled</td><td>Boolean</td><td>Sets the 'shapes filled' for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shapesVisible</td><td>Boolean</td><td>Sets the 'shapes visible' for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>useFillPaint</td><td>boolean</td><td>Sets the flag that controls whether the fill paint is used to fill<br>
shapes, and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>useOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether the outline paint is used for shape<br>
outlines, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>useSeriesOffset</td><td>boolean</td><td>Sets the flag that controls whether or not the x-position for each<br>
data item is offset within its category according to the series, and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Min Max</h2>
<p>Renderer for drawing min max plot. This renderer draws all the series under<br>
the same category in the same x position using <pre><code>objectIcon</code></pre> and<br>
a line from the maximum value to the minimum value. For use with the<br>
{@link CategoryPlot} class. The example shown here is generated by<br>
the <pre><code>MinMaxCategoryPlotDemo1.java</code></pre> program included in the<br>
JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/MinMaxCategoryRendererSample.png' />"<br>
alt="MinMaxCategoryRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/MinMaxCategoryRenderer.html'>org.jfree.chart.renderer.category.MinMaxCategoryRenderer</a></b></td></tr>
<tr><td>drawLines</td><td>boolean</td><td>Sets the flag that controls whether or not lines are drawn to connect<br>
the items within a series and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>groupPaint</td><td>Paint</td><td>Sets the paint used to draw the line between the minimum and maximum<br>
value items in each category and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>groupStroke</td><td>Stroke</td><td>Sets the stroke of the line between the minimum value and the maximum<br>
value and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Stacked Area</h2>
<p>A renderer that draws stacked area charts for a {@link CategoryPlot}.<br>
The example shown here is generated by the<br>
<pre><code>StackedAreaChartDemo1.java</code></pre> program included in the<br>
JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/StackedAreaRendererSample.png' />"<br>
alt="StackedAreaRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedAreaRenderer.html'>org.jfree.chart.renderer.category.StackedAreaRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item<br>
value as a percentage (so that the stacked areas add to 100%), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Stacked Bar</h2>
<p>A stacked bar renderer for use with the {@link CategoryPlot} class.<br>
The example shown here is generated by the<br>
<pre><code>StackedBarChartDemo1.java</code></pre> program included in the<br>
JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/StackedBarRendererSample.png' />"<br>
alt="StackedBarRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedBarRenderer.html'>org.jfree.chart.renderer.category.StackedBarRenderer</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item<br>
value as a percentage (so that the stacked bars add to 100%), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Stacked Bar 3D</h2>
<p>Renders stacked bars with 3D-effect, for use with the {@link CategoryPlot}<br>
class.  The example shown here is generated by the<br>
<pre><code>StackedBarChart3DDemo1.java</code></pre> program included in the<br>
JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/StackedBarRenderer3DSample.png' />"<br>
alt="StackedBarRenderer3DSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/StackedBarRenderer3D.html'>org.jfree.chart.renderer.category.StackedBarRenderer3D</a></b></td></tr>
<tr><td>renderAsPercentages</td><td>boolean</td><td>Sets the flag that controls whether the renderer displays each item<br>
value as a percentage (so that the stacked bars add to 100%), and sends<br>
a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>wallPaint</td><td>Paint</td><td>Sets the paint used to hightlight the left and bottom walls in the plot<br>
background, and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>#dddddd</code></pre></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Step</h2>
<p>A "step" renderer similar to {@link XYStepRenderer} but<br>
that can be used with the {@link CategoryPlot} class.  The example shown<br>
here is generated by the <pre><code>CategoryStepChartDemo1.java</code></pre> program<br>
included in the JFreeChart Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/CategoryStepRendererSample.png' />"<br>
alt="CategoryStepRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/CategoryStepRenderer.html'>org.jfree.chart.renderer.category.CategoryStepRenderer</a></b></td></tr>
<tr><td>stagger</td><td>boolean</td><td>Sets the flag that controls whether or not the series steps are<br>
staggered and sends a {@link RendererChangeEvent} to all registered<br>
listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>
<h2>Waterfall</h2>
<p>A renderer that handles the drawing of waterfall bar charts, for use with<br>
the {@link CategoryPlot} class.  Some quirks to note:<br>
<ul>
<li>the value in the last category of the dataset should be (redundantly)<br>
<blockquote>specified as the sum of the items in the preceding categories - otherwise<br>
the final bar in the plot will be incorrectly plotted;</li>
</blockquote><li>the bar colors are defined using special methods in this class - the<br>
<blockquote>inherited methods (for example,<br>
{@link AbstractRenderer#setSeriesPaint(int, Paint)}) are ignored;</li>
</blockquote></ul>
The example shown here is generated by the<br>
<pre><code>WaterfallChartDemo1.java</code></pre> program included in the JFreeChart<br>
Demo Collection:<br>
<br /><br />
<img src="<img src='http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/WaterfallBarRendererSample.png' />"<br>
alt="WaterfallBarRendererSample.png" /></p>
<table>
<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>
<tr><td align='center'><b><a href='http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/renderer/category/WaterfallBarRenderer.html'>org.jfree.chart.renderer.category.WaterfallBarRenderer</a></b></td></tr>
<tr><td>firstBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw the first bar and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>gradient:#2222ff,#6666ff,0.0,0.0,0.0,0.0,true</code></pre></td></tr>
<tr><td>lastBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw the last bar and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>gradient:#ffff22,#ffff66,0.0,0.0,0.0,0.0,true</code></pre></td></tr>
<tr><td>negativeBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw bars having negative values,<br>
and sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>gradient:#ff2222,#ff6666,0.0,0.0,0.0,0.0,true</code></pre></td></tr>
<tr><td>positiveBarPaint</td><td>Paint</td><td>Sets the paint that will be used to draw bars having positive values.</td><td><pre><code>gradient:#22ff22,#66ff66,0.0,0.0,0.0,0.0,true</code></pre></td></tr>
<tr><td>barPainter</td><td>BarPainter</td><td>Sets the bar painter for this renderer and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>SOLID</code></pre></td></tr>
<tr><td>base</td><td>double</td><td>Sets the base value for the bars and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>drawBarOutline</td><td>boolean</td><td>Sets the flag that controls whether or not bar outlines are drawn and<br>
sends a {@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>includeBaseInRange</td><td>boolean</td><td>Sets the flag that controls whether or not the base value for the bars<br>
is included in the range calculated by<br>
{@link #findRangeBounds(CategoryDataset)}.  If the flag is changed,<br>
a {@link RendererChangeEvent} is sent to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>itemMargin</td><td>double</td><td>Sets the item margin and sends a {@link RendererChangeEvent} to all<br>
registered listeners.  The value is expressed as a percentage of the<br>
available width for plotting all the bars, with the resulting amount to<br>
be distributed between all the bars evenly.</td><td><pre><code>0.2</code></pre></td></tr>
<tr><td>maximumBarWidth</td><td>double</td><td>Sets the maximum bar width, which is specified as a percentage of the<br>
available space for all bars, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>1.0</code></pre></td></tr>
<tr><td>minimumBarLength</td><td>double</td><td>Sets the minimum bar length and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  The minimum bar length is specified in Java2D<br>
units, and can be used to prevent bars that represent very small data<br>
values from disappearing when drawn on the screen.  Typically you would<br>
set this to (say) 0.5 or 1.0 Java 2D units.  Use this attribute with<br>
caution, however, because setting it to a non-zero value will<br>
artificially increase the length of bars representing small values,<br>
which may misrepresent your data.</td><td><pre><code>0.0</code></pre></td></tr>
<tr><td>shadowPaint</td><td>Paint</td><td>Sets the shadow paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>shadowVisible</td><td>boolean</td><td>Sets the flag that controls whether or not shadows are<br>
drawn by the renderer.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shadowXOffset</td><td>double</td><td>Sets the x-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>shadowYOffset</td><td>double</td><td>Sets the y-offset for the bar shadow and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>4.0</code></pre></td></tr>
<tr><td>autoPopulateSeriesFillPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series fill paint list is<br>
automatically populated when {@link #lookupSeriesFillPaint(int)} is<br>
called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlinePaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline paint list<br>
is automatically populated when {@link #lookupSeriesOutlinePaint(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesOutlineStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series outline stroke list<br>
is automatically populated when {@link #lookupSeriesOutlineStroke(int)}<br>
is called.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>autoPopulateSeriesPaint</td><td>boolean</td><td>Sets the flag that controls whether or not the series paint list is<br>
automatically populated when {@link #lookupSeriesPaint(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesShape</td><td>boolean</td><td>Sets the flag that controls whether or not the series shape list is<br>
automatically populated when {@link #lookupSeriesShape(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>autoPopulateSeriesStroke</td><td>boolean</td><td>Sets the flag that controls whether or not the series stroke list is<br>
automatically populated when {@link #lookupSeriesStroke(int)} is called.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseCreateEntities</td><td>boolean</td><td>Sets the base flag that controls whether entities are created<br>
for a series, and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseFillPaint</td><td>Paint</td><td>Sets the base fill paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#ffffff</code></pre></td></tr>
<tr><td>baseItemLabelFont</td><td>Font</td><td>Sets the base item label font and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>SansSerif-10</code></pre></td></tr>
<tr><td>baseItemLabelPaint</td><td>Paint</td><td>Sets the base item label paint and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>#000000</code></pre></td></tr>
<tr><td>baseItemLabelsVisible</td><td>Boolean</td><td>Sets the base setting for item label visibility and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>false</code></pre></td></tr>
<tr><td>baseLegendShape</td><td>Shape</td><td>Sets the default legend shape and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextFont</td><td>Font</td><td>Sets the default legend text font and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseLegendTextPaint</td><td>Paint</td><td>Sets the default legend text paint and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseOutlinePaint</td><td>Paint</td><td>Sets the base outline paint and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>#808080</code></pre></td></tr>
<tr><td>baseOutlineStroke</td><td>Stroke</td><td>Sets the base outline stroke and sends a {@link RendererChangeEvent} to<br>
all registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>basePaint</td><td>Paint</td><td>Sets the base paint and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>#0000ff</code></pre></td></tr>
<tr><td>baseSeriesVisible</td><td>boolean</td><td>Sets the base visibility and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseSeriesVisibleInLegend</td><td>boolean</td><td>Sets the base visibility in the legend and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>baseShape</td><td>Shape</td><td>Sets the base shape and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>baseStroke</td><td>Stroke</td><td>Sets the base stroke and sends a {@link RendererChangeEvent} to all<br>
registered listeners.</td><td><pre><code>line=1.0|dash=0</code></pre></td></tr>
<tr><td>createEntities</td><td>Boolean</td><td>Sets the flag that controls whether or not chart entities are created<br>
for the items in ALL series, and sends a {@link RendererChangeEvent} to<br>
all registered listeners.  This flag overrides the per series and<br>
default settings - you must set it to <pre><code>null</code></pre> if you want the<br>
other settings to apply.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>dataBoundsIncludesVisibleSeriesOnly</td><td>boolean</td><td>Sets the flag that controls whether or not the data bounds reported<br>
by this renderer will exclude non-visible series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>true</code></pre></td></tr>
<tr><td>defaultEntityRadius</td><td>int</td><td>Sets the radius of the circle used for the default entity area<br>
when no area is specified.</td><td><pre><code>3</code></pre></td></tr>
<tr><td>fillPaint</td><td>Paint</td><td>Sets the fill paint for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelAnchorOffset</td><td>double</td><td>Sets the item label anchor offset.</td><td><pre><code>2.0</code></pre></td></tr>
<tr><td>itemLabelFont</td><td>Font</td><td>Sets the item label font for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  You can set<br>
this to <pre><code>null</code></pre> if you prefer to set the font on a per series<br>
basis.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelPaint</td><td>Paint</td><td>Sets the item label paint for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>itemLabelsVisible</td><td>Boolean</td><td>Sets the visibility of the item labels for ALL series (optional).</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlinePaint</td><td>Paint</td><td>Sets the outline paint for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>outlineStroke</td><td>Stroke</td><td>Sets the outline stroke for ALL series and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>paint</td><td>Paint</td><td>Sets the paint to be used for ALL series, and sends a<br>
{@link RendererChangeEvent} to all registered listeners.  If this is<br>
<pre><code>null</code></pre>, the renderer will use the paint for the series.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>shape</td><td>Shape</td><td>Sets the shape for ALL series (optional) and sends a<br>
{@link RendererChangeEvent} to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
<tr><td>stroke</td><td>Stroke</td><td>Sets the stroke for ALL series and sends a {@link RendererChangeEvent}<br>
to all registered listeners.</td><td><pre><code>&amp;nbsp;</code></pre></td></tr>
</table>