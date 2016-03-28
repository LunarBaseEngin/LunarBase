package LCG.Examples;

import java.io.IOException;

import LCG.DB.API.LunarTable;
import LCG.DB.EDF.DBTaskCenter; 
import LCG.DB.EDF.Events.IncommingRecords; 
import LCG.DB.EDF.Events.QuerySimple;

public class Case1InsertRecords {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order";
		
		if(!tc.getActiveDB().hasTable(table))
		{
			tc.getActiveDB().createTable(table); 
			tc.getActiveDB().openTable(table);
			LunarTable l_table = tc.getActiveDB().getTable(table);
			l_table.addSearchable("string", "name");
			l_table.addSearchable("int", "payment");
			l_table.addSearchable("int", "age");
			
			
		}
		
		
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
		tc.dispatch(new IncommingRecords(table, records));
		tc.saveDB();
		
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
