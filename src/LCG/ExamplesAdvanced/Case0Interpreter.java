package LCG.ExamplesAdvanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.Events.QueryRecs;
import LCG.DB.EDF.Events.QueryResult; 
import LCG.DB.EDF.Events.QuerySimpleIDs;
import LCG.EnginEvent.Interfaces.LFuture;
import LCG.RecordTable.StoreUtile.Record32KBytes;
import LCG.DB.EventHandler.Customized.ResultOrderBy;
import LCG.DB.Interpreter.OpsStack;
import LCG.DB.Interpreter.Utiles;
import LCG.DB.Interpreter.Operators.Ops;

/*
 * Query may be complicated. You need to know a bunch of 
 * records satisfying "age=25 And (payment=600 Or payment=700) And name=Rafael8", 
 * and further you need only the latest 100 results.
 * 
 * It is the interpreter pattern we should implement. 
 * This example demos a simple solution for this purpose.
 */
public class Case0Interpreter {
	
	public static void main(String[] args) throws IOException {
	
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
	
		
		
		/*
		 * Step1: then construct a batch of queries, 
		 * each of them returns an array of records ids for quick processing, 
		 * as we know that fetch the whole long records are time consuming.
		 * 
		 * It is executed multi-threaded.  
		 */
		 
		 
		/*
		 * the query is a combination of multiple conditions:
		 * select where age=36 AND payment=600 AND name=Rafael8
		 */
		QuerySimpleIDs sq1 = new QuerySimpleIDs("age", "36", 0);
		QuerySimpleIDs sq2 = new QuerySimpleIDs("payment", "600", 0);
		QuerySimpleIDs sq3 = new QuerySimpleIDs("name", "Rafael8", 0);
		
		LFuture<int[]> ids1 = tc.dispatch(sq1);
		LFuture<int[]> ids2 = tc.dispatch(sq2);
		LFuture<int[]> ids3 = tc.dispatch(sq3); 
		 
		
		OpsStack os = new OpsStack();
		os.addOps(Ops.NOTHING, ids1.get());/* add an nothing-operator just for keeping 
											* the lengths of ops and candidates the same.
											*/
		
		os.addOps(Ops.AND, ids2.get());
		os.addOps(Ops.AND, ids3.get());
		
		int[] result_ids = os.execute();
		LFuture<ArrayList<Record32KBytes>> results = tc.dispatch(new QueryRecs(result_ids));
		
		
		/*
		 * Step2: create an instance of result handler, 
		 * in which we process the query results. 
		 * Tasks like ranking, calculation, or any thing you want to deal with.
		 */
		ResultOrderBy my_handler = new ResultOrderBy("payment", false);
		 
		/*
		 * Step3: The DBTaskCenter has default handler, which just 
		 * simply print all the query results from the latest to the eldest. 
		 */
		if(tc.hasHandler(QueryResult.class))
		{
			tc.replaceHandler(QueryResult.class, my_handler);
		} 
		else
		{
			tc.registerHandler(QueryResult.class, my_handler);
		}
		
		tc.dispatch(new QueryResult(results.get()));
		
		tc.shutdownDB();
	}

}
