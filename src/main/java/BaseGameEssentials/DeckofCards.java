package BaseGameEssentials;

import BaseGameEssentials.Card;
import BaseGameEssentials.Game;
import BaseGameEssentials.Territory;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class DeckofCards extends Game {
   // private BaseGameEssentials.Game Regions;
    private ArrayList<Territory> countries = new ArrayList();
    private ArrayList<Card> set = new ArrayList();


    public DeckofCards( ArrayList<Territory> cr,ArrayList<Card> CardSet ){
        this.countries= cr;
        this.set = CardSet;
    }

     public void generate(){
         Collections.shuffle(countries, new Random( ));
         for( int i = 0; i< 14; i++){
             set.add( new Card ("Infantry",1, countries.get(i), false));
         }
         for( int i = 14; i< 28; i++){
             set.add( new Card ("Cavalry",5, countries.get(i), false));
         }
         for( int i = 28; i< 42; i++){
             set.add( new Card ("Artillery",10, countries.get(i), false));
         }
     }

     public Card drawACard() {
        Card variable;
        Collections.shuffle(set, new Random());

        do {
           int i = (int) (Math.random() * (42 - 0)) + 0;
            variable = set.get(i);
        }while(variable.getInUseStatus()==true);
        variable.isInUse(true);
         return(variable);
     }

     public void ShowCard( Card element){
         System.out.println("Type of Card: " + element.getType() );
         System.out.println("Country on the Card: " + element.getCountry() );
         System.out.println("Worth of Card: " + element.getWorth() +" Armies");
     }
}
