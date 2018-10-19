package BaseGameEssentials;

import BaseGameEssentials.Territory;

public class Card {

    private String type;
    private int worth;
    private Territory country;
    private boolean inUse;

    public Card(String type, int worth, Territory country, boolean taken) {
        this.type = type;
        this.worth = worth;
        this.country = country;
        this.inUse = taken;
    }

    public String getType() {
        return type;
    }

    public int getWorth() {
        return worth;
    }

    public String getCountry() {
        return country.getName();
    }

    public boolean getInUseStatus() {
        return inUse;
    }

    public boolean isInUse(boolean status) {
        return this.inUse = status;
    }
}