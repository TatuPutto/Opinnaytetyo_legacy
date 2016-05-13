package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.GetGistFilesJSON;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetSingleGistAJAX")
public class GetSingleGistAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetGistFilesJSON files = new GetGistFilesJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		ArrayList<String> responseContent;
		String gistId = request.getParameter("id");
		
		
		if(!gistId.equals(null) || !gistId.equals("")) {
			String url = "https://api.github.com/gists/" + gistId;
			String accessToken = (String)request.getAttribute("accessToken");
			
			//Jos accesstoken löytyy voidaan hakea julkisia ja salaisia gistejä
			if(accessToken != null && !accessToken.isEmpty()) {
				responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
			}
			//Jos accesstokenia ei löydy voidaan hakea vain julkisia
			else {
				responseContent = UnauthConnection.formConnection("GET", url, "");
			}
			
			String data = files.GetGistFiles(responseContent.get(2)).toString();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			
		}
		else {
			response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Gistiä ei pystytty hakemaan.");	
		}		
	}
}
