package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
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
		AuthorizedConnection AuthConnection = new AuthorizedConnection();
		UnauthorizedConnection UnauthConnection = new UnauthorizedConnection();
		
		String gistId = request.getParameter("id");
		String url = "https://api.github.com/gists/" + gistId;
		String accessToken = (String)request.getAttribute("accessToken");
		
		ArrayList<String> responseContent;
		Gist gist;
		
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

}
