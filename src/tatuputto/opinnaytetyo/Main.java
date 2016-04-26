package tatuputto.opinnaytetyo;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		String accessToken = "ab2e7eeac02d4565dfd8816b233d8e336b90ab18";
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		GetGists getGists = new GetGists();
		GetGistSource getGistSource = new GetGistSource();
		
		gists = getGists.getGistsThroughAPI(accessToken);
		System.out.println(getGistSource.getSource(gists.get(0).getRawUrl()));
	}
}
