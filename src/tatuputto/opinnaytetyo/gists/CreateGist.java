package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.EncodeJSON;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateGist")
public class CreateGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private	String[] responseContent;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String accessToken = (String)request.getSession(false).getAttribute("accessToken");
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

	
	private void sendPostData(String accessToken, String description, Boolean isPublic, String[] filenames, String[] sources) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		EncodeJSON encode = new EncodeJSON();
	
		String url = "https://api.github.com/gists";
		
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String data = encode.encodeJSONRequestPOST(description, isPublic, filenames, sources).toString();
		responseContent = connection.formConnection("POST", url, data, accessToken);
	
	}
}
