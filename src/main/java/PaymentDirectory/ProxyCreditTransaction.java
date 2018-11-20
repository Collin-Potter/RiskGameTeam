package PaymentDirectory;

import BaseGameEssentials.*;

import java.util.Scanner;

public class ProxyCreditTransaction implements CreditInterface {

    private RealCreditTransaction realCreditTransaction;

    @Override
    public void display(Player currentPlayer) {
        Scanner keyboard = new Scanner(System.in);
        boolean inputIncorrect = true;
        while (inputIncorrect) {
            //Display initial console prompt for transaction requests
            try {
                System.out.println("Please choose one of the following:\n1.Purchase Credit\n2.Purchase Cards\n3.Purchase Undo Actions\n4.Transfer Credits\n5.Continue Without Credits");
                String input = keyboard.nextLine();
                int userInput = Integer.parseInt(input);
                inputIncorrect = false;
                if(realCreditTransaction == null){
                    realCreditTransaction = new RealCreditTransaction(userInput, currentPlayer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Please select one of the previous options...");
            }
        }

    }
}
