package tatuputto.opinnaytetyo.connections;

import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class UnauthorizedConnection extends Connection {
	/**
	 * Muodostaa yhteyden palvelimeen ei auktorisoituna k�ytt�j�n�.
	 * @param method K�ytett�v� HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyynt� l�het��n.
	 * @param data Pyynn�n mukana l�hetett�v� data.
	 * @param accessToken K�ytt�j�kohtainen avain, jonka avulla voidaan tehda muutoksia k�ytt�j�n gisteihin API:n v�lityksell�.
	 * @return Palauttaa vastauksen sis�ll�n String muodossa.
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
			System.out.println("Yhteytt� ei voitu muodostaa.");
		}
	
		return null;
	}
}
