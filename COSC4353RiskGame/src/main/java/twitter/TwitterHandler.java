
import java.util.List;
import java.util.Scanner;

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
	
	private static String consumerKeyStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private static String consumerSecretStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private static String accessTokenStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private static String accessTokenSecretStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private Twitter twitterHandler;
	private Scanner userInput;

	public TwitterHandler(){
		twitterHandler = new TwitterFactory().getInstance();
		String in = "";
		userInput = new Scanner(System.in);
		
		//Get private Twitter info from user
		System.out.println("Enter Consumer Key for Twitter API: ");
		in = userInput.nextLine();
		consumerKeyStr = in;
		System.out.println("Enter Consumer Secret Key for Twitter API: ");
		in = userInput.nextLine();
		consumerSecretStr = in;
		System.out.println("Enter Access Token for Twitter API: ");
		in = userInput.nextLine();
		accessTokenStr = in;
		System.out.println("Enter Access Token Secret for Twitter API: ");
		in = userInput.nextLine();
		accessTokenSecretStr = in;
		
		//Authenticate user
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
			while(!twitterHandler.getUserTimeline().isEmpty()){
				List<Status> status = twitterHandler.getUserTimeline();
				for(Status st: status){
					twitterHandler.destroyStatus(st.getId());
				}
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
