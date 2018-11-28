package BaseGameEssentials;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static BaseGameEssentials.TelegramBot.AttackingTerr;
import static BaseGameEssentials.TelegramBot.NotEnoughTroops;
import static BaseGameEssentials.TelegramBot.WIN;


public class Game{
    //private static final int MAP_REGION_AMOUNT = 42; //NOT USED
   // private static int UNTOUCHED_TERRITORIES = 42; //NOT USED
    public static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    public static ArrayList<Player> playerList = new ArrayList<Player>();
    public static ArrayList<Card> Cards = new ArrayList();
    public static DeckofCards Deck = new DeckofCards(territoryList, Cards);
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<String> territoryInformation = new ArrayList<String>();
    public static Warning DisplayWarning = new Warning();
    public static Attack invade = new Attack();
    public static ArrayList<Territory> tempTerritoryHolder = new ArrayList<Territory>();
    public static TwitterHandler twitterHandler = new TwitterHandler();

    private int tweetTerr = 0; //keeps track of how many territories each player won in a turn for tweet functionality
    private int turnCount = 0; //keeps track of current turn number 
//public static Update update;
    public static void main(String[] args){
        readFileTerritories();
        // Initialize Api Context
        ApiContextInitializer.init();
        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();
        // Register our bot
        try {
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        gameSetUp();
        newTroopDistribution();
        reinforceTerritories(0);
        Deck.generate();
        invade.registerObserver(DisplayWarning);
        invade.attackRegion();

    }

    public  static void gameSetUp(){

        String pAmt;
        Scanner input = new Scanner(System.in);
        boolean isValid = false;
        int imStressed;
        do {
            System.out.println("Enter player amount...");
            pAmt = input.nextLine();
            if(Integer.parseInt(pAmt) == 2||
                    Integer.parseInt(pAmt) == 3||
                    Integer.parseInt(pAmt) == 4||
                    Integer.parseInt(pAmt) == 5||
                    Integer.parseInt(pAmt) == 6){
                isValid = true;
            }
            imStressed = new HelperClass().undo();
        }while(imStressed == 1 || !isValid);
        String playerName;
        for(int i = 0; i < Integer.parseInt(pAmt); i++){
            do {
                System.out.println("Enter player " + (i + 1) + "'s name...");
                playerName = input.nextLine();
                imStressed = new HelperClass().undo();
            } while(imStressed == 1);
            playerList.add(new Player(0,playerName,false,false, (i+1)));
        }
        creditTransaction();
        switch(playerList.size()){
            case(2):
                for(Player p: playerList){
                    p.setTroopCount(40);
                }
                break;
            case(3):
                for(Player p: playerList){
                    p.setTroopCount(35);
                }
                break;
            case(4):
                for(Player p: playerList){
                    p.setTroopCount(30);
                }
                break;
            case(5):
                for(Player p: playerList){
                    p.setTroopCount(25);
                }
                break;
            case(6):
                for(Player p: playerList){
                    p.setTroopCount(20);
                }
                break;
            default:
                break;
        }
        //Delete old tweets
  		System.out.println("Deleting old tweets...");
  		twitterHandler.deleteTweets();
        //Tweet about game's creation
  		System.out.println("Updating Twitter for new game...");
  		String tweet  = "A new game has been created by: \n";
  		for(int i = 0; i < playerList.size(); i++){
  			tweet = tweet + " -" + playerList.get(i).getTeam() + "\n";
  		}
  		twitterHandler.postTweet(tweet);
  		tweet = "";
  		// shared method between console set-up and telegram set-up to distribute territories
        System.out.println("Randomizing initial territory control...");
        int player = 1;
        Random random = new Random();
        ArrayList<Territory> wTerritories = getDeepCopy(territoryList);
        while(wTerritories.size() > 0){
            if(wTerritories.size() < playerList.size()){
                while(wTerritories.size() > 0){
                    int nextTerritory = random.nextInt(wTerritories.size());
                    Territory tempT = wTerritories.get(nextTerritory);
                    Player tempP = playerList.get(player-1);
                    tempT.setTeam(tempP.getTeam());
                    wTerritories.remove(nextTerritory);
                    player++;
                }
            }else{
                while(player <= playerList.size()){
                    int nextTerritory = random.nextInt(wTerritories.size());
                    Territory tempT = wTerritories.get(nextTerritory);
                    Player tempP = playerList.get(player-1);
                    tempT.setTeam(tempP.getTeam());
                    wTerritories.remove(nextTerritory);
                    player++;
                }
                player = 1;
            }
        }

    }

    public static ArrayList<Territory> getDeepCopy(ArrayList<Territory> original){
        ArrayList<Territory> copy = new ArrayList<Territory>();
        copy.addAll(original);
        return copy;
    }

    public static void reinforceTerritories(int stage){
        if(stage != 1) {
            System.out.println("Reinforce Stage in Effect...");
            for (Player p : playerList) {
                System.out.println(p.getTeam() + " you have " + p.getTerritoryCount() + " territories");

            }
        }
    }

    public static void newTroopDistribution(){
        System.out.println("Adding one reinforcement to every region...");
        for(Player p:playerList){
            for(Territory t:territoryList){
                if(t.getTeam().equals(p.getTeam())){
                    p.incTerritoryCount();
                    p.decTroopCount(1);
                    t.setTroopCount(1);
                }
            }
        }
        int amt = playerList.get(0).getTroopCount();
        for(int i = 0; i < amt*2; i++) {
            for (Player p : playerList) {
                if (p.getTroopCount() != 0) {
                    System.out.println(p.getTeam() + " you have " + p.getTroopCount() + " troops to distribute...");
                    System.out.println("Current territories: \n");
                    for (Territory t : territoryList) {
                        if (t.getTeam().equals(p.getTeam())) {
                            System.out.printf("ID: %-5s Name: %-25s TroopCount: %-5d", t.getID(), t.getName(), t.getTroopCount());
                            System.out.printf(" Continent: %-10s\n", t.getContinent());
                        }
                    }
                    p.reinforceRegions(0);
                    p.percentageInControl();
                }
            }
        }
    }

 /**   public static void reinforcementStage(){
        for (Player p : playerList) {
            if (p.getTroopCount() != 0) {
                System.out.println(p.getTeam() + " you have " + p.getTroopCount() + " troops to distribute...");
                System.out.println("Current territories: \n");
                for (Territory t : territoryList) {
                    if (t.getTeam().equals(p.getTeam())) {
                        System.out.printf("ID: %-5s Name: %-25s TroopCount: %-20d", t.getID(), t.getName(), t.getTroopCount());
                    }
                }
                p.reinforceRegions(1);
                p.percentageInControl();
            }
        }
    }**/

    public static void readFileTerritories(){
        File currentDir = new File(".");
        File parentDir = currentDir.getAbsoluteFile();
        File newFile = new File(parentDir + "/src/main/resources/world-map");
        try{
            FileReader fr = new FileReader(newFile);
            BufferedReader br = new BufferedReader(fr);
            Scanner scanner = new Scanner(br);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                territoryInformation.add(line);
            }
            for(int i = 0; i < territoryInformation.size(); i++){
                String[] tempArray = (territoryInformation.get(i).split("\\."));
                ArrayList<Integer> tempConnectArray = new ArrayList<Integer>();
                String[] tempArray2 = tempArray[3].split(",");
                for(String s: tempArray2){
                    tempConnectArray.add(Integer.parseInt(s));
                }
                territoryList.add(new Territory(tempArray[0],Integer.parseInt(tempArray[1]),tempArray[2],tempConnectArray));
            }
            System.out.println("Current Territory List");
            for(Territory t: territoryList){
                System.out.printf("%-5d %-25s %-15s\n", t.getID(), t.getName(), t.getContinent());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void creditTransaction(){
        for(Player p: playerList){
            p.beginCreditTransaction();
        }
    }
    /*
     Method to distribute the territories randomly among the three telegram players and sends out the result to telegram bot.
     Also sends instruction to the players about the next step for example the format to send a command for reinforcement is
     /reinforce@CSGSanaz_bot#42 and the format for attack command is /attack@CSGSanaz_bot#33#40 the first number is territory id attacking from
     the second number is the territory id to be attacked.
     @Param: No parameters required
     */
    public static void TelegramTerritoryDistribution() {
        //Delete old tweets

        System.out.println("Randomizing initial territory control...");
        int player = 1;
        Random random = new Random();
        ArrayList<Territory> wTerritories = getDeepCopy(territoryList);
        while(wTerritories.size() > 0){
            if(wTerritories.size() < playerList.size()){
                while(wTerritories.size() > 0){
                    int nextTerritory = random.nextInt(wTerritories.size());
                    Territory tempT = wTerritories.get(nextTerritory);
                    Player tempP = playerList.get(player-1);
                    tempT.setTeam(tempP.getTeam());
                    wTerritories.remove(nextTerritory);
                    player++;
                }
            }else{
                while(player <= playerList.size()){
                    int nextTerritory = random.nextInt(wTerritories.size());
                    Territory tempT = wTerritories.get(nextTerritory);
                    Player tempP = playerList.get(player-1);
                    tempT.setTeam(tempP.getTeam());
                    wTerritories.remove(nextTerritory);
                    player++;
                }
                player = 1;
            }
        }
        System.out.println("Adding one reinforcement to every region...");
        for(Player p:playerList){
            for(Territory t:territoryList){
                if(t.getTeam().equals(p.getTeam())){
                    p.incTerritoryCount();
                    p.decTroopCount(1);
                    t.setTroopCount(1);
                }
            }
        }
    }
    /*
    Method should implement reinforcement by adding one troop to the requested territory,
    checks if its the players turn to reinforce, checks if the given territory ID belong to this player
    @Param :  player name and territory id to reinforce
     */
    public static void TelegramReinforce(String Player){

        for(Player p: playerList){
            if(p.getTeam().equals(Player) && p.getTroopCount() > 0){
                for(Territory t: territoryList) {
                    if(t.getTeam().equals(Player)) {
                        t.addTroops(7);
                        p.decTroopCount(7);
                        tempTerritoryHolder.add(t);
                    }
                }
            }else if(p.getTeam().equals(Player) && p.getTroopCount() == 0){
                System.out.println(Player + " is out of reinforcements");
            }
        }
    }
    // Find territories to attack From
    public static ArrayList FindWhereToAttackFrom(Player p){
        ArrayList<Territory> TList = new ArrayList<Territory>();
        for(Territory t: territoryList){
            if(t.getTeam().equals(p.getTeam()) && t.getTroopCount()>1){
                for(int i=0; i<t.getConnections().size();i++){
                    for(Territory X:territoryList){
                        if(X.getID() == t.getConnections().get(i) && !X.getTeam().equals(p.getTeam())){
                           if(!TList.contains(t)){TList.add(t);}
                        }
                    }
                }
            }
        } return TList;
    }
    // Find territories to attack
    public static ArrayList FindWhereICanAttack(String country) {
        ArrayList<Territory> TList2 = new ArrayList<Territory>();
        for (Territory t : territoryList) {
            if (t.getName().equals(country)) {
                AttackingTerr =t;
                for (int i = 0; i < t.getConnections().size(); i++) {
                    for (Territory X : territoryList) {
                        if (X.getID() == t.getConnections().get(i) && !X.getTeam().equals(t.getTeam())) {
                            if (!TList2.contains(X)) {
                                TList2.add(X);
                            }
                        }
                    }
                }
            }
        }return TList2;
    }

    // Simplifies process of Attacking by requesting attacking Player, defending territory, and attacking territory
    public static void fulfillAttack(Player p, Territory defendingTerritory, Territory attackingTerritory){
        //Initialize variables

        Integer[] AttackerDice;
        Integer[] DefenderDice;
        Dice dice = new Dice();
        //Notify console which territory is being attacked and roll die based on attacking and defending troopCount
        while(!WIN) {
            if (attackingTerritory.getTroopCount() == 1) {
                NotEnoughTroops = true;
                System.out.println(p.getTeam() + " only has 1 unit left. You must withdraw from attacking or start a new attack");
                break;
            }
            System.out.println(p.getTeam() + " is attacking " + defendingTerritory.getName() + " from " + attackingTerritory.getName());
            if (attackingTerritory.getTroopCount() >= 3 && defendingTerritory.getTroopCount() >= 2) {
                AttackerDice = dice.roll(3);
                DefenderDice = dice.roll(2);
                dice.compareFaceValue(AttackerDice, DefenderDice, attackingTerritory, defendingTerritory);
            } else if (attackingTerritory.getTroopCount() == 2 && defendingTerritory.getTroopCount() >= 2) {
                AttackerDice = dice.roll(2);
                DefenderDice = dice.roll(2);
                dice.compareFaceValue(AttackerDice, DefenderDice, attackingTerritory, defendingTerritory);
            } else if (attackingTerritory.getTroopCount() >= 3 && defendingTerritory.getTroopCount() == 1) {
                AttackerDice = dice.roll(3);
                DefenderDice = dice.roll(1);
                dice.compareFaceValue(AttackerDice, DefenderDice, attackingTerritory, defendingTerritory);
            } else if (attackingTerritory.getTroopCount() == 2 && defendingTerritory.getTroopCount() == 1) {
                AttackerDice = dice.roll(2);
                DefenderDice = dice.roll(1);
                dice.compareFaceValue(AttackerDice, DefenderDice, attackingTerritory, defendingTerritory);
            }
        }
    }
}
