package COSC4353RiskGame.COSC4353RiskGame;

import main.Game;

/**
 * App class acts as main class that will run game object and serve as simplest class 
 * @author Grant Williams
 * 
 * @date 10/12/18
 **/
public class App 
{
    public static void main( String[] args )
    {
    	Game game = new Game();
		game.run();
    }
}
