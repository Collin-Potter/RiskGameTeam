package entities;

import java.util.ArrayList;
import java.util.Collections;

import entities.Territory;
import main.Helper;

/**
 * Defines the properties of the Risk Player objects.
 * @author Grant Williams
 * 
 * @date 9/1/18
 **/

public class Player {
	
	private String name; //keeps track of player's name
	private int troops; //keeps track of players remaining troop count
	private int ID; //keeps track of which player this is (player1, player2, etc)
	private int recentlyAddedTerr; //keeps track of how many territories a player acquires per turn
	private int recentlyLostTerr; //keeps track of how many territories lost by a player in a turn
	
	private ArrayList<Territory> territories; //ArrayList to keep track of territories the player controls
	
	//Constructor takes the name, army count, and color of player
	public Player(String name, int troops, int ID){
		this.name = name;
		this.troops = troops;
		this.ID = ID;
		recentlyAddedTerr = 0;
		recentlyLostTerr = 0;
		territories = new ArrayList<Territory>();
	}
	
	//Standard Get Methods
	public String getName(){
		return name;
	}
	
	public int getTroops(){
		return troops;
	}
	
	public int getID(){
		return ID;
	}
	
	public ArrayList<Territory> getTerritories(){
		return territories;
	}
	
	public int getRecentlyAdded(){
		return recentlyAddedTerr;
	}
	
	public int getRecentlyLost() {
		return recentlyLostTerr;
	}
	
	//Set recently added to number given
	public void setRecentlyAdded(int num){
		recentlyAddedTerr = num;
	}
	
	//Set recently lost to number given
	public void setRecentlyLost(int recentlyLostTerr) {
		this.recentlyLostTerr = recentlyLostTerr;
	}
	
	//Increases player army count based on given number
	public void increaseTroops(int num){
		troops = troops + num;
		//System.out.println(name + " has gained " + num + " troop(s) and now has " + troops + " in total.");
	}
	
	//Decreases player army count based on given number
	public void decreaseTroops(int num){
		troops = troops - num;
		//System.out.println(name + " has lost " + num + " troop(s) and now has " + troops + " remaining.");
	}
	
	//Adds given territory to player
	public void addTerritory(Territory t){
		territories.add(t);
		//System.out.println(name + " now controls " + t.getName());
		Collections.sort(territories, (t1, t2) -> t1.getID() - t2.getID()); //sorts territories into alphabetical order
	}
	
	//Allows for multiple territories to be given to player by deep copy
	public void addTerritories(ArrayList<Territory> giveTerritories){
		for(int i = 0; i < giveTerritories.size(); i++){
			territories.add(giveTerritories.get(i));
			//System.out.println(name + " now controls " + giveTerritories.get(i).getName());
		}
		Collections.sort(territories, (t1, t2) -> t1.getID() - t2.getID()); //sorts territories into alphabetical order
	}
	
	//Removes given territory from player
	public void removeTerritory(int removeID){
		for(int i = 0; i < territories.size(); i++){
			if(territories.get(i).getID() == removeID){
				territories.remove(i);
			}
		}
	}
	
	//Prints info of Player object
	public void printPlayer(){
		System.out.println(name + " is Player " + ID + " and has " + troops + " troop(s).");
	}
}

