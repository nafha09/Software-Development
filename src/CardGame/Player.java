package CardGame;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
//import org.junit.BeforeClass;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
import java.io.*;
//import java.io.FileWriter;
//import java.io.IOException;


/**
 * Represents a player in the game
 * each player will have : id, hand of hards, left deck(discard deck), right deck(draw deck)
 * must draw and discard cards during their turn
 * check if player won
 * run in parallel threads
 */

 public class Player implements Runnable{

    private final int playerId;
    private final List<Card> hand;
    private Deck leftDeck;
    private Deck rightDeck;
    private boolean hasWon;
    private final int preferredValue;
    private final String outputFile;
    //shared win flag
    private static volatile boolean gameWon=false;
    private static volatile int winningPlayerId=-1;

    /**
     * constructor
     * @param playerId -unique ID of player
     * @param startingCards - list of 4 cards
     * @param IllegalArgumentException - if playerId is negative or hand is empty
     */
    public Player(int playerId, List<Card> startingCards){
        if (playerId < 0){
            throw new IllegalArgumentException("Player Id must be non-negative");
        }
        if (startingCards == null ){//|| startingCards.isEmpty()){
            throw new IllegalArgumentException("Starting hand must not be empty");
        }
        this.playerId=playerId;
        this.hand=new ArrayList<>(startingCards);
        this.hasWon=false;
        this.preferredValue = playerId;
        this.outputFile="player"+playerId+"output.txt";
        clearLog();
        log("final hand: "+handToString());
    }

    /// setting decks
    public void setLeftDeck(Deck deck){
        this.leftDeck=deck;
    }
    public void setRightDeck(Deck deck){
        this.rightDeck=deck;
    }

    ///getters
    public int getPlayerId(){
        return playerId;
    }

    public int getPreferredValue(){
        return playerId;
    }

    public boolean hasWon(){
        return hasWon;
    }

    /**
     * add cards to players hand during set
     * @param card to add
     */
    public void addCard(Card card){
        hand.add(card);
    }

    //Gameplay
    /**
     * draw card and add it to player's hand
     * @throws EmptyDeckException - if right deck is empty
     * @throws IllegalStateException -if right deck has not been assigned
     */
    public synchronized void drawCard() throws EmptyDeckException{
        if (leftDeck==null){
            throw new IllegalStateException("Right deck not assigned");
        }
        Card drawn=leftDeck.discardCard();
        hand.add(drawn);
        log("Player "+playerId+" draws "+drawn.getValue()+" from deck "+leftDeck.getId());
    }

    /** discard card and add it to left deck
     * @throws EmptyDeckException if the left deck is empty
     * @throws IllegalStateException if the left deck has not been assigned
     */
    // Discard a card to the RIGHT deck
    public synchronized void discardCard() throws EmptyDeckException {
        if (rightDeck == null) {
            throw new IllegalStateException("Left deck not assigned");
        }
        if (!hand.isEmpty()) {
            // Prefer discarding a non-preferred card
            Card toDiscard = null;
            for (Card c : hand) {
                if (c.getValue() != preferredValue) {
                    toDiscard = c;
                    break;
                }
            }
            // if all are preferred, just discard the first card
            if (toDiscard == null) {
                toDiscard = hand.get(0);
            }
            hand.remove(toDiscard);
            rightDeck.drawCard(toDiscard);  //  put card on bottom of right deck
            log("Player " + playerId + " discards " + toDiscard.getValue() + " to deck " + rightDeck.getId());
        }
    }
            //if (discarded.getValue() == (preferredValue)){
              //  return; 
            //}  
            //leftDeck.drawCard(discarded);
            //log("Player"+playerId+"discards"+discarded.getValue()+"to deck"+leftDeck.getId());
        //}
    

    /** check is all cards in players hand is of same value
     * @return true if all cards are equal, else false
     */
    public synchronized boolean checkIfWon(){
        if (hand.isEmpty()) return false;
        int firstValue = hand.get(0).getValue();
        for (Card c: hand){
            if (c.getValue()!= firstValue){
                return false;
            }
        }
        if (!gameWon){
            gameWon=true;
            winningPlayerId= playerId;
            hasWon=true;
            log("Player "+playerId+" has won");
            System.out.println("Player "+playerId+" has won");
        }
        return true;
    }

    //THREAD
    //represent player's turn as a thread
    //player will draw/ discard cards until one wins of deck becomes empty
    @Override
    public void run() {
        try {
            // Continue playing until someone wins
            while (!gameWon) {

                // Only draw if the left deck still has cards
                if (!leftDeck.isEmpty()) {
                    drawCard();
                } else {
                    // Stop this player's turn if deck is empty
                    System.err.println("Player " + playerId + " has encounted an empty deck");
                    break;
                }

                // Always attempt to discard a card to the right deck
                if (!hand.isEmpty()) {
                    discardCard();
                }

                // Check after each round if the player has won
                checkIfWon();
            }

            // When game is over, log outcome for this player
            if (!hasWon) {
                log("Player " + winningPlayerId + " has won");
            }

            log("Final hand: " + handToString());

        } catch (EmptyDeckException e) {
            // Safety catch — though with isEmpty() guard, this should not occur
            System.err.println("Player " + playerId + " has encounted an empty deck");
        }
    }


    /* 
    public void run() {
        try {
            while (!gameWon) {
                synchronized (this) { 
                    drawCard();
                    discardCard();
                    checkIfWon();
                }

                // Allow other players a chance to act (simulate turn-taking)
                Thread.sleep(100);
            }

            if (!hasWon) {
                log("Player " + winningPlayerId + " has won");
            }
            log("Final hand: " + handToString());

        } catch (EmptyDeckException e) {
            System.err.println("Player " + playerId + " has encounted an empty deck");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Player " + playerId + " was interrupted");
        }
    }
    */

    @Override
    public String toString() {
        return "Player " + playerId + " hand: " + handToString();
    }

    private void log(String msg){
        try (FileWriter fw= new FileWriter(outputFile, true)){
            fw.write(msg+System.lineSeparator());
        } catch(IOException e){
            System.err.println("Error in writing log for player "+playerId);
        }
    }
    private void clearLog(){
        try (FileWriter fw=new FileWriter(outputFile, false)){
            fw.write("");
        } catch (IOException ignored){
        }
    }

    //return string representation of player's hand
    //@Override
    private String handToString(){
        StringBuilder sb= new StringBuilder("[");
        for (int i=0; i< hand.size();i++){
            sb.append(hand.get(i).getValue());
            if (i< hand.size()-1){
                 sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
        //StringBuilder sb= new StringBuilder("Player"+playerId+"hand:");
        //for (Card c: hand){
        //    sb.append(c.getValue()).append(" ");
//}
        //return sb.toString().trim();
    }
        /**
     * Utility method for testing — resets shared game state.
     * This ensures JUnit tests run independently.
     */
    public static void resetGameStateForTests() {
        gameWon = false;
        winningPlayerId = -1;
    }

 }

