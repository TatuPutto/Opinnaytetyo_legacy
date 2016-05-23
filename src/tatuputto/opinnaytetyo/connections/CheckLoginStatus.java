package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Tämä filtteri tarkistaa onko sessio vielä voimassa. 
 * Ohjataan käyttäjä takaisin kirjautumissivulle, jos sessiota ei löydy.
 */
//@WebFilter(servletNames = {"GetGists", "CreateGist"}, urlPatterns = {"/gists/*", "/create/*"}) //urlPatterns = ("/gists/*")
//TODO keksi parempi tapa estää pääsy kirjautumattomana muille sivuille kuin login ja forbidden
@WebFilter(urlPatterns = {
		"/Gists", 
		"/CreateGist", 
		"/EditGist", 
		"/DeleteGist", 
		"/jsps/Gists.jsp", 
		"/jsps/CreateNewGist.jsp",
		"/jsps/EditGist.jsp"
})
public class CheckLoginStatus implements Filter {
	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}
	
	/**
	 * Tarkistetaan löytyykö sessiota.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
      
		if(session.getAttribute("loggedIn") != null) {
			chain.doFilter(req, res);
		}
		else {
			res.sendRedirect("http://localhost:8080/Opinnaytetyo/jsps/Forbidden.jsp");
		}		
	}
}
