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
 * Servlet Filter implementation class CheckSession
 */
@WebFilter(filterName = "CheckSession", urlPatterns = ("/*"))
public class CheckSession implements Filter {
    public CheckSession() {
    }

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
		String accessToken = "";

		
		if(req.getSession(false) != null) {
			HttpSession session = req.getSession(false);
			accessToken = (String)session.getAttribute("accessToken"); 
		}
		
		req.setAttribute("accessToken", accessToken);
		req.setAttribute("asd", "asd123");
		chain.doFilter(req, res);
	}

}
