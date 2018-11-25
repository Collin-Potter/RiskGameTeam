package BaseGameEssentials;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;

public class GameTest2 {
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @BeforeClass
    public static void setup(){
        playerList.clear();
        territoryList.clear();

    }

    @Test
    public void gameSetUp(){
        playerList.clear();
        systemInMock.provideLines("2","2","Sanaz", "2", "Collin","2","5","5");
        Game.gameSetUp();
        playerList.clear();
        systemInMock.provideLines("3","2","Sa", "2", "Co","2","Ga","2","5","5","5");
        Game.gameSetUp();
        playerList.clear();
        systemInMock.provideLines("4","2","Sa", "2", "Co","2","Ga","2","Ju","2", "5","5","5","5");
        Game.gameSetUp();
        playerList.clear();
        systemInMock.provideLines("5","2","Sa", "2", "Co","2","Ga","2","Ju","2","Na","2","5","5","5","5","5");
        Game.gameSetUp();
        playerList.clear();
        systemInMock.provideLines("6","2","Sa", "2", "Co","2","Ga","2","Ju","2","Na","2","So","2","5","5","5","5","5","5");
        Game.gameSetUp();
    }

    @Test
    public void newTroopDistribution(){
        playerList.clear();
        territoryList.clear();
        Player User1 = new Player(0, "Sanaz", false, false, 1);
        Player User2 = new Player(0, "Collin", false, false, 1);
        User1.setTroopCount(35);
        User2.setTroopCount(35);
        playerList.add(User1);
        playerList.add(User2);
        Game.readFileTerritories();
        Game.TelegramTerritoryDistribution();
        Game.newTroopDistribution();
    }
}