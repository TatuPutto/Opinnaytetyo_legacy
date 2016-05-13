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
		EncodeJSON encodejson = new EncodeJSON();
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		
		String description = request.getParameter("description");
		Boolean isPublic =  Boolean.parseBoolean(request.getParameter("ispublic"));
		String[] filenames = request.getParameterValues("filenames[]");
		String[] sources = request.getParameterValues("sources[]");
		
		ArrayList<String> responseContent;
		String url = "https://api.github.com/gists";
		String accessToken = (String)request.getAttribute("accessToken");
		
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String data = encodejson.encodeJSONRequestPOST(description, isPublic, filenames, sources).toString();
		
		//Jos accesstoken löytyy muodostetaan yhteys auktorisoituna käyttäjänä -> haetaan käyttäjän gistit(julkiset ja salaiset)
		if(accessToken != null && !accessToken.isEmpty()) {
			responseContent = connection.formConnection("POST", url, data, accessToken);
		}
		//Jos accesstokenia ei löytdy muodostetaan yhteys anonyyminä -> haetaan muiden käyttäjien julkisia gistejä
		else {
			responseContent = UnauthConnection.formConnection("POST", url, data);
		}
		
		
		//Lähetetään pyynnön vastauskoodi
		//lisäys onnistui: 201 - CREATED
		//ei onnistunut: 401 - Unauthorized / 422 - Unprocessable Entity 
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(0) + ", " + responseContent.get(1));
		
	}

}
