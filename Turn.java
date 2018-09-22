package events;

import java.util.ArrayList;
import java.util.Scanner;

import player.Player;
import territories.Territory;

/**
 * Turn class simulates one round for one Player with three phases
 * @author Grant Williams
 * 
 * @date 9/10/18
 **/

public class Turn {
	
	private Scanner userInput;
	private boolean validInput;
	private String in;
	private ArrayList<Territory> wTerr;
	
	public Turn(){
		userInput = new Scanner(System.in);
		validInput = false;
		in = "";
		wTerr = new ArrayList<Territory>();
	}
	
	public void runTurn(ArrayList<Player> players, int turnCount, ArrayList<Territory> wTerritories){
		wTerr = wTerritories;
		//Reinforcement Phase
		System.out.println("COMMENCING REINFORCEMENT PHASE OF TURN: " + turnCount);
		System.out.println("+=============================================================================================================================================================+");
		if(turnCount > 1){
			for(int i = 0; i < players.size(); i++){
				int numTerr = players.get(i).getTerritories().size();
				int bonusTroop = numTerr/3;
				players.get(i).increaseTroops(bonusTroop);
			}
		}
		int playerCount = players.size();
		while(playerCount > 0){
			for(int i = 0; i < players.size(); i++){
				if(players.get(i).getTroops() > 0){
					phaseOne(players.get(i));
				}
				else{
					playerCount--;
					System.out.println(players.get(i).getName() + " is out of reinforcements...");
					System.out.println("---------------------------------------------------------------------");
				}
			}
		}
		System.out.println("REINFORCEMENT PHASE COMPLETE");
		System.out.println("+=============================================================================================================================================================+");
		
		//Attack Phase
		System.out.println("COMMENCING ATTACK PHASE OF TURN: " + turnCount);
		System.out.println("+=============================================================================================================================================================+");
		//phaseTwo(player);
		System.out.println("ATTACK PHASE COMPLETE");
		System.out.println("+=============================================================================================================================================================+");
		
		//Fortify Phase
		System.out.println("COMMENCING FORTIFY PHASE OF TURN: " + turnCount);
		System.out.println("+=============================================================================================================================================================+");
		for(int i = 0; i < players.size(); i++){
			phaseThree(players.get(i));
		}
		System.out.println("FORTIFY PHASE COMPLETE");
		System.out.println("+=============================================================================================================================================================+");
	}
	
	//Reinforcement phase of turn
	private void phaseOne(Player player){
		System.out.println("Running Reinforce Phase for " + player.getName());
		System.out.println("---------------------------------------------------------------------");
		ArrayList<Territory> pTerr = player.getTerritories();
		validInput = false; 
		in = "";
		System.out.println(player.getName() + " you have " + player.getTroops() + " troops to distribute amongst " + player.getTerritories().size() + " territories...");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Current Territories: ");
		//Upper Table Bounds
		System.out.print(" .");
		for(int i = 0; i < 58; i++){
			System.out.print("=");
		}
		System.out.print(".");
		System.out.println();
		for(int i = 0; i < pTerr.size(); i++){
			//System.out.println("||ID: " + pTerr.get(i).getID() + "||Name: " + pTerr.get(i).getName() + "||Troop Count: " + pTerr.get(i).getTroopCount());
			printTerrInfo(pTerr.get(i));
		}
		//Lower Table Bounds
		System.out.print(" '");
		for(int i = 0; i < 58; i++){
			System.out.print("=");
		}
		System.out.print("'");
		System.out.println();
		System.out.println("---------------------------------------------------------------------");
		while(!validInput){
			System.out.println(player.getName() + " enter the ID of the Territory you want to place a troop in: ");
			in = userInput.nextLine();
			System.out.println("---------------------------------------------------------------------");
			validInput = validPhaseOne(in, pTerr);
			if(!validInput){
				System.out.println("Invalid input. Please try again.");
				System.out.println("---------------------------------------------------------------------");
			}
		}
		int tempID = Integer.parseInt(in);
		placeTroopInto(tempID, pTerr);
		player.decreaseTroops(1);
	}
	
	//Attack phase of turn
	private void phaseTwo(Player player){
		
	}
	
	//Fortify phase of turn
	private void phaseThree(Player player){
		//Check if fortification possible
		ArrayList<Territory> playerT = player.getTerritories();
		ArrayList<Territory> validT = new ArrayList<Territory>();
		int validCount = 0;
		for(int i = 0; i < playerT.size(); i++){
			if(playerT.get(i).getTroopCount() >= 2){
				validT.add(playerT.get(i));
				validCount++;
			}
		}
		if(validCount > 0){
			//Let player pick which to fortify from
			System.out.println("Current valid territories for " + player.getName() + ": \n");
			for(int i = 0; i < validT.size(); i++){
				Territory temp = validT.get(i);
				System.out.println(" -ID: " + temp.getID() + ", Name: " + temp.getName() + ", Troop Count: " + temp.getTroopCount());
				System.out.println(" --Which is adjacent to: ");
				ArrayList<Integer> tempAdj = temp.getAdjacencies();
				for(int j = 0; j < tempAdj.size(); j++){
					printIfValid(tempAdj.get(i), player);
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------------------------------");
			System.out.println(player.getName() + " enter the ID of the territory you want to take troops from to fortify another.");
			in = userInput.nextLine();
			//Let player pick which territory to fortify
			//Fortify into territory A from territory B
		}
		else{
			System.out.println(player.getName() + " can not fortify at this time.");
		}
	}
	
	//Checks the validity of input for phaseOne method
	private boolean validPhaseOne(String input, ArrayList<Territory> pTerr){
		try{
			int check = Integer.parseInt(in);
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
	
	//Places troop into territory by given ID
	private void placeTroopInto(int ID, ArrayList<Territory> pTerr){
		for(int i = 0; i < pTerr.size(); i++){
			if(pTerr.get(i).getID() == ID){
				System.out.println("Placing one troop in " + pTerr.get(i).getName() + "...");
				System.out.println("---------------------------------------------------------------------");
				pTerr.get(i).increaseTroopCount(1);
				//System.out.println();
			}
		}
	}
	
	//Prints info of territory for phaseOne method
	private void printTerrInfo(Territory t){
		//ID Section
		if(t.getID() < 10){
			System.out.print("|| ID: " + t.getID() + "  |");
		}
		else{
			System.out.print("|| ID: " + t.getID() + " |");
		}
		//Name Section
		System.out.print("| Name: " + t.getName());
		for(int i = 0; i < (21-t.getName().length()); i++){
			System.out.print(" ");
		}
		System.out.print(" |");
		//Troop Count Section
		if(t.getTroopCount() < 10){
			System.out.print("| Troop Count: " + t.getTroopCount() + "  ||");
		}
		else{
			System.out.print("| Troop Count: " + t.getTroopCount() + " ||");
		}
		System.out.println();
	}
	
	//Prints territory info based on ID and player occupant to be used in phaseThree method
	private void printIfValid(int ID, Player p){
		for(int i = 0; i < wTerr.size(); i++){
			if(wTerr.get(i).getID() == ID && wTerr.get(i).getOccupant().getID() == p.getID()){
				System.out.print("--> ID: " + ID + wTerr.get(i).getName() + ", Troop Count:");
			}
		}
	}
}
