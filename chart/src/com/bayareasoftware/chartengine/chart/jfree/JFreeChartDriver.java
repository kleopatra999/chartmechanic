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

import static com.bayareasoftware.chartengine.model.PlotType.PLOT_CATEGORY;
import static com.bayareasoftware.chartengine.model.PlotType.PLOT_GANTT;
import static com.bayareasoftware.chartengine.model.PlotType.PLOT_HISTOGRAM;
import static com.bayareasoftware.chartengine.model.PlotType.PLOT_TIME;
import static com.bayareasoftware.chartengine.model.PlotType.PLOT_XY;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.Arrangement;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.TitleEntity;
import org.jfree.chart.imagemap.ImageMapUtilities;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.imagemap.ToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.URLTagFragmentGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.ImageTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

import com.bayareasoftware.chartengine.chart.ChartDiskResult;
import com.bayareasoftware.chartengine.chart.ChartDriver;
import com.bayareasoftware.chartengine.chart.ChartResult;
import com.bayareasoftware.chartengine.chart.ShapeUtil;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.JoinTimeStream;
import com.bayareasoftware.chartengine.ds.StringDataStream;
import com.bayareasoftware.chartengine.model.BaseInfo;
import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.LogoInfo;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.MarkerValue;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.PieItemLabelFormats;
import com.bayareasoftware.chartengine.model.PlotType;
import com.bayareasoftware.chartengine.model.Rectangle;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.model.TimeConstants;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;
import com.bayareasoftware.chartengine.util.FileUtil;
import com.bayareasoftware.chartengine.util.URLUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * JFreeChartDriver is the main entry point for the Chart Engine APIs 
  */
public class JFreeChartDriver implements ChartDriver {

    private final Log log = LogFactory.getLog(JFreeChartDriver.class);
    
     /**
     * the typeSystem contains the bean properties that describe the various kinds of
     * plots, renderers, etc. 
     */
    private ChartTypeSystem typeSystem;
   
    /**
     * the name of the typesystem file can be overridded by a java system property named TYPESYSTEM_PROPNAME
     */
    private final static String TYPESYSTEM_PROPNAME = "chartengine.typeSystem";
    
    static {
        initializeCMDefaults();
    }
    /**
     * central place where we override various defaults from JFreeChart
     */
    public static void initializeCMDefaults() {
        /** JFreeChart 1.0.11 introduces "ChartTheme", the default of which
         * interferes with our typesystem.  Just use legacy no-op chart theme, at
         * least for now...
         */
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        /* make 'plain' bar renderers the default, not gradient
         */
        BarRenderer.setDefaultBarPainter(new StandardBarPainter());
        XYBarRenderer.setDefaultBarPainter(new StandardXYBarPainter());
    }

    private ChartLogo defaultLogo = new ChartLogo();
    
    // keep an LRU cache of LogoInfo->ChartLogo mappings
    private static int lru_size = 5000;
    private LinkedHashMap<LogoInfo,ChartLogo> logoCache;
    
//    // watermark in all images
//    private Image watermarkImage = null;
//    private int watermark_width = 0;
//    private int watermark_height = 0;
//    
//    // a scaled version of the watermark to be used if the chart is too small
//    private Image smallWatermarkImage = null;
//    
//    private URL watermarkURL;
//    private boolean useWatermark = true; 
            
    /**
     * create a new ChartDriver that uses jFreeChart as the rendering technology 
     *  
     */
    public JFreeChartDriver() {
        // the typeFile is a properties file that describes all the JFreeChart specific capabilities
        // (pre-generated via reflection from TypeInspector)
        String typeFile = System.getProperties().getProperty(TYPESYSTEM_PROPNAME,"/jfreechart-types.xml");
        try {
            typeSystem = TypeInspector.load(typeFile);
        } catch (Exception e) {
            String msg = "cannot load typesystem file '" + typeFile + "'";
            log.error(msg, e); 
            handleException(msg, e);
        }
          
        BufferedImage bi = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
          
        SUBTITLE_FONTMETRICS = g2.getFontMetrics(SUBTITLE_FONT);
        
        logoCache = new LinkedHashMap<LogoInfo,ChartLogo>(1000,0.75f,true) {
            // since this is a access-order LinkedHashMap
            // eldest means least recently accessed map
            // we remove the LRU entry when we need to
            protected boolean removeEldestEntry(Map.Entry<LogoInfo,ChartLogo> eldest) {
                if (size() > lru_size){
//                    log.warn("removing eldest entry due to size: " + size() + " exceeding LRU size of " + PER_CHART_LRU_SIZE + " chartresult:" + eldest.getValue());
                    ChartLogo cl = eldest.getValue();
                    if (cl.img != null)
                        cl.img.flush();
                    if (cl.smallImg != null)
                        cl.smallImg.flush();
                    return true;
                }
                return false;
            }
        };
        
      }
    
    // used by getData()
    // to return date values, either as coarse or fine representation, depending on whether the timeperiod of the chart is coarser than day or not
    
    private String coarseDateFmt = "yyyy-MM-dd";
    private String fineDateFmt = "HH:mm:ss";
    private SimpleDateFormat coarseDateFormatter = new SimpleDateFormat(coarseDateFmt);
    private SimpleDateFormat fineDateFormatter = new SimpleDateFormat(fineDateFmt);
    
    /**
     * if coarseDates if true, return a coarse string representation of the Date ('yyyy-MM-dd'), otherwise
     * return a fine date representation 
     * @param coarserDates
     * @param d
     * @return
     */
    private String formatDate(boolean coarserDates, Date d) {
        if (coarserDates)  {
            return coarseDateFormatter.format(d);
        } else {
            return fineDateFormatter.format(d);
        }
    }
    
//    /**
//     * toggle whether to use a water mark in each image
//     * @param useWatermark
//     */
//    public void setUseWaterMark(boolean useWatermark) {
//        this.useWatermark = useWatermark;
//    }
//
    public static final int SUBTITLE_MAXLINES = 5;
    public static Font SUBTITLE_FONT=Font.decode("Arial-10");
    public static Font TEXTLOGO_FONT=Font.decode("Arial-14");
    public static FontMetrics SUBTITLE_FONTMETRICS;
    
    /**
     * add a logo to the chart, for now, has some hardcoded positioning and margins
     * @param chart
     * @param w   - width of the chart
     * @param h   - height of the chart
     */
    private void addLogo(ChartInfo ci, JFreeChart chart, int w, int h, ChartLogo chartLogo) {
        ImageTitle imageLogo = null;
        TextTitle textLogo = null;
        
        Block logoBlock = null; // either imageLogo or textLogo;
        
        TextTitle subTitle = null;
        
        int logoWidth = -1;
        if (chartLogo.visible) {
            if (chartLogo.txt == null) {
                // use an image logo
                Image img = chartLogo.img;
                logoWidth = chartLogo.width; 
                 if (chartLogo.width > w / 4 || chartLogo.height > h / 4) {
                     // if the img is bigger than 20% of the height and width of the chart, then use the smaller image
                     // if it exists
                     if (chartLogo.smallImg != null) {
                         logoWidth = 2 * chartLogo.width / 3;
                         img = chartLogo.smallImg;
                     }
                }
                imageLogo = new ImageTitle(img);
                imageLogo.setVerticalAlignment(VerticalAlignment.BOTTOM);
                imageLogo.setPosition(RectangleEdge.BOTTOM);
                logoBlock = imageLogo;
            } else {
                logoWidth = 200;
                // use a text logo
                textLogo = new TextTitle();
                textLogo.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                textLogo.setTextAlignment(HorizontalAlignment.RIGHT);
                textLogo.setText(chartLogo.txt);
                textLogo.setFont(TEXTLOGO_FONT);
                logoBlock = textLogo;
            }
        }
        
        String subTitleText = StringUtil.trim(ci.getDescription());
        
        // make sure that the subTitleText fits within the maxWidth;
        // if there's not enough room, don't show the subTitleText, leave the logo
        
        double maxWidth;
        
        if (logoBlock != null && logoWidth != -1) {
            maxWidth = w - logoWidth - 15; // 15 is a fudge factor for specing
        } else {
            maxWidth = w;
        }

        if (subTitleText != null && maxWidth > 0) {
            
            // work around font metrics jfreechart bug - make sure there are
            // no empty lines, with just newline \n
            String[] sa = StringUtil.splitCompletely(subTitleText, '\n', true);
            StringBuffer sb = new StringBuffer();
            
            for (int i = 0; i < SUBTITLE_MAXLINES && i < sa.length; i++) {
                if (sa[i] == null || sa[i].length() == 0) {
                    sa[i] = " ";
                }
                if (sb.length() != 0) {
                    sb.append('\n');
                }

                char[] s = sa[i].toCharArray();
                
                int len = s.length;
                // ensure that each line's width fits into maxWidth
                while (len > 0 && SUBTITLE_FONTMETRICS != null &&
                      SUBTITLE_FONTMETRICS.charsWidth(s, 0, len) > maxWidth) {
                    len = len-1;
                }
                if (len < s.length) {
                    // if we need to chop off some characters, try to do it at a word-break
                    int l = len;
                    while (l > 1 && !Character.isWhitespace(s[l])) {
                        l--;
                    }
                    if (l > 1) {
                        String fittedString = new String(s,0,l);
                        String remainderString = new String(s,l+1,s.length-(l+1)); // l+1 to skip the whitespace char
                        sb.append(fittedString);
                        if (i < sa.length-1) {
                            // and as long as we're not in the last line,
                            // add the remainder to the next line and let the while loop take care of it
                            sa[i+1] = remainderString + "\n" + sa[i+1];
                        } else {
                            // we are at the end of all the original characters, check to 
                            // see if we have room left to append
                            if (i < SUBTITLE_MAXLINES-1) {
                                sb.append("\n"+remainderString);
                            }
                        }
                    } else {
                        // we need to chop off some characters but there's no good word-break
                        // just truncate
                        sb.append(new String(s,0,len));
                    }
                } else {
                    // no need to truncate anywhere, just stick the original line in there
                    sb.append(sa[i]);
                }
            }
            subTitle = new TextTitle();
            subTitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            subTitle.setTextAlignment(HorizontalAlignment.LEFT);
            subTitle.setMaximumLinesToDisplay(SUBTITLE_MAXLINES);
            subTitle.setText(sb.toString());
            subTitle.setFont(SUBTITLE_FONT);
//            subTitle.setFrame(new BlockBorder(Color.red));   
        }

        //
        Arrangement a;
        a = new BorderArrangement();
        //a = new FlowArrangement();
        BlockContainer container = new BlockContainer(a);
        
        if (subTitle != null) {
            container.add(subTitle, RectangleEdge.LEFT);
        }
        
        // trick copied from jfree chart examples.  stuff a big empty block to force
        // the subtitle and logo to the edges
        TextTitle emptyBlock = new TextTitle();
        emptyBlock.setExpandToFitSpace(true);
        container.add(emptyBlock);

        if (logoBlock != null) {
            container.add(logoBlock, RectangleEdge.RIGHT);
        }
        
        CompositeTitle ct = new CompositeTitle(container);
        ct.setPosition(RectangleEdge.BOTTOM);
        // adding this at index 0 should help put it below the legend, if any
        chart.addSubtitle(0,ct);
    }
    
