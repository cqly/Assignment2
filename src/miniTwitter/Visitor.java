package miniTwitter;

public interface Visitor {

	public void addSingleUser(SingleUser su);
	
	public void addGroupUser(GroupUser gu);
	
	public void addTotalMessage(TextTweet tt);
	
	public void addPossitiveTweet(TextTweet tt);
	
}
