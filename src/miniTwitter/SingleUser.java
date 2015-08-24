package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class SingleUser implements User {
	
	private String userId;
	private List<SingleUser> followers;
	private List<SingleUser> following;
	private List<String> newsfeed;
	
	public SingleUser(String userId) {
		this.userId = userId;
		followers = new ArrayList<SingleUser>();
		following = new ArrayList<SingleUser>();
		newsfeed = new ArrayList<String>();
	}
	
	public void addFollower(SingleUser user) {
		followers.add(user);
	}

	public String getID() {
		return userId;
	}

	public List<SingleUser> getFollowers() {
		return followers;
	}

	public List<SingleUser> getFollowing() {
		return following;
	}


	public List<String> getNewsfeed() {
		return newsfeed;
	}

	public void setNewsfeed(List<String> newsfeed) {
		this.newsfeed = newsfeed;
	}
	
	public String toString() {
	    return this.userId;
	}
		
	
}
