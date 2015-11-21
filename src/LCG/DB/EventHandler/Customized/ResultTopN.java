package LCG.DB.EventHandler.Customized;

import java.util.ArrayList;

import LCG.DB.EDF.Events.QueryResult;
import LCG.EnginEvent.Event;
import LCG.EnginEvent.EventHandler;
import LCG.EnginEvent.Interfaces.LHandler;
import LCG.RecordTable.StoreUtile.Record32KBytes;
import LCG.RecordTable.StoreUtile.ValueTypes.SupportedTypes;
/*
 * Top N is a popular demand on lots of web sites. 
 * Suppose we have an e-commerce site, and on some hot 
 * area, we want top 100 best selling products listed on the right page side.
 * Then we need a handler to finish this job.
 * 
 * Although LunarBase has its internal big cache that keeps hot records 
 * in direct memory(off jvm heap), it is shared by the whole database. 
 * For our special purpose, say top 1000 products among all the 10 million stored 
 * in db, we need a private memory space to cache this.
 * 
 * This example illustrates how to use LunarMMU for managing native memory.
 * LunarMMU is designed for server applications which are running for very long 
 * time, and malloc/free operations are in high frequency. 
 * The default solution generates memory fragments in a long term running. 
 * LunarMMU will not. It is for fast allocate and zero fragment. 
 * The price is a little space waste. Memory consumption is a little bigger than 
 * default one. 
 * 
 * Given that the per GB memory is going to be cheaper, 
 * we think it's worthy for the trade off. 
 * 
 * Check out the benchmark on lunarbase wiki.
 * 
 * User must: 
 * 1. release the memory manually;
 * 2. LunarMMU instance must be terminated when you finish using it. 
 * It is out of the control of JVM, GC will not do this for you definitely .  
 *  
 */
public class ResultTopN implements LHandler<Event, Void> { 

	public final String property;
	public final boolean inverted;
  
	public ResultTopN(String _property, boolean _inverted) {
		this.property = _property;
		this.inverted = _inverted;
	    
	} 
	  
	public Void execute(Event evt) {
		if (evt.getClass() == QueryResult.class) {
			QueryResult recs = (QueryResult) evt;
			return internalExecute( recs._results, this.property, this.inverted );
	    }
		return null;
	  } 
	 
	  
	  private Void internalExecute( ArrayList<Record32KBytes> __result, String _order_by_property, boolean _inverted) 
	  {
		  return null;
	  } 

}
