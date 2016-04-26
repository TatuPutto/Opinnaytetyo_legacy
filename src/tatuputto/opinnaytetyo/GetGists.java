package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GetGists {
	//TODO hae gistit apin kautta
	ParseResponseJSON parse = new ParseResponseJSON();
	AuthorizedConnection connection = new AuthorizedConnection();
	//Haetaan gistit API:n välityksellä
	public ArrayList<Gist> getGistsThroughAPI(String accessToken) {
		try { 
			//Muodostetaan yhteys palvelimeen
			String newUrl = "https://api.github.com/gists";
			//URLConnection connection = myURL.openConnection();
			//URLConnection connection = connection1.formConnection(newUrl, accessToken);
			//String userName = "TatuPutto";
			// String userPassword = "Source93!";  
			// String authInfo = userName + ":" + userPassword;
			//String authString = "token " + Base64.encodeBase64String(accessToken.getBytes());
			//System.out.println(connection);
			

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
			//String authString = "token " + accessToken;
			//connection.setRequestProperty("Authorization", authString);
			
			
			
			
			
	
			String line = "";
			String response = "";

			//Luetaan palvelimelta saatu vastaus
			try(BufferedReader br1 = new BufferedReader(new InputStreamReader(connection.formConnection(newUrl, accessToken).getInputStream()))) {
				while ((line = br1.readLine()) != null) {
					response = response.concat(line);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Vastausta ei pystytty lukemaan.");
			}
			
			//Lähetään vastaus parsittavaksi ja palautetaan parsittu vastaus
			return parse.parseResponse(response);
	
		}
		catch(Exception e) {

		}
		
		return null;
	}
}
