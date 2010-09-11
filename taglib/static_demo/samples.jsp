


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
<li id="selected">Samples</li>
<li><a href="tag-reference.jsp">Tag Reference</a></li>
<li><a href="property-reference.jsp">Property Reference</a></li>
<li><a href="faq.jsp">FAQ</a></li>

</ul>
</div>


<div id="content">
<div class="setup-div">
Declare some templates, to setup a common look &amp; feel for all charts.







<div class="text-right"> <a href="source/charts/templates.jspf">View Source</a></div>

</div>

<div class="setup-div">
Declare some global data sources, if necessary, to be shared by multiple charts.





<div class="text-right"> <a href="source/charts/data-sources.jspf">View Source</a></div>

</div>

<p>
Then start drawing charts:
</p>

<div class="item-div">
 <div class="chart-div">
 
 
<img src="chart-images/web-cache/fdf4090efdfe0700f10d0ff90804f109.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-category.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart&gt;<br> &lt;c:series name=&quot;Stooge Count&quot; datasource=&quot;d1&quot; x=&quot;3&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=Stooge Preferences (Category Plot)<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
<div class="chart-div">
 
 
<img src="chart-images/web-cache/0df6f0090ef507030a0cfe05f1fb03fb.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-pie.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart plotType=&quot;pie3d&quot;&gt;<br> &lt;c:series name=&quot;Stooges&quot; datasource=&quot;d1&quot; x=&quot;3&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=Stooge Preferences (Pie)<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
<div class="chart-div">
 
 


<img src="chart-images/web-cache/f3f60206f00cf5f10b04060200f003fa.png" width="500" height="400"/>


</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/timeseries1.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart renderType=&quot;Bar&quot;&gt;<br> &lt;c:series name=&quot;My Values&quot; datasource=&quot;d1&quot;<br> &nbsp; &nbsp;x=&quot;1&quot; y=&quot;2&quot; timeperiod=&quot;year&quot; color=&quot;dark_green&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=A Time Series<br> &lt;/c:props&gt;<br>&lt;c:marker name=&quot;x axis line&quot; xaxis=&quot;true&quot; dateValues=&quot;2007-07-01&quot; color=&quot;red&quot;&gt;<br>stroke=line=2.0<br>&lt;/c:marker&gt;<br>&lt;c:marker name=&quot;x axis band&quot; xaxis=&quot;true&quot; dateValues=&quot;2009-01-01|2010-01-01&quot; color=&quot;gray&quot;&gt;<br>layer=foreground<br>&lt;/c:marker&gt;<br>&lt;/c:chart&gt;<br><br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
<div class="chart-div">



