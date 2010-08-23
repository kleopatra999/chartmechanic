var LIMIT = 15;
var i = 0;
var goldColumn = data.col("Gold");
var silverColumn = data.col("Silver");
var bronzeColumn = data.col("Bronze");
var countryColumn = data.col("Country");
var totalColumn = data.col("Total");

for (i = 0; i < LIMIT && i < data.getRowCount(); i++) {
   var val = data.string(i, countryColumn);
   if (val != null && val.startsWith("Flag of ")) {
       var val = val.substring(8);
       data.setString(i, countryColumn, val);
   }
}
var gold = 0.0, silver = 0.0, bronze = 0.0;
for (i = LIMIT; i < data.getRowCount(); i++) {
   gold +=  data.num(i, goldColumn);
   silver += data.num(i, silverColumn);
   bronze += data.num(i, bronzeColumn);
}
var label = (data.getRowCount() - LIMIT) + " countries";
data.setString(LIMIT, countryColumn, label);
data.setNum(LIMIT, goldColumn, gold);
data.setNum(LIMIT, silverColumn, silver);
data.setNum(LIMIT, bronzeColumn, bronze);
data.setNum(LIMIT, totalColumn, gold + silver + bronze);
data.limit(0,LIMIT+1);


