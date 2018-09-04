package player;

import java.util.ArrayList;

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
	
	private ArrayList<String> countries; //ArrayList to keep track of countries the player controls
	private ArrayList<String> continents; //ArrayList to keep track of entire continents player controls
	
	//Constructor takes the name, army count, and color of player
	public Player(String name, int armies, int ID){
		this.name = name;
		this.armies = armies;
		this.ID = ID;
		countries = new ArrayList<String>();
		continents = new ArrayList<String>();
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
	
	public ArrayList<String> getCountries(){
		return countries;
	}
	
	public ArrayList<String> getContinents(){
		return continents;
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
	
	//Adds given country to player
	public void addCountry(String country){
		countries.add(country);
		System.out.println(name + " now controls " + country);
	}
	
	//Allows for multiple countries to be given to player
	public void addCountries(ArrayList<String> giveCountries){
		for(int i = 0; i < giveCountries.size(); i++){
			countries.add(giveCountries.get(i));
			System.out.println(name + " now controls " + giveCountries.get(i));
		}
	}
	
	//Removes given country from player
	public void removeCountry(String country){
		for(int i = 0; i < countries.size(); i++){
			if(countries.get(i).equals(country)){
				System.out.println(name + " has lost control of " + countries.get(i));
				countries.remove(i);
			}
		}
	}
	
	//Adds given continent to player, indicating that the player controls all countries on given continent
	public void addContinent(String continent){
		continents.add(continent);
		System.out.println(name + " now controls all of " + continent);
	}
	
	//Removes given continent from player, indicating that the player no longer controls all countries of given continent
	public void removeContinent(String continent){
		for(int i = 0; i < continents.size(); i++){
			if(continents.get(i).equals(continent)){
				continents.remove(i);
				System.out.println(name + " no longer controls all of " + continent);
			}
		}
	}
}

