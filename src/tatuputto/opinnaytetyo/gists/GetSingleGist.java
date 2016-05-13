package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;


@WebServlet("/GetSingleGist")
public class GetSingleGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseSingleGistJSON parse = new ParseSingleGistJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		
		String gistId = request.getParameter("id");
		String url = "https://api.github.com/gists/" + gistId;
		String accessToken = (String)request.getAttribute("accessToken");
		
		ArrayList<String> responseContent;
		Gist gist;
		
		//Jos accesstoken löytyy voidaan hakea julkisia ja salaisia gistejä
		if(accessToken != null && !accessToken.isEmpty()) {
			responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
			gist = parse.parseJSON(responseContent.get(2));
		}
		//Jos accesstokenia ei löydy voidaan hakea vain julkisia
		else {
			responseContent = UnauthConnection.formConnection("GET", url, "");
			gist = parse.parseJSON(responseContent.get(2));
		}
		
		
		request.setAttribute("gist", gist);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/EditGist.jsp");
		rd.forward(request, response);
	}
}