<img src="chart-images/web-cache/fb090800f3ff030d02f20c08f5f806fb.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/favorite-fruits.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data id=&quot;fruits&quot;&gt;<br>name,votes,state<br>apple,1,CA<br>apple,2,NY<br>apple,3,MA<br>apple,5,AZ<br>apple,7,CA<br>banana,10,CA<br>banana,6,NY<br>banana,3,MA<br>banana,1,AZ<br>kiwi,1,CA<br>kiwi,2,NY<br>kiwi,4,MA<br>kiwi,1,AZ<br>kiwi,3,NY<br>cherry,10,CA<br>cherry,8,NY<br>cherry,1,MA<br>cherry,7,AZ<br>&lt;/c:data&gt;<br>&lt;c:chart title=&quot;Favorite Fruits by U.S. State&quot;&gt;<br>&lt;c:series name=&quot;fruits&quot; datasource=&quot;fruits&quot; <br> &nbsp;graphType=&quot;Stacked Bar 3D&quot; x=&quot;3&quot; y=&quot;2&quot;<br> &nbsp;dynamicNameColumn=&quot;1&quot;&gt;<br>renderer.drawBarOutline=true<br>renderer.outlineStroke=0.25<br>renderer.outlinePaint=#d0d0d0<br>&lt;/c:series&gt;<br>&lt;c:props&gt;<br>chart.backgroundPaint=gradient:#bbeaff,#cccccc,0,0,0,400,true<br>chart.backgroundImageAlpha=.05<br>plot.foregroundAlpha=0.9<br>legend.backgroundPaint=#ffffff00<br>legend.border=0<br>legend.position=right<br>range-axis-0.tickUnit=4<br>range-axis-0.axisLineVisible=false<br>range-axis-0.tickMarksVisible=false<br>domain-axis.axisLineVisible=false<br>domain-axis.tickMarksVisible=false<br>title.Paint=#606060<br>&lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">





 
<img src="chart-images/web-cache/fe030f080503f6f103f0010e02f0f6f2.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/weblog.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart&gt;<br>&lt;c:data id=&quot;log stats&quot; type=&quot;CSV&quot;&gt;<br>2010-07-01,413,9.27187315E2<br>2010-07-02,117,7.28394634E2<br>2010-07-03,102,1.05<br>2010-07-04,100,6.05151615E2<br>2010-07-05,159,2.5<br>2010-07-06,223,4.56410034E2<br>2010-07-07,107,4.60074102E2<br>2010-07-08,346,6.7<br>2010-07-09,93,3.1<br>2010-07-10,124,1.856196822E3<br>2010-07-11,114,6.0524204E2<br>2010-07-12,240,1.53293522E2<br>2010-07-13,174,4.50552268E2<br>2010-07-14,130,3.02573846E2<br>2010-07-15,157,1.52319428E2<br>2010-07-16,68,3.8<br>2010-07-17,70,1.50015679E2<br>2010-07-18,389,2.40<br>2010-07-19,1312,1.53013693E2<br>2010-07-20,716,3.03350403E2<br>2010-07-21,262,1.51901641E2<br>2010-07-22,414,7.56432768E2<br>&lt;/c:data&gt;<br>&lt;c:series name=&quot;Hits&quot; datasource=&quot;log stats&quot; x=&quot;1&quot; y=&quot;2&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;yaxis=&quot;0&quot; render=&quot;Line And Shape&quot;&gt;<br>renderer.shape=circle<br>renderer.useOutlinePaint=true<br>renderer.outlinePaint=gray<br>renderer.outlineStroke=line=2.0|dash=0<br>renderer.useFillPaint=true<br>renderer.fillPaint=orange<br>&lt;/c:series&gt;<br>&lt;c:series name=&quot;Bandwidth&quot; datasource=&quot;log stats&quot; x=&quot;1&quot; y=&quot;3&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;yaxis=&quot;1&quot; render=&quot;Area&quot; color=&quot;gray&quot;/&gt;<br>&lt;c:marker value=&quot;600&quot; name=&quot;SIX&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=light_blue<br>&lt;/c:marker&gt;<br>&lt;c:marker bandValues=&quot;50|100|150|200&quot; name=&quot;pink band&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=pink<br>&lt;/c:marker&gt;<br> &lt;c:props&gt;<br>title.text=Web Log Summary (July '10)<br>legend.position=right<br>legend.verticalAlignment=bottom<br>legend.visible=true<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">






<img src="chart-images/web-cache/0bf4020408f505fdfdf50f0bf10dfc03.png" width="500" height="400"/>


</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/xy1.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart plotType=&quot;xy&quot;&gt;<br>&lt;c:data id=&quot;random&quot; type=&quot;SQL&quot; jndiName=&quot;jdbc/DemoDS&quot;&gt;<br>select x,y from random<br>&lt;/c:data&gt;<br>&lt;c:series name=&quot;random series&quot; datasource=&quot;random&quot; paint=&quot;red&quot;/&gt;<br>&lt;c:props&gt;<br>renderer.shape=circle<br>renderer.stroke=2.0<br>chart.backgroundPaint=#a0a0a0<br>#plot.backgroundAlpha=1<br>&lt;/c:props&gt;<br>&lt;c:marker name=&quot;x-axis band&quot; xaxis=&quot;true&quot; value=&quot;3|4&quot; color=&quot;blue&quot;/&gt;<br>&lt;c:marker value=&quot;2|6&quot; name=&quot;y-axis band&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=#e0e0e0<br>layer=background<br>labelVisible=true<br>#label=My Band<br>labelPaint=#000000<br>alpha=0.7<br>&lt;/c:marker&gt;<br>&lt;c:marker-function name=&quot;AVERAGE&quot; series=&quot;random series&quot; function=&quot;AVG&quot;&gt;<br>stroke=line=2.0|dash=1<br>paint=yellow<br>labelAnchor=CENTER<br>labelFont=Arial-bold-14<br>&lt;/c:marker-function&gt;<br>&lt;/c:chart&gt;<br><br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">
 
  
<img src="chart-images/web-cache/0c02f5040dfe04f60b0bfd0d0a07000b.png" width="500" height="400"/>
</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-multi.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart plotType=&quot;time&quot; title=&quot;Stooge Sales by Month&quot;&gt;<br> &lt;c:series datasource=&quot;all stooge sales&quot; <br> &nbsp;y=&quot;3&quot; name=&quot;multi&quot; dynamicNameColumn=&quot;2&quot;<br> &nbsp;render=&quot;Stacked Bar&quot; timeperiod=&quot;month&quot;/&gt;<br> &nbsp;&lt;c:props&gt;<br>chart.backgroundImage=/demo/images/smiley-600.png &nbsp;<br> &nbsp;&lt;/c:props&gt;<br>&lt;/c:chart&gt;</pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">

 
 
 
 
 
 
 
 
 
<img src="chart-images/web-cache/f3fafef0f60cfdf70bf107f200f4f8f8.png" width="500" height="400"/>


