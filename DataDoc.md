  * [Tabular Data](#tabular-data.md)
  * [Data Types](#data-types.md)
  * [Type Inferencing](#type-inferencing.md)
  * [NUMBER Formats](#number-formats.md)
  * [DATE Formats](#date-formats.md)
  * [CSV Example](#example-csv.md)
  * [SQL Example](#example-sql.md)

## Tabular Data ##

The `<data>` tag specifies a **data source**, which provides a tablular (having columns and rows) source of values that can be represented graphically in a chart. The raw data, i.e., the actual provider of the data, can be one of the following formats:

  * CSV: comma separated values
  * SQL query

A later release of the tag library will support raw data from the following sources as well:

  * Microsoft Excel Spreadsheet
  * Google Docs Spreadsheet
  * HTML: tables from web pages

## Data Types ##

the `<data>` tag understands data values in three basic types:

  * **DATE** - Time-based values. Many charts are time-related and time values are commonly used in the X-axis
  * **NUMBER** - Integer or decimal values. Number typed values are most frequently used as the Y-axis values in charts
  * **TEXT** - String or alphanumeric values. Text typed values are commonly used as categories in pie charts or bar charts.

> Each column of a data source has one of these three types. For example, a table of monthly sales figures would be typed as follows:

| Month **(DATE)** | Sales **(NUMBER)** | Representative **(TEXT)** |
|:-----------------|:-------------------|:--------------------------|
| May 2009         | $500               | Larry                     |
| June 2009        | $800               | Moe                       |
| July 2009        | $1,300             | Curly                     |
| August 2009      | $900               | Shemp                     |
| September 2009   | $2,300             | Joe                       |

## Type Inferencing ##

Raw, tabular data must be converted to typed columns in order to be used by the `<data>` and `<chart>` tags. This is done by looking at each column, and inferring if the column's type should be **TEXT** **NUMBER** or **DATE**.

Any data values may be considered as TEXT, and this is the default column type. Columns will be converted to either **NUMBER** or **DATE** if enough of their columns "look like" that type. ChartMechanic tries to be lenient and pragmatic in parsing data. However, it's possible that not all values from your data can always be understood as **NUMBER** or **DATE** values. When a **NUMBER** or **DATE** cannot be successfully parsed as such, that cell is treated as if it had been empty; that is, there is no value for that cell. Such empty values will be omitted from any charts for that data as well.

Note that no type inferencing is needed when data is loaded from **SQL**: The results from the database are already typed, and the `<data>` tag simply maps JDBC types to one of our 3 types. Inferencing occurs only for raw data sources whose type information is not already known.

## NUMBER Formats ##

Currency symbols, exponential notation, commas, and decimal points are valid as part of a number. Extraneous alphabetical text, such as units, labels or other markers, generally invalidate a cell's consideration as a number:

| Example | Valid Number? |
|:--------|:--------------|
| 12,345  | yes           |
| 1.23E3  | yes           |
| $12,345 | yes           |
| €12,345.00 | yes           |
| ¥12,345 | yes           |
| 12345 dollars | **no**        |
| 12345 (see footnote) | **no**        |

## DATE Formats ##

The current list of supported date formats is shown below:

| Format | Example | Accepts time format? |
|:-------|:--------|:---------------------|
| MMM d, yyyy | Dec 5, 2006 | yes                  |
| MM/dd/yy | 12/05/06 | yes                  |
| MM-dd-yy | 12-05-06 | yes                  |
| yyyy-MM-dd | 2006-12-05 | yes                  |
| yyyyMMdd | 20061205 | yes                  |
| yyyy   | 2006    | no                   |
| yyyy.'0' | 2006.0  | no                   |
| 'FY' yy | FY 06   | no                   |
| dd-MMM-yy | 05-Dec-06 | yes                  |
| dd MMM yy | 05 Dec 06 | yes                  |
| dd-MMM | 05-Dec  | no                   |
| EE MMM dd yy | Tue Dec 05 06 | yes                  |
| EE MMM dd HH:mm:ss yy | Tue Dec 05 12:16:45 06 | no                   |
| MMM yy | Dec 06  | no                   |
| MMM-yy | Dec-06  | no                   |
| MMM yyyy | Dec 2006 | no                   |
| MMM-yyyy | Dec-2006 | no                   |
| MM/yy  | 12/06   | no                   |
| MM/yyyy | 12/2006 | no                   |
| EE MMM dd HH:mm:ss z yyyy | Tue Dec 05 12:16:45 PST 2006 | yes                  |
| dd MMM | 05 Dec  | yes                  |
| yyyy/MM | 2006/12 | no                   |
| yyyy-MM | 2006-12 | no                   |
| yyyy-MMM | 2006-Dec | no                   |
| yyyy MMM | 2006 Dec | no                   |
| yyyy-MMM'.' | 2006-Dec. | no                   |
| yyyy-MMM'.' d | 2006-Dec. 5 | no                   |
| yyyy.MM | 2006.12 | no                   |
| yyyy-MMM d | 2006-Dec 5 | no                   |
| dd MMM yyyy | 05 Dec 2006 | no                   |
| yyyy-MM-dd'T'HH:mm:ss.SSS | 2006-12-05T12:16:45.680 | no                   |
| yyyy-MM-dd'T'HH:mm:ss.SSSZ | 2006-12-05T12:16:45.680-0800 | no                   |
| yyyy-MM-dd'T'HH:mm:ss.SSS'Z' | 2006-12-05T12:16:45.680Z | no                   |
| quarter | 2006-Q4 | no                   |
| MMM. dd, yyyy | Dec. 05, 2006 | no                   |
| yyyyMMM | 2006Dec | no                   |
| yyyy'M'MM | 2006M12 | no                   |
| dd/MM/yy | 05/12/06 | no                   |
| HH:mm:ss | 12:16:45 | no                   |

## `<data>` Tag Example, CSV ##

```

<%-- CSV data of the 3 Stooges sales organization --%>
<%-- The first row of column names is optional, and not part of the "data"  --%>
<c:data id="stooge sales" type="CSV">
 Date,#Sales,Quota,Actual
 05/2010,7,$800,$500
 06/2010,2,$800,$800
 07/2010,8,$1000,$1300
 08/2010,9,$1000,$900
 09/2010,12,$1000,$2400
</c:data>

```

## `<data>` Tag Example, SQL ##

```

<%-- data from an SQL query on a JDBC data source --%>
<c:data id="weblog summary" type="SQL" jndiName="jdbc/DemoDS">
 SELECT rq_date,hits,bandwidth FROM web_log
 WHERE rq_date > '2010-01-01'
 ORDER BY rq_date ASC
</c:data>

```