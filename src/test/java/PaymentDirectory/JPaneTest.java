package PaymentDirectory;

import BaseGameEssentials.Game;
import BaseGameEssentials.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import static BaseGameEssentials.Game.playerList;
import static org.junit.Assert.*;

public class JPaneTest {
JPane object = new JPane();
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
    public void displayGUI() {
       /** playerList.get(0).setNumUndo(5);
        object.displayGUI(1, playerList.get(0));
        object.displayGUI(2, playerList.get(0));
        object.displayGUI(3, playerList.get(0));
        object.displayGUI(4, playerList.get(0));**/
    }


    @Test
    public void determineMenu() {

    }

    @Test
    public void displayCreditAmountPrompt() {
    }

    @Test
    public void displayCardAmount() {
    }

    @Test
    public void displayUndoActionAmount() {
    }

    @Test
    public void displayTransferAmountPrompt() {
    }

    @Test
    public void displayTransferRecipientPrompt() {
    }
}