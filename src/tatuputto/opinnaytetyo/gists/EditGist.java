package tatuputto.opinnaytetyo.gists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.EncodeJSON;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;


@WebServlet("/EditGist")
public class EditGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accessToken = (String)request.getAttribute("accessToken");
	
		//Luetaan pyynnön mukana lähettetty JSON
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonData = "";
        if(br != null){
        	jsonData = br.readLine();
        }
        
        
        ArrayList<String> responseContent = sendPatchData(response, accessToken, jsonData);
       
        response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(0) + ", " + responseContent.get(1));
     
	}
	
	
	private ArrayList<String> sendPatchData(HttpServletResponse response, String accessToken, String jsonData) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		ParseSingleGistJSON parse = new ParseSingleGistJSON();
		
		String gistId = parse.parseJSON(jsonData).getId();
		String url = "https://api.github.com/gists/" + gistId;
		ArrayList<String> responseContent = new ArrayList<String>();
		
		//Jos access token löytyy, lähetetään muokkauspyyntö
		if(accessToken != null && !accessToken.isEmpty()) {
			responseContent = connection.formConnection("PATCH", url, jsonData, accessToken);
		}
		//Jos ei löydy, lähetetään vastauskoodi 401 - Unauthorized ja lopetetaan muokkausprosessi
		else {
			responseContent.add("401");
			responseContent.add("Unauthorized");
		}
		
		return responseContent;
	}

}
