package LCG.Examples.FullText;

 
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import Lunarion.SE.AtomicStructure.TermScore;
import Lunarion.SE.FullText.Lexer.TokenizerInterface;
 
/*
 * this class requirs a language parser ansj_seg at github:
 * https://github.com/NLPChina/ansj_seg
 * 
 * For a customized tokenizer, which extends the interface TokenizerInterface,
 * shall implement the interfac:
 * HashMap<String, TermScore> tokenizeTerm(String input_str)
 * 
 * returns the hash map with each team and its score.
 */
 


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import Lunarion.SE.AtomicStructure.TermScore;
import Lunarion.SE.FullText.Lexer.TokenizerInterface;
 
public class TokenizerForSearchEngine extends TokenizerInterface{

 
    private List<Term> tokens;
    private ListIterator<Term> token_iterator;

    public TokenizerForSearchEngine(){
        tokens = new LinkedList<Term>();
        token_iterator = tokens.listIterator();
    }

    
  
 
    public int size(){
        return tokens.size();
    }

    
    public boolean hasNext(){
        return token_iterator.hasNext();
    }

   
    public String nextToken(){

        return token_iterator.next().getName();
    }

    
    public void add(String token){

        if (token == null){
            return;
        }
        Term new_term = new Term(token, 0, token, 1);
        tokens.add(new_term);
    }

    
    public String toString(String delim){
        StringBuilder sb = new StringBuilder();
        if (tokens.size() < 1){
            return sb.toString();
        }
        ListIterator<Term> tempTokenIter = tokens.listIterator();
        sb.append(tempTokenIter.next().getName());
        while (tempTokenIter.hasNext()){
            sb.append(delim).append(tempTokenIter.next().getName());
        }

        return sb.toString();
    }

    public void tokenize(String input_str)
    { 
    	tokens = ToAnalysis.parse(input_str);
        token_iterator = tokens.listIterator();
    }

	@Override
	public HashMap<String, TermScore> tokenizeTerm(String input_str) {
		
		//long startt = System.nanoTime(); 
		tokens = ToAnalysis.parse(input_str);
		token_iterator = tokens.listIterator();
		
		HashMap<String, TermScore> hash = new HashMap<String, TermScore>();
		while(token_iterator.hasNext())
		{
			Term term = token_iterator.next();
			if(term.getName().length()>=2)
			{
				if(hash.get(term.getName()) == null)
					hash.put(term.getName(), new TermScore(term.getName(), 0));
				else
				{
					TermScore exist_term = hash.get(term.getName());
					int new_score = exist_term.getScore()+1;
					exist_term.setScore(new_score);
					hash.put(term.getName(), exist_term);
				}
			}
		}
		//long endd = System.nanoTime(); 
		//System.out.println("Tokenization costs: " + (endd - startt ) + " ns"); 
		
		return hash;
	}

	public static void main(String[] args) throws InterruptedException {
		
		TokenizerForSearchEngine tfs = new TokenizerForSearchEngine();
		String[] records = new String[4];		
		records[0] = "{name=jackson6, score=50, comment=[\"一年一年可以看到更富更强些。而这个富，是共同的富，这个强，是共同的强，大家都有份,,.  \"]}"; 
		HashMap<String, TermScore> ttt = null;
		for(int i=0;i<1000;i++)
			ttt = tfs.tokenizeTerm(records[0]);
		
		Iterator<String> keys = ttt.keySet().iterator();
		while(keys.hasNext())
		{
			System.out.println(keys.next());
		}
	}
	
}
