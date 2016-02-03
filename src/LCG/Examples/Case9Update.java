package LCG.Examples;

import java.io.IOException;
import java.util.ArrayList;

import LCG.DB.EDF.DBTaskCenter;
import LCG.DB.EDF.Events.Delete;
import LCG.DB.EDF.Events.QueryAnd;
import LCG.DB.EDF.Events.QueryLatestN;
import LCG.DB.EDF.Events.QueryRange;
import LCG.DB.EDF.Events.QueryRecs;
import LCG.DB.EDF.Events.QueryResult;
import LCG.DB.EDF.Events.QuerySimple;
import LCG.DB.EDF.Events.Update;
import LCG.DB.EventHandler.Customized.CustomizedResultHandler;
import LCG.DB.EventHandler.Customized.ResultOrderBy;
import LCG.EnginEvent.Interfaces.LFuture;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class Case9Update {

	public static void main(String[] args) throws IOException {
		String db_root = "/home/feiben/DBTest/RTSeventhDB";
		DBTaskCenter tc = new DBTaskCenter(db_root);   
		String table = "order"; 
		
		/*
		 * Step 1: List some latest 10 records that need to be updated.
		 */
		
		QueryLatestN l_n = new QueryLatestN(table, 10);  
		LFuture<ArrayList<Record32KBytes>> recs = tc.dispatch(l_n);
		for(int i=0;i<recs.get().size();i++)
			System.out.println(recs.get().get(i).getID() + ":" + recs.get().get(i).recData());
		/*
		 * Step 2: then construct a new Update task.
		 * Here we update the latest record in the database.
		 */ 
		int rec_tobe_updated = recs.get().get(recs.get().size()-1).getID();
		String[] prop = {"payment", "age"};
		String[] val = {"1000000", "101"}; 
		
		Update update_task = new Update(table, rec_tobe_updated, prop, val);
		LFuture<Boolean> succeed = tc.dispatch(update_task);
		
		/*
		 * Step 3: check if succeed.
		 */
		if(succeed.get())
		{
			QueryLatestN after_delete = new QueryLatestN(table, 10);  
			LFuture<ArrayList<Record32KBytes>> recs_after_delete = tc.dispatch(after_delete);
			for(int i=0;i<recs_after_delete.get().size();i++)
				System.out.println(recs_after_delete.get().get(i).getID() + ":" + recs_after_delete.get().get(i).recData());
			
		} 
		
		tc.saveDB();
		
		tc.shutdownDB();
		
		
	}

}
