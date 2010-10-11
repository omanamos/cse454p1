import java.io.File;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		test();
	}
	
	public static void test(){
		ArrayList<File> files = new ArrayList<File>();
		ArrayList<Integer> classes = new ArrayList<Integer>();
		
		for(int className = 0; className < Trainer.CLASSES.length; className++){
			loadClass(className, files, classes);
		}
		
		int incr = 10;
		int start = 0;
		int end = incr;
		
		while(start < files.size() && end < files.size()){
			ArrayList<File> trainingFiles = new ArrayList<File>();
			ArrayList<Integer> trainingClasses = new ArrayList<Integer>();
			ArrayList<File> unknowns = new ArrayList<File>();
			
			String[] results = execute(trainingFiles, trainingClasses, unknowns);
			
			System.out.println(buildString(unknowns, results));
			System.out.println();
			
			start = end;
			end += incr;
		}
		
		return;
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
			rtn[i] = Trainer.CLASSES[Classifier.classify(t, unknowns.get(i))];
		}
		return rtn;
	}
	
	private static void loadClass(Integer className, ArrayList<File> fileLst, ArrayList<Integer> classes){
		File folder = new File("classifier_data_small_train/" + Trainer.CLASSES[className] + "/train");
		File[] files = folder.listFiles();
		for(File f : files){
			fileLst.add(f);
			classes.add(className);
		}
	}
}
