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

/**v
 * 
 * @author Tatu Putto
 * Tämä luokka muodostaa yhteyden API:in Apachen HTTP Clientin välityksellä
 */
public class AuthorizedConnection implements APIConnection {
	
	public String formConnection(String method, String url, String data, String accessToken) {
		//Avataan yhteys ja lisätään mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			//HttpGet httpget = new HttpGet(url);
			HttpRequestBase httpMethod = setHTTPMethod(method, url, data);

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
	    	accessToken = "c6a07d2fce28b39405409ce97513a4d8c605b9f4";
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
	
	//Luetaan responsen sisältö
	private String readResponse(CloseableHttpResponse response) {
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		HttpEntity entity = response.getEntity();
		String line = "";
		String str = "";
	    if (entity != null) {
	        //InputStream instream = entity.getContent();
	        try(BufferedReader br1 = new BufferedReader(new InputStreamReader(entity.getContent()))) {
	        	while ((line = br1.readLine()) != null) {
	        		str = str.concat(line + "\n");
				}
	        	System.out.println(str);
	        	
	        	return str;
	        } 
	        catch(IOException e) { 	
	        	System.out.println("Vastausta ei pystytty lukemaan.");
	        }
	    }
	    return null;
	}
	
	//Hyödynnetään polymorhismia, luodaan metodi joka palautaa 
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
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return httpPost;
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}

