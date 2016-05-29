package tatuputto.opinnaytetyo.gists;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;

/**
 * Gistin poistaminen.
 */
@WebServlet("/DeleteGist")
public class DeleteGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Lähetetään poistamispyyntö valitusta gististä.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accessToken = (String)request.getSession(false).getAttribute("accessToken");
		//Haetaan pyynnöstä poistettavan gistin id
		String gistId = request.getParameter("id");
	
		String url = "https://api.github.com/gists/" + gistId;
		//Lähetään gistin poistamispyyntö
		//String[] responseContent = new AuthorizedConnectionOauth().formConnection("DELETE", url, "", accessToken);
		new AuthorizedConnectionOauth().formConnection("DELETE", url, "", accessToken);
		
		response.sendRedirect("http://localhost:8080/Opinnaytetyo/gists");
	}

}
