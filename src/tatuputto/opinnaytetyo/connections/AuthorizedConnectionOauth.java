package tatuputto.opinnaytetyo.connections;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;

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
	public String[] formConnection(String method, String url, String data, String accessToken) {
		String[] responseContent = new String[5];
		
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Lisätään auktorisointi header
		    String authInfo = "token " + accessToken;
		    httpMethod.addHeader("Authorization", authInfo);
		    
		    //Suoritetaan pyyntö
			CloseableHttpResponse response = httpClient.execute(httpMethod);
		
			//Haetaan Link-headerin arvot, seuraava sivu ja viimeinen sivu
			String nextPage = "";
			String lastPage = "";
			
			HeaderElementIterator iterator = new BasicHeaderElementIterator(response.headerIterator("Link"));

			int i = 0;
			while (iterator.hasNext()) {
				HeaderElement elem = iterator.nextElement();
				
				if(i == 0) {
					nextPage = elem.getValue().split("&")[0];
					i++;
				}
				else {
					lastPage = elem.getValue().split("&")[0];
				}
			}
			
			System.out.println("Seuraava sivu: " + nextPage + ", Viimeinen sivu: " + lastPage);
		
			
			
			HttpEntity entity = response.getEntity();
			
			String line = "";
			String content = "";
			
			if (entity != null) {
			    try(BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()))) {
			    	while ((line = br.readLine()) != null) {
			    		content = content.concat(line + "\n");
					}
			    	
			    	response.close();
			    } 
			    catch(IOException e) { 	
			    	e.printStackTrace();
			    	httpMethod.abort();
			    }
			    finally {
			    	if(response != null) {
			    		try {
			    			response.close();
			    		} 
			    		catch (IOException e) {
			    			e.printStackTrace();
			    		}
			    	}
			    }
			}
			
			
			//Palautetaan oleelliset vastauksesta saadut tiedot
			responseContent[0] = Integer.toString(response.getStatusLine().getStatusCode());
			responseContent[1] = response.getStatusLine().getReasonPhrase();
			responseContent[2] = content;
			responseContent[3] = nextPage;
			responseContent[4] = lastPage;
			
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

