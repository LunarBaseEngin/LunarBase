package LCG.SearchEngine.Examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.DB.API.Result.FTQueryResult;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case1SearchByKeywords {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		
		LunarDB l_db = new LunarDB();
		String table = "profile";
		  
	 
		l_db.openDB(db_root);
				
		if(!l_db.hasTable(table))
			return;  

		//int[] key_ids = l_db.queryFullTextIDs(table, "comment","purchases",0);
		FTQueryResult result = l_db.queryFullText(table, "comment", "purchases",0);
		
		if(result.resultCount() == 0)
		{
			System.out.println("no results found");
			l_db.closeDB();
			return;
		}
		
		int top_n =1000;
		ArrayList<Record32KBytes> recs = result.fetchRecords(top_n);
				
		for(int i=0 ;i<recs.size() ;i++)
		{
			System.out.println(recs.get(i).getID() + ": "+recs.get(i).recData());
		}
		
			
		l_db.save();
		l_db.closeDB();
			 
		 
		 
	} 

}
