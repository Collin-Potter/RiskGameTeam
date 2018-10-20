package PaymentDirectory;

import BaseGameEssentials.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JPane {

    private String[] nameOptionList = new String[Game.playerList.size()-1];
    private JOptionPane pane = new JOptionPane();
    private Player currentUser;
    private UIManager ui = new UIManager();
    private boolean inputIncorrect;
    private ArrayList<String> tempArray = new ArrayList<String>();
    private ArrayList<Player> tempPlayerList = new ArrayList<Player>();

    public void displayGUI(int transactionType, Player currentUser){
        this.currentUser = currentUser;
        determineMenu(transactionType);
    }

    public void determineMenu(int transactionType){
        switch(transactionType){
            case(1):
                displayCreditAmountPrompt();
                break;
            case(2):
                displayCardAmount();
                break;
            case(3):
                displayUndoActionAmount();
                break;
            case(4):
                int selectedValue = displayTransferRecipientPrompt();
                if(selectedValue == -1) {
                    System.out.println("User has closed selection menu...\nContinuing without transfer");
                }else {
                    displayTransferAmountPrompt(selectedValue);
                }
                break;
        }
    }

    public void displayCreditAmountPrompt(){
        String selectedCreditAmount;
        int selectedValue;
        inputIncorrect = true;
        while(inputIncorrect) {
            try {
                selectedCreditAmount = pane.showInputDialog(null,
                        "Enter Amount of Credits Desired ",
                        "Credit Purchase Amount Menu",
                        JOptionPane.QUESTION_MESSAGE);
                if (selectedCreditAmount == null) {
                    throw new InterruptedException();
                }
                selectedValue = Integer.parseInt(selectedCreditAmount);
                currentUser.setCredits(currentUser.getCredits() + selectedValue);
                System.out.println(currentUser.getTeam() + " has received " + selectedValue + " credits!");
                inputIncorrect = false;
            } catch (InterruptedException ie) {
                System.out.println("Selector Dialog Menu Has Been Closed. Continuing With Program...");
                selectedValue = 0;
                inputIncorrect = false;
            } catch (Exception e) {
                System.out.println("Please enter an integer value of your desired credit amount...");
                selectedCreditAmount = "";
            }
        }
    }

    public void displayCardAmount() {
        tempArray = new ArrayList<String>();
        inputIncorrect = true;
        int cardChoice = 0;
        String[] cardChoiceList = {"Infantry","Cavalry","Artillery","Return"};
        while(inputIncorrect){
            try{
                ui.put("OptionPane.minimumSize",new Dimension(600,300));
                JOptionPane pane = new JOptionPane();
                cardChoice = pane.showOptionDialog(null,
                        "Please Choose the Card Type",
                        "Card Selector",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        cardChoiceList,
                        cardChoiceList[0]);
                inputIncorrect = false;
            } catch (Exception e) {
                System.out.println("Error opening card choice interface");
            }
        }
        inputIncorrect = true;
        //Prompt user for selection of card amount based on card type chosen.
        while(inputIncorrect){
            switch(cardChoice){
                case(0):
                    try{
                        ui.put("OptionPane.minimumSize",new Dimension(600,300));
                        JOptionPane pane = new JOptionPane();
                        String cardAmount = pane.showInputDialog(null,
                                "Enter Amount of " + cardChoiceList[cardChoice] + " cards you would like to purchase\n" +
                                        "You currently hold " + currentUser.getInfantryCardsList().size() + " " + cardChoiceList[cardChoice] + " cards.\n" +
                                        "You currently have " + currentUser.getCredits() + " credits.\n" +
                                        cardChoiceList[cardChoice] + " cards cost 1000 credits...",
                                "Transfer Amount Menu",
                                JOptionPane.QUESTION_MESSAGE);
                        inputIncorrect = false;
                        if (cardAmount == null){
                            throw new InterruptedException("User closed console");
                        } else if(((Integer.parseInt(cardAmount))*1000) <= currentUser.getCredits()){
                            System.out.println("Adding " + Integer.parseInt(cardAmount) + " " + cardChoiceList[cardChoice] + " to " + currentUser.getTeam() + "'s deck...");
                            for(int i = 0; i < (Integer.parseInt(cardAmount)); i++) {
                                Card tempCard = new Card("Infantry", 1, Game.territoryList.get(0), false);
                                currentUser.addInfantryCardsList(tempCard);
                                currentUser.setCredits(currentUser.getCredits() - 1000);
                            }
                        } else {
                            System.out.println("You do not have enough funds...");
                        }
                    } catch (Exception e) {
                        System.out.println("Error opening card amount interface");
                    }
                    break;
                case(1):
                    try{
                        ui.put("OptionPane.minimumSize",new Dimension(600,300));
                        JOptionPane pane = new JOptionPane();
                        String cardAmount = pane.showInputDialog(null,
                                "Enter Amount of " + cardChoiceList[cardChoice] + " cards you would like to purchase\n" +
                                        "You currently hold " + currentUser.getCalvalryCardsList().size() + " " + cardChoiceList[cardChoice] + " cards.\n" +
                                        "You currently have " + currentUser.getCredits() + " credits.\n" +
                                        cardChoiceList[cardChoice] + " cards cost 3000 credits...",
                                "Transfer Amount Menu",
                                JOptionPane.QUESTION_MESSAGE);
                        inputIncorrect = false;
                        if (cardAmount == null){
                            throw new InterruptedException("User closed console");
                        } else if(((Integer.parseInt(cardAmount))*3000) <= currentUser.getCredits()){
                            System.out.println("Adding " + Integer.parseInt(cardAmount) + " " + cardChoiceList[cardChoice] + " to " + currentUser.getTeam() + "'s deck...");
                            for(int i = 0; i < (Integer.parseInt(cardAmount)); i++) {
                                Card tempCard = new Card("Cavalry",5, Game.territoryList.get(0), false);
                                currentUser.addCalvalryCardsList(tempCard);
                                currentUser.setCredits(currentUser.getCredits() - 3000);
                            }
                        } else {
                            System.out.println("You do not have enough funds...");
                        }
                    } catch (Exception e) {
                        System.out.println("Error opening card amount interface");
                    }
                    break;
                case(2):
                    try{
                        ui.put("OptionPane.minimumSize",new Dimension(600,300));
                        JOptionPane pane = new JOptionPane();
                        String cardAmount = pane.showInputDialog(null,
                                "Enter Amount of " + cardChoiceList[cardChoice] + " cards you would like to purchase\n" +
                                        "You currently hold " + currentUser.getArtillaryCardsList().size() + " " + cardChoiceList[cardChoice] + " cards.\n" +
                                        "You currently have " + currentUser.getCredits() + " credits.\n" +
                                        cardChoiceList[cardChoice] + " cards cost 5000 credits...",
                                "Transfer Amount Menu",
                                JOptionPane.QUESTION_MESSAGE);
                        inputIncorrect = false;
                        if (cardAmount == null){
                            throw new InterruptedException("User closed console");
                        } else if(((Integer.parseInt(cardAmount))*5000) <= currentUser.getCredits()){
                            System.out.println("Adding " + Integer.parseInt(cardAmount) + " " + cardChoiceList[cardChoice] + " to " + currentUser.getTeam() + "'s deck...");
                            for(int i = 0; i < (Integer.parseInt(cardAmount)); i++) {
                                Card tempCard = new Card("Artillery",10, Game.territoryList.get(0), false);
                                currentUser.addArtillaryCardsList(tempCard);
                                currentUser.setCredits(currentUser.getCredits() - 5000);
                            }
                        } else {
                            System.out.println("You do not have enough funds...");
                        }
                    } catch (Exception e) {
                        System.out.println("Error opening card amount interface");
                    }
                    break;
                    default:
                        System.out.println("User has exited interface...");
                        inputIncorrect = false;
                        break;
            }
        }
    }

    public void displayUndoActionAmount() {
        inputIncorrect = true;
        while(inputIncorrect){
            try{
                ui.put("OptionPane.minimumSize",new Dimension(600,300));
                JOptionPane pane = new JOptionPane();
                String undoAmount = pane.showInputDialog(null,
                        "Enter Amount of undo actions you would like to purchase\n" +
                                "You currently hold " + currentUser.getNumUndo() + " undo actions...\n" +
                                "You currently have " + currentUser.getCredits() + " credits.\n" +
                                "undo actions cost 1000 credits...",
                        "Transfer Amount Menu",
                        JOptionPane.QUESTION_MESSAGE);
                inputIncorrect = false;
                if (undoAmount == null){
                    throw new InterruptedException("User closed console");
                } else if(((Integer.parseInt(undoAmount))*1000) <= currentUser.getCredits()){
                    System.out.println("Adding " + Integer.parseInt(undoAmount) + " undo actions to " + currentUser.getTeam() + "'s hand...");
                    for(int i = 0; i < (Integer.parseInt(undoAmount)); i++) {
                        currentUser.setNumUndo(currentUser.getNumUndo() + Integer.parseInt(undoAmount));
                        currentUser.setCredits(currentUser.getCredits() - 1000);
                    }
                } else {
                    System.out.println("You do not have enough funds...");
                }
            } catch (Exception e) {
                System.out.println("Error opening undo action amount interface");
            }
            break;
        }
    }

    public void displayTransferAmountPrompt(int selectedValue){
        String userSelected = nameOptionList[selectedValue];
        String selectedTransferAmount;
        int selectedValue2;
        inputIncorrect = true;
        while(inputIncorrect) {
            try {
                selectedTransferAmount = pane.showInputDialog(null,
                        "Enter Amount of Credits to Transfer to " + userSelected +
                                "\nYou currently hold " + currentUser.getCredits() + " credits.",
                        "Transfer Amount Menu",
                        JOptionPane.QUESTION_MESSAGE);
                if (selectedTransferAmount == null) {
                    throw new InterruptedException();
                }
                Player recipient = tempPlayerList.get(selectedValue);
                selectedValue2 = Integer.parseInt(selectedTransferAmount);
                if (selectedValue2 <= currentUser.getCredits()) {
                    currentUser.setCredits(currentUser.getCredits() - selectedValue2);
                    System.out.println(currentUser.getTeam() + " is transferring " + selectedValue2 + " credits...");
                    recipient.setCredits(recipient.getCredits() + selectedValue2);
                    System.out.println(recipient.getTeam() + " has received " + selectedValue2 + " credits!");
                    inputIncorrect = false;
                } else {
                    System.out.println("You have chosen a credit amount over your current credit amount..\n" +
                            "Please Try Again");
                }
            } catch (InterruptedException ie) {
                System.out.println("Selector Dialog Menu Has Been Closed. Continuing With Program...");
                selectedValue2 = 0;
                inputIncorrect = false;
            } catch (Exception e) {
                System.out.println("Please enter an integer value of your desired transfer amount...");
                selectedTransferAmount = "";
            }
        }
    }

    public int displayTransferRecipientPrompt(){
        tempArray = new ArrayList<String>();
        tempPlayerList = new ArrayList<Player>();
        for(int i = 0; i < Game.playerList.size(); i++){
            if(Game.playerList.get(i) != currentUser) {
                tempArray.add(Game.playerList.get(i).getTeam());
                tempPlayerList.add(Game.playerList.get(i));
            }
        }
        for(int i = 0; i < tempArray.size(); i++){
            nameOptionList[i] = tempArray.get(i);
        }
        try {
            ui.put("OptionPane.minimumSize",new Dimension(600,300));
            JOptionPane pane = new JOptionPane();
            return pane.showOptionDialog(null,
                    "Please Choose the Recipient",
                    "Recipient Selector",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    nameOptionList,
                    nameOptionList[0]);
        } catch(Exception e) {
            System.out.println("ERROR: Failure creating Recipient Selector Menu");
        }
        return 0;
    }
}
