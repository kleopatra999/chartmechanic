


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



<img src="chart-images/web-cache/0e0df2f8010209f7fc06fdf6f908f105.png" width="500" height="400" border="0" usemap="#0e0df2f8010209f7fc06fdf6f908f105"/>
<map id="0e0df2f8010209f7fc06fdf6f908f105" name="0e0df2f8010209f7fc06fdf6f908f105">
<area shape="poly" coords="329,48,397,48,397,164,329,164,329,164" href="javascript:alert('10.0 votes for cherry in CA');" alt=""/>
<area shape="poly" coords="329,164,397,164,397,175,329,175,329,175" href="javascript:alert('1.0 votes for kiwi in CA');" alt=""/>
<area shape="poly" coords="329,175,397,175,397,291,329,291,329,291" href="javascript:alert('10.0 votes for banana in CA');" alt=""/>
<area shape="poly" coords="329,291,397,291,397,372,329,372,329,372" href="javascript:alert('7.0 votes for apple in CA');" alt=""/>
<area shape="poly" coords="235,210,303,210,303,291,235,291,235,291" href="javascript:alert('7.0 votes for cherry in AZ');" alt=""/>
<area shape="poly" coords="235,291,303,291,303,303,235,303,235,303" href="javascript:alert('1.0 votes for kiwi in AZ');" alt=""/>
<area shape="poly" coords="235,303,303,303,303,314,235,314,235,314" href="javascript:alert('1.0 votes for banana in AZ');" alt=""/>
<area shape="poly" coords="235,314,303,314,303,372,235,372,235,372" href="javascript:alert('5.0 votes for apple in AZ');" alt=""/>
<area shape="poly" coords="141,245,209,245,209,256,141,256,141,256" href="javascript:alert('1.0 votes for cherry in MA');" alt=""/>
<area shape="poly" coords="141,256,209,256,209,303,141,303,141,303" href="javascript:alert('4.0 votes for kiwi in MA');" alt=""/>
<area shape="poly" coords="141,303,209,303,209,337,141,337,141,337" href="javascript:alert('3.0 votes for banana in MA');" alt=""/>
<area shape="poly" coords="141,337,209,337,209,372,141,372,141,372" href="javascript:alert('3.0 votes for apple in MA');" alt=""/>
<area shape="poly" coords="47,152,115,152,115,245,47,245,47,245" href="javascript:alert('8.0 votes for cherry in NY');" alt=""/>
<area shape="poly" coords="47,245,115,245,115,279,47,279,47,279" href="javascript:alert('3.0 votes for kiwi in NY');" alt=""/>
<area shape="poly" coords="47,279,115,279,115,349,47,349,47,349" href="javascript:alert('6.0 votes for banana in NY');" alt=""/>
<area shape="poly" coords="47,349,115,349,115,372,47,372,47,372" href="javascript:alert('2.0 votes for apple in NY');" alt=""/>
</map>
</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/favorite-fruits.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data id=&quot;fruits&quot;&gt;<br>name,votes,state<br>apple,2,NY<br>apple,3,MA<br>apple,5,AZ<br>apple,7,CA<br>banana,10,CA<br>banana,6,NY<br>banana,3,MA<br>banana,1,AZ<br>kiwi,1,CA<br>kiwi,2,NY<br>kiwi,4,MA<br>kiwi,1,AZ<br>kiwi,3,NY<br>cherry,10,CA<br>cherry,8,NY<br>cherry,1,MA<br>cherry,7,AZ<br>&lt;/c:data&gt;<br>&lt;c:chart title=&quot;Favorite Fruits by U.S. State&quot; border=&quot;0&quot;&gt;<br>&lt;c:series name=&quot;fruits&quot; datasource=&quot;fruits&quot; <br> &nbsp;graphType=&quot;Stacked Bar 3D&quot; x=&quot;3&quot; y=&quot;2&quot;<br> &nbsp;dynamicNameColumn=&quot;1&quot;<br> &nbsp;linkExpression=&quot;javascript:alert('{2} votes for {series} in {3}');&quot;&gt;<br>renderer.drawBarOutline=true<br>renderer.outlineStroke=0.25<br>renderer.outlinePaint=#d0d0d0<br>&lt;/c:series&gt;<br>&lt;c:props&gt;<br>chart.backgroundPaint=gradient:#bbeaff,#cccccc,0,0,0,400,true<br>chart.backgroundImageAlpha=.05<br>plot.foregroundAlpha=0.9<br>legend.backgroundPaint=#ffffff00<br>legend.border=0<br>legend.position=right<br>range-axis-0.tickUnit=4<br>range-axis-0.axisLineVisible=false<br>range-axis-0.tickMarksVisible=false<br>domain-axis.axisLineVisible=false<br>domain-axis.tickMarksVisible=false<br>title.Paint=#606060<br>&lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>


