package tatuputto.opinnaytetyo.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Datan muuntaminen JSON-muotoon.
 */
public class EncodeJSON {

	/**
	 * Muunnetaan lis�tt�v�n Gistin tiedot JSON-muotoon.
	 * @param description Gistin kuvaus.
	 * @param isPublic Gistin n�kyvyys(public/private).
	 * @param filenames Gistin sis�lt�mien tiedostojen nimet.
	 * @param sources Gistin sis�lt�mien tiedostojen l�hdekoodi.
	 * @return requestJSON - tiedot muunnettuna JSON-muotoon ja pakattuna yhteen JSON-olioon(JSONObject).
	 */
	public JSONObject encodeJSONRequestPOST(String description, boolean isPublic, String[] filenames, String[] sources) {
		try {
			JSONObject requestJSON = new JSONObject();
			requestJSON.put("description", description);
			requestJSON.put("public", isPublic);

			//Lisätään gistin sis�lt�m�t tiedostot muotoon 
			//files: {
			//	<tiedostonimi>:{
			//		content:<l�hdekoodi>
			//	}
			//}
			JSONObject files = new JSONObject();
			for(int i = 0; i < filenames.length; i++) {
				JSONObject file = new JSONObject();	
				file.put("content", sources[i]);
				files.put(filenames[i], file);	
			}

			requestJSON.put("files", files);
			//System.out.print(requestJSON);

			
			
			
			return replaceSpecialCharacters(requestJSON);
		}
		catch(JSONException e) {
			e.printStackTrace();
			System.out.println("JSON k�sittelyss� tapahtui virhe.");
		}

		return null;
	}

	/**
	 * Muunnetaan muokattavan Gistin p�ivitetyt tiedot JSON-muotoon.
	 * @param description Gistin kuvaus.
	 * @param filesToUpdate Muokattavat tiedostot.
	 * @param updatedFilenames Muokattavan tiedoton uusi nimi.
	 * @param updatedSources Muokattavan tiedoton uusi l�hdekoodi.
	 * @return requestJSON - tiedot muunnettuna JSON-muotoon ja pakattuna yhteen JSON-olioon(JSONObject).
	 */
	public JSONObject encodeJSONRequestPATCH(String description, String[] filesToUpdate, String[] updatedFilenames, String[] updatedSources) {

		try {
			JSONObject requestJSON = new JSONObject();
			requestJSON.put("description", description);

			JSONObject files = new JSONObject();
			for(int i = 0; i < filesToUpdate.length; i++) {
				JSONObject file = new JSONObject();
				file.put("filename", updatedFilenames[i]);
				file.put("content", updatedSources[i]);
				files.put(filesToUpdate[i], file);
			}

			//System.out.println(nested);
			requestJSON.put("files", files);
			System.out.print(requestJSON);

			return requestJSON;
		}
		catch(JSONException e) {
			e.printStackTrace();
			System.out.println("JSONin k�sittelyss� tapahtui virhe.");
		}

		return null;	
	}
	
	
	private JSONObject replaceSpecialCharacters(JSONObject obj) {
		String objStr = obj.toString().replace("ä", "\u00E4").replace("ö", "\u00F6");
		//objStr.replace("ä", "U+00E4");
		//objStr.replace("ö", "U+00F6");
		System.out.println(objStr);
		
		try {
			JSONObject jsonObj = new JSONObject(objStr);
			return jsonObj;
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}

