import BaseGameEssentials.Attack;
import BaseGameEssentials.Dice;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttackTest {

   /* @Test
    public void setDice() {
         Attack invade = new Attack();
         Dice dice = new Dice();
         invade.setDice(dice);
         assertEquals(dice,dice);
    }*/

    @Test
    public void attackRegion() {
        Attack invade = new Attack();
        assertEquals("PhaseTwoComplete",   invade.attackRegion());
    }
}