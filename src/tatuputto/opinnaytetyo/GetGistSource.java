package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author Tatu
 * Tämä luokka hakee gistin sisältämän tiedoston lähdekoodin
 */
public class GetGistSource {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public String getSource(String rawUrl) {
		String line = "";
		String response = "";
		String accessToken = "ab2e7eeac02d4565dfd8816b233d8e336b90ab18";
		//Luetaan palvelimelta saatu vastaus
		try(BufferedReader br1 = new BufferedReader(new InputStreamReader(connection.formConnection(rawUrl, accessToken).getInputStream()))) {
			while ((line = br1.readLine()) != null) {
				response = response.concat(line + "\n");
			}
			
			return response;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Vastausta ei pystytty lukemaan.");
		}
		
		return null;
	}
	
}
