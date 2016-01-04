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

public class Case4SearchAnd {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		 
		
		QuerySimple sq1 = new QuerySimple("age", "25", 0);
		QuerySimple sq2 = new QuerySimple("payment", "600", 0);
		QueryAnd qa = new QueryAnd(sq1, sq2, 200);
		tc.dispatch(qa);
		
		tc.shutdownDB();

	}

}
