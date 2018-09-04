package territories;

import java.util.ArrayList;
import player.Player;

/**
 * Defines the properties of the Risk Territory objects.
 * @author Grant Williams
 * 
 * @date 9/2/18
 **/

public class Territory {

	private int ID;
	private String name;
	private String continent;
	private int troopCount;
	private boolean occupied;
	private Player occupant;
	private ArrayList<Integer> adjTerritories;
	
	public Territory(int ID, String name, String continent){
		this.ID = ID;
		this.name = name;
		this.continent = continent;
		troopCount = 0;
		occupied = false;
		adjTerritories = new ArrayList<Integer>();
		
	}
	
	//Standard Get Methods
	public int getID(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getContinent(){
		return continent;
	}
	
	public int getTroopCount(){
		return troopCount;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	
	public Player getOccupant(){
		return occupant;
	}
	
	public ArrayList<Integer> getAdjacencies(){
		return adjTerritories;
	}
	
	//Adds list of all adjacent territories, to be used in construction of territories
    //Not to be used after whole board constructed
    public void addAdjacencies(ArrayList<Integer> adjacencies){
    	adjTerritories = adjacencies;
    	System.out.println(name + " of " + continent + " is adjacent to: ");
    	for(int i = 0; i < adjTerritories.size(); i++){
    		System.out.println("-Territory ID: " + adjTerritories.get(i));
    	}
    	System.out.println();
    }
    
  //Player object set as occupant if that player controls the territory
    public void setOccupant(Player occupant){
    	occupied = true;
    	this.occupant = occupant;
    }
    
    //Sets the number of troops currently occupying the territory
    public void setTroopCount(int troopCount){
    	this.troopCount = troopCount;
    }
    
    //Increases the territory's troopCount based on given number
    public void increaseTroopCount(int num) {
		troopCount = troopCount + num;
		System.out.println(occupant.getName() + " added " + troopCount + " armies to " + name + ".");
	}
	
    //Decreases the territory's troopCount based on a given number
	public void decreaseTroopCount(int num) {
		troopCount = troopCount - num;
		System.out.println(occupant.getName() + " lost " + troopCount + " armies in " + name + ".");
	}
}

