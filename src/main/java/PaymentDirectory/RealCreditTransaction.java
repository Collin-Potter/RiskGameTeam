package PaymentDirectory;

import BaseGameEssentials.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RealCreditTransaction implements CreditInterface {

    private int userInput;
    private Player currentPlayer;

    public RealCreditTransaction(int userInput, Player currentPlayer){
        this.currentPlayer = currentPlayer;
        this.userInput = userInput;
        display(currentPlayer);
        beginTransaction(userInput);
    }

    @Override
    public int display(Player currentPlayer) {
        /**System.out.println("Displaying Selection Menu for " + currentPlayer.getTeam() + "...\n" +
                "You currently have " + currentPlayer.getCredits() + " credits\n" +
                "You currently have " + currentPlayer.getInfantryCardsList().size() + " infantry cards\n" +
                "You currently have " + currentPlayer.getCalvalryCardsList().size() + " cavalry cards\n" +
                "You currently have " + currentPlayer.getArtillaryCardsList().size() + " artillery cards\n" +
                "You currently have " + currentPlayer.getNumUndo() + "undo actions");**/
        return 0;
    }

    private void beginTransaction(int userInput) {
       /** switch (userInput) {
            case (1):
                System.out.println("\"Purchase Credit\" selected...");
                purchaseCredit();
                break;
            case (2):
                System.out.println("\"Purchase Cards\" selected...");
                purchaseCards();
                break;
            case (3):
                System.out.println("\"Purchase Undo Actions\" selected...");
                purchaseUndoActions();
                break;
            case (4):
                System.out.println("\"Transfer Credits\" selected...");
                transferCredits();
                break;
            case (5):
                System.out.println("\"Continue Without Credits\" selected...");
                currentPlayer.setShopping(false);
                break;
            default:
                System.out.println("Please enter a valid menu option");
                break;
        }**/
    }
  /**  private void run(int transactionType){
        JPane jpane = new JPane();
        jpane.displayGUI(transactionType, currentPlayer);
    }**/
    private void purchaseCredit(){
        //run(1);
    }
    private void purchaseCards(){
       // run(2);
    }
    private void purchaseUndoActions(){
       // run(3);
    }
    private void transferCredits(){
       // run(4);
    }

}
