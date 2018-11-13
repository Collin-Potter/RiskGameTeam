/*TurnTimer.java:
 * Purpose: The goal of this class is to create a time limit on a players response at the beginning of their turn. Either
 * the users input will be returned allowing them to proceed with their turn or the program will return an input for them
 * that causes the players turn to be skipped
 *
 * Process: As soon as the get_input method is called the timer begins. It has a limit of 30 seconds.
 * In-between 0-30sec a user has the opportunity to set their own input. The input a user sets on their
 * own is the input that will be returned allowing them to proceed into their turn. If a user does
 * not set their own input in-between the allotted time a value is set for them by the program. The value set for them
 * is 4, and is the value that will be returned into the Attack class. This value returned by the program causes their
 * turn to be skipped.
 *
 * */
package BaseGameEssentials;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TurnTimer
{
    private int choice = 0;

    public int get_input() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // looks for user input
        long startTime = System.currentTimeMillis(); // logs the current time.

        try
        {
            /*Sets the amount of time a user has for their turn. Uses the time this method
             * began and subtracts it by the current time of the system.*/
            while ((System.currentTimeMillis() - startTime) < 30 * 1000
                    && !in.ready()) {

            }
            if (in.ready()) {
                /*This loop is entered when a user enters their own input*/
                String str = "";
                str = in.readLine();
                choice = Integer.parseInt(str);
            } else {
                /*This loop is entered when a user takes longer than 30sec & a value
                 * is set for them*/
                System.out.println("\nYou have been skipped! Slow poke!");
                choice = 4;
            }
        }
        catch(Exception e)
        {
            /*Catching any exceptions*/
            System.out.println(e);
        }
        /*Returns either the users choice or the programs choice*/
        return choice;
    }
}

//package BaseGameEssentials;
//
//        import java.util.Scanner;
//        import java.lang.InterruptedException;
//        import java.lang.Runnable;
//        import java.util.Timer;
//        import java.util.TimerTask;


//public class TimerClass {
//    private int time = 0;
//   public int flag = 5;
//    int secondsPassed = 0;
//
//
//    Timer myTimer = new Timer();
//    TimerTask task = new TimerTask() {
//
//        public int flag =0;
//
//        public void run() {
//            secondsPassed++;
//
//            if (secondsPassed == 30) {
//                this.flag = this.flag = 5;
//
//            }
//
//        }
//
//
//    };


//    public void start() {
//        myTimer.scheduleAtFixedRate(task, 0, 1000);
//    }

//    TimerClass() {
////        sleep(1000);
////        this.time +=
//        this.flag = this.flag * 2;
//        this.time = this.time * 1000;

//
//    }
//    public static int ReturnSumthing(String[] args) {
//
//        return 4;
//    }
//    }

//class Timer implements Runnable {
//
//    public int run() {
//        try {
//            Thread.sleep(30000);
//        } catch (Exception e) {}
//        return 3;
//    }
//}
//public class TimerClass {
//
//    //Start Timer here
//    String userInput = getInput();
//
//
//    public static String getInput() {
//        Scanner iID = new Scanner(System.in);
//        String Y = iID.nextLine();
//        return Y;
//
//    }
//
//}
