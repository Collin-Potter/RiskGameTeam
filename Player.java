package player;

import java.util.ArrayList;

import territories.Territory;

/**
 * Defines the properties of the Risk Player objects.
 * @author Grant Williams
 * 
 * @date 9/1/18
 **/

public class Player {
	
	private String name; //keeps track of player's name
	private int armies; //keeps track of players remaining troop count
	private int ID; //keeps track of which player this is (player1, player2, etc)
	
	private ArrayList<Territory> territories; //ArrayList to keep track of territories the player controls
	
	//Constructor takes the name, army count, and color of player
	public Player(String name, int armies, int ID){
		this.name = name;
		this.armies = armies;
		this.ID = ID;
		territories = new ArrayList<Territory>();
	}
	
	//Standard Get Methods
	public String getName(){
		return name;
	}
	
	public int getArmies(){
		return armies;
	}
	
	public int getID(){
		return ID;
	}
	
	public ArrayList<Territory> getTerritories(){
		return territories;
	}
	
	//Increases player army count based on given number
	public void increaseArmies(int num){
		armies = armies + num;
		System.out.println(name + " has gained " + num + " armies and now has " + armies + " in total.");
	}
	
	//Decreases player army count based on given number
	public void decreaseArmies(int num){
		armies = armies - num;
		System.out.println(name + " has lost " + num + " armies and now has " + armies + " remaining.");
	}
	
	//Adds given territory to player
	public void addCountry(Territory t){
		territories.add(t);
		System.out.println(name + " now controls " + t.getName());
	}
	
	//Allows for multiple territories to be given to player by deep copy
	public void addTerritories(ArrayList<Territory> giveTerritories){
		for(int i = 0; i < giveTerritories.size(); i++){
			territories.add(giveTerritories.get(i));
			System.out.println(name + " now controls " + giveTerritories.get(i).getName());
		}
	}
	
	//Removes given country from player
	public void removeCountry(int removeID){
		for(int i = 0; i < territories.size(); i++){
			if(territories.get(i).getID() == removeID){
				System.out.println(name + " has lost control of " + territories.get(i).getName());
				territories.remove(i);
			}
		}
	}
	
	//Prints info of Player object
	public void printPlayer(){
		System.out.println(name + " is Player " + ID + " and has " + armies + " armies.");
	}
}

