import java.io.*;
import java.util.*;

public class Classifier {
	Trainer known;


	
	public static int getClass(Trainer t, File f) {
		return Utils.maxInd(scoreClass(t, f));
	}
	
	private static List<String> getTokens(File f) {
		List<String> tokens = new ArrayList<String>();
		
		try {
	        Scanner s = new Scanner(f);
	        
	        while(s.hasNextLine()) {
	        	for(String token : s.nextLine().split(" ")) {
	        		tokens.add(token);
	        	}
	        }
		} catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}
        return tokens;
	}
	
	private static Double[] scoreClass(Trainer t, File f) {
		List<String> tokens = getTokens(f);
		
		Double[] score = new Double[t.CLASSES.length];
		for(String token : tokens) {
			for(int i = 0; i < t.CLASSES.length; i++) {
				score[i] += t.vocab.get(token)[i];
			}
		}
		return score;
	}

/*		HashMap<String,Integer[]> rtn = new HashMap<String,Integer[]>();
		Integer[] classCounts = Utils.initIntArr(CLASSES.length, 0);
		int classTotal = 0;
		
		for(int i = 0; i < files.size(); i++){
			try{
				Scanner s = new Scanner(files.get(i));
				Integer c = classes.get(i);
				classCounts[c]++;
				classTotal++;

				while(s.hasNextLine()){
					for(String key : s.nextLine().split(" ")){
						if(!rtn.containsKey(key)){
							rtn.put(key, Utils.initIntArr(CLASSES.length, 0));
						}
						rtn.get(key)[c] += 1;
					}
				}
				s.close();
			}catch(FileNotFoundException fe){
				fe.printStackTrace();
			}
		}
		
		for(int i = 0; i < classCounts.length; i++){
			prior[i] = ((float)classCounts[i]) / classTotal;
		}
		
		return rtn;
	} */
}
