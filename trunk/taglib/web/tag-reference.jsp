<%@ page import="com.bayareasoftware.tag.*" %>
<d:header/>
<div id="content">
<p>
<ul>
 <li><a href="#data"><code>&lt;data&gt;</code></a></li>
 <li><a href="#props"><code>&lt;props&gt;</code></a></li>
 <li><a href="#chart"><code>&lt;chart&gt;</code></a></li>
 <li><a href="#series"><code>&lt;series&gt;</code></a></li>
 <li><a href="#series-function"><code>&lt;series-function&gt;</code></a></li>
 <li><a href="#marker"><code>&lt;marker&gt;</code></a></li>
 <li><a href="#data-table"><code>&lt;data-table&gt;</code></a></li>
</ul>
</p>

<a name="data"></a><h2><code>data</code> tag</h2>
<p>
<d:doc-tag tag="<%= new DataTag() %>"/>
</p>

<a name="props"></a><h2><code>props</code> tag</h2>
<p>
<d:doc-tag tag="<%= new PropsTag() %>"/>
</p>

<a name="chart"></a><h2><code>chart</code> tag</h2>
<p>
<d:doc-tag tag="<%= new ChartTag() %>"/>
</p>

<a name="series"></a><h2><code>series</code> tag</h2>
<p>
<d:doc-tag tag="<%= new SeriesTag() %>"/>
</p>

<a name="series-function"></a><h2><code>series-function</code> tag</h2>
<p>
<d:doc-tag tag="<%= new SeriesFunctionTag() %>"/>
</p>

<a name="marker"></a><h2><code>marker</code> tag</h2>
<p>
<d:doc-tag tag="<%= new MarkerTag() %>"/>
</p>

<a name="data-table"></a><h2><code>data-table</code> tag</h2>
<p>
<d:doc-tag tag="<%= new DataTableTag() %>"/>
</p>

</div>
<d:footer/>
