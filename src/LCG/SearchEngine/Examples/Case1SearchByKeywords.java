package LCG.SearchEngine.Examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case1SearchByKeywords {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		
		LunarDB l_db = LunarDB.getInstance();
		String table = "profile";
		  
	 
		l_db.openDB(db_root);
				
		if(!l_db.hasTable(table))
			return;  

		int[] key_ids = l_db.queryFullTextIDs(table, "comment","purchases",0);
		if(key_ids == null)
		{
			System.out.println("no results found");
			return;
		}
		ArrayList<Record32KBytes> recs = l_db.fetchRecords(table, key_ids);
				
		for(int i=0;i<key_ids.length;i++)
		{
			System.out.println(key_ids[i] + ": "+recs.get(i).recData());
		}
			
		l_db.save();
		l_db.closeDB();
			 
		 
		 
	} 

}
