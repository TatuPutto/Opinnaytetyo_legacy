package tatuputto.opinnaytetyo;


/**
 * 
 * @author Tatu
 * T�m� luokka hakee gistin sis�lt�m�n tiedoston l�hdekoodin
 */
public class GetGistSource {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public String getSource(String rawUrl, String accessToken) {
		return connection.formConnection(rawUrl, accessToken);
	}
	
}
