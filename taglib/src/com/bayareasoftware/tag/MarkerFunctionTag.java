package com.bayareasoftware.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.FunctionDescriptor;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.FunctionDescriptor.ArgDescriptor;

public class MarkerFunctionTag extends AbstractMarkerTag 
implements ITagDoc {

    private String seriesId, func, arg;
    public void setSeries(String sname) {
        seriesId = sname;
    }
    public void setFunction(String f) {
        func = f;
    }
    public void setArg(String a) {
        arg = a;
    }
    
    private static Object[][] ATTS = {
        {"series", "Series to which the function will be applied", true },
        {"function", "Function to apply on the series.  Valid values are:"
            + getFuncNames(), true },
        {"arg", "Argument for the function, if any", true },
        //{"", "", false },
    };
    
    @Override
    public int doEndTag() throws JspException {
        ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
        if (ct == null)
            throw new JspException("cannot find enclosing ChartTag for marker "
                    + md.getName());
        
        FunctionDescriptor fd = BuiltInFunctions.findMarkerFunction(func);
        if (fd == null) {
            throw new JspException("'" + func + "' not a valid marker function");
        }
        SeriesDescriptor other = ct.findSeries(seriesId);
        if (other == null) {
            throw new JspException(
                    "marker '" + md.getName() + "' cannot find series '"
                    + seriesId + "'");
        }
        
        //md.setSid(other.getSid());
        md.setFunc(fd.getName());
        md.setAxisIndex(other.getAxisIndex());
        md.setRange(true);
        List<Arg> args = new ArrayList();
        args.add(new Arg(ArgType.SID, other.getSid()));
        // reducers have 0 or 1 arg, besides the series
        if (fd.getArgs() != null && fd.getArgs().size() > 1) {
            ArgDescriptor ad = fd.getArgs().get(1);
            if (arg != null)
                arg = SeriesFunctionTag.stringifyArg(ad.getType(), arg, ct);
            else
                arg = ad.getDefaultValue();
            args.add(new Arg(ad.getType(), arg));
        }
        md.setArgs(args);
        p("made marker=" + md + ", with args=" + args);
        p("func expects " + fd.getArgs().size() + " args");
        ct.addMarkerDescriptor(md);
        //ct.addSeriesDescriptor(sd);
        release();
        return super.doEndTag();
    }
    
    private static String getFuncNames() {
        StringBuilder ret = new StringBuilder("<code>");
        List<FunctionDescriptor> l = BuiltInFunctions.getReducerFunctions();
        for (FunctionDescriptor fd : l) {
            String n = fd.getName();
            if (n.startsWith("fn.")) n = n.substring(3);
            ret.append(n.toUpperCase()).append("<br/>\n");
        }
        return ret.append("</code>").toString();
    }
    
    public List<AttInfo> getAttributes() {
        List ret = super.getBaseAttributes();
        return DataTag.makeAttsList(ATTS, ret);
    }    
    
    private static void p(String s) {
        System.out.println("[MarkerFunctionTag] " + s);
    }
    
}
