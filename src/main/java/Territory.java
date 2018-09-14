import java.lang.reflect.Array;
import java.util.ArrayList;

public class Territory extends Game{
    private String name;
    private int ID;
    private ArrayList<Integer> connectionID = new ArrayList<Integer>();
    private String team;
    private int troopCount;
    private String continent;

    public Territory(String n, int id, String con, ArrayList<Integer> cID){
        this.name = n;
        this.ID = id;
        this.connectionID = cID;
        this.troopCount = 1;
        this.continent = con;
    }

    public String getName(){
        return this.name;
    }

    public String getContinent(){
        return this.continent;
    }

    public void setTeam(String givenTeam){
        this.team = givenTeam;
    }

    public void setTroopCount(int amt){
        this.troopCount = amt;
    }

    public void addTroops(int i){ this.troopCount += i; }

//    public int getIDByName(String givenName){
//        if(this.name.equalsIgnoreCase(givenName)){
//            return this.ID;
//        }else{
//            return 0;
//        }
//    }

    public int getID(){
        return this.ID;
    }

    public ArrayList<Integer> getConnections(){
        //WORK IN PROGRESS
        return this.connectionID;
    }

    public String getTeam(){
        return this.team;
    }

    public int getTroopCount(){
        return this.troopCount;
    }
}
