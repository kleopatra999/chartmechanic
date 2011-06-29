/*
 * Copyright 2008-2010 Bay Area Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.bayareasoftware.chartengine.chart.jfree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import com.bayareasoftware.chartengine.chart.ShapeUtil;


/**
 * Little swing program to noodle with JfreeChart quickly and easily...
 * 
 * @author dave
 *
 */
public class JFreeNoodle {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame;
        JFreeChart[] charts = new JFreeChart[] {
        chart1(), 
        chart2(),
        chart3(),
        chart4(), chart5(), chart6(),
        chart7(), chart8(),
        chart9(), chart10(),
        chart11(), 
        chart12(), 
//        chart12a(),
        chart13(),
        chart14()
        };
        // legacy "no-op" theme
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JPanel pan = new JPanel();
        //pan.setLayout(new FlowLayout());
        pan.setLayout(new GridLayout(6,1));
        for (int i = 0; i < charts.length; i++) {
            ChartPanel cp = new ChartPanel(charts[i]);
            //cp.setPreferredSize(new Dimension(500, 600));
            pan.add(cp);
        }
        JScrollPane sp = new JScrollPane(pan);
        frame = new JFrame("JFreeNoodle");
        frame.getContentPane().add(sp);
        frame.setSize(1024,768);
        
