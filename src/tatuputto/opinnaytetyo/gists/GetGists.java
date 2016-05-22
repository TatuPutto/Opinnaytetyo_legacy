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
		/*
		
		//boolean loginStatus = (Boolean)session.getAttribute("loggedIn");
		String accessToken = (String)session.getAttribute("accessToken");
		log(accessToken);
		String fetchGists = request.getParameter("fetch");
		
		fetchGists = fetchGists == null ? "user" : fetchGists;
		
		//if(loginStatus) {
		sendGetRequest(accessToken, fetchGists);
		ArrayList<Gist> gists = new ParseMultipleGistsJSON().parseJSON(responseContent[2]);
		
		request.setAttribute("gists", gists);
		request.setAttribute("fetchMethod", fetchGists);
		request.setAttribute("lastPage", responseContent[4]);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsps/Gists.jsp");
		rd.forward(request, response);
	*/
		/*}
		else {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
		}*/
	}
	
	
	private void sendGetRequest(String accessToken, String fetchGists) {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
	
		//Haetaan käyttäjän gistit
		if(fetchGists.equals("user")) {
			String url = "https://api.github.com/gists";
			responseContent = connection.formConnection("GET", url, "", accessToken);
			log("Käyttäjän gistit");
		}
		//Haetaan kaikkien käyttäjien julkisia gistejä uusimmista vanhimpiin
		else if(fetchGists.equals("all")) {
			String url = "https://api.github.com/gists/public?page=1&per_page=100";
			responseContent = connection.formConnection("GET", url, "", accessToken);
			log("Kaikki gistit");
		}
		
		/*//Jos accesstokenia ei l�ytdy muodostetaan yhteys anonyymin� -> haetaan muiden k�ytt�jien julkisia gistej�
		else {
			responseContent = UnauthConnection.formConnection("GET", urlGetAll, "");
			//gists = parse.parseJSON(responseContent.get(2));
			log("Kaikki gistit - else");
		}*/
	}
}
