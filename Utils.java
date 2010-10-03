public class Utils {
	public static Integer[] initIntArr(int len, int init){
		Integer[] rtn = new Integer[len];
		for(int i = 0; i < len; i++){
			rtn[i] = 0;
		}
		return rtn;
	}
}
