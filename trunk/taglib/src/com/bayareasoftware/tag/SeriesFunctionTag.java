package com.bayareasoftware.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.FunctionDescriptor;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.model.FunctionDescriptor.ArgDescriptor;

public class SeriesFunctionTag extends AbstractSeriesTag
 implements ITagDoc {

    private String seriesId, func, args;
    
    @Override
    public List<AttInfo> getAttributes() {
        List<AttInfo> l = DataTag.makeAttsList(BASE_ATTS);
        l.add(SERIES_ATT);
        return DataTag.makeAttsList(ATTS, l);
    }

    protected static final AttInfo SERIES_ATT =
        new AttInfo("series", "The series name (ID) that this " +
        		"functional series will operate on.", true);
    
    private static final Object[][] ATTS = {
        {"series", "The series name (ID) that this functional series will operate on.", true },
        {"function", "Specifies the function that will be applied to another series." +
            "  Valid values are: <br/>" + getFuncNames(), true },
        {"args", "Sets the arguments for <code>function</code>.  In general, you " +
        		"probably want to use the appropriate function-specific tags," +
        		" rather than specifying the arguments here.<br/>" +
        		"Args must be separated by vertical bars, '|'"
             , true }
    };

    public void setSeries(String sname) { seriesId = sname; }
    public void setFunction(String fname) { func = fname; }
    public void setArgs(String args) { this.args = args; }
    
    private static String getFuncNames() {
        StringBuilder ret = new StringBuilder("<code>");
        List<FunctionDescriptor> l = BuiltInFunctions.getSeriesFunctions();
        for (FunctionDescriptor fd : l) {
            String n = fd.getName();
            if (n.startsWith("fn.")) n = n.substring(3);
            ret.append(n.toUpperCase()).append("<br/>\n");
        }
        return ret.append("</code>").toString();
    }
    
    @Override
    public int doEndTag() throws JspException {
        ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
        if (ct == null)
            throw new JspException("cannot find enclosing ChartTag");
        // validate & bind up our arguments
        FunctionDescriptor fd = BuiltInFunctions.findSeriesFunction(func);
        if (fd == null) {
            throw new JspException("'" + func + "' not a valid series function");
        }
        sd.setFunc(fd.getName());
        SeriesDescriptor other = ct.findSeries(seriesId);
        if (other == null) {
            throw new JspException("cannot find series '"
                    + seriesId + "'");
        }
        // series always first arg
        sd.addArg(new Arg(ArgType.SID, other.getSid()));
        String[] strArgs = StringUtil.splitCompletely(args, '|');
        List<ArgDescriptor> ads = fd.getArgs();
        // offset of one btw args and descriptors: SID is an arg
        if (strArgs.length != ads.size() - 1) {
            throw new JspException("series '" + sd.getName() + "' has wrong " +
            		"number of arguments for function '" + fd.getName() + "' (" +
            		"got " + strArgs.length + " expected " + ads.size() + ")");
        }
        for (int i = 0; i < strArgs.length; i++) {
            Arg a = new Arg(ads.get(i+1).getType(), strArgs[i]);
            sd.addArg(a);
        }
        ct.addSeriesDescriptor(sd);
        release();
        return super.doEndTag();
    }
    
    private static void p(String s) {
        System.out.println("[SeriesFuncTag] " + s);
    }
}
