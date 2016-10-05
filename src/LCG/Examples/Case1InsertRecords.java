package LCG.Examples;

import java.io.IOException;

import LCG.DB.API.LunarTable;
import LCG.DB.EDF.DBTaskCenter; 
import LCG.DB.EDF.Events.IncommingRecords; 
import LCG.DB.EDF.Events.QuerySimple;
import LCG.EnginEvent.Interfaces.LFuture;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case1InsertRecords {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order";
		
		if(!tc.getActiveDB().hasTable(table))
		{
			tc.getActiveDB().createTable(table); 
			tc.getActiveDB().openTable(table); 
			
		}
		
		LunarTable l_table = tc.getActiveDB().getTable(table);
		 
		l_table.addSearchable("long", "payment");
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
		LFuture<Record32KBytes[]> interted = tc.dispatch(new IncommingRecords(table, records));
		tc.saveDB();
		
		/*
		 * check if all the records inserted correctly. 
		 * If one fails, its rec_id is -1;
		 */
		Record32KBytes[] all_recs = interted.get();
		for(int i=0; i < all_recs.length; i++)
		{
			if(all_recs[i].getID() == -1)
				System.out.println(i + "-th records in the input array fails inserting");
		}
		/*
		 * Step 3: Test query, see if they are correctly inserted, 
		 * and if property-value pair can be retrieved. 
		 */
		QuerySimple sq = new QuerySimple(table, "age", "36", 200);
		tc.dispatch(sq);
		
		/*
		 * Step 4: Must not forget to shut down the db.
		 */
		tc.shutdownDB();

	}

}
