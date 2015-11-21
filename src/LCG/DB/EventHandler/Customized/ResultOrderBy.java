package LCG.DB.EventHandler.Customized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import LCG.DB.EDF.Events.QueryResult;
import LCG.EnginEvent.Event;
import LCG.EnginEvent.EventHandler;
import LCG.EnginEvent.Interfaces.LHandler;
import LCG.RecordTable.StoreUtile.Record32KBytes;
import LCG.RecordTable.StoreUtile.ValueTypes.SupportedTypes;

/*
 * this implementation studies how to sort the result by a specific property.
 * It is work for a relatively small data collection, say, hundreds of thousands.
 * For bigger results set, say tens of millions of records, we need a map-reduce 
 * strategy to quicken sorting. 
 * 
 * We will go through how to map data set to multiple cpu cores in another tutorial.
 */
public class ResultOrderBy implements LHandler<Event, Void> { 
	 
		public final String property;
		public final boolean inverted;
	  
		public ResultOrderBy(String _property, boolean _inverted) {
			this.property = _property;
			this.inverted = _inverted;
	    
		} 
	  
		public Void execute(Event evt) {
			if (evt.getClass() == QueryResult.class) {
				QueryResult recs = (QueryResult) evt;
				internalExecute( recs._results, this.property, this.inverted);
				return null;
			}
			return null;
		} 
	 
	  
	  private void internalExecute( ArrayList<Record32KBytes> __result, String _order_by_property, boolean _inverted) 
	  {
		  if(_order_by_property == null || _order_by_property == "")
		  {
			  System.out.println("LunarBase engin can not order the query results by an empty property.");
			  return;
		  }
		  
		  //Collections.sort(__result, new SortBy<Record32KBytes>(_order_by_property, _inverted));
		  Collections.sort(__result, new Comparator<Record32KBytes>(){
			  @Override
			  public int compare(Record32KBytes rec_1, Record32KBytes rec_2)
			  {
				  //return new Integer(Integer.parseInt(rec_1.valueOf(property))).compareTo(new Integer(Integer.parseInt(rec_2.valueOf(property))));
				  if(Integer.parseInt(rec_1.valueOf(property)) <  Integer.parseInt(rec_2.valueOf(property)) )
					  return -1;
				  if(Integer.parseInt(rec_1.valueOf(property)) ==  Integer.parseInt(rec_2.valueOf(property)) )
					  return 0;
				  else
					  return 1;
			  }
		  });
		  
		  for(Record32KBytes recs:__result)
			  System.out.println(recs.getID() + ": " + recs.recData());
		  
	  } 
	  
	  private class SortBy<T> implements Comparator
	  {
		  String property;
		  boolean inverted;
		  
		  SortBy(String _prop, boolean _inverted)
		  {
			  this.property = _prop;
			  this.inverted = _inverted;
		  }
		  public int compare(Object obj_1, Object obj_2) 
		  {
			  Record32KBytes rec1 = (Record32KBytes) obj_1;
			  Record32KBytes rec2 = (Record32KBytes) obj_2;
			  int bigger = 1;
			  if(inverted)
				  bigger = 0;
			  
			  //if(rec1.compareTo(rec2, this.property, SupportedTypes._integer)) 
			  if(Integer.parseInt(rec1.valueOf(property)) < Integer.parseInt(rec2.valueOf(property)))
			  {
				  return bigger;
			  }
			  
			  return 1-bigger;
			 
				  
		  }
	  }

}
