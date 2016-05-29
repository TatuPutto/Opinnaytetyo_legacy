package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.EncodeJSON;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gistin luominen.
 */
@WebServlet("/CreateGist")
public class CreateGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private	String[] responseContent;
 
	/**
	 * Otetaan luotavan gistin tiedot pyynnöstä, käsitellään tiedot ja lähetetään ne eteenpäin.
	 * Lähetetään vastauksena luontipyynnön vastauskoodi, esim 201 Created, jos gistin luominen onnistui.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String accessToken = (String)request.getSession(false).getAttribute("accessToken");
		
		//Otetaan luotavan gistin tiedot pyynnöstä
		String description = request.getParameter("description");
		Boolean isPublic =  Boolean.parseBoolean(request.getParameter("ispublic"));
		String[] filenames = request.getParameterValues("filenames[]");
		String[] sources = request.getParameterValues("sources[]");
		
		sendPostData(accessToken, description, isPublic, filenames, sources);
		
		//Lähetetään vastauskoodi
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent[0] + ", " + responseContent[1]);		
	}

	
	/**
	 * Käännetään tiedot JSON-muotoon ja lähetetään ne eteenpäin GitHubiin yhteyden muodostavalle luokalle.
	 * @param accessToken Käyttäjän access token.
	 * @param description Gistin kuvaus
	 * @param isPublic Onko gist julkinen vai salainen.
	 * @param filenames Tiedostonimet.
	 * @param sources Tiedostojen lähdekoodit.
	 */
	private void sendPostData(String accessToken, String description, Boolean isPublic, String[] filenames, String[] sources) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		EncodeJSON encode = new EncodeJSON();
		
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String data = encode.encodeJSONRequestPOST(description, isPublic, filenames, sources).toString();
		String url = "https://api.github.com/gists";
		responseContent = connection.formConnection("POST", url, data, accessToken);
	
	}
}
