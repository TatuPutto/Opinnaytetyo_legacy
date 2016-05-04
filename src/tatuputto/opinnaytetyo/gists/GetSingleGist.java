package tatuputto.opinnaytetyo.gists;

/**
 * Haetaan yksittäinen gist.
 *
 */
public class GetSingleGist {
	ParseSingleGistJSON parse = new ParseSingleGistJSON();
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public Gist getGist(String accessToken, String gistId) {
		String url = "https://api.github.com/gists/" + gistId;
		
		//Palautetaan parsittu JSON
		return parse.parseJSON(connection.formConnection("GET", url, "", accessToken));
	}
}
