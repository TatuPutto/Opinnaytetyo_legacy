package tatuputto.opinnaytetyo;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		String accessToken = "8c12e3f78956b6b03e57d10a100676d3726e8f77";
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
