package BaseGameEssentials;

import PaymentDirectory.CreditInterface;
import PaymentDirectory.ProxyCreditTransaction;

import java.util.*;
import java.util.Scanner;

public class Player extends Game {
    private static final int MAP_REGION_AMOUNT = 42;
    private int troopCount;
    private String team;
    private boolean isTurn;
    private boolean enabled ;
    public boolean checker = true ;
    private int territoryCount;
    private int ID;
    private final int totalNA = 9;
    private final int totalSA = 4;
    private final int totalAsia = 12;
    private final int totalAfrica = 6;
    private final int totalEU = 7;
    private final int totalAustralia = 4;
    public int TestPrecentage;
    public ArrayList<Card> PlayerHand = new ArrayList();
    private Dice dice = new Dice();
    private int credits = 0;
    private int numUndo = 0;
    private boolean playerStillShopping = true;

    public void setShopping(boolean b){
        playerStillShopping = b;
    }

    public int getNumUndo() {
        return numUndo;
    }

    public void setNumUndo(int numUndo) {
        this.numUndo = numUndo;
    }

    public ArrayList<BaseGameEssentials.Card> getInfantryCardsList() {
        return infantryCardsList;
    }

    public void addInfantryCardsList(BaseGameEssentials.Card infantryCardsList) {
        this.infantryCardsList.add(infantryCardsList);
    }

    public ArrayList<BaseGameEssentials.Card> getCalvalryCardsList() {
        return calvalryCardsList;
    }

    public void addCalvalryCardsList(BaseGameEssentials.Card calvalryCardsList) {
        this.calvalryCardsList.add(calvalryCardsList);
    }

    public ArrayList<BaseGameEssentials.Card> getArtillaryCardsList() {
        return artillaryCardsList;
    }

    public void addArtillaryCardsList(BaseGameEssentials.Card artillaryCardsList) {
        this.artillaryCardsList.add(artillaryCardsList);
    }

    private ArrayList<Card> infantryCardsList = new ArrayList<Card>();
    private ArrayList<Card> calvalryCardsList = new ArrayList<Card>();
    private ArrayList<Card> artillaryCardsList = new ArrayList<Card>();

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDice(Dice newDice) {
        this.dice = dice;
    }

    private ArrayList<Integer> countryID = new ArrayList<Integer>();

    public Player(int troops, String t, boolean turn, boolean playing, int ID) {
        this.troopCount = troops;
        this.team = t;
        this.isTurn = turn;
        this.enabled = playing;
        this.ID = ID;
    }

    public ArrayList<Integer> getCountryID(ArrayList<Territory> list) {
        for (Territory t : list) {
            if (t.getTeam().equals(this.team)) {
                countryID.add(t.getID());
            }
        }
        return this.countryID;
    }

    public int setTurn() {
        return (this.ID + 1);
    }
    public boolean getTurn(){return(this.isTurn);}
    public void SetTurn(boolean status){ this.isTurn = status;}

    public void setTroopCount(int i) {
        this.troopCount = i;
    }

    public void addTroops(int i) {
        this.troopCount += i;
    }

    public void decTroopCount(int i) {
        this.troopCount -= i;
    }

    public int getTroopCount() {
        return this.troopCount;
    }

    public String getTeam() {
        return this.team;
    }

    public int getTerritoryCount() {
        return this.territoryCount;
    }

    public void setTerritoryCount(int numT) {
        this.territoryCount = numT;
    }

    public void incTerritoryCount() {
        this.territoryCount++;
    }

    public void beginCreditTransaction(){
        while(playerStillShopping) {
            CreditInterface creditTransaction = new ProxyCreditTransaction();
            creditTransaction.display(this);
            System.out.println("\n");
        }
    }

