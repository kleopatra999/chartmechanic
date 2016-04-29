

# Anatomy of a Chart #

The ChartMechanic tag library lets you customize many aspects of the charts it generates. Charts are organized into various pieces, identified in the diagram below:

![http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/anatomy-1.png](http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/anatomy-1.png)

  * **Title** - a single line of text at the top of the chart
  * **Description** - a multi line block of text describing a chart
  * **X Axis** - also called the **domain** axis. The line along the plot expressing the first column of a series.
  * **Y Axis (0 - 3)** - also called a **range** axis, which expresses the second column of one or more series. A chart may have between 1 and 4 Y axes.
  * **Plot** - the rendering area for data values, where series are drawn. The plot area is contained by the axes.
  * **Series** - the graphical representation of a data set having both **X**, **Y**, (and optionally **Z**) values. You can represent a series using various lines, shapes and bars as determined by the [graph type](#graph-types.md) for the series. In the anatomy diagram above, the green bars, and the red and blue lines, represent series with different series.
  * **Markers** - a horizontal or vertical line across the plot area, tied to a point on an X or Y axis, with an optional descriptive label. You can specify a marker's value as a specific NUMBER or DATE value, or its value can be from a [FUNCTION](#marker-functions.md) of a series.
  * **Legend** - a block showing a color and shape coded key for each series in the chart.
  * **Chart** - the background, or bottom layer of a chart. Basically, everything that is behind the other chart components.

# Chart Properties #

The attributes of a chart are expressed with a collection of name/value pairs, known as the chart's **properties**. These name/value pairs are converted into `setXXX(...)` calls in the [JFreeChart](http://www.jfree.org/jfreechart/api/javadoc/index.html) API. The prefix of a property name indicate which part of the chart the property is setting. For example, the following `<c:props>` tag:

``

```

<c:props>
chart.backgroundPaint=white
plot.outlineVisible=false
title.paint=black
title.font=Arial-bold-16
</c:props>

```

Will have a white background, a black title with 16pt Arial bold font, and no outline visible around the plot area. If a set of properties share the same prefix, the prefix may be specified in the tag, and then won't need to be repeated for each name/value pair:

``

```

<c:props prefix="title">

paint=black
font=Arial-bold-16
</c:props>

```

Is equivalent to the previous tag usage, for the chart title.

# Property Types #

Properties are converted into `set(...) ` calls in the [JFreeChart API](http://www.jfree.org/jfreechart/api/javadoc/index.html). This requires certain conventions for the format of property values to b e converted from strings. Basic types (numeric, boolean, etc) are simply represted as their literal values. Other supported types from the `java.awt.*` and JFreeChart API are represented as follows:

| Type | Encoding | Example(s) |
|:-----|:---------|:-----------|
| **java.awt.Image** | publicly accessible URL to an image Resource path of an image within your webapp | `http://example.org/images/my-background.jpg /resources/images/background.png` |
| **java.awt.Font** | standard [java font specification string](http://java.sun.com/j2se/1.5.0/docs/api/java/awt/Font.html#decode(java.lang.String)): _fontname-style-pointsize_ | `Arial-BOLD-12` |
| **java.awt.Paint java.awt.Color** | Either a hex RGB color for a solid color, or a string specifying a color gradient. A gradient paint specifies, as comma-separated values, two colors, four numbers that denote a rectangle, and a boolean. The two colors are the colors that the paint will shift between, and the rectangle denotes the direction and speed of the gradient shift. The first two numbers specify the XY coordinates for the upper left of the rectangle, and the second two numbers specify the width and height of the rectangle. The boolean specifies whether or not the gradient should be cyclical (repeating). | `00ff00` - light green solid color `gradient:d0d0d0,ffffff,0,0,200,200,true` - a gradient paint, shifting diagonally from light gray to white, in a 200 x 200 square, cyclically |
| **java.awt.Stroke** | A Stroke describes the width and style of a line, which can be solid or have some dashed pattern. It is encoded as `line=<width>|dash=<dash-style>`. The dash style is a number, 0-7 inclusive, where 0 specifies a solid line. As a shorthand for a solid line, only the width number of the stroke width may be used. | `line=0.5|dash=0` - A thin solid line `line=2.0|dash=4` - A thicker solid line `1.0` - solid line, shorthand width |
| **java.awt.Shape** | Shapes are used for decorations on renderers, and on other locations on a chart. Valid values are `square circle diamond triangle-up triangle-down` | _see valid values_ |
| **PlotOrientation** | Specifies if a plot should be laid out horizontally, with the domain axis horizontal (the default), or vertical. | `HORIZONTAL VERTICAL` |
| **AxisLocation** | Sets the location of an axis relative to a plot. | `BOTTOM_OR_LEFT BOTTOM_OR_RIGHT TOP_OR_LEFT TOP_OR_RIGHT` |
| **java.text.DateFormat** | Any format String supported by `SimpleDateFormat` | `MMM-yy`   |
| **java.text.NumberFormat** | Any format String supported by `DecimalFormat` | `###,###`  |
| **RectangleAnchor** | JFreeChart block alignment constants | ` CENTER TOP TOP_LEFT TOP_RIGHT BOTTOM BOTTOM_LEFT BOTTOM_RIGHT LEFT RIGHT ` |
| **TextAnchor** | JFreeChart text alignment constants | ` TOP_LEFT TOP_CENTER TOP_RIGHT HALF_ASCENT_LEFT HALF_ASCENT_CENTER HALF_ASCENT_RIGHT CENTER_LEFT CENTER CENTER_RIGHT BASELINE_LEFT BASELINE_CENTER BASELINE_RIGHT BOTTOM_LEFT BOTTOM_CENTER BOTTOM_RIGHT` |
| **RectangleInsets** | JFreeChart inset rectangle, format: `top,left,bottom,right{,RELATIVE|ABSOLUTE}` | `4.0,4.0,4.0,2.0,RELATIVE` |
| **HorizontalAlignment** | JFreeChart horizontal alignment constant | `CENTER LEFT RIGHT` |
| **VerticalAlignment** | JFreeChart vertical alignment constant | `BOTTOM CENTER TOP` |
| **NumberTickUnit** | The size/spacing for a JFreeChart [NumberTickUnit](http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/NumberTickUnit.html) | `any positive number` |
| **!XYBarPainter CategoryBarPainter** | JFreeChart bar painting styles | `SOLID GRADIENT` |
| **DateTickUnit** | encodes a JFreeChart [DateTickUnit](http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/axis/DateTickUnit.html) `{unit_count}-{unit-type}` | `4-DAY` ���draw a tick every 4 days `12-HOUR` ��draw a tick every 12 hours |
| **DateTickMarkPosition** | Position of a date tick mark within its cell | `START MIDDLE END` |
| **RectangleEdge** | Layout constants to specify the edge of a rectangle | `BOTTOM LEFT RIGHT TOP` |
| **CategoryLabelPositions** | Orientation of the labels on a category axis | `STANDARD UP_45 UP_90 DOWN_45 DOWN_90` |
| **BlockBorder** | The width and color around a block region, such as the `legend` `title` or `description` blocks.  3 formats are supported:`width`  **or** `width,color`   **or** `top,left,bottom,right,color` | `1.5` **or** `0.5,#ff00ff` **or** `1.0,2.0,1.0,2.0,black` |

# Plot Types #

The plot types supported by the ChartMechanic tag library include:

  * **Time Series** Time series plots are like XY plots except the X axis is a Date value.
  * **XY** XY plots graph numeric values along a two-dimensional coordinate system.
  * **Category** Category plots show values based on Text-typed values. A common example is a bar chart where each bar corresponds to a string category and the height of the bar corresponds to the numeric value for that category.
  * **Gantt** Gantt charts show duration of various categories against time on the X-axis.
  * **Pie** Pie charts are a special kind of category chart that displays each category as a pie slice. In this way, the relative values of the categories can be easily compared. Pie charts can also be show in 3D as well as ring plots. Ring plots are like pie charts except that are missing the middle section
  * **Histogram** Histogram plots show a frequency distribution of values along a spectrum of possible values

| Plot & Data Compatibility |
|:--------------------------|
| Plot Type                 | X Axis Type               | Y Axis Type               |
| timeseries                | DATE                      | NUMBER                    |
| xy                        | NUMBER                    | NUMBER                    |
| category                  | _ANY_                     | NUMBER                    |
| pie                       | _ANY_                     | NUMBER                    |
| pie3d                     | _ANY_                     | NUMBER                    |
| histogram                 | _N/A_                     | NUMBER                    |
| gantt                     | _ANY_                     | DATE                      |

# Series #

A `series` is the graphical representation of a data set having both **X**, **Y**, (and optionally **Z**) values. More simply, each line, or set of bars on a chart, are a series.

The `X, Y (,Z)` values for the series typically come from [data.jsp columns on the data source] for the series, but the series may also be derived by applying a [SERIES FUNCTION](#series-functions.md) to another series.

Every series is tied to the X axis for a chart/plot, and to one of the 4 Y axes. The different series on a chart are rendered using one of the available [graph types](#graph-types.md) for the [plot type](#plot-types.md) of the chart.

See the [tag-reference.jsp#series  series tag reference] for how to set up a series.

# Graph Types #

**Graph types for Time Series, XY, and Histogram Charts**

| Graph Type | Description |
|:-----------|:------------|
| Area       | render a series as filled Area |
| Bar        | horizontal/vertical rectagular Bars |
| Bubble     | circular, filled "bubble", with the diameter determined by a Z column |
| Candlestick | Candlestick (Box and Whisker) shows Open/High/Low/Close values, usually for a tradeable financial product. An optional fifth column renders trading volume. |
| Clustered Bar | Bar rendering, where multiple series are clustered next to each other along the X axis. |
| Difference | renders a difference between two series, with different colors for positive and negative differences |
| Dot        | draws values as Dots, a.k.a. a scatter plot |
| Line       | render a series as simple line |
| Line 3D    | draws a Line, with an optional shadow behind it |
| Line And Shape | render a series as line with a shape at value points. Shape can be filled/outlined. |
| Spline     | render a series as a line, with spline iterpolation between points. |
| Stacked Area | Area rendering, with multiple series stacked on top of each other |
| Stacked Bar | renders values as rectangular bars, with multiple series stacked on top of each other |
| Step       | line rendering, with discrete vertical steps between values |
| Step Area  | Step rendering, with the area below the line filled |

**Graph types for Category plots**

| Graph Type | Description |
|:-----------|:------------|
| Area       | render a series as a filled Area |
| Bar        | horizontal/vertical rectagular Bars |
| Bar 3D     | Bar rendering, with 3D depth effect |
| Gantt      | Applicable only for Gantt chart type |
| Grouped Stacked Bar | stacked bar rendering, with clustering by group |
| Layered Bar | render a series as LayeredBar |
| Level      | draws a single line at the Y value |
| Line 3D    | draws a Line, with an optional shadow behind it |
| Line And Shape | render a series as line with a shape at value points. Shape can be filled/outlined. |
| Min Max    | similar to Level rendering, with values at the minimum and maximum |
| Stacked Area | Area rendering, with multiple series stacked on top of each other |
| Stacked Bar | renders values as rectangular bars, with multiple series stacked on top of each other |
| Stacked Bar 3D | stacked Bar rendering, with 3D depth effect |
| Step       | line rendering, with discrete vertical steps between values |
| Waterfall  | renders as Waterfall effect, with sorted series values flowing into the next value |

# Series Functions #

Under construction

| Function | Description | Argument(s) |
|:---------|:------------|:------------|
| **MOVING AVG** | Moving Average | **Series** (sid) - Apply to series **Window** (number) - Number of samples to take the moving average of |
| **SCALE** | Scale       | **Series** (sid) - Apply to series **Scale Factor** (number) - Numeric value to multiply by original data |
| **INFLATION ADJUST** | Adjust for Inflation | **Series** (sid) - Apply to series **Base Year** (number) - Year for inflation normalization (between 1913 and present) |
| **TIME CHANGE** | Change over Time | **Series** (sid) - Apply to series **Time Interval** (timeinterval) - Earlier time period from which to compute change **Percentage** (boolean) - Express as Percentage |
| **TIME JOIN** | Join Time Series | **Operand 1** (sid) - First Series **Operator** (math\_operator) - Math Function **Operand 2** (sid) - Second Series **Join Tolerance** (timeinterval) - Tolerance interval for time join |

# Markers #

A marker is a horizontal or vertical line across the plot area, tied to a point on an X or Y axis, with an optional descriptive label. You can specify a marker's value as a specific NUMBER or DATE value, or its value can be from a [FUNCTION](#marker-functions.md) of a series.

# Marker Functions #

Under construction

| Function | Description | Argument(s) |
|:---------|:------------|:------------|
| **AVG**  | Average     | **Apply to Series** (sid) - null |
| **MIN**  | Minimum     | **Apply to Series** (sid) - null |
| **MAX**  | Maximum     | **Apply to Series** (sid) - null |
| **COUNT** | Count       | **Apply to Series** (sid) - null |
| **SUM**  | Sum         | **Apply to Series** (sid) - null |
| **STDEV** | Standard Deviation | **Apply to Series** (sid) - null **Multiplier** (number) - Numeric factor to multiply the standard deviation by |
| **STDEVP** | Population Standard Deviation | **Apply to Series** (sid) - null **Multiplier** (number) - Numeric factor to multiply the standard deviation by |

# Templates #

Frequently, one wants to have a common look and feel for all the charts in an application. It grows cumbersome to repeat the same properties over and over again for each usage of the `<chart>` tag.

A template can be set up instead to declare all of the common properties once, in one place, using the `<props>` tag. A tag like:

``

```

<c:props template="default">
chart.backgroundPaint=white
plot.outlineVisible=false
title.paint=black
title.font=Arial-bold-16
</c:props>

```

will store the specified properties in a template called "default". The "default" template will be applied to all charts; other template names must be referenced explicitly by each tag.

See the [tag-reference.jsp#props props tag reference] for more on setting up and using templates.