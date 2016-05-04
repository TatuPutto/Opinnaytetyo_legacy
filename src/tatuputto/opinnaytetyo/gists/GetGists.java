package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseMultipleGistsJSON;

import java.util.ArrayList;

/**
 * Haetaan kaikki kirjautuneen käyttäjän gistit. Jos käyttäjä ei ole kirjautunut haetaan uusimmat julkiset gistit.
 */
public class GetGists {
	ParseMultipleGistsJSON parse = new ParseMultipleGistsJSON();
	AuthorizedConnection connection = new AuthorizedConnection();
	
	//Haetaan gistit
	public ArrayList<Gist> getGists(String accessToken) {
		String url = "https://api.github.com/gists";
		
		//Palautetaan parsittu JSON
		return parse.parseJSON(connection.formConnection("GET", url, "", accessToken));
	}
}
