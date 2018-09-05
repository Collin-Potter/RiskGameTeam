package Main;

import java.util.ArrayList;
import java.util.Scanner;

import events.Dice;
import maps.WorldMap;
import player.Player;

/**
 * Game class manipulates all Risk objects to be called in main
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class Game {
	
	private Scanner userInput;
	private WorldMap world;
	private ArrayList<Player> players;
	private boolean validInput;
	private Dice dice;
	
	public Game(){
		userInput = new Scanner(System.in);
		world = new WorldMap();
		players = new ArrayList<Player>();
		validInput = false;
		dice = new Dice();
	}
	
	public void run(){
		//Setting up players
		getPlayerInfo();
		
		//Setting up world map
		world.createWorldMap();
		world.printWorldMap();
		
		userInput.close();
	}
	
	//Gets the number of players from the user
	private int getPlayerCount(){
		validInput = false;
		String in = "";
		int numPlayers = 0;
		while(!validInput){
			System.out.println("How many players?(2, 3, 4, 5, or 6)");
			in = userInput.nextLine();
			if(!(in.equals("2") || in.equals("3") || in.equals("4") || in.equals("5") || in.equals("6"))){
				System.out.println("Invalid input. Please try again.");
			}
			else{
				numPlayers = Integer.parseInt(in);
				validInput = true;
			}
		}
		return numPlayers;
	}
	
	//Returns amount of troops to give each player upon construction
	private int getTroopCount(int numPlayers){
		if(numPlayers == 2){
			return 40;
		}
		else if(numPlayers == 3){
			return 35;
		}
		else if(numPlayers == 4){
			return 30;
		}
		else if(numPlayers == 5){
			return 25;
		}
		else{
			return 20;
		}
	}
	
	//Gets the names of the players from the user
	private void getPlayerInfo(){
		validInput = false;
		String in = "";
		int numPlayers = getPlayerCount();
		int numTroops = getTroopCount(numPlayers);
		for(int i = 1; i <= numPlayers; i++){
			System.out.println("Enter Name for Player " + i + ": ");
			in = userInput.nextLine();
			Player temp = new Player(in, numTroops, i);
			players.add(temp);
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).printPlayer();
		}
	}
}