<div class="item-div">
 <div class="chart-div">
 
 
<img src="chart-images/web-cache/f6f1050b0af5f10ef00ef70b0d0ff5f5.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-category.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart&gt;<br> &lt;c:series name=&quot;Favorite Stooge Votes&quot; datasource=&quot;d1&quot; x=&quot;3&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=Stooge Preferences (Category Plot)<br>renderer.shadowVisible=false<br>renderer.drawBarOutline=true<br>plot.foregroundAlpha=.8<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
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

<div class="item-div">
<div class="chart-div">
 
 
<img src="chart-images/web-cache/fe0f01f9f303ff00fc0402050c07f6f4.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-pie.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart plotType=&quot;pie3d&quot; template=&quot;sky&quot;&gt;<br> &lt;c:series name=&quot;Stooges&quot; datasource=&quot;d1&quot; x=&quot;3&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=Stooge Preferences (Pie)<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
<div class="chart-div">
 
 


<img src="chart-images/web-cache/0501f407fbfff0fe0af70d07fcf80cfa.png" width="500" height="400"/>


</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/timeseries1.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart renderType=&quot;Bar&quot; template=&quot;shadow&quot;&gt;<br> &lt;c:series name=&quot;My Values&quot; datasource=&quot;d1&quot;<br> &nbsp; &nbsp;x=&quot;1&quot; y=&quot;2&quot; timeperiod=&quot;year&quot; color=&quot;dark_green&quot;/&gt;<br> &lt;c:props&gt;<br>title.text=A Time Series<br> &lt;/c:props&gt;<br>&lt;c:marker name=&quot;x axis line&quot; xaxis=&quot;true&quot; dateValues=&quot;2007-07-01&quot; color=&quot;red&quot;&gt;<br>stroke=line=2.0<br>&lt;/c:marker&gt;<br>&lt;c:marker name=&quot;x axis band&quot; xaxis=&quot;true&quot; dateValues=&quot;2009-01-01|2010-01-01&quot; color=&quot;gray&quot;&gt;<br>layer=foreground<br>&lt;/c:marker&gt;<br>&lt;/c:chart&gt;<br><br></pre></code>
</div>
 </div>
</div>

<div class="item-div">
 <div class="chart-div">

 
 
 
 
