import java.util.ArrayList;

public class Utils {
	public static Integer[] initIntArr(int len, int init){
		Integer[] rtn = new Integer[len];
		for(int i = 0; i < len; i++){
			rtn[i] = 0;
		}
		return rtn;
	}
	
	public static <E> ArrayList<E> initArrLst(int len, E init){
		ArrayList<E> rtn = new ArrayList<E>();
		for(int i = 0; i < len; i++){
			rtn.add(init);
		}
		return rtn;
	}
	
	public static int maxInd(Double[] arr){
		int maxInd = 0;
		Double max = arr[0];
		for(int i = 0; i < arr.length; i++){
			if(arr[i] > max){
				maxInd = i;
				max = arr[i];
			}
		}
		return maxInd;
	}
	
	public static Integer reduce(Integer[] arr){
		Integer sum = 0;
		for(Integer i : arr){
			sum += i;
		}
		return sum;
	}
	
	public static void printTable(String title, Integer[] correct, Integer[] guesses, Integer[] total){
		int totalDocs = reduce(total);
		int maxWidth = 18;
		String lineDivider = buildPattern("+" + buildPattern("-", maxWidth + 2), Trainer.CLASSES.length + 2) + "+\n";
		String s = "+" + buildPattern("*", maxWidth + 2) + "+\n|" + formatString(title, maxWidth) + "|\n" + lineDivider + "|       VALUE        |";
		
		for(String className : Trainer.CLASSES){
			s += formatString(className.toUpperCase(), maxWidth) + "|";
		}
		s += formatString("TOTAL", maxWidth) + "|\n" + lineDivider + "|" + formatString("PRECISION", maxWidth) + "|";
		
		double averagePrecision = 0.0;
		for(int i = 0; i < correct.length; i++){
			double precision = ((double)correct[i]) / guesses[i];
			averagePrecision += precision * total[i];
			s += formatString(Double.toString(precision), maxWidth) + "|";
		}
		s += formatString(Double.toString(averagePrecision / totalDocs), maxWidth) + "|\n" + 
				lineDivider + 
				"|" + formatString("RECALL", maxWidth) + "|";
		
		double averageRecall = 0.0;
		for(int i = 0; i < correct.length; i++){
			double recall = ((double)correct[i]) / total[i];
			averageRecall += recall * total[i];
			s += formatString(Double.toString(recall), maxWidth) + "|";
		}
		s += formatString(Double.toString(averageRecall / totalDocs), maxWidth) + "|\n" +
				lineDivider + 
				"\n";
		
		System.out.println(s);
	}
	
	private static String buildPattern(String pattern, int repeat){
		String rtn = "";
		for(int i = 0; i < repeat; i++){
			rtn += pattern;
		}
		return rtn;
	}
	
	private static String formatString(String s, int width){
		width -= s.length();
		String rightBuffer = (width % 2 != 0) ? "  " : " ";
		String leftBuffer = " ";
		width = width / 2;
		
		for(int i = 0; i < width; i++){
			rightBuffer += " ";
			leftBuffer += " ";
		}
		return rightBuffer + s + leftBuffer;
	}
}
