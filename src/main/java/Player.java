public class Player {
    private int troopCount;
    private String team;
    private boolean isTurn;
    private boolean enabled;

    public Player(int troops, String t, boolean turn, boolean playing){
        this.troopCount = troops;
        this.team = t;
        this.isTurn = turn;
        this.enabled = playing;
    }

    public void reinforceRegions(){

    }

    public void attackRegion(){

    }

    public void defendRegion(){

    }

    public void isPlaying(boolean play){
        this.enabled = play;
    }
}
