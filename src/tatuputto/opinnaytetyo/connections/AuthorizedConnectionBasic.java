package tatuputto.opinnaytetyo.connections;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class AuthorizedConnectionBasic extends Connection {
	/**
	 * Muodostaa yhteyden palvelimeen, lisää pyyntöön Authorization headerin(Basic).
	 * @param method Käytettävä HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyyntö lähetään.
	 * @param data Pyynnön mukana lähetettävä data.
	 * @param username Käyttäjätunnus/sovelluksen client id.
	 * @param password Käyttäjän salasana/sovelluksen client secret
	 * @return Palauttaa vastauksen sisällön String muodossa.
	 */
	public ArrayList<String> formConnection(String method, String url, String data, String username, String password) {
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
	        String authInfo = username + ":" + password;
	        byte[] authBytes = authInfo.getBytes(StandardCharsets.UTF_8);
	        String authInfoEncoded = Base64.getEncoder().encodeToString(authBytes);
	        
		    httpMethod.addHeader("Authorization", "Basic " + authInfoEncoded);
			CloseableHttpResponse response = httpClient.execute(httpMethod);
			
			return readResponse(response);
		
			
		//TODO Tarkemmat poikkeustilanteet
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Yhteyttä ei voitu muodostaa.");
		}
	
		return null;
	}
}
