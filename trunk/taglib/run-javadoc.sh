#!/bin/sh


CP=bin:../chart/bin:../ds/bin

for i in `ls ../chart/lib/*.jar` ; do
 CP=$CP:$i
done

for i in `ls ../common/lib/*.jar` ; do
 CP=$CP:$i
done

for i in `ls ../common/compile-lib/*.jar` ; do
 CP=$CP:$i
done

for i in `ls ../ds/lib/*.jar` ; do
 CP=$CP:$i
done

#java -cp $CP com.bayareasoftware.chartengine.chart.jfree.BeanUtil
#echo $CP
#exit 1

JFREE_SOURCE=$HOME/apps/jfreechart-1.0.13/source
JCOMMON_SOURCE=$HOME/apps/jcommon-1.0.16/source

CHART_SRC=$JFREE_SOURCE/org/jfree/chart/JFreeChart.java
BLOCK_SRC=$JFREE_SOURCE/org/jfree/chart/block/AbstractBlock.java
TITLE_SRC=$JFREE_SOURCE/org/jfree/chart/title/Title.java
TEXT_SRC=$JFREE_SOURCE/org/jfree/chart/title/TextTitle.java
LEGEND_SRC=$JFREE_SOURCE/org/jfree/chart/title/LegendTitle.java
ABS_RENDER_SRC=$JFREE_SOURCE/org/jfree/chart/renderer/AbstractRenderer.java
PLOT_PKG=org.jfree.chart.plot
XY_PKG=org.jfree.chart.renderer.xy
CAT_PKG=org.jfree.chart.renderer.category
AXIS_PKG=org.jfree.chart.axis

javadoc -doclet doclet.PropsDoclet \
 -sourcepath $JFREE_SOURCE:$JCOMMON_SOURCE \
 -docletpath $CP -classpath $CP \
 $CHART_SRC $TITLE_SRC $LEGEND_SRC $ABS_RENDER_SRC \
 $TEXT_SRC $BLOCK_SRC \
 $PLOT_PKG $XY_PKG $CAT_PKG $AXIS_PKG

# -J-verbose:class -verbose \
