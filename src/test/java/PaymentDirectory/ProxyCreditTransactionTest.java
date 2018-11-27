package PaymentDirectory;

import BaseGameEssentials.Game;
import BaseGameEssentials.Player;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;

public class ProxyCreditTransactionTest {

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
    @Test
    public void display() {
     /**  ProxyCreditTransaction object = new ProxyCreditTransaction();
        systemInMock.provideLines("1");
        object.display(playerList.get(0));
**/
    }
}