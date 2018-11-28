package BaseGameEssentials;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static BaseGameEssentials.Game.*;


public class TelegramBot extends TelegramLongPollingBot {

	private static String botKeyStr = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	public static ArrayList <String> temp = new ArrayList <String>();
	public static String message_text = new String();
	public int i = 0;
	public static Territory AttackingTerr, DefendingTerr;
	public static boolean WIN = false;
	public static boolean NotEnoughTroops = false;
	public Player PLAYERTurnKeeper;
	public static Update update2;

	@Override
	/**
	 * receiving updates from the bot, directing the updates to their responsive methods.
	 * @param Update
	 * **/
	public void onUpdateReceived(Update update) {

		// receiving commands
		update2 = update;
			String command = update.getMessage().getText();
			if (command.equals("/start") || command.equals("/join@CSGSanaz_bot") || command.equals("/attack@CSGSanaz_bot")) {
				translateMessage(command);
			}
	}
	@Override
	public String getBotUsername() {
		// Return bot username
		return "CSGSanaz_bot";
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return botKeyStr;
	}
	/**
	 * Method to Translate the initial commands(/start , /join) and to take actions respectively
	 * On /start creates the first player and sends welcome message
	 * On /join allows creation of the two other players
	 * On presence of the needed number of player, territories get distributed and reinforced with one troop
	 * through calling TelegramTerritoryDistribution(), and List of territories are printed back on telegram chat by calling the
	 * method printTerritories()
	 *
	 * @Param input which is the string received from the bot
	 **/
	public void translateMessage(String input) {

		if (input.equals("/start")) {
			String playerName = update2.getMessage().getFrom().getFirstName();
			playerList.add(new Player(35, playerName, false, false, (i)));
		}
		if (input.equals("/join@CSGSanaz_bot") && playerList.size() != 3) {
			String playerName = update2.getMessage().getFrom().getFirstName();
				playerList.add(new Player(35, playerName, false, false, (i)));
				if (playerList.size() == 2) {
					TelegramTerritoryDistribution(); // Distribute territories among players
				}
			}


	}
	/**
	 * Method to keep track of players turns
	 * @param List : playerList
	 * **/
	public void TurnKeeper(ArrayList<Player> List){
		if(List.get(0).getTurn()== true && List.get(1).getTurn()==true && List.get(2).getTurn()==true){
			for(Player p: playerList ){ p.SetTurn(false);}
		}
		for(Player P: playerList){
			if(P.getTurn() == false){
				P.SetTurn(true);
				PLAYERTurnKeeper = P;
				break;
			}
		}
	}

}

