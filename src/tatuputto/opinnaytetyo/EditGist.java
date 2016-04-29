package tatuputto.opinnaytetyo;

import java.util.ArrayList;
import java.util.Scanner;

public class EditGist {
	AuthorizedConnection connection = new AuthorizedConnection();
	EncodeJSON encodejson = new EncodeJSON();
	
	public void patchGist(int gistId) {
		
		
		ArrayList<String> filenames = new ArrayList<String>();
		ArrayList<String> fileSources = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("Gist description");
		String desc = input.nextLine();
		
		/*for(int i = 0; i < 2; i++) {
			
			input.nextLine();
			System.out.println("Filename");
			filenames.add(input.nextLine());
			
			System.out.println("Code");
			fileSources.add(input.nextLine());
			
			
		}*/
		
		filenames.add("UpdatedTestFile3.java");
		filenames.add("Testfile4.java");
		
		fileSources.add("public class Testfile3 {}");
		fileSources.add("public class Testfile4 {}");

		
		input.close();
		
		String url = "https://api.github.com/gists/" + gistId;
		String data = encodejson.encodeJSONRequest(desc, filenames, fileSources).toString();
		String accessToken = "ab3c91435e4fadb492e9c570ee58959a514b6c86";
		
		connection.formConnection("PATCH", url, data, accessToken);
		
	}
	
	
}