<img src="chart-images/web-cache/fd0ef6030af6f20d0ef901fcfdf2fafd.png" width="500" height="400" border="0" usemap="#fd0ef6030af6f20d0ef901fcfdf2fafd"/>
<map id="fd0ef6030af6f20d0ef901fcfdf2fafd" name="fd0ef6030af6f20d0ef901fcfdf2fafd">
<area shape="poly" coords="129,349,129,363,96,363,96,349,96,349" href="javascript:alert('Italy won 4.0 Bronze medals');" alt=""/>
<area shape="poly" coords="344,330,344,344,261,344,261,330,261,330" href="javascript:alert('80+countries won 10.0 Bronze medals');" alt=""/>
<area shape="poly" coords="261,330,261,344,137,344,137,330,137,330" href="javascript:alert('80+countries won 15.0 Silver medals');" alt=""/>
<area shape="poly" coords="137,330,137,344,96,344,96,330,96,330" href="javascript:alert('80+countries won 5.0 Gold medals');" alt=""/>
<area shape="poly" coords="145,311,145,325,129,325,129,311,129,311" href="javascript:alert('Poland won 2.0 Bronze medals');" alt=""/>
<area shape="poly" coords="129,311,129,325,104,325,104,311,104,311" href="javascript:alert('Poland won 3.0 Silver medals');" alt=""/>
<area shape="poly" coords="104,311,104,325,96,325,96,311,96,311" href="javascript:alert('Poland won 1.0 Gold medals');" alt=""/>
<area shape="poly" coords="145,292,145,306,112,306,112,292,112,292" href="javascript:alert('Czech+Republic won 4.0 Bronze medals');" alt=""/>
<area shape="poly" coords="112,292,112,306,112,306,112,292,112,292" href="javascript:alert('Czech+Republic won 0.0 Silver medals');" alt=""/>
<area shape="poly" coords="112,292,112,306,96,306,96,292,96,292" href="javascript:alert('Czech+Republic won 2.0 Gold medals');" alt=""/>
<area shape="poly" coords="162,273,162,287,137,287,137,273,137,273" href="javascript:alert('Netherlands won 3.0 Bronze medals');" alt=""/>
<area shape="poly" coords="137,273,137,287,129,287,129,273,129,273" href="javascript:alert('Netherlands won 1.0 Silver medals');" alt=""/>
<area shape="poly" coords="129,273,129,287,96,287,96,273,96,273" href="javascript:alert('Netherlands won 4.0 Gold medals');" alt=""/>
<area shape="poly" coords="170,254,170,268,145,268,145,254,145,254" href="javascript:alert('Switzerland won 3.0 Bronze medals');" alt=""/>
<area shape="poly" coords="145,254,145,268,145,268,145,254,145,254" href="javascript:alert('Switzerland won 0.0 Silver medals');" alt=""/>
<area shape="poly" coords="145,254,145,268,96,268,96,254,96,254" href="javascript:alert('Switzerland won 6.0 Gold medals');" alt=""/>
<area shape="poly" coords="187,235,187,249,137,249,137,235,137,235" href="javascript:alert('France won 6.0 Bronze medals');" alt=""/>
<area shape="poly" coords="137,235,137,249,112,249,112,235,112,235" href="javascript:alert('France won 3.0 Silver medals');" alt=""/>
<area shape="poly" coords="112,235,112,249,96,249,96,235,96,235" href="javascript:alert('France won 2.0 Gold medals');" alt=""/>
<area shape="poly" coords="187,216,187,230,154,230,154,216,154,216" href="javascript:alert('Sweden won 4.0 Bronze medals');" alt=""/>
<area shape="poly" coords="154,216,154,230,137,230,137,216,137,216" href="javascript:alert('Sweden won 2.0 Silver medals');" alt=""/>
<area shape="poly" coords="137,216,137,230,96,230,96,216,96,216" href="javascript:alert('Sweden won 5.0 Gold medals');" alt=""/>
<area shape="poly" coords="187,197,187,211,154,211,154,197,154,197" href="javascript:alert('China won 4.0 Bronze medals');" alt=""/>
<area shape="poly" coords="154,197,154,211,137,211,137,197,137,197" href="javascript:alert('China won 2.0 Silver medals');" alt=""/>
<area shape="poly" coords="137,197,137,211,96,211,96,197,96,197" href="javascript:alert('China won 5.0 Gold medals');" alt=""/>
<area shape="poly" coords="212,178,212,192,195,192,195,178,195,178" href="javascript:alert('South+Korea won 2.0 Bronze medals');" alt=""/>
<area shape="poly" coords="195,178,195,192,145,192,145,178,145,178" href="javascript:alert('South+Korea won 6.0 Silver medals');" alt=""/>
<area shape="poly" coords="145,178,145,192,96,192,96,178,96,178" href="javascript:alert('South+Korea won 6.0 Gold medals');" alt=""/>
<area shape="poly" coords="220,159,220,174,162,174,162,159,162,159" href="javascript:alert('Russia won 7.0 Bronze medals');" alt=""/>
<area shape="poly" coords="162,159,162,174,120,174,120,159,120,159" href="javascript:alert('Russia won 5.0 Silver medals');" alt=""/>
<area shape="poly" coords="120,159,120,174,96,174,96,159,96,159" href="javascript:alert('Russia won 3.0 Gold medals');" alt=""/>
<area shape="poly" coords="228,140,228,155,178,155,178,140,178,140" href="javascript:alert('Austria won 6.0 Bronze medals');" alt=""/>
<area shape="poly" coords="178,140,178,155,129,155,129,140,129,140" href="javascript:alert('Austria won 6.0 Silver medals');" alt=""/>
<area shape="poly" coords="129,140,129,155,96,155,96,140,96,140" href="javascript:alert('Austria won 4.0 Gold medals');" alt=""/>
<area shape="poly" coords="286,121,286,136,236,136,236,121,236,121" href="javascript:alert('Norway won 6.0 Bronze medals');" alt=""/>
<area shape="poly" coords="236,121,236,136,170,136,170,121,170,121" href="javascript:alert('Norway won 8.0 Silver medals');" alt=""/>
<area shape="poly" coords="170,121,170,136,96,136,96,121,96,121" href="javascript:alert('Norway won 9.0 Gold medals');" alt=""/>
<area shape="poly" coords="311,102,311,117,270,117,270,102,270,102" href="javascript:alert('Canada won 5.0 Bronze medals');" alt=""/>
<area shape="poly" coords="270,102,270,117,212,117,212,102,212,102" href="javascript:alert('Canada won 7.0 Silver medals');" alt=""/>
<area shape="poly" coords="212,102,212,117,96,117,96,102,96,102" href="javascript:alert('Canada won 14.0 Gold medals');" alt=""/>
<area shape="poly" coords="344,83,344,98,286,98,286,83,286,83" href="javascript:alert('Germany won 7.0 Bronze medals');" alt=""/>
<area shape="poly" coords="286,83,286,98,178,98,178,83,178,83" href="javascript:alert('Germany won 13.0 Silver medals');" alt=""/>
<area shape="poly" coords="178,83,178,98,96,98,96,83,96,83" href="javascript:alert('Germany won 10.0 Gold medals');" alt=""/>
<area shape="poly" coords="402,64,402,79,294,79,294,64,294,64" href="javascript:alert('United+States won 13.0 Bronze medals');" alt=""/>
<area shape="poly" coords="294,64,294,79,170,79,170,64,170,64" href="javascript:alert('United+States won 15.0 Silver medals');" alt=""/>
<area shape="poly" coords="170,64,170,79,96,79,96,64,96,64" href="javascript:alert('United+States won 9.0 Gold medals');" alt=""/>
</map>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/olympics.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:data id=&quot;medals&quot; type=&quot;csv&quot;<br> &nbsp; url=&quot;/data/olympic-medals-vancouver.csv&quot;/&gt;<br>&lt;c:chart title=&quot;Winter Olympic Medals, Vancouver 2010 (click me!)&quot;<br> &nbsp; graphType=&quot;Stacked Bar 3D&quot; border=&quot;0&quot;&gt;<br> &lt;c:props&gt;<br>plot.orientation=HORIZONTAL<br>chart.backgroundImage=NONE<br>legend.position=right<br>description=Top 15 countries winning medals at the 2010 winter olympic games<br>plot.foregroundAlpha=.9<br>domain-axis.axisLineVisible=false<br>domain-axis.tickMarksVisible=false<br> &lt;/c:props&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Gold&quot; x=&quot;2&quot; y=&quot;3&quot; paint=&quot;#ffdd70&quot;<br> &nbsp; &nbsp;linkExpression=&quot;javascript:alert('{2} won {3} {series} medals');&quot;/&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Silver&quot; x=&quot;2&quot; y=&quot;4&quot; paint=&quot;#c0c0c0&quot;<br> &nbsp; &nbsp;linkExpression=&quot;javascript:alert('{2} won {4} {series} medals');&quot;/&gt;<br> &lt;c:series datasource=&quot;medals&quot; name=&quot;Bronze&quot; x=&quot;2&quot; y=&quot;5&quot; paint=&quot;#CD7F32&quot;<br> &nbsp; &nbsp;linkExpression=&quot;javascript:alert('{2} won {5} {series} medals');&quot;/&gt;<br>&lt;/c:chart&gt;<br>&lt;%--&lt;c:data-table datasource=&quot;medals&quot; length=&quot;25&quot;/&gt;--%&gt;<br></pre></code>
</div>
 </div>
