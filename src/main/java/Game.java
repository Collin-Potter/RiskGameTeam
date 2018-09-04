import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final int MAP_REGION_AMOUNT = 42;
    public static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    public static ArrayList<Player> playerList = new ArrayList<Player>();
    public static Scanner input = new Scanner(System.in);
    public static Player p1 = new Player(0,"playerOne",false,false);
    public static Player p2 = new Player(0,"playerTwo",false,false);
    public static Player p3 = new Player(0,"playerThree",false,false);
    public static Player p4 = new Player(0,"playerFour",false,false);
    public static Player p5 = new Player(0,"playerFive",false,false);
    public static Player p6 = new Player(0,"playerSix",false,false);
    public static void main(String[] args){
        fillRegions();
        gameSetUp();
        newTroopDistribution();
    }

    public static void gameSetUp(){
        System.out.println("Enter player amount...");
        int playerAmt = input.nextInt();
        System.out.println("Randomizing regions...");
        int rand;
        switch(playerAmt){
            case(2):
                playerList.add(p1);
                playerList.add(p2);
                for(Player p:playerList){
                    p.setTroopCount(40);
                }
                for(Territory o:territoryList){
                    rand = 1 + (int)(Math.random()*2);
                    switch(rand){
                        case(1):
                            o.setTeam("playerOne");
                            break;
                        case(2):
                            o.setTeam("playerTwo");
                            break;
                    }
                }
                break;
            case(3):
                playerList.add(p1);
                playerList.add(p2);
                playerList.add(p3);
                for(Player p:playerList){
                    p.setTroopCount(35);
                }
                for(Territory o:territoryList){
                    rand = 1 + (int)(Math.random()*3);
                    switch(rand){
                        case(1):
                            o.setTeam("playerOne");
                            break;
                        case(2):
                            o.setTeam("playerTwo");
                            break;
                        case(3):
                            o.setTeam("playerThree");
                            break;
                    }
                }
                break;
            case(4):
                playerList.add(p1);
                playerList.add(p2);
                playerList.add(p3);
                playerList.add(p4);
                for(Player p:playerList){
                    p.setTroopCount(30);
                }
                for(Territory o:territoryList){
                    rand = 1 + (int)(Math.random()*4);
                    switch(rand){
                        case(1):
                            o.setTeam("playerOne");
                            break;
                        case(2):
                            o.setTeam("playerTwo");
                            break;
                        case(3):
                            o.setTeam("playerThree");
                            break;
                        case(4):
                            o.setTeam("playerFour");
                            break;
                    }
                }
                break;
            case(5):
                playerList.add(p1);
                playerList.add(p2);
                playerList.add(p3);
                playerList.add(p4);
                playerList.add(p5);
                for(Player p:playerList){
                    p.setTroopCount(25);
                }
                for(Territory o:territoryList){
                    rand = 1 + (int)(Math.random()*5);
                    switch(rand){
                        case(1):
                            o.setTeam("playerOne");
                            break;
                        case(2):
                            o.setTeam("playerTwo");
                            break;
                        case(3):
                            o.setTeam("playerThree");
                            break;
                        case(4):
                            o.setTeam("playerFour");
                            break;
                        case(5):
                            o.setTeam("playerFive");
                            break;
                    }
                }
                break;
            case(6):
                playerList.add(p1);
                playerList.add(p2);
                playerList.add(p3);
                playerList.add(p4);
                playerList.add(p5);
                playerList.add(p6);
                for(Player p:playerList){
                    p.setTroopCount(20);
                }
                for(Territory o:territoryList){
                    rand = 1 + (int)(Math.random()*6);
                    switch(rand){
                        case(1):
                            o.setTeam("playerOne");
                            break;
                        case(2):
                            o.setTeam("playerTwo");
                            break;
                        case(3):
                            o.setTeam("playerThree");
                            break;
                        case(4):
                            o.setTeam("playerFour");
                            break;
                        case(5):
                            o.setTeam("playerFive");
                            break;
                        case(6):
                            o.setTeam("playerSix");
                            break;
                    }
                }
            break;
        }
        System.out.println("\n\nRandomizing ");
    }

    public static void newTroopDistribution(){
        System.out.println("Adding one reinforcement to every region...");
        for(Player p:playerList){
            for(Territory t:territoryList){
                if(t.getTeam().equals(p.getTeam())){
                    p.incTerritoryCount();
                    t.setTroopCount(1);
                }
            }
        }
        for(Player p:playerList){
            if(p.getTroopCount() != 0){
                System.out.println(p.getTeam() + " you have " + p.getTroopCount() + " troops to distribute...");
                System.out.println("Current territories: \n");
                for(Territory t:territoryList){
                    if(t.getTeam().equals(p.getTeam())){
                        System.out.printf("ID: %-5s Name: %-25s TroopCount: %-20d\n", t.getID(), t.getName(),t.getTroopCount());
                    }
                }
                p.reinforceRegions();
            }
        }
    }

    public static void fillRegions(){
        ArrayList<Integer> connectionList1 = new ArrayList<Integer>();
        connectionList1.add(2);
        connectionList1.add(6);
        connectionList1.add(22);
        Territory t1 = new Territory("Alaska", 1, connectionList1, "", 0, "NA");
        ArrayList<Integer> connectionList2 = new ArrayList<Integer>();
        connectionList2.add(1);
        connectionList2.add(6);
        connectionList2.add(7);
        connectionList2.add(9);
        Territory t2 = new Territory("Alberta", 2, connectionList2, "", 0, "NA");
        ArrayList<Integer> connectionList3 = new ArrayList<Integer>();
        connectionList3.add(4);
        connectionList3.add(9);
        connectionList3.add(32);
        Territory t3 = new Territory("Central America", 3, connectionList3, "", 0, "NA");
        ArrayList<Integer> connectionList4 = new ArrayList<Integer>();
        connectionList4.add(3);
        connectionList4.add(7);
        connectionList4.add(8);
        connectionList4.add(9);
        Territory t4 = new Territory("Eastern United States", 4, connectionList4, "", 0, "NA");
        ArrayList<Integer> connectionList5 = new ArrayList<Integer>();
        connectionList5.add(6);
        connectionList5.add(7);
        connectionList5.add(8);
        connectionList5.add(11);
        Territory t5 = new Territory("Greenland", 5, connectionList5, "", 0, "NA");
        ArrayList<Integer> connectionList6 = new ArrayList<Integer>();
        connectionList6.add(1);
        connectionList6.add(2);
        connectionList6.add(5);
        connectionList6.add(7);
        Territory t6 = new Territory("Northwest Territory", 6, connectionList6, "", 0, "NA");
        ArrayList<Integer> connectionList7 = new ArrayList<Integer>();
        connectionList7.add(2);
        connectionList7.add(4);
        connectionList7.add(5);
        connectionList7.add(6);
        connectionList7.add(8);
        Territory t7 = new Territory("Ontario", 7, connectionList7, "", 0, "NA");
        ArrayList<Integer> connectionList8 = new ArrayList<Integer>();
        connectionList8.add(4);
        connectionList8.add(5);
        connectionList8.add(7);
        Territory t8 = new Territory("Quebec",8,connectionList8,"",0,"NA");
        ArrayList<Integer> connectionList9 = new ArrayList<Integer>();
        connectionList9.add(2);
        connectionList9.add(3);
        connectionList9.add(4);
        connectionList9.add(7);
        Territory t9 = new Territory("Western United States", 9, connectionList9, "", 0, "NA");
        ArrayList<Integer> connectionList10 = new ArrayList<Integer>();
        connectionList10.add(11);
        connectionList10.add(12);
        connectionList10.add(13);
        connectionList10.add(16);
        Territory t10 = new Territory("Great Britain", 10, connectionList10, "", 0, "EU");
        ArrayList<Integer> connectionList11 = new ArrayList<Integer>();
        connectionList11.add(10);
        connectionList11.add(13);
        connectionList11.add(5);
        Territory t11 = new Territory("Iceland", 11, connectionList11, "", 0, "EU");
        ArrayList<Integer> connectionList12 = new ArrayList<Integer>();
        connectionList12.add(10);
        connectionList12.add(13);
        connectionList12.add(14);
        connectionList12.add(15);
        connectionList12.add(16);
        Territory t12 = new Territory("Northern Europe", 12, connectionList12, "", 0, "EU");
        ArrayList<Integer> connectionList13 = new ArrayList<Integer>();
        connectionList13.add(10);
        connectionList13.add(11);
        connectionList13.add(12);
        connectionList13.add(15);
        Territory t13 = new Territory("Scandinavia", 13, connectionList13, "", 0, "EU");
        ArrayList<Integer> connectionList14 = new ArrayList<Integer>();
        connectionList14.add(12);
        connectionList14.add(15);
        connectionList14.add(16);
        connectionList14.add(35);
        connectionList14.add(37);
        connectionList14.add(23);
        Territory t14 = new Territory("Southern Europe", 14, connectionList14, "", 0, "EU");
        ArrayList<Integer> connectionList15 = new ArrayList<Integer>();
        connectionList15.add(12);
        connectionList15.add(13);
        connectionList15.add(14);
        connectionList15.add(17);
        connectionList15.add(23);
        connectionList15.add(27);
        Territory t15 = new Territory("Ukraine", 15, connectionList15, "", 0, "EU");
        ArrayList<Integer> connectionList16 = new ArrayList<Integer>();
        connectionList16.add(10);
        connectionList16.add(12);
        connectionList16.add(14);
        connectionList16.add(37);
        Territory t16 = new Territory("Western Europe", 16, connectionList16, "", 0, "EU");
        ArrayList<Integer> connectionList17 = new ArrayList<Integer>();
        connectionList17.add(18);
        connectionList17.add(19);
        connectionList17.add(23);
        connectionList17.add(27);
        connectionList17.add(15);
        Territory t17 = new Territory("Afghanistan", 17, connectionList17, "", 0, "Asia");
        ArrayList<Integer> connectionList18 = new ArrayList<Integer>();
        connectionList18.add(17);
        connectionList18.add(19);
        connectionList18.add(24);
        connectionList18.add(25);
        connectionList18.add(26);
        connectionList18.add(27);
        Territory t18 = new Territory("China", 18, connectionList18, "", 0, "Asia");
        ArrayList<Integer> connectionList19 = new ArrayList<Integer>();
        connectionList19.add(17);
        connectionList19.add(18);
        connectionList19.add(23);
        connectionList19.add(25);
        Territory t19 = new Territory("India", 19, connectionList19, "", 0, "Asia");
        ArrayList<Integer> connectionList20 = new ArrayList<Integer>();
        connectionList20.add(22);
        connectionList20.add(24);
        connectionList20.add(26);
        connectionList20.add(28);
        Territory t20 = new Territory("Irkutsk", 20, connectionList20, "", 0, "Asia");
        ArrayList<Integer> connectionList21 = new ArrayList<Integer>();
        connectionList21.add(22);
        connectionList21.add(24);
        Territory t21 = new Territory("Japan", 21, connectionList21, "", 0, "Asia");
        ArrayList<Integer> connectionList22 = new ArrayList<Integer>();
        connectionList22.add(20);
        connectionList22.add(21);
        connectionList22.add(24);
        connectionList22.add(28);
        connectionList22.add(1);
        Territory t22 = new Territory("Kamchatka", 22, connectionList22, "", 0, "Asia");
        ArrayList<Integer> connectionList23 = new ArrayList<Integer>();
        connectionList23.add(17);
        connectionList23.add(19);
        connectionList23.add(34);
        connectionList23.add(35);
        Territory t23 = new Territory("Middle East", 23, connectionList23, "", 0, "Asia");
        ArrayList<Integer> connectionList24 = new ArrayList<Integer>();
        connectionList24.add(18);
        connectionList24.add(20);
        connectionList24.add(21);
        connectionList24.add(22);
        connectionList24.add(26);
        Territory t24 = new Territory("Mongolia", 24, connectionList24, "", 0, "Asia");
        ArrayList<Integer> connectionList25 = new ArrayList<Integer>();
        connectionList25.add(18);
        connectionList25.add(19);
        connectionList25.add(40);
        Territory t25 = new Territory("Slam", 25, connectionList25, "", 0, "Asia");
        ArrayList<Integer> connectionList26 = new ArrayList<Integer>();
        connectionList26.add(18);
        connectionList26.add(20);
        connectionList26.add(24);
        connectionList26.add(27);
        connectionList26.add(28);
        Territory t26 = new Territory("Siberia", 26, connectionList26, "", 0, "Asia");
        ArrayList<Integer> connectionList27 = new ArrayList<Integer>();
        connectionList27.add(17);
        connectionList27.add(18);
        connectionList27.add(26);
        connectionList27.add(15);
        Territory t27 = new Territory("Ural", 27, connectionList27, "", 0, "Asia");
        ArrayList<Integer> connectionList28 = new ArrayList<Integer>();
        connectionList28.add(20);
        connectionList28.add(22);
        connectionList28.add(26);
        Territory t28 = new Territory("Yakutsk", 28, connectionList28, "", 0, "Asia");
        ArrayList<Integer> connectionList29 = new ArrayList<Integer>();
        connectionList29.add(30);
        connectionList29.add(31);
        Territory t29 = new Territory("Argentina", 29, connectionList29, "", 0, "SA");
        ArrayList<Integer> connectionList30 = new ArrayList<Integer>();
        connectionList30.add(29);
        connectionList30.add(31);
        connectionList30.add(32);
        connectionList30.add(37);
        Territory t30 = new Territory("Brazil", 30, connectionList30, "", 0, "SA");
        ArrayList<Integer> connectionList31 = new ArrayList<Integer>();
        connectionList31.add(29);
        connectionList31.add(30);
        connectionList31.add(32);
        Territory t31 = new Territory("Peru", 31, connectionList31, "", 0, "SA");
        ArrayList<Integer> connectionList32 = new ArrayList<Integer>();
        connectionList32.add(30);
        connectionList32.add(31);
        connectionList32.add(3);
        Territory t32 = new Territory("Venezuela", 32, connectionList32, "", 0, "SA");
        ArrayList<Integer> connectionList33 = new ArrayList<Integer>();
        connectionList33.add(34);
        connectionList33.add(37);
        connectionList33.add(38);
        Territory t33 = new Territory("Congo", 33, connectionList33, "", 0, "Africa");
        ArrayList<Integer> connectionList34 = new ArrayList<Integer>();
        connectionList34.add(33);
        connectionList34.add(35);
        connectionList34.add(36);
        connectionList34.add(37);
        connectionList34.add(23);
        Territory t34 = new Territory("East Africa", 34, connectionList34, "", 0, "Africa");
        ArrayList<Integer> connectionList35 = new ArrayList<Integer>();
        connectionList35.add(34);
        connectionList35.add(37);
        connectionList35.add(14);
        connectionList35.add(23);
        Territory t35 = new Territory("Egypt", 35, connectionList35, "", 0, "Africa");
        ArrayList<Integer> connectionList36 = new ArrayList<Integer>();
        connectionList36.add(34);
        connectionList36.add(38);
        Territory t36 = new Territory("Madagascar", 36, connectionList36, "", 0, "Africa");
        ArrayList<Integer> connectionList37 = new ArrayList<Integer>();
        connectionList37.add(33);
        connectionList37.add(34);
        connectionList37.add(35);
        connectionList37.add(14);
        connectionList37.add(16);
        connectionList37.add(30);
        Territory t37 = new Territory("North Africa", 37, connectionList37, "", 0, "Africa");
        ArrayList<Integer> connectionList38 = new ArrayList<Integer>();
        connectionList38.add(33);
        connectionList38.add(34);
        connectionList38.add(36);
        Territory t38 = new Territory("South Africa", 38, connectionList38, "", 0, "Africa");
        ArrayList<Integer> connectionList39 = new ArrayList<Integer>();
        connectionList39.add(41);
        connectionList39.add(42);
        Territory t39 = new Territory("Eastern Australia", 39, connectionList39, "", 0, "Australia");
        ArrayList<Integer> connectionList40 = new ArrayList<Integer>();
        connectionList40.add(41);
        connectionList40.add(42);
        connectionList40.add(25);
        Territory t40 = new Territory("Indonesia", 40, connectionList40, "", 0, "Australia");
        ArrayList<Integer> connectionList41 = new ArrayList<Integer>();
        connectionList41.add(39);
        connectionList41.add(40);
        connectionList41.add(42);
        Territory t41 = new Territory("New Guinea", 41, connectionList41, "", 0, "Australia");
        ArrayList<Integer> connectionList42 = new ArrayList<Integer>();
        connectionList42.add(39);
        connectionList42.add(40);
        connectionList42.add(41);
        Territory t42 = new Territory("Western Australia", 42, connectionList42, "", 0, "Australia");
        territoryList.add(t1);
        territoryList.add(t2);
        territoryList.add(t3);
        territoryList.add(t4);
        territoryList.add(t5);
        territoryList.add(t6);
        territoryList.add(t7);
        territoryList.add(t8);
        territoryList.add(t9);
        territoryList.add(t10);
        territoryList.add(t11);
        territoryList.add(t12);
        territoryList.add(t13);
        territoryList.add(t14);
        territoryList.add(t15);
        territoryList.add(t16);
        territoryList.add(t17);
        territoryList.add(t18);
        territoryList.add(t19);
        territoryList.add(t20);
        territoryList.add(t21);
        territoryList.add(t22);
        territoryList.add(t23);
        territoryList.add(t24);
        territoryList.add(t25);
        territoryList.add(t26);
        territoryList.add(t27);
        territoryList.add(t28);
        territoryList.add(t29);
        territoryList.add(t30);
        territoryList.add(t31);
        territoryList.add(t32);
        territoryList.add(t33);
        territoryList.add(t34);
        territoryList.add(t35);
        territoryList.add(t36);
        territoryList.add(t37);
        territoryList.add(t38);
        territoryList.add(t39);
        territoryList.add(t40);
        territoryList.add(t41);
        territoryList.add(t42);

    }
}
