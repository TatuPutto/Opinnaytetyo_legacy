package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseMultipleGistsJSON;
import tatuputto.opinnaytetyo.gists.Gist;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
@WebServlet("/GetGists")
public class GetGists extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseMultipleGistsJSON parse = new ParseMultipleGistsJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		
		ArrayList<String> responseContent;
		ArrayList<Gist> gists;
		
		String url = "https://api.github.com/gists";
		String accessToken = (String)request.getAttribute("accessToken");
		
		//Jos accesstoken löytyy muodostetaan yhteys auktorisoituna käyttäjänä -> haetaan käyttäjän gistit(julkiset ja salaiset)
		if(accessToken != null && !accessToken.isEmpty()) {
			responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
			gists = parse.parseJSON(responseContent.get(2));
		}
		//Jos accesstokenia ei löytdy muodostetaan yhteys anonyyminä -> haetaan muiden käyttäjien julkisia gistejä
		else {
			responseContent = UnauthConnection.formConnection("GET", url, "");
			gists = parse.parseJSON(responseContent.get(2));
		}
			
			
		request.setAttribute("gists", gists);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListGists.jsp");
		rd.forward(request, response);
	}
}
