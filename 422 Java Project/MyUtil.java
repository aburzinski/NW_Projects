package AliciaEdits;

public class MyUtil {

	public static boolean tryParseInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
