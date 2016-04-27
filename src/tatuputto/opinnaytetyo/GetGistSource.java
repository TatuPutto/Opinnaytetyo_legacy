package tatuputto.opinnaytetyo;


/**
 * 
 * @author Tatu
 * Tämä luokka hakee gistin sisältämän tiedoston lähdekoodin
 */
public class GetGistSource {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public String getSource(String rawUrl, String accessToken) {
		return connection.formConnection(rawUrl, accessToken);
	}
	
}
