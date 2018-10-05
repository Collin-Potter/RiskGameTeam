import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class PlayerTest {
    ArrayList<Integer> connections = new ArrayList<Integer>();
    Territory temp = new Territory("LaLaLand",777, "Asia", connections);
    ArrayList<Territory> territoryList = new ArrayList<Territory>();
    ArrayList<Integer> countryID = new ArrayList<Integer>();
    Player p = new Player(0, "Ateam", false,false,1);
    @Test
    public void getCountryID() {
        for(int i=0;i<5;i++){
            territoryList.add(temp);
            temp.setTeam("Ateam");
        }

        ArrayList<Integer> ID = new ArrayList<Integer>();
        ID = p.getCountryID(territoryList);
        assertEquals(5,ID.size());
    }

    @Test
    public void setTurn() {
        int id = p.setTurn();
        assertEquals(2,id);
    }

    @Test
    public void setTroopCount() {
         p.setTroopCount(4);
        int troopCount = p.getTroopCount();
        assertEquals(4, troopCount);
    }

    @Test
    public void addTroops() {
        p.addTroops(2);
        int troopCount = p.getTroopCount();
        assertEquals(2, troopCount);
    }

    @Test
    public void decTroopCount() {
        p.setTroopCount(4);
        p.decTroopCount(2);
        int troopCount = p.getTroopCount();
        assertEquals(2, troopCount);
    }

    @Test
    public void getTroopCount() {
        p.setTroopCount(4);
        int troopCount = p.getTroopCount();
        assertEquals(4, troopCount);
    }

    @Test
    public void getTeam() {
        assertEquals("Ateam", p.getTeam());
    }

    @Test
    public void getTerritoryCount() {
        p.setTerritoryCount(8);
        int count = p.getTerritoryCount();
        assertEquals(8, count);
    }

    @Test
    public void setTerritoryCount() {
        p.setTerritoryCount(8);
        int count = p.getTerritoryCount();
        assertEquals(8, count);
    }

    @Test
    public void incTerritoryCount() {
        p.incTerritoryCount();
        int count = p.getTerritoryCount();
        assertEquals(1, count);
    }

    @Test
    public void reinforceRegions() {

        p.reinforceRegions(8);
        assertEquals(false, p.checker);
    }

    @Test
    public void percentageInControl() {
        p.percentageInControl();
        assertEquals(0 , p.TestPrecentage);
    }
}