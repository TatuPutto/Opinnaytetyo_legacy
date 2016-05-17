package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.gists.GistOwner;
import tatuputto.opinnaytetyo.json.ParseAuthorizationInfo;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseAuthorizationInfo parse = new ParseAuthorizationInfo();
		
		Cookie cookie = null;
		Cookie[] cookies = null;
	    cookies = request.getCookies();
	    String accessToken = "";
	    boolean tokenCookieFound = false;
	    boolean validAuthorization = false;
		
	   
	    //Tarkistetaan l�ytyyk� accesstoken ev�stett�
	    if(cookies != null) {
	    	for (int i = 0; i < cookies.length; i++) {
	    		cookie = cookies[i];
	    		
	    		if(cookie.getName().equals("accesstoken")) {
	    			log("Cookie: " + cookie.getName() + " Value: " + cookie.getValue());
	    			tokenCookieFound = true;
	    			accessToken = cookie.getValue();
	    			
	    			break;
	    		}	
	    	}
	    	
	    	if(!tokenCookieFound) {
	    		response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    	}
	    	//Jos ev�ste l�ytyi, tarkistetaan onko access token viel� voimassa
	    	else {
	    		ArrayList<String> responseContent = checkAuthorization(accessToken);	
	    		
	    		//Jos ev�ste on voimassa, aloitetaan sessio ja asetetaan ev�steen arvo sessiomuuttujaksi
	    		if(!responseContent.get(0).equals("404")) {
	    			log("Access token on voimassa.");
	    			
	    			GistOwner user = parse.parseJSON(responseContent.get(2));
	    			
	    			HttpSession session = request.getSession(true);
	    			session.setAttribute("username", user.getLogin());
	    			session.setAttribute("avatar", user.getAvatarUrl());
	    			session.setAttribute("accessToken", accessToken);

	    			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
	    		}
	    		//Jos ei ole voimassa, poistetaan vanha ev�ste ja l�hetet��n k�ytt�j� tekem��n uusi auktorisointi
	    		else {
	    			log("Access token ei ole enää voimassa.");
	    			
	    			Cookie accessTokenCookie = new Cookie("accesstoken", "");
	    			accessTokenCookie.setMaxAge(0); 
	    	   		response.addCookie(accessTokenCookie);
	    			
	    			response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    		}
	    	}    	
	    }
	    //Jos sessiota eik� ev�stett� l�ydy, l�hetet��n k�ytt�j� tunnistautumaan githubin v�lityksell�
	    //https://developer.github.com/v3/oauth/#web-application-flow
	    else {
	    	response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    }
	}
	
	
	//Selvit��n onko access token voimassa
	private ArrayList<String> checkAuthorization(String accessToken) {
		AuthorizedConnectionBasic connection = new AuthorizedConnectionBasic();
		
		String id = GetAccessToken.clientId;
		String secret = GetAccessToken.clientSecret;
		String url = "https://api.github.com/applications/" + id + "/tokens/" + accessToken + "";
		
		return connection.formConnection("GET", url, "", id, secret);

	}
	
}
