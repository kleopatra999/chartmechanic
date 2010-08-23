// load treasury 3 month
var tpath = 'demo/FRB/H15/H15/RIFLGFCM03_N.B.data';
var treas = fn.load(tpath);

var liborCol = data.col('LIBOR USD 3 months');

// add data points for 10/20,10/21
var newrow = data.addNewRow();
data.setValue(newrow,1,fn.date('2008-10-20'));
// FIXME: 4.4 on 10/20/08 fudged from looking @ chart
data.setValue(newrow,liborCol,4.41);
newrow = data.addNewRow();
data.setValue(newrow,1,fn.date('2008-10-21'));
data.setValue(newrow,liborCol,4.06);
// two new treasury rows
newrow = treas.addNewRow();
treas.setValue(newrow,1,fn.date('2008-10-17'));
treas.setValue(newrow,2,0.83);
newrow = treas.addNewRow();
treas.setValue(newrow,1,fn.date('2008-10-20'));
treas.setValue(newrow,2,1.24);

newrow = treas.addNewRow();
treas.setValue(newrow,1,fn.date('2008-10-21'));
treas.setValue(newrow,2,1.24);

data.sort(1,true);
treas.sort(1,true);
var drows = data.getRowCount();
var trows = treas.getRowCount();
var tcol = data.addCol('3 Month T-Bill', 'DOUBLE');
var tedcol = data.addCol('TED','DOUBLE');


var i,j = -1,tdate;
for (i = 0; i < drows; i++) {
   var ddate = data.date(i,1);
   if (ddate == null) continue;
   while (tdate == null || tdate.after(ddate)) {
      tdate = treas.date(++j,1);
   }
   if (!ddate.equals(tdate)) { continue; }

   if (!treas.isNull(j,2)) {
    var trate = treas.num(j,2);
    data.setNum(i,tcol,trate);
    if (!data.isNull(i,liborCol)) {
     var lrate = data.num(i,liborCol);
     var ted = lrate - trate;
     data.setNum(i,tedcol,ted);
    }
   }
}
//data = treas;