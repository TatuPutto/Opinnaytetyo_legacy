package tatuputto.opinnaytetyo;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		String accessToken = "c6a07d2fce28b39405409ce97513a4d8c605b9f4";
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		GetGists getGists = new GetGists();
		GetGistSource getGistSource = new GetGistSource();
		
		//getGists.getGists(accessToken);
		
		//gists = getGists.getGists(accessToken);
		//System.out.println(getGistSource.getSource(gists.get(1).getRawUrl(), accessToken));
		
		
		CreateGist createGist = new CreateGist();
		createGist.createNewGist();
	}
}
