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
package com.bayareasoftware.chartengine.model.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bayareasoftware.chartengine.model.DataType;

public class RendererDocInfo implements Serializable {

    private String classname, displayName, description;
    private boolean excluded = false;
    private String datasetType;
    private List<RendererColumnDocInfo> columns;
    public RendererDocInfo() { }
    
    public static RendererDocInfo createDefault(ChartBeanInfo cbi, boolean isCategory) {
        RendererDocInfo ret = new RendererDocInfo();
        ret.setClassname(cbi.getClassname());
        ret.setExcluded(false);
        ret.setDisplayName(RendererDocSet.renderShorthand(cbi.getDisplayname()));
        ret.setDescription("render a series as " + ret.getDisplayName());
        // FIXME: need enumeration of dataset types:
        // XY, XYZ, OHLC, statistical category, interval y,
        ret.setDatasetType("XY");
        // columns
        RendererColumnDocInfo domain;
        if (isCategory) {
            domain = new RendererColumnDocInfo(DataType.LABEL_TEXT, "any type is valid: text,date,number");
        } else {
            domain = new RendererColumnDocInfo(DataType.LABEL_NUMBER, "numeric type required");
        }
        ret.addColumn(domain);
        RendererColumnDocInfo range;
        range = new RendererColumnDocInfo(DataType.LABEL_NUMBER, "numeric type required");
        ret.addColumn(range);
        return ret;
    }
    public static class RendererColumnDocInfo implements Serializable {
        private String type, description;
        public RendererColumnDocInfo() { }
        public RendererColumnDocInfo(String t, String desc) {
            type = t;
            description = desc;
        }
        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }
    }
    private void addColumn(RendererColumnDocInfo cdoc) {
        if (columns == null) {
            columns = new ArrayList<RendererColumnDocInfo>();
        }
        columns.add(cdoc);
    }
    public int getColumnCount() {
        return columns != null ? columns.size() : 0;
    }
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public List<RendererColumnDocInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<RendererColumnDocInfo> columns) {
        this.columns = columns;
    }
    
}