    public ChartTypeSystem getTypeSystem() {
        return typeSystem;
    }
    
    private Rectangle makeRect(ChartEntity ce) {
        Rectangle ret = null;
        if (ce != null) {
            Shape shape = ce.getArea();
            if (shape != null) {
                java.awt.Rectangle jar = shape.getBounds();
                ret = new Rectangle(jar.x,jar.y,jar.width,jar.height);
            }
        }
        return ret;
    }
    
    private Rectangle makeRect(Rectangle2D r) {
        Rectangle ret = null;
        if (r != null) {
            int x,y,w,h;
            x = (int)r.getX();
            y = (int)r.getY();
            w = (int)r.getWidth();
            h = (int)r.getHeight();
            ret = new Rectangle(x,y,w,h);
        }
        return ret;
    }
    
    private BufferedImage createThumbnail(JFreeChart chart, ChartRenderingInfo cri) {
        BufferedImage ret = null;
        LegendTitle lt = chart.getLegend();
        boolean oldVis = false;
        try {
            if (lt != null) {
                oldVis = lt.isVisible();
                lt.setVisible(false);
            }
            ret = chart.createBufferedImage(150, 110, 360, 240, null);
        } finally {
            if (lt != null) lt.setVisible(oldVis);
            
        }
        return ret;
    }
    
