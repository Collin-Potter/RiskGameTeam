package BaseGameEssentials;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class PlayerTest {
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @BeforeClass
    public static void setup() {
        Player User1 = new Player(0, "Sanaz", false, false, 1);
        Player User2 = new Player(0, "Collin", false, false, 1);
        User1.setTroopCount(35);
        User2.setTroopCount(35);
        playerList.add(User1);
        playerList.add(User2);
        Game.readFileTerritories();
        Game.TelegramTerritoryDistribution();
    }

    @AfterClass
    public static void cleanUp(){
        playerList.clear();
        territoryList.clear();
    }

    @Test
    public void setShopping() {
        playerList.get(0).setShopping(true);
    }

    @Test
    public void getNumUndo() {
        int test = playerList.get(1).getNumUndo();
        assertEquals(0, test);
    }

    @Test
    public void setNumUndo() {
        playerList.get(0).setNumUndo(2);
        assertEquals(2,playerList.get(0).getNumUndo());
    }

    @Test
    public void getInfantryCardsList() {
        assertEquals(0, playerList.get(0).getInfantryCardsList().size());
    }

    @Test
    public void addInfantryCardsList() {
    }

    @Test
    public void getCalvalryCardsList() {
    }

    @Test
    public void addCalvalryCardsList() {
    }

    @Test
    public void getArtillaryCardsList() {
    }

    @Test
    public void addArtillaryCardsList() {
    }

    @Test
    public void setCredits() {
        playerList.get(0).setCredits(5);
        assertEquals(5, playerList.get(0).getCredits());
    }

    @Test
    public void SetTurn() {
        playerList.get(0).SetTurn(true);
        assertEquals(true, playerList.get(0).getTurn());
    }

    @Test
    public void setTroopCount() {
        playerList.get(0).setTroopCount(10);
        assertEquals(10,playerList.get(0).getTroopCount());
    }

    @Test
    public void decTroopCount() {
        int test = playerList.get(1).getTroopCount();
        playerList.get(1).decTroopCount(2);
        assertEquals(test-2, playerList.get(1).getTroopCount());
    }



    @Test
    public void getTeam() {
        assertEquals("Collin",playerList.get(1).getTeam());
    }

    @Test
    public void getTerritoryCount() {
        int count = playerList.get(1).getTerritoryCount();
       System.out.println(count);
    }


    @Test
    public void incTerritoryCount() {
        playerList.get(1).incTerritoryCount();
        int count = playerList.get(1).getTerritoryCount();
        if(count>= 22)
        assertTrue(true);
    }

    @Test
    public void beginCreditTransaction() {
        systemInMock.provideLines("5");
        playerList.get(1).beginCreditTransaction();
    }

    @Test
    public void reinforceRegions() {
        for (Territory t : territoryList) {
            if (t.getTeam().equals("Sanaz")) {
                playerList.get(0).reinforceRegions(8);
                assertEquals(false, playerList.get(0).checker);
                systemInMock.provideLines(Integer.toString(t.getID()));
                playerList.get(0).reinforceRegions(0);
                systemInMock.provideLines(Integer.toString(t.getID()),"2");
                playerList.get(0).reinforceRegions(1);
                break;
            }
        }
    }

    @Test
    public void percentageInControl() {
        playerList.get(0).percentageInControl();
    }
    @Test
    public void useHand() {
        systemInMock.provideLines("2");
        playerList.get(0).useHand();

        playerList.get(0).setNumUndo(5);
        systemInMock.provideLines("1","1");
        playerList.get(0).useHand();
    }


}