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

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
import tatuputto.opinnaytetyo.json.EncodeJSON;


@WebServlet("/EditGist")
public class EditGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorizedConnection connection = new AuthorizedConnection();
		EncodeJSON encodejson = new EncodeJSON();
		
		String accessToken = "f08ced82cc79020c2e3e992516421db6557e0f64";
		String url = "https://api.github.com/gists/f99404b40846ae22ca4b0df222bc5d7a";
		
		/*try {
			
			JSONObject jObj = new JSONObject(request.getParameter("files"));
			
			
			log(""+jObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
		
        
        log(json);
       ArrayList<String> responseContent = connection.formConnection("PATCH", url, json, accessToken);
		
		
		//Lähetetään pyynnön vastauskoodi
		//lisäys onnistui: 201 - CREATED
		//ei onnistunut: 401 - Unauthorized / 422 - Unprocessable Entity 
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(0) + ", " + responseContent.get(1));
		
	}

}
