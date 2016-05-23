package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CheckSession
 */
//@WebFilter(servletNames = {"GetGists", "CreateGist"}, urlPatterns = {"/gists/*", "/create/*"}) //urlPatterns = ("/gists/*")
//TODO keksi parempi tapa estää pääsy kirjautumattomana muille sivuille kuin login ja forbidden
@WebFilter(urlPatterns = {
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
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
      
		if(session.getAttribute("userId") != null) {
			//session.setAttribute("loggedIn", true);
			chain.doFilter(req, res);
		}
		else {
			res.sendRedirect("http://localhost:8080/Opinnaytetyo/jsps/Forbidden.jsp");
		}
		
		
		
	}

}
