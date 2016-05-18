package tatuputto.opinnaytetyo.json;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;
import tatuputto.opinnaytetyo.gists.User;

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

			//K�yd��n muodostettu JSON taulukko olio kerrallaan l�pi
			for (int i = 0; i < jArray.length(); i++) {
				//Muodostetaan jokaisesta taulukon indeksist� JSON olio
				JSONObject jObject = jArray.getJSONObject(i); 
			
				String gistId = jObject.getString("id");
				String description = jObject.getString("description"); 
				
				JSONObject owner = jObject.getJSONObject("owner"); 
				
				//Etsit��n avain, joka sis�lt�� gistin tiedostot
				JSONObject files = jObject.getJSONObject("files"); 
				
				//luodaan uusi gist, jolle annetaan arvoksi id, kuvaus, sekä lista sen sisältämistä tiedostoista
				gists.add(new Gist(gistId, description, parseGistOwnerInfo(owner), parseNestedObjects(files)));
			}
			
			return gists;
		}
		catch(JSONException e) {}
		return null;
	}
	
	
	public User parseGistOwnerInfo(JSONObject ownerInfo) {
		try {
			String login = ownerInfo.getString("login");
			String avatarUrl = ownerInfo.getString("avatar_url");
			
			User owner = new User(login, avatarUrl);  
		    return owner;
		}	
		catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	
	
	
	//Puretaan files-olio yksi sisennetty olio kerrallaan
	public ArrayList<GistFile> parseNestedObjects(JSONObject files) {
		ArrayList<GistFile> gistFiles = new ArrayList<GistFile>();
		Iterator<?> iterator = files.keys();
		
	    while (iterator.hasNext()) {
	        String key = (String)iterator.next();

	        try {
	            //Etsit��n sisennetyst� oliosta koodileikkeen n�ytt�miseen tarvittavat tiedot
	        	JSONObject singleFile = (JSONObject)files.get(key);
	            String filename = singleFile.getString("filename");
	            String language = singleFile.getString("language");
	            String rawUrl = singleFile.getString("raw_url");
	          
	            gistFiles.add(new GistFile(filename, language, rawUrl));
	            
	        } catch (JSONException e) {}
	    }
	    
	    return gistFiles;
	}
}
