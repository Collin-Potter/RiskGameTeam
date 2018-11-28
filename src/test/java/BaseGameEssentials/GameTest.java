package BaseGameEssentials;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class GameTest {
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
        Game.Deck.generate();
        Game.TelegramTerritoryDistribution();
    }

    @AfterClass
    public static void cleanUp(){
        playerList.clear();
        territoryList.clear();
    }

    @Test
    public void telegramReinforce() {
        System.out.println("Telegram REINFORCE TEST********************************************************");
        Territory terr = null;
        for(Territory t: territoryList){
            if(t.getTeam().equals("Sanaz")){
                terr =t;
                break;
            }
        }
        int currentTroops = terr.getTroopCount();
        System.out.println(terr.getTeam()+" "+ terr.getTroopCount()+"  "+playerList.get(0).getTroopCount());
        playerList.get(0).setTroopCount(1000);
        Game.TelegramReinforce("Sanaz", terr.getID());
        assertEquals(currentTroops+1, terr.getTroopCount());

    }

    @Test
    public void findWhereToAttackFrom() {
        System.out.println("ATTACK FROM**********************************************************************");
        for(Territory t: territoryList){
            if(t.getTeam().equals("Sanaz")){
                Game.TelegramReinforce("Sanaz", t.getID());
                ArrayList<Territory> From = Game.FindWhereToAttackFrom(playerList.get(0));
                if(From.size()==0){
                    System.out.println("Negative Test");
                }
                else {
                    assertEquals(t.getID(), From.get(0).getID());
                }
                break;
            }
        }
    }
    @Test
    public void getDeepCopy() {
        System.out.println("DEEP COPY TEST**********************************************************************");
        ArrayList<Territory> test =  Game.getDeepCopy(territoryList);
        assertEquals(Game.territoryList, test);
    }

    @Test
    public void reinforceTerritories() {
        Game.reinforceTerritories(2);
    }


    @Test
    public void findWhereICanAttack() {
        System.out.println("ATTACK TO**********************************************************************");
        for(Territory t: territoryList){
            if(t.getTeam().equals("Sanaz")){
                Game.TelegramReinforce("Sanaz", t.getID());
                ArrayList<Territory> To = Game.FindWhereICanAttack(t.getName());
                assertEquals("Collin", To.get(0).getTeam());
                break;
            }
        }
    }

    @Test
    public void fulfillAttack() {
        System.out.println("FULLFIL ATTACK**********************************************************************");
        for(Territory t: territoryList){
            if(t.getTeam().equals("Sanaz")){
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                Game.TelegramReinforce("Sanaz", t.getID());
                ArrayList<Territory> To = Game.FindWhereICanAttack(t.getName());
                Game.TelegramReinforce("Collin", To.get(0).getID());
                Game.TelegramReinforce("Collin", To.get(0).getID());
                Game.fulfillAttack(playerList.get(0), To.get(0),t);
                break;
            }
        }
    }


}