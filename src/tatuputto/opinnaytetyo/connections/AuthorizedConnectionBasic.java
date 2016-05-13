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
	 * Muodostaa yhteyden palvelimeen, lis�� pyynt��n Authorization headerin(Basic).
	 * @param method K�ytett�v� HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyynt� l�het��n.
	 * @param data Pyynn�n mukana l�hetett�v� data.
	 * @param username K�ytt�j�tunnus/sovelluksen client id.
	 * @param password K�ytt�j�n salasana/sovelluksen client secret
	 * @return Palauttaa vastauksen sis�ll�n String muodossa.
	 */
	public ArrayList<String> formConnection(String method, String url, String data, String username, String password) {
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lis�t��n auktorisointi header pyynt��n
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
			System.out.println("Yhteytt� ei voitu muodostaa.");
		}
	
		return null;
	}
}
