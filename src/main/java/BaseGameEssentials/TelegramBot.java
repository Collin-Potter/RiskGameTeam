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
	private BufferedReader br;
	private FileReader fr;
	public static ArrayList <String> temp = new ArrayList <String>();
	public static String message_text = new String();
	public int i = 0;
	public boolean gameOn = false;
	public boolean AttackPhase = false;
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
			System.out.println(command);
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
			i++;
			message_text = "Welcome To Risk " + playerName + " you are our first player. We need two more players to play the game." +
					"If you want to play send /join@CSGSanaz_bot " + "command to become a player.";
			Send(message_text);
		}
		if (input.equals("/join@CSGSanaz_bot") && playerList.size() != 3) {
			String playerName = update2.getMessage().getFrom().getFirstName();
			i++;
			boolean goodToGo = false;
			//ensuring the same player does not enroll to the game more than once
			if (playerList.size() == 1 && !playerName.equals(playerList.get(0).getTeam())) {
				goodToGo = true;
			}
			if (goodToGo == true) {
				playerList.add(new Player(35, playerName, false, false, (i)));
				if (playerList.size() == 2) {
					message_text = " Dividing territories among players, Reinforcement will begin soon .";
					Send(message_text);
					TelegramTerritoryDistribution(); // Distribute territories among players
					for (Player p : playerList) {
						int ID = 0;
						for (Territory t : territoryList) {
							if (t.getTeam().equals(p.getTeam())) {TelegramReinforce(p.getTeam(), t.getID());}
						}
					}

				}

			}
		}
		if (input.equals("/attack@CSGSanaz_bot") && AttackPhase == true && gameOn == true) {
			for(Player p : playerList) {
				ArrayList <Territory> AttackFrom = new ArrayList(), To = new ArrayList <Territory>();
				AttackFrom = FindWhereToAttackFrom(PLAYERTurnKeeper);
				To = Game.FindWhereICanAttack(AttackFrom.get(0).getName());
				fulfillAttack(p,To.get(0),AttackFrom.get(0));
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
	/** General Method to send Message to telegram chat
	 * @param mess : String message to be sent
	 * **/
	public void Send(String mess){
		long chat_id = update2.getMessage().getChatId();
		SendMessage messageX = new SendMessage() // Create a message object object
				.setChatId(chat_id)
				.setText(mess);
		try {
			execute(messageX); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}

