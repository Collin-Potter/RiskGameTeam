package BaseGameEssentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * BaseGameEssentials.TwitterHandler class is responsible for uploading and deleting tweets for Risk game
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
	private BufferedReader br;
	private FileReader fr;

	public TwitterHandler(){
		twitterHandler = new TwitterFactory().getInstance();
		String in = "";
		userInput = new Scanner(System.in);

		//Get Twitter credentials
        readInCredentials("/src/main/java/BaseGameEssentials/TestTwitter");
		
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
	/**	try {
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
		}**/
	}
	
	private void readInCredentials(String filepath){
		try{
			File currentDir = new File(".");
	        File parentDir = currentDir.getAbsoluteFile();
	        File newFile = new File(parentDir + filepath);
	        fr = new FileReader(newFile);
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
		}
		finally{
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
