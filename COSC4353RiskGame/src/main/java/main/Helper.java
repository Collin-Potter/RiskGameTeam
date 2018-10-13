package main;

import java.util.Scanner;

/**
 * Helper class to be used throughout Risk game for methods that need to be used often and in different classes
 * @author Grant Williams
 * 
 * @date 10/11/18
 **/

public class Helper {

	private Scanner userInput;
	private String daBar;
	
	
	public Helper(){
		userInput = new Scanner(System.in);
		daBar = "---------------------------------------------------------------------------------------------------------------------------------------------------------------";
	}
	
	//Runs loop to confirm users input
	public boolean confirmDialog(String confirmMessage){
		boolean out = false;
		boolean validInput = false;
		String in = "";
		while(!validInput){ //confirm user input
			System.out.println(confirmMessage);
			System.out.println("Y/N");
			in = userInput.nextLine();
			if(!(in.equals("Y") || in.equals("N") || in.equals("y") || in.equals("n"))){
				System.out.println(daBar);
				System.out.println("Invalid input. Please try again.");
				System.out.println(daBar);
			}
			else{
				validInput = true;
				if(in.equals("Y") || in.equals("y")){
					out = true;
				}
				else{
					out = false;
				}
				System.out.println(daBar);
			}
		}
		return out;
	}
	
}
