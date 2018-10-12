package Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import ascii.ASCII;
import events.Turn;
import maps.MapReader;
import maps.WorldMap;
import player.Player;
import territories.Territory;

/**
 * Game class manipulates all Risk objects to be called in main
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class Game {
	
	private Scanner userInput;
	private MapReader mapReader;
	private ArrayList<Player> players;
	private ArrayList<Territory> wTerr;
	private boolean validInput;
	private Turn turn;
	private ASCII ascii;
	private WorldMap world;
	private String eqBar;
	private String daBar;
	private Helper helper;
	
	public Game(){
		userInput = new Scanner(System.in);
		mapReader = new MapReader();
		players = new ArrayList<Player>();
		wTerr = new ArrayList<Territory>();
		validInput = false;
		turn = new Turn();
		ascii = new ASCII();
		world = new WorldMap();
		helper = new Helper();
		eqBar = "+=============================================================================================================================================================+";
		daBar = "---------------------------------------------------------------------------------------------------------------------------------------------------------------";
	
	}
	
	public void run(){
		//Display Banner
		ascii.readASCII("C:\\Users\\grant\\Desktop\\School\\COSC\\COSC 4353\\Projects\\Risk Game\\myRiskGame\\src\\ascii\\asciiTitle");
		
		System.out.println("GAME SETUP:");
		System.out.println(eqBar);
		
		//Setting up players
		getPlayerInfo();
		
		//Setting up world mapReader
		System.out.println(daBar);
		//mapReader.readInMap("C:\\Users\\grant\\Desktop\\School\\COSC\\COSC 4353\\Projects\\Risk Game\\myRiskGame\\src\\maps\\worldmap");
		//mapReader.printWorldMap();
		//System.out.println("Map Created...");
		
		world.createWorldMap();
		//world.printWorldMap();
		System.out.println("World Created...");
		System.out.println(daBar);	
		
		//Distribute territories of world mapReader to players
		distributeTerritories();
		System.out.println("Territories Distributed...");
		System.out.println(daBar);
		
		System.out.println("GAME SETUP COMPLETE");
		System.out.println(eqBar);;
		
		//Turn iteration
		runOneTurn(1);
		//runOneTurn(2);
		
		//End of Game run function
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
				System.out.println(daBar);
				System.out.println("Invalid input. Please try again.");
				System.out.println(daBar);
				}
			else{
				numPlayers = Integer.parseInt(in);
				System.out.println(daBar);
				validInput = helper.confirmDialog("Are you sure you want " + numPlayers + " players?");
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
		String playerName = "";
		int numPlayers = getPlayerCount();
		int numTroops = getTroopCount(numPlayers);
		for(int i = 1; i <= numPlayers; i++){
			validInput = false;
			while(!validInput){
				System.out.println("Enter name for player " + i + ": ");
				in = userInput.nextLine();
				playerName = in;
				System.out.println(daBar);
				validInput = helper.confirmDialog("Player " + i + " is named " + playerName + "?");
			}
			Player temp = new Player(playerName, numTroops, i);
			players.add(temp);
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).printPlayer();
		}
	}
	
	//Returns a deep copy of a given ArrayList of Territories
	private ArrayList<Territory> getDeepCopy(ArrayList<Territory> original){
		ArrayList<Territory> copy = new ArrayList<Territory>();
		for(int i = 0; i < original.size(); i++){
			copy.add(original.get(i));
		}
		return copy;
	}
	
	//Distributes all territories amongst amount of players
	//To be called after player information is obtained and WorldMap created
	private void distributeTerritories(){
		int playerCount = players.size();
		//wTerr = getDeepCopy(mapReader.getMapTerritories());
		wTerr = getDeepCopy(world.getWorldTerritories());
		int player = 1;
		Random random = new Random();
		while(wTerr.size() > 0){
			if(wTerr.size() < playerCount){
				while(wTerr.size() > 0){
					int nextTerritory = random.nextInt(wTerr.size());
					Territory tempT = wTerr.get(nextTerritory);
					System.out.println(tempT.getName());
					Player tempP = players.get(player-1);
					tempP.addTerritory(tempT);
					tempP.decreaseTroops(1);
					tempT.setOccupant(tempP);
					tempT.increaseTroopCount(1);
					wTerr.remove(nextTerritory);
					player++;
				}
			}
			else{
				while(player <= playerCount){
					int nextTerritory = random.nextInt(wTerr.size());
					Territory tempT = wTerr.get(nextTerritory);
					Player tempP = players.get(player-1);
					tempP.addTerritory(tempT);
					tempP.decreaseTroops(1);
					tempT.setOccupant(tempP);
					tempT.increaseTroopCount(1);
					wTerr.remove(nextTerritory);
					player++;
				}
				player = 1;
			}
		}
	}
	
	//Runs one cycle of a turn between all players
	private void runOneTurn(int turnCount){
		turn.runTurn(players, turnCount, getDeepCopy(world.getWorldTerritories()));
	}
	
}
