package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**v
 * 
 * @author Tatu Putto
 * Tämä luokka muodostaa yhteyden API:in Apachen HTTP Clientin välityksellä
 */
public class AuthorizedConnection implements APIConnection {
	
	public String formConnection(String url, String accessToken) {
		//Avataan yhteys ja lisätään mukaan auktorisointi header
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);

			//Muodostetaan ja lisätään auktorisointi header pyyntöön
	    	accessToken = "615b87d48ce87d6563b93ef117c4aac23281f429";
		    String authString = "token " + accessToken;
			httpget.addHeader("Authorization", authString);
			CloseableHttpResponse response = httpClient.execute(httpget);
			
			return readResponse(response);
			
		//TODO Tarkemmat poikkeustilanteet
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Yhteyttä ei voitu muodostaa.");
		}
	
		return null;
	}
	
	
	//Luetaan responsen sisältö
	public String readResponse(CloseableHttpResponse response) {
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
	        	//System.out.println(response);
	        	
	        	return str;
	        } 
	        catch(IOException e) { 	
	        	System.out.println("Vastausta ei pystytty lukemaan.");
	        }
	    }
	    return null;
	}
	
	
}

