package BaseGameEssentials;

import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import static BaseGameEssentials.HelperClass.*;
import static BaseGameEssentials.TelegramBot.AttackingTerr;
import static BaseGameEssentials.TelegramBot.DefendingTerr;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;

public class AttackTest {

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
        playerList.clear();
    }

   // @Test
   /** public void attackRegion() {
        Attack invade = new Attack();
        assertEquals("PhaseTwoComplete", invade.attackRegion());

    }**/


    @Test
    public void print() {
        Attack.Print(territoryList, playerList.get(0));
    }

    @Test
    public void transferUnits() {
        systemInMock.provideLines("1");
        for (Territory t : territoryList) {
            if (t.getTeam().equals("Sanaz") && t.getID() != 1) {
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                AttackingTerr = t;
                DefendingTerr = territoryList.get(0);
                Attack.TransferUnits();
                assertEquals(1, territoryList.get(0).getTroopCount());
                break;
            }
        }

    }

    @Test
    public void takeTerritoryInput() {
        systemInMock.provideLines("1", "2");
        Territory Chosen = Attack.TakeTerritoryInput(territoryList);
        assertEquals(1, Chosen.getID());

    }
    @Test
    public void WithdrawOrNOt(){
        systemInMock.provideLines("2", "2");
        boolean test =Attack.WithdrawOrNOt();
        assertTrue(test);
    }

    @Test
    public void registerObserver() {

    }

    @Test
    public void removeObserver() {
    }

    @Test
    public void notifyObservers() {
    }

    @Test
    public void dataChanged() {
        Attack test = new Attack();
        test.dataChanged("sanaz","hi");
    }


}