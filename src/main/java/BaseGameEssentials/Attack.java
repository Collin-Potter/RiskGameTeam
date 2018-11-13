package BaseGameEssentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Attack extends Game implements SubjectInterface {
    private boolean possible = false;
    private Integer[] DefenderDice;
    private Integer[] AttackerDice;
    private Dice dice = new Dice();
    public String playerName;
    public String countryName;
    ArrayList<Observer> observerList;
    public void setDice(Dice newDice) {
        this.dice = dice;
    }

    public Attack() {
        observerList = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(observerList.indexOf(o));
    }

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

    public void dataChanged(String p, String c)
    {
        //get attack latest data
        playerName = p;
        countryName = c;

        notifyObservers();
    }

    public String attackRegion() {
        for (Player p : playerList) {

            TurnTimer userinput = new TurnTimer(); // sets variable to start timer in TurnTimer class
            /*Player is notified its their turn*/
            System.out.println("\n"+ p.getTeam() + " it's your turn!! You ready? (Enter 1 when ready, you have 30sec)");
            int readyornot = userinput.get_input();// receives return value from TurnTimer

            /*First checks if a user is to be skipped or not based on the value returned from TurnTimer*/
            if (readyornot != 4) {

                boolean InvasionStatus = true;
                while (InvasionStatus == true) {
                    ArrayList eligibleOptions = new ArrayList();
                    ArrayList eligibleInvasions = new ArrayList();
                    ArrayList<Territory> InWarCounties = new ArrayList<Territory>(); // Index 0 always invader, Index 1 defender
                    for (Territory t : territoryList) {
                        if (t.getTeam().equals(p.getTeam()) && t.getTroopCount() > 1) {
                            String AlreadyPrinted = new String();

                            for (int i = 0; i < t.getConnections().size(); i++)
                                if (!p.getCountryID(territoryList).contains(t.getConnections().get(i)) && !AlreadyPrinted.equals(t.getName())) {
                                    System.out.println("ID: " + t.getID() +
                                            "  Country Name: " + t.getName() + " Your troops: " + t.getTroopCount());
                                    AlreadyPrinted = t.getName();
                                    eligibleOptions.add(t.getID());
                                    possible = true;
                                }
                        }
                    }

                    if (possible) {
                        System.out.println(p.getTeam() + " You may attack from one of the above territories that belongs to you");
                        Scanner input = new Scanner(System.in);
                        int tempID = 0;
                        boolean inputIncorrect = true;
                        while (inputIncorrect == true) {
                            System.out.println(" enter ID of the territory which you will ATTACK FROM... ");
                            // Timer userinput= new Timer();
                            String X = input.nextLine();
                            try {

                                tempID = Integer.parseInt(X);
                                if (eligibleOptions.contains(tempID)) {
                                    for (Territory t : territoryList) {
                                        if (t.getID() == tempID)
                                            InWarCounties.add(t);
                                    }
                                    inputIncorrect = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Incorrect input...");
                            }
                        }
                        new HelperClass().undo();

                        System.out.println(" You may Invade one of the following... ");
                        for (Territory t : territoryList) {
                            if (!t.getTeam().equals(p.getTeam()) && t.getConnections().contains(tempID)) {
                                System.out.println("ID: " + t.getID() +
                                        "  Country Name: " + t.getName() + "  troops present: " + t.getTroopCount());
                                eligibleInvasions.add(t.getID());
                            }
                        }


                        Scanner iID = new Scanner(System.in);
                        int tempiID = 0;
                        boolean iIDIncorrect = true;
                        while (iIDIncorrect == true) {
                            System.out.println(" enter ID of the territory which you will Invade... ");
                            String Y = iID.nextLine();
                            try {
                                tempiID = Integer.parseInt(Y);
                                if (eligibleInvasions.contains(tempiID)) {
                                    for (Territory t : territoryList) {
                                        if (t.getID() == tempiID)
                                            InWarCounties.add(t);
                                    }
                                    iIDIncorrect = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Incorrect input...");
                            }
                        }
                        new HelperClass().undo();
                        Territory Attacker = InWarCounties.get(0);
                        Territory Defender = InWarCounties.get(1);
                        dataChanged(Defender.getTeam(), Defender.getName());
                        boolean win = false;
                        boolean NotEnoughTroops = false;
                        while (win == false) {
                            boolean TypeInCheck1 = true;
                            Scanner rolling = new Scanner(System.in);
                            while (TypeInCheck1 == true) {
                                if (Attacker.getTroopCount() >= 3) {
                                    System.out.println(" You can roll the 3 dice, Type in 1 to roll");
                                    String Z = rolling.nextLine();
                                    if (Z.equals("1")) {
                                        AttackerDice = new Integer[3];
                                        AttackerDice = dice.roll(3);
                                        TypeInCheck1 = false;
                                    }
                                } else if (Attacker.getTroopCount() == 2) {
                                    System.out.println(" You can roll the 2 dice, Type in 1");
                                    String Z = rolling.nextLine();
                                    if (Z.equals("1")) {
                                        AttackerDice = new Integer[2];
                                        AttackerDice = dice.roll(2);
                                        TypeInCheck1 = false;
                                    }
                                } else if (Attacker.getTroopCount() == 1) {
                                    NotEnoughTroops = true;
                                    System.out.println(" You have only 1 unit left, you must withdraw from attacking or start a new attack");
                                    System.out.println(Attacker.getTeam() + " If you would like to withdraw from attacking " +
                                            "Type in 1 otherwise Type in 2: ");
                                    String D = rolling.nextLine();
                                    if (D.equals("1")) {
                                        InvasionStatus = false;
                                        TypeInCheck1 = false;
                                        win = true;
                                    }
                                    if (D.equals("2")) {
                                        InvasionStatus = true;
                                        TypeInCheck1 = false;
                                        win = true;
                                    }
                                }
                            }
                        }
                        if (NotEnoughTroops == false) {
                            Scanner defenderRoll = new Scanner(System.in);
                            boolean TypeInCheck2 = true;
                            while (TypeInCheck2 == true) {
                                if (Defender.getTroopCount() >= 2) {
                                    System.out.println(Defender.getTeam() + " You can roll the 2 dice to defend your land," +
                                            " Type in 1 to roll");
                                    String K = defenderRoll.nextLine();
                                    if (K.equals("1")) {
                                        DefenderDice = new Integer[2];
                                        DefenderDice = dice.roll(2);
                                        TypeInCheck2 = false;
                                    }
                                } else if (Defender.getTroopCount() == 1) {
                                    System.out.println(Defender.getTeam() + " You can roll the 1 dice to defend your land," +
                                            " Type in 1 to roll");
                                    String K = defenderRoll.nextLine();
                                    if (K.equals("1")) {
                                        DefenderDice = new Integer[1];
                                        DefenderDice = dice.roll(1);
                                        TypeInCheck2 = false;
                                    }
                                }
                            }
                            dice.compareFaceValue(AttackerDice, DefenderDice, InWarCounties.get(0), InWarCounties.get(1));
                            //If Invader win
                            if (Defender.getTroopCount() == 0) {
                                // InvasionStatus = false;
                                win = true;
                                System.out.println(Defender.getTeam() + " You have to place armies on your new territory... ");
                                System.out.println(Defender.getTeam() + " You may transfer units from " + Attacker.getName() + " current troops: " + Attacker.getTroopCount());
                                Scanner in = new Scanner(System.in);
                                int temp = 0;
                                boolean check1 = true;
                                while (check1 == true) {
                                    System.out.println(" How many units are you transferring... ");
                                    String X = in.nextLine();
                                    try {
                                        temp = Integer.parseInt(X);
                                        // for (BaseGameEssentials.Territory t : territoryList) {
                                        if (Attacker.getTroopCount() > temp) {
                                            Attacker.setTroopCount(Attacker.getTroopCount() - temp);
                                            Defender.setTroopCount(temp);
                                            System.out.println(" Your Troops in " + Defender.getName() + " is " + Defender.getTroopCount());
                                            check1 = false;
                                        }
                                        // }
                                    } catch (Exception e) {
                                        System.out.println("Incorrect input...");
                                    }
                                }
                            }
                            new HelperClass().undo();


                            if (win) {
                                boolean TypeInCheck3 = true;
                                while (TypeInCheck3 == true) {
                                    System.out.println(Attacker.getTeam() + " If you would like to withdraw from attacking " +
                                            "Type in 1 otherwise Type in 2: ");
                                    Scanner AttackControl = new Scanner(System.in);
                                    String D = AttackControl.nextLine();
                                    if (D.equals("1")) {
                                        InvasionStatus = false;
                                        TypeInCheck3 = false;
                                    }
                                    if (D.equals("2")) {
                                        InvasionStatus = true;
                                        TypeInCheck3 = false;
                                    }
                                }
                            }
                        }

                    } else {
                        System.out.println("You may not attack at this time");
                        InvasionStatus = false;
                    }
                }
            }
        }
        String done = "PhaseTwoComplete";
        return (done);
    }

}
