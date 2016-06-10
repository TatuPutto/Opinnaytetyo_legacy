package tatuputto.opinnaytetyo.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ParseJSONForAJAXResponse {
	
	
	/**
	 * Palauta - ID, Omistaja, gist nimi, kuvaus
	 *
	 */
	public JSONArray parseJSON(String JSONresponse) {
		
		
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONArray arr = new JSONArray(JSONresponse);

			JSONArray arrToReturn = new JSONArray();
			
			//K�yd��n muodostettu JSON taulukko olio kerrallaan l�pi
			for (int i = 0; i < arr.length(); i++) {
			
				//Muodostetaan jokaisesta taulukon indeksist� JSON olio
				JSONObject singleGistObj = arr.getJSONObject(i); 
			
				String gistId = singleGistObj.getString("id");
				String description = singleGistObj.getString("description"); 
		
				//Etsit��n avain, joka sis�lt�� gistin tiedostot
				String files = parseNestedObjects(singleGistObj.getJSONObject("files"));
				
				try {
					//String owner = "";
					//owner1 = parseGistOwnerInfo(singleGistObj.getJSONObject("owner"));
					JSONObject owner = singleGistObj.getJSONObject("owner");
					
					JSONObject object = new JSONObject(); 
					object.put("id", gistId);
					object.put("description", description);
					object.put("files", files);
					object.put("owner", owner.getString("login"));
					
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
	
	/*
	public String parseGistOwnerInfo(JSONObject ownerInfo) {
		try {
			String login = ownerInfo.getString("login");
			String avatarUrl = ownerInfo.getString("avatar_url");
			
			
			return login;
		}	
		catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	*/
	
	
	//Puretaan files-olio yksi sisennetty olio kerrallaan
	public String parseNestedObjects(JSONObject files) {
		Iterator<?> iterator = files.keys();
        String key = (String)iterator.next();

        try {
            //Etsit��n sisennetyst� oliosta koodileikkeen n�ytt�miseen tarvittavat tiedot
        	JSONObject singleFile = (JSONObject)files.get(key);
            String filename = singleFile.getString("filename");
            String language = singleFile.getString("language");
            String rawUrl = singleFile.getString("raw_url");
          
           
            return filename;
        } catch (JSONException e) {}
    
   
	    return null;
	}
	
}
