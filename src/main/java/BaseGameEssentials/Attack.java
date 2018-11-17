package BaseGameEssentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import static BaseGameEssentials.TelegramBot.*;

// Class Attack implement the SubjectInterface which will allow the registered observers be informed of its changes
public class Attack extends Game implements SubjectInterface {

    public String playerName;
    public String countryName;
    ArrayList<Observer> observerList;
    public Attack() {
        observerList = new ArrayList<Observer>();
    }
    /**
     * This method implements attack phase. Loops through the list of players to give them turn to attack.
     * Calls FindWhereToAttackFrom() method to find what are the countries from which the player may attack.
     * Calls FindWhereICanAttack() method to find where can be attacked from the chosen country to attack from.
     * Calls on TakeTerritoryInput() method to take the user's choice of territory and verify it.
     * Calls on fulFillAttack() method to keep rolling dices until the attack is finalized.
     * On WIN calls the TransferUnit() method to allow the winner to move troops to the new territory
     * **/
    public String attackRegion() {
        for (Player p : playerList) {
            TurnTimer userinput = new TurnTimer(); // sets variable to start timer in TurnTimer class
            /*Player is notified its their turn*/
            System.out.println("\n" + p.getTeam() + " it's your turn!! You ready? (Enter 1 when ready, you have 30sec)");
            int readyornot = userinput.get_input();// receives return value from TurnTimer

            /*First checks if a user is to be skipped or not based on the value returned from TurnTimer*/
            if (readyornot != 4) {
                boolean InvasionStatus = true; // used to control if the player decides to start a new attack
                while (InvasionStatus == true) {
                    // from is the list of territories that attacking from is possible
                    ArrayList<Territory> from = FindWhereToAttackFrom(p);
                    if (from.size() != 0) {
                        System.out.println(p.getTeam() + " You may attack from one of the given territories that belongs to you");
                        for (int i = 0; i < from.size(); i++) {
                            System.out.println("ID: " + from.get(i).getID() + " NAME: " + from.get(i).getName()+
                                    " Troops: "+ from.get(i).getTroopCount());
                        }
                    } else {
                        System.out.println("You may not attack at this this time.");
                        InvasionStatus = false;
                        break;
                    }
                    AttackingTerr = TakeTerritoryInput(from);
                    // to is the list of the territories that can be attacked from the chosen territory
                    ArrayList<Territory> to = FindWhereICanAttack(AttackingTerr.getName());
                    System.out.println(" You may Invade one of the following... ");
                    for (int i = 0; i < to.size(); i++) {
                        System.out.println("ID: " + to.get(i).getID() + " NAME: " + to.get(i).getName()
                                + " Troops: " + to.get(i).getTroopCount());
                    }
                    DefendingTerr = TakeTerritoryInput(to);
                    //Warning that the attack is taking place
                    dataChanged(DefendingTerr.getTeam(), DefendingTerr.getName());
                    //Finalizing the attack
                    fulfillAttack(p, DefendingTerr, AttackingTerr);
                    if (NotEnoughTroops == true) {
                        System.out.println(AttackingTerr.getTeam() + " If you would like to withdraw from attacking " +
                                "Type in 1 otherwise Type in 2: ");
                        boolean TypeInCheck1 = true;
                        Scanner rolling = new Scanner(System.in);
                        while (TypeInCheck1 == true) {
                            String D = rolling.nextLine();
                            if (D.equals("1")) { InvasionStatus = false; TypeInCheck1 = false; }
                            if (D.equals("2")) { InvasionStatus = true; TypeInCheck1 = false; }
                        }
                        new HelperClass().undo();
                        NotEnoughTroops=false;
                    }

                    //If Invader wins
                    if (WIN) {
                        TransferUnits();
                        boolean TypeInCheck3 = true;
                        while (TypeInCheck3 == true) {
                            System.out.println(AttackingTerr.getTeam() + " If you would like to withdraw from attacking " +
                                    "Type in 1 otherwise Type in 2: ");
                            Scanner AttackControl = new Scanner(System.in);
                            String D = AttackControl.nextLine();
                            if (D.equals("1")) { InvasionStatus = false; TypeInCheck3 = false; }
                            if (D.equals("2")) { InvasionStatus = true; TypeInCheck3 = false; }
                        }
                        new HelperClass().undo();
                        WIN=false;
                    }
                }
            }
        }
        // A string is returned that verifies attack phase has been completed
        String done = "PhaseTwoComplete";
        return (done);
    }
    /**
     * This method infroms the winner of the number troops they have available and enable them to
     * transfer troops to the newly won territory.
     * **/
    public static void TransferUnits(){
        System.out.println(AttackingTerr.getTeam() + " You may transfer units from " +
                AttackingTerr.getName() + " current troops: " + AttackingTerr.getTroopCount());
        Scanner in = new Scanner(System.in);
        int temp = 0;
        boolean check1 = true;
        while (check1 == true) {
            System.out.println(" How many units are you transferring... ");
            String X = in.nextLine();
            try {
                temp = Integer.parseInt(X);
                if (AttackingTerr.getTroopCount() > temp) {
                    AttackingTerr.setTroopCount(AttackingTerr.getTroopCount() - temp);
                    DefendingTerr.setTroopCount(temp);
                    System.out.println(" Your Troops in " + DefendingTerr.getName() + " is " +
                            DefendingTerr.getTroopCount());
                    check1 = false;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input...");
            }
        }
    }
    /**
     * This method takes the player's choice of territory to attack from or to
     * finds it in the list of territories that are possible choices
     * Verifies the user's input
     * @param LIST : list of possible territories to pick from
     * **/
     public static Territory TakeTerritoryInput(ArrayList<Territory> LIST){
         Territory T = null;
         Scanner input = new Scanner(System.in);
         int tempID = 0;
         boolean inputIncorrect = true;
         while (inputIncorrect == true) {
             System.out.println(" enter ID of the territory which you choose... ");
             String X = input.nextLine();
             try {
                 tempID = Integer.parseInt(X);
                 for (int i = 0; i < LIST.size(); i++) {
                     if (LIST.get(i).getID() == tempID) {
                          T = LIST.get(i);
                         inputIncorrect = false;
                     }
                 }
             } catch (Exception e) {
                 System.out.println("Incorrect input...");
             }
         }
         new HelperClass().undo();
         return T;
     }
    /**
     * Method to allow registering Observers(any class that implements observer Interface)
     * **/
    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }
    /**
     * Method to allow removing Observers(any class that implements observer Interface) from the observer list
     * **/
    @Override
    public void removeObserver(Observer o) {
        observerList.remove(observerList.indexOf(o));
    }
    /**
     * This method goes through the list of observers and notify them of the changes happened in Attack
     * **/
    @Override
    public void notifyObservers()
    {
        for (Iterator<Observer> it =
             observerList.iterator(); it.hasNext();)
        {
            Observer o = it.next();
            o.Warn(playerName,countryName);
        }
    }
    /**
     * This method gets the latest changes information which is the player name who is being attacked and the country in
     * which this player is being attacked and calls the notifyObservers() method to inform observers of the changes.
     * @param c : country name
     * @param p : player name
     * **/
    public void dataChanged(String p, String c)
    {
        //get attack latest data
        playerName = p;
        countryName = c;

        notifyObservers();
    }
}