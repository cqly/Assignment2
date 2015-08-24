package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class SingleUser implements User {
	
	private String userId;
	private List<User> followers;
	private List<User> following;
	private List<TextTweet> newsfeed;
	
	public SingleUser(String userId) {
		this.userId = userId;
		followers = new ArrayList<User>();
		following = new ArrayList<User>();
		newsfeed = new ArrayList<TextTweet>();
		
	}
	
	public void addFollower(SingleUser user) {
		followers.add(user);
	}

	public String getID() {
		return userId;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public List<User> getFollowing() {
		return following;
	}

	public List<TextTweet> getNewsfeed() {
		return newsfeed;
	}
	

	public void setNewsfeed(List<TextTweet> newsfeed) {
		this.newsfeed = newsfeed;
	}
	
	public String toString() {
	    return this.userId;
	}
	
	@Override
	public boolean equals(Object o){
		
		User user = (User) o;		        
        return this.userId.equalsIgnoreCase(user.getID());
    }

	@Override
	public void postTextTweet(TextTweet tweet) {
		this.newsfeed.add(tweet);
		
	}
	
	public void followUser(User user) {
		this.following.add(user);
		user.addFollower(this);
		
	}
		
	public void addFollower(User user) {
		this.followers.add(user);
	}
}
