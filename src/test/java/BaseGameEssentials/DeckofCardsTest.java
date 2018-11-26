package BaseGameEssentials;

import BaseGameEssentials.Card;
import BaseGameEssentials.DeckofCards;
import BaseGameEssentials.Territory;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;

import static BaseGameEssentials.Game.playerList;
import static BaseGameEssentials.Game.territoryList;
import static org.junit.Assert.*;

public class DeckofCardsTest {
    ArrayList<Integer> connections = new ArrayList<Integer>();
    Territory temp = new Territory("LaLaLand",777, "Moon", connections);
    ArrayList<Territory> territoryList = new ArrayList<Territory>();
    ArrayList<Card> Cards = new ArrayList();
    DeckofCards Deck = new DeckofCards(territoryList, Cards);

    @AfterClass
    public static void cleanUp(){
        playerList.clear();
        Game.territoryList.clear();
    }

    @Test
    public void generate() {
        for(int i=0;i<42;i++)
            territoryList.add(temp);
        Deck.generate();
       assertEquals(42,Cards.size());
    }

    @Test
    public void drawACard() {
        for(int i=0;i<42;i++)
            territoryList.add(temp);
        Deck.generate();
        Card ACard = Deck.drawACard();
        assertEquals(true,ACard.getInUseStatus());
    }

    @Test
    public void showCard(){
        for(int i=0;i<42;i++)
            territoryList.add(temp);
        Deck.generate();
        Card ACard = Deck.drawACard();
        Deck.ShowCard(ACard);
    }

}