package twitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * TwitterHandler class is responsible for uploading and deleting tweets for Risk game
 * @author Grant Williams
 * 
 * @date 10/12/18
 **/

public class TwitterHandler {
	
	private static String consumerKeyStr = "XkfwxXM3s61NM0ofhnMs2qRXv";
	private static String consumerSecretStr = "C1F1JUuReT3rftv4SqCxubWOlo67NkDus8vqiiShgyyIGeL4mY";
	private static String accessTokenStr = "1050478756991787015-nURgfJuD6HtyCjLjMjuleb0frGs7Ra";
	private static String accessTokenSecretStr = "oUGCAnlJ3gJHHtBXhmpgjY0NvZrqsJ08l6R18TpimGBiv";
	private Twitter twitterHandler;

	public TwitterHandler(){
		twitterHandler = new TwitterFactory().getInstance();
	
		twitterHandler.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);
	
		twitterHandler.setOAuthAccessToken(accessToken);
	}
	
	public void postTweet(String tweet){
		try{
			twitterHandler.updateStatus(tweet);
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}
	
	public void deleteTweets(){
		try {
			List<Status> status = twitterHandler.getUserTimeline();
			for(Status st: status){
				twitterHandler.destroyStatus(st.getId());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