    public void reinforceRegions(int type) {
        if (type == 0) {
            Scanner input = new Scanner(System.in);
            if (this.troopCount != 0) {
                int tempID = 0;
                boolean inputIncorrect = true;
                while (inputIncorrect) {
                    System.out.println(getTeam() + " enter ID of territory for desired reinforcement... ");
                    try {
                        tempID = Integer.parseInt(input.nextLine());
                        for (Territory t : territoryList) {
                            if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                                System.out.println(t.getTeam());
                                inputIncorrect = false;
                            }
                        }
                        if (inputIncorrect) {
                            System.out.println("Incorrect possible input");
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input...");
                    }
                }

                for (Territory t : territoryList) {
                    if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                        System.out.println("Reinforcing " + t.getName() + "...");
                        Replay.recordAction(this.team + " reinforced " + t.getName() + " with 1 troop");
                        this.troopCount -= 1;
                        t.addTroops(1);
                        System.out.println("You now have " + this.troopCount + " possible reinforcements left");
                    }
                }
                System.out.println("Current territory forces: \n");
                for (Player p : playerList) {
                    System.out.println(p.getTeam() + " territories...");
                    for (Territory t : territoryList) {
                        if (t.getTeam().equals(p.getTeam())) {
                            System.out.printf("ID: %-5s Name: %-25s TroopCount: %-20d\n", t.getID(), t.getName(), t.getTroopCount());
                        }
                    }
                }
            }
        } else if (type == 1) {
            if (this.troopCount != 0) {
                int tempID = 0;
                boolean inputIncorrect = true;
                while (inputIncorrect) {
                    System.out.println(getTeam() + " enter ID of territory for desired reinforcement... ");
                    try {
                        tempID = Integer.parseInt(input.nextLine());
                        for (Territory t : territoryList) {
                            if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                                System.out.println(t.getTeam());
                                inputIncorrect = false;
                            }
                        }
                        if (inputIncorrect) {
                            System.out.println("Incorrect possible input");
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input...");
                    }
                }

                for (Territory t : territoryList) {
                    if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                        while (inputIncorrect) {
                            System.out.println("You have " + this.troopCount + " possible reinforcements");
                            try {
                                System.out.println("How many would you like to reinforce with?");
                                tempID = Integer.parseInt(input.nextLine());
                                inputIncorrect = false;
                            } catch (Exception e) {
                                System.out.println("Incorrect input...");
                            }
                        }
                        System.out.println("Reinforcing " + t.getName() + "...");
                        this.troopCount -= 1;
                        t.addTroops(1);
                        System.out.println("You now have " + this.troopCount + " possible reinforcements left");
                    }
                }
                System.out.println("Current territory forces: \n");
                for (Player p : playerList) {
                    System.out.println(p.getTeam() + " territories...");
                    for (Territory t : territoryList) {
                        if (t.getTeam().equals(p.getTeam())) {
                            System.out.printf("ID: %-5s Name: %-25s TroopCount: %-20d\n", t.getID(), t.getName(), t.getTroopCount());
                        }
                    }
                }
            }
        }else{checker = false; }

    }

    public void percentageInControl() {
        double numNA = 0;
        double numSA = 0;
        double numAsia = 0;
        double numAfrica = 0;
        double numEurope = 0;
        double numAustralia = 0;
        for (Territory t : territoryList) {
            if (t.getTeam() == this.team) {
                if (t.getContinent().equals("North America")) {
                    numNA++;
                } else if (t.getContinent().equals("South America")) {
                    numSA++;
                } else if (t.getContinent().equals("Asia")) {
                    numAsia++;
                } else if (t.getContinent().equals("Africa")) {
                    numAfrica++;
                } else if (t.getContinent().equals("Europe")) {
                    numEurope++;
                } else if (t.getContinent().equals("Australia")) {
                    numAustralia++;
                }
            }
        }
        double percentageWorld = Math.round((((double) this.territoryCount) / MAP_REGION_AMOUNT) * 100);
        double percentageNA = Math.round((numNA / totalNA) * 100);
        double percentageSA = Math.round((numSA / totalSA) * 100);
        double percentageAsia = Math.round((numAsia / totalAsia) * 100);
        double percentageAfrica = Math.round((numAfrica / totalAfrica) * 100);
        double percentageEurope = Math.round((numEurope / totalEU) * 100);
        double percentageAustralia = Math.round((numAustralia / totalAustralia) * 100);
        TestPrecentage = 0;
        System.out.println(this.team + " currently has control of...\n" +
                "North America: %" + percentageNA + "\n" +
                "South America: %" + percentageSA + "\n" +
                "Asia         : %" + percentageAsia + "\n" +
                "Africa       : %" + percentageAfrica + "\n" +
                "Europe       : %" + percentageEurope + "\n" +
                "Australia    : %" + percentageAustralia + "\n" +
                "The World    : %" + percentageWorld);
    }

    /**
     * Method takes player input to see whether or not they would like to use an undo, or cards
     *  and returns an integer value of either:
     *  0. is a return for only debugging purposes so DO NOT check for it
     *  1. for undo actions
     *  2. for infantry cards
     *  3. for cavalry cards
     *  4. for artillery cards
     *  for other methods to take advantage of their decision
     */
    public int useHand(){
        Scanner input = new Scanner(System.in);
        boolean promptCompleted = false;
        int userInputTranslated = 0;
        //Check if user has any available extra actions
        if(this.getNumUndo() > 0 ||
                this.getCalvalryCardsList().size() > 0 ||
                this.getInfantryCardsList().size() > 0 ||
                this.getArtillaryCardsList().size() > 0) {
            do {
                System.out.println("Would you like to take advantage of one of your previously purchased undo actions or cards?\n" +
                        "1. Yes\n" +
                        "2. No");
                String userInput = input.nextLine();
                try {
                    userInputTranslated = Integer.parseInt(userInput);
                    promptCompleted = true;
                } catch (Exception e) {
                    System.out.println("Please enter one of the following options in integer format");
                }
            } while (!promptCompleted);
        //Based on user's input, return a specific item of choice
            switch(userInputTranslated){
                case 1:
                    promptCompleted = false;
                    do {
                        System.out.println("\'Yes\' selected...\n" +
                                "Which of the following would you like to take advantage of?");
                        if (this.getNumUndo() > 0) {
                            System.out.println("1. Undo Action");
                        }
                        if (this.getInfantryCardsList().size() > 0) {
                            System.out.println("2. Use your Infantry card deck");
                        }
                        if (this.getCalvalryCardsList().size() > 0) {
                            System.out.println("3. Use your Cavalry card deck");
                        }
                        if (this.getArtillaryCardsList().size() > 0) {
                            System.out.println("4. Use your Artillery card deck");
                        }
        //If somehow it gets this far and they have no items
                        if(this.getNumUndo() == 0 &&
                                this.getInfantryCardsList().size() == 0 &&
                                this.getCalvalryCardsList().size() == 0 &&
                                this.getArtillaryCardsList().size() == 0){
                            System.out.println("You have no items to take advantage of.\n" +
                                    "Moving on...");
                            break;
                        }
                        String userInput = input.nextLine();
                        try{
                            userInputTranslated = Integer.parseInt(userInput);
        //Checking for what the user wants to use
                            switch(userInputTranslated){
                                case 1:
                                    promptCompleted = true;
                                    return 1;
                                case 2:
                                    promptCompleted = true;
                                    return 2;
                                case 3:
                                    promptCompleted = true;
                                    return 3;
                                case 4:
                                    promptCompleted = true;
                                    return 4;
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter one of the following options in integer format");
                        }
                    } while(!promptCompleted);
                    break;
                case 2:
        // Remove user from prompt
                    System.out.println("\'No\' selected...\n" +
                            "Moving on...");
                    break;
            }
        } else {
            System.out.println("You have no extra actions to take advantage of.\n" +
                    "Moving on...");
        }
        return 0;
    }

}
