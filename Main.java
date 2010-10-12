import java.io.File;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		//test();
		
		test2("classifier_data_test");
	}
	
	public static void testFolder(String folderName) {
		ArrayList<File> files = new ArrayList<File>();
		
		File folder = new File(folderName);
		File[] fileList = folder.listFiles();
		for(File f : fileList){
			files.add(f);
		}
		
		ArrayList<Integer> trainingClasses = new ArrayList<Integer>();
		ArrayList<File> trainingFiles = new ArrayList<File>();
		
		for(int i = 0; i < Trainer.CLASSES.length; i++) {
			ArrayList<File> temp = loadClass(i);
			
			for(File f : temp) {
				trainingFiles.add(f);
				trainingClasses.add(i);
			}
			
			
		}
	}
	
	public static void test(){
		ArrayList<ArrayList<File>> files = new ArrayList<ArrayList<File>>();
		
		for(int className = 0; className < Trainer.CLASSES.length; className++){
			files.add(loadClass(className));
		}
		
		int incr = 20;
		int maxClassSize = 200;
		
		int count = 0;
		int start = 0;
		int end = incr;
		
		Integer[] numCorrect = Utils.initIntArr(Trainer.CLASSES.length, 0);
		Integer[] numGuesses = Utils.initIntArr(Trainer.CLASSES.length, 0);
		Integer[] numDocs = Utils.initIntArr(Trainer.CLASSES.length, 0);
		
		while(end < maxClassSize){
			ArrayList<File> trainingFiles = new ArrayList<File>();
			ArrayList<Integer> trainingClasses = new ArrayList<Integer>();
			ArrayList<File> unknowns = new ArrayList<File>();
			ArrayList<Integer> unknownClasses = new ArrayList<Integer>();
			
			for(int className = 0; className < files.size(); className++){
				ArrayList<File> curFiles = files.get(className);
				for(int i = 0; i < curFiles.size(); i++){
					if(i < start || i >= end){
						trainingFiles.add(curFiles.get(i));
						trainingClasses.add(className);
					}else{
						unknowns.add(curFiles.get(i));
						unknownClasses.add(className);
					}
				}
			}
			
			int[] results = execute(trainingFiles, trainingClasses, unknowns);
			for(int i = 0; i < results.length; i++){
				if(results[i] == unknownClasses.get(i))
					numCorrect[results[i]]++;
				numGuesses[results[i]]++;
				numDocs[unknownClasses.get(i)]++;
			}
			
			start = end;
			end += incr;
		}
		
		for(int i = 0; i < Trainer.CLASSES.length; i++){
			double precision = ((double)numCorrect[i]) / numGuesses[i];
			System.out.println("Precision(" + Trainer.CLASSES[i] + "): " + precision);
			double recall = ((double)numCorrect[i]) / numDocs[i];
			System.out.println("Recall(" + Trainer.CLASSES[i] + "): " + recall);
		}
	}
	
	private static String buildString(ArrayList<File> files, String[] results){
		String rtn = "";
		for(int i = 0; i < files.size(); i++){
			rtn += files.get(i).getName() + "|" + results[i] + "\n";
		}
		return rtn;
	}
	
	private static int[] execute(ArrayList<File> trainingFiles, ArrayList<Integer> trainingClasses, ArrayList<File> unknowns){
		Trainer t = new Trainer(trainingFiles, trainingClasses);
		
		int[] rtn = new int[unknowns.size()]; 
		for(int i = 0; i < rtn.length; i++){
			rtn[i] = Classifier.getClass(t, unknowns.get(i));
		}
		return rtn;
	}
	
	private static ArrayList<File> loadClass(Integer className){
		ArrayList<File> rtn = new ArrayList<File>();
		File folder = new File("classifier_data_small_train/" + Trainer.CLASSES[className] + "/train");
		File[] files = folder.listFiles();
		for(File f : files){
			rtn.add(f);
		}
		return rtn;
	}
}
