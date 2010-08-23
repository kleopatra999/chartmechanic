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
package com.bayareasoftware.chartengine.chart;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.PlotType;
import com.bayareasoftware.chartengine.model.Rectangle;

/**
 * A ChartResult encapsulates the final result of making a chart,
 * At this point, there is no programmatic handle into the chart object anymore;
 * 
 * The 'output' of the chart is stored as various files in ChartDiskResult
 * 
 * ChartResult also contains some timing diagnostics for how long it
 * took to create the chart and to persist it to disk
 *
 */
public class ChartResult implements Serializable {
    
    public ChartResult() {
        this.diskResult = new ChartDiskResult();
    }
    
    public ChartResult(ChartDiskResult dr) {
        this.diskResult = dr;
    }
    
//    public static class Rectangle implements Serializable {
//        public int x,y,width,height;
//        public Rectangle() { }
//        public Rectangle(int x, int y, int w, int h) {
//            this.x = x;
//            this.y = y;
//            this.width = w;
//            this.height = h;
//        }
//        public Rectangle(double x, double y, double w, double h) {
//            this((int)x,(int)y,(int)w,(int)h);
//        }
//        public String toString() {
//            return "[Rectangle: x=" + x + ",y=" + y + ",w=" + width + ",h=" + height + "]";
//        }
//    }
    
//    public enum ResultType { PNG, URL }
//  private ResultType resultType;
    
    // type of chart/plot - see ChartConstants
    private PlotType plotType;

    private ChartDiskResult diskResult;

    public ChartDiskResult getDiskResult() {
        return diskResult;
    }
    
    public void setDiskResult(ChartDiskResult dr) {
        diskResult = dr;
    }
  
    // show images as error?
//    private boolean showErrorImage = false;

    // error message raised during chart creation
//  private String chartCreationError;
    
    // path to the files that contain the image, thumbnail, and image map (optional) 
//    private String imagePath;
//    private String thumbPath;
//    private String imageMapPath;
    
    private int width = -1, height = -1;

    /**
     * time in msecs to query the data source to get the dataset necessary to create the chart
     */
    private long queryTime;
    
    /*
     * time to create the chart object given the data set
     */
    private long createTime;
    
    /**
     * time to persist the chart to storage after it's been created
     */
    private long persistTime;

    /**
     * geometries of the title area, the (entire) plot area, and the
     * 'data' area of the plot (inside all the axes)
     */
    private Rectangle titleRect, plotRect, plotDataRect;
    /**
     * geometry of the subtitle (chart description) area
     */
    private Rectangle subtitleRect;
    
    
    private double[][] axisRanges;
    public double[][] getAxisRanges() { return axisRanges; }
    public double[] getAxisRange(int id) {
        return id >= 0 && axisRanges != null && id < axisRanges.length ? axisRanges[id] : null;
    }
    public void setAxisRange(int ind, double low, double high) {
        if (axisRanges == null) {
            axisRanges = new double[5][];
        }
        axisRanges[ind] = new double[2];
        axisRanges[ind][0] = low;
        axisRanges[ind][1] = high;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    public int getWidth() {
        return width;
    }

//    public void setData(byte[] data) {
//        this.data = data;
//    }
//    
//    public void setPNGData(byte[] data) {
//        setData(data);
//        setResultType(ResultType.PNG);
//    }
//    
//    public byte[] getData() {
//        return data;
//    }
//
//    public boolean hasData() {
//        return data.length > 0;
//    }

//    public void setThumbnail(byte[] tn) {
//        this.thumbnail = tn;
//    }
//    
//    public byte[] getThumbnail() {
//        return thumbnail;
//    }
//    
//    public void setPNGThumbnail(byte[] tn) {
//        setThumbnail(tn);
//        setResultType(ResultType.PNG);
//    }
//    
//    public boolean hasThumbnail() {
//        return thumbnail.length > 0;
//    }
    
//    public void setResultType(ResultType rt) {
//        this.resultType = rt;
//    }
//    
//    public ResultType getResultType() {
//        return resultType;
//    }
    
    public void setWidth(int width) {
        this.width = width;
    }

    
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long chartTime) {
        this.createTime = chartTime;
    }

    public long getPersistTime() {
        return persistTime;
    }

    public void setPersistTime(long persistTime) {
        this.persistTime = persistTime;
    }

    public long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(long queryTime) {
        this.queryTime = queryTime;
    }
     
    public Rectangle getTitleRect() { return titleRect; }
    public void setTitleRect(Rectangle r) { titleRect = r; }

    public Rectangle getSubtitleRect() { return subtitleRect; }
    public void setSubtitleRect(Rectangle r) { subtitleRect = r;}
    
    public Rectangle getPlotRect() { return plotRect; }
    public void setPlotRect(Rectangle r) { plotRect = r; }
    
    public Rectangle getPlotDataRect() { return plotDataRect; }
    public void setPlotDataRect(Rectangle r) { plotDataRect = r; }
    public PlotType getPlotType() { return plotType; }
    public void setPlotType(PlotType pt) { plotType = pt; }

//    public boolean isShowErrorImage() { return showErrorImage; }
//    public void setShowErrorImage(boolean b) { showErrorImage = b; }
    
//    public String getErrorMessage() { return this.chartCreationError; }
//    public void setErrorMessage(String msg) { this.chartCreationError = msg; }
    
    public String toString() {
        return diskResult.toString();
    }

    public String describeGeometry() {
        StringBuffer sb = new StringBuffer();
        sb.append("Chart data: width=" + width + " height=" + height + "\n");
        sb.append(" titleRect=").append(titleRect).append("\n");
        sb.append(" plotArea=" + this.plotRect + "\n");
        sb.append(" dataArea=" + this.plotDataRect + "\n");
        double[] domainRange = this.getAxisRange(0);
        if (domainRange != null && PlotType.PLOT_TIME == getPlotType()) {
            Date d = new Date((long)domainRange[0]);
            Date d2 = new Date((long)domainRange[1]);
            sb.append(" domain range: ").append(d).append(" - ").append(d2)
            .append("\n");
        }
        for (int i = 0; i < ChartConstants.MAX_RANGE_AXES; i++) {
            double[] vals = this.getAxisRange(i+1);
            if (vals != null && vals[0] != vals[1]) {
                sb.append(" range-axis-" + i + " range: " + vals[0] + " - " + vals[1])
                .append("\n");
            }
        }
        return sb.toString();
    }
    
    // map of BaseDescriptor sid to Color value as a string 
    public Map<Integer,String> colorMap; 
}
