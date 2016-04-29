> Chart servlet configuration.  They may be specified in web.xml, as
> init-param's to the ChartServlet, or else via java system
> properties by the same name.

> In the case that both are specified, system properties will take precedence over init-param's in web.xml.

> If you elect to do the configuration
> via init-param's as shown below, then you should also keep
> the ChartServlet with a load-on-startup of greater than zero.
> Otherwise, the chart tag will run before the servlet is initialized,
> and you will get broken image links for the first time that the
> chart tag runs.
```
  <init-param>
    <description>
     Specifies a path on the local disk
    </description>
    <param-name>cm.chartCacheDir</param-name>
    <param-value>/tmp/chart-cache</param-value>
  </init-param>
  <init-param>
    <description>
     Specifies a TTL in seconds, for the chart cache.
     A value of 0 means the cache is never used.
    </description>
    <param-name>cm.chartCacheTTL</param-name>
    <param-value>60</param-value>
  </init-param>
  <init-param>
    <description>
      This *MUST* match the url-pattern of the ChartServlet
      mapping stanza, without the wildcard (*).  The servlet
      API provides no portable way to glean this information
      at runtime, I'm afraid.
    </description>
    <param-name>cm.chartServletPrefix</param-name>
    <param-value>/chart-images</param-value>
  </init-param>
  <init-param>
    <description>
     The default JDBC driver class name for SQL queries
    </description>
    <param-name>cm.jdbcDriver</param-name>
    <param-value>org.postgresql.Driver</param-value>
  </init-param>
  <init-param>
    <description>
     The default JDBC URL for SQL queries
    </description>
    <param-name>cm.jdbcUrl</param-name>
    <param-value>jdbc:postgresql://localhost:5432/my_db</param-value>
  </init-param>
  <init-param>
    <description>
     The default JDBC username for SQL queries
    </description>
    <param-name>cm.jdbcUsername</param-name>
    <param-value>scott</param-value>
  </init-param>
  <init-param>
    <description>
     The default JDBC password for SQL queries
    </description>
    <param-name>cm.jdbcPassword</param-name>
    <param-value>tiger</param-value>
  </init-param>
  <init-param>
    <description>
     The JNDI name of the default data source for SQL queries
    </description>
    <param-name>cm.jdbcJndiName</param-name>
    <param-value>jdbc/DemoDS</param-value>
  </init-param>
</servlet>

....

<servlet-mapping>
  <servlet-name>chart</servlet-name>
  <url-pattern>/chart-images/*</url-pattern>
</servlet-mapping>
```