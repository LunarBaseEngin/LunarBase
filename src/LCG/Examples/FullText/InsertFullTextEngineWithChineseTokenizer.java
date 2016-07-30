package LCG.Examples.FullText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable; 
import LCG.RecordTable.StoreUtile.Record32KBytes; 

public class InsertFullTextEngineWithChineseTokenizer {

	public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
		
	String db_root = "/home/feiben/DBTest/RTSeventhDB";
	
	LunarDB l_db = LunarDB.getInstance();
	String table = "profile";
	String column = "comment";
	String[] records = new String[4];
	records[0] = "{name=jackson6, score=50, comment=[\"一年一年可以看到更富更强些。而这个富，是共同的富，这个强，是共同的强，大家都有份,,.  \"]}"; 
	records[1] = "{name=John6, score=95, comment=[\"John6 purchases 250 tvs, it is confirmed \"]}"; 
	records[2] = "{name=Rafael6, score=75, comment=[\"Rafael6 has a complain submitted, and this is the 10-th time. Shall be answered.  \"]}"; 
	records[3] = "{name=tobedeleted, score=95, comment=[\"tobedeleted, this record is not real, shall be deleted.  \"]}"; 
 
	 
	l_db.openDB(db_root);
			
	if(!l_db.hasTable(table))
	{	
		l_db.createTable(table); 
		l_db.openTable(table);
		LunarTable tt = l_db.getTable(table);
				
		/*
		 * add the full-text searchable column, 
		 * notifying lunarbase to index the content of this column.
		 */
		tt.addFulltextSearchable(column);
	} 
			
	LunarTable t_table = l_db.getTable(table);
	TokenizerForSearchEngine t_e = new TokenizerForSearchEngine(); 
	t_table.registerTokenizer(t_e); 
		 
	for(int i=0;i<5000 ;i++)
	{
		long start_time = System.nanoTime(); 
				
		l_db.insertRecord(table, records);
				
		long end_time = System.nanoTime();
		long duration = end_time - start_time;
		System.out.println(i + "-th round succeed!");
		System.out.print("insert "+ records.length +" records takes time(ns):");
		System.out.println (duration)  ; 
	}
	 
	if(l_db!=null)
	{ 
		l_db.save();
		l_db.closeDB();
			 
	} 

	} 
}
