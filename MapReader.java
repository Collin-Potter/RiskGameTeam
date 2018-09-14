package maps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import territories.Territory;

/**
 * MapReader object reads in text files in a specific format to dynamically create maps
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class MapReader {
	
	private ArrayList<Territory> mTerritories;
	private String tName;
	private int tID;
	private String tContinent;
	private ArrayList<Integer> tAdjs;
	private BufferedReader br;
	private FileReader fr;
	
	public MapReader(){
		mTerritories = new ArrayList<Territory>();
		tID = 0;
		tName = null;
		tContinent = null;
		br = null;
		fr = null;
	}
	
	//Reads in text file to construct ArrayList of Territories
	//Assumes format: "Name;Continent;Adjacencies"
	public void readInMap(String filename){
		
		try{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = null;
			String[] tokens = null;
			int countID = 1;
			while((line = br.readLine()) != null){
				tokens = line.split(";");
				for(int i = 0; i < tokens.length; i++){
					tID = countID; //Territory ID
					tName = tokens[0]; //Territory Name
					tContinent = tokens[1]; //Territory Continent
					String[] adjs = tokens[2].split(","); //Split adjacency portion
					tAdjs = new ArrayList<Integer>();
					for(int j = 0; j < adjs.length; j++){
						tAdjs.add(Integer.parseInt(adjs[j])); 
					}
				}
				countID++;
				Territory t = new Territory(tID, tName, tContinent);
				t.addAdjacencies(tAdjs);
				mTerritories.add(t);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br != null){
					br.close();
				}
				if(fr != null){
					fr.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	//Returns filled out ArrayList of Territories that make up map
	public ArrayList<Territory> getMapTerritories(){
		return mTerritories;
	}
	
	//Prints all territories of map
	//Only to be called after readInMap() has been called
	public void printWorldMap(){
		for(int i = 0; i < mTerritories.size(); i++){
			Territory temp = mTerritories.get(i);
			System.out.println(temp.getID() + " -> " + temp.getName() + " of " + temp.getContinent() + " is adjacent to: ");
			ArrayList<Integer> tempAdjs = temp.getAdjacencies();
			for(int k = 0; k < tempAdjs.size(); k++){
				int tempID = tempAdjs.get(k);
				String tempName = getTerritoryName(tempID);
				if(!tempName.equals("Territory Not Found")){
					System.out.println(" -ID: " + tempID + ", Name: " + tempName);
				}
			}
			System.out.println();
		}
	}
	
	//Returns Name of Territory that matches given ID	
		public String getTerritoryName(int ID){
			String out = "Territory Not Found";
			for(int i = 0; i < mTerritories.size(); i++){
				if(mTerritories.get(i).getID() == ID){
					out = mTerritories.get(i).getName();
				}
			}
			return out;
		}
}
