import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Trainer{
	public static final Integer ACTOR = 0;
	public static final Integer BOOK = 1;
	public static final Integer COMPANY = 2;
	public static final Integer STADIUM = 3;
	public static final Integer UNIVERSITY = 4;
	public static final Integer WRITER = 5;
	public static final String[] CLASSES = {"actor", "book", "company", "stadium", "university", "writer"};
	
	/**
	 * key = vocab token
	 * value = array of counts of that word for each class(index = class as defined in {@link Trainer}
	 */
	public final HashMap<String,Integer[]> vocab;
	private final ArrayList<Integer> denoms;
	
	public Double[] prior;
	/**
	 * key = vocab token
	 * value = array of conditional probabilities of that word for each class(index = class as defined in {@link Trainer}
	 */
	public HashMap<String,ArrayList<Double>> condProb;
	
	public Trainer(ArrayList<File> files, ArrayList<Integer> classes){
		this.prior = new Double[CLASSES.length];
		this.condProb = new HashMap<String,ArrayList<Double>>();
	
		this.vocab = getVocab(files, classes);
		this.denoms = getDenoms(this.vocab);
		this.train();
	}
	
	/**
	 * Loads conditional probabilities for each token
	 */
	private void train(){
		for(String term : vocab.keySet()){
		
			if(!this.condProb.containsKey(term)){
				this.condProb.put(term, new ArrayList<Double>());
			}
			
			Integer[] counts = vocab.get(term);
			for(int i = 0; i < counts.length; i++){
				this.condProb.get(term).add(((double)(counts[i] + 1)) / this.denoms.get(i));
			}
		}
	}
	
	/**
	 * @param vocab vocab built by Trainer.getVocab
	 * @return ArrayList of counts. Each count is the total number of vocab terms plus the summation of all counts of 
	 * tokens in that class. Each index is a class as defined in {@link Trainer}
	 */
	private static ArrayList<Integer> getDenoms(HashMap<String,Integer[]> vocab){
		ArrayList<Integer> rtn = Utils.initArrLst(CLASSES.length, vocab.size());

		for(String term : vocab.keySet()){
			Integer[] counts = vocab.get(term);
			for(int i = 0; i < counts.length; i++){
				rtn.set(i, rtn.get(i) + counts[i]);
			}
		}
		return rtn;
	}
	
	/**
	 * @param files files to read in
	 * @param classes class corresponding to each file in the files ArrayList
	 * @return Hash of all tokens in the given files. values for each key are Integer arrays that 
	 * represent counts for each class (class = index) for each token.
	 */
	private HashMap<String,Integer[]> getVocab(ArrayList<File> files, ArrayList<Integer> classes){
		HashMap<String,Integer[]> rtn = new HashMap<String,Integer[]>();
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
			prior[i] = ((double)classCounts[i]) / classTotal;
		}
		
		return rtn;
	}
}
