package events;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.Helper;
import twitter.TwitterHandler;
import entities.Dice;
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
	private Helper helper;
	private TwitterHandler twitterHandler;
	private boolean winConditionMet;
	private Dice dice;
	
	public Turn(TwitterHandler twitter){
		userInput = new Scanner(System.in);
		validInput = false;
		goBack = false;
		in = "";
		wTerr = new ArrayList<Territory>();
		helper = new Helper();
	    twitterHandler = twitter;
		winConditionMet = false;
		dice = new Dice();
		}
	
	public void runTurn(ArrayList<Player> players, int turnCount, ArrayList<Territory> wTerritories){
		wTerr = wTerritories;
		//Reinforcement Phase
		System.out.println("COMMENCING REINFORCEMENT PHASE OF TURN " + turnCount + ": ");
		System.out.println(helper.eqBar);
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
			System.out.println(helper.daBar);
			if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
				System.out.println("Invalid input. Please try again.");
				System.out.println(helper.daBar);
			}
			else{
				
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
			System.out.println(helper.daBar);
			playerCount = 0;
			while(playerCount < players.size()){
				playerCount = 0;
				for(int i = 0; i < players.size(); i++){
					if(players.get(i).getTroops() > 0){
						randomReinforcementPhase(players.get(i));
					}
					else{
						playerCount++;
						//System.out.println(players.get(i).getName() + " is out of reinforcements...");
						//System.out.println(helper.daBar);
					}
				}
			}
			for(int i = 0; i < players.size(); i++){
				ArrayList<Territory> pTerr = players.get(i).getTerritories();
				System.out.println("TROOP DISTRIBUTION FOR " + players.get(i).getName().toUpperCase() + ": ");
				//System.out.println(getPlayerTable(pTerr));
				printTerrTable(pTerr);
				System.out.println(helper.daBar);
			}
		}
		else{ //have players choose troops distribution
			playerCount = 0;
			while(playerCount < players.size()){
				playerCount = 0;
				for(int i = 0; i < players.size(); i++){
					if(players.get(i).getTroops() > 0){
						reinforcementPhase(players.get(i));
					}
					else{
						playerCount++;
						System.out.println(players.get(i).getName() + " is out of reinforcements...");
						System.out.println(helper.daBar);
					}
				}
			}
		}
		System.out.println("REINFORCEMENT PHASE OF TURN " + turnCount + " COMPLETE");
		System.out.println(helper.eqBar);
		
		//Attack Phase
		System.out.println("COMMENCING ATTACK PHASE OF TURN " + turnCount + ": ");
		System.out.println(helper.eqBar);
		for(int i = 0; i < players.size(); i++){
			if(!checkWinCondition() && !players.get(i).getTerritories().isEmpty()){
				attackPhase(players.get(i));
			}
			else{
				if(players.get(i).getTerritories().isEmpty()){
					String temp = players.get(i).getName() + " has been defeated on turn " + turnCount + "!";
					System.out.println(temp);
					System.out.println(helper.daBar);
					twitterHandler.postTweet(temp);
					players.remove(players.get(i));
				}
			}
		}
		System.out.println("ATTACK PHASE TURN " + turnCount + " COMPLETE");
		System.out.println(helper.eqBar);
		
		if(!checkWinCondition()){
			//Fortify Phase
			System.out.println("COMMENCING FORTIFY PHASE OF TURN " + turnCount + ": ");
			System.out.println(helper.eqBar);
			for(int i = 0; i < players.size(); i++){
				fortifyPhase(players.get(i));
			}
			System.out.println("FORTIFY PHASE OF TURN " + turnCount + " COMPLETE");
			System.out.println(helper.eqBar);
		}
		
		//Tweets game status at end of turn
		System.out.println("Posting update for turn " + turnCount + " on Twitter...");
		System.out.println(helper.daBar);
		for(int i = 0; i < players.size(); i++){
			String tweet  = getPlayerTweet(turnCount, players.get(i));
			twitterHandler.postTweet(tweet);
		}
		
		//Checks if win condition has been met and returns result to game class
		for(int i = 0; i < players.size(); i++){
			ArrayList<Territory> pTerr = players.get(i).getTerritories();
			String temp = "";
			if(pTerr.size() == 42){ //player controls all territories
				temp = players.get(i).getName() + " controls all territories and has won the game on turn " + turnCount + "!";
				System.out.println(temp);
				System.out.println(helper.daBar);
				twitterHandler.postTweet(temp);
				winConditionMet = true;
			}
		}
	}

	//Returns state of winCondition
	public boolean checkWinCondition(){
		return winConditionMet;
	}
	
	//Creates content of tweet for end of each turn
	private String getPlayerTweet(int turnCount, Player p) {
		String tweet = "";
		if(p.getRecentlyAdded() == 0){
			tweet = tweet + p.getName() + " did not gain any new territories on turn " + turnCount + "\n";
		}
		else{
			tweet = tweet + p.getName() + " gained " + p.getRecentlyAdded() + " territories  on turn " + turnCount + "\n";
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
		System.out.println("One troop placed into " + wTerr.get(findIndexOf(tempID, wTerr)).getName() 
				+ ", " + player.getName() + " has " + player.getTroops() + " reinforcement(s) remaining.");
		System.out.println(helper.daBar);
	}
	
	//Standard Reinforcement phase of turn
	private void reinforcementPhase(Player player){
		ArrayList<Territory> pTerr = player.getTerritories();
		validInput = false; 
		in = "";
		
		System.out.println("REINFORCEMENT OPTIONS FOR " + player.getName().toUpperCase() + ": ");
		//System.out.println(getPlayerTable(pTerr));
		printTerrTable(pTerr);
		System.out.println(helper.daBar);
		
		System.out.println(player.getName() + " you have " + player.getTroops() + " troops to distribute...");
		System.out.println(helper.daBar);
		
		int tempID = 0;
		while(!validInput){
			System.out.println(player.getName() + " enter the ID of the Territory you want to place a troop in: ");
			in = userInput.nextLine();
			System.out.println(helper.daBar);
			validInput = validIDInput(in, pTerr);
			if(!validInput){
				System.out.println("Invalid input. Please try again.");
				System.out.println(helper.daBar);
			}
			else{
				tempID = Integer.parseInt(in);
				validInput = helper.confirmDialog("Place 1 troop in " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + "?");
			}
		}
		wTerr.get(findIndexOf(tempID, wTerr)).increaseTroopCount(1);
		System.out.println("One troop placed in " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + " by " + player.getName());
		System.out.println(helper.daBar);
		player.decreaseTroops(1);
	}
	
	//Attack phase of turn
	private void attackPhase(Player player){
		System.out.println("ATTACK OPTIONS FOR " + player.getName().toUpperCase() + ": ");
		System.out.println(helper.daBar);
		boolean endAttackPhase = false;
		player.setRecentlyAdded(0);
		validInput = false; 
		in = "";
		while(!validInput){ //give player option to skip attack phase
			System.out.println(player.getName() + ", would you like to skip the attack phase this turn?");
			System.out.println("Y/N");
			in = userInput.nextLine();
			System.out.println(helper.daBar);
			if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
				System.out.println("Invalid input. Please try again.");
				System.out.println(helper.daBar);
			}
			else{
				if(in.equals("Y") || in.equals("y")){ //player is choosing to skip attack pahse
					validInput = helper.confirmDialog(player.getName() + ", are you sure you want to skip the attack phase this turn?");
					endAttackPhase = true;
				}
				else{ //player is not skipping attack phase
					validInput = helper.confirmDialog(player.getName() + ", are you sure you do not want to skip the attack phase this turn?");
					endAttackPhase = false;
				}
			}
		}
		while(!endAttackPhase){
			//Check if attacking is possible
			ArrayList<Territory> pTerr = player.getTerritories();
			ArrayList<Territory> validT = new ArrayList<Territory>();
			int validCount = 0;
			for(int i = 0; i < pTerr.size(); i++){
				if(checkTerritoryAttackValidity(pTerr.get(i).getID(), player)){
					validT.add(pTerr.get(i));
					validCount++;
				}
			}
			if(validCount > 0){
				goBack = true;
				while(goBack){
					//Let player pick which to attack from
					System.out.println("TERRITORIES THAT CAN ATTACK FOR " + player.getName().toUpperCase() + ": ");
					//System.out.println(getPlayerTable(validT)); 
					printTerrTable(validT);
					System.out.println(helper.daBar);
					validInput = false;
					in = "";
					while(!validInput){
						System.out.println(player.getName() + " enter the ID of the Territory you want to attack from: ");
						in = userInput.nextLine();
						System.out.println(helper.daBar);
						validInput = validIDInput(in, validT);
						if(!validInput){
							System.out.println("Invalid input or nonvalid ID. Please try again.");
							System.out.println(helper.daBar);
						}
						else{
							int tempID = Integer.parseInt(in);
							validInput = helper.confirmDialog("Attack from " + wTerr.get(findIndexOf(tempID, wTerr)).getName() + "?");
						}
					}
					
					//Let player pick which territory to attack
					int aID = Integer.parseInt(in);
					Territory territoryA = wTerr.get(findIndexOf(aID, wTerr));
					ArrayList<Integer> tempAdj = territoryA.getAdjacencies();
					ArrayList<Territory> vTerr = new ArrayList<Territory>();
					for(int i = 0; i < tempAdj.size(); i++){
						Territory tempTerr = wTerr.get(findIndexOf(tempAdj.get(i), wTerr));
						if(tempTerr.getOccupant().getID() != player.getID()){ //not owned by player
							vTerr.add(tempTerr);
						}
					}
					System.out.println("TERRITORIES THAT " + territoryA.getName().toUpperCase() + " CAN ATTACK:");
					//System.out.println(getPlayerTable(vTerr));
					printTerrTable(vTerr);
					System.out.println(helper.daBar);
					
					validInput = false;
					in = "";
					int bID = 0;
					while(!validInput){
						System.out.println(player.getName() + " enter the ID of the Territory you want to attack: ");
						System.out.println("(Or enter G to go back to select another territory to attack from)");
						in = userInput.nextLine();
						System.out.println(helper.daBar);
						if(in.equals("G") || in.equals("g")){ //go back
							validInput = helper.confirmDialog(player.getName() + ", are you sure you want to go back to select another territory to attack from?");
							if(validInput){
								goBack = true;
							}
						}
						else{
							goBack = false;
						}
						if (!goBack){
							validInput = validIDInput(in, wTerr);
							if(!validInput){
								System.out.println("Invalid input. Please try again.");
								System.out.println(helper.daBar);
							}
							else{
								bID = Integer.parseInt(in);
								validInput = helper.confirmDialog("Attack " + wTerr.get(findIndexOf(bID, wTerr)).getName() + " from " + territoryA.getName() + "?");
							}
						}
					}
					
					if(!goBack){
						//Let player pick how many troops to fortify B with from A
						Territory territoryB = wTerr.get(findIndexOf(bID, wTerr));
						
						//Attack Territory B with Territory A
						boolean victory = false;
						boolean canAttack = true;
						int attackCount = 0;
						while(canAttack){ //while attack still possible
							int totalTroops = territoryA.getTroopCount();
							if(victory && territoryB.getTroopCount() < 1){ //take territoryB from other player and give to current player and assign troops to new territory
								System.out.println(player.getName() + " has defeated all of " + territoryB.getOccupant().getName() + "'s troops and thus has conquered " + territoryB.getName() + "!");
								System.out.println(helper.daBar);
								territoryB.getOccupant().removeTerritory(bID);
								player.addTerritory(territoryB);
								territoryB.setOccupant(player);
								int setTroops = availableTroops(territoryA, territoryB);
								
								//Place troops into territory B from territory A
								territoryA.decreaseTroopCount(setTroops);
								territoryB.setTroopCount(setTroops);
								System.out.println(player.getName() + " has moved " + setTroops + " troop(s) from " + territoryA.getName() + " into " + territoryB.getName() + ".");
								System.out.println(helper.daBar);
								player.setRecentlyAdded(player.getRecentlyAdded() + 1);
								canAttack = false;
							}
							else{
								if(attackCount > 0 && territoryA.getTroopCount() > 1){
									if(!victory){
										//Ask if player wants to attack again
										validInput = false;
										in = "";
										while(!validInput){ //ask user whether or not they want to keep attacking same territory
											System.out.println(player.getName() + ", would you like to attack " + territoryB.getName() + " again?");
											System.out.println("Y/N");
											in = userInput.nextLine();
											System.out.println(helper.daBar);
											if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
												System.out.println("Invalid input. Please try again.");
												System.out.println(helper.daBar);
											}
											else{
												if(in.equals("Y") || in.equals("y")){
													validInput = helper.confirmDialog(player.getName() + ", are you sure you want to attack " + territoryB.getName() + " again?");
													canAttack = true;
													attackCount++;
													
												}
												else{ //User selects N
													validInput = helper.confirmDialog(player.getName() + ", are you sure you want to stop attacking " + territoryB.getName() + "?");
													canAttack = false;
													System.out.println(player.getName() + " stopped the attack on " + territoryB.getName() + " after " + attackCount + " attempt(s).");
													System.out.println(helper.daBar);
												}
											}
										}
									}
									//Attacking again
									if(totalTroops > 1 && canAttack){ //can still attack with territory a
										victory = runAttack(territoryA, territoryB);
										attackCount++;
									}
									else{ //can no longer attack with territory a
										canAttack = false;
										System.out.println(territoryA.getName() + " is out of troops and can no longer attack!");
										System.out.println(helper.daBar);
									}
								}
								else{ //first time attacking
									if(totalTroops > 1){ //can still attack with territory a
										victory = runAttack(territoryA, territoryB);
										attackCount++;
									}
									else{ //can no longer attack with territory a
										canAttack = false;
										System.out.println(territoryA.getName() + " is out of troops and can no longer attack!");
										System.out.println(helper.daBar);
									}	
								}
							}
						}
						
						//Ask if player wants to continue attack phase
						validInput = false;
						in = "";
						while(!validInput){ //ask user whether or not they want to end attacking
							System.out.println(player.getName() + ", would you like to continue attack phase?");
							System.out.println("Y/N");
							in = userInput.nextLine();
							System.out.println(helper.daBar);
							if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
								System.out.println("Invalid input. Please try again.");
								System.out.println(helper.daBar);
							}
							else{
								if(in.equals("Y") || in.equals("y")){
									validInput = helper.confirmDialog(player.getName() + ", are you sure you want to continue attacking?");
									endAttackPhase = false;
									
								}
								else{ //User selects N
									validInput = helper.confirmDialog(player.getName() + ", you want to end your attack phase of the turn?");
									endAttackPhase = true;
								}
							}
						}
					}
				}
			}
			else{
				System.out.println(player.getName() + " can not attack at this time.");
				System.out.println(helper.daBar);
				endAttackPhase = true;
			}
		}
	}
	
	//Returns number of troops available to send to conquered territory
	private int availableTroops(Territory a, Territory b) {
		//Ask if player wants to attack again
		validInput = false;
		in = "";
		int troopCount = 1;
		while(!validInput){
			if(a.getTroopCount() > 4){ //territory a has more than 3 troops to spare
				System.out.println(a.getOccupant().getName() + ", you can send anywhere between 3-" + (a.getTroopCount()-1) + " troops to " + b.getName());
				System.out.println("Enter the amount of troops to move to " + b.getName() + ": ");
				in = userInput.nextLine();
				System.out.println(helper.daBar);
				validInput = validAmountInput(in, a.getTroopCount(), 2);
				if(!validInput){
					System.out.println("Invalid input or amount of troops to move. Please try again.");
					System.out.println(helper.daBar);
				}
				else{
					troopCount = Integer.parseInt(in);
					validInput = helper.confirmDialog("Place " + troopCount + " troops into " + b.getName() + "?");
				}
			}
			else{
				System.out.println("Only " + (a.getTroopCount() - 1) + " troops available in " + a.getName() + " to send to " + b.getName() + ".");
				//System.out.println(helper.daBar);
				troopCount = a.getTroopCount() - 1;
				validInput = true;
			}
		}
		return troopCount;
	}

	//Returns of owners of territory a is victorious for one round of battle
	private boolean runAttack(Territory a, Territory b) {
		int aDice = 0;
		int bDice = 0;
		int[] aResults;
		int[] bResults;
		
		//Get number of Dice for territory A
		if(a.getTroopCount() > 4){
			aDice = 3;
		}
		else{
			aDice = a.getTroopCount() - 1;
		}
		
		//Get number of Dice for territory B
		if(b.getTroopCount() >= 2){
			bDice = 2;
		}
		else{
			bDice = b.getTroopCount();
		}
		
		//Get array of rolled dice
		aResults = dice.roll(aDice);
		bResults = dice.roll(bDice);
		
		//Find maximum value of rolled dice for A
		int aMax = 0;
		for(int i = 0; i < aResults.length; i++){
			if(aResults[i] > aMax){
				aMax = aResults[i];
			}
		}
		System.out.println(a.getOccupant().getName() + " rolled a max of " + aMax);
		//Find maximum value of rolled dice for A
		int bMax = 0;
		for(int i = 0; i < bResults.length; i++){
			if(bResults[i] > bMax){
				bMax = bResults[i];
			}
		}
		System.out.println(b.getOccupant().getName() + " rolled a max of " + bMax);
		System.out.println(helper.daBar);
		//Compare results
		if(aMax > bMax){ //results in favor of A
			b.decreaseTroopCount(1);
			System.out.println(a.getOccupant().getName() + " rolled a higher max and " + b.getOccupant().getName() + " has lost a troop in " + b.getName() + "!");
			if(b.getTroopCount() > 0){
				System.out.println("(" + b.getOccupant().getName() + " has " + b.getTroopCount() + " troop(s) remaining in " + b.getName() + ")");
			}
			System.out.println(helper.daBar);
			return true;
		}
		else{ //results in favor of B
			a.decreaseTroopCount(1);
			System.out.println(b.getOccupant().getName() + " rolled a higher max and successfully defended " + b.getName() + " from the attack by " + a.getOccupant().getName() + "!");
			System.out.println("(" + a.getOccupant().getName() + " has " + a.getTroopCount() + " troop(s) remaining in " + a.getName() + ")");
			System.out.println(helper.daBar);
			return false;
		}
	}

	//Fortify phase of turn
	private void fortifyPhase(Player player){
		System.out.println("FORTIFICATION OPTIONS FOR " + player.getName().toUpperCase() + ": ");
		System.out.println(helper.daBar);
		
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
			boolean skipFortify = false;
			validInput = false; 
			in = "";
			while(!validInput){ //give player option to skip attack phase
				System.out.println(player.getName() + ", would you like to skip the fortify phase this turn?");
				System.out.println("Y/N");
				in = userInput.nextLine();
				System.out.println(helper.daBar);
				if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
					System.out.println("Invalid input. Please try again.");
					System.out.println(helper.daBar);
				}
				else{
					if(in.equals("Y") || in.equals("y")){ //player is choosing to skip attack phase
						validInput = helper.confirmDialog(player.getName() + ", are you sure you want to skip the fortify phase this turn?");
						skipFortify = true;
					}
					else{ //player is not skipping fortify phase
						validInput = helper.confirmDialog(player.getName() + ", are you sure you do not want to skip the fortify phase this turn?");
						skipFortify = false;
					}
				}
			}
			if(!skipFortify){
				goBack = true;
				while(goBack){
					//Let player pick which to fortify from
					System.out.println("TERRITORIES WITH SPARE TROOPS FOR " + player.getName().toUpperCase() + ": ");
					//System.out.println(getPlayerTable(validT)); 
					printTerrTable(validT);
					System.out.println(helper.daBar);
					validInput = false;
					in = "";
					while(!validInput){
						System.out.println(player.getName() + " enter the ID of the Territory you want to take troops from: ");
						in = userInput.nextLine();
						System.out.println(helper.daBar);
						validInput = validIDInput(in, validT);
						if(!validInput){
							System.out.println("Invalid input or nonvalid ID. Please try again.");
							System.out.println(helper.daBar);
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
					System.out.println("TERRITORIES THAT " + territoryA.getName().toUpperCase() + " CAN SEND TROOPS TO:");
					//System.out.println(getPlayerTable(vTerr));
					printTerrTable(vTerr);
					System.out.println(helper.daBar);
					
					validInput = false;
					in = "";
					while(!validInput){
						System.out.println(player.getName() + " enter the ID of the Territory you want to place a troop in: ");
						System.out.println("(Or enter G to go back to select another territory to take troops from)");
						in = userInput.nextLine();
						System.out.println(helper.daBar);
						if(in.equals("G") || in.equals("g")){ //go back
							validInput = helper.confirmDialog(player.getName() + ", are you sure you want to go back to select another territory to take troops from?");
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
								System.out.println(helper.daBar);
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
							System.out.println(helper.daBar);
							validInput = validAmountInput(in, totalTroops, 0);
							if(!validInput){
								System.out.println("Invalid input or amount of troops to move. Please try again.");
								System.out.println(helper.daBar);
							}
							else{
								amount = Integer.parseInt(in);
								validInput = helper.confirmDialog("Place " + amount + " troops into " + territoryB.getName() + "?");
							}
						}
						
						//Fortify into territory B from territory A
						territoryA.decreaseTroopCount(amount);
						territoryB.increaseTroopCount(amount);
						System.out.println(player.getName() + " has moved " + amount + " troop(s) from " + territoryA.getName() + " into " + territoryB.getName() + ".");
						System.out.println(helper.daBar);
					}
				}
			}
		}
		else{
			System.out.println(player.getName() + " will not fortify at this time.");
			System.out.println(helper.daBar);
		}
	}
	
	//Checks the validity of input for turn phase methods
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

	//Checks the validity of input for an integer amount
	private boolean validAmountInput(String input, int upperBound, int lowerBound){
		try{
			int check = Integer.parseInt(input);
			if(check < (upperBound) && check > lowerBound){
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

	//Returns amount of troops to be given to player at beginning of reinforcementPhase
	private int bonusTroopCalculation(Player player){
		ArrayList<Territory> tempTerr = player.getTerritories();
		int bonus = tempTerr.size()/3;
		int[] continents = new int[6];
		for(int i = 0; i < tempTerr.size(); i++){
			String cont = tempTerr.get(i).getContinent();
			if(cont.equals("Africa")){
				continents[0]++;
			}
			else if(cont.equals("Asia")){
				continents[1]++;
			}
			else if(cont.equals("Australia")){
				continents[2]++;
			}
			else if(cont.equals("Europe")){
				continents[3]++;
			}
			else if(cont.equals("North America")){
				continents[4]++;
			}
			else if(cont.equals("South America")){
				continents[5]++;
			}
		}
		//System.out.println(helper.daBar);
		if(continents[0] == 6){
			bonus = bonus + 3; //Adds bonus for controlling all of Africa
			System.out.println(player.getName() + " was awarded 3 bonus troops for controlling all of Africa...");
			System.out.println(helper.daBar);
		}
		if(continents[1] == 12){
			bonus = bonus + 7; //Adds bonus for controlling all of Asia
			System.out.println(player.getName() + " was awarded 7 bonus troops for controlling all of Asia...");
			System.out.println(helper.daBar);
		}
		if(continents[2] == 4){
			bonus = bonus + 2; //Adds bonus for controlling all of Australia
			System.out.println(player.getName() + " was awarded 2 bonus troops for controlling all of Australia...");
			System.out.println(helper.daBar);
		}
		if(continents[3] == 7){
			bonus = bonus + 5; //Adds bonus for controlling all of Europe
			System.out.println(player.getName() + " was awarded 5 bonus troops for controlling all of Europe...");
			System.out.println(helper.daBar);
		}
		if(continents[4] == 9){
			bonus = bonus + 5; //Adds bonus for controlling all of North America
			System.out.println(player.getName() + " was awarded 5 bonus troops for controlling all of North America...");
			System.out.println(helper.daBar);
		}
		if(continents[5] == 4){
			bonus = bonus + 2; //Adds bonus for controlling all of South America
			System.out.println(player.getName() + " was awarded 2 bonus troops for controlling all of South America...");
			System.out.println(helper.daBar);
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
	
	//Checks if territory has troopCount above 2 and is adjacent to at least one territory controlled by a different player
	private boolean checkTerritoryAttackValidity(int ID, Player p){
		Territory tempTerr = wTerr.get(findIndexOf(ID, wTerr));
		if(tempTerr.getTroopCount() > 1){
			ArrayList<Integer> tempAdj = tempTerr.getAdjacencies();
			int count = 0;
			for(int j = 0; j < wTerr.size(); j++){
				for(int k = 0; k < tempAdj.size(); k++){
					if(wTerr.get(j).getID() == tempAdj.get(k) && wTerr.get(j).getOccupant().getID() != p.getID()){
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
	
	/*String version of player table, some issues present
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
	*/
}
