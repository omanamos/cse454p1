import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		String training = "classifier_data_small_train";
		String testing = "classifier_data_test";
		
		if(args.length == 2) {
			training = args[0];
			testing = args[1];
		} else {
			System.out.println("No parameters passed.\n\nFolders are assumed to be:\nTraining data: " + training + "\nTesting data: " + testing + "\n");
		}
		try{
			testFolder(training, testing);
			//test(training); for performing 10 fold testing
		}catch(FileNotFoundException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	/**
	 * Used to perform general testing
	 * @param training location of training data
	 * @param testing locations of testing data
	 * @throws FileNotFoundException 
	 */
	public static void testFolder(String training, String testing) throws FileNotFoundException {
		// Make sure passed directories exist and are directories
		File tempFile = new File(training);
		if(!tempFile.exists() || !tempFile.isDirectory()) {
			System.err.println(training + " doesn't exist or isn't a directory!\n");
			System.exit(1);
		}
		tempFile = new File(testing);
		if(!tempFile.exists() || !tempFile.isDirectory()) {
			System.err.println(testing + " doesn't exist or isn't a directory!\n");
			System.exit(1);
		}
		
		ArrayList<File> files = new ArrayList<File>();
		
		File folder = new File(testing);
		File[] fileList = folder.listFiles();
		for(File f : fileList){
			files.add(f);
		}
		
		ArrayList<Integer> trainingClasses = new ArrayList<Integer>();
		ArrayList<File> trainingFiles = new ArrayList<File>();
		
		for(int i = 0; i < Trainer.CLASSES.length; i++) {
			ArrayList<File> temp = loadClass(training, i);
			
			for(File f : temp) {
				trainingFiles.add(f);
				trainingClasses.add(i);
			}
		}
		
		int[] results = execute(trainingFiles, trainingClasses, files);
		try {
			FileOutputStream fs = new FileOutputStream("test.output");
			OutputStreamWriter out = new OutputStreamWriter(fs, "UTF-8");
			for(int i = 0; i < results.length; i++) {
				out.write(files.get(i).getName() + "|" + Trainer.CLASSES[results[i]].toUpperCase() + "\n");
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used for performing 10 fold testing
	 * @param training location of training data
	 * @throws FileNotFoundException
	 */
	public static void test(String training) throws FileNotFoundException{
		ArrayList<ArrayList<File>> files = new ArrayList<ArrayList<File>>();
		
		for(int className = 0; className < Trainer.CLASSES.length; className++){
			files.add(loadClass(training, className));
		}
		
		int incr = 20;
		int maxClassSize = 200;
		
		int count = 1;
		int start = 0;
		int end = incr;
		
		Integer[] totalCorrect = Utils.initIntArr(Trainer.CLASSES.length, 0);
		Integer[] totalGuesses = Utils.initIntArr(Trainer.CLASSES.length, 0);
		Integer[] totalDocs = Utils.initIntArr(Trainer.CLASSES.length, 0);
		
		while(start < maxClassSize){
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
			
			Integer[] curNumCorrect = Utils.initIntArr(Trainer.CLASSES.length, 0);
			Integer[] curNumGuesses = Utils.initIntArr(Trainer.CLASSES.length, 0);
			Integer[] curNumDocs = Utils.initIntArr(Trainer.CLASSES.length, 0);
			
			for(int i = 0; i < results.length; i++){
				if(results[i] == unknownClasses.get(i)){
					totalCorrect[results[i]]++;
					curNumCorrect[results[i]]++;
				}
				curNumGuesses[results[i]]++;
				curNumDocs[unknownClasses.get(i)]++;
				
				totalGuesses[results[i]]++;
				totalDocs[unknownClasses.get(i)]++;
			}
			Utils.printTable("FOLD " + count, curNumCorrect, curNumGuesses, curNumDocs);
			
			count++;
			start = end;
			end += incr;
		}
		
		Utils.printTable("FINAL", totalCorrect, totalGuesses, totalDocs);
	}
	
	private static int[] execute(ArrayList<File> trainingFiles, ArrayList<Integer> trainingClasses, ArrayList<File> unknowns){
		Trainer t = new Trainer(trainingFiles, trainingClasses);
		
		int[] rtn = new int[unknowns.size()]; 
		for(int i = 0; i < rtn.length; i++){
			rtn[i] = Classifier.getClass(t, unknowns.get(i));
		}
		return rtn;
	}
	
	private static ArrayList<File> loadClass(String trainingFolder, Integer className) throws FileNotFoundException{
		ArrayList<File> rtn = new ArrayList<File>();
		File folder = new File(trainingFolder + "/" + Trainer.CLASSES[className] + "/train");
		File[] files = folder.listFiles();
		
		if(files == null)
			throw new FileNotFoundException(trainingFolder + " does not contain valid training data.");
		
		for(File f : files)
			rtn.add(f);
		
		return rtn;
	}
}
