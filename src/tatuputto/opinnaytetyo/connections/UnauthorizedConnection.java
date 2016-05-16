package tatuputto.opinnaytetyo.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
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
		ArrayList<String> responseContent = new ArrayList<String>();
		//Muodostetaan yhteys
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);
			CloseableHttpResponse response = httpClient.execute(httpMethod);
			
			HttpEntity entity = response.getEntity();

			String line = "";
			String str = "";
			if (entity != null) {
				//K�sitell��n vastauksen sis�lt� rivi kerrallaan
			    try(BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()))) {
			    	while ((line = br.readLine()) != null) {
			    		str = str.concat(line + "\n");
					}
			    	//System.out.println(str);
			    	//Lisätään vastaukoodi ja entityn sisältö taulukkoon
			    	responseContent.add(Integer.toString(response.getStatusLine().getStatusCode()));
					responseContent.add(response.getStatusLine().getReasonPhrase());
			    	responseContent.add(str);
			    	
			    	response.close();
			    } 
			    catch(IOException e) { 	
			    	//System.out.println("Vastausta ei pystytty lukemaan.");
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
