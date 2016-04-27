package tatuputto.opinnaytetyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Tatu Putto
 * Tämä luokka hoitaa uuden gistin luomisen
 */
public class CreateGist {
//	AuthorizedConnection connection = new AuthorizedConnection();
	
	public void createNewGist() {
		ArrayList<String> filenames = new ArrayList<String>();
		ArrayList<String> fileSources = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("Gist description");
		String desc = input.nextLine();
		
		//for(int i = 0; i < 2; i++) {
			
			input.nextLine();
			System.out.println("Filename");
			filenames.add(input.nextLine());
			
			System.out.println("Code");
			fileSources.add(input.nextLine());
			
			
		//}
		
		encodeJSON(desc, filenames, fileSources);
		input.close();
	}
	
	//Tehdään syötteen pohjalta tarvittavat JSON-oliot
	public void encodeJSON(String desc, ArrayList<String> filename, ArrayList<String> code) {
		try {
			
			JSONObject obj = new JSONObject();

			obj.put("description", desc);
			obj.put("public", "false");
			
			//JSONObject combine = new JSONObject();
			for(int i = 0; i < filename.size(); i++) {
				JSONObject nestedfiles2 = new JSONObject();
				nestedfiles2.put("content", code.get(i));
				
				
				JSONObject nestedfiles = new JSONObject();
				nestedfiles.put(filename.get(i), nestedfiles2);
				
				obj.accumulate("files", nestedfiles);
			}
			
			
		
		


			System.out.print(obj);
			sendGist(obj);
		}
		catch(JSONException e) {}
		
	
	}
	
	public void sendGist(JSONObject obj) {
		/*
		String url = "https://api.github.com/gists";
		URLConnection connection1 = connection.formConnection(url, accessToken);
		connection1.setRequestProperty("POST", obj);*/
	
		
		URL url;
	    HttpURLConnection connection = null;  
		String data = obj.toString();
	    try {
			//url = new URL("https://api.github.com/gists");
		/*
			String accessToken = "ab2e7eeac02d4565dfd8816b233d8e336b90ab18";
	      connection = (HttpURLConnection)url.openConnection();
	      String authString = "token " + accessToken;
		 connection.setRequestProperty("Authorization", authString);
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	      StringEntity params = new StringEntity(data);
	      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

	      writer.write(data);
	      writer.flush();*/
	    	
	    	String accessToken = "615b87d48ce87d6563b93ef117c4aac23281f429";
		      
		      String authString = "token " + accessToken;
		      
	    	CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://api.github.com/gists");

			httpPost.addHeader("Authorization", authString);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			StringEntity params = new StringEntity(data);
			httpPost.setEntity(params);
			
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			
			System.out.println(response1.getProtocolVersion());
			System.out.println(response1.getStatusLine().getStatusCode());
			System.out.println(response1.getStatusLine().getReasonPhrase());
			System.out.println(response1.getStatusLine().toString());
			
			HttpEntity entity = response1.getEntity();
	        String responseString = EntityUtils.toString(entity, "UTF-8");
	        System.out.println(responseString);
	        response1.close();
			/*
			HttpEntity entity = response1.getEntity();
			
			String line = "";
			String response = "";
		    if (entity != null) {
		        //InputStream instream = entity.getContent();
		        try(BufferedReader br1 = new BufferedReader(new InputStreamReader(entity.getContent()))) {
		        	while ((line = br1.readLine()) != null) {
						response = response.concat(line);
					}
		        	System.out.println(response);
		        } 
		        catch(IOException e) {
		        	
		        }
		        finally {
		        	response1.close();
		        }*/
	    	
	    	/*
	    	String accessToken = "ab2e7eeac02d4565dfd8816b233d8e336b90ab18";
	    	 String authString = "token " + accessToken;
	    	
	    	HttpPost request = new HttpPost("https://api.github.com/gists");
	    	StringEntity params = new StringEntity(data);
	        request.addHeader("content-type", "application/x-www-form-urlencoded");
	        request.setEntity(params);
	        request.addHeader("Authorization", authString);
	        HttpResponse response = httpClient.execute(request);
			
	        HttpEntity entity = response.getEntity();
	        String responseString = EntityUtils.toString(entity, "UTF-8");
	        System.out.println(responseString);
			
			/*try(BufferedReader reader = new BufferedReader(new InputStreamReader(response))) {
				
			}*/
			
			
		
			
		//connection.setRequestProperty("Authorization", authString);
	 
	   	}
	    catch (Exception e) {}
	    finally {
	    	
	    }
	
}
}