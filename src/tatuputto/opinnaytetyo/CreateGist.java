package tatuputto.opinnaytetyo;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
		
		/*for(int i = 0; i < 2; i++) {
			
			input.nextLine();
			System.out.println("Filename");
			filenames.add(input.nextLine());
			
			System.out.println("Code");
			fileSources.add(input.nextLine());
			
			
		}*/
		
		filenames.add("Testfile1.java");
		filenames.add("Testfile2.java");
		
		fileSources.add("public class Testfile1 {}");
		fileSources.add("public class Testfile2 {}");

		encodeJSON(desc, filenames, fileSources);
		input.close();
	}
	
	//Tehdään syötteen pohjalta tarvittavat JSON-oliot
	public void encodeJSON(String desc, ArrayList<String> filename, ArrayList<String> code) {
		try {
			JSONObject requestJSON = new JSONObject();
			JSONObject files = new JSONObject();
			
			requestJSON.put("description", desc);
			requestJSON.put("public", "false");
			
		
			for(int i = 0; i < filename.size(); i++) {
				JSONObject file = new JSONObject();
				file.put("content", code.get(i));
			
				files.put(filename.get(i), file);
				
			}
			
			//System.out.println(nested);
			requestJSON.put("files", files);
			System.out.print(requestJSON);
			
			String url = "https://api.github.com/gists";
			String data = requestJSON.toString();
			String accessToken = "8c12e3f78956b6b03e57d10a100676d3726e8f77";
			
			connection.formConnection("POST", url, data, accessToken);
		}
		catch(JSONException e) {
			e.printStackTrace();
			System.out.println("JSON käsittelyssä tapahtui virhe.");
		}
	
	}
}