package territories;

import java.util.ArrayList;

import player.Player;

/**
 * Defines the properties of the Risk Country objects.
 * @author Grant Williams
 * 
 * @date 9/2/18
 **/

public class Country {
	
	private int troopCount; //keeps track of the number of troops currently occupying the country
	private boolean occupied; //boolean to check if a player occupies the country yet
	
    private String name; //name of the country itself
    private Player occupant; //name of the player who has control of the country
    private ArrayList<Country> adjacentCountries; //keeps track of all adjacent countries
    
    public Country(String name){
    	this.name = name;
    	occupied = false;
    	troopCount = 0;
    	
    	//System.out.println(name + " has been created.");
    }
    
    //Standard Get Methods
    public String getName(){
    	return name;
    }
    
    public boolean isOccupied(){
    	return occupied;
    }
    
    public Player getOccupant(){
    	return occupant;
    }
    
    public int getTroopCount(){
    	return troopCount;
    }
    
    public ArrayList<Country> getAdjacentCountries(){
    	return adjacentCountries;
    }
    
    //Adds list of all adjacent countries, to be used in construction of countries
    //Not to be used after whole board constructed
    public void addAdjacentCountries(ArrayList<Country> adjacentCountries){
    	this.adjacentCountries = adjacentCountries;
    	System.out.println(name + " is adjacent to: ");
    	for(int i = 0; i < this.adjacentCountries.size(); i++){
    		System.out.println(" -" + this.adjacentCountries.get(i).getName());
    	}
    	System.out.println();
    }
    
    //Player object set as occupant if that player controls the country
    public void setOccupant(Player occupant){
    	occupied = true;
    	this.occupant = occupant;
    }
    
    //Sets the number of troops currently occupying the country
    public void setTroopCount(int troopCount){
    	this.troopCount = troopCount;
    }
    
    //Increases the country's troopCount based on given number
    public void increaseTroopCount(int num) {
		troopCount = troopCount + num;
		System.out.println(occupant.getName() + " added " + troopCount + " armies to " + name + ".");
	}
	
    //Decreases the country's troopCount based on a given number
	public void decreaseTroopCount(int num) {
		troopCount = troopCount - num;
		System.out.println(occupant.getName() + " lost " + troopCount + " armies in " + name + ".");
	}
}
