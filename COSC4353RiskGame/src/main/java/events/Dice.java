package events;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Allows the creation of Risk Dice objects used to handle player rolling.
 * @author Grant Williams
 * 
 * @date 9/4/18
 **/

public class Dice {

	private int roll;
	private int[] diceArray;
    private Random die;

    public Dice() {
	
    }

    //Returns int array of random dice rolls dependent on number given
    public int[] roll(int numberOfDice) {
	
		diceArray = new int[numberOfDice];
		
		for(int i = 0; i < diceArray.length; i++) {
			die = new Random();
			roll = die.nextInt(6) + 1;
			diceArray[i] = roll;
		}
		
		Arrays.sort(diceArray);
		Collections.reverse(Arrays.asList(diceArray));
		
		return diceArray;
    }
}
