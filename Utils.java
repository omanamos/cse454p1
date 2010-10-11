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
}
