package PaymentDirectory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RealCreditTransaction implements CreditInterface {

    private int userInput;

    public RealCreditTransaction(int userInput){
        this.userInput = userInput;
        beginTransaction(userInput);
    }

    @Override
    public void display() {
        System.out.println("Beginning \"" + userInput + "\"");
    }

    private void beginTransaction(int userInput) {
        switch (userInput) {
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
                break;
            default:
                System.out.println("Please enter a valid menu option");
                break;
        }
    }
    private void run(){
        JPane jpane = new JPane();
        jpane.displayGUI();
    }
    private void purchaseCredit(){
        run();
    }
    private void purchaseCards(){
        run();
    }
    private void purchaseUndoActions(){
        run();
    }
    private void transferCredits(){
        run();
    }

}
