package tatuputto.opinnaytetyo.json;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseSingleGistJSON {
	
	public Gist parseJSON(String JSONresponse) {
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONObject jObject = new JSONObject(JSONresponse);
			String gistId = jObject.getString("id"); //Etsit‰‰n JSON oliosta gistin id
			String description = jObject.getString("description"); //Etsit‰‰n JSON oliosta tiedoston kuvauksen sis‰lt‰v‰ avain
			JSONObject files = jObject.getJSONObject("files"); //Etsit‰‰n avain, joka sis‰lt‰‰ tiedostojen tarkemmat tiedot nested olioina

			Gist gist = new Gist(gistId, description, parseNestedObjects(description, files));
			
			return gist;
		}
		catch(JSONException e) {}
		return null;
	}
	
	//Puretaan files-olio yksi sisennetty olio kerrallaan
	public ArrayList<GistFile> parseNestedObjects(String description, JSONObject files) {
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
	            String content = singleFile.getString("content");
	           // System.out.println(filename + ", " + language + ", " + rawUrl);
	            gistFiles.add(new GistFile(filename, language, rawUrl, content));
	            
	        } catch (JSONException e) {}
	    }
	    
	    return gistFiles;
	}
}
