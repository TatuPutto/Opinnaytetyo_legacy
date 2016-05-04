package tatuputto.opinnaytetyo.gists;

import java.util.ArrayList;
import java.util.Scanner;

public class EditGist {
	GetSingleGist getGist = new GetSingleGist();
	AuthorizedConnection connection = new AuthorizedConnection();
	EncodeJSON encodejson = new EncodeJSON();
	
	public void patchGist(String gistId, String accessToken) {
		
		Gist gistToEdit = getGist.getGist(accessToken, gistId);
		
		ArrayList<String> filesToUpdate = new ArrayList<String>();
		ArrayList<String> updatedFilenames = new ArrayList<String>();
		ArrayList<String> updatedSources = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("Gist description");
		//String desc = input.nextLine();
		String desc = "First edit";
		
		
	/*
		int files = gistToEdit.getFiles().size();
		for(int i = 0; i < files; i++) {
			
			input.nextLine();
			System.out.println("Filename");
			filenames.add(input.nextLine());
			
			System.out.println("Code");
			fileSources.add(input.nextLine());
			
			
		}
		*/
		
		filesToUpdate.add("Testfile1.java");
		
		updatedFilenames.add("Testfile1Updated.java");
		updatedSources.add("{private int sum;}");
		/*
		filenames.add("UpdatedTestFile1.java");
		filenames.add("Testfile2.java");
		filenames.add("FileAddedThroughEdit.java");
		
		fileSources.add("public class Testfile1 {private int sum;}");
		fileSources.add("public class Testfile2 {System.out.print(\"Hello!\")}");
		fileSources.add("public class FileAddedThroughEdit {}");
		*/
		
		
		
		
		input.close();
		//encodejson.encodeJSONRequestPATCH(desc, filesToUpdate, updatedFilenames, updatedSources);
		String url = "https://api.github.com/gists/" + gistId;
		String data = encodejson.encodeJSONRequestPATCH(desc, filesToUpdate, updatedFilenames, updatedSources).toString();
		
		connection.formConnection("PATCH", url, data, accessToken);
		
	}
	
	
}
