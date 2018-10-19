import BaseGameEssentials.Card;
import BaseGameEssentials.Territory;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardTest {
    ArrayList<Integer> connections = new ArrayList<Integer>();

    Territory temp = new Territory("LaLaLand",777, "Moon", connections);

    Card testCard = new Card("Infantry",1, temp, false);
    @Test
    public void getType() {
        assertEquals("Infantry",testCard.getType());
    }

    @Test
    public void getWorth() {
        assertEquals(1,testCard.getWorth());
    }

    @Test
    public void getCountry() {
        assertEquals("LaLaLand",testCard.getCountry());
    }

    @Test
    public void getInUseStatus() {
        assertEquals(false,testCard.getInUseStatus());
    }

    @Test
    public void isInUse() {
        assertEquals(true,testCard.isInUse(true));
    }
}