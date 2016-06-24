package LCG.SearchEngine.Examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case0BuildColumnFullTextIndex {
	public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
		
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		
		LunarDB l_db = LunarDB.getInstance();
		String table = "profile";
		
		/*
		 * Step 1: construct an object of a records array, 
		 * the column, which is going to be indexed by lunarbase search engine, 
		 * is in such a format: column_name=[\" column content here... ]\"
		 */
		String[] records = new String[4];
		records[0] = "{name=jackson6, score=50, comment=[\"jackson6 purchases 50 phones, is it true?,,.  \"]}"; 
		records[1] = "{name=John6, score=95, comment=[\"John6 purchases 250 tvs, it is confirmed \"]}"; 
		records[2] = "{name=Rafael6, score=75, comment=[\"Rafael6 has a complain submitted, and this is the 10-th time. Shall be answered.  \"]}"; 
		records[3] = "{name=tobedeleted, score=95, comment=[\"tobedeleted, this record is not real, shall be deleted.  \"]}"; 
  

	 
		l_db.openDB(db_root);
				
		if(!l_db.hasTable(table))
		{	
			l_db.createTable(table); 
			l_db.openTable(table);
			LunarTable tt = l_db.getTable(table);
			tt.addSearchable("string", "name");
					
			/*
			 * Step 2: add the column "comment" to lunarbase search engine.
			 */
			tt.addFulltextSearchable("comment");
		 
			 
			/*
			 * Step 3: insert records. LunarBase will tokenize the column "comment", 
			 * and build index on it for further key word search.
			 * 
			 * In this case, we has another searchable column "name" as well. 
			 * What we can search on this column is the queries 
			 * like "name=steve jackson", "name=jay", "payment=500" only. 
			 * 
			 */
			for(int i=0;i<20 ;i++)
			{
				l_db.insertRecord(table, records);
				System.out.println(i + "-th round succeed!");
			}
				
		 
				 
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
				
		 
		 
			if(l_db!=null)
			{
				l_db.save();
				l_db.closeDB();
				 
			} 

		}
	}
 

}
