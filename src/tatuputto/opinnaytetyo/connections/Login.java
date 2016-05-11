package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = null;
		Cookie[] cookies = null;
	    cookies = request.getCookies();
		
	    /*//Jos sessio on jo olemassa ohjataan käyttäjä takaisin etusivulle
	    if(request.getSession(false) != null) {
	    	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
	    }*/
	    //Jos accesstokenin sisältävä eväste löytyy, aloitetaan uusi sessio
	    if(cookies != null){
	    	for (int i = 0; i < cookies.length; i++){
	    		cookie = cookies[i];
	    		
	    		if(cookie.getName().equals("accesstoken")) {
	    			HttpSession session = request.getSession(true);
	    			session.setAttribute("accessToken", cookie.getValue());
	    			
	    			break;
	    		}
	    	}
	    	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
	    }
	    //Jos sessiota eikä evästettä löydy, lähetetään käyttäjä tunnistautumaan githubin välityksellä
	    //https://developer.github.com/v3/oauth/#web-application-flow
	    else {
	    	response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    }
		
		
		
		
		
	}

}
