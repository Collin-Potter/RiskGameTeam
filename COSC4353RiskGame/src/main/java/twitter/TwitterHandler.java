package twitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import main.Helper;
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
	private Helper helper;
	private BufferedReader br;
	private FileReader fr;

	public TwitterHandler(){
		twitterHandler = new TwitterFactory().getInstance();
		helper = new Helper();
		String in = "";
		userInput = new Scanner(System.in);
		
		/*
		//Get private Twitter info from user
		System.out.println("Enter Consumer Key for Twitter API: ");
		in = userInput.nextLine();
		consumerKeyStr = in;
		System.out.println(helper.daBar);
		System.out.println("Enter Consumer Secret Key for Twitter API: ");
		in = userInput.nextLine();
		consumerSecretStr = in;
		System.out.println(helper.daBar);
		System.out.println("Enter Access Token for Twitter API: ");
		in = userInput.nextLine();
		accessTokenStr = in;
		System.out.println(helper.daBar);
		System.out.println("Enter Access Token Secret for Twitter API: ");
		in = userInput.nextLine();
		accessTokenSecretStr = in;
		System.out.println(helper.daBar);
		*/
		
		//Get Twitter credentials
		readInCredentials("C:\\Users\\grant\\git\\RiskGameGrant\\COSC4353RiskGame\\src\\main\\java\\twitter\\twitterCredentials");
		
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
	
	private void readInCredentials(String filename){
		try{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = null;
			int count = 0;
			while((line = br.readLine()) != null){
				if(count == 0){
					consumerKeyStr = line;
				}
				if(count == 1){
					consumerSecretStr = line;
				}
				if(count == 2){
					accessTokenStr = line;
				}
				if(count == 3){
					accessTokenSecretStr = line;
				}
				count++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br != null){
					br.close();
				}
				if(fr != null){
					fr.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
