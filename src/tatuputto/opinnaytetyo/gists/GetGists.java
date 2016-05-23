package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
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
import javax.servlet.http.HttpSession;

/**
 * 
 */
@WebServlet("/GetGists")
public class GetGists extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] responseContent;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String accessToken = (String)session.getAttribute("accessToken");
		String fetchMethod = request.getParameter("fetch");
		fetchMethod = fetchMethod == null ? "user" : fetchMethod;
		
		sendGetRequest(accessToken, fetchMethod);
		ArrayList<Gist> gists = new ParseMultipleGistsJSON().parseJSON(responseContent[2]);
		
		request.setAttribute("gists", gists);
		request.setAttribute("fetchMethod", fetchMethod);
		request.setAttribute("lastPage", responseContent[4]);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsps/Gists.jsp");
		rd.forward(request, response);
	}
	
	
	private void sendGetRequest(String accessToken, String fetchMethod) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
	
		//Haetaan käyttäjän gistit
		if(fetchMethod.equals("user")) {
			String url = "https://api.github.com/gists";
			responseContent = connection.formConnection("GET", url, "", accessToken);
			log("Käyttäjän gistit");
		}
		//Haetaan kaikkien käyttäjien julkisia gistejä uusimmista vanhimpiin
		else if(fetchMethod.equals("all")) {
			//String url = "https://api.github.com/gists/public?page=1&per_page=100";
			String url = "https://api.github.com/gists/public?page=1&per_page=30";
			responseContent = connection.formConnection("GET", url, "", accessToken);
			log("Kaikki gistit");
		}
	}
}
