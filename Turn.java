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
	
	public Turn(){
	}
	
	public void runTurn(Player player){
		
		phaseOne(player);
		phaseTwo(player);
		phaseThree(player);
	}
	
	//Reinforcement phase of turn
	private void phaseOne(Player player){
		ArrayList<Territory> playerT = player.getTerritories();
		for(int i = 0; i < playerT.size() / 2; i++){
			playerT.get(i).increaseTroopCount(1);
		}
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
			System.out.println(player.getName() + " can choose to fortify from these " + validT.size() + " Territories: ");
			for(int i = 0; i < validT.size(); i++){
				Territory temp = validT.get(i);
				System.out.println(" -ID: " + temp.getID() + ", Name: " + temp.getName());
				System.out.println(" --Which is adjacent to: ");
				ArrayList<Integer> tempAdj = temp.getAdjacencies();
				for(int j = 0; j < tempAdj.size(); j++){
					System.out.println(" -->ID: " + tempAdj.get(j));
				}
				System.out.println();
			}
			System.out.println(player.getName() + " enter the ID of the territory you want to take troops from to fortify another.");
			String in = "";
			//Let player pick which territory to fortify
			//Fortify into territory A from territory B
		}
		else{
			System.out.println(player.getName() + " can not fortify at this time.");
		}
	}
	
}
