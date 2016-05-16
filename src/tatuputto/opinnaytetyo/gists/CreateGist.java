package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.EncodeJSON;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateGist")
public class CreateGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String accessToken = (String)request.getAttribute("accessToken");
		String description = request.getParameter("description");
		Boolean isPublic =  Boolean.parseBoolean(request.getParameter("ispublic"));
		String[] filenames = request.getParameterValues("filenames[]");
		String[] sources = request.getParameterValues("sources[]");
		
		ArrayList<String> responseContent = sendPostData(accessToken, description, isPublic, filenames, sources);
		
		//Lähetetään vastauskoodi
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(0) + ", " + responseContent.get(1));		
	}

	
	private ArrayList<String> sendPostData(String accessToken, String description, Boolean isPublic, String[] filenames, String[] sources) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		EncodeJSON encode = new EncodeJSON();
		
		ArrayList<String> responseContent;
		String url = "https://api.github.com/gists";
		
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String data = encode.encodeJSONRequestPOST(description, isPublic, filenames, sources).toString();
		
		//Jos accesstoken l�ytyy muodostetaan yhteys auktorisoituna k�ytt�j�n� -> haetaan k�ytt�j�n gistit(julkiset ja salaiset)
		if(accessToken != null && !accessToken.isEmpty()) {
			responseContent = connection.formConnection("POST", url, data, accessToken);
		}
		//Jos accesstokenia ei löydy muodostetaan yhteys anonyyminä-> haetaan muiden k�ytt�jien julkisia gistej�
		else {
			responseContent = UnauthConnection.formConnection("POST", url, data);
		}
		
		return responseContent;
	}
}
