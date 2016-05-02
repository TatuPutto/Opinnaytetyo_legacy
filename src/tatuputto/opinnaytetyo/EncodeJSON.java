package tatuputto.opinnaytetyo;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Datan muuntaminen JSON-muotoon.
 */
public class EncodeJSON {

	/**
	 * Muunnetaan lisättävän Gistin tiedot JSON-muotoon.
	 * @param description Gistin kuvaus.
	 * @param isPublic Gistin näkyvyys(public/private).
	 * @param filenames Gistin sisältämien tiedostojen nimet.
	 * @param sources Gistin sisältämien tiedostojen lähdekoodi.
	 * @return requestJSON - tiedot muunnettuna JSON-muotoon ja pakattuna yhteen JSON-olioon(JSONObject).
	 */
	public JSONObject encodeJSONRequestPOST(String description, boolean isPublic, String[] filenames, String[] sources) {
		try {
			JSONObject requestJSON = new JSONObject();
			requestJSON.put("description", description);
			requestJSON.put("public", isPublic);

			//Lisätään gistin sisältämät tiedostot muotoon 
			//files: {
			//	<tiedostonimi>:{
			//		content:<lähdekoodi>
			//	}
			//}
			JSONObject files = new JSONObject();
			for(int i = 0; i < filenames.length; i++) {
				JSONObject file = new JSONObject();	
				file.put("content", sources[i]);
				files.put(filenames[i], file);	
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

	/**
	 * Muunnetaan muokattavan Gistin päivitetyt tiedot JSON-muotoon.
	 * @param description Gistin kuvaus.
	 * @param filesToUpdate Muokattavat tiedostot.
	 * @param updatedFilenames Muokattavan tiedoton uusi nimi.
	 * @param updatedSources Muokattavan tiedoton uusi lähdekoodi.
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
			System.out.println("JSONin käsittelyssä tapahtui virhe.");
		}

		return null;	
	}
}

