import java.util.Scanner;
public class HelperClass {
    public int undo(){
        Scanner input = new Scanner(System.in);
        int userInput = 0;
        int loop = 0;
        while(loop == 0) {
            System.out.println("Would you like to undo the previous action?\n" +
                    "1: Yes\n" +
                    "2: No");
            try {
                userInput = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Incorrect Input");
                undo();
            }
            switch (userInput) {
                case (1):
                    System.out.println("\'Yes\' selected... Undoing previous action");
                    loop = 1;
                    break;
                case (2):
                    System.out.println("\'No\' selected... Continuing with program");
                    loop = 1;
                    break;
                default:
                    System.out.println("That was not a possible choice.. Please try again");
                    break;
            }
        }
        return(userInput); //Error int return
    }
}
