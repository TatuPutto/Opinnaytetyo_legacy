package tatuputto.opinnaytetyo.gists;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.GetGistFilesJSON;

@WebServlet("/GetSingleGist")
public class GetSingleGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetGistFilesJSON files = new GetGistFilesJSON();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		
		String[] responseContent;
		String gistId = request.getParameter("id");
		
		
		if(!gistId.equals(null) || !gistId.equals("")) {
			String url = "https://api.github.com/gists/" + gistId;
			HttpSession session = request.getSession(false);
			String accessToken = (String)session.getAttribute("accessToken");
			
			responseContent = AuthConnection.formConnection("GET", url, "", accessToken);
			
			
			
			String data = files.GetGistFiles(responseContent[2]).toString();
			
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
