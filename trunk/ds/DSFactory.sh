#!/bin/sh
CP=bin:classes
for i in lib/*.jar; do
  CP=$i:$CP;
done
for i in ../common/lib/*.jar; do
  CP=$i:$CP;
done
java -classpath $CP com.bayareasoftware.chartengine.ds.DSFactory $*