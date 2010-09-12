package demo;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.util.FileUtil;

/** quick program to remove chart-specific properties from the
 * template charts that I got from ChartMechanic.com
 * @author dave
 *
 */
public class FixTemplates {

    public static void main(String[] a) throws Exception {
        File dir = new File("src/com/bayareasoftware/tag/templates");
        File[] files = dir.listFiles();
        for (File f : files) {
            if (!f.getName().endsWith(".template")) continue;
            String s = FileUtil.readAsString(f);
            SimpleProps sp = new SimpleProps(s);
            SimpleProps sp2 = new SimpleProps();
            for (String key : sp.keySet()) {
                if (!ignore(key)) sp2.put(key, sp.get(key));
            }
            FileWriter fw = new FileWriter(f);
            fw.write(sp2.toString());
            fw.close();
        }
    }
    
    static Map<String,Boolean> ig = new HashMap();
    static {
        ig.put("_type", Boolean.TRUE);
        ig.put("_version", Boolean.TRUE);
        ig.put("description", Boolean.TRUE);
        ig.put("height", Boolean.TRUE);
        ig.put("width", Boolean.TRUE);
        ig.put("plottype", Boolean.TRUE);
        ig.put("rendertype", Boolean.TRUE);
        ig.put("timeperiod", Boolean.TRUE);
        ig.put("useCache", Boolean.TRUE);
    }
    private static boolean ignore(String s) {
        if (s.indexOf('.') == -1) return true;
        if (s.startsWith("sd.")) return true;
        if (s.startsWith("ds.")) return true;
        if (s.startsWith("renderer.")) return true;
        if (s.equals("title.text")) return true;
        return false;
    }
}
