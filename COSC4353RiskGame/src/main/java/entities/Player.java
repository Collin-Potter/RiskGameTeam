package entities;

import java.util.ArrayList;
import java.util.Collections;

import entities.Territory;

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
	
	private ArrayList<Territory> territories; //ArrayList to keep track of territories the player controls
	
	//Constructor takes the name, army count, and color of player
	public Player(String name, int troops, int ID){
		this.name = name;
		this.troops = troops;
		this.ID = ID;
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
	
	//Removes given country from player
	public void removeCountry(int removeID){
		for(int i = 0; i < territories.size(); i++){
			if(territories.get(i).getID() == removeID){
				System.out.println(name + " has lost control of " + territories.get(i).getName());
				System.out.println("---------------------------------------------------------------------------------------------------------");
				territories.remove(i);
			}
		}
	}
	
	//Prints info of Player object
	public void printPlayer(){
		System.out.println(name + " is Player " + ID + " and has " + troops + " troop(s).");
	}
	
}

