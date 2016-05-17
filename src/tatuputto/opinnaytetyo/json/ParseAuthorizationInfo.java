package tatuputto.opinnaytetyo.json;


import org.json.JSONException;
import org.json.JSONObject;

import tatuputto.opinnaytetyo.gists.GistOwner;

public class ParseAuthorizationInfo {
	
	public GistOwner parseJSON(String JSONresponse) {
		try {
			JSONObject authorizationInfo = new JSONObject(JSONresponse);
			JSONObject userInfo = authorizationInfo.getJSONObject("user"); 
			
			return parseUserInfo(userInfo);
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public GistOwner parseUserInfo(JSONObject userInfo) {
		try {
			String login = userInfo.getString("login");
			String avatarUrl = userInfo.getString("avatar_url");
			
			GistOwner user = new GistOwner(login, avatarUrl);  
		    return user;
		}	
		catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
}
