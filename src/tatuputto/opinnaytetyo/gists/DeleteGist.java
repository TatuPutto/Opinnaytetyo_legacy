package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;


@WebServlet("/DeleteGist")
public class DeleteGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorizedConnection connection = new AuthorizedConnection();
		
		String gistId = request.getParameter("id");
		String accessToken = "c72276ff4963b622806d1f141b907b465106b4e8";
		String url = "https://api.github.com/gists/" + gistId;
		
		ArrayList<String> responseContent = connection.formConnection("DELETE", url, "", accessToken);
		//log("ID = " + gistId + " URL = " + url + " " + responseContent.get(0) + " " + responseContent.get(1));
		//TODO lähetä vastauskoodit
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
	}

}
