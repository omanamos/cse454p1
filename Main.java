import java.io.File;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		test();
	}
	
	public static void test(){
		ArrayList<ArrayList<File>> files = new ArrayList<ArrayList<File>>();
		
		for(int className = 0; className < Trainer.CLASSES.length; className++){
			files.add(loadClass(className));
		}
		
		int incr = 10;
		int maxClassSize = 200;
		
		int start = 0;
		int end = incr;
		
		while(end < maxClassSize){
			ArrayList<File> trainingFiles = new ArrayList<File>();
			ArrayList<Integer> trainingClasses = new ArrayList<Integer>();
			ArrayList<File> unknowns = new ArrayList<File>();
			
			for(int className = 0; className < files.size(); className++){
				ArrayList<File> curFiles = files.get(className);
				for(int i = 0; i < curFiles.size(); i++){
					if(i < start || i >= end){
						trainingFiles.add(curFiles.get(i));
						trainingClasses.add(className);
					}else{
						unknowns.add(curFiles.get(i));
					}
				}
			}
			
			String[] results = execute(trainingFiles, trainingClasses, unknowns);
			System.out.println(buildString(unknowns, results));
		}
	}
	
	private static String buildString(ArrayList<File> files, String[] results){
		String rtn = "";
		for(int i = 0; i < files.size(); i++){
			rtn += files.get(i).getName() + "|" + results[i] + "\n";
		}
		return rtn;
	}
	
	private static String[] execute(ArrayList<File> trainingFiles, ArrayList<Integer> trainingClasses, ArrayList<File> unknowns){
		Trainer t = new Trainer(trainingFiles, trainingClasses);
		
		String[] rtn = new String[unknowns.size()]; 
		for(int i = 0; i < rtn.length; i++){
			rtn[i] = Trainer.CLASSES[Classifier.getClass(t, unknowns.get(i))];
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
