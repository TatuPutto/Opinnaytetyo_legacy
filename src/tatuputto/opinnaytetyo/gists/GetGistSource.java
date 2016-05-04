package tatuputto.opinnaytetyo.gists;

import java.util.ArrayList;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;

/**
 * 
 * @author Tatu
 * Tämä luokka hakee gistin sisältämän tiedoston lähdekoodin
 */
public class GetGistSource {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public ArrayList<String> getSource(String rawUrl, String accessToken) {
		return connection.formConnection("GET", rawUrl, "", accessToken);
	}
	
}
