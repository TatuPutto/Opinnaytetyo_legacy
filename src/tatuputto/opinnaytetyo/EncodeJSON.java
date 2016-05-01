package tatuputto.opinnaytetyo;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class EncodeJSON {
	//Tehdään syötteen pohjalta tarvittavat JSON-oliot
		public JSONObject encodeJSONRequestPOST(String desc, boolean isPublic, ArrayList<String> filename, ArrayList<String> code) {
			try {
				JSONObject requestJSON = new JSONObject();
				JSONObject files = new JSONObject();
				
				requestJSON.put("description", desc);
				requestJSON.put("public", isPublic);
				
			
				for(int i = 0; i < filename.size(); i++) {
					JSONObject file = new JSONObject();
					file.put("content", code.get(i));
				
					files.put(filename.get(i), file);
					
				}
				
				//System.out.println(nested);
				requestJSON.put("files", files);
				System.out.print(requestJSON);
				
				return requestJSON;
			}
			catch(JSONException e) {
				e.printStackTrace();
				System.out.println("JSON käsittelyssä tapahtui virhe.");
			}
			
			return null;
		
		}
		
		
		public JSONObject encodeJSONRequestPATCH(String desc, ArrayList<String> filesToUpdate, ArrayList<String> updatedFilenames, ArrayList<String> updatedSources) {
			try {
				JSONObject requestJSON = new JSONObject();
				JSONObject files = new JSONObject();
				
				requestJSON.put("description", desc);
				

				for(int i = 0; i < filesToUpdate.size(); i++) {
					JSONObject file = new JSONObject();
					file.put("filename", updatedFilenames.get(i));
					file.put("content", updatedSources.get(i));
					files.put(filesToUpdate.get(i), file);
				}
				
				//System.out.println(nested);
				requestJSON.put("files", files);
				System.out.print(requestJSON);
				
				return requestJSON;
			}
			catch(JSONException e) {
				e.printStackTrace();
				System.out.println("JSONin käsittelyssä tapahtui virhe.");
			}
			
			return null;
		
		}
}

