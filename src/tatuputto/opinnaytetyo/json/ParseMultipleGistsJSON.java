package tatuputto.opinnaytetyo.json;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;
import tatuputto.opinnaytetyo.gists.User;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Tämä luokka hoitaa useamman gistin tiedot sisältävän JSONin parsimisen.
 * Lopputuotoksena palautetaan taulukko, joka sisältää referenssimuuttujia parsituista tiedoista muodostettuihin Gist-luokan olioihin.
 */
public class ParseMultipleGistsJSON {
	
	public ArrayList<Gist> parseJSON(String JSONresponse) {
		ArrayList<Gist> gists = new ArrayList<Gist>();
		JSONArray jArray;
		
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON-taulukko
			jArray = new JSONArray(JSONresponse);
	
			//K�yd��n muodostettu JSON taulukko olio kerrallaan l�pi
			for (int i = 0; i < jArray.length(); i++) {
				
				try {
					//Muodostetaan jokaisesta taulukon indeksistä JSON-olio, indeksi == gist
					JSONObject jObject = jArray.getJSONObject(i); 
				
					String gistId = jObject.getString("id");
					String description = jObject.getString("description"); 
			
					//Etsitään olio, joka sisältää gistin tiedostot
					JSONObject files = jObject.getJSONObject("files"); 
					
					//Poikkeus anonyyminä lisätyille gisteille
					User owner;
					try {
						owner = parseGistOwnerInfo(jObject.getJSONObject("owner"));
		
						gists.add(new Gist(gistId, description, owner, parseNestedObjects(files)));
					}
					catch(JSONException e) {
						//owner = new User("Anonymous", "https://avatars.githubusercontent.com/u/5699778?v=3");
					}
					
					/*String spam = "Bootstrap Customizer Config";
					String spam2 = "An error occurred while processing events.";
					
					if(description.equals(spam) || description.equals(spam2)) {
						
					}
					else {
						gists.add(new Gist(gistId, description, owner, parseNestedObjects(files)));
					}
					*/
				}
				catch(JSONException e) {}
			}
		}
		catch(JSONException e) {
			return null;
		}
		
		return gists;
	}
	
	
	public User parseGistOwnerInfo(JSONObject ownerInfo) {
		try {
			int id = ownerInfo.getInt("id");
			String login = ownerInfo.getString("login");
			String avatarUrl = ownerInfo.getString("avatar_url");
			
			User owner = new User(id, login, avatarUrl);  
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
