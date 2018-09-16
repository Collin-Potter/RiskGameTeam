import jdk.internal.util.xml.impl.Input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player extends Game{
    private static final int MAP_REGION_AMOUNT = 42;
    private int troopCount;
    private String team;
    private boolean isTurn;
    private boolean enabled;
    private int territoryCount;
    private int ID;
    private final int totalNA = 9;
    private final int totalSA = 4;
    private final int totalAsia = 12;
    private final int totalAfrica = 6;
    private final int totalEU = 7;
    private final int totalAustralia = 4;

    public Player(int troops, String t, boolean turn, boolean playing, int ID){
        this.troopCount = troops;
        this.team = t;
        this.isTurn = turn;
        this.enabled = playing;
        this.ID = ID;
    }

    public int setTurn() { return (this.ID + 1); }

    public void setTroopCount(int i){
        this.troopCount = i;
    }

    public void addTroops(int i){ this.troopCount += i; }

    public void decTroopCount(int i) { this.troopCount -= i; }

    public int getTroopCount(){
        return this.troopCount;
    }

    public String getTeam(){
        return this.team;
    }

    public int getTerritoryCount() { return this.territoryCount; }

    public void setTerritoryCount(int numT) { this.territoryCount = numT; }

    public void incTerritoryCount(){ this.territoryCount++; }

    public void reinforceRegions(int type){
        if(type == 0) {
            Scanner input = new Scanner(System.in);
            if (this.troopCount != 0) {
                int tempID = 0;
                boolean inputIncorrect = true;
                while (inputIncorrect) {
                    System.out.println(getTeam() + " enter ID of territory for desired reinforcement... ");
                    try {
                        tempID = Integer.parseInt(input.nextLine());
                        for(Territory t: territoryList){
                            if(t.getID() == tempID && t.getTeam().equals(this.team)){
                                System.out.println(t.getTeam());
                                inputIncorrect = false;
                            }
                        }
                        if(inputIncorrect){
                            System.out.println("Incorrect possible input");
                        }                    } catch (Exception e) {
                        System.out.println("Incorrect input...");
                    }
                }

                for (Territory t : territoryList) {
                    if (t.getID() == tempID && t.getTeam().equals(this.team)) {
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
        }else if(type == 1){
            if (this.troopCount != 0) {
                int tempID = 0;
                boolean inputIncorrect = true;
                while (inputIncorrect) {
                    System.out.println(getTeam() + " enter ID of territory for desired reinforcement... ");
                    try {
                        tempID = Integer.parseInt(input.nextLine());
                        for(Territory t: territoryList){
                            if(t.getID() == tempID && t.getTeam().equals(this.team)){
                                System.out.println(t.getTeam());
                                inputIncorrect = false;
                            }
                        }
                        if(inputIncorrect){
                            System.out.println("Incorrect possible input");
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input...");
                    }
                }

                for (Territory t : territoryList) {
                    if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                        while(inputIncorrect) {
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
        }
    }

    public void percentageInControl(){
        double numNA = 0;
        double numSA = 0;
        double numAsia = 0;
        double numAfrica = 0;
        double numEurope = 0;
        double numAustralia = 0;
        for(Territory t: territoryList){
            if(t.getTeam() == this.team){
                if(t.getContinent().equals("North America")){
                    numNA++;
                }else if(t.getContinent().equals("South America")){
                    numSA++;
                }else if(t.getContinent().equals("Asia")){
                    numAsia++;
                } else if (t.getContinent().equals("Africa")) {
                    numAfrica++;
                } else if(t.getContinent().equals("Europe")){
                    numEurope++;
                }else if(t.getContinent().equals("Australia")){
                    numAustralia++;
                }
            }
        }
        double percentageWorld = Math.round((((double)this.territoryCount)/MAP_REGION_AMOUNT)*100);
        double percentageNA = Math.round((numNA/totalNA)*100);
        double percentageSA = Math.round((numSA/totalSA)*100);
        double percentageAsia = Math.round((numAsia/totalAsia)*100);
        double percentageAfrica = Math.round((numAfrica/totalAfrica)*100);
        double percentageEurope = Math.round((numEurope/totalEU)*100);
        double percentageAustralia = Math.round((numAustralia/totalAustralia)*100);

        System.out.println(this.team + " currently has control of...\n" +
                "North America: %" + percentageNA + "\n" +
                "South America: %" + percentageSA + "\n" +
                "Asia         : %" + percentageAsia + "\n" +
                "Africa       : %" + percentageAfrica + "\n" +
                "Europe       : %" + percentageEurope + "\n" +
                "Australia    : %" + percentageAustralia + "\n" +
                "The World    : %" + percentageWorld);
    }

    public void attackRegion(){

    }

    public void defendRegion(){

    }

    public void isPlaying(boolean play){
        this.enabled = play;
    }
}
