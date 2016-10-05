package LCG.Examples;

import java.io.IOException;
import java.util.ArrayList;

import LCG.DB.API.Result.RGQueryResult;
import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.Events.QueryAnd;
import LCG.DB.EDF.Events.QueryRange;
import LCG.DB.EDF.Events.QueryRecs;
import LCG.DB.EDF.Events.QueryResult;
import LCG.DB.EDF.Events.QuerySimple;
import LCG.DB.EventHandler.Customized.CustomizedResultHandler;
import LCG.DB.EventHandler.Customized.ResultOrderBy;
import LCG.EnginEvent.Interfaces.LFuture;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case7SearchRange {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);   
		String table = "order"; 
		/*
		 * Step1: then construct a new range query 
		 */ 
		QueryRange sq = new QueryRange(table, "payment", 600, 700);
		LFuture<RGQueryResult> results = tc.dispatch(sq);
		
		/*
		 * Step2: we have a result ids, which is 2 dimension:
		 * result_ids1[0]: all the ages between 25 to 38
		 * result_ids1[1]: all the records that match
		 */
		if(results.get().resultCount() >= 0)
		{
			ArrayList<Record32KBytes> recs = results.get().fetchRecords();	
			tc.dispatch(new QueryResult(recs));
		}
		 
	
		
		tc.shutdownDB();
		
		
	}

}
