package LCG.Examples;

import java.io.IOException;
import java.util.ArrayList;

import LCG.DB.EDF.DBTaskCenter; 
import LCG.DB.EDF.Events.QueryLatestN;
import LCG.DB.EDF.Events.QueryResult; 
import LCG.DB.EventHandler.Customized.CustomizedResultHandler;
import LCG.EnginEvent.Interfaces.LFuture;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case2ListLatestN {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);  
		String table = "order";
		 
		CustomizedResultHandler my_handler = new CustomizedResultHandler();
		if(tc.hasHandler(QueryResult.class))
		{
			tc.replaceHandler(QueryResult.class, my_handler);
		}
		
		
		QueryLatestN l_n = new QueryLatestN(table, 1000);  
		LFuture<ArrayList<Record32KBytes>> recs = tc.dispatch(l_n);
		
		tc.dispatch(new QueryResult(recs.get()));
		
		tc.shutdownDB();

	}

}
