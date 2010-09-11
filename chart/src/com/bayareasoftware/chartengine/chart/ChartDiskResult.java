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

import com.bayareasoftware.chartengine.model.StringUtil;

public class ChartDiskResult implements Serializable {
    // path to the files that contain the image, thumbnail, and image map (optional) 
    private String pngPath;
    private String thumbPath;
    private String imageMapPath;
    private String pdfPath;
    private String psPath;
    private String emfPath;
    
    //private transient byte[] data;
    //private transient byte[] thumbnail;
    //private String imageMap;

    // we ALWAYS generate the image,
    // and by default, we also want to generate thumbnails unless otherwise set

    private boolean generateThumbnail = true;
    private boolean generateImageMap = false;
    
    private boolean generatePDF = false;
    // Postscript
    private boolean generatePS = false;
    // Enhanced MetaFile format (Microsoft)
    private boolean generateEMF = false;
    
    // show images as error?
    private boolean showErrorImage = false;

    // error message raised during chart creation
    private String chartCreationError;

    public ChartDiskResult() {
        
    }
    
    public boolean isShowErrorImage() { return showErrorImage; }
    public void setShowErrorImage(boolean b) { showErrorImage = b; }

    
    public String getImagePath() {
        return pngPath;
    }

    public void setImagePath(String imagePath) {
        this.pngPath = imagePath;
    }

    public String getPdfPath() {
        return pdfPath;
    }
    
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
    
    public boolean hasPdfPath() {
        return notEmpty(pdfPath);
    }
    
    public String getPSPath() {
        return psPath;
    }
    
    public void setPSPath(String psPath) {
        this.psPath = psPath;
    }
    
    public boolean hasPSPath() {
        return notEmpty(psPath);
    }
    
    public String getEMFPath() {
        return emfPath;
    }
    
    public void setEMFPath(String emfPath) {
        this.emfPath = emfPath;
    }
    
    public boolean hasEMFPath() {
        return notEmpty(emfPath);
    }
    
    public String getThumbPath() {
        return thumbPath;
    }
    
    public void setThumbPath(String s) {
        thumbPath = s;
    }
    
    public String getImageMapPath() {
        return imageMapPath;
    }

    public void setImageMapPath(String imageMapPath) {
        this.imageMapPath = imageMapPath;
    }
    
    public String getImageMapId() {
        String ret = getImageMapPath();
        if (ret != null) {
            ret = StringUtil.basename(ret);
            ret = StringUtil.beforeDot(ret);
        }
        return ret;
    }
    public boolean hasImagePath() {
        return notEmpty(pngPath);
    }
    public boolean hasThumbPath() {
        return notEmpty(thumbPath);
    }
    
    public boolean hasImageMapPath() {
        return notEmpty(imageMapPath);
    }
    
    public boolean isGenerateThumbnail() {
        return generateThumbnail;
    }

    public void setGenerateThumbnail(boolean generateThumbnail) {
        this.generateThumbnail = generateThumbnail;
    }

    public boolean isGenerateImageMap() {
        return generateImageMap;
    }

    public void setGenerateImageMap(boolean generateImageMap) {
        this.generateImageMap = generateImageMap;
    }

    public boolean isGeneratePDF() {
        return generatePDF;
    }

    public void setGeneratePDF(boolean generatePDF) {
        this.generatePDF = generatePDF;
    }

    public boolean isGeneratePS() {
        return generatePS;
    }

    public void setGeneratePS(boolean generatePS) {
        this.generatePS = generatePS;
    }

    public boolean isGenerateEMF() {
        return generateEMF;
    }

    public void setGenerateEMF(boolean generateEMF) {
        this.generateEMF = generateEMF;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (pngPath != null)
            sb.append("imagePath:").append(pngPath).append(" ");
        if (imageMapPath != null)
            sb.append("imageMapPath:").append(imageMapPath).append(" ");
        if (thumbPath != null)
            sb.append("thumbPath:").append(thumbPath);
        if (pdfPath != null) 
            sb.append("pdfPath:").append(pdfPath);
        if (psPath != null) 
            sb.append("psPath:").append(psPath);
        return sb.toString();
    }
    
    public String getErrorMessage() { return this.chartCreationError; }
    public void setErrorMessage(String msg) { this.chartCreationError = msg; }
    
    
    private boolean notEmpty(String s) {return s !=null && s.trim().length() > 0;}

}