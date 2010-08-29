package doclet;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Properties;

import com.bayareasoftware.chartengine.chart.jfree.BeanUtil;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;

public class PropsDoclet extends Doclet {

    static Properties docProps = new Properties();
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {
            ClassDoc cd = classes[i];
            if (inspectMethods(cd, cd.methods())) {
                docProps.put(cd.qualifiedName() + ".class", cd.commentText());
            }
        }
        try {
            Writer w = new FileWriter("chart-props-doc.properties");
            docProps.store(w, "");
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    private static boolean inspectMethods(ClassDoc cd, ExecutableMemberDoc[] mems) {
        int count = 0;
        for (int i = 0; i < mems.length; i++) {
            if (isBeanSetter(mems[i])) {
                if (isTypeSupported(mems[i].parameters()[0].type())) {
                    String pname = cd.qualifiedName() + '.' + beanPropName(mems[i].name());
                    String pval = mems[i].commentText();
                    docProps.put(pname, pval);
                    count++;
                }
            }
        }
        return count > 0;
    }

    private static boolean isTypeSupported(Type p) {
        boolean ret = false;
        try {
        if (p.isPrimitive()) {
            ret = true;
        } else if (BeanUtil.supportsType(p.qualifiedTypeName())) {
            ret = true;
        } else {
            System.out.println("[PropsDoclet] " + p.qualifiedTypeName()
                    + " unsupported type");
        }
        } catch (Throwable t) {
            //t.printStackTrace();
            while (t.getCause() != null) t = t.getCause();
            t.printStackTrace();
        }
        return ret;
    }
    private static boolean isBeanSetter(ExecutableMemberDoc md) {
        boolean ret = false;
        if (md.isMethod() && !md.isStatic()) {
            Parameter[] params = md.parameters();
            if (params != null && params.length == 1) {
                String name = md.name();
                if (name.startsWith("set")) {
                    return true;
                }
            }
        }
        return ret;
    }
    
    private static boolean isBeanGetter(ExecutableMemberDoc md) {
        boolean ret = false;
        if (md.isMethod() && !md.isStatic()) {
            Parameter[] params = md.parameters();
            if (params == null || params.length == 0) {
                String name = md.name();
                if (name.startsWith("is") || name.startsWith("get")) {
                    return true;
                }
            }
        }
        return ret;
    }

    private static String beanPropName(String n) {
        String ret;
        if (n.startsWith("is")) ret = n.substring(2);
        else if (n.startsWith("get")) ret = n.substring(3);
        else if (n.startsWith("set")) ret = n.substring(3);
        else throw new RuntimeException("not a bean method '" + n + "'");
        if (ret.length() > 1 &&
                (Character.isLowerCase(ret.charAt(0)) ||
                    Character.isLowerCase(ret.charAt(1)))) {
                    ret = Character.toLowerCase(ret.charAt(0)) + ret.substring(1);
        }
        return ret;
    }
    
}
