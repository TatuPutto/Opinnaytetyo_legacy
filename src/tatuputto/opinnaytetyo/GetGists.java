package tatuputto.opinnaytetyo;

import java.util.ArrayList;


public class GetGists {
	ParseResponseJSON parse = new ParseResponseJSON();
	AuthorizedConnection connection = new AuthorizedConnection();
	
	//Haetaan gistit
	public ArrayList<Gist> getGists(String accessToken) {
		String url = "https://api.github.com/gists";
		
		//Palautetaan parsittu JSON
		return parse.parseResponse(connection.formConnection(url, accessToken));
	}
}
