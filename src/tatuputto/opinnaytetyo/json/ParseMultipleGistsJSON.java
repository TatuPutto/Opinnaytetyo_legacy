package tatuputto.opinnaytetyo.json;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseMultipleGistsJSON {
	
	public ArrayList<Gist> parseJSON(String JSONresponse) {
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONArray jArray = new JSONArray(JSONresponse);

			//K‰yd‰‰n muodostettu JSON taulukko olio kerrallaan l‰pi
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObject = jArray.getJSONObject(i); //Muodostetaan jokaisesta taulukon indeksist‰ JSON olio
				String gistId = jObject.getString("id"); //Etsit‰‰n JSON oliosta gistin id
				String description = jObject.getString("description"); //Etsit‰‰n JSON oliosta tiedoston kuvauksen sis‰lt‰v‰ avain
				JSONObject files = jObject.getJSONObject("files"); //Etsit‰‰n avain, joka sis‰lt‰‰ tiedostojen tarkemmat tiedot sisennettyin‰ olioina
				
				gists.add(new Gist(gistId, description, parseNestedObjects(files)));
			}
			
			return gists;
		}
		catch(JSONException e) {}
		return null;
	}
	
	//Puretaan files-olio yksi sisennetty olio kerrallaan
	public ArrayList<GistFile> parseNestedObjects(JSONObject files) {
		ArrayList<GistFile> gistFiles = new ArrayList<GistFile>();
		Iterator<?> iterator = files.keys();
		
	    while (iterator.hasNext()) {
	        String key = (String)iterator.next();

	        try {
	            //Etsit‰‰n sisennetyst‰ oliosta koodileikkeen n‰ytt‰miseen tarvittavat tiedot
	        	JSONObject singleFile = (JSONObject)files.get(key);
	            String filename = singleFile.getString("filename");
	            String language = singleFile.getString("language");
	            String rawUrl = singleFile.getString("raw_url");
	           // System.out.println(filename + ", " + language + ", " + rawUrl);
	            gistFiles.add(new GistFile(filename, language, rawUrl));
	            
	        } catch (JSONException e) {}
	    }
	    
	    return gistFiles;
	}
}