</div>


<div class="item-div">
 <div class="chart-div">





 
<img src="chart-images/web-cache/faff0cf3fcf8f5fd06030a0e0df5fefe.png" width="500" height="400"/>

</div>
 
 <div class="source-div"><div id="title"><a href="source/charts/weblog.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart template=&quot;sunny&quot;&gt;<br>&lt;c:data id=&quot;log stats&quot; type=&quot;CSV&quot;&gt;<br>2010-07-01,413,9.27187315E2<br>2010-07-02,117,7.28394634E2<br>2010-07-03,102,1.05<br>2010-07-04,100,6.05151615E2<br>2010-07-05,159,2.5<br>2010-07-06,223,4.56410034E2<br>2010-07-07,107,4.60074102E2<br>2010-07-08,346,6.7<br>2010-07-09,93,3.1<br>2010-07-10,124,1.856196822E3<br>2010-07-11,114,6.0524204E2<br>2010-07-12,240,1.53293522E2<br>2010-07-13,174,4.50552268E2<br>2010-07-14,130,3.02573846E2<br>2010-07-15,157,1.52319428E2<br>2010-07-16,68,3.8<br>2010-07-17,70,1.50015679E2<br>2010-07-18,389,2.40<br>2010-07-19,1312,1.53013693E2<br>2010-07-20,716,3.03350403E2<br>2010-07-21,262,1.51901641E2<br>2010-07-22,414,7.56432768E2<br>&lt;/c:data&gt;<br>&lt;c:series name=&quot;Hits&quot; datasource=&quot;log stats&quot; x=&quot;1&quot; y=&quot;2&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;yaxis=&quot;0&quot; render=&quot;Line And Shape&quot;&gt;<br>renderer.shape=circle<br>renderer.useOutlinePaint=true<br>renderer.outlinePaint=gray<br>renderer.outlineStroke=line=2.0|dash=0<br>renderer.useFillPaint=true<br>renderer.fillPaint=orange<br>&lt;/c:series&gt;<br>&lt;c:series name=&quot;Bandwidth&quot; datasource=&quot;log stats&quot; x=&quot;1&quot; y=&quot;3&quot;<br> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;yaxis=&quot;1&quot; render=&quot;Area&quot; color=&quot;gray&quot;/&gt;<br>&lt;c:marker value=&quot;600&quot; name=&quot;SIX HUNDRED&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=light_blue<br>&lt;/c:marker&gt;<br>&lt;c:marker bandValues=&quot;150|200|700|775&quot; name=&quot;yellow band&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=yellow<br>layer=background<br>alpha=0.5<br>&lt;/c:marker&gt;<br> &lt;c:props&gt;<br>title.text=Web Log Summary (July '10)<br>legend.position=bottom<br>legend.verticalAlignment=bottom<br>legend.visible=true<br> &lt;/c:props&gt;<br>&lt;/c:chart&gt;<br></pre></code>
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
 
  

