package LCG.Examples;

import java.io.IOException;

import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.Events.QueryAnd;
import LCG.DB.EDF.Events.QueryResult;
import LCG.DB.EDF.Events.QuerySimple;
import LCG.DB.EventHandler.Customized.CustomizedResultHandler;
import LCG.DB.EventHandler.Customized.ResultOrderBy;

public class Case6SortBy {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order"; 
		/*
		 * Step1: create an instance of result handler, 
		 * in which we process the query results. 
		 * Tasks like ranking, calculation, or any thing you want to deal with.
		 */
		ResultOrderBy my_handler = new ResultOrderBy("payment", false);
		 
		/*
		 * Step2: The DBTaskCenter has default handler, which just 
		 * simply print all the query results from the latest to the eldest. 
		 */
		if(tc.hasHandler(QueryResult.class))
		{
			tc.replaceHandler(QueryResult.class, my_handler);
		} 
		
		/*
		 * Step3: then construct a new query, see if my new handler works :-)
		 */
		 
		 
		QuerySimple sq = new QuerySimple( table , "age", "36", 1000);
		tc.dispatch(sq);
		
		
		tc.shutdownDB();
		
		
	}

}
