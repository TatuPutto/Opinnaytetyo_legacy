package tatuputto.opinnaytetyo.connections;


import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Muodostaa yhteyden palvelimeen API:n ja Apachen HTTP Clientin avulla.
 * 
 */
public class AuthorizedConnectionOauth extends Connection {
	
	/**
	 * Muodostaa yhteyden palvelimeen, lisää pyyntöön Authorization headerin(Oauth).
	 * @param method Käytettävä HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyyntö lähetään.
	 * @param data Pyynnön mukana lähetettävä data.
	 * @param accessToken Käyttäjäkohtainen avain, jonka avulla voidaan tehda muutoksia käyttäjän gisteihin API:n välityksellä.
	 * @return Palauttaa vastauksen sisällön String muodossa.
	 */
	public ArrayList<String> formConnection(String method, String url, String data, String accessToken) {
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
		    String authInfo = "token " + accessToken;
		    httpMethod.addHeader("Authorization", authInfo);
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

