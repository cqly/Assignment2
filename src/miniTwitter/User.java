package miniTwitter;

import java.util.List;

public interface User {
	
	public String getID();

	public List<User> getFollowers();

	public List<User> getFollowing();

	public List<TextTweet> getNewsfeed(); 
	
	public void postTextTweet(TextTweet t);
	
	public void addFollower(User u);
	
	public void followUser(User u);
	
	
	

}
