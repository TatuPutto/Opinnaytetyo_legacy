package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.connections.UnauthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseJSONForAJAXResponse;
import tatuputto.opinnaytetyo.json.ParseMultipleGistsJSON;

/**
 * Servlet implementation class GetMoreGists
 */
@WebServlet("/GetMoreGists")
public class GetMoreGists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ParseMultipleGistsJSON parse = new ParseMultipleGistsJSON();
		ParseJSONForAJAXResponse parse = new ParseJSONForAJAXResponse();
		AuthorizedConnectionOauth AuthConnection = new AuthorizedConnectionOauth();
		
	
		String pageNum = request.getParameter("page");
		log("PageNumber: " + pageNum);
		
		
		
		ArrayList<Gist> gists;
		
		/*
		 * The pagination info is included in the Link header. 
		 * It is important to follow these Link header values instead of constructing your own URLs. 
		 * In some instances, such as in the Commits API, pagination is based on SHA1 and not on page number.
		 * Link: <https://api.github.com/user/repos?page=3&per_page=100>; rel="next",
  		 * 		<https://api.github.com/user/repos?page=50&per_page=100>; rel="last"
		 */
		String url = "https://api.github.com/gists/public?page=2&per_page=100";

		HttpSession session = request.getSession(false);
		String accessToken = (String)session.getAttribute("accessToken");
		
		

	
		String[] responseContent = AuthConnection.formConnection("GET", url, "", "e20b425a981e16204e6bea7ac7e79dad329a70bc");
		
		String data = "";

		JSONArray arr = parse.parseJSON(responseContent[2]);
		if(arr.length() > 0) {
			log("" + arr);
			data = arr.toString();
		}
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(data);
	}

	
}
