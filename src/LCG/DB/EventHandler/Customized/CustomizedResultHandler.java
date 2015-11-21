package LCG.DB.EventHandler.Customized;

import java.util.ArrayList;

import LCG.DB.EDF.Events.QueryResult;
import LCG.EnginEvent.Event;
import LCG.EnginEvent.EventHandler;
import LCG.EnginEvent.Interfaces.LHandler;
import LCG.RecordTable.StoreUtile.Record32KBytes;

public class CustomizedResultHandler implements LHandler<Event, Void> { 
	 
	  public CustomizedResultHandler( ) {
	    
	  } 
	  
	  public Void execute(Event evt) {
	    if (evt.getClass() == QueryResult.class) {
	    	QueryResult recs = (QueryResult) evt;
	    	internalExecute( recs._results);
	    	return null;
	    }
	    return null;
	  } 
	 
	  /*
	   * this is where we handle our records. If the result size is big,
	   * you need to send it to any map-reduce framework, maybe located 
	   * on another remote machine.  
	   * 
	   */
	  private void internalExecute( ArrayList<Record32KBytes> __result) 
	  {
		  for(int i=0;i<__result.size();i++)
		  {
			  System.out.println(__result.get(i).getID() + ": " + __result.get(i).recData());
		  }
	  } 

}
