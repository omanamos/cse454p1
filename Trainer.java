import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//
public class Trainer{
	public static final Integer ACTOR = 0;
	public static final Integer BOOK = 1;
	public static final Integer COMPANY = 2;
	public static final Integer STADIUM = 3;
	public static final Integer UNIVERSITY = 4;
	public static final Integer WRITER = 5;
	public static final String[] CLASSES = {"actor", "book", "company", "stadium", "university", "writer"};
	
	public final HashMap<String,Integer[]> vocab;
	public Float[] prior;
	public HashMap<String,ArrayList<Float>> condProb;
	
	public Trainer(ArrayList<File> files, ArrayList<Integer> classes){
		this.prior = new Float[CLASSES.length];
		this.condProb = new HashMap<String,ArrayList<Float>>();
		
		this.vocab = getVocab(files, classes);
		this.train(classes);
	}
	
	public void train(ArrayList<Integer> classes){
		
	}
	
	private static HashMap<String,Integer[]> getVocab(ArrayList<File> files, ArrayList<Integer> classes){
		HashMap<String,Integer[]> rtn = new HashMap<String,Integer[]>();
		for(int i = 0; i < files.size(); i++){
			try{
				Scanner s = new Scanner(files.get(i));
				Integer c = classes.get(i);

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
		return rtn;
	}
}
