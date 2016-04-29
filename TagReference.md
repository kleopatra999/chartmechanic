

## `data` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `id`      | Identifier for this data source, so that other tags can refer to it. The ID should be unique within a JSP page. | yes       |         |
| `type`    | Type of the data source. Currently only CSV or SQL are supported. Default assumes CSV | no        |         |
| `jndiName` | JNDI name of a SQL data source. Use either this or 'driver'/'url' are required | no        | `jndi`  |
| `driver`  | JDBC driver class for when type='SQL' | no        |         |
| `url`     | JDBC driver URL for when type='SQL' | no        |         |
| `username` | resource username for data URL | no        | `user`  |
| `password` | resource password for data URL | no        | `passwd` |

## `props` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `template` | Names this bag of properties as a **template**, which lets these properties be shared across multiple charts. | no        |         |
| `prefix`  | Provides a shorthand for targeting this bag of properties at one particular element of a chart. See the [charts.jsp#properties chart properties] section for details on how the shorhand works. Valid values for prefix are: `chart plot renderer title legend domain-axis range-axis-0 range-axis-1 range-axis-2 range-axis-3 ` | no        |         |

## `chart` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `width`   | The width of the chart, in pixels | no        |         |
| `height`  | The height of the chart, in pixels | no        |         |
| `plotType` | Specifies which kind of plot to use for the chart. Note that only certain data types for the X and Y axes are valid for a given type of plot; see [charts.jsp#plot-types the charts document] for compatibility. Valid values are` PLOT_XY PLOT_TIME PLOT_PIE PLOT_PIE3D PLOT_CATEGORY PLOT_RING PLOT_HISTOGRAM PLOT_GANTT`. If `plotType` is not set, the chart tag will try to infer your desired plot type, based on the [data.jsp#data-types data type] of the X columns of your series. | no        |         |
| `graphType` | Sets the default graph type (renderer) to be used for series of this chart (e.g., lines, bars, dots, etc). Individual `<series>` tags may override the default graphType. See [charts.jsp#graph-types graph types] section for a list of valid graph types. | no        | `renderType` |
| `ttl`     | Specifies a time-to-live, in seconds, for the cache entry of this chart. Charts with cache entries younger than the TTL will not be re-drawn. A ttl of `0` will never use the cache; a ttl of `1` will always use the cache. The default is `120`. | no        |         |
| `title`   | Sets the title text of the chart. Shorthand for setting chart property `title.text` | no        |         |
| `template` | Provides a bag of pre-set chart properties (a [charts.jsp#templates template]). If no template is set for a chart, then a template called `default` will be used, if oneis defined. | no        |         |

## `series` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `name`    | The name of this series as it will appear on the chart. | yes       |         |
| `graphType` | Specifies the [charts.jsp#graph-types graph type] for this series. This will override the default graph type set for the entire chart, if any. | no        | `renderer` `render` `type` `graph-type` |
| `color`   | Sets the color (or gradient paint) for this series. | no        | `paint` |
| `timeperiod` | Sets the time frequency of data points for this series, which in turn controls the "width" of each point for the series. Most relevant when using certain graphTypes (e.g., `bar`). | no        | `time` `period` |
| `yaxis`   | Specifies the Y (or `range`) axis that this series will bind to. Valid values are `0 1 2 3`. Defaults to `0`. | no        |         |
| `visible` | `boolean` to toggle if this series is shown on the chart or not. | no        |         |
| `datasource` | The datasource ID that this series will pull data from. | yes       |         |
| `x`       | The (1-based) column index on the series' datasource for the X axis values of this series. Defaults to `1` | no        |         |
| `y`       | The (1-based) column index on the series' datasource for the Y axis values of this series. Defaults to `2` | no        |         |
| `z`       | The (1-based) column index on the series' datasource for the Z values of this series, if needed. Note that only a few graph-type renderers (e.g., `bubble`) even use the Z value for anything. Defaults to `3` | no        |         |
| `dynamicNameColumn` | Specifies a (1-based) column index whose value(s) will determine the names of more than one series on this chart. You will get one series created for each distinct value in the specified column. As a sanity check, no more than 30 series will be created. | no        |         |

## `marker` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `name`    | name for this marker, as it will appear on the chart. | yes       |         |
| `yaxis`   | The Y axis `[0-3]` this marker is aligned with. Defaults to 0 | no        |         |
| `color`   | The color or gradient paint of this marker. Shorthand for the `paint` property | no        |         |
| `value`   | Specifies the numeric value(s) for this marker, along a numeric axis. A line marker will be drawn for a single value, multiple values will produce an interval (band) marker. Multiple valuesmust be separated by a vertical bar '|' | no        | `bandValues` |
| `dateValues` | Specifies the date value(s) for this marker, along a date/time axis. Multiple values will produce an interval (band) marker. Multiple values must be separated by a vertical bar '|'. The format of the dates must comply with one of [data.jsp#date-formats the valid date formats]for the `data` tag. | no        | `date`  |
| `visible` | boolean that toggles whether or not this marker is displayed. | no        |         |
| `xaxis`   | boolean that specifies if this marker should be on the X (domain) axis | no        |         |
| `template` | Sets the name of a template property set that should be applied to this marker. | no        |         |

## `data-table` tag ##

| Attribute | Description | Required? | Aliases |
|:----------|:------------|:----------|:--------|
| `datasource` | datasource ID to display as an HTML table | yes       |         |
| `css`     | Sets the `class=...` CSS class of the generated table | no        | `cssClass` |
| `style`   | Sets the `style=...` CSS style attribute of the generated table | no        |         |
| `offset`  | Specifies the number of rows to skip in the beginning of the data. Defaults to 0 | no        |         |
| `length`  | Specifies the maximum number of rows to display. Defaults to 25 | no        |         |