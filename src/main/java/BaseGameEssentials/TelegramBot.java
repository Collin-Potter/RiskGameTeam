package BaseGameEssentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//This class sets up the functionality and credentials of the TelegramBot to be called and created by the TelegramBotHandler class
public class TelegramBot extends TelegramLongPollingBot {
	
	private static String botKeyStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private BufferedReader br;
	private FileReader fr;
	
	public TelegramBot(){
		
		//Get TelegramBot credentials
        readInCredentials("/src/main/java/BaseGameEssentials/telegramBotToken");
	}
	
    @Override
	//This method is called whenever the Bot recieves a message
	//This method is the core functionality of the Bot
    public void onUpdateReceived(Update update) {
    	// We check if the update has a message and the message has text
	    //This method (currently) will echo the message sent by the user
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            
            //Determines content of message
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "CSGRiskGame_bot";
    }

    @Override
    public String getBotToken() {
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
}
