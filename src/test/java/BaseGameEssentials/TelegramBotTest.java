package BaseGameEssentials;

import org.junit.BeforeClass;
import org.junit.Test;

import static BaseGameEssentials.Game.playerList;
import static org.junit.Assert.*;

public class TelegramBotTest {
    TelegramBot object = new TelegramBot();
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
    public void onUpdateReceived() {
    }

    @Test
    public void getBotUsername() {

        String test = object.getBotUsername();
        assertEquals("CSGSanaz_bot", test);
    }

    @Test
    public void getBotToken() {
        String Token = object.getBotToken();

    }

    @Test
    public void translateMessage() {
        //object.translateMessage("/start");
    }

    @Test
    public void parsTranslate() {
    }

    @Test
    public void printTerritories() {
    }

    @Test
    public void showReinforcement() {
    }

    @Test
    public void printWhatTerritoriesToAttackFrom() {
    }

    @Test
    public void printWhereICanAttack() {
    }

    @Test
    public void parsQuery() {
    }

    @Test
    public void send() {
    }

    @Test
    public void turnKeeper() {

        object.TurnKeeper(playerList);
    }
}