import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiceTest {
 Dice dd = new Dice();
 Integer[] A=new Integer[2];
 Integer[] D=new Integer[2];

 ArrayList<Integer> connections = new ArrayList<Integer>();
 Territory temp1 = new Territory("LaLaLand1",777, "Moon", connections);
 Territory temp2 = new Territory("LaLaLand2",776, "Moon", connections);
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
}