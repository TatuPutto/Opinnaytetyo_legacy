package tatuputto.opinnaytetyo.json;

public class Main {
	public static void main(String[] args) {
		EncodeJSON encode = new EncodeJSON();
		
		String[] filenames = new String[1];
		filenames[0] = "testfile.java";
		
		String description = "ääkköset";
		String[] sources = new String[1];
		sources[0] = "äääasdffas";
		
		encode.encodeJSONRequestPOST(description, false, filenames, sources);
	
	}
	
}