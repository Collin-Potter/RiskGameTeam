import jdk.internal.util.xml.impl.Input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player extends Game{
    private int troopCount;
    private String team;
    private boolean isTurn;
    private boolean enabled;
    private int territoryCount;

    public Player(int troops, String t, boolean turn, boolean playing){
        this.troopCount = troops;
        this.team = t;
        this.isTurn = turn;
        this.enabled = playing;
    }

    public void setTroopCount(int i){
        this.troopCount = i;
    }

    public int getTroopCount(){
        return this.troopCount;
    }

    public String getTeam(){
        return this.team;
    }

    public int getTerritoryCount() { return this.territoryCount; }

    public void setTerritoryCount(int numT) { this.territoryCount = numT; }

    public void incTerritoryCount(){ this.territoryCount++; }

    public void reinforceRegions(){
        Scanner input = new Scanner(System.in);
        while(this.troopCount != 0) {
            int tempID = 0;
            boolean inputIncorrect = true;
            while(inputIncorrect == true) {
                System.out.println(getTeam() + " enter ID of territory for desired reinforcement... ");
                try {
                    tempID = Integer.parseInt(input.nextLine());
                    inputIncorrect = false;
                } catch (Exception e) {
                    System.out.println("Incorrect input...");
                }
            }

            for (Territory t : territoryList) {
                if (t.getID() == tempID && t.getTeam().equals(this.team)) {
                    tempID = 0;
                    inputIncorrect = true;
                    while(inputIncorrect == true) {
                        System.out.println("How many troops would you like to reinforce " + t.getName() + " with?");
                        try {
                            tempID = Integer.parseInt(input.nextLine());
                            inputIncorrect = false;
                            if (!(tempID <= this.troopCount)) {
                                System.out.println("Insufficient amount of troops");
                                inputIncorrect = true;
                            } else {
                                System.out.println("Reinforcing " + t.getName() + " with " + tempID + " troops...");
                                this.troopCount -= tempID;
                                t.setTroopCount(tempID);
                                System.out.println("You now have " + this.troopCount + " possible reinforcements left");
                            }
                        } catch (Exception e) {
                            System.out.println("Incorrect input...");
                        }
                    }
                }
            }
        }
        System.out.println("Current territory forces: \n");
        for(Player p:playerList) {
            System.out.println(p.getTeam() + " territories...");
            for (Territory t : territoryList) {
                if (t.getTeam().equals(p.getTeam())) {
                    System.out.printf("ID: %-5s Name: %-25s TroopCount: %-20d\n", t.getID(), t.getName(), t.getTroopCount());
                }
            }
        }
    }

    public void attackRegion(){

    }

    public void defendRegion(){

    }

    public void isPlaying(boolean play){
        this.enabled = play;
    }
}
