package LCG.Examples.FullText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.RecordTable.StoreUtile.Record32KBytes; 

public class queryFullTextEngine {

	public static void main(String[] args) throws InterruptedException, IOException {
		
	String db_root = "/home/feiben/DBTest/RTSeventhDB";
	
	LunarDB l_db = LunarDB.getInstance();
	String table = "profile"; 
	String column = "comment";  

	l_db.openDB(db_root); 

	long start_time = System.nanoTime(); 
			
	//String query = "purchases";
	String query = "大家";
	int[] key_ids = l_db.queryFullTextIDs(table, column,query,0);
			
	long end_query_id_time = System.nanoTime(); 
			
	if(key_ids == null)
	{
		System.out.println("no results found");
		if(l_db != null)  
			l_db.closeDB();  
		return;
			
	}
	int len = Math.min(key_ids.length, 1500);
			
	int[] ids = new int[len];

	System.arraycopy(key_ids, key_ids.length-len, ids, 0, len);
			
	ArrayList<Record32KBytes> recs = l_db.fetchRecords(table, ids);
			
	long end_time = System.nanoTime();
	double duration_get_id  = (end_query_id_time - start_time) / 1000000.0 ;
			 
	double duration_ms  = (end_time - start_time) / 1000000.0 ;
		 
	for(int i=0 ;i<ids.length;i++)
	{
		System.out.println(ids[i] + ": "+recs.get(i).recData());
	}
			
	System.out.println("full text search has records: " + key_ids.length );
	System.out.println("Getting ids costs "+ duration_get_id + " (ms)");
	System.out.println("Fetching records costs "+ duration_ms + " (ms)");
			

	if(l_db!=null)
	{ 
		l_db.save();
		l_db.closeDB(); 
	} 

	} 
}
