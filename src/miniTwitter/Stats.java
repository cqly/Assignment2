package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class Stats implements Visitor {
	
	private int totalSingleUser = 0;
	private int totalGroupUser = 0;
	private int totalMessage = 0;
	private int totalPossitiveTweet = 0;
	private List<String> positiveKeywords;
	
	public Stats() {
		
		//set some default keyword
		positiveKeywords = new ArrayList<String>();
		positiveKeywords.add("good");
		positiveKeywords.add("nice");
	}
	
	public void setPositiveKeywords(List<String> kw) {
		positiveKeywords = new ArrayList<String>(kw);
	}
	
	@Override
	public void addSingleUser(SingleUser su) {
		totalSingleUser += 1;
	}

	@Override
	public void addGroupUser(GroupUser gu) {
		totalGroupUser += 1;
	}

	@Override
	public void addTotalMessage(TextTweet tt) {
		totalMessage += 1;
	}

	@Override
	public void addPossitiveTweet(TextTweet tt) {
		
		for (String s : positiveKeywords) {
			if (tt.getMessage().toLowerCase().contains(s.toLowerCase())) {
				totalPossitiveTweet += 1;
			}
		}
	}
	
	public int getTotalSingleUser() {
		return totalSingleUser;
	}
	
	public int getTotalGroupUser() {
		return totalGroupUser;
	}
	
	public int getTotalMessages() {
		return totalMessage;
	}
	
	public int getTotalPossitiveTweet() {
		return totalPossitiveTweet;
	}
}
