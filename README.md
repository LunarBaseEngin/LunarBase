#10 minuts getting started
Runnable java examples are under the folder [example](https://github.com/LunarBaseEngin/Application/tree/master/src/LCG/Examples), clone this project to your desktop, and run it for yourself:

[creation.conf](https://github.com/LunarBaseEngin/Application/blob/master/creation.conf) is the configure file that tells LunarBase how to initialize a database. Any parameters in the [creation example](https://github.com/LunarBaseEngin/Application/blob/master/creation.conf) can be changed to meet your requirements. For example, if you need to cache the hottest 1 million records in memory, you shall change the parameter cache_records_in_memory = 20, (1 << 20 = 1 million). Of course, all the runtime tunable parameters can be changed at any time the db running.

#step 1: create a new database   
```
 String creation_conf = "/home/feiben/EclipseWorkspace/LunarBaseTutorial/creation.conf";  
 LunarDB.getInstance().createDB(creation_conf);  
 LunarDB.getInstance().closeDB();  
```

Where within the creation.conf, it tells us the database is located under ```root_path=/home/feiben/DBTest/```, 
and the name is ```RTSeventhDB```.
``` 
# ----------------#  
# Global settings #  
# --------------- #  

root_path=/home/feiben/DBTest/  

database_name = RTSeventhDB  
```

So the db root is ```/home/feiben/DBTest/RTSeventhDB```. The DBTaskCenter knowns from where to create the engine instance:
 
#step 2: create a new table and insert data to it   
```
String db_root = "/home/feiben/DBTest/RTSeventhDB";  
DBTaskCenter tc = new DBTaskCenter(db_root);  
String table = "order";
if(!tc.getActiveDB().hasTable(table))
{
    tc.getActiveDB().createTable(table); 
    tc.getActiveDB().openTable(table);  
}
		
LunarTable l_table = tc.getActiveDB().getTable(table);
l_table.addSearchable("string", "name");
l_table.addSearchable("int", "payment");
l_table.addSearchable("int", "age");
		
			
/*  
 * Step 1: construct an object of a records array  
 */  
 String[] records = new String[6];  
 records[0] = "{name=jackson8, payment=500, age=36}";  
 records[1] = "{name=jackson9, payment=500, age=25}";  
 records[2] = "{name=John8, payment=600, age=36}";  
 records[3] = "{name=Rafael8, payment=600, age=36}";  
 records[4] = "{name=Rafael9, payment=700, age=25}";  
 records[5] = "{name=John9, payment=700, age=36}";  
 		
 /*  
  * Step2: dispatch it. LunarBase engine handles it.  
  */  
 LFuture<Record32KBytes[]> inserted = tc.dispatch(new IncommingRecords(table, records));  
 tc.saveDB();  
``` 

#step 3: simple query  
```   	
 /*  
  * Step 3: Test query, see if they are correctly inserted,   
  * and if property-value pair can be retrieved.   
  */  
 QuerySimple sq = new QuerySimple(table, "age", "36", 200);  
 tc.dispatch(sq);
```

#step 4: shut down the database  
``` 		
 /*  
  * Step 4: Must not forget to shut down the db.  
  */  
 tc.shutdownDB();
```


# Embeded version
For more detail discussion on the feature and use of LunarBase, visit the wiki [here](
https://github.com/LunarBaseEngin/LunarBase/wiki)

or download the knowledge base file at [here](https://github.com/LunarBaseEngin/LunarBase/blob/master/LunarBase%20--%20A%20database%20engin%20for%20managing%20very%20large%20amounts%20of%20data%20--%20EN%20--V0.8.pdf)


