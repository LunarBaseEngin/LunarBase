package LCG.DB.Interpreter;

import java.util.Vector;

import LCG.DB.Interpreter.Operators.Ops;

public class OpsStack {
	
	Vector<int[]> cadidates = new Vector<int[]>();
	 
	Vector<Ops> operators = new Vector<Ops>();
	 
	
	public void addOps(Ops _op, int[] ids)
	{
		cadidates.add(ids);
		operators.add(_op);
	}
	
	public int[] execute()
	{
		int indecator_first = cadidates.size()-2;
		int indecator_second = cadidates.size()-1;
		int[] cadidate_2 = cadidates.elementAt(indecator_second);
		while(indecator_first>=0)
		{
			int[] cadidate_1 = cadidates.elementAt(indecator_first);
			
			Ops ops = operators.elementAt(indecator_second);
			switch(ops)
			{
			case AND:
				cadidate_2 = Utiles.intersectOrderedSets(cadidate_1, cadidate_2, Math.max(cadidate_1.length, cadidate_2.length));
			}
			
			indecator_first--;
			indecator_second--;
		}
		
		return cadidate_2;
		
	}

}
