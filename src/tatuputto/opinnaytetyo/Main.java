package tatuputto.opinnaytetyo;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		String accessToken = "ab3c91435e4fadb492e9c570ee58959a514b6c86";
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		GetGists getGists = new GetGists();
		GetGistSource getGistSource = new GetGistSource();
		
		//getGists.getGists(accessToken);
		
		//gists = getGists.getGists(accessToken);
		//System.out.println(getGistSource.getSource(gists.get(1).getRawUrl(), accessToken));
		
		
		//CreateGist createGist = new CreateGist();
		//createGist.createNewGist();
		
		
		GetSingleGist getSingleGist = new GetSingleGist();
		System.out.println(getSingleGist.getGist(accessToken, "1682557972e98f15f0d034eecb6a62f9"));
		
		//System.out.println(getGists.getGists(accessToken));
	}
}
