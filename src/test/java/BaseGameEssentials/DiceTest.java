package BaseGameEssentials;


import BaseGameEssentials.Dice;
import BaseGameEssentials.Territory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static BaseGameEssentials.Game.Cards;
import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;

public class DiceTest {
 Dice dd = new Dice();
 Integer[] A=new Integer[2];
 Integer[] D=new Integer[2];

 ArrayList<Integer> connections = new ArrayList<Integer>();
 Territory temp1 = new Territory("LaLaLand1",777, "Moon", connections);
 Territory temp2 = new Territory("LaLaLand2",776, "Moon", connections);
 public static DeckofCards Deck = new DeckofCards(Game.territoryList, Cards);

    @BeforeClass
    public static void setup() {

        Game.readFileTerritories();

    }

    @AfterClass
    public static void cleanUp(){
        playerList.clear();
        territoryList.clear();
    }

    @Test
    public void roll() {
        Integer[] diceArray;
        diceArray = dd.roll(2);
        assertEquals(2,diceArray.length);
    }

    @Test
    public void compareFaceValue() {
        A[0]=3;A[1]=1;D[0]=2;D[1]=1;
        dd.compareFaceValue(A,D,temp1,temp2);
        assertEquals(true,dd.comparison);

    }

    @Test
    public void compareFaceValue2() {
        Deck.generate();
        A[0]=6;A[1]=6;D[0]=1;D[1]=1;
        temp1.setTeam("Sanaz"); temp1.setTroopCount(10);
        temp2.setTeam("Collin"); temp2.setTroopCount(2);
        Player User1 = new Player(20, "Sanaz", false, false, 1);
        Player User2 = new Player(20, "Collin", false, false, 1);
        playerList.add(User1);
        playerList.add(User2);
        dd.compareFaceValue(A,D,temp1,temp2);
        assertEquals(true,dd.comparison);

    }
}