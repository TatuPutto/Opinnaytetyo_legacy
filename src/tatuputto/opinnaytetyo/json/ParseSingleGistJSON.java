package tatuputto.opinnaytetyo.json;

import tatuputto.opinnaytetyo.gists.Gist;
import tatuputto.opinnaytetyo.gists.GistFile;
import tatuputto.opinnaytetyo.gists.User;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Tämä luokka hoitaa yksittäisen gistin JSONin parsimisen.
 * Lopputuotoksena palautetaan referenssimuuttuja parsituista tiedoista muodostettuun Gist-luokan olioon.
 */
public class ParseSingleGistJSON {
	
	/**
	 * Parsitaan JSON ja luodaan Gist-luokasta olio, jolle parsittu tieto asetetaan.
	 * @param JSONresponse, yksittäisen gistin JSON-data String-muodossa.
	 * @return Referenssimuuttuja luotuun Gist-luokan olioon.
	 */
	public Gist parseJSON(String JSONresponse) {
		try {
			//Muodostetaan vastauksena saadusta String muotoisesta JSONinsta, JSON taulukko
			JSONObject jObject = new JSONObject(JSONresponse);
			 //Etsitään JSON oliosta gistin id
			String gistId = jObject.getString("id");
			//Etsitään JSON oliosta tiedoston kuvauksen sisältävä avain
			String description = jObject.getString("description"); 
			
			//Etsitään avain, joka sisältää gistin omistajan tiedot
			JSONObject owner = jObject.getJSONObject("owner");
			
			//Etsitään avain, joka sisältää tiedostojen tarkemmat tiedot nested olioina
			JSONObject files = jObject.getJSONObject("files");

			Gist gist = new Gist(gistId, description, parseGistOwnerInfo(owner), parseFileObjects(files));
			
			return gist;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return null;
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
	public ArrayList<GistFile> parseFileObjects(JSONObject files) {
		ArrayList<GistFile> gistFiles = new ArrayList<GistFile>();
		
		//Käydään files JSON-olio läpi yksi sisennetty olio kerrallaan
		Iterator<?> iterator = files.keys();
	    while (iterator.hasNext()) {
	        String key = (String)iterator.next();

	        try {
	            //Etsitään sisennetystä oliosta tarvittavat tiedoston tiedot
	        	JSONObject singleFile = (JSONObject)files.get(key);
	            String filename = singleFile.getString("filename"); 
	            String rawUrl = singleFile.getString("raw_url");
	            String content = singleFile.getString("content");
	            
	            //Yritetään löytää tiedoston ohjelmointikieli
	            try {
	            	String language = singleFile.getString("language");
	            	gistFiles.add(new GistFile(filename, language, rawUrl, content));
	            }
	            catch(JSONException e) {
	            	gistFiles.add(new GistFile(filename, rawUrl, content));
	            }
	        } 
	        catch (JSONException e) {
	        	e.printStackTrace();
	        }
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
