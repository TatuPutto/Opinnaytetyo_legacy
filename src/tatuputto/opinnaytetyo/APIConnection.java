package tatuputto.opinnaytetyo;

import java.net.URLConnection;
/**
 * 
 * @author Tatu Putto
 * Rajapintaluokka, jonka toteuttavat luokat muodostavat yhteyden GitHubin API:in eri tavoin.
 */
interface APIConnection {
	public URLConnection formConnection(String url, String accessToken);
}