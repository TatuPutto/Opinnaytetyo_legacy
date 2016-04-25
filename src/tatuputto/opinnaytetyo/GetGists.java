package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetGists {
	//TODO hae gistit apin kautta
		ParseResponseJSON parse = new ParseResponseJSON();
		
		//Haetaan gistit API:n välityksellä
		public void getGistsThroughAPI(String accessToken) {
			
			try {
				//Muodostetaan yhteys palvelimeen
				String newUrl = "https://api.github.com/gists";
				URL myURL = new URL(newUrl);
				URLConnection connection = myURL.openConnection();
			
				//String userName = "TatuPutto";
		       // String userPassword = "Source93!";  
		       // String authInfo = userName + ":" + userPassword;
		        //String authString = "token " + Base64.encodeBase64String(accessToken.getBytes());
		      
		        
		        //Muodostetaan ja lisätään auktorisointi header pyyntöön
		        String authString = "token " + accessToken;
				connection.setRequestProperty("Authorization", authString);
				
				String line = "";
				String response = "";
				
				//Luetaan palvelimelta saatu vastaus
				BufferedReader br1 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br1.readLine()) != null) {
					response = response.concat(line);
				}
				
				//Lähetään vastaus parsittavaksi
				parse.parseResponse(response);
			}
			catch(Exception e) {
				
			}
		}
}
