package tatuputto.opinnaytetyo.gists;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;


@WebServlet("/EditGist")
public class EditGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseSingleGistJSON parse = new ParseSingleGistJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		HttpSession session = request.getSession(false);
		
		String gistId = request.getParameter("id");
		String url = "https://api.github.com/gists/" + gistId;
		String accessToken = (String)session.getAttribute("accessToken");
		int userId = (int)session.getAttribute("userId");
		
		String[] responseContent;
		Gist gist = null;
		
		
		
		responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
		gist = parse.parseJSON(responseContent[2]);
		
		//Tarkistetaan onko kirjautunut käyttäjä gistin omistaja
		if(gist.getOwner().getId() == userId) {
			request.setAttribute("gist", gist);
			request.setAttribute("id", gistId);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsps/EditGist.jsp");
			rd.forward(request, response);
		}
		//Jos ei ole lähetetään vastauskoodi 403 - forbidden
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sinulla ei ole oikeuksia muokata tätä gistiä.");
		}
		
		
		
	}
}
