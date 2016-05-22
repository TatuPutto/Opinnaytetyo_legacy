package tatuputto.opinnaytetyo.json;


import org.json.JSONException;
import org.json.JSONObject;

import tatuputto.opinnaytetyo.gists.User;

public class ParseAuthorizationInfo {
	
	public User parseJSON(String JSONresponse) {
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
	
	
	public User parseUserInfo(JSONObject userInfo) {
		try {
			int id = userInfo.getInt("id");
			String login = userInfo.getString("login");
			String avatarUrl = userInfo.getString("avatar_url");
			
			User user = new User(id, login, avatarUrl);  
		    return user;
		}	
		catch (JSONException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
}
