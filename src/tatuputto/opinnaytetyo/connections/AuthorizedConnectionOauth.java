package tatuputto.opinnaytetyo.connections;


import java.util.ArrayList;

import org.apache.http.HttpEntity;
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
	 * Muodostaa yhteyden palvelimeen, lis�� pyynt��n Authorization headerin(Oauth).
	 * @param method K�ytett�v� HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyynt� l�het��n.
	 * @param data Pyynn�n mukana l�hetett�v� data.
	 * @param accessToken K�ytt�j�kohtainen avain, jonka avulla voidaan tehda muutoksia k�ytt�j�n gisteihin API:n v�lityksell�.
	 * @return Palauttaa vastauksen sis�ll�n String muodossa.
	 */
	public ArrayList<String> formConnection(String method, String url, String data, String accessToken) {
		ArrayList<String> responseContent = new ArrayList<String>();
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lis�t��n auktorisointi header pyynt��n
		    String authInfo = "token " + accessToken;
		    httpMethod.addHeader("Authorization", authInfo);
			CloseableHttpResponse response = httpClient.execute(httpMethod);
			
			responseContent = readResponse(response);
			
			if(response != null) {
				response.close();
			}
		
			return responseContent;
			
		//TODO Tarkemmat poikkeustilanteet
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Yhteytt� ei voitu muodostaa.");
		}
		
	
		return null;
	}
}

