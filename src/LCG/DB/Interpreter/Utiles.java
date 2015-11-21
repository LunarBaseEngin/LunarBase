package LCG.DB.Interpreter;

public class Utiles {
	
	 public static int[] intersectOrderedSets(int[] a, int[] b, int top_count) 
	  {
			if (a[0] > b[b.length - 1] || b[0] > a[a.length - 1]) 
				return new int[0];
		
			int[] intersection = new int[Math.max(a.length, b.length)];
			int max_id_a = a.length-1;
			int max_id_b = b.length-1;
			int intersection_max_id = intersection.length-1;
			
			int offset = 0;
			for (int i = 0, b_index = i; i < a.length && b_index < b.length && offset<top_count; i++) 
			{
				while (a[max_id_a-i] < b[max_id_b-b_index])  
					b_index++;
		 
				if (a[max_id_a-i] == b[max_id_b-b_index]) 
				{ 
					intersection[intersection_max_id - offset] = b[max_id_b - b_index];
					offset++;
					b_index++;
				}
				while (i < (a.length - 1) && a[max_id_a-i] == a[max_id_a - i - 1])  
					i++;
		 
			}
			if (intersection.length == offset) 
			{
				if(intersection.length <= top_count)
					return intersection;
				else
				{
					int[] truncated = new int[top_count];
					System.arraycopy(intersection, intersection.length-top_count, truncated, 0, top_count);
					return truncated;
				}
			}
			int len = Math.min(offset, top_count);
			int[] truncated = new int[len];
			System.arraycopy(intersection, intersection.length-len, truncated, 0, len);
			return truncated;
		}

}
