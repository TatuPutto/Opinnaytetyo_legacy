package tatuputto.opinnaytetyo.json;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;
import tatuputto.opinnaytetyo.gists.User;


public class ParseJSONForAJAXResponse {
	
	
	/**
	 * Palauta - ID, Omistaja, gist nimi, kuvaus
	 *
	 */
	public JSONArray parseJSON(String JSONresponse) {
		
		
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONArray jArray = new JSONArray(JSONresponse);

			JSONArray arrToReturn = new JSONArray();
			
			//K�yd��n muodostettu JSON taulukko olio kerrallaan l�pi
			for (int i = 0; i < jArray.length(); i++) {
			
				//Muodostetaan jokaisesta taulukon indeksist� JSON olio
				JSONObject jObject = jArray.getJSONObject(i); 
			
				String gistId = jObject.getString("id");
				String description = jObject.getString("description"); 
		
				//Etsit��n avain, joka sis�lt�� gistin tiedostot
				String files = parseNestedObjects(jObject.getJSONObject("files"));
				
				//JSONObject owner = new JSONObject();
				
				String owner1 = "";
				try {

					owner1 = parseGistOwnerInfo(jObject.getJSONObject("owner"));
					
					JSONObject object = new JSONObject(); 
					object.put("id", gistId);
					object.put("description", description);
					object.put("files", files);
					object.put("owner", "dafsdf");
					
					arrToReturn.put(object);
				}
				catch(JSONException e) {
					//e.printStackTrace();
					
					/*JSONObject object = new JSONObject(); 
					object.put("id", gistId);
					object.put("description", description);
					object.put("files", files);
					object.put("owner", "Ano");
					
					arrToReturn.put(object);*/
				}
			}
			System.out.println(arrToReturn);
			return arrToReturn;
		}
		catch(JSONException e) {}
		return null;
	}
	
	
	public String parseGistOwnerInfo(JSONObject ownerInfo) {
		try {
			String login = ownerInfo.getString("login");
			String avatarUrl = ownerInfo.getString("avatar_url");
			
			/*JSONObject owner = new JSONObject();
			owner.put("login", login);
			owner.put("avatar_url", avatarUrl);*/
		    
			return login;
		}	
		catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	
	
	
	//Puretaan files-olio yksi sisennetty olio kerrallaan
	public String parseNestedObjects(JSONObject files) {
		JSONObject object = new JSONObject();
	
		Iterator<?> iterator = files.keys();
		
	   
	        String key = (String)iterator.next();

	        try {
	            //Etsit��n sisennetyst� oliosta koodileikkeen n�ytt�miseen tarvittavat tiedot
	        	JSONObject singleFile = (JSONObject)files.get(key);
	            String filename = singleFile.getString("filename");
	            String language = singleFile.getString("language");
	            String rawUrl = singleFile.getString("raw_url");
	          
	           /* object.put("filename", filename);
	          
	            object.put("language", language);
	            object.put("rawUrl", rawUrl);*/
	            
	            return filename;
	        } catch (JSONException e) {}
	    
	   
	    return null;
	}
}
