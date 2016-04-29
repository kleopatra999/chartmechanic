

# How do I rotate/flip my chart 90 degrees? #

Set the property:
```
plot.orientation=HORIZONTAL
```
The default is VERTICAL.  This is commonly used in Category charts, to make the X-axis labels more readable.

# How do I place an image in the background of my chart? #

Set properties on the chart like:
```
chart.backgroundImage=/images/my-chart-background.jpg
chart.backgroundImageAlpha=0.3
plot.backgroundAlpha=0.0
```

Charts are specified as a path within your web application, so that the image is accessible via `getClass().getResource(path)`, or else an external URL (e.g., ![http://server/image.jpg](http://server/image.jpg)).

Setting the alpha on the background image makes it more or less transparent; you will probably want some transparency, so that the image does not obscure the foreground of the chart.  Similarly, setting the plot alpha to zero makes the plot completely transparent, so that the image is visible.  Adjust these alpha values as needed to achieve the visual effect that you want.

# How do I make parts of the chart more/less transparent? #

Some areas of the chart have a property known as _alpha_, whose value will be a floating point number between `0.0` and `1.0`.  The _alpha_ value controls how transparent/opaque the element will be: `0.0` is perfectly transparent, and `1.0` will make the element in question perfectly opaque.

The following _alpha_ properties are supported:

```
chart.backgroundImageAlpha
plot.backgroundAlpha
plot.backgroundImageAlpha
plot.foregroundAlpha
marker.alpha
```

# How can I use a gradient paint? #

Any chart property of type [java.awt.Paint](http://chartmechanic.googlecode.com/svn/trunk/taglib/static_demo/charts.jsp#property-types) can take a single color, which can be the common, simple name or else an RGB value:

```
red
white
black
light_green
#abcdef
```

Any `Paint` property can also take a `gradient` paint, which is a way of shifting from one color to another across some area of the chart.  As a string, this should start with `gradient:`, and then comma-separated values for: two colors, four numbers that denote a rectangle, and a boolean. The two colors are the colors that the paint will shift between, and the rectangle denotes the direction and speed of the gradient shift. The first two numbers specify the XY coordinates for the upper left of the rectangle, and the second two numbers specify the width and height of the rectangle. The boolean specifies whether or not the gradient should be cyclical (repeating).  For example:

```
chart.backgroundPaint=gradient:#bbeaff,#f0f0f0,0,0,400,400,true
chart.backgroundPaint=gradient:#bbeaff,#f0f0f0,0,0,0,400,true
chart.backgroundPaint=gradient:#bbeaff,#f0f0f0,0,0,400,0,true
```

All describe a gradient that shifts from light blue to light gray.  In order, the direction of the shift is diagonally (top left/bottom right), vertically, and horizontally.

The best way to get a feel for using a gradient paint is to simply try different values until you achieve an effect that pleases you.

# How can I move or modify the chart's legend? #

The chart's positioning (relative to the `plot`) is controlled by the properties:

```
legend.position=BOTTOM|LEFT|TOP|RIGHT
legend.horizontalAlignment=CENTER|LEFT|RIGHT
legend.verticalAlignment=BOTTOM|CENTER|TOP
```

You can make the legend visible or not with:

```
legend.visible=true|false
```

# How do I control the shape when using "Line And Shape" renderer? #

One nice effect that can be achieved with the `Line And Shape` renderer is to put spacing around each point, so that it appears _disconnected_ from the line part of the renderer.  Assuming the background of your chart is white, and that your renderer paint is orange, this can be accomplished by:

```
renderer.shape=circle
renderer.useOutlinePaint=true
renderer.outlinePaint=gray
renderer.outlineStroke=line=2.0|dash=0
renderer.useFillPaint=true
renderer.fillPaint=orange
```

Conceptually, a white solid outline, with stroke width 2, is being draw around each circle in the _Line And Shape_ graph.

# How do I make my Y axis use Logarithmic (Log) scale? #

Setting the chart property:

```
range-axis-0.axisType=log
```

will make first Y axis log scale.