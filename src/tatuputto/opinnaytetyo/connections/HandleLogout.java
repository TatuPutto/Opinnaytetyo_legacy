package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Tämä luokka hoitaa uloskirjautumisen.
 */
@WebServlet("/HandleLogout")
public class HandleLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * Tuhotaan sessio ja poistetaan eväste.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//Tuhotaan sessio
			HttpSession session = request.getSession(false);
			session.invalidate();
			
			//Poistetaan access tokenin sisältävä eväste
			Cookie tokenCookie = new Cookie("accesstoken", "");
			tokenCookie.setMaxAge(0);
			response.addCookie(tokenCookie);
			
			response.sendRedirect("http://localhost:8080/Opinnaytetyo/jsps/Login.jsp");
		}
	}
}
