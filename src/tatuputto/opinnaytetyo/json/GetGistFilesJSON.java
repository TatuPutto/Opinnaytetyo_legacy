package tatuputto.opinnaytetyo.json;

import org.json.JSONException;
import org.json.JSONObject;


public class GetGistFilesJSON {
	public JSONObject GetGistFiles(String JSONresponse) {
		try {
			
			JSONObject jObject = new JSONObject(JSONresponse);
			JSONObject files = jObject.getJSONObject("files");
			System.out.println(files);
			return files;
			
		}
		catch(JSONException e) {}
		return null;
	}
}
