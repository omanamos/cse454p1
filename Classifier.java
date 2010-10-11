import java.io.*;
import java.util.*;

public class Classifier {
	Trainer known;

	public static void main(String[] args) {
		
		/*
		
		For each (class) in (Set of classes)
		 
		*/
		
		System.out.println("TEST");
	}
	
	private static List<String> getTokens(String file) {
		List<String> tokens = new ArrayList<String>();
		
        Scanner s = new Scanner(file);
        while(s.hasNextLine()) {
        	for(String token : s.nextLine().split(" ")) {
        		tokens.add(token);
        	}
        }
        return tokens;
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