<img src="chart-images/web-cache/fd06f1f7f5040c02f7f103f000f5fafa.png" width="500" height="400" border="0" usemap="#fd06f1f7f5040c02f7f103f000f5fafa"/>
<map id="fd06f1f7f5040c02f7f103f000f5fafa" name="fd06f1f7f5040c02f7f103f000f5fafa">
<area shape="rect" coords="370,290,482,352" href="javascript:alert('Larry sold 6.0 in 2010-08-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="258,279,370,351" href="javascript:alert('Larry sold 7.0 in 2010-07-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="149,290,257,352" href="javascript:alert('Larry sold 6.0 in 2010-06-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="36,300,148,352" href="javascript:alert('Larry sold 5.0 in 2010-05-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="370,248,482,289" href="javascript:alert('Moe sold 4.0 in 2010-08-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="258,196,370,279" href="javascript:alert('Moe sold 8.0 in 2010-07-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="149,217,257,289" href="javascript:alert('Moe sold 7.0 in 2010-06-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="36,237,148,299" href="javascript:alert('Moe sold 6.0 in 2010-05-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="370,237,482,247" href="javascript:alert('Shemp sold 1.0 in 2010-08-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="258,352,370,353" href="javascript:alert('Shemp sold 0.0 in 2010-07-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="149,206,257,216" href="javascript:alert('Shemp sold 1.0 in 2010-06-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="36,217,148,237" href="javascript:alert('Shemp sold 2.0 in 2010-05-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="370,50,482,237" href="javascript:alert('Curly sold 18.0 in 2010-08-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="258,40,370,196" href="javascript:alert('Curly sold 15.0 in 2010-07-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="149,92,257,206" href="javascript:alert('Curly sold 11.0 in 2010-06-01+00%3A00%3A00');" alt=""/>
<area shape="rect" coords="36,123,148,216" href="javascript:alert('Curly sold 9.0 in 2010-05-01+00%3A00%3A00');" alt=""/>
</map></div>
 
 <div class="source-div"><div id="title"><a href="source/charts/stooge-multi.jspf"><b>source</b></a></div>
  <div id="source-content"><code><pre>
&lt;c:chart plotType=&quot;time&quot; title=&quot;Stooge Sales by Month (click me!)&quot; border=&quot;0&quot;&gt;<br> &lt;c:series datasource=&quot;all stooge sales&quot; <br> &nbsp;y=&quot;3&quot; name=&quot;multi&quot; dynamicNameColumn=&quot;2&quot;<br> &nbsp;render=&quot;Stacked Bar&quot; timeperiod=&quot;month&quot;<br> &nbsp;linkExpression=&quot;javascript:alert('{2} sold {3} in {1}');&quot;/&gt;<br> &nbsp;&lt;c:props&gt;<br>chart.backgroundImage=none<br>range-axis-0.tickUnit=5<br> &nbsp;&lt;/c:props&gt;<br>&lt;c:marker value=&quot;25&quot; name=&quot;QUOTA TARGET = 25&quot; yaxis=&quot;0&quot; template=&quot;y-marker&quot;&gt;<br>paint=#808080<br>stroke=line=3.0|dash=1<br>labelPaint=black<br>labelAnchor=TOP_LEFT<br>labelTextAnchor=HALF_ASCENT_LEFT<br>&lt;/c:marker&gt;<br>&lt;/c:chart&gt;</pre></code>
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
