import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game
 * each player will have : id, hand of hards, left deck(discard deck), right deck(draw deck)
 * must draw and discard cards during their turn
 * check if player won
 * run in parallel threads
 */

 public class Player implements Runnable{

    private final in playerId;
    private final List<Card> hand;
    private Deck leftDeck;
    private Deck rightDeck;
    private boolean hasWon;

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
        if (startingCards == null || startingCards.isEmpty()){
            throw new IllegalArgumentException("Starting hand must not be empty");
        }
        this.playerId=playerId;
        this.hand=new ArrayList<>(startingCards);
        this.hasWon=false;
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

    public boolean hasWon(){
        return hasWon;
    }

    //Gameplay
    /**
     * draw card and add it to player's hand
     * @throws EmptyDeckException - if right deck is empty
     * @throws IllegalStateException -if right deck has not been assigned
     */
    public synchronized void drawCard() throws EmptyDeckException{
        if (rightDeck==null){
            throw new IllegalStateException("Right deck is empty");
        }
        Card drawn=rightDeck.discardCard();
        hand.add(drawn);
        System.out.println("Player"+playerId+"drew card"+drawn.getValue());
    }

    /** discard card and add it to left deck
     * @throws EmptyDeckException if the left deck is empty
     * @throws IllegalStateException if the left deck has not been assigned
     */
    public synchronized void discard() throws EmptyDeckException{
        if (leftDeck==null){
            throw new IllegalStateException("Left deck is empty");
        }
        if (!hand.isEmpty()){
            Card discarded=hand.remove(0);
            leftDeck.drawCard(discarded);
            System.out.println("Player"+playerId+"discarded card"+discarded.getValue());
        }
    }

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
        hasWon=true;
        System.out.println("Player"+playerId+"has won");
        return true;
    }

    //THREAD
    //represent player's turn as a thread
    //player will draw/ discard cards until one wins of deck becomes empty
    @Override
    public void run(){
        try {
            while (!hasWon){
                drawCard();
                discardCard();
                if (checkIfWon()){
                    break;
                }
            }
        } catch (EmptyDeckException e){
            System.err.println("Player"+playerId+"has encounted an empty deck");
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    //return string representation of player's hand
    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder("Player"+playerId+"hand:");
        for (Card c: hand){
            sb.append(c.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
 }