        frame.setLocation(100, 100);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

    
    /**
     * Utility method for creating <code>Date</code> objects.
     *
     * @param day  the date.
     * @param month  the month.
     * @param year  the year.
     *
     * @return a date.
     */
    private static Date date(int day, int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date result = calendar.getTime();
        return result;

    }
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
    private static Date parseDate(String s) {
        Date result = null;
        try {
            result = sdf.parse(s);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + s);
        }
        return result;
    }
    
    private static TaskSeriesCollection ganttData() {
        String[][] data = { 
                {"Process Mapping","6/16/2008","3/31/2009","All"},
                {"IT Strategy","6/16/2008","12/31/2009","Erin"},
                {"Quick Sigma: AP #1 Incomplete POs","9/15/2008","2/27/2009","Erin"},
                {"Enterprise Account Structure: Customers","1/1/2009","6/1/2009","Erin"},
                {"Enterprise Account Structure: Managers","3/2/2009","12/30/2009","Erin"},
                {"Pre Define: Document Retention & Storage","1/1/2009","1/30/2009","Erin"},
                {"Pre Define: Personal Printers","1/1/2009","1/30/2009","Erin"},
                {"JDI: Selling Used Oil (PPS)","11/3/2008","1/30/2009","Jim"},
                {"DMAIC: Vehicle Program","12/1/2008","3/2/2009","Jim"},
                {"Pre Define: Freight Expense","12/1/2008","12/31/2008","Jim"},
                {"Pre-Define: Fleet Management","1/1/2009","1/30/2009","Jim"},
                {"Pre Define: Document Retention & Storage","1/1/2009","1/30/2009","Jim"},
                {"Pre-Define: O-Parts","1/1/2009","1/30/2009","Jim"},
                {"DMEDI: Recycling Oil #1 (PPS)","3/2/2009","12/31/2009","Jim"},
                {"DMAIC: AP #3 Payment Methods","4/1/2009","12/31/2009","Jim"},
                {"DMAIC: Utilities","6/12/2008","2/27/2009","Alex"},
                {"Quick Sigma: AP #1 Incomplete POs","9/15/2008","2/27/2009","Alex"},
                {"DMAIC: AP #2 Process Improvement","10/1/2008","3/31/2009","Alex"},
                {"Pre Define: Freight Expense","12/1/2008","12/31/2008","Alex"},
                {"Pre-Define: Asset Management","12/1/2008","12/31/2008","Alex"},
                {"Pre-Define: Fleet Management","1/1/2009","1/30/2009","Alex"},
                {"Pre-Define: Parts Reconciliation","1/1/2009","1/30/2009","Alex"},
                {"DMAIC: Repair Communication (PTCo)","2/2/2009","12/31/2009","Alex"},
                {"DMAIC: CCE Equipment Standardization (PMCo)","12/1/2008","9/1/2009","Bill"},
                {"JDI: Inventory Personnel (PMCo)","12/1/2008","1/30/2009","Bill"},
                {"Pre-Define: Asset Management","12/1/2008","12/31/2008","Bill"},
                {"Pre-Define: O-Parts","1/1/2009","1/30/2009","Bill"},
                {"Pre-Define: Parts Reconciliation","1/1/2009","1/30/2009","Bill"},
                {"DMAIC: Parts Reconciliation #1","2/9/2009","12/31/2009","Bill"}  
        };
        
        HashMap<String,TaskSeries> map = new HashMap<String,TaskSeries>();
        for (String[] row : data) {
            String taskName = row[0];
            String start = row[1];
            String end = row[2];
            String series = row[3];
            
            TaskSeries ts = map.get(series);
            if (ts == null) {
               ts = new TaskSeries(series);
               map.put(series,ts);
            }
            
            ts.add(new Task(taskName, parseDate(start), parseDate(end)));
        }
        TaskSeriesCollection collection = new TaskSeriesCollection();
        
        for (TaskSeries ts : map.values()) {
            collection.add(ts);
        }
        return collection;
    }
    
    public static JFreeChart chart12a() {
        
        JFreeChart chart = ChartFactory.createGanttChart(
                "QFS Portal Schedule",// chart title
                "Task",              // domain axis label
                "Date",              // range axis label
                ganttData(),             // data
                true,                // include legend
                true,                // tooltips
                false                // urls
            );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //plot.getDomainAxis().setMaximumCategoryLabelWidthRatio(10.0f);
        //plot.getDomainAxis().setCategoryMargin(10.0f);
        plot.setRangeCrosshairVisible(true);
        GanttRenderer renderer = (GanttRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setItemMargin(0);
        return chart;
    }
    
    
  public static JFreeChart chart12b() {
      XYTaskDataset dataset = new XYTaskDataset(ganttData());
      JFreeChart chart = ChartFactory.createXYBarChart("QFS Portal Schedule (XYTaskDataset)",
              "Task",
              false,
              "Date",
              dataset,
              PlotOrientation.HORIZONTAL,
              true, false, false);
      
      XYPlot plot = (XYPlot) chart.getPlot();
//      SymbolAxis xAxis = new SymbolAxis("Series", new String[] {"Team A",
//              "Team B", "Team C", "Team D"});
//      xAxis.setGridBandsVisible(false);
//      plot.setDomainAxis(xAxis);
      XYBarRenderer re = (XYBarRenderer) plot.getRenderer();
      re.setUseYInterval(true);
      plot.setRangeAxis(new DateAxis("Timing"));

      return chart;
    }
  
    public static JFreeChart chart12() {
        TaskSeries s1 = new TaskSeries("Scheduled");
        s1.add(new Task("Write Proposal",
               new SimpleTimePeriod(date(1, Calendar.APRIL, 2001),
                                    date(5, Calendar.APRIL, 2001))));
        s1.add(new Task("Obtain Approval", new SimpleTimePeriod(date(9,
                Calendar.APRIL, 2001), date(9, Calendar.APRIL, 2001))));
        s1.add(new Task("Requirements Analysis",
               new SimpleTimePeriod(date(10, Calendar.APRIL, 2001),
                                    date(5, Calendar.MAY, 2001))));
        s1.add(new Task("Design Phase",
               new SimpleTimePeriod(date(6, Calendar.MAY, 2001),
                                    date(30, Calendar.MAY, 2001))));
        s1.add(new Task("Design Signoff",
               new SimpleTimePeriod(date(2, Calendar.JUNE, 2001),
                                    date(2, Calendar.JUNE, 2001))));
        s1.add(new Task("Alpha Implementation",
               new SimpleTimePeriod(date(3, Calendar.JUNE, 2001),
                                    date(31, Calendar.JULY, 2001))));
        s1.add(new Task("Design Review",
               new SimpleTimePeriod(date(1, Calendar.AUGUST, 2001),
                                    date(8, Calendar.AUGUST, 2001))));
        s1.add(new Task("Revised Design Signoff",
               new SimpleTimePeriod(date(10, Calendar.AUGUST, 2001),
                                    date(10, Calendar.AUGUST, 2001))));
        s1.add(new Task("Beta Implementation",
               new SimpleTimePeriod(date(12, Calendar.AUGUST, 2001),
                                    date(12, Calendar.SEPTEMBER, 2001))));
        s1.add(new Task("Testing",
               new SimpleTimePeriod(date(13, Calendar.SEPTEMBER, 2001),
                                    date(31, Calendar.OCTOBER, 2001))));
        s1.add(new Task("Final Implementation",
               new SimpleTimePeriod(date(1, Calendar.NOVEMBER, 2001),
                                    date(15, Calendar.NOVEMBER, 2001))));
        s1.add(new Task("Signoff",
               new SimpleTimePeriod(date(28, Calendar.NOVEMBER, 2001),
                                    date(30, Calendar.NOVEMBER, 2001))));

        TaskSeries s2 = new TaskSeries("Actual");
        s2.add(new Task("Write Proposal",
               new SimpleTimePeriod(date(1, Calendar.APRIL, 2001),
                                    date(5, Calendar.APRIL, 2001))));
        s2.add(new Task("Obtain Approval",
               new SimpleTimePeriod(date(9, Calendar.APRIL, 2001),
                                    date(9, Calendar.APRIL, 2001))));
        s2.add(new Task("Requirements Analysis",
               new SimpleTimePeriod(date(10, Calendar.APRIL, 2001),
                                    date(15, Calendar.MAY, 2001))));
        s2.add(new Task("Design Phase",
               new SimpleTimePeriod(date(15, Calendar.MAY, 2001),
                                    date(17, Calendar.JUNE, 2001))));
        s2.add(new Task("Design Signoff",
               new SimpleTimePeriod(date(30, Calendar.JUNE, 2001),
                                    date(30, Calendar.JUNE, 2001))));
        s2.add(new Task("Alpha Implementation",
               new SimpleTimePeriod(date(1, Calendar.JULY, 2001),
                                    date(12, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Design Review",
               new SimpleTimePeriod(date(12, Calendar.SEPTEMBER, 2001),
                                    date(22, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Revised Design Signoff",
               new SimpleTimePeriod(date(25, Calendar.SEPTEMBER, 2001),
                                    date(27, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Beta Implementation",
               new SimpleTimePeriod(date(27, Calendar.SEPTEMBER, 2001),
                                    date(30, Calendar.OCTOBER, 2001))));
        s2.add(new Task("Testing",
               new SimpleTimePeriod(date(31, Calendar.OCTOBER, 2001),
                                    date(17, Calendar.NOVEMBER, 2001))));
        s2.add(new Task("Final Implementation",
               new SimpleTimePeriod(date(18, Calendar.NOVEMBER, 2001),
                                    date(5, Calendar.DECEMBER, 2001))));
        s2.add(new Task("Signoff",
               new SimpleTimePeriod(date(10, Calendar.DECEMBER, 2001),
                                    date(11, Calendar.DECEMBER, 2001))));

        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        collection.add(s2);

        JFreeChart chart = ChartFactory.createGanttChart(
                "Gantt Chart Demo",  // chart title
                "Task",              // domain axis label
                "Date",              // range axis label
                collection,             // data
                true,                // include legend
                true,                // tooltips
                false                // urls
            );
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.getDomainAxis().setMaximumCategoryLabelWidthRatio(10.0f);
            plot.setRangeCrosshairVisible(true);
            GanttRenderer renderer = (GanttRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(false);
            renderer.setShadowVisible(false);
            renderer.setItemMargin(0);
        return chart;
    }
    
    public static JFreeChart chart11() {
        JFreeChart ret = null;
        XYPlot p1 = chart2().getXYPlot();
        XYPlot p2 = chart4().getXYPlot();
        CombinedDomainXYPlot cp = new CombinedDomainXYPlot(p1.getDomainAxis());
        cp.add(p1);
        cp.add(p2);
        cp.setGap(0);
        ret = new JFreeChart("combined domain plot", cp);
        //ret.getLegend().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        LegendTitle lt = ret.getLegend();
        lt.setPosition(RectangleEdge.RIGHT);
        //lt.setBorder(0, 0, 0, 0);
        //lt.setBorder(border)
        lt.setBorder(BlockBorder.NONE);
        lt.setBorder(new BlockBorder(3,3,3,3,paint2()));
        return ret;
    }
    public static JFreeChart chart10() {
        DefaultXYDataset dset = new DefaultXYDataset();
        double[][] data = new double[2][50];
        for (int d = 0; d < 50; d++) {
            data[0][d] = d;
            data[1][d] = d*d;
        }
        dset.addSeries("y=X^2", data);
        JFreeChart ret = ChartFactory.createXYLineChart("log axis", "x",
                "y", dset, PlotOrientation.VERTICAL, true, false, false);
        LogarithmicAxis la = new LogarithmicAxis("");
        la.setLabel("Log");
        la.setAllowNegativesFlag(true);
        ret.getXYPlot().setRangeAxis(la);
        int start = 4, end = 40;
        XYLineAnnotation xyl = new XYLineAnnotation(
                data[0][start],data[1][start],data[0][end],data[1][end],
                new BasicStroke(1.0f),ChartUtil.decodeColor("dark_green")
                );
        ret.getXYPlot().addAnnotation(xyl);
        return ret;
    }

    public static JFreeChart chart13() {
        DefaultXYDataset dset = new DefaultXYDataset();
        double[][] data = new double[2][50];
        double[][] data2 = new double[2][50];
        for (int d = 0; d < 50; d++) {
            data[0][d] = d;
            data[1][d] = d*d;
            data2[0][d] = d;
            data2[1][d] = 20*d;
        }
        dset.addSeries("y=X^2 (1st series)", data);
        dset.addSeries("y=X (2nd series)", data2);
        JFreeChart ret = ChartFactory.createXYLineChart("two lines (with Default SeriesRenderingOrder)", "x",
                "y", dset, PlotOrientation.VERTICAL, true, false, false);
        
        ret.setBackgroundPaint(paint2());
        XYPlot plot = ret.getXYPlot();
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        //plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
        
        XYLineAndShapeRenderer liner = new XYLineAndShapeRenderer();
        liner.setSeriesPaint(0,paint3());
        liner.setSeriesPaint(1,paint1());
        plot.setRenderer(0, liner);
        
        return ret;
    }
    
    public static JFreeChart chart14() {
        DefaultXYDataset dset = new DefaultXYDataset();
        double[][] data = new double[2][50];
        double[][] data2 = new double[2][50];
        for (int d = 0; d < 50; d++) {
            data[0][d] = d;
            data[1][d] = d*d;
            data2[0][d] = d;
            data2[1][d] = 20*d;
        }
        dset.addSeries("y=X^2 (1st series)", data);
        dset.addSeries("y=X (2nd series)", data2);
        JFreeChart ret = ChartFactory.createXYLineChart("two lines (with FORWARD SeriesRenderingOrder)", "x",
                "y", dset, PlotOrientation.VERTICAL, true, false, false);
        
        ret.setBackgroundPaint(paint2());
        XYPlot plot = ret.getXYPlot();
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
        
        XYLineAndShapeRenderer liner = new XYLineAndShapeRenderer();
        liner.setSeriesPaint(0,paint3());
        liner.setSeriesPaint(1,paint1());
        plot.setRenderer(0, liner);
        
        return ret;
    }

    
    /*
     * http://www.jfree.org/phpBB2/viewtopic.php?t=21344
     */
    private static JFreeChart chart3() {
        JFreeChart jfc = null;
        DefaultXYZDataset xyzd = null;
        xyzd = new DefaultXYZDataset();
        addTimeSeriesToXYZ(xyzd, 18, 5, "MyBubble");
        jfc = ChartFactory.createBubbleChart(
                "bubble (range axis on right)", "xAxisLabel", "yAxisLabel",
                xyzd, PlotOrientation.VERTICAL,
                true, true, true);
        DateAxis da = new DateAxis();
        
        //da.setStandardTickUnits(source);
        jfc.getXYPlot().setDomainAxis(da);
        jfc.getXYPlot().setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        XYBubbleRenderer bub;
        //bub = (XYBubbleRenderer) jfc.getXYPlot().getRenderer();
        bub = new XYBubbleRenderer(XYBubbleRenderer.SCALE_ON_RANGE_AXIS);
        p("bub.baseoutlineStroke=" + BeanUtil.type2string(bub.getClass(), bub.getBaseOutlineStroke()));
        //bub.setBaseFillPaint(paint2());
        bub.setPaint(paint2());
        jfc.getXYPlot().setRenderer(bub);
        Date d = daysAgo(3);
        /*Value*/Marker marker = new ValueMarker(d.getTime());
        marker.setLabel("3 days ago");
        marker.setPaint(ChartUtil.decodeColor("orange"));
        marker.setLabelPaint(Color.red);
        marker.setLabelFont((Font)BeanUtil.convertType("SanSerif-italic-12", Font.class));
        // fixme: cannot tell what LengthAdjustmentType does here
        marker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
        marker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        RectangleInsets ri = null;
        // top,left,bottom,right
        ri = new RectangleInsets(10.0,0,0,0);
        marker.setLabelOffset(ri);
        float[] dashes = new float[] { 0,6,0,6 };
        BasicStroke bs = new BasicStroke(3.0f,
                BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_BEVEL,
                0, dashes, 0);
        marker.setStroke(bs);
        
        jfc.getXYPlot().addDomainMarker(0, marker, Layer.FOREGROUND);
        
        d = daysAgo(5);
        Date d2 = daysAgo(12);
        marker = new IntervalMarker(d2.getTime(), d.getTime());
        marker.setLabel("1 week ending 5 days ago");
        p("interval marker default paint is " + marker.getPaint());
        p("interval marker default alpha is " + marker.getAlpha());
        //marker.setAlpha(0.5f);
        //marker.setPaint(ChartUtil.decodeColor("light_gray"));
        marker.setLabelPaint(ChartUtil.decodeColor("blue"));
        marker.setLabelFont((Font)BeanUtil.convertType("SanSerif-italic-12", Font.class));
        marker.setLabelOffset(ri);
        jfc.getXYPlot().addDomainMarker(0, marker, Layer.BACKGROUND);
        //jfc.getXYPlot().addDomainMarker(marker);
        // see that a marker outside the natural range of the plot
        // doesn't show up
        d = daysAgo(100);
        System.err.println("100 days ago: " + d);
        marker = new ValueMarker(d.getTime());
        marker.setLabel("100 days ago");
        marker.setPaint(ChartUtil.decodeColor("dark_green"));
        jfc.getXYPlot().addDomainMarker(0, marker, Layer.BACKGROUND);
        //jfc.getXYPlot().addDomainMarker(marker);
        
        // try a pointer annotation
        {
            int annoItem = 8;
            double x = xyzd.getXValue(0, annoItem);
            double y = xyzd.getYValue(0, annoItem);
            p("adding pointer at xy=" + x + "," + y);
            XYPointerAnnotation pointer = new XYPointerAnnotation("Data Point Eight", x, y, 0);
            pointer.setAngle(-1);
            pointer.setLabelOffset(pointer.getLabelOffset() + 10);
            Paint red = ChartUtil.decodeColor("red");
            pointer.setPaint(red);
            pointer.setArrowPaint(red);
            jfc.getXYPlot().addAnnotation(pointer);
        }
        // mess w/ legend
        {
            LegendItemSource src = new LegendItemSource() {

                public LegendItemCollection getLegendItems() {
                    LegendItemCollection ret = new LegendItemCollection();
                    // label,desc,tooltip,urltext,shape,fillPaint
                    LegendItem le = new LegendItem(
                            "US Recessions", null, null, null,
                            ShapeUtil.SQUARE, ChartUtil.decodeColor("light_gray")
                            );
                    ret.add(le);
                    return ret;
                }
                
            };
            LegendTitle lt = jfc.getLegend();
            LegendItemSource[] srcs = lt.getSources();
            LegendItemSource[] tmp = null;
            if (srcs == null) {
                tmp = new LegendItemSource[1];
                tmp[0] = src;
            } else {
                tmp = new LegendItemSource[srcs.length + 1];
                System.arraycopy(srcs, 0, tmp, 1, srcs.length);
                tmp[0] = src;
            }
            lt.setSources(tmp);
        }
        return jfc;
    }
    private static Paint paint1() {
        return new GradientPaint(
                0,0,ChartUtil.decodeColor("#ff0000"),
                600,600,ChartUtil.decodeColor("#303030"),false
                );
    }
    
    private static Paint paint2() {
        return new GradientPaint(
                0,0,ChartUtil.decodeColor("#a0a0ff"),
                100,100,ChartUtil.decodeColor("#404040"), true
                );
    }

    private static Paint paint3() {
        return new GradientPaint(
                0,0,ChartUtil.decodeColor("#00ff00"),
                500,500,ChartUtil.decodeColor("#404040"), false
                );
    }
    
    private static Date daysAgo(int i) {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_YEAR, -i);
        return cal.getTime();
    }
    private static void addTimeSeriesToXYZ(DefaultXYZDataset set, int rowCount, int base, String series) {
        TimeRow[] rows;
        rows = makeTimeSeries(rowCount, base);
        double[][] data = new double[3][rows.length];
        for (int i = 0; i < rows.length; i++) {
            data[0][i] = rows[i].d.getTime();
            data[1][i] = rows[i].val;
            data[2][i] = i % 3;
        }
        set.addSeries(series, data);
        
    }
    private static void addSeriesToSet(TimeTableXYDataset set, int rowCount, int base, String series) {
        TimeRow[] rows;
        rows = makeTimeSeries(rowCount, base);
        for (int i = 0; i < rows.length; i++) {
            set.add(new Day(rows[i].d), rows[i].val, series);
        }
        //p("added " + rows.length + " rows to timeseries, first is " + rows[0].d
          //      + " last is " + rows[rows.length - 1].d);
    }
    
    /*
     * try a stacked bar chart with each series coming from a different dataset
     * does NOT work.
     */
    private static JFreeChart chart9() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        {
            dataset.addValue(3, "Row 1", "Column 1");
            dataset.addValue(4, "Row 1", "Column 2");
            dataset.addValue(5, "Row 1", "Column 3");
            dataset.addValue(null, "dummy 2", "Column 1");
            dataset.addValue(null, "dummy 2", "Column 2");
            dataset.addValue(null, "dummy 2", "Column 3");
        }
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        {
            dataset2.addValue(null, "dummy 1", "Column 1");
            dataset2.addValue(null, "dummy 1", "Column 2");
            dataset2.addValue(null, "dummy 1", "Column 3");
            dataset2.addValue(33.0, "Row 2", "Column 1");
            dataset2.addValue(44.0, "Row 2", "Column 2");
            dataset2.addValue(55.0, "Row 2", "Column 3");
        }
        BarRenderer r1 = new BarRenderer();
        r1.setPaint(ChartUtil.decodePaint("#ff0000"));
        CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis(), new NumberAxis("Axis I"), r1) {
            public LegendItemCollection getLegendItems() {

                LegendItemCollection result = new LegendItemCollection();

                CategoryDataset data = getDataset();
                if (data != null) {
                    CategoryItemRenderer r = getRenderer();
                    if (r != null) {
                        LegendItem item = r.getLegendItem(0, 0);
                        result.add(item);
                    }
                }

                // the JDK 1.2.2 compiler complained about the name of this
                // variable 
                CategoryDataset dset2 = getDataset(1);
                if (dset2 != null) {
                    CategoryItemRenderer renderer2 = getRenderer(1);
                    if (renderer2 != null) {
                        LegendItem item = renderer2.getLegendItem(1, 1);
                        result.add(item);
                    }
                }

                return result;

            }            
        };
        plot.setDataset(0, dataset);
        plot.setDataset(1, dataset2);
        
        //plot.setRenderer(0, r1);
        plot.setBackgroundPaint(paint2());
        plot.setBackgroundAlpha(0f);

        //plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        NumberAxis axe2 = new NumberAxis("axis II, bigger range");
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setRangeAxis(1, axe2);
        BarRenderer r2 = new BarRenderer();
        r2.setPaint(ChartUtil.decodePaint("dark_green"));
        plot.setRenderer(1, r2);
        // FIXME: no direct way to set legend.backgroundAlpha from jfreechart
        // using our BeanUtil property system
        JFreeChart ret = new JFreeChart("Bar Category, 2 datasets, 2 axes chart 9", plot);
        CategoryAxis cax = plot.getDomainAxis();
        cax.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        //cax.setCategoryLabelPositions(positions)
        ret.getLegend().setBackgroundPaint(new Color(0xa0,0xff,0,0));
        return ret;
      }    
    private static JFreeChart chart8() {
        JFreeChart chart = null;
        OHLCDataItem[] items = new OHLCDataItem[100];
        Random rand = new Random();
        double[] rands = new double[5];
        for (int i = 99; i >= 0; i--) {
            Date d = daysAgo(i);
            for (int j = 0; j < rands.length; j++) {
                rands[j] = Math.abs(rand.nextDouble());
            }
            double open, high, low, close, vol;
            open = rands[0] * 3 + 10;
            high = open + .5;
            low = open - .5;
            close = rands[3] * 3 + 10;
            vol = rands[4] * 100 + 100;
            items[99 - i] = new OHLCDataItem(d,open,high,low,close,vol);
        }
        DefaultOHLCDataset dset = new DefaultOHLCDataset("ACME", items);
        chart = ChartFactory.createCandlestickChart(
                "ACME OHLC", "date", "price", dset, true
                );
        CandlestickRenderer cr;
        cr = (CandlestickRenderer) chart.getXYPlot().getRenderer();
        cr.setDownPaint(ChartUtil.decodeColor("dark_red"));
        cr.setUpPaint(ChartUtil.decodePaint("dark_green"));
        //cr.setDownPaint(paint1());
        //cr.setUpPaint(paint2());
        cr.setDrawVolume(true);
        return chart;
    }
    /**
     * a histogram example, modified from JFreeChart examples
     * @return
     */
    private static JFreeChart chart6() {
        HistogramDataset dataset = new HistogramDataset();
        double[] values = new double[1000];
        Random generator = new Random(12345678L);
        for (int i = 0; i < 1000; i++) {
            values[i] = generator.nextGaussian() + 5;
        }
        dataset.addSeries("Gaussian", values, 100, 0.0, 10.0);
        values = new double[1000];
        for (int i = 0; i < 1000; i++) {
            values[i] = generator.nextDouble() * 10;
        }
        dataset.addSeries("Uniform", values, 100, 0.0, 10.0);
        JFreeChart chart = ChartFactory.createHistogram(
                "Histogram Demo 1", 
                null, 
                null, 
                dataset, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setForegroundAlpha(0.85f);
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        {
            chart.getLegend().setPosition(RectangleEdge.RIGHT);
            //Rectangle2D rect = null;
            //rect = new Rectangle2D(40,40,100,100);
            //chart.getLegend().setBounds()
        }
        return chart;
    }

    /**
     * a histogram example, using the stream-able SimpleHistogramDataset, as opposed to above 
     */
    private static JFreeChart chart7() {
        SimpleHistogramDataset dset;
        dset = new SimpleHistogramDataset("my_uniform");
        for (int i = 0; i < 10; i++) {
            double low = i;
            double high = i + 1;
            dset.addBin(new SimpleHistogramBin(low,high,true,false));
        }
        Random rand = new Random(0);
        for (int i = 0; i < 1000; i++) {
            double val = rand.nextDouble();
            val = Math.abs(val);
            val *= 10;
            dset.addObservation(val);
        }
        JFreeChart chart = null;
        chart = ChartFactory.createHistogram(
                "Histogram Demo 2", 
                null, 
                null, 
                dset,
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false
        );
        XYPlot xyplot = chart.getXYPlot();
        dset = new SimpleHistogramDataset("my_gaussian");
        for (int i = 0; i < 10; i++) {
            double low = i;
            double high = i + 1;
            dset.addBin(new SimpleHistogramBin(low,high,true,false));
        }
        for (int i = 0; i < 1000; i++) {
            double val = rand.nextGaussian() + 5;
            dset.addObservation(val);
        }
        xyplot.setDataset(1, dset);
        XYItemRenderer br;
        br = new XYBarRenderer();
        br.setPaint(ChartUtil.decodePaint("#ffff00"));
        xyplot.setRenderer(0, br);
        br = new XYLineAndShapeRenderer();
        br.setPaint(ChartUtil.decodePaint("#00ffff"));
        xyplot.setRenderer(1, br);
        // make sure to render dataset 0 first, so the second "line" renderer
        // appears in front of the bar renderer...
        xyplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        return chart;
    }    
    /*
     * try a stacked bar chart with each series coming from a different dataset
     * does NOT work.
     */
    private static JFreeChart chart5() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Row 1", "Column 1");
        dataset.addValue(5.0, "Row 1", "Column 2");
        dataset.addValue(3.0, "Row 1", "Column 3");
        dataset.addValue(2.0, "Row 2", "Column 1");
        dataset.addValue(3.0, "Row 2", "Column 2");
        dataset.addValue(2.0, "Row 2", "Column 3");      
        
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.addValue(10.0, "Row 2", "Column 1");
        dataset2.addValue(10.0, "Row 2", "Column 2");
        dataset2.addValue(10.0, "Row 2", "Column 3");

        JFreeChart ret = null;
        
        ret = ChartFactory.createStackedBarChart(
                "StackedBarDemo1 - 2 datasets - no work",         // chart title
                "Category",               // domain axis label
                "Value",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
                );
        ret.setBackgroundPaint(paint2());
        CategoryPlot plot = (CategoryPlot)ret.getPlot();
        BarRenderer br = new BarRenderer();
        p("default bar painter is " + br.getBarPainter());
        br.setBarPainter(new StandardBarPainter());
        plot.setRenderer(br);
        plot.setBackgroundPaint(paint2());
        plot.setBackgroundAlpha(0f);
//        plot.setDataset(1, ttxy1);
//        XYBarRenderer br = new XYBarRenderer();
//        br.setPaint(paint1());
        //plot.setRenderer(0, br);
        plot.setDataset(1, dataset2);
        
//        XYLineAndShapeRenderer liner = new XYLineAndShapeRenderer();
//        liner.setPaint(paint3());
//        plot.setRenderer(1, liner);
        //ret.getLegend().setBackgroundPaint(ChartUtil.decodeColor("yellow"));
        // BACKGROUND ALPHA of zero for legend
        // FIXME: no direct way to set legend.backgroundAlpha from jfreechart
        // using our BeanUtil property system
        ret.getLegend().setBackgroundPaint(new Color(0,0xff,0,0));
        return ret;
      }
    /*
     * experiment w/ moving average
     */
    private static JFreeChart chart4() {
        JFreeChart ret = chart2();
        XYPlot xyp = ret.getXYPlot();
        XYDataset d1 = xyp.getDataset(0); 
        String suffix = "-moving avg";
        // range axis, when date, is in millisecs...
        // adjust moving avg params by msec/day
        final double msperday = 1000 * 60 * 60 * 24;
        final int movingAvgPeriod = 10;

        double period = msperday * movingAvgPeriod;
        double skip = msperday * movingAvgPeriod;

        XYDataset ma0 = MovingAverage.createMovingAverage(d1, suffix, period, skip);
        xyp.setDataset(2, ma0);
        StandardXYItemRenderer mar = new StandardXYItemRenderer();
        p("standardXYRender.baseOutlineStroke=" + BeanUtil.type2string(mar.getClass(), mar.getBaseOutlineStroke()));
        xyp.getRenderer(0).setPaint(ChartUtil.decodePaint("yellow"));
        mar.setPaint(ChartUtil.decodePaint("red"));
        xyp.setRenderer(2, mar);
        
        XYDataset d2 = xyp.getDataset(1);
        XYDataset ma1 = MovingAverage.createMovingAverage(d2, suffix, period, skip);
        xyp.setDataset(3, ma1);
        mar = new StandardXYItemRenderer();
        StandardXYItemLabelGenerator gen = new StandardXYItemLabelGenerator() {
            @Override
            public String generateLabel(XYDataset set, int series, int num) {
                p("generating label: " + series + "#" + num);
                return series + " #" + num;
            }
        };
        Date d = new Date(2007, 10, 12);
        XYTextAnnotation anno = new XYTextAnnotation("some label", /*d.getTime()*/
                100, 100);
        //xyp.addAnnotation(anno);
        
        ValueMarker columbus = new ValueMarker((double)d.getTime());
        columbus.setLabel("Columbus Day");
        xyp.addDomainMarker(columbus);
        mar.addAnnotation(anno);
        mar.setItemLabelGenerator(new StandardXYItemLabelGenerator());
        mar.setItemLabelPaint(ChartUtil.decodePaint("#000000"));
        mar.setPaint(ChartUtil.decodePaint("blue"));
        xyp.setRenderer(3, mar);
        // set the rendering order to FORWARD so that the later datasets/renderers appear on top of the previous
        xyp.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        xyp.setBackgroundPaint(ChartUtil.decodePaint("white"));
        ret.setBackgroundPaint(ChartUtil.decodePaint("white"));

        ret.getTitle().setText("TimeSeries w/ 10-day moving averages");
        return ret;
    }
    /**
     * try to get different renderers (bar and line) for different
     * series with DIFFERENT datasets.  This DOES work as expected.
     */
    private static JFreeChart chart2() {
        TimeTableXYDataset ttxy0 = new TimeTableXYDataset();
        addSeriesToSet(ttxy0, 10, 5, "S1");
        TimeTableXYDataset ttxy1 = new TimeTableXYDataset();        
        addSeriesToSet(ttxy1, 10, 15, "S2");
        JFreeChart ret = null;
        ret = ChartFactory.createTimeSeriesChart(
                "My Time Series", "time", "value",
                ttxy0, true, true, true
                );
        DateAxis da = (DateAxis) ret.getXYPlot().getDomainAxis();
        {
            // 10 day tick units
            DateTickUnit dtu = new DateTickUnit(DateTickUnit.DAY, 10);
            DateFormat fmt = new SimpleDateFormat("MMM d");
            da.setDateFormatOverride(fmt);
            da.setTickUnit(dtu);
        }
        ret.setBackgroundPaint(paint2());
        XYPlot plot = ret.getXYPlot();
        plot.setBackgroundPaint(paint2());
        plot.setBackgroundAlpha(0f);
        plot.setDataset(1, ttxy1);
        
        XYItemRenderer br;
        br = new XYBarRenderer();
        {
            br.setBaseItemLabelsVisible(true);
            br.setBaseItemLabelGenerator(new MyXYLabeler());
            br.setBaseItemLabelPaint(ChartUtil.decodePaint("#ffffff"));
            Font f = Font.decode("Arial-10");
            p("using label font: " + f);
            br.setBaseItemLabelFont(f);
            ItemLabelPosition pos = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
            br.setBasePositiveItemLabelPosition(pos);
        }
        

        br.setPaint(paint1());
        plot.setRenderer(0, br);
        
        XYLineAndShapeRenderer liner = new XYLineAndShapeRenderer();

        //liner.setBaseItemLabelGenerator(new MyXYLabeler());
        liner.setPaint(paint3());
        plot.setRenderer(1, liner);
        //ret.getLegend().setBackgroundPaint(ChartUtil.decodeColor("yellow"));
        // BACKGROUND ALPHA of zero for legend
        // FIXME: no direct way to set legend.backgroundAlpha from jfreechart
        // using our BeanUtil property system
        ret.getLegend().setBackgroundPaint(new Color(0,0xff,0,0));
        return ret;
    }
    
    static class MyXYLabeler implements XYItemLabelGenerator {

        public String generateLabel(XYDataset dset, int series, int item) {
            String ret = null;
            //Comparable skey = dset.getSeriesKey(series);
            Number x = dset.getX(series, item);
            Number y = dset.getY(series, item);
            if (x != null && y != null) {
                //ret = "X/Y: " + x + "/" + y;
                if (y.doubleValue() >= 10) { ret = y + " *"; }
            }
            return ret;
        }
        
    }
    
    /**
     * try to get different renderers (bar and line) for different
     * series within a SINGLE dataset.  This does NOT work as expected,
     * all series get the same renderer, that of index 0
     * @return
     */
    private static JFreeChart chart1() {
        TimeTableXYDataset ttxy = new TimeTableXYDataset();
        addSeriesToSet(ttxy, 1520, 5, "S1");
        addSeriesToSet(ttxy, 1520, 15, "S2");
        JFreeChart ret = null;
        ret = ChartFactory.createTimeSeriesChart(
                "My Time Series (PeriodAxis)", "time", "value",
                ttxy, true, true, true
                );
        XYPlot plot = ret.getXYPlot();
        //XYBarRenderer br = new XYBarRenderer();
        StandardXYItemRenderer br = new StandardXYItemRenderer();
        br.setItemLabelsVisible(true);
        
        //br.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        br.setBaseItemLabelPaint(ChartUtil.decodePaint("dark_green"));
        plot.setRenderer(0, br);
        
        XYLineAndShapeRenderer liner = new XYLineAndShapeRenderer(); 
        plot.setRenderer(1, liner);
        Day d1 = new Day(new Date());
        PeriodAxis pa = new PeriodAxis("my period axis");//, d1, d2);
        //pa.setLast(new Day(new Date()));
        //pa.setFirst(new Day(daysAgo(1500)));
        pa.setMajorTickTimePeriodClass(Year.class);
        //pa.setAutoRangeTimePeriodClass(Year.class);
        pa.setMinorTickTimePeriodClass(Month.class);
        /*
        PeriodAxisLabelInfo[] labels = new PeriodAxisLabelInfo[2];
        labels[0] = new PeriodAxisLabelInfo(Month.class, new SimpleDateFormat("MMM"));
        labels[1] = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("yyyy"));
        pa.setLabelInfo(labels);
        */
        //pa.setAutoRange(true);
        plot.setDomainAxis(pa);
        return ret;
    }
    
    private static class TimeRow {
        TimeRow(Date d, int v) {
            this.d = d;
            val = v;
        }
        Date d;
        int val;
        public String toString() {
            return "[" + d + "," + val + "]";
        }
    }
    
    private static TimeRow[] makeTimeSeries(int rows, int base) {
        TimeRow[] ret = new TimeRow[rows];
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // backup rows days
        cal.add(Calendar.DAY_OF_YEAR, -rows);
        //p("starting date: " + cal.getTime());
        for (int i = 0; i < rows; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            
            ret[i] = new TimeRow(cal.getTime(), rand(base));
        }
        return ret;
    }
    
    private static Random rand = new Random();
    private static int rand(int baseline) {
        int ret = rand.nextInt();
        ret = Math.abs(ret);
        ret %= 10;
        ret += baseline;
        return ret;
    }
    private static void p(String s) {
        System.out.println("[noodle] " + s);
    }
}
