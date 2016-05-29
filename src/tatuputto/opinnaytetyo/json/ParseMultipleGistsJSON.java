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
	

	/**
	 * Parsitaan JSON ja luodaan parsituista tiedoista gist-luokan olioita,
	 * joiden referenssimuuttujat tallennetaan taulukkoon.
	 * @param JSONresponse, yksittäisen gistin JSON-data String-muodossa.
	 * @return Taulukko, joka sisältää referenssimuuttujat luotuihin gist-luokan olioihin.
	 */
	public ArrayList<Gist> parseJSON(String JSONresponse) {
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONista, JSON-taulukko
			JSONArray arr = new JSONArray(JSONresponse);
	
			for (int i = 0; i < arr.length(); i++) {
				try {
					//Muodostetaan jokaisesta taulukon indeksistä JSON-olio, indeksi == gist
					JSONObject singleGistObj = arr.getJSONObject(i); 
				
					String gistId = singleGistObj.getString("id");
					String description = singleGistObj.getString("description"); 
			
					//Etsitään olio, joka sisältää gistin tiedostot
					JSONObject files = singleGistObj.getJSONObject("files"); 
					
					
					//Jos gist on lisätty anonyyminä sitä ei lisätä listaan spämmin välttämiseksi
					try {
						User owner = parseGistOwnerInfo(singleGistObj.getJSONObject("owner"));
						gists.add(new Gist(gistId, description, owner, parseNestedObjects(files)));
					}
					catch(JSONException e) {
						//owner = new User("Anonymous", "https://avatars.githubusercontent.com/u/5699778?v=3");
						e.printStackTrace();
					}
					
				}
				catch(JSONException e) {}
			}
		}
		catch(JSONException e) {
			return null;
		}
		
		return gists;
	}
	
	
	/**
	 * Parsitaan JSONin sisältämä files-olio, joka sisältää tiedostojen tiedot sisennettyinä olioina.
	 * "files": {
	 *     "esimerkkiTiedosto1.java": {
	 *         "filename:" "esimerkkiTiedosto1.java",
	 *         "language:" "java",
	 *         ...
	 *     }
	 *     "esimerkkiTiedosto2.js": {
	 *     	   ...
	 *     }
	 *     ...
	 *     
	 * @param files Tiedostojen tiedot sisältävä JSON-olio.
	 * @return Taulukko, joka sisältää referenssit GistFile-luokasta luotuihin olioihin.
	 */
	public ArrayList<GistFile> parseNestedObjects(JSONObject files) {
		ArrayList<GistFile> gistFiles = new ArrayList<GistFile>();
		Iterator<?> iterator = files.keys();
		
		//Käydään files JSON-olio läpi yksi sisennetty olio kerrallaan
	    while (iterator.hasNext()) {
	        String key = (String)iterator.next();

	        try {
	            //Etsitään sisennetystä oliosta tarvittavat tiedoston tiedot
	        	JSONObject singleFile = (JSONObject)files.get(key);
	            String filename = singleFile.getString("filename");
	            String rawUrl = singleFile.getString("raw_url");
	            
	            try {
	            	String language = singleFile.getString("language");
	            	gistFiles.add(new GistFile(filename, language, rawUrl));
	            }
	            catch(JSONException e) {
	            	gistFiles.add(new GistFile(filename, rawUrl));
	            }
	        } 
	        catch (JSONException e) {}
	    }
	    
	    return gistFiles;
	}
	
	
	
	/**
	 * Parsitaan gistin omistajan tiedot ja luodaan parsittujen tietojen pohjalta user-luokan olio
	 * @param ownerInfo Gistin omistajan tiedot sisältävä JSON-olio.
	 * @return Referenssimuuttuja User-luokasta luotuun olioon.
	 */
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
}
