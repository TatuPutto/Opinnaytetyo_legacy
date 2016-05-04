package tatuputto.opinnaytetyo.gists;

import java.util.ArrayList;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;

/**
 * 
 * @author Tatu
 * T�m� luokka hakee gistin sis�lt�m�n tiedoston l�hdekoodin
 */
public class GetGistSource {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public ArrayList<String> getSource(String rawUrl, String accessToken) {
		return connection.formConnection("GET", rawUrl, "", accessToken);
	}
	
}
