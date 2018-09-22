package maps;

import java.util.ArrayList;

import territories.Territory;

/**
 * Creates all the countries and continents of the world map.
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class WorldMap {
	
	private ArrayList<Territory> worldTerritories;
	
	public WorldMap(){
		worldTerritories = new ArrayList<Territory>();
	}
	
	public void createWorldMap(){
		//AFRICA
			//CONGO
			int[] tempAdj = {9, 24, 34};
			Territory congo = new Territory(8, "Congo", "Africa", tempAdj);
			worldTerritories.add(congo);
			//EAST AFRICA
			int[] tempAdj1 = {8, 12, 21, 22, 25, 34};
			Territory eAfrica = new Territory(9, "East Africa", "Africa", tempAdj1);
			worldTerritories.add(eAfrica);
			//EGYPT
			int[] tempAdj2 = {9, 22, 25, 35};
			Territory egypt = new Territory(12, "Egypt", "Africa", tempAdj2);
			worldTerritories.add(egypt);
			//MADAGASCAR
			int[] tempAdj3 = {9, 34};
			Territory madagascar = new Territory(21, "Madagascar", "Africa", tempAdj3);
			worldTerritories.add(madagascar);
			//NORTH AFRICA
			int[] tempAdj4 = {5, 8, 9, 12, 35, 40};
			Territory nAfrica = new Territory(25, "North Africa", "Africa", tempAdj4);
			worldTerritories.add(nAfrica);
			//SOUTH AFRICA
			int[] tempAdj5 = {8, 9, 21};
			Territory sAfrica = new Territory(34, "South Africa", "Africa", tempAdj5);
			worldTerritories.add(sAfrica);
		//ASIA
			//AFGHANISTAN
			int[] tempAdj6 = {7, 16, 22, 36, 37};
			Territory afghanistan = new Territory(1, "Afghanistan", "Asia", tempAdj6);
			worldTerritories.add(afghanistan);
			//CHINA
			int[] tempAdj7 = {1, 16, 23, 32, 33, 37};
			Territory china = new Territory(7, "China", "Asia", tempAdj7);
			worldTerritories.add(china);
			//INDIA
			int[] tempAdj8 = {1, 7, 22, 32};
			Territory india = new Territory(16, "India", "Asia", tempAdj8);
			worldTerritories.add(india);
			//IRKUTSK
			int[] tempAdj9 = {19, 20, 23, 33, 42};
			Territory irkutsk = new Territory(18, "Irkutsk", "Asia", tempAdj9);
			worldTerritories.add(irkutsk);
			//JAPAN
			int[] tempAdj10 = {18, 20, 23};
			Territory japan = new Territory(19, "Japan", "Asia", tempAdj10);
			worldTerritories.add(japan);
			//KAMCHATKA
			int[] tempAdj11 = {2, 18, 19, 42};
			Territory kamchatka = new Territory(20, "Kamchatka", "Asia", tempAdj11);
			worldTerritories.add(kamchatka);
			//MIDDLE EAST
			int[] tempAdj12 = {1, 9, 12, 16, 35, 36};
			Territory midEast = new Territory(22, "Middle East", "Asia", tempAdj12);
			worldTerritories.add(midEast);
			//MONGOLIA
			int[] tempAdj13 = {7, 18, 19, 33};
			Territory mongolia = new Territory(23, "Mongolia", "Asia", tempAdj13);
			worldTerritories.add(mongolia);
			//SIAM
			int[] tempAdj14 = {7, 16, 17};
			Territory siam = new Territory(32, "Siam", "Asia", tempAdj14);
			worldTerritories.add(siam);
			//SIBERIA
			int[] tempAdj15 = {7, 18, 23, 37, 42};
			Territory siberia = new Territory(33, "Siberia", "Asia", tempAdj15);
			worldTerritories.add(siberia);
			//URAL
			int[] tempAdj16 = {1, 7, 33, 36};
			Territory ural = new Territory(37, "Ural", "Asia", tempAdj16);
			worldTerritories.add(ural);
			//YAKUTSK
			int[] tempAdj17 = {18, 20, 33};
			Territory yakutsk = new Territory(42, "Yakutsk", "Asia", tempAdj17);
			worldTerritories.add(yakutsk);
		//AUSTRALIA
			//EASTERN AUSTRALIA
			int[] tempAdj18 = {24,39};
			Territory eAustralia = new Territory(10, "Eastern Australia", "Australia", tempAdj18);
			worldTerritories.add(eAustralia);
			//INDONESIA
			int[] tempAdj19 = {24, 32, 39};
			Territory indonesia = new Territory(17, "Indonesia", "Australia", tempAdj19);
			worldTerritories.add(indonesia);
			//NEW GUINEA
			int[] tempAdj20 = {10, 17, 39};
			Territory nGuinea = new Territory(24, "New Guinea", "Australia", tempAdj20);
			worldTerritories.add(nGuinea);
			//WESTERN AUSTRALIA
			int[] tempAdj21 = {10, 17, 24};
			Territory wAustralia = new Territory(39, "Western Australia", "Australia", tempAdj21);
			worldTerritories.add(wAustralia);
		//EUROPE
			//GREAT BRITAIN
			int[] tempAdj22 = {15, 26, 31, 40};
			Territory gBritain = new Territory(13, "Great Britain", "Europe", tempAdj22);
			worldTerritories.add(gBritain);
			//ICELAND
			int[] tempAdj23 = {13, 14, 31};
			Territory iceland = new Territory(15, "Iceland", "Europe", tempAdj23);
			worldTerritories.add(iceland);
			//NORTHERN EUROPE
			int[] tempAdj24 = {13, 31, 35, 36, 40};
			Territory nEurope = new Territory(26, "Northern Europe", "Europe", tempAdj24);
			worldTerritories.add(nEurope);
			//SCANDANAVIA
			int[] tempAdj25 = {13, 15, 26, 36};
			Territory scandanavia = new Territory(31, "Scandanavia", "Europe", tempAdj25);
			worldTerritories.add(scandanavia);
			//SOUTHERN EUROPE
			int[] tempAdj26 = {12, 22, 25, 26, 36, 40};
			Territory sEurope = new Territory(35, "Southern Europe", "Europe", tempAdj26);
			worldTerritories.add(sEurope);
			//UKRAINE
			int[] tempAdj27 = {1, 22, 26, 31, 35, 37};
			Territory ukraine = new Territory(36, "Ukraine", "Europe", tempAdj27);
			worldTerritories.add(ukraine);
			//WESTERN EUROPE
			int[] tempAdj28 = {13, 25, 26, 35};
			Territory wEurope = new Territory(40, "Western Europe", "Europe", tempAdj28);
			worldTerritories.add(wEurope);
		//NORTH AMERICA
			//ALASKA
			int[] tempAdj29 = {3, 20, 27};
			Territory alaska = new Territory(2, "Alaska", "North America", tempAdj29);
			worldTerritories.add(alaska);
			//ALBERTA
			int[] tempAdj30 = {2, 27, 28, 41};
			Territory alberta = new Territory(3, "Alberta", "North America", tempAdj30);
			worldTerritories.add(alberta);
			//CENTRAL AMERICA
			int[] tempAdj31 = {11, 41, 38};
			Territory cAmerica = new Territory(6, "Central America", "North America", tempAdj31);
			worldTerritories.add(cAmerica);
			//EASTERN UNITED STATES
			int[] tempAdj32 = {6, 28, 30, 41};
			Territory eUS = new Territory(11, "Eastern United States", "North America", tempAdj32);
			worldTerritories.add(eUS);
			//GREENLAND
			int[] tempAdj33 = {15, 28, 30, 27};
			Territory greenland = new Territory(14, "Greenland", "North America", tempAdj33);
			worldTerritories.add(greenland);
			//NORTHWEST TERRITORY
			int[] tempAdj34 = {2, 3, 14, 28};
			Territory nwTerritory = new Territory(27, "Northwest Territory", "North America", tempAdj34);
			worldTerritories.add(nwTerritory);
			//ONTARIO
			int [] tempAdj35 = {3, 11, 14, 27, 28, 41};
			Territory ontario = new Territory(28, "Ontario", "North America", tempAdj35);
			worldTerritories.add(ontario);
			//QUEBEC
			int[] tempAdj36 = {11, 14, 28};
			Territory quebec = new Territory(30, "Quebec", "North America", tempAdj36);
			worldTerritories.add(quebec);
			//WESTERN UNITED STATES
			int[] tempAdj37 = {3, 6, 11, 28};
			Territory wUS = new Territory(41, "Western United States", "North America", tempAdj37);
			worldTerritories.add(wUS);
		//SOUTH AMERICA
			//ARGENTINA
			int[] tempAdj38 = {5, 29, 38};
			Territory argentina = new Territory(4, "Argentina", "South America", tempAdj38);
			worldTerritories.add(argentina);
			//BRAZIL
			int[] tempAdj39 = {4, 25, 29, 38};
			Territory brazil = new Territory(5, "Brazil", "South America", tempAdj39);
			worldTerritories.add(brazil);
			//PERU
			int[] tempAdj40 = {4, 5, 38};
			Territory peru = new Territory(29, "Peru", "South America", tempAdj40);
			worldTerritories.add(peru);
			//VENEZUELA
			int[] tempAdj41 = {5, 6, 29};
			Territory venezuela = new Territory(38, "Venezuela", "South America", tempAdj41);
			worldTerritories.add(venezuela);
			
			
	}
	
	public ArrayList<Territory> getWorldTerritories(){
		return worldTerritories;
	}
	
	//Prints all territories of WorldMap
	//Only to be called after createWorldMap() has been called
		public void printWorldMap(){
			for(int i = 0; i < worldTerritories.size(); i++){
				Territory temp = worldTerritories.get(i);
				System.out.println(temp.getName() + " of " + temp.getContinent() + " is adjacent to: ");
				ArrayList<Integer> tempAdjs = temp.getAdjacencies();
				for(int k = 0; k < tempAdjs.size(); k++){
					int tempID = tempAdjs.get(k);
					String tempName = getTerritoryName(tempID);
					System.out.println(" -ID: " + tempID + ", Name: " + tempName);
				}
				System.out.println();
			}
		}
		
	//Returns Name of Territory that matches given ID	
	public String getTerritoryName(int ID){
		String out = "Territory Not Found";
		for(int i = 0; i < worldTerritories.size(); i++){
			if(worldTerritories.get(i).getID() == ID){
				out = worldTerritories.get(i).getName();
			}
		}
		return out;
	}
	
	public int getTerritoryID(String name){
		int out = 0;
		for(int i = 0; i < worldTerritories.size(); i++){
			if(worldTerritories.get(i).getName().matches(name)){
				out = worldTerritories.get(i).getID();
			}
		}
		return out;
	}
}