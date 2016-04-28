package tatuputto.opinnaytetyo;


import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Tatu Putto
 * Tämä luokka hoitaa uuden gistin luomisen
 */
public class CreateGist {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public void createNewGist() {
		ArrayList<String> filenames = new ArrayList<String>();
		ArrayList<String> fileSources = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("Gist description");
		String desc = input.nextLine();
		
		//for(int i = 0; i < 2; i++) {
			
			input.nextLine();
			System.out.println("Filename");
			filenames.add(input.nextLine());
			
			System.out.println("Code");
			fileSources.add(input.nextLine());
			
			
		//}
		
		encodeJSON(desc, filenames, fileSources);
		input.close();
	}
	
	//Tehdään syötteen pohjalta tarvittavat JSON-oliot
	public void encodeJSON(String desc, ArrayList<String> filename, ArrayList<String> code) {
		try {
			
			JSONObject obj = new JSONObject();

			obj.put("description", desc);
			obj.put("public", "false");
			
			//JSONObject combine = new JSONObject();
			for(int i = 0; i < filename.size(); i++) {
				JSONObject nestedfiles2 = new JSONObject();
				nestedfiles2.put("content", code.get(i));
				
				
				JSONObject nestedfiles = new JSONObject();
				nestedfiles.put(filename.get(i), nestedfiles2);
				
				obj.accumulate("files", nestedfiles);
			}
			
	
			System.out.print(obj);
			//sendGist(obj);
			String url = "https://api.github.com/gists";
			String data = obj.toString();
			String accessToken = "c6a07d2fce28b39405409ce97513a4d8c605b9f4";
			
			connection.formConnection("POST", url, data, accessToken);
		}
		catch(JSONException e) {}
	
	}
}