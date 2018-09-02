import java.lang.reflect.Array;
import java.util.ArrayList;

public class Territory extends Game{
    private String name;
    private int ID;
    private ArrayList<Integer> connectionID = new ArrayList<Integer>();
    private String team;
    private int troopCount;
    private String continent;

    public Territory(String n, int id, ArrayList<Integer> cID,String t, int tC, String con){
        this.name = n;
        this.ID = id;
        this.connectionID = cID;
        this.team = t;
        this.troopCount = tC;
        this.continent = con;
    }

    public String getName(){
        return this.name;
    }

    public void setTeam(String givenTeam){
        this.team = givenTeam;
    }

    public void setTroopCount(int amt){
        this.troopCount = amt;
    }

    public int getIDByName(String givenName){
        if(this.name.equalsIgnoreCase(givenName)){
            return this.ID;
        }else{
            return 0;
        }
    }

    public void getConnections(){
        //WORK IN PROGRESS

    }

    public String getTeam(){
        return this.team;
    }
}
