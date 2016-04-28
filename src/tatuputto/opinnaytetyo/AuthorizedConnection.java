package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Muodostaa yhteyden palvelimeen API:n ja Apachen HTTP Clientin avulla.
 * Toteuttaa APIConnection rajapintaluokan.
 */
public class AuthorizedConnection implements APIConnection {
	
	/**
	 * Muodostaa yhteyden palvelimeen, lisää pyyntöön Authorization headerin.
	 * @param method Käytettävä HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyyntö lähetään.
	 * @param data Pyynnön mukana lähetettävä data.
	 * @param accessToken Käyttäjäkohtainen avain, jonka avulla voidaan tehda muutoksia käyttäjän gisteihin API:n välityksellä.
	 * @return Palauttaa vastauksen sisällön String muodossa.
	 */
	public String formConnection(String method, String url, String data, String accessToken) {
		//Avataan yhteys ja lisataan mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
	    	accessToken = "8c12e3f78956b6b03e57d10a100676d3726e8f77";
		    String authString = "token " + accessToken;
		    httpMethod.addHeader("Authorization", authString);
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
	
	/**
	 * Lukee vastauksen sisällön.
	 * @param response Palvelimen vastaus lähetettyyn pyyntöön.
	 * @return Vastauksen sisältö(body, ei headereita) String muodossa.
	 */
	private String readResponse(CloseableHttpResponse response) {
		//Vastauskoodi
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		HttpEntity entity = response.getEntity();
		
		String line = "";
		String str = "";
	    if (entity != null) {
	    	//Käsitellään vastauksen sisältö rivi kerrallaan
	        try(BufferedReader br1 = new BufferedReader(new InputStreamReader(entity.getContent()))) {
	        	while ((line = br1.readLine()) != null) {
	        		str = str.concat(line + "\n");
				}
	        	System.out.println(str);
	        	response.close();
	        	
	        	return str;
	        } 
	        catch(IOException e) { 	
	        	System.out.println("Vastausta ei pystytty lukemaan.");
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
	    return null;
	}
	
	/**
	 * Palautetaan HTTP -metodin(GET, POST, PATCH, DELETE) käsittelyn hoitavan luokan referenssi hyödyntäen polymorfismia.
	 * @param method Käytettävä HTTP -metodi(GET, POST, PATCH, DELETE).
	 * @param url URL-osoite, minne pyyntö lähetään.
	 * @param data Pyynnön mukana lähetettävä data.
	 * @param accessToken Käyttäjäkohtainen avain, jonka avulla voidaan tehda muutoksia käyttäjän gisteihin API:n välityksellä.
	 * @return Referenssi HTTP -metodin käsittelevään luokkaan(HttpGet, HttpPost, HttpPatch, HttpDelete).
	 */
	private HttpRequestBase setHTTPMethod(String method, String url, String data) {
		if(method.equals("GET")) {
			HttpGet httpGet = new HttpGet(url);
			return httpGet;
		}
		else if(method.equals("POST")) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			StringEntity params;
			
			try {
				params = new StringEntity(data);
				httpPost.setEntity(params);
				return httpPost;
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}