    private void writeImageAsPNG(BufferedImage bi, String filename) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(filename)));
            EncoderUtil.writeBufferedImage(bi,ImageFormat.PNG,out);
        } finally {
            if (out != null)
                out.close();
        }
        //log.warn("||||||||||||||||| JFreeChartDriver.writeImageAsPNG: wrote image to : " + filename);
    }
    
    /**
     * given a chart, calculate the datastream that is the 'JOIN' of all the data that is being plotted
     *  
     *   TODO: may be more efficient to calculate this as a side-effect of creating the chart
     *
     * @param ci
     * @param sMap
     * @param template
     * @param maxRows                         - if > 0, limit the number of rows returned
     * @return          data stream of results
     * @throws Exception
     */
    public DataStream getData(ChartInfo ci, Map<Integer,DataStream> sMap, SimpleProps template, int maxRows) 
        throws Exception {
        
        ChartContext ctxt = new ChartContext(ci,sMap);

        DataStream ret = null;
        
        int numDatasets = ctxt.getDatasetCount();
        
        DataStream[] results = new DataStream[numDatasets];

        if (maxRows <= 0) {
            maxRows = Integer.MAX_VALUE;
        }
        if (numDatasets > 0) {
            
            PlotType ptype = ci.getPlotType();
            
            switch (ptype) {
            case PLOT_TIME:
            {
                // for time data, we return the time value as either as yyyy-MM-dd 
                // or as a string HH:mm:ss, depending on whether the time period of the plot
                // is coarser than a DAY
                boolean coarseDates = ci.getTimePeriod() >= TimeConstants.TIME_DAY;
                
                for (int i = 0; i< numDatasets; i++) {
                    Dataset dset = ctxt.getDataset(i);
                    if (dset instanceof TimeTableXYDataset) {
                        TimeTableXYDataset ttxy = (TimeTableXYDataset)ctxt.getDataset(i);

                        int numSeries = ttxy.getSeriesCount();

                        List<String[]> data = new ArrayList<String[]>();
                        int item = 0;
                        int itemCount = ttxy.getItemCount(); // every series has the same itemcount in TimeTableXYDataset
                        
                        for (int si = 0; si < itemCount; si++) {
                            for (int s = 0; s < numSeries ;s++) {
                                TimePeriod tp = ttxy.getTimePeriod(si);

                                Comparable seriesKey = ttxy.getSeriesKey(s);
                                Number y = ttxy.getY(s,si);

                                if (y != null) {
                                    String[] dataRow = new String[3]; // every row has a time, a series, and a Y value
                                    dataRow[0] = formatDate(coarseDates, tp.getStart());
                                    dataRow[1] = seriesKey.toString();
                                    dataRow[2] = y.toString();

                                    data.add(dataRow);
                                    item++;
                                }
                            }
                            if (item == maxRows) {
                                break;
                            }
                        }

                        Metadata md = new Metadata(3);
                        md.setColumnType(1,DataType.DATE);
                        md.setColumnName(1,"Date");
                        if (coarseDates) {
                            md.setColumnFormat(1,coarseDateFmt);
                        } else {
                            md.setColumnFormat(1,fineDateFmt);
                        }
                        md.setColumnType(2,DataType.STRING);
                        md.setColumnName(2,"Series");
                        md.setColumnType(3,DataType.DOUBLE);
                        md.setColumnName(3,"Value");
                        results[i] = new StringDataStream(data,md,0,item);
                        results[i].reset();
                    } else if (dset instanceof TimeSeriesCollection) {
                        TimeSeriesCollection tsc = (TimeSeriesCollection)ctxt.getDataset(i);

                        int numSeries = tsc.getSeriesCount();
                        // to join the stream's we create a tree-map (sorted map) that
                        // maps Long (date value in msecs)  to a 'row' of numeric values
                        TreeMap<Long,Number[]> tmap = new TreeMap<Long,Number[]>();

                        for (int s = 0; s < numSeries;s++) {
                            TimeSeries tseries = tsc.getSeries(s);
                            for (int item = 0 ; item < tseries.getItemCount();item++) {
                                TimeSeriesDataItem tdata = tseries.getDataItem(item);
                                RegularTimePeriod rtp = tdata.getPeriod();
                                if (rtp != null) {
                                    Long key = new Long(rtp.getFirstMillisecond());

                                    Number[] row = tmap.get(key);
                                    if (row == null) {
                                        // this is the first time, we've seen this key, add a new row
                                        row = new Number[numSeries];
                                        tmap.put(key,row);
                                    }
                                    row[s] = tdata.getValue();
                                }
                            }
                        }

                        List<String[]> data = new ArrayList<String[]>();
                        int endRow = 0;

                        // convert the sorted TreeMap of values to an array of strings for use as a StringDataStream
                        for (Long k : tmap.keySet()) {
                            Number[] row = tmap.get(k);
                            String[] dataRow = new String[numSeries+1];
                            dataRow[0] = formatDate(coarseDates, new Date(k));
                            for (int j=0;j<numSeries;j++) {
                                if (row[j]!=null) {
                                    dataRow[j+1] = row[j].toString(); // FIXME: better formatting of numbers
                                }
                            }
                            data.add(dataRow);
                            endRow++;
                            if (endRow == maxRows) {
                                break;
                            }
                        }

                        Metadata md = new Metadata(numSeries+1);
                        md.setColumnType(1,DataType.DATE);
                        md.setColumnName(1,"Date");
                        //md.setColumnFormat(1,Metadata.INTERNAL_DATE_FORMAT);
                        if (coarseDates) {
                            md.setColumnFormat(1,coarseDateFmt);
                        } else {
                            md.setColumnFormat(1,fineDateFmt);
                        }
                        for (int j=0;j<numSeries;j++) {
                            md.setColumnType(2+j,DataType.DOUBLE);
                            md.setColumnName(2+j,tsc.getSeriesKey(j).toString());
                        }
                        results[i] = new StringDataStream(data,md,0,endRow);
                        results[i].reset();
                    } else if (dset instanceof DefaultXYZDataset) {
                        // we use defaultXYZdataset for TimeSeries bubble plots
                        // all datasets have common columns (though potentially in different order)

                        DefaultXYZDataset dxyz = (DefaultXYZDataset) ctxt.getDataset(i);

                        int numSeries = dxyz.getSeriesCount();

                        List<String[]> data = new ArrayList<String[]>();
                        int item = 0;
                        for (int s = 0; s < numSeries && item < maxRows;s++) {
                            for (int si = 0; si < dxyz.getItemCount(s); si++) {
                                Number x = dxyz.getX(s,si);
                                Number y = dxyz.getY(s,si);
                                Number z = dxyz.getZ(s,si);

                                log.warn("s = " + s + " si = " + si + " x = " + x + " y = " + y + " z = " + z);

                                String[] dataRow = new String[3]; // every row has a time, a series, and a Y value
                                //dataRow[0] = String.valueOf(x.longValue()); // the first column is the time in msecs
                                dataRow[0] = formatDate(coarseDates,new Date(x.longValue()));

                                dataRow[1] = y.toString();
                                dataRow[2] = z.toString();

                                data.add(dataRow);
                                item++;
                                if (item == maxRows) {
                                    break;
                                }
                            }
                        }

                        Metadata md = new Metadata(3);
                        md.setColumnType(1,DataType.DATE);
                        md.setColumnName(1,"Date");
                        //md.setColumnFormat(1,Metadata.INTERNAL_DATE_FORMAT);
                        if (coarseDates) {
                            md.setColumnFormat(1,coarseDateFmt);
                        } else {
                            md.setColumnFormat(1,fineDateFmt);
                        }
                        md.setColumnType(2,DataType.DOUBLE);
                        md.setColumnName(2,"Y");
                        md.setColumnType(3,DataType.DOUBLE);
                        md.setColumnName(3,"Z");
                        results[i] = new StringDataStream(data,md,0,item);
                        results[i].reset();
                    }

                }

                // join the time streams together using JoinTimeStream
                if (numDatasets == 1) {
                    ret = results[0];
                } else {
                    int[] dateCols = new int[numDatasets];
                    for (int i=0;i<numDatasets;i++) 
                        dateCols[i]=1; // the date col is in the 1st column in all the stringdatastreams we've created

                    List<DataStream> dstreams = new ArrayList<DataStream>();
                    for (int i=0;i<results.length;i++) {
                        if (results[i] != null)
                            dstreams.add(results[i]);
                    }

                    // if we have to use join time stream, the date columns will not be coarse/fine, but only coarse, (i.e. yyyy-MM-dd)
                    ret = new JoinTimeStream(dstreams.toArray(new DataStream[(dstreams.size())]),dateCols);
                }

            }
            break;
            case PLOT_CATEGORY:
            {
                // for the category datasets, the "categories" are the columns
                // and all datasets have common columns (though potentially in different order)

                // the String key is the column key
                // and there are as many columns as there are row keys (series names)
                LinkedHashMap<Comparable,List<Number>> map = new LinkedHashMap<Comparable,List<Number>>();
                int numCols = 0; 

                List<Comparable> rowKeys = new ArrayList<Comparable>();

                for (int i = 0; i < numDatasets; i++) {
                    DefaultCategoryDataset cds = (DefaultCategoryDataset)ctxt.getDataset(i);

                    for (int r = 0; r < cds.getRowCount(); r++) {
                        rowKeys.add(cds.getRowKey(r));
                        numCols = cds.getColumnCount();
                        for (int col = 0; col < numCols; col++) {

                            Comparable colKey = cds.getColumnKey(col);
                            List<Number> row = map.get(colKey);
                            if (row == null) {
                                row = new ArrayList<Number>();
                                map.put(colKey,row);
                            }
                            row.add(cds.getValue(r,col));
                        }
                    }
                }

                List<String[]> data = new ArrayList<String[]>();
                int endRow = 0;
                Metadata md = new Metadata(rowKeys.size()+1);
                md.setColumnType(1,DataType.STRING);
                md.setColumnName(1,"Category");
                for (int c = 0; c < rowKeys.size(); c++) {
                    md.setColumnType(c+2,DataType.DOUBLE);
                    md.setColumnName(c+2,rowKeys.get(c).toString());
                }

                for (Comparable k : map.keySet()) {
                    List<Number> row = map.get(k);
                    String[] dataRow = new String[row.size()+1];

                    String key = k.toString();
                    dataRow[0] = key;
                    for (int j=0;j<row.size();j++) {
                        if (row.get(j)!=null) {
                            dataRow[j+1] = row.get(j).toString(); // FIXME: better formatting of numbers
                        }
                    }
                    data.add(dataRow);
                    endRow++;
                    if (endRow == maxRows) {
                        break;
                    }
                }

                ret = new StringDataStream(data,md,0,endRow);
                ret.reset();
            }
            break;
            case PLOT_GANTT:
            {
                TaskSeriesCollection tsc = (TaskSeriesCollection)ctxt.getDataset(0);

                List<String[]> data = new ArrayList<String[]>();
                Metadata md = new Metadata(4);
                md.setColumnType(1,DataType.STRING);
                md.setColumnName(1,"Task Description");
                md.setColumnType(2,DataType.STRING);
                md.setColumnName(2,"Series");
                md.setColumnType(3,DataType.DATE);
                md.setColumnFormat(3,Metadata.INTERNAL_DATE_FORMAT);
                md.setColumnName(3,"Start Date");
                md.setColumnType(4,DataType.DATE);
                md.setColumnFormat(4,Metadata.INTERNAL_DATE_FORMAT);
                md.setColumnName(4,"End Date");

                int endRow = 0;

                for (int s=0;s<tsc.getSeriesCount() && endRow < maxRows;s++) {
                    Comparable seriesKey = tsc.getSeriesKey(s);
                    //log.warn("TaskSeriesCollection, series key = " + seriesKey);
                    TaskSeries series = tsc.getSeries(s);
                    for (int si = 0; si < series.getItemCount(); si++) {
                        Task task = series.get(si);
                        String description = task.getDescription();
                        TimePeriod tp = task.getDuration();
                        Date start = tp.getStart();
                        Date end = tp.getEnd();
//                        log.warn("description = " + description + " start = " + start + " end = " + end);
                        
                        String[] dataRow = new String[4];
                        dataRow[0] = description;
                        dataRow[1] = seriesKey.toString();
                        dataRow[2] = String.valueOf(start.getTime());
                        dataRow[3] = String.valueOf(end.getTime());

                        data.add(dataRow);
                        endRow++;
                        if (endRow == maxRows) {
                            break;
                        }
                    }
                }

                ret = new StringDataStream(data,md,0,endRow);
                ret.reset();
            }
                break;
            case PLOT_HISTOGRAM:
            {
                // for histogram's, only handle one dataset
                SimpleHistogramDataset shd = (SimpleHistogramDataset)ctxt.getDataset(0);
                // note that the series count is always 1 for SimpleHistogramDataset
                
                List<String[]> data = new ArrayList<String[]>();
                Metadata md = new Metadata(3);
                md.setColumnType(1,DataType.DOUBLE);
                md.setColumnName(1,"Start X Value");
                md.setColumnType(2,DataType.DOUBLE);
                md.setColumnName(2,"End X Value");
                md.setColumnType(3,DataType.DOUBLE);
                md.setColumnName(3,"Y Value");

                int endRow = 0;
                for (int si = 0 ; si < shd.getItemCount(0);si++) {
                    Number startX = shd.getStartX(0,si);
                    Number endX = shd.getEndX(0,si);
                    Number y = shd.getY(0,si);
//                    log.warn("SeriesKey: " + shd.getSeriesKey(0) + " startX = " + startX + " endX = " + endX + "y = " + y);
                    String[] dataRow = new String[3];
                    dataRow[0] = startX.toString();
                    dataRow[1] = endX.toString();
                    dataRow[2] = y.toString();
                    data.add(dataRow);
                    endRow++;
                    if (endRow == maxRows) {
                        break;
                    }
                }

                ret = new StringDataStream(data,md,0,endRow);
                ret.reset();
            }
                break;
            case PLOT_PIE:
            case PLOT_PIE3D:
            case PLOT_RING:
            {
                DefaultPieDataset pieds = (DefaultPieDataset) ctxt.getDataset(0);

                List<String[]> data = new ArrayList<String[]>();

                int endRow = 0;
                Metadata md = new Metadata(2);
                md.setColumnType(1,DataType.STRING);
                md.setColumnName(1,"Category");
                md.setColumnType(1,DataType.DOUBLE);
                md.setColumnName(2,"Value");

                for (int i=0;i<pieds.getItemCount();i++) {
                    String[] dataRow = new String[2];
                    Comparable key = pieds.getKey(i);
                    Number value = pieds.getValue(i);

                    if (value != null) {
                        dataRow[0] = key.toString();
                        dataRow[1] = value.toString();

                        data.add(dataRow);
                        endRow++;
                    }
                    if (endRow == maxRows) {
                        break;
                    }
                }

                ret = new StringDataStream(data,md,0,endRow);
                ret.reset();
            }
            break;
            case PLOT_XY:
            {
                // DefaultTableXYDataset is used of XY Plots
                // the key is the X value
                // and there are as many columns as there are different series
                LinkedHashMap<Number,List<Number>> map = new LinkedHashMap<Number,List<Number>>();

                // the number of columns is the sum of all the series in every dataset
                List<Comparable> seriesKeys = new ArrayList<Comparable>();
                for (int i = 0 ;i < numDatasets; i++) {
                    DefaultTableXYDataset dtxy = (DefaultTableXYDataset) ctxt.getDataset(i);
                    for (int s = 0 ; s < dtxy.getSeriesCount(); s++) {
                        seriesKeys.add(dtxy.getSeriesKey(s));
                        for (int si = 0; si < dtxy.getItemCount(s); si ++) {
                            Number x = dtxy.getX(s,si);
                            Number y = dtxy.getY(s,si);

                            if (x != null) {
                                List<Number> row = map.get(x);
                                if (row == null) {
                                    row = new ArrayList<Number>();
                                    map.put(x,row);
                                }
                                row.add(y);
                            }
                        }
                    }
                }

                List<String[]> data = new ArrayList<String[]>();
                Metadata md = new Metadata(seriesKeys.size()+1);
                md.setColumnType(1,DataType.DOUBLE);
                md.setColumnName(1,"X");
                for (int c = 0; c < seriesKeys.size(); c++) {
                    md.setColumnType(c+2,DataType.DOUBLE);
                    md.setColumnName(c+2,seriesKeys.get(c).toString());
                }

                int endRow = 0;
                for (Number k : map.keySet()) {
                    List<Number> row = map.get(k);
                    String[] dataRow = new String[row.size()+1];

                    dataRow[0] = k.toString();
                    for (int j=0;j<row.size();j++) {
                        if (row.get(j)!=null) {
                            dataRow[j+1] = row.get(j).toString(); // FIXME: better formatting of numbers
                        }
                    }
                    data.add(dataRow);
                    endRow++;
                    if (endRow == maxRows) {
                        break;
                    }
                }

                ret = new StringDataStream(data,md,0,endRow);
                ret.reset();
            }
            break;
            }
            
        } // if numDataSets > 0
        
         
        
        return ret;
    }
    
    /**
     * main entry point for creating charts,  given a ChartInfo, create a chart and return it.  Does not cache the chart
     * 
     * @param ci        ChartInfo that describes the UI properties for the chart
     * @param logo      logo for this chart (if null, use default logo)
     * @param sMap      the map of descriptor sid's to DataStream that supply all the data for the chart series and markers
     * @param template  Template of default properties,  can be null
     * @param ret       an optional ChartResult can be passed in to guide the creation of the object.
     *                  the dimensions (width/height) in the ChartResult override the dimensions in the ChartInfo  
     *                  If the ChartResult has filePaths set, then those files will be use to materialize
     *                  the image, thumbnail, and image maps on disk
     *                  if not the, the data will be kept inline in the ChartResult
     *                  if res is null, then a new ChartResult is created and data will be kept in that object
     * @return          the ChartResult
     * @throws          rethrows any exception raised during chart creation.  but if the ChartResult passed in has  isShowErrorImage() set then
     *                  the exception is converted into a chart image and returned instead of being re-thrown
     */
    public ChartResult create(ChartInfo ci,
                              LogoInfo logo,
                              Map<Integer,DataStream> sMap, 
                              SimpleProps template, 
                              ChartResult ret) throws Exception  {
        
        try {
            if (ret == null)
                ret = new ChartResult();
            
            long start, now;
            
            // how long does it take to do various stages
            long queryTime, createTime, persistTime, logoTime, imageTime, vectorTime, thumbnailTime;
            
            int WIDTH = ret.getWidth() > 0 ? ret.getWidth() : ci.getWidth();
            WIDTH = Math.min(WIDTH, MAX_WIDTH);
            int HEIGHT = ret.getHeight() > 0 ? ret.getHeight() : ci.getHeight();
            HEIGHT = Math.min(HEIGHT, MAX_HEIGHT);
            ret.setSize(WIDTH, HEIGHT);
            ret.setPlotType(ci.getPlotType());
            start = System.currentTimeMillis();
            long createStart = start;
            
            // ====== Step 1: create the necessary data sets
            ChartContext ctxt = new ChartContext(ci,sMap);
            
            //getData(ctxt);
            
            now = System.currentTimeMillis();
            queryTime = now - start;
            start = now;
            
            ret.colorMap = new HashMap<Integer,String>();
            // ====== Step 2: create the actual JFreeChart
            // using the ChartBundle and the dsMap
            //JFreeChart chart = createChart(ci, template, dsMap, markerValues);
            JFreeChart chart = createChart(ci, template, ctxt, ret.colorMap);
            
//            if (ret.colorMap != null) {
//                log.warn("*############################ colorMap ########################");
//                for (Integer k : ret.colorMap.keySet()) {
//                    log.warn("colorMap(" + k + ") = " + ret.colorMap.get(k));
//                }
//            }

            now = System.currentTimeMillis();
            createTime = now - start;
            start = now;
            
            // ====== Step 3: add ChartMechanic logo to the chart
            //  TODO: add other fixtures like ads, etc.
            
            ChartLogo chartLogo = defaultLogo;
            if (logo != null) {
                chartLogo = logoCache.get(logo);
                //System.err.println("*******************logoCache.get(" + logo + ") returned = " + chartLogo);
                if (chartLogo == null) {
                    chartLogo = logoinfo2chartlogo(logo);
                    //System.err.println("*******************logoinfo2chartlogo(" + logo + ") returned = " + chartLogo);
                    logoCache.put(logo, chartLogo);
                }
            }
            
            addLogo(ci,chart, WIDTH, HEIGHT, chartLogo);

            now = System.currentTimeMillis();
            logoTime = now - start;
            start = now;

            ChartRenderingInfo cri = new ChartRenderingInfo();
            ChartDiskResult diskResult = ret.getDiskResult();
            // ====== Step 4: generate the full image 
            // either as raw PNGdata or to a file as specified in the ChartResult 
            BufferedImage bi = chart.createBufferedImage(WIDTH, HEIGHT, cri);
                // record geometry of various regions of the chart into the
            // chart result...
            EntityCollection ecoll = cri.getEntityCollection();
            if (ecoll != null && ecoll.getEntityCount() > 0) {
                int ecount = ecoll.getEntityCount();
                TextTitle title = chart.getTitle();
                // the first entity is the entire chart itself
                int r = 1;
                if (title != null) {
                    String s = title.getText();
                    if (s != null && !s.trim().equals("") && r < ecount) {
                        // the 2nd entity is the title as long as it's not
                        // null
                        ChartEntity ce = ecoll.getEntity(r);
                        if (isTitleEntity(ce)) {
                            Rectangle re = makeRect(ce);
                            ret.setTitleRect(re);
                        }
                        r++;
                    }
                }

                if (chart.getSubtitleCount() > 0) {
                    // the next entity is the subtitle (either the first or
                    // second entry depending on whether there is a title
                    String desc = ci.getDescription();
                    if (desc != null && !desc.trim().equals("") && r < ecount) {
                        ChartEntity ce = ecoll.getEntity(r);
                        if (isTitleEntity(ce)) {
                            Rectangle re = makeRect(ce);
                            ret.setSubtitleRect(re);
                        }
                    }
                }
                /*
                 * for (int i = 0; i < ecoll.getEntityCount(); i++) {
                 * ChartEntity ce = ecoll.getEntity(i); System.out.println(
                 * "[JFreeDriver] entity #" + i + " =" + ce.getClass().getName()
                 * + " coords=" + ce.getShapeCoords()); }
                 */
            }

            now = System.currentTimeMillis();
            imageTime = now - start;
            start = now;

            if (diskResult.hasImagePath()) {
                writeImageAsPNG(bi,diskResult.getImagePath());
            } 
            // step 4.1: record plot geometry
            {
                PlotRenderingInfo pri = cri.getPlotInfo();
                ret.setPlotRect(makeRect(pri.getPlotArea()));
                ret.setPlotDataRect(makeRect(pri.getDataArea()));
            }
            
            now = System.currentTimeMillis();
            persistTime = now - start;
            start = now;
            
            // ====== Step 4a: generate a vector file (e.g. pdf) if necessary
            // 
            if (diskResult.isGeneratePDF()) {
                if (diskResult.hasPdfPath()) {
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(new File(diskResult.getPdfPath()));
                        writeChartAsPDF(out, chart, WIDTH,HEIGHT, new DefaultFontMapper());
                    } finally {
                        if (out != null)
                            out.close();
                    }
                } 
            } else if (diskResult.isGeneratePS()) {
                    if (diskResult.hasPSPath()) {
                        OutputStream out = null;
                        try {
                            out = new FileOutputStream(new File(diskResult.getPSPath()));
                            writeChartAsPS(out, chart, WIDTH,HEIGHT);
                        } finally {
                            if (out != null)
                                out.close();
                        }
                    }
            } else if (diskResult.isGenerateEMF()) {
                if (diskResult.hasEMFPath()) {
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(new File(diskResult.getEMFPath()));
                        writeChartAsEMF(out, chart, WIDTH,HEIGHT);
                    } finally {
                        if (out != null)
                            out.close();
                    }
                }
            }
            now = System.currentTimeMillis();
            vectorTime = now - start;
            start = now;

            
            // ====== Step 5: generate the thumbnail (if necessary)
            // either as raw PNGdata or to a file as specified in the ChartResult
            if (diskResult.isGenerateThumbnail()) {
                BufferedImage thumbnail = createThumbnail(chart,cri);
                if (diskResult.hasThumbPath()) {
                    writeImageAsPNG(thumbnail,diskResult.getThumbPath());
                } 
            }

            now = System.currentTimeMillis();
            thumbnailTime = now - start;
            start = now;

            // ====== Step 6: generate the imageMap (if necessary)
            if (diskResult.isGenerateImageMap()) {
                URLTagFragmentGenerator utag = new StandardURLTagFragmentGenerator();
                ToolTipTagFragmentGenerator ttgen = new StandardToolTipTagFragmentGenerator();
                String imapId = diskResult.getImageMapId();
                String imap = ImageMapUtilities.getImageMap(imapId, cri, ttgen, utag);
                if (diskResult.hasImageMapPath()) {
                     FileUtil.writeString(new File(diskResult.getImageMapPath()), imap);
                } 
//                else {
//                    diskResult.setImageMap(imap);
//                }
            }


            // === Step 7: record axis ranges
            {
                Range range;
                Plot plot = chart.getPlot();
                if (plot instanceof XYPlot) {
                    XYPlot xyp = (XYPlot) plot;
                    ValueAxis domain = xyp.getDomainAxis();
                    range = domain.getRange();
                    ret.setAxisRange(0, range.getLowerBound(), range.getUpperBound());
                    for (int i = 0; i < xyp.getRangeAxisCount(); i++) {
                        ValueAxis va = xyp.getRangeAxis(i);
                        if (va == null) {
                            // range axis may not exist
                            continue;
                        }
                        range = va.getRange();
                        double lower = range.getLowerBound();
                        double upper = range.getUpperBound();
                        //p("rangeAxis[" + i + "] lower=" + lower + " upper=" + upper);
                        ret.setAxisRange(i+1, lower, upper);
                    }
                } else if (plot instanceof CategoryPlot) {
                    CategoryPlot cplot = (CategoryPlot) plot;
                    for (int i = 0; i < cplot.getRangeAxisCount(); i++) {
                        ValueAxis va = cplot.getRangeAxis(i);
                        if (va == null) {
                            // range axis may not exist
                            continue;
                        }
                        range = va.getRange();
                        ret.setAxisRange(i+1, range.getLowerBound(), range.getUpperBound());
                    }
                }
            }
            
            if (log.isDebugEnabled()) {
                log.debug("created chart: id=" + ci.getId() + "/' in "
                        + (System.currentTimeMillis() - createStart) + " ms." 
                        + " create time= " + createTime
                        + " query time=" + queryTime
                        + " image time = " + imageTime 
                        + " logo time = " + imageTime 
                        + " persist time =" + persistTime
                        + " vector time = " + vectorTime
                        + " thumbnail time = " + thumbnailTime);
            }
            ret.setQueryTime(queryTime);
            ret.setCreateTime(createTime);
            ret.setPersistTime(persistTime);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            if (ret.getDiskResult().isShowErrorImage()) {
                throwable2chart(ret, e);
                return ret;
            } 
            throw e;
        }
        
    }
    
    private boolean isTitleEntity(ChartEntity ce) {
        return ce != null && ce instanceof TitleEntity;
    }
    
    /**
     * convert a logoInfo into a chartInfo
     * @param logoInfo
     * @return
     */
    private ChartLogo logoinfo2chartlogo(LogoInfo logo) {
        final ChartLogo ret = new ChartLogo();
        ret.visible = false;
        if (logo.isVisible()) {
            if (logo.getTxt() != null) {
                ret.txt = logo.getTxt();
            } else {
                String urlStr = logo.getUrl();
                try {
                    if (urlStr != null) {
                        URL u;
                        if (urlStr.startsWith("/com/bayareasoftware/chartengine")) {
                            // if the URL starts with /com/bayareasoftware/chartengine
                            // treat it as a resource
                            u = JFreeChartDriver.class.getResource(urlStr);
                        } else {
                            // otherwise, try to make a URL out of the string
                            if (!(urlStr.startsWith("vfs:")) && !(urlStr.contains(":"))) {
                                // if it's just a flat string, look up the file name from
                                // vfs:admin/images/
                                urlStr = "vfs:admin/images/" + urlStr;
                            }
                            u = URLUtil.safeURL(urlStr);
                            //u = new URL(urlStr);
                        }

                        ImageIcon ii = new ImageIcon(u);
                        ret.img = ii.getImage();
                        ret.width = ii.getIconWidth();
                        ret.height = ii.getIconHeight();

                        if (ret.width > 10 && ret.height > 10) {
                            int w = ret.width*2/3;
                            int h = ret.height*2/3;

                            ret.smallImg = null;
                            // instead of using getScaledInstance, which is asynchronous
                            // use the scaling version of Graphics2D.drawImage.
                            // it is also asynchronous but we can add set an ImageObserver on it
                            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
                            final Graphics2D g2 = bi.createGraphics();
                            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                            boolean ok = g2.drawImage(ret.img,0,0,w,h,new ImageObserver() {
                                public boolean imageUpdate(Image img, int infoflags, int x,
                                        int y, int width, int height) {
                                    ret.smallImg = img;
                                    boolean complete = (infoflags & ImageObserver.ALLBITS) != 0;
                                    if (complete) {
                                        ret.smallImg = img;
                                        g2.dispose();
                                    }
                                    return !complete;
                                }

                            });
                            if (ok) {
                                ret.smallImg = bi;
                            }
                        }
                        
//                        log.info("############################################setting chart logo to " + u.toExternalForm() +
//                                 "ret.img = " + ret.img + " ret.width = " + ret.width + " ret.height = " + ret.height);
                        
                    }
                } catch (MalformedURLException e) {
                    log.error("Malformed url: " + urlStr + " for chart_logo");
                    return ret;
                }
            }
            ret.visible = true;
        }
        return ret;
    }
   
    /**
     * when we encounter an error, generate a chart with the textual content of that exception
     * and stick it in the ChartResult as a PNG  
     * @param res
     * @param t
     * @throws IOException
     */
    private void throwable2chart(ChartResult res, Throwable t) throws IOException {
        BufferedImage bi = new BufferedImage(res.getWidth(), res.getHeight(), BufferedImage.TYPE_INT_RGB);
        res.getDiskResult().setErrorMessage(t.toString());
        Graphics2D g2 = bi.createGraphics();
        Font f = Font.decode("monospaced-9");
        g2.setFont(f);
        int lineHeight = f.getSize() + 3;
        int x = 4;
        int y = 10;
        g2.drawString(t.toString(), x, y);
        y+=lineHeight;
        StackTraceElement[] elems = t.getStackTrace();
        for (StackTraceElement elem : elems) {
            g2.drawString(elem.toString(), x, y);
            y+=lineHeight;
        }
        g2.dispose();
        if (res.getDiskResult().hasImagePath()) {
            writeImageAsPNG(bi,res.getDiskResult().getImagePath());
        } 
//        else {
//            res.setPNGData(ChartUtilities.encodeAsPNG(bi));
//        }
    }

    /**
     * create the actual JFreeChart object
     * @param ci               - ChartInfo chart specification
     * @param templateProps    - chart template properties that serve as defaults for the chart
     * @param ctxt             - ChartContext 
     * @param colorMap         - map of BaseDescriptor sid to String value of color
     * @return                 - the JFreeChart object
     */
    private JFreeChart createChart(ChartInfo ci,
                                   SimpleProps templateProps,
                                   final ChartContext ctxt,
                                   Map<Integer,String> colorMap) {
        
        //Dataset dset = ctxt.getDefaultDataset();
        Dataset dset = null;
        JFreeChart ret = null;
        // use the last part of the ID as the default title;
        //String defaultChartTitle = BaseInfo.lastIDPart(ci.getId(),"");
        
        String defaultChartTitle = "";
        
        PlotType ptype = ci.getPlotType();
        if (ptype == null) {
            throw new RuntimeException("Unexpected null plot type");
        }
        switch (ptype) {
        case PLOT_XY:
            ret = ChartFactory.createXYLineChart(
                    defaultChartTitle,null,null,
                    (XYDataset) dset, PlotOrientation.VERTICAL, true, true, true);
            break;
        case PLOT_TIME:
            ret = ChartFactory.createTimeSeriesChart(
                    defaultChartTitle,null,null,
                    (XYDataset) dset, true, true, true);
            break;
        case PLOT_CATEGORY:
            ret = ChartFactory.createBarChart(
                    defaultChartTitle,null,null,
                    (CategoryDataset) dset, PlotOrientation.VERTICAL,
                    true, true, true
                    );
            break;
        case PLOT_GANTT:
            ret = ChartFactory.createGanttChart(
                    defaultChartTitle,null,null,
                    (IntervalCategoryDataset)dset,
                    true, /* legend*/
                    false, /* tooltips*/
                    false /* urls*/
                    );
            break;
        case PLOT_PIE:
            ret = ChartFactory.createPieChart(
                    defaultChartTitle,
                    (PieDataset) dset,
                    true, true, true
                    );
            break;
        case PLOT_PIE3D:
            ret = ChartFactory.createPieChart3D(
                    defaultChartTitle,
                    (PieDataset) dset,
                    true, true, true
                    );
            break;
        case PLOT_RING:
            ret = ChartFactory.createRingChart(
                    defaultChartTitle,
                    (PieDataset) dset,
                    true, true, true
                    );
            break;
        case PLOT_HISTOGRAM:
            ret = ChartFactory.createHistogram(
                    defaultChartTitle,
                    null, null,
                    (SimpleHistogramDataset)dset, PlotOrientation.VERTICAL,
                    true, true, true);
            break;
        }
        
        SimpleProps opaqueProps = ci.getProps();
        
        if (templateProps != null) {
            SimpleProps tmp = (SimpleProps) templateProps.clone();
            tmp.putAll(opaqueProps);
            opaqueProps = tmp;
        }
        
        TextTitle txtTitle = ret.getTitle();
        BeanUtil.setProps(txtTitle, opaqueProps, "title.");
        LegendTitle legend = ret.getLegend();
        if (legend != null) {
            BeanUtil.setProps(legend, opaqueProps, "legend.");
            Paint p = legend.getBackgroundPaint();
            if (p != null && p instanceof Color) {
                float alpha = (float)ChartUtil.decodeDouble(
                        opaqueProps.get("legend." +
                                ChartConstants.CM_PROP_PREFIX + "backgroundAlpha"),
                        1.0);
                if (alpha != 1.0) {
                    int aint = (int) (alpha * 255);
                    Color c = (Color)p;
                    c = new Color(c.getRed(), c.getGreen(), c.getBlue(), aint);
                    legend.setBackgroundPaint(c);
                }
                //int 
                //c = new Color(c.getRed(), c.getGreen(), c)
            }
            //Paint p = new Color(0xff,0xff,0xff,0);
            //legend.setBackgroundPaint(p);
            //legend.getBackgroundPaint()
        }

        Axis domain = null;
        
        if (PLOT_XY == ptype || PLOT_TIME == ptype || PLOT_HISTOGRAM == ptype) {
            domain = ret.getXYPlot().getDomainAxis();
        } else if (PLOT_CATEGORY == ptype || PLOT_GANTT == ptype) {
            domain = ret.getCategoryPlot().getDomainAxis();
        }
        if (false) { // experiment with PeriodAxis for timeseries
            if (PLOT_TIME.equals(ci.getPlotType())) {
                XYPlot xyplot = (XYPlot) ret.getPlot();
                String lbl = xyplot.getDomainAxis().getLabel();
                PeriodAxis pa =  new PeriodAxis(lbl);
                xyplot.setDomainAxis(0, pa, true);
                //xyplot.setDomainAxis(pa);
                domain = pa;
            }
        }
        /*
        if (domain != null) {
            String domainAxisPrefix = ChartInfo.getDomainAxisPropertyPrefix();
            BeanUtil.setProps(domain,opaqueProps,domainAxisPrefix+".");
            if (domain instanceof ValueAxis) {
                ValueAxis va = (ValueAxis) domain;
                String autoRange = opaqueProps.get(domainAxisPrefix+".autoRange");
                if (!"false".equals(autoRange)) {
                    // autoRange is by default true
                    // so if it not set, make sure we set it to override any minimum/maximum settings the user may have left in
                    va.setAutoRange(true);
                }
                makeAxisTicksSane(va,ci);
            }
        }

        for (int i = 0; i < ChartConstants.MAX_RANGE_AXES; i++) {
            if (i != 0 && !ctxt.isRangeAxisVisible(i)) {
                continue;
            }
            AxisLocation location = ctxt.getRangeAxisLocation(i);
            ValueAxis range = ChartUtil.decodeValueAxis(ctxt.getRangeAxisType(i));
            if (range == null && PLOT_GANTT != ptype) {
                range = new NumberAxis();
            }
            if (PLOT_XY == ptype || PLOT_TIME == ptype || PLOT_HISTOGRAM == ptype) {
                ret.getXYPlot().setRangeAxis(i, range);
                if (location != null) {
                    ret.getXYPlot().setRangeAxisLocation(i, location);
                }
            } else if (PLOT_CATEGORY == ptype) {
                ret.getCategoryPlot().setRangeAxis(i, range);
                if (location != null) {
                    ret.getCategoryPlot().setRangeAxisLocation(i, location);
                }
            } else if (PLOT_GANTT == ptype) {
                if (location != null) {
                    ret.getCategoryPlot().setRangeAxisLocation(i, location);
                }
                continue;
            } else {
                continue;
            }
            String rangeAxisPrefix = ChartInfo.getRangeAxisPropertyPrefix(i);
            BeanUtil.setProps(range,opaqueProps,rangeAxisPrefix+".");
            String autoRange = opaqueProps.get(rangeAxisPrefix+".autoRange");
            if (!"false".equals(autoRange)) {
                // autoRange is by default true
                // so if it not set, make sure we set it to override any minimum/maximum settings the user may have left in
                if (range != null)
                    range.setAutoRange(true);
            }
            // sanity check tick size
            this.makeAxisTicksSane(range,ci);
        }
        */

        Plot plot = ret.getPlot();

        // there can be up to 4 default renderers, one per axis
        // if the user didn't specify per-series renderers but he chose to put the series
        // on different axes

        List<Object> defaultRenderers = new ArrayList<Object>();
        
        if (plot instanceof XYPlot) {
            XYURLGenerator urlGen = new XYURLGenerator() {
                public String generateURL(XYDataset ds, int series, int item) {
                    try {
                        return ctxt.getItemURL(ds, series, item);
                    } catch (RuntimeException re) {
                        re.printStackTrace();
                        throw re;
                    }
                }
            };
            // TimeSeriesURLGenerator urlGen = new TimeSeriesURLGenerator();
            XYPlot xyplot = (XYPlot) plot;
            xyplot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
            xyplot.setSeriesRenderingOrder(SeriesRenderingOrder.REVERSE);
            String defaultRenderType = ci.getRenderType();
            if (defaultRenderType == null) {
                defaultRenderType = DEFAULT_XY_RENDERER;
            }

            for (int i = 0; i < ctxt.getDatasetCount(); i++) {
                XYDataset xyd = (XYDataset) ctxt.getDataset(i);
                String rname = ctxt.getRendererForDataset(i);
                
                XYItemRenderer render;
                if (rname != null && !rname.equals("")) { // empty string means use the default renderer
                    // this DataSet has its own renderer, separate from the default for this chart
                    render = getXYRenderer(rname);
                    // set its custom renderer properties
                    SimpleProps sp = ctxt.getRendererPropsForDataset(i);
                    BeanUtil.setProps(render,sp,"renderer.");
                } else {
                    // clone of default renderer
                    render = getXYRenderer(defaultRenderType);
                    defaultRenderers.add(render);
                }
                xyplot.setRenderer(i,render);
                xyplot.setDataset(i, xyd);
                render.setURLGenerator(urlGen);
                int axis = ctxt.getRangeAxisForDataset(i);
                xyplot.mapDatasetToRangeAxis(i, axis);

            }
            DrawingSupplier dsup = xyplot.getDrawingSupplier();
            

            int[] seriesCounts = new int[ctxt.getDatasetCount()];
            
            //for (int i = ctxt.getSeriesCount() - 1; i >= 0; i--) {
            for (int i = 0; i < ctxt.getSeriesCount(); i++) {
                SeriesDescriptor sd = ctxt.getSeries(i);
                
                // calculate the series paint regardless of whether the series is visible or not 
                // to avoid having series colors be unnecessarily jostled due when series are
                // set visible or not visible
                Paint seriesPaint = null;
                String color = ctxt.getColor(i);
                if (color != null) {
                    seriesPaint = ChartUtil.decodePaint(color);
                }
                if (seriesPaint == null) {
                    seriesPaint = dsup.getNextPaint();
                }
                
                if (seriesPaint instanceof Color) {
                    // only encode the paints that are Color, not the GradientPaints since the front-end can't handle those
                    colorMap.put(sd.getSid(),ChartUtil.encodeColor((Color)seriesPaint));
                }
                if (!ctxt.isSeriesVisible(i)) {
                    continue;
                }
                XYDataset xyd = (XYDataset) ctxt.getDatasetForSeries(sd);
                int dsIndex = ctxt.getIndexOfDataset(xyd);
                XYItemRenderer render = xyplot.getRenderer(dsIndex);

                if (sd.getSeriesNameFromData() == -1) {
                    //p("setting series paint for #" + i + "/" + sd.getName() + " to " + seriesPaint
                      //      + " at index " + seriesCounts[dsIndex]);
                    // set the series paint as long as we don't  
                    // have a dynamic number of series created from a data column
                    render.setSeriesPaint(seriesCounts[dsIndex]++, seriesPaint);
                    
                    /*int ind = getIndexOfSeries(xyd, sd.getName());
                    if (ind != -1) {
                        render.setSeriesPaint(ind, seriesPaint);
                    } else {
                        log.warn("cannot find series " + sd.getName() + " in dsIndex=" + dsIndex);
                    }
                    */
                }
            }
            for (int i = 0; i <ci.getMarkerCount() ;i++ ) {
                MarkerDescriptor md = ci.getMarker(i);
                if (md.isVisible()) {
                    this.addMarker(ctxt,md,xyplot,ctxt.getMarkerValue(i));
                }
            }
            // try a pointer annotation
            if (false) {
                int annoItem = 8;
                XYDataset xyd = (XYDataset) ctxt.getDataset(0);
                if (xyd.getItemCount(0) > annoItem) {
                double x = xyd.getXValue(0, annoItem);
                    double y = xyd.getYValue(0, annoItem);
                    XYPointerAnnotation pointer = new XYPointerAnnotation(
                            "Data Point Eight", x, y, 0);
                    pointer.setAngle(-1);
                    pointer.setLabelOffset(pointer.getLabelOffset() + 10);
                    /*
                     * Paint red = ChartUtil.decodeColor("purple");
                     * pointer.setPaint(red); pointer.setArrowPaint(red);
                     */
                    xyplot.addAnnotation(pointer);
                }
            }            
        } else if (plot instanceof CategoryPlot) {
            CategoryPlot cplot = (CategoryPlot) plot;
            cplot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
            String defaultRenderType = ci.getRenderType();
            if (defaultRenderType == null) {
                defaultRenderType = DEFAULT_CATEGORY_RENDERER;
            }
            /* We create a dataset and renderer for each distinct (renderType,rangeAxis)
             * SeriesDescriptor tuple on the chart.  Each series is then mapped to a
             * series paint within the renderer.  We create a default renderer for each
             * of the possible range axes.  Note that each of these renderers needn't
             * actually be used....
             */
            for (int i = 0; i < ChartConstants.MAX_RANGE_AXES; i++) {
                CategoryItemRenderer defaultCategoryRenderer = getCategoryRenderer(defaultRenderType);
                //cplot.setRenderer(defaultCategoryRenderer);
                defaultRenderers.add(defaultCategoryRenderer);
            }
            
            for (int i = 0; i < ctxt.getDatasetCount(); i++) {
                CategoryDataset cd = (CategoryDataset) ctxt.getDataset(i);
                

                String rname = ctxt.getRendererForDataset(i);
                
                CategoryItemRenderer render;
                if (rname != null && !rname.equals("") && !rname.equals(ci.getRenderType())) {
                    // empty string means use the default renderer
                    // this DataSet has its own renderer, separate from the default for this chart
                    render = getCategoryRenderer(rname);
                    // set its custom renderer properties
                    SimpleProps sp = ctxt.getRendererPropsForDataset(i);
                    BeanUtil.setProps(render,sp,"renderer.");
                } else {
                    int axis = ctxt.getRangeAxisForDataset(i);
                    render = (CategoryItemRenderer)defaultRenderers.get(axis);
                }
                cplot.setRenderer(i,render);
                cplot.setDataset(i, cd);
                int axis = ctxt.getRangeAxisForDataset(i);
                cplot.mapDatasetToRangeAxis(i, axis);
            }
            DrawingSupplier dsup = cplot.getDrawingSupplier();
            
            // keep track of how many series are attached to each renderer
            int[] seriesCounts = new int[ctxt.getDatasetCount()];

            //for (int i = ctxt.getSeriesCount() - 1; i >= 0; i--) {
            for (int i = 0; i < ctxt.getSeriesCount(); i++) {
                SeriesDescriptor sd = ci.getSeriesDescriptor(i);
                // calculate the series paint regardless of whether the series is visible or not 
                // to avoid having series colors be unnecessarily jostled due when series are
                // set visible or not visible
                Paint seriesPaint = null;
                String color = ctxt.getColor(i);
                if (color != null) {
                    seriesPaint = ChartUtil.decodePaint(color);
                }
                if (seriesPaint == null) {
                    seriesPaint = dsup.getNextPaint();
                }

                if (seriesPaint instanceof Color) {
                    // only encode the paints that are Color, not the GradientPaints since the front-end can't handle those
                    colorMap.put(sd.getSid(),ChartUtil.encodeColor((Color)seriesPaint));
                }

                if (!ctxt.isSeriesVisible(i)) {
                    continue;
                }

                
                CategoryDataset cd = (CategoryDataset) ctxt.getDatasetForSeries(sd);
                int dsIndex = ctxt.getIndexOfDataset(cd);
                //p("category color(" + i + ")->" + color + " paint=" + seriesPaint + " on dsIndex=" + dsIndex);
                CategoryItemRenderer renderer = cplot.getRenderer(dsIndex);
                renderer.setSeriesPaint(seriesCounts[dsIndex]++, seriesPaint);
            }
            List<MarkerDescriptor> markers = ci.getMarkers();
            for (int i = 0; i < markers.size(); i++) {
                MarkerDescriptor md = markers.get(i);
                if ((md.getType() == MarkerDescriptor.MARKER_TYPE_NUMERIC ||md.getType() == MarkerDescriptor.MARKER_TYPE_NUMERIC_INTERVAL) &&
                    md.isVisible() && md.isRange() && ctxt.isRangeAxisVisible(md.getAxisIndex())) {
                    if (md.getType() == MarkerDescriptor.MARKER_TYPE_NUMERIC) {
                        //Marker m = this.createValueMarker(md,markerValues.get(i));
                        Marker m = this.createValueMarker(md,ctxt.getMarkerValue(i));
                        if (m != null) {
                            Layer l;
                            l = ChartUtil.decodeLayer(md.getLayer(), Layer.BACKGROUND);
                            cplot.addRangeMarker(md.getAxisIndex(), m, l);
                        }
                    } else {
                        //MarkerValue mv = markerValues.get(i);
                        MarkerValue mv = ctxt.getMarkerValue(i);
                        this.addIntervalMarker(cplot, md, mv);
                    }
                }
                
            }

        } else if (plot instanceof PiePlot) {
            PiePlot pplot = (PiePlot) plot;
            // only one dataset for pie plots, use the first one in the dsmap.
            if (ctxt.getSeriesCount() > 0) {
                Dataset ds = ctxt.getDataset(0);
                if (ds == null) {
                    log.warn("unexpected null data set for pie plot");
                } else {
                    pplot.setDataset((PieDataset) ds);
                    PieURLGenerator purl = new PieURLGenerator() {
                        public String generateURL(PieDataset pds, Comparable comp,
                                int pieIndex) {
                            return comp + "|" + pieIndex + "|0";
                        }
                    };
                    pplot.setURLGenerator(purl);
                    String pieLabelFormat = opaqueProps.get("plot."+PieItemLabelFormats.PIE_ITEM_FORMAT_PROPERTY);
                    pplot.setLabelGenerator(getPieLabelGenerator(pieLabelFormat));
                }
            } else {
                log.warn("no visible data set for pie plot");
            }
        }

        BeanUtil.setProps(ret, opaqueProps, "chart.");
        BeanUtil.setProps(plot, opaqueProps, "plot.");
        for (Object renderer : defaultRenderers) {
            BeanUtil.setProps(renderer, opaqueProps,"renderer.");
        }

        for (int i = 0; i < ci.getMarkers().size(); i++ ) {
            MarkerDescriptor md = ci.getMarkers().get(i);
            SimpleProps markerProps = md.getMarkerProps();
            if (markerProps != null) {
                String pstr = markerProps.get("paint");
                if (pstr != null) {
                    colorMap.put(md.getSid(),pstr);
                }        
            }
        }
        
        final LegendItemCollection lcoll = new LegendItemCollection();
        // add interval markers to the legend
        if (plot instanceof XYPlot || plot instanceof CategoryPlot) {
            List<MarkerDescriptor> markers = ci.getMarkers();
            for (int i = 0; i < markers.size(); i++) {
                MarkerDescriptor md = markers.get(i);
                if ( md.isInterval() && md.isVisible()) {
//                    SimpleProps markerProps = md.getMarkerProps();
//                    String label = markerProps.get("label");
                    String label = md.getLabel();
                    if (label == null) label = md.getName();
                    if (label == null) label = "";
                    //String pstr = markerProps.get("paint");
                    String pstr = md.getPaint();
                    if (pstr == null) {
                        pstr = "#808080"; // default for IntervalMarker
                    }
                    
                    colorMap.put(md.getSid(),pstr);
                    
                    float alpha = 0.8f; // default for IntervalMarker
                    //String fstr = markerProps.get("alpha");
                    String fstr = md.getAlpha();
                    if (fstr != null) {
                        try {
                            alpha = Float.parseFloat(fstr);
                        } catch (NumberFormatException ignore) {}
                    }
                    Paint paint =  ChartUtil.decodeColor(pstr, alpha);
                    LegendItem le = new LegendItem(
                            label, null, null, null,
                            ShapeUtil.SQUARE, paint
                            );
                    lcoll.add(le);
                }
            }
            if (lcoll.getItemCount() > 0) {
                LegendItemSource src = new LegendItemSource() {
                    public LegendItemCollection getLegendItems() { return lcoll; }
                };
                LegendTitle lt = ret.getLegend();
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
        }
//        LegendTitle lt = ret.getLegend();
//        if (lt != null && !opaqueProps.containsKey("legend.itemFont")) {
//            // smaller fonts in legend if user didn't set it
//            lt.setItemFont(SUBTITLE_FONT);
//        }
        

        if (domain != null) {
            String domainAxisPrefix = ChartInfo.getDomainAxisPropertyPrefix();
            BeanUtil.setProps(domain,opaqueProps,domainAxisPrefix+".");
            if (domain instanceof ValueAxis) {
                ValueAxis va = (ValueAxis) domain;
                String autoRange = opaqueProps.get(domainAxisPrefix+".autoRange");
                if (!"false".equals(autoRange)) {
                    // autoRange is by default true
                    // so if it not set, make sure we set it to override any minimum/maximum settings the user may have left in
                    va.setAutoRange(true);
                }
                makeAxisTicksSane(va,ci);
            }
        }

        for (int i = 0; i < ChartConstants.MAX_RANGE_AXES; i++) {
            if (!ctxt.isRangeAxisVisible(i)) {
                if (i == 0) {
                    if (PLOT_XY == ptype || PLOT_TIME == ptype || PLOT_HISTOGRAM == ptype) {
                        ret.getXYPlot().getRangeAxis(0).setVisible(false);
                    } else if (PLOT_CATEGORY == ptype || PLOT_GANTT == ptype) {
                        ret.getCategoryPlot().getRangeAxis(0).setVisible(false);
                    }
                }
                continue;
            }
            AxisLocation location = ctxt.getRangeAxisLocation(i);
            ValueAxis range = ChartUtil.decodeValueAxis(ctxt.getRangeAxisType(i));
            if (range == null && PLOT_GANTT != ptype) {
                range = new NumberAxis();
            }
            if (PLOT_XY == ptype || PLOT_TIME == ptype || PLOT_HISTOGRAM == ptype) {
                ret.getXYPlot().setRangeAxis(i, range);
                if (location != null) {
                    ret.getXYPlot().setRangeAxisLocation(i, location);
                }
            } else if (PLOT_CATEGORY == ptype) {
                ret.getCategoryPlot().setRangeAxis(i, range);
                if (location != null) {
                    ret.getCategoryPlot().setRangeAxisLocation(i, location);
                }
            } else if (PLOT_GANTT == ptype) {
                if (location != null) {
                    ret.getCategoryPlot().setRangeAxisLocation(i, location);
                }
                continue;
            } else {
                continue;
            }
            String rangeAxisPrefix = ChartInfo.getRangeAxisPropertyPrefix(i);
            BeanUtil.setProps(range,opaqueProps,rangeAxisPrefix+".");
            String autoRange = opaqueProps.get(rangeAxisPrefix+".autoRange");
            if (!"false".equals(autoRange)) {
                // autoRange is by default true
                // so if it not set, make sure we set it to override any minimum/maximum settings the user may have left in
                if (range != null)
                    range.setAutoRange(true);
            }
            // sanity check tick size
            this.makeAxisTicksSane(range,ci);
        }
        
        return ret;
    }

    
    private HashMap<String,StandardPieSectionLabelGenerator> pieLabelGeneratorsMap = new HashMap<String,StandardPieSectionLabelGenerator>();
    
    /**
     * return the StandardPieLabelGenerator based on the string setting (one of the legal PieItemLabelFormats values)
     * @param s
     * @return
     */
    private StandardPieSectionLabelGenerator getPieLabelGenerator(String s) {
        if (s == null)
            s = PieItemLabelFormats.DEFAULT.toString();
        
        StandardPieSectionLabelGenerator gen = pieLabelGeneratorsMap.get(s);
        if (gen == null) {
            DecimalFormat numberFormat = new DecimalFormat("##");
            DecimalFormat number2Format = new DecimalFormat("##.##");
            DecimalFormat percentFormat = new DecimalFormat("##%");
            DecimalFormat percent2Format = new DecimalFormat("##.##%");

            PieItemLabelFormats pfmt = PieItemLabelFormats.get(s);
            if (pfmt == null) {
                pfmt = PieItemLabelFormats.DEFAULT;
            }
         
            switch (pfmt) {
            case NAME_ONLY:
                gen = new StandardPieSectionLabelGenerator("{0}");
                break;
            case NAME_PERCENTAGE:
                gen = new StandardPieSectionLabelGenerator("{0} {2}",numberFormat,percentFormat);
                break;
            case NAME_PERCENTAGE2:
                gen = new StandardPieSectionLabelGenerator("{0} {2}",numberFormat,percent2Format);
                break;
            case NAME_PERCENTAGE2_IN_PAREN:
                gen = new StandardPieSectionLabelGenerator("{0} ({2})",numberFormat,percent2Format);
                break;
            case NAME_PERCENTAGE_IN_PAREN:
                gen = new StandardPieSectionLabelGenerator("{0} ({2})",numberFormat,percentFormat);
                break;
            case NAME_EQ_VALUE2_PERCENTAGE2:
                gen = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",number2Format,percent2Format);
                break;
            case NAME_EQ_VALUE_PERCENTAGE:
                gen = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",numberFormat,percentFormat);
                break;
            case PERCENTAGE2_ONLY:
                gen = new StandardPieSectionLabelGenerator("{2}",numberFormat,percent2Format);
                break;
            case PERCENTAGE_ONLY:
                gen = new StandardPieSectionLabelGenerator("{2}",numberFormat,percentFormat);
                break;
            case VALUE2_ONLY:
                gen = new StandardPieSectionLabelGenerator("{1}",number2Format,percentFormat);
                break;
            case VALUE_ONLY:
                gen = new StandardPieSectionLabelGenerator("{1}",numberFormat,percentFormat);
                break;

            }
            pieLabelGeneratorsMap.put(s,gen);
        }
        return gen;
    }
    /**
     * add a marker to the xyplot
     * @param ci
     * @param dsmap
     * @param md
     * @param xyplot
     * @param mv
     */
    private void addMarker(ChartContext ctxt, MarkerDescriptor md, XYPlot xyplot, MarkerValue mv) {
        if (!md.isInterval()) {
            Marker marker = createValueMarker(md,mv);
            if (marker != null) {
                Layer layer;
                layer = ChartUtil.decodeLayer(md.getLayer(), Layer.BACKGROUND);
                if (md.isRange()) {
                    if (ctxt.isRangeAxisVisible(md.getAxisIndex())) {
                        xyplot.addRangeMarker(md.getAxisIndex(), marker, layer); 
                    }
                } else {
                    xyplot.addDomainMarker(0, marker, layer);
                }
            }
        } else {
            this.addIntervalMarker(xyplot, md, mv);
        }
    }

    private void addIntervalMarker(Plot plot, MarkerDescriptor md, MarkerValue mv) {
        CategoryPlot cplot = null;
        XYPlot xyplot = null;
        if (plot instanceof CategoryPlot) {
            cplot = (CategoryPlot) plot;
        } else if (plot instanceof XYPlot) {
            xyplot = (XYPlot) plot;
        }
        Layer layer;
        layer = ChartUtil.decodeLayer(md.getLayer(), Layer.BACKGROUND);
        
        SimpleProps markerProps = md.getMarkerProps();
        List<double[]> valList = mv.getValues();
        for (int j = 0; j < valList.size(); j++) {
            double[] vals = valList.get(j);
            if (vals.length >= 2 && vals[0] <= vals[1]) {
                IntervalMarker im = new IntervalMarker(vals[0], vals[1]);
                setMarkerInsets(im);
                BeanUtil.setProps(im,markerProps,null);
                // use no label
                if (!md.isLabelVisible()) {
                    // no label
                    im.setLabel(null);
                }
                if (cplot != null) {
                    if (md.isRange()) {
                        cplot.addRangeMarker(md.getAxisIndex(), im, layer);
                    } else {
                        // FIXME: does domain (interval) marker on category plot even work?
                    }
                } else if (xyplot != null) {
                    if (md.isRange()) {
                        xyplot.addRangeMarker(md.getAxisIndex(), im, layer);
                    } else {
                        xyplot.addDomainMarker(im, layer);
                    }
                }
                
            }
        }
    }
    private void setMarkerInsets(Marker marker) {
        RectangleInsets ri = new RectangleInsets(10,10,10,10);
        marker.setLabelOffset(ri);

    }
    private ValueMarker createValueMarker(MarkerDescriptor md,MarkerValue mv) {
        ValueMarker marker = null;
        List<double[]> vals = mv.getValues();
        if (vals != null && vals.size() > 0) {
            double value = vals.get(0)[0];
            marker = new ValueMarker(value);
            setMarkerInsets(marker);
            marker.setLabel(md.getName());
            BeanUtil.setProps(marker,md.getMarkerProps(),null);
        }
        return marker;
    }
    
//    private int getIndexOfSeries(SeriesDataset xyd, String seriesName) {
//        int ret = -1;
//        for (int i = 0; seriesName != null && i < xyd.getSeriesCount(); i++) {
//            Object sk = xyd.getSeriesKey(i);
//            // comparing the series name is not optimal since series name is not unique
//            if (seriesName.equals(sk)) {
//                ret = i;
//                break;
//            }
//        }
//        return ret;
//    }
    
    
    private static String DEFAULT_XY_RENDERER = DefaultXYItemRenderer.class.getName();
    private XYItemRenderer getXYRenderer(String type) {
        XYItemRenderer ret = null;
        String cname = typeSystem.getXYRenderer(type);
        if (cname == null) {
            log.warn("unknown XYRenderer '" + type + "' using default XY renderer");
            cname = DEFAULT_XY_RENDERER;
            //throw new RuntimeException("unknown XYRenderer '" + type + "' using default XY renderer");
        }
        
        try {
            if (cname.endsWith("XYBubbleRenderer")) {
                ret = new XYBubbleRenderer(XYBubbleRenderer.SCALE_ON_RANGE_AXIS);
//            } else if ("CandlestickRenderer".equals(type)) {
//                ret = new CandlestickRenderer();
            } else if ("Line".equals(type)) {
                // yes lines, no shapes
                ret = new XYLineAndShapeRenderer(true, false);
            } else {
                Class c = Class.forName(cname);
                ret = (XYItemRenderer) c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed to instantiate " + cname,e);
            throw new RuntimeException(
                    "Cannot instantiate: " + cname, e
                    );
        }
        return ret;
    }

    private static String DEFAULT_CATEGORY_RENDERER = BarRenderer.class.getName();
    private CategoryItemRenderer getCategoryRenderer(String type) {
        CategoryItemRenderer ret = null;
        String cname = typeSystem.getCatRenderer(type);
        if (cname == null) {
            log.warn("unknown CategoryRenderer '" + type + "' using default category renderer");
            cname = DEFAULT_CATEGORY_RENDERER;
            //throw new RuntimeException("unknown CategoryRenderer '" + type + "' using default category renderer");
        }

        try {
            Class c = Class.forName(cname);
            ret = (CategoryItemRenderer) c.newInstance();
        } catch (Exception e) {
            log.error("failed to instantiate " + cname,e);
            throw new RuntimeException(
                    "Cannot instantiate: " + cname, e
                    );
        }
        return ret;
    }

    /* re-throw RuntimeException as-is
     * wrap checked exceptions
     */
    private void handleException(String msg, Exception e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }
        if (msg == null) {
            msg = e.getMessage();
        }
        throw new RuntimeException(msg, e);
    }
    
    // 
    // in order to force auto ticks to be no smaller than the timePeriod, 
    // we have to set the TickUnitSource for the DateAxis accordingly b/c JFreeChart chooses from the TickUnitSource
    // when calculating the auto ticks at render time.  We have no earlier opportunity to intercept and change it
    //
    // a bit cumbersome, but the effect of this is to allow auto tick to do its thing normally, and then when the range is small
    // to ensure that we never have ticks that is smaller than the timePeriod of the chart
    //
    // logic adding of the ticks derived from JfreeChart code, see org.jfree.chart.axis.DateAxis
    
    private TickUnits yearTicks = null;
    private TickUnits monthTicks = null;
    private TickUnits qtrTicks = null;
    private TickUnits weekTicks = null;
    
    private TickUnits getYearTicks() {
        if (yearTicks == null) {
            TickUnits units = new TickUnits();
            yearTicks = addYearlyTicks(units);
        }
        return yearTicks;
    }
    private TickUnits getMonthTicks() {
        if (monthTicks == null) {
            TickUnits units = new TickUnits();
            units = addMonthlyTicks(units);
            monthTicks = addYearlyTicks(units);
        }
        return monthTicks;
    }

    private TickUnits getQuarterTicks() {
        if (qtrTicks == null) {
            TickUnits units = new TickUnits();
            units = addQuarterlyTicks(units);
            qtrTicks = addYearlyTicks(units);
        }
        return qtrTicks;
    }

    private TickUnits getWeekTicks() {
        if (weekTicks == null) {
            TickUnits units = new TickUnits();
            units = addWeeklyTicks(units);
            weekTicks = addYearlyTicks(units);
        }
        return weekTicks;
    }

    
    private TickUnits addWeeklyTicks(TickUnits units) {
        DateFormat f5 = new SimpleDateFormat("d-MMM");
        // days                                                                                          
        units.add(new DateTickUnit(DateTickUnit.DAY, 7,
                DateTickUnit.HOUR, 1, f5));
        units.add(new DateTickUnit(DateTickUnit.DAY, 14,
        DateTickUnit.HOUR, 1, f5));
        units.add(new DateTickUnit(DateTickUnit.DAY, 21,
                DateTickUnit.DAY, 1, f5));
        return units;
    }
    
    private TickUnits addMonthlyTicks(TickUnits units) {
        DateFormat f6 = new SimpleDateFormat("MMM-yyyy");
        // months                                                                                        
        units.add(new DateTickUnit(DateTickUnit.MONTH, 1,
                DateTickUnit.DAY, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 2,
                DateTickUnit.DAY, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 3,
                DateTickUnit.MONTH, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 4,
                DateTickUnit.MONTH, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 6,
                DateTickUnit.MONTH, 1, f6));
        return units;
    }
    
    private TickUnits addQuarterlyTicks(TickUnits units) {
        DateFormat f6 = new SimpleDateFormat("MMM-yyyy");
        // months                                                                                        
        units.add(new DateTickUnit(DateTickUnit.MONTH, 3,
                DateTickUnit.DAY, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 6,
                DateTickUnit.MONTH, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 9,
                DateTickUnit.MONTH, 1, f6));
        return units;
    }

    private TickUnits addYearlyTicks(TickUnits units) {
        DateFormat f7 = new SimpleDateFormat("yyyy");
        
        // years                                                                                                                         
        units.add(new DateTickUnit(DateTickUnit.YEAR, 1,
                DateTickUnit.MONTH, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 2,
                DateTickUnit.MONTH, 3, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 5,
                DateTickUnit.YEAR, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 10,
                DateTickUnit.YEAR, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 25,
                DateTickUnit.YEAR, 5, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 50,
                DateTickUnit.YEAR, 10, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 100,
                DateTickUnit.YEAR, 20, f7));

        return units;
    }
    
    /**
     * make sure that our axis ticks are reasonable. 
     * ensure that there aren't too many, and ensure that ticks are no shorter than the timePeriod of timeseries plots
     * @param va
     * @param ci
     */
    private void makeAxisTicksSane(ValueAxis va, ChartInfo ci) {
        if (va == null) {
            return;
        }
        if (va instanceof DateAxis) {
            DateAxis da = (DateAxis) va;
            DateTickUnit dtu = da.getTickUnit();
            if (dtu != null) {
                if (da.isAutoTickUnitSelection()) {
                    switch (ci.getTimePeriod()) {
                    case TimeConstants.TIME_YEAR:
                        da.setStandardTickUnits(getYearTicks());
                        break;
                    case TimeConstants.TIME_QUARTER:
                        da.setStandardTickUnits(getQuarterTicks());
                        break;
                    case TimeConstants.TIME_MONTH:
                        da.setStandardTickUnits(getMonthTicks());
                        break;
                    case TimeConstants.TIME_WEEK:
                        da.setStandardTickUnits(getWeekTicks());
                        break;
                    default:
                        // don't worry about smaller time units
                        break;
                    }
                }
  
                Range rng = da.getRange();
                double upper = rng.getUpperBound();
                double lower = rng.getLowerBound();
                double tick = dtu.getSize();
                double width = Math.abs(upper - lower);
                double nticks = width / tick;
                if (!da.isAutoTickUnitSelection() && tick != 1.0 && nticks > 100.0) {
                    // for now, just pick a really big unit like YEAR
                    da.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, 1));
                }
                
//                DateRange dr = (DateRange) da.getRange();
//                log.warn("*** dr.getLowerDate() = " + dr.getLowerDate() +
//                         " dr.getUpperDate() = " + dr.getUpperDate() +
//                         " da.getMinimumDate() = " + da.getMinimumDate() +
//                         " da.getMaximumDate() = " + da.getMaximumDate() +
//                         " da.getAutoRangeMinimumSize() = " + da.getAutoRangeMinimumSize());
//                
//                tick = dtu.getSize();
//                width = Math.abs(upper - lower);
//                nticks = width / tick;
//                log.warn("***** dtu = " + dtu + " rng = " + rng + " upper = " + upper + 
//                         " lower = " + lower + " tick = " + dtu.getSize() + " nticks = " + nticks +
//                         "da.isAutoTickUnitSelection = " + da.isAutoTickUnitSelection() +
//                         "chart time period = " + ci.getTimePeriod());
            }
        } else {
            Range rng = va.getRange();
            double lower = rng.getLowerBound();
            double upper = rng.getUpperBound();
            if (va instanceof NumberAxis) {
                NumberAxis na = (NumberAxis) va;
                    double tick = na.getTickUnit().getSize();
                    if (na.isAutoTickUnitSelection()) {
                        if ( tick != 1.0d) {
                            // give the default tick size, which is 1
                            na.setTickUnit(new NumberTickUnit(1));
                        }
                    } else {
                    double width = Math.abs(upper - lower);
                    double nticks = width  / tick;
//                    p("NUM AXIS: autoTick=" + na.isAutoTickUnitSelection() + " tick=" + tick
//                            + " nticks=" + nticks + " width=" + width);
                    if (!na.isAutoTickUnitSelection() && tick != 1.0 && nticks > 50.0) {
                        double newtick = width / 20;
//                        p("numAxis converting tick size from " + tick + " to " + newtick);
                        na.setTickUnit(new NumberTickUnit(newtick));
                        //p("changing range axis nticks from " + nticks + " to " + width / 100.0);
                        //p("changing range axis tick size from " + tick + " to " + width / 20);
                    }
                    }
            }
        }
        
    }

    /**
     * write a chart to an output stream in Encapsulated PostScript format
     * @param out
     * @param chart
     * @param width
     * @param height
     * @throws IOException
     */
    public void writeChartAsPS(OutputStream out, JFreeChart chart, int width, int height) throws IOException {
        // instantiate a EPSDocumentGraphics2D instance

        // this is the code for using org.apache.xmlgraphics but the results seem inferior to the freeHEP
//        EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
//        g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
//        
//        g2d.setupDocument(out,width,height);
//        Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
//      chart.draw(g2d, r2D);
//        g2d.finish();
//        g2d.dispose();
            PSGraphics2D g2d = new PSGraphics2D(out, new Dimension(width,height));
            g2d.startExport();
            Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(g2d, r2D);
            g2d.endExport();
            g2d.closeStream();
    }
    

    /**
     * write out the chart as a EMF (Microsoft Enhanced MetaFormat file)
     * @param out
     * @param chart
     * @param width
     * @param height
     * @throws IOException
     */
    public void writeChartAsEMF(OutputStream out, JFreeChart chart, int width, int height) throws IOException {
            EMFGraphics2D g2d = new EMFGraphics2D(out, new Dimension(width,height));
            g2d.startExport();
            Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(g2d, r2D);
            g2d.endExport();
            g2d.closeStream();
    }

    
    /**
    * Writes a chart to an output stream in PDF format.
    *
    * @param out the output stream.
    * @param chart the chart.
    * @param width the chart width.
    * @param height the chart height.
    *
    */
    public void writeChartAsPDF(OutputStream out, JFreeChart chart, int width, int height, FontMapper mapper) {
        com.lowagie.text.Rectangle pagesize = new com.lowagie.text.Rectangle(width, height);
        Document document = new Document(pagesize,50.0f,50.0f,50.0f,50.0f);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.addAuthor("ChartMechanic");
            document.addSubject("ChartMechanic PDF");
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(width, height);
            Graphics2D g2 = tp.createGraphics(width, height, mapper);
            Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(g2, r2D);
            g2.dispose();
            cb.addTemplate(tp, 0, 0);
        }
        catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        document.close();
        
    }
    private static void p(String s) {
        System.out.println("[JFreeChartDriver] " + s);
    }

    
    /**
     * set the watermark chart logo.  if null, then clear it and use no logo
     * @param u
     */
    public void setDefaultChartLogo(LogoInfo logo) {
        defaultLogo = logoinfo2chartlogo(logo);
        
//        defaultLogo.visible = false;
//        if (logo.isVisible()) {
//            if (logo.getTxt() != null) {
//                defaultLogo.txt = logo.getTxt();
//            } else {
//                String urlStr = logo.getUrl();
//                try {
//                    if (urlStr != null) {
//                        URL u;
//                        if (urlStr.startsWith("/com/bayareasoftware/chartengine")) {
//                            // if the URL starts with /com/bayareasoftware/chartengine
//                            // treat it as a resource
//                            u = JFreeChartDriver.class.getResource(urlStr);
//                        } else {
//                            // otherwise, try to make a URL out of the string
//                            if (!(urlStr.startsWith("vfs:")) && !(urlStr.contains(":"))) {
//                                // if it's just a flat string, look up the file name from
//                                // vfs:admin/images/
//                                urlStr = "vfs:admin/images/" + urlStr;
//                            }
//
//                            u = new URL(urlStr);
//                        }
//                        log.info("setting chart logo to " + u.toExternalForm());
//
//                        ImageIcon ii = new ImageIcon(u);
//                        defaultLogo.img = ii.getImage();
//                        defaultLogo.width = ii.getIconWidth();
//                        defaultLogo.height = ii.getIconHeight();
//
//                        if (defaultLogo.width > 10 && defaultLogo.height > 10) {
//                            int w = defaultLogo.width*2/3;
//                            int h = defaultLogo.height*2/3;
//
//                            defaultLogo.smallImg = null; 
//
//                            // instead of using getScaledInstance, which is asynchronous
//                            // use the scaling version of Graphics2D.drawImage.
//                            // it is also asynchronous but we can add set an ImageObserver on it
//                            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
//                            Graphics2D g2 = bi.createGraphics();
//
//                            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//                            boolean ok = g2.drawImage(defaultLogo.img,0,0,w,h,new ImageObserver() {
//                                public boolean imageUpdate(Image img, int infoflags, int x,
//                                        int y, int width, int height) {
//                                    defaultLogo.smallImg = img;
//                                    return false;
//                                }
//
//                            });
//                            if (ok) {
//                                defaultLogo.smallImg = bi;
//                            }
//                        }
//                    }
//                } catch (MalformedURLException e) {
//                    log.error("Malformed url: " + urlStr + " for chart_logo");
//                    return;
//                }
//            }
//            defaultLogo.visible = true;
//        }
    }

    /*
    //@Override
    public void setChartLogo(URL u) {
        watermarkURL = u;
        if (u != null) {
            ImageIcon ii = new ImageIcon(u);
            watermarkImage = ii.getImage();
            watermark_width = ii.getIconWidth();
            watermark_height = ii.getIconHeight();
            
            if (watermark_width > 10 && watermark_height > 10) {
                int w = watermark_width*2/3;
                int h = watermark_height*2/3;
                //Image reducedImage = watermarkImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
                smallWatermarkImage = null; 
                
                // instead of using getScaledInstance, which is asynchronous
                // use the scaling version of Graphics2D.drawImage.
                // it is also asynchronous but we can add set an ImageObserver on it
                BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bi.createGraphics();
                
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                boolean ok = g2.drawImage(watermarkImage,0,0,w,h,new ImageObserver() {
                    public boolean imageUpdate(Image img, int infoflags, int x,
                                               int y, int width, int height) {
                        smallWatermarkImage = img;
                        return false;
                    }
                    
                });
                if (ok) {
                    smallWatermarkImage = bi;
                }
                
            }
            
        } else {
            watermarkImage = null;
            watermark_width = 0;
            watermark_height = 0;
            smallWatermarkImage = null;
        }
    }
    */
    
    
//    public URL getChartLogoURL() {
//        return watermarkURL; 
//    }
}
