package miniTwitter;

import java.util.List;

public interface User extends VisitorElement {
	
	public String getID();
	
	public UserPanel getUserPanel();

	public List<User> getFollowers();

	public List<User> getFollowing();

	public List<Tweet> getNewsfeed();
	
	public void setUserPanel(UserPanel p);
	
	public void addToNewsfeed(Tweet t);
	
	public void addFollower(User u);
	
	public void followUser(User u);
	
	
	

}
