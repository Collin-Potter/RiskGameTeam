package BaseGameEssentials;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static BaseGameEssentials.Game.*;


public class TelegramBot extends TelegramLongPollingBot {

	private static String botKeyStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private BufferedReader br;
	private FileReader fr;

	public TelegramBot(){
		//Get TelegramBot credentials
		readInCredentials("/src/main/java/BaseGameEssentials/telegramBotToken");
	}
	public static ArrayList<String> temp = new ArrayList<String>();
    public static String message_text = new String();
    public int i = 0;
    public boolean gameOn = false;
    Random rand = new Random();
	String  Game_ID = Integer.toString((rand.nextInt(5000) + 1000));
    public static Update update2;

	@Override
	public void onUpdateReceived(Update update) {

        // receiving commands
		update2 = update;
		String command = update.getMessage().getText();
		if(command.equals("/start") || command.equals("/join@CSGSanaz_bot")) {
			translateMessage(command);
		}else if(gameOn==true){ParsTranslate(command);}

	}

	@Override
	public String getBotUsername() {
		// Return bot username
		// If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
		return "CSGSanaz_bot";
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return botKeyStr;
	}

	//Read in BotToken
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
					botKeyStr = line;
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

    //Method to Translate the initial commands and to take actions respectively
	public void translateMessage(String input) {

		if(input.equals("/start")){
			String playerName=update2.getMessage().getFrom().getFirstName();
			playerList.add(new Player(35,playerName,false,false, (i)));	i++;
			message_text = "Welcome To Risk "+ playerName +" you are our first player. We need two more players to play the game." +
					"If you want to play send /join " +	"command to become a player.";
			long chat_id = update2.getMessage().getChatId();
			SendMessage message = new SendMessage() // Create a message object object
					.setChatId(chat_id)
					.setText(message_text);
			try {
				execute(message); // Sending our message object to user
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

		if(input.equals("/join@CSGSanaz_bot")&& playerList.size()!= 3) {
			String playerName = update2.getMessage().getFrom().getFirstName();
			i++;
			boolean goodToGo = false;
			//ensuring the same player does not enroll to the game more than once
				if (playerList.size()==1 && !playerName.equals(playerList.get(0).getTeam())) {
					goodToGo = true;
				}else if(playerList.size()==2 && !playerName.equals(playerList.get(0).getTeam()) && !playerName.equals(playerList.get(1).getTeam())) {
					goodToGo = true;
				}
			if (goodToGo == true) {
				playerList.add(new Player(35, playerName, false, false, (i)));
				System.out.println(playerName + " is a player now");
				message_text = playerName + " you are a Risk player now .";
				long chat_id = update2.getMessage().getChatId();
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id)
						.setText(message_text);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				if (playerList.size() == 3) {
					gameOn = true;
					message_text = " Dividing territories among players, Reinforcement will begin soon .";
					SendMessage message1 = new SendMessage() // Create a message object object
							.setChatId(chat_id)
							.setText(message_text);
					try {
						execute(message1); // Sending our message object to user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
					TelegramTerritoryDistribution();
				}
			} else {
				System.out.println("User enrolled already.");
			}
		}
	}
   //Method to translate Messages that require parsing
	public void ParsTranslate(String message){

		String  playerName = update2.getMessage().getFrom().getFirstName();
		String[] tokens = message.split("#");
		for(String t:tokens){temp.add(t);}
		String Command = temp.get(0);
		int LandID = Integer.valueOf(temp.get(1));
		int LandID2 = 0;
		if(temp.size()==3){ LandID2 = Integer.valueOf(temp.get(2));}

		if(Command.equals("/reinforce@CSGSanaz_bot")){TelegramReinforce(playerName,LandID);}
		if(Command.equals("/attack@CSGSanaz_bot")){TelegramAttack(playerName, LandID, LandID2);}
	}


}