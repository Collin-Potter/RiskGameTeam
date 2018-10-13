package events;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.Helper;
import twitter.TwitterHandler;
import entities.Player;
import entities.Territory;

/**
 * Turn class simulates one round for one Player with three phases
 * @author Grant Williams
 * 
 * @date 9/10/18
 **/

public class Turn {
	
	private Scanner userInput;
	private boolean validInput;
	private boolean goBack;
	private String in;
	private ArrayList<Territory> wTerr;
	private int tweetTerr; //keeps track of players recently acquired territories per turn to tweet
	private String eqBar;
	private String daBar;
	private Helper helper;
	private TwitterHandler twitterHandler;
	
	public Turn(){
		userInput = new Scanner(System.in);
		validInput = false;
		goBack = false;
		in = "";
		wTerr = new ArrayList<Territory>();
		tweetTerr = 0;
		helper = new Helper();
		twitterHandler = new TwitterHandler();
		eqBar = "+=============================================================================================================================================================+";
		daBar = "---------------------------------------------------------------------------------------------------------------------------------------------------------------";
	}
	
	public void runTurn(ArrayList<Player> players, int turnCount, ArrayList<Territory> wTerritories){
		wTerr = wTerritories;
		//Reinforcement Phase
		System.out.println("COMMENCING REINFORCEMENT PHASE OF TURN: " + turnCount);
		System.out.println(eqBar);
		int playerCount = players.size();
		if(turnCount > 1){
			for(int i = 0; i < players.size(); i++){
				int bonusTroop = bonusTroopCalculation(players.get(i));
				players.get(i).increaseTroops(bonusTroop);
			}
		}
		//For turn give players option to randomly distribute first reinforcements
		validInput = false;
		boolean randomTroops = false;
		while(!validInput){ //ask user to randomly distribute initial troops
			System.out.println("Would you like to randomly distribute reinforcements for all players?");
			System.out.println("Y/N");
			in = userInput.nextLine();
			if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
				System.out.println(daBar);
				System.out.println("Invalid input. Please try again.");
				System.out.println(daBar);
			}
			else{
				System.out.println(daBar);
				if(in.equals("Y") || in.equals("y")){
					validInput = helper.confirmDialog("Are you sure you want to randomly distribute reinforcements for all players?");
					randomTroops = true;
					
				}
				else{ //User selects N
					validInput = helper.confirmDialog("Are you sure you do not want to randomly distribute reinforcements for all players?");
					randomTroops = false;
				}
			}
		}
		if(randomTroops){ //randomly distribute reinforcements
			playerCount = players.size();
			System.out.println("Randomly Distributing Reinforcements for all Players...");
			System.out.println(daBar);
			while(playerCount > 0){
				for(int i = 0; i < players.size(); i++){
					if(players.get(i).getTroops() > 0){
						randomReinforcementPhase(players.get(i));
					}
					else{
						playerCount--;
					}
				}
			}
			for(int i = 0; i < players.size(); i++){
				ArrayList<Territory> pTerr = players.get(i).getTerritories();
				System.out.println("TROOP DISTRIBUTION: ");
				System.out.println(pTerr);//printTerrTable(pTerr);
				System.out.println(daBar);
			}
		}
		else{ //have players choose troops distribution
			playerCount = players.size();
			while(playerCount > 0){
				for(int i = 0; i < players.size(); i++){
					if(players.get(i).getTroops() > 0){
						reinforcementPhase(players.get(i));
					}
					else{
						playerCount--;
						System.out.println(players.get(i).getName() + " is out of reinforcements...");
						System.out.println(daBar);
					}
				}
			}
		}
		System.out.println("REINFORCEMENT PHASE COMPLETE");
		System.out.println(eqBar);
		
		//Attack Phase
		System.out.println("COMMENCING ATTACK PHASE OF TURN: " + turnCount);
		System.out.println(eqBar);
		for(int i = 0; i < players.size(); i++){
			attackPhase(players.get(i));
		}
		System.out.println("ATTACK PHASE COMPLETE");
		System.out.println(eqBar);
		
		//Fortify Phase
		System.out.println("COMMENCING FORTIFY PHASE OF TURN: " + turnCount);
		System.out.println(eqBar);
		for(int i = 0; i < players.size(); i++){
			fortifyPhase(players.get(i));
		}
		System.out.println("FORTIFY PHASE COMPLETE");
		System.out.println(eqBar);
		
		//Tweets game status at end of turn
		System.out.println("Posting update for turn " + turnCount + " on Twitter...");
		for(int i = 0; i < players.size(); i++){
			String tweet  = getPlayerTweet(turnCount, players.get(i), tweetTerr);
			twitterHandler.postTweet(tweet);
		}
	}
	
	private String getPlayerTweet(int turnCount, Player p, int tweetTerr) {
		String tweet = "";
		if(tweetTerr == 0){
			tweet = p.getName() + " did not gain any new territories on turn " + turnCount;
		}
		else{
			tweet = p.getName() + " gained " + tweetTerr + "territories  on turn " + turnCount + ":\n";
		}
		return tweet;
	}

	//Random Reinforcement phase of turn
	private void randomReinforcementPhase(Player player){
		
		ArrayList<Territory> pTerr = player.getTerritories();
		
		Random random = new Random();
		int tempID = random.nextInt(wTerr.size());
		while(findIndexOf(tempID, pTerr) == -1){ //make sure tempID valid player territory
			tempID = random.nextInt(wTerr.size());
		}
		wTerr.get(findIndexOf(tempID, wTerr)).increaseTroopCount(1);
		player.decreaseTroops(1);
	}
	
	//Standard Reinforcement phase of turn
	private void reinforcementPhase(Player player){
		System.out.println("REINFORCEMENT FOR PLAYER " + player.getID() + ": ");
		System.out.println(daBar);
		
		ArrayList<Territory> pTerr = player.getTerritories();
		validInput = false; 
		in = "";
		
		//System.out.println("Twitter Territores: ");
		//System.out.println(getPlayerTable(pTerr));
		//System.out.println(daBar);
		
		System.out.println("Current Territories: ");
		System.out.println(getPlayerTable(pTerr));//printTerrTable(pTerr);
		System.out.println(daBar);
		
		System.out.println(player.getName() + " you have " + player.getTroops() + " troops to distribute...");
		System.out.println(daBar);
		
		int tempID = 0;
		while(!validInput){
			System.out.println(player.getName() + " enter the ID of the Territory you want to place a troop in: ");
			in = userInput.nextLine();
			System.out.println(daBar);
			validInput = validIDInput(in, pTerr);
			if(!validInput){
				System.out.println("Invalid input. Please try again.");
				System.out.println(daBar);
			}
			else{
				tempID = Integer.parseInt(in);
				validInput = helper.confirmDialog("Place 1 troop in " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + "?");
			}
		}
		wTerr.get(findIndexOf(tempID, wTerr)).increaseTroopCount(1);
		System.out.println("One troop placed in " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + " by " + player.getName());
		player.decreaseTroops(1);
	}
	
	//Attack phase of turn
	private void attackPhase(Player player){
		System.out.println("ATTACK FOR PLAYER " + player.getID() + ": ");
		System.out.println(daBar);
	}
	
	//Fortify phase of turn
	private void fortifyPhase(Player player){
		
		System.out.println("FORTIFICATION FOR PLAYER " + player.getID() + ": ");
		System.out.println(daBar);
		
		//Check if fortification possible
		ArrayList<Territory> pTerr = player.getTerritories();
		ArrayList<Territory> validT = new ArrayList<Territory>();
		int validCount = 0;
		for(int i = 0; i < pTerr.size(); i++){
			if(checkTerritoryValidity(pTerr.get(i).getID(), player)){
				validT.add(pTerr.get(i));
				validCount++;
			}
		}
		if(validCount > 0){
			goBack = true;
			while(goBack){
				//Let player pick which to fortify from
				System.out.println("Current valid territories for " + player.getName());
				System.out.println(getPlayerTable(validT)); //printTerrTable(validT);
				System.out.println(daBar);
				validInput = false;
				in = "";
				while(!validInput){
					System.out.println(player.getName() + " enter the ID of the Territory you want to take troops from: ");
					in = userInput.nextLine();
					System.out.println(daBar);
					validInput = validIDInput(in, validT);
					if(!validInput){
						System.out.println("Invalid input or nonvalid ID. Please try again.");
						System.out.println(daBar);
					}
					else{
						int tempID = Integer.parseInt(in);
						validInput = helper.confirmDialog("Take troops from " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + "?");
					}
				}
				
				//Let player pick which territory to fortify
				int aID = Integer.parseInt(in);
				Territory territoryA = wTerr.get(findIndexOf(aID, wTerr));
				ArrayList<Integer> tempAdj = territoryA.getAdjacencies();
				ArrayList<Territory> vTerr = new ArrayList<Territory>();
				for(int i = 0; i < tempAdj.size(); i++){
					Territory tempTerr = wTerr.get(findIndexOf(tempAdj.get(i), wTerr));
					if(tempTerr.getOccupant().getID() == player.getID()){
						vTerr.add(tempTerr);
					}
				}
				System.out.println(territoryA.getName() + " has " + (territoryA.getTroopCount()-1) + " troops that it can send to fortify to one of the following territories: ");
				System.out.println(getPlayerTable(vTerr));//printTerrTable(vTerr);
				
				System.out.println(daBar);
				validInput = false;
				in = "";
				while(!validInput){
					System.out.println(player.getName() + " enter the ID of the Territory you want to place a troop in: ");
					System.out.println("(Or enter G to go back to select another territory to take troops from)");
					in = userInput.nextLine();
					System.out.println(daBar);
					if(in.equals("G") || in.equals("g")){ //go back
						validInput = helper.confirmDialog("Are you sure you want to go back to select another territory to take troops from?");
						if(validInput){
							goBack = true;
						}
					}
					else{
						goBack = false;
					}
					if (!goBack){
						validInput = validIDInput(in, pTerr);
						if(!validInput){
							System.out.println("Invalid input. Please try again.");
							System.out.println(daBar);
						}
						else{
							int tempID = Integer.parseInt(in);
							validInput = helper.confirmDialog("Place troops into " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + "?");
						}
					}
				}
				if(!goBack){
					//Let player pick how many troops to fortify B with from A
					int bID = Integer.parseInt(in);
					Territory territoryB = wTerr.get(findIndexOf(bID, wTerr));
					int totalTroops = territoryA.getTroopCount();
				
					validInput = false;
					in = "";
					int amount = 0;
					while(!validInput){
						System.out.println(player.getName() + " enter the amount of troops you want to take from " + territoryA.getName() + " and place into " + territoryB.getName());
						System.out.println("(Note: Only " + (totalTroops-1) + " troops in total can be moved from " + territoryA.getName() + "): ");
						in = userInput.nextLine();
						System.out.println(daBar);
						validInput = validAmountInput(in, totalTroops);
						if(!validInput){
							System.out.println("Invalid input or amount of troops to move. Please try again.");
							System.out.println(daBar);
						}
						else{
							amount = Integer.parseInt(in);
							validInput = helper.confirmDialog("Place " + amount + " troops into " + territoryB.getName());
						}
					}
					
					//Fortify into territory A from territory B
					territoryA.decreaseTroopCount(amount);
					System.out.println(player.getName() + " has lost " + amount + " troop(s) in " + territoryB.getName() + ".");
					System.out.println(daBar);
					territoryB.increaseTroopCount(amount);
					System.out.println(player.getName() + " has gained " + amount + " troop(s) in " + territoryA.getName() + ".");
					System.out.println(daBar);
				}
			}
		}
		else{
			System.out.println(player.getName() + " can not fortify at this time.");
		}
	}
	
	//Checks the validity of input for reinforcementPhase and fortifyPhase methods
	private boolean validIDInput(String input, ArrayList<Territory> pTerr){
		try{
			int check = Integer.parseInt(input);
			for(int i = 0; i < pTerr.size(); i++){
				if(check == pTerr.get(i).getID()){
					return true;
				}
			}
			return false;
		}catch(NumberFormatException e){
			return false;
		}
		
	}

	//Checks the validity of input for fortifyPhase method
	private boolean validAmountInput(String input, int totalAmount){
		try{
			int check = Integer.parseInt(input);
			if(check < (totalAmount) && check > 0){
				return true;
			}
			else{

				return false;
			}
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	//Returns index of territory by given ID
	public int findIndexOf(int ID, ArrayList<Territory> wTerr){
		int out = -1;
		for(int i = 0; i < wTerr.size(); i++){
			if(wTerr.get(i).getID() == ID){
				out = i;
			}
		}
		return out;
	}
	
	/*
	//Prints info of territory for reinforcementPhase method
	private void printTerrInfo(Territory t, ArrayList<Territory> wTerr){
		//ID Section
		System.out.print("|| ");
		if(t.getID() < 10){
			System.out.print(" " + t.getID() + " |");
		}
		else{
			System.out.print(t.getID() + " |");
		}
		//Name Section
		System.out.print("| " + t.getName());
		for(int i = 0; i < (19-t.getName().length()); i++){
			System.out.print(" ");
		}
		System.out.print(" |");
		//Troop Count Section
		if(t.getTroopCount() < 10){
			System.out.print("|    " + t.getTroopCount() + "   |");
		}
		else{
			System.out.print("|   " + t.getTroopCount() + "   |");
		}
		//Adjacency Section
		System.out.print("| ");
		ArrayList<Integer> tAdjs = t.getAdjacencies();
		int count = 6; 
		for(int i = 0; i < tAdjs.size(); i++){
			Territory tempTerr = wTerr.get(findIndexOf(tAdjs.get(i), wTerr));
			String tempS = "";
			if(tAdjs.get(i) < 10){
				tempS = "(0" + tAdjs.get(i) + ": " + tempTerr.getName() + ") ";
			}
			else{
				tempS = "(" + tAdjs.get(i) + ": " + tempTerr.getName() + ") ";
			}
			System.out.print(tempS);
			count = count + tempS.length();
		}
		for(int i = 0; i < (115 - count); i++){
			System.out.print(" ");
		}
		System.out.print("||");
		System.out.println();
	}	
			
	//Prints table of territories given an ArrayList of territories
	public void printTerrTable(ArrayList<Territory> pTerr){
		String tableHeader = "|| ID ||         NAME        || TROOPS ||                                             ADJACENCT TO                                                     ||";
		//Upper Table Bounds
		System.out.print(" .");
		for(int i = 0; i < tableHeader.length()-4; i++){
			System.out.print("=");
		}
		System.out.print(".\n");
		System.out.println(tableHeader);
		System.out.print("||");
		for(int i = 0; i < tableHeader.length()-4; i++){
			if(i == 4 || i == 5 || i == 27 || i == 28 || i == 37 || i == 38){
				System.out.print("|");
			}
			else{
				System.out.print("-");
			}
		}
		System.out.print("||\n");
		for(int i = 0; i < pTerr.size(); i++){
			printTerrInfo(pTerr.get(i), wTerr);
		}
		//Lower Table Bounds
		System.out.print(" '");
		for(int i = 0; i < tableHeader.length()-4; i++){
			System.out.print("=");
		}
		System.out.print("'\n");
	}
	*/

	//Returns amount of troops to be given to player at beginning of reinforcementPhase
	private int bonusTroopCalculation(Player player){
		ArrayList<Territory> tempTerr = player.getTerritories();
		int bonus = tempTerr.size()/3;
		int[] continents = new int[6];
		for(int i = 0; i < tempTerr.size(); i++){
			switch(tempTerr.get(i).getContinent()){
			case "Africa":
				continents[0]++;
			case "Asia":
				continents[1]++;
			case "Australia":
				continents[2]++;
			case "Europe":
				continents[3]++;
			case "North America":
				continents[4]++;
			case "South America":
				continents[5]++;
			}
		}
		if(continents[0] == 6){
			bonus = bonus + 3; //Adds bonus for controlling all of Africa
			System.out.println(player.getName() + " was awarded 3 bonus troops for controlling all of Africa...");
		}
		if(continents[1] == 12){
			bonus = bonus + 7; //Adds bonus for controlling all of Asia
			System.out.println(player.getName() + " was awarded 7 bonus troops for controlling all of Asia...");
		}
		if(continents[2] == 4){
			bonus = bonus + 2; //Adds bonus for controlling all of Australia
			System.out.println(player.getName() + " was awarded 2 bonus troops for controlling all of Australia...");
		}
		if(continents[3] == 7){
			bonus = bonus + 5; //Adds bonus for controlling all of Europe
			System.out.println(player.getName() + " was awarded 5 bonus troops for controlling all of Europe...");
		}
		if(continents[4] == 9){
			bonus = bonus + 5; //Adds bonus for controlling all of North America
			System.out.println(player.getName() + " was awarded 5 bonus troops for controlling all of North America...");
		}
		if(continents[5] == 4){
			bonus = bonus + 2; //Adds bonus for controlling all of South America
			System.out.println(player.getName() + " was awarded 2 bonus troops for controlling all of South America...");
		}
		return bonus;
	}
	
	//Checks if territory has troopCount above 2 and is adjacent to at least one territory controlled by the same player
	private boolean checkTerritoryValidity(int ID, Player p){
		Territory tempTerr = wTerr.get(findIndexOf(ID, wTerr));
		if(tempTerr.getTroopCount() > 1){
			ArrayList<Integer> tempAdj = tempTerr.getAdjacencies();
			int count = 0;
			for(int j = 0; j < wTerr.size(); j++){
				for(int k = 0; k < tempAdj.size(); k++){
					if(wTerr.get(j).getID() == tempAdj.get(k) && wTerr.get(j).getOccupant().getID() == p.getID()){
						count++;
					}
				}
			}
			
			if(count > 0){
				return true;
			}
		}
		return false;
	}
	
	//Returns string table of players territories
	private String getPlayerTable(ArrayList<Territory> pTerr){
		String tableHeader = "|| ID ||         NAME        || TROOPS ||                                             ADJACENCT TO                                                     ||";
		String table = "";
		//Upper Table Bounds
		table = table + " .";
		for(int i = 0; i < tableHeader.length()-4; i++){
			table = table + "=";
		}
		table = table + ".\n";
		table = table + tableHeader + "\n";
		table = table + "||";
		for(int i = 0; i < tableHeader.length()-4; i++){
			if(i == 4 || i == 5 || i == 27 || i == 28 || i == 37 || i == 38){
				table = table + "|";
			}
			else{
				table = table + "-";
			}
		}
		table = table + "||\n";
		for(int i = 0; i < pTerr.size(); i++){
			table = table + getTerrInfo(pTerr.get(i), wTerr);
		}
		//Lower Table Bounds
		table = table + " '";
		for(int i = 0; i < tableHeader.length()-4; i++){
			table = table + "=";
		}
		table = table + "'\n";
		return table;
	}
	
	//Helper method to put territory info into getPlayerTable
	private String getTerrInfo(Territory t, ArrayList<Territory> wTerr){
		String info = "";
		//ID Section
		info = info + "|| ";
		if(t.getID() < 10){
			info = info + " " + t.getID() + " |";
		}
		else{
			info = info + t.getID() + " |";
		}
		//Name Section
		info = info + "| " + t.getName();
		for(int i = 0; i < (19-t.getName().length()); i++){
			info = info + " ";
		}
		info = info + " |";
		//Troop Count Section
		if(t.getTroopCount() < 10){
			info = info + "|    " + t.getTroopCount() + "   |";
		}
		else{
			info = info + "|   " + t.getTroopCount() + "   |";
		}
		//Adjacency Section
		info = info + "| ";
		ArrayList<Integer> tAdjs = t.getAdjacencies();
		int count = 6; 
		for(int i = 0; i < tAdjs.size(); i++){
			Territory tempTerr = wTerr.get(findIndexOf(tAdjs.get(i), wTerr));
			String tempS = "";
			if(tAdjs.get(i) < 10){
				tempS = "(0" + tAdjs.get(i) + ": " + tempTerr.getName() + ") ";
			}
			else{
				tempS = "(" + tAdjs.get(i) + ": " + tempTerr.getName() + ") ";
			}
			info = info + tempS;
			count = count + tempS.length();
		}
		for(int i = 0; i < (115 - count); i++){
			info = info + " ";
		}
		info = info + "||\n";
		return info;
	}
}