</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/timeseries2.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data type=&quot;SQL&quot; id=&quot;random walk&quot; driver=&quot;org.h2.Driver&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp;url=&quot;jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1&quot; user=&quot;sa&quot; password=&quot;bogus&quot;&gt;<br> &nbsp;SELECT * FROM RANDOM_WALK<br>&lt;/c:data&gt;<br>&lt;c:chart plotType=&quot;time&quot; graphType=&quot;Line&quot; title=&quot;random walk&quot;&gt;<br> &lt;c:props&gt;<br>renderer.stroke=3.0<br>plot.foregroundAlpha=1.0<br> &lt;/c:props&gt;<br> &lt;c:series name=&quot;W&quot; datasource=&quot;random walk&quot; y=&quot;2&quot; visible=&quot;false&quot;/&gt;<br> &lt;c:series-function series=&quot;W&quot; function=&quot;MVAVG&quot; args=&quot;20&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; name=&quot;W MA-20&quot; visible=&quot;false&quot;/&gt;<br> &lt;c:series-function series=&quot;W&quot; function=&quot;timechange&quot; args=&quot;month|false&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; name=&quot;W MoM % change&quot; yaxis=&quot;1&quot;/&gt;<br> &lt;c:series name=&quot;X&quot; datasource=&quot;random walk&quot; y=&quot;3&quot; visible=&quot;false&quot;/&gt;<br> &lt;c:series name=&quot;Y&quot; datasource=&quot;random walk&quot; y=&quot;4&quot; visible=&quot;false&quot;/&gt;<br> &lt;c:series name=&quot;Z&quot; datasource=&quot;random walk&quot; y=&quot;5&quot; visible=&quot;false&quot;/&gt;<br> &lt;c:series-function series=&quot;Y&quot; function=&quot;timejoin&quot; name=&quot;Y+Z&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; args=&quot;+|Z|day&quot;/&gt;<br> &lt;c:marker xaxis=&quot;true&quot; color=&quot;red&quot;<br> &nbsp;date=&quot;2010-07-04&quot; name=&quot;Independence Day&quot;&gt;<br>stroke=3.0<br> &lt;/c:marker&gt;<br>&lt;/c:chart&gt;<br><br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">

 
 
 
 
<img src="chart-images/web-cache/0d0a06f7f60f0905faf80ff80002fff5.png" width="500" height="400"/>


</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/olympics.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data id=&quot;medals&quot; type=&quot;csv&quot;<br> &nbsp; url=&quot;/data/olympic-medals-vancouver.csv&quot;/&gt;<br>&lt;c:chart title=&quot;Winter Olympic Medals, Vancouver 2010&quot;<br> &nbsp; graphType=&quot;Stacked Bar 3D&quot;&gt;<br> &lt;c:props&gt;<br>plot.orientation=HORIZONTAL<br>chart.backgroundImage=NONE<br>legend.position=right<br>description=Top 15 countries winning medals at the 2010 winter olympic games<br>plot.foregroundAlpha=.9<br>domain-axis.axisLineVisible=false<br>domain-axis.tickMarksVisible=false<br> &lt;/c:props&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Gold&quot; x=&quot;2&quot; y=&quot;3&quot; paint=&quot;#ffdd70&quot;/&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Silver&quot; x=&quot;2&quot; y=&quot;4&quot; paint=&quot;#c0c0c0&quot;/&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Bronze&quot; x=&quot;2&quot; y=&quot;5&quot; paint=&quot;#CD7F32&quot;/&gt;<br>&lt;/c:chart&gt;<br>&lt;%--&lt;c:data-table datasource=&quot;medals&quot; length=&quot;25&quot;/&gt;--%&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">






