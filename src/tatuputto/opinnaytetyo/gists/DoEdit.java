package tatuputto.opinnaytetyo.gists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseGistEditJSON;


@WebServlet("/DoEdit")
public class DoEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] responseContent;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String accessToken = (String)session.getAttribute("accessToken");
	
		//Luetaan pyynnön mukana lähettetty JSON
		try(BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
			String jsonData = "";
			if(br != null){
				jsonData = br.readLine();
			}

			sendPatchData(response, accessToken, jsonData);

			response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseContent[0] + ", " + responseContent[1]);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void sendPatchData(HttpServletResponse response, String accessToken, String jsonData) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		ParseGistEditJSON parse = new ParseGistEditJSON();
		
		String gistId= parse.parseJSON(jsonData);
		log(gistId);
		
		String url = "https://api.github.com/gists/" + gistId;
		
		responseContent = connection.formConnection("PATCH", url, jsonData, accessToken);
		log(responseContent[0] + ", " + responseContent[1]);

	}

}
