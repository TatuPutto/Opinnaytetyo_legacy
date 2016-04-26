package tatuputto.opinnaytetyo;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Tatu Putto
 * T�m� luokka muodostaa yhteyden API:in, ja liitt�� pyynt��n auktorisointi headerin
 */
public class AuthorizedConnection implements APIConnection {
	
	public URLConnection formConnection(String url, String accessToken) {
		//Avataan yhteys ja lis�t��n mukaan auktorisointi header
		try {
			URL myURL = new URL(url);
			URLConnection connection = myURL.openConnection();

			//String userName = "TatuPutto";
			// String userPassword = "Source93!";  
			// String authInfo = userName + ":" + userPassword;
			//String authString = "token " + Base64.encodeBase64String(accessToken.getBytes());


			//Muodostetaan ja lis�t��n auktorisointi header pyynt��n
			String authString = "token " + accessToken;
			connection.setRequestProperty("Authorization", authString);

			return connection;
		}
		catch(IOException e) {
			System.out.println("Yhteytt� ei voitu muodostaa.");
		}
		return null;
	}
}

