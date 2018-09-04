package territories;

import java.util.ArrayList;

/**
 * Defines the properties of the Risk Continent objects.
 * @author Grant Williams
 * 
 * @date 9/2/18
 **/

public class Continent {
	
	private String name; //name of continent itself
	private int bonusArmies; //number of additional troops a player will receive if they control whole continent
	private ArrayList<Country> countries; //keeps track of all countries that make up the continent
	
	public Continent(String name, int bonusArmies, ArrayList<Country> countries){
		this.name = name;
		this.bonusArmies = bonusArmies;
		this.countries = countries;
		
		System.out.println("Created Continent of " + name + ". \nWith Bonus Armies of " + bonusArmies);
	}
	
	//Standard Get Methods
	public String getName(){
		return name;
	}
	
	public int getBonusArmies(){
		return bonusArmies;
	}
	
	public ArrayList<Country> getCountries(){
		return countries;
	}
}
