package Main;

import java.util.ArrayList;

import maps.WorldMap;
import player.Player;
import territories.Country;
import territories.Continent;

/**
 * Main method to manipulate other Risk objects.
 * @author Grant Williams
 * 
 * @date 9/1/18
 **/

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player p = new Player("Grant", 21, 1);
		System.out.println(p.getName() + " is player " + p.getID());
		System.out.println(p.getName() + " has " + p.getArmies() + " armies.");
		
		//World Map creation
		WorldMap world = new WorldMap();
		world.createWorldMap();
		
		
		
		
		
	}

}
