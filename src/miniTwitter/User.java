package miniTwitter;

import java.util.List;

public interface User {
	
	public String getID();

	public List<SingleUser> getFollowers();

	public List<SingleUser> getFollowing();

	public List<TextTweet> getNewsfeed(); 
	
	
	
	
	

}
