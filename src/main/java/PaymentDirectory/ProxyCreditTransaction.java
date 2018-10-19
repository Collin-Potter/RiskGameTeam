package PaymentDirectory;

import java.util.Scanner;

public class ProxyCreditTransaction implements CreditInterface {

    private RealCreditTransaction realCreditTransaction;

    @Override
    public void display() {
        Scanner keyboard = new Scanner(System.in);
        boolean inputIncorrect = true;
        while (inputIncorrect) {
            try {
                System.out.println("Please choose one of the following:\n1.Purchase Credit\n2.Purchase Cards\n3.Purchase Undo Actions\n4.Transfer Credits\n5.Continue Without Credits");
                String input = keyboard.nextLine();
                int userInput = Integer.parseInt(input);
                inputIncorrect = false;
                if(realCreditTransaction == null){
                    realCreditTransaction = new RealCreditTransaction(userInput);
                }
                realCreditTransaction.display();
            } catch (Exception e) {
                System.out.println("Please select one of the previous options...");
            }
        }

    }
}
