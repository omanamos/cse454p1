import java.io.*;
import java.util.*;

public class Classifier {
	
	/**
	 * Determines what class a file most likely fits, based on known data
	 * 
	 * @param t	trained data for comparison
	 * @param f	file to check
	 * @return	integer representation of the class the file most likely matches
	 */
	public static int getClass(Trainer t, File f) {
		return Utils.maxInd(scoreClass(t, f));
	}
	
	/**
	 * Takes an input file and gets all the unique tokens
	 * 
	 * @param f	file
	 * @return	list of unique tokens
	 */
	private static List<String> getTokens(File f) {
		List<String> tokens = new ArrayList<String>();
		
		try {
			// Get unique tokens from file
	        Scanner s = new Scanner(f);
	        while(s.hasNextLine()) {
	        	for(String token : s.nextLine().split(" ")) {
	        		//if(!tokens.contains(token))
	        			tokens.add(token);
	        	}
	        }
		} catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}
        return tokens;
	}
	
	/**
	 * Implementation of the getClass method
	 * 
	 * @param t	training data
	 * @param f	file
	 * @return	array of values, indexed by class
	 */
	private static Double[] scoreClass(Trainer t, File f) {
		List<String> tokens = getTokens(f);

		// for each c in C
		
		// score[c] <- log prior[c]
		Double[] score = new Double[t.prior.length];
		for(int i = 0; i < t.prior.length; i++)
			score[i] = Math.log(t.prior[i]);
		
		for(int i = 0; i < t.CLASSES.length; i++) {
			// for each t in W
			for(String token : tokens) {
				if(t.vocab.containsKey(token)) { // make sure we actually saw the token when we were checking the class 
					// score[c] += log condprob[t][c]
					// DEBUGGING - System.out.println(i + " " + token + " " + t.condProb.get(token).get(i));
					score[i] += Math.log(t.condProb.get(token).get(i));
				}
			}
		}
		return score;
	}
}