<img src="chart-images/web-cache/f406f408fe04f204fbf6f2f60ff70407.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/djia.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data id=&quot;DJIA&quot; url=&quot;/data/DJIA-monthly.csv&quot;/&gt;<br>&lt;c:data id=&quot;recessions&quot; url=&quot;/data/us-recessions.csv&quot;/&gt;<br>&lt;c:chart title=&quot;Dow Jones Monthly (log axis)&quot;<br> &nbsp; plotType=&quot;time&quot; graphType=&quot;Line&quot;&gt;<br>&lt;c:series datasource=&quot;DJIA&quot; name=&quot;Dow&quot;/&gt;<br>&lt;c:series-function series=&quot;Dow&quot; function=&quot;inflation&quot; args=&quot;1990&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;name=&quot;Dow (inflation adjusted, 1990)&quot;/&gt;<br>&lt;c:marker name=&quot;Recession&quot; color=&quot;#c0c0c0&quot; xaxis=&quot;true&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; datasource=&quot;recessions&quot;&gt;<br>layer=foreground<br>alpha=0.5<br>&lt;/c:marker&gt;<br>&lt;c:props&gt;<br>chart.backgroundImage=none<br>renderer.stroke=2.0<br>plot.foregroundAlpha=1.0<br>range-axis-0.axisType=log<br>range-axis-0.axisLocation=right<br>description=Dow Jones Industrial Average,<br> nominal and inflation-adjusted<br>&lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<p>
<b>&lt;data-table&gt; tag example</b>
<table><tr>
<th>C1<br/>
(DATE)<br/>
(MM/yy)</th><th>C2<br/>
(TEXT)</th><th>C3<br/>
(NUMBER)</th></tr>
<tr class="even">
<td>2010-05-01 12:00:00</td>
<td>Larry</td>
<td>5.0</td>
</tr>
<tr class="odd">
<td>2010-05-01 12:00:00</td>
<td>Moe</td>
<td>6.0</td>
</tr>
<tr class="even">
<td>2010-05-01 12:00:00</td>
<td>Shemp</td>
<td>2.0</td>
</tr>
<tr class="odd">
<td>2010-05-01 12:00:00</td>
<td>Curly</td>
<td>9.0</td>
</tr>
<tr class="even">
<td>2010-06-01 12:00:00</td>
<td>Larry</td>
<td>6.0</td>
</tr>
<tr class="odd">
<td>2010-06-01 12:00:00</td>
<td>Moe</td>
<td>7.0</td>
</tr>
<tr class="even">
<td>2010-06-01 12:00:00</td>
<td>Curly</td>
<td>11.0</td>
</tr>
<tr class="odd">
<td>2010-06-01 12:00:00</td>
<td>Shemp</td>
<td>1.0</td>
</tr>
<tr class="even">
<td>2010-07-01 12:00:00</td>
<td>Larry</td>
<td>7.0</td>
</tr>
<tr class="odd">
<td>2010-07-01 12:00:00</td>
<td>Moe</td>
<td>8.0</td>
</tr>
<tr class="even">
<td>2010-07-01 12:00:00</td>
<td>Curly</td>
<td>15.0</td>
</tr>
<tr class="odd">
<td>2010-07-01 12:00:00</td>
<td>Shemp</td>
<td>0.0</td>
</tr>
<tr class="even">
<td>2010-08-01 12:00:00</td>
<td>Larry</td>
<td>6.0</td>
</tr>
<tr class="odd">
<td>2010-08-01 12:00:00</td>
<td>Moe</td>
<td>4.0</td>
</tr>
<tr class="even">
<td>2010-08-01 12:00:00</td>
<td>Curly</td>
<td>18.0</td>
</tr>
<tr class="odd">
<td>2010-08-01 12:00:00</td>
<td>Shemp</td>
<td>1.0</td>
</tr>
</table>

</p>
</div>
</body>
</html>
