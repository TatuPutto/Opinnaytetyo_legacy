package tatuputto.opinnaytetyo.json;

import org.json.JSONException;
import org.json.JSONObject;


public class ParseGistEditJSON {
	public String parseJSON(String JSONresponse) {
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONObject jObject = new JSONObject(JSONresponse);
			 //Etsitään JSON oliosta gistin id
			String gistId = jObject.getString("id");
		
			return gistId;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
