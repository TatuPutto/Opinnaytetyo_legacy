package tatuputto.opinnaytetyo.connections;

import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class UnauthorizedConnection extends Connection {
	/**
	 * Muodostaa yhteyden palvelimeen ei auktorisoituna käyttäjänä.
	 * @param method Käytettävä HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyyntö lähetään.
	 * @param data Pyynnön mukana lähetettävä data.
	 * @param accessToken Käyttäjäkohtainen avain, jonka avulla voidaan tehda muutoksia käyttäjän gisteihin API:n välityksellä.
	 * @return Palauttaa vastauksen sisällön String muodossa.
	 */
	public ArrayList<String> formConnection(String method, String url, String data) {
		//Muodostetaan yhteys
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);
			CloseableHttpResponse response = httpClient.execute(httpMethod);
			
			//Palautetaan vastaus
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
