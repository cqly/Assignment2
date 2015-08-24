package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class GroupUser implements User {

	private String groupID;
	private List<User> followers;
	private List<User> following;
	private List<TextTweet> newsfeed;
	private List<User> userList;
	
	public GroupUser(String groupId) {
		
		this.groupID = groupId;
		followers = new ArrayList<User>();
		following = new ArrayList<User>();
		newsfeed = new ArrayList<TextTweet>();
		userList = new ArrayList<User>();
	}
	
	@Override
	public String getID() {
		return groupID;
	}

	@Override
	public List<User> getFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFollowing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TextTweet> getNewsfeed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addUser(User user) {
		userList.add(user);
	}
	
	public List<User> getUsersInGroup() {
		return userList;
	}
	
	public String toString() {
		return "[GROUP] " + groupID;
	}

	@Override
	public void postTextTweet(TextTweet t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFollower(User u) {
		// TODO Auto-generated method stub
		
	}

}
