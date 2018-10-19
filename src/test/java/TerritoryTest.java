import BaseGameEssentials.Territory;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerritoryTest {

    ArrayList<Integer> connections = new ArrayList<Integer>();

    Territory temp = new Territory("LaLaLand",777, "Moon", connections);


    @Test
    public void getName() {
        assertEquals("LaLaLand",temp.getName());
    }

    @Test
    public void getContinent() {
        assertEquals("Moon",temp.getContinent());
    }

    @Test
    public void setTeam() {
        temp.setTeam("A-team");
        assertEquals("A-team",temp.getTeam());
    }

    @Test
    public void setTroopCount() {
        temp.setTroopCount(2);
        assertEquals(2,temp.getTroopCount());
    }

    @Test
    public void addTroops() {
        int num = temp.getTroopCount();
        temp.addTroops(2);
        assertEquals(num+2,temp.getTroopCount());
    }

    @Test
    public void getTroopsById() {

        assertEquals(0, temp.GetTroopsById(777));
    }

    @Test
    public void getID() {
        assertEquals(777,temp.getID());
    }

    @Test
    public void getConnections() {
        connections.add(1); connections.add(2); connections.add(3);
        assertEquals(connections,temp.getConnections());
    }

    @Test
    public void getTeam() {
        temp.setTeam("A-team");
        assertEquals("A-team",temp.getTeam());
    }

    @Test
    public void getTroopCount() {
        int num = temp.getTroopCount();
        assertEquals(num,temp.getTroopCount());
    }
}