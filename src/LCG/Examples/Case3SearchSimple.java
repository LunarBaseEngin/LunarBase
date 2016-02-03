package LCG.Examples;

import java.io.IOException;

import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.TaskInsert;
import LCG.DB.EDF.TaskPrint;
import LCG.DB.EDF.TaskQuery;
import LCG.DB.EDF.Events.IncommingRecords;
import LCG.DB.EDF.Events.QueryAnd;
import LCG.DB.EDF.Events.QueryResult;
import LCG.DB.EDF.Events.QuerySimple;

public class Case3SearchSimple {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order";
		 
		
		/*
		 * construct an QuerySimple with a pair of property-value. 
		 * LunarBase engine execute it, and returns the result.
		 * By default result handler, just print out. 
		 * 
		 * Here print out the latest 200 results that match. 
		 * if the input count is less than 0, engine prints all the 
		 * records that matches.
		 */
		QuerySimple sq = new QuerySimple(table, "age", "25", 200);
		tc.dispatch(sq);
		 
		tc.shutdownDB();

	}

}
