package maps;

import java.util.ArrayList;

import territories.Country;
import territories.Territory;
import territories.Continent;

/**
 * Creates all the countries and continents of the world map.
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class WorldMap {
	
	private ArrayList<Country> worldCountries;
	private ArrayList<Continent> worldContinents;
	private ArrayList<Country> tempAdjacencies;
	
	private ArrayList<Territory> worldTerritories;
	private ArrayList<Integer> tempIDs;
	
	public WorldMap(){
		worldCountries = new ArrayList<Country>();
		worldContinents = new ArrayList<Continent>();
		tempAdjacencies = new ArrayList<Country>();
		
		worldTerritories = new ArrayList<Territory>();
		tempIDs = new ArrayList<Integer>();
		
	}
	
	public void createWorldMap(){
		//AFRICA
			//CONGO
			Territory congo = new Territory(8, "Congo", "Africa");
			tempIDs.add(9); tempIDs.add(24); tempIDs.add(34);
			congo.addAdjacencies(tempIDs); 
			tempIDs.clear();
			worldTerritories.add(congo);
			//EAST AFRICA
			Territory eAfrica = new Territory(9, "East Africa", "Africa");
			tempIDs.add(8); tempIDs.add(12); tempIDs.add(21); tempIDs.add(22); tempIDs.add(25); tempIDs.add(34);
			eAfrica.addAdjacencies(tempIDs); 
			tempIDs.clear();
			worldTerritories.add(eAfrica);
			//EGYPT
			Territory egypt = new Territory(12, "Egypt", "Africa");
			tempIDs.add(8); tempIDs.add(9); tempIDs.add(21); tempIDs.add(22); tempIDs.add(25); tempIDs.add(35);
			egypt.addAdjacencies(tempIDs);
			tempIDs.clear();
			worldTerritories.add(egypt);
			//MADAGASCAR
			Territory madagascar = new Territory(21, "Madagascar", "Africa");
			tempIDs.add(9); tempIDs.add(34);
			madagascar.addAdjacencies(tempIDs);
			tempIDs.clear();
			worldTerritories.add(madagascar);
			//NORTH AFRICA
			Territory nAfrica = new Territory(25, "North Africa", "Africa");
			tempIDs.add(5); tempIDs.add(8); tempIDs.add(9); tempIDs.add(12); tempIDs.add(35); tempIDs.add(40);
			nAfrica.addAdjacencies(tempIDs);
			tempIDs.clear();
			worldTerritories.add(nAfrica);
			//SOUTH AFRICA
			Territory sAfrica = new Territory(34, "South Africa", "Africa");
			tempIDs.add(8); tempIDs.add(9); tempIDs.add(21);
			sAfrica.addAdjacencies(tempIDs);
			tempIDs.clear();
			worldTerritories.add(sAfrica);
			
	}
	
	public ArrayList<Territory> getWorldTerritories(){
		return worldTerritories;
	}
	
	public void printWorldMap(){
		for(int i = 0; i < worldTerritories.size(); i++){
			Territory temp = worldTerritories.get(i);
			System.out.println(temp.getName() + " of " + temp.getContinent() + " is adjacent to: ");
			
		}
	}
	
	public void createWorldMapCountries(){
		
		//COUNTRIES OF AFRICA
		Country congo = new Country("Congo");
		Country eastAfrica = new Country("East Africa");
		Country egypt = new Country("Egypt");
		Country madagascar = new Country("Madagascar");
		Country northAfrica = new Country("North Africa");
		Country southAfrica = new Country("South Africa");
		
		//COUNTRIES OF ASIA
		Country afghanistan = new Country("Afghanistan");
		Country china = new Country("China");
		Country india = new Country("India");
		Country irkutsk = new Country("Irkutsk");
		Country japan = new Country("Japan");
		Country kamchatka = new Country("kamchatka");
		Country middleEast = new Country("Middle East");
		Country mongolia = new Country("Mongolia");
		Country siam = new Country("Siam");
		Country siberia = new Country("Siberia");
		Country ural = new Country("Ural");
		Country yakutsk = new Country("Yakutsk");
		
		//COUNTRIES OF AUSTRALIA
		Country eastAustralia = new Country("Eastern Australia");
		Country indonesia = new Country("Indonesia");
		Country newGuinea = new Country("New Guinea");
		Country westAustralia = new Country("Western Australia");
		
		//COUNTRIES OF EUROPE
		Country greatBritain = new Country("Great Britain");
		Country iceland = new Country("Iceland");
		Country northEurope = new Country("Northern Europe");
		Country scandanavia = new Country("Scandanavia");
		Country southEurope = new Country("Southern Europe");
		Country ukraine = new Country("Ukraine");
		Country westEurope = new Country("Western Europe");
				
		//COUNTRIES OF NORTH AMERICA
		Country alaska = new Country("Alaska");
		Country alberta = new Country("Alberta");
		Country centralAmerica = new Country("Central America");
		Country eastUS = new Country("Eastern United States");
		Country greenland = new Country("Greenland");
		Country northwestTerritory = new Country("Northwest Territory");
		Country ontario = new Country("Ontario");
		Country quebec = new Country("Quebec");
		Country westUS = new Country("Western United States");
		
		//COUNTRIES OF SOUTH AMERICA
		Country argentina = new Country("Argentina");
		Country brazil = new Country("Brazil");
		Country peru = new Country("Peru");
		Country venezuela = new Country("Venezuela");
		
		//ADJACENCIES OF AFRICA
			//CONGO
			tempAdjacencies.add(eastAfrica);
			tempAdjacencies.add(northAfrica);
			tempAdjacencies.add(southAfrica);
			congo.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//EAST AFRICA
			tempAdjacencies.add(congo);
			tempAdjacencies.add(egypt);
			tempAdjacencies.add(madagascar);
			tempAdjacencies.add(middleEast);
			tempAdjacencies.add(northAfrica);
			tempAdjacencies.add(southAfrica);
			eastAfrica.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//EGYPT
			tempAdjacencies.add(eastAfrica);
			tempAdjacencies.add(middleEast);
			tempAdjacencies.add(northAfrica);
			tempAdjacencies.add(southEurope);
			egypt.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//MADAGASCAR
			tempAdjacencies.add(eastAfrica);
			tempAdjacencies.add(southAfrica);
			madagascar.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//NORTH AFRICA
			tempAdjacencies.add(brazil);
			tempAdjacencies.add(congo);
			tempAdjacencies.add(eastAfrica);
			tempAdjacencies.add(egypt);
			tempAdjacencies.add(southEurope);
			tempAdjacencies.add(westEurope);
			northAfrica.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//SOUTH AFRICA
			tempAdjacencies.add(congo);
			tempAdjacencies.add(eastAfrica);
			tempAdjacencies.add(madagascar);
			southAfrica.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			
		//ADJACENCIES OF ASIA
			//AFGHANISTAN
			tempAdjacencies.add(china);
			tempAdjacencies.add(india);
			tempAdjacencies.add(middleEast);
			tempAdjacencies.add(ukraine);
			tempAdjacencies.add(ural);
			afghanistan.addAdjacentCountries(tempAdjacencies);
			tempAdjacencies.clear();
			//CHINA
			
	}
}
