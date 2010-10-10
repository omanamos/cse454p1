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
		Trainer t = new Trainer(files, classes);
		return;
	}
	
	public static void loadClass(Integer className, ArrayList<File> fileLst, ArrayList<Integer> classes){
		File folder = new File("classifier_data_small_train/" + Trainer.CLASSES[className] + "/train");
		File[] files = folder.listFiles();
		for(File f : files){
			fileLst.add(f);
			classes.add(className);
		}
	}
}
