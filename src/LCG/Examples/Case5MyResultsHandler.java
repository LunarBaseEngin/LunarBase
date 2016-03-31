package LCG.Examples;

import java.io.IOException;

import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.Events.QueryAnd;
import LCG.DB.EDF.Events.QueryResult;
import LCG.DB.EDF.Events.QuerySimple;
import LCG.DB.EventHandler.Customized.CustomizedResultHandler;

public class Case5MyResultsHandler {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order"; 
		
		/*
		 * Step1: create an instance of result handler, 
		 * in which we process the query results. 
		 * Tasks like ranking, calculation, or any thing you want to deal with.
		 */
		CustomizedResultHandler my_handler = new CustomizedResultHandler();
		
		/*
		 * Step2: The DBTaskCenter has default handler, which just 
		 * simply print all the query results from the latest to the eldest. 
		 */
		if(tc.hasHandler(QueryResult.class))
		{
			tc.replaceHandler(QueryResult.class, my_handler);
		}
		else
			tc.registerHandler(QueryResult.class, my_handler);
		
		/*
		 * Step3: then construct a new query, see if my new handler works :-)
		 */
		
		QuerySimple sq1 = new QuerySimple(table, "age", "36", 0);
		QuerySimple sq2 = new QuerySimple(table, "payment", "600", 0);
		QueryAnd qa = new QueryAnd(sq1, sq2, 200);
		tc.dispatch(qa);
		
		
		tc.shutdownDB();
		
		
	}

}
