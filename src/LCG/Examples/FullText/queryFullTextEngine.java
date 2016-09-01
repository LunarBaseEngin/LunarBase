package LCG.Examples.FullText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.DB.API.Result.FTQueryResult;
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
	 
	FTQueryResult result = l_db.queryFullText(table, column ,query,0);
			
	long end_query_time = System.nanoTime(); 
			
	if(result.resultCount() == 0)
	{
		System.out.println("no results found");
		if(l_db != null)  
			l_db.closeDB();  
		return;
			
	}
	 
			
	ArrayList<Record32KBytes> recs = result.fetchRecords(result.resultCount());
			
	long end_time = System.nanoTime();
	double duration_query  = (end_query_time - start_time) / 1000000.0 ;
			 
	double duration_ms  = (end_time - start_time) / 1000000.0 ;
		 
	for(int i=0 ;i<recs.size() ;i++)
	{
		System.out.println(recs.get(i).getID() + ": "+recs.get(i).recData());
	}
			
	System.out.println("full text search has records: " + result.resultCount());
	System.out.println("Full-text query costs "+ duration_query + " (ms)");
	 		

	if(l_db!=null)
	{ 
		l_db.save();
		l_db.closeDB(); 
	} 

	} 
}
