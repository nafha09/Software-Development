import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the card game.
 * Each player has:
 *  - a unique ID
 *  - a hand of cards
 *  - a left deck (to discard to)
 *  - a right deck (to draw from)
 *
 * The Player can draw and discard cards during their turn,
 * and can check if they have won (when all cards in hand have the same value).
 *
 * This class implements Runnable, allowing multiple players
 * to run in parallel threads if needed.
 */
public class Player implements Runnable {

    private final int playerId;   // unique player identifier
    private final List<Card> hand; // player's hand (cards currently held)
    private Deck leftDeck;        // deck on the left (where player discards)
    private Deck rightDeck;       // deck on the right (where player draws from)
    private boolean hasWon;       // flag indicating if player has won

    /**
     * Constructs a new player with an ID and a starting hand of cards.
     *
     * @param playerId       the unique ID of the player
     * @param startingCards  list of the cards the player starts with
     * @throws IllegalArgumentException if playerId is negative or the hand is empty
     */
    public Player(int playerId, List<Card> startingCards) {
        if (playerId < 0) {
            throw new IllegalArgumentException("Player ID must be non-negative");
        }
        if (startingCards == null || startingCards.isEmpty()) {
            throw new IllegalArgumentException("Starting hand cannot be empty");
        }
        this.playerId = playerId;
        this.hand = new ArrayList<>(startingCards); // defensive copy of cards
        this.hasWon = false;
    }
    // ASSIGNING DECKS
    /** Assigns the left deck where this player will discard cards. */
    public void setLeftDeck(Deck deck) {
        this.leftDeck = deck;
    }
    /** Assigns the right deck where this player will draw cards from. */
    public void setRightDeck(Deck deck) {
        this.rightDeck = deck;
    }
    // GETTERS
    /** Returns the player's unique ID. */
    public int getPlayerId() {
        return playerId;
    }

    /** Returns true if the player has won the game. */
    public boolean hasWon() {
        return hasWon;
    }
    // GAMEPLAY METHODS
    /**
     * Draws one card from the right deck and adds it to the player's hand.
     *
     * @throws EmptyDeckException if the right deck is empty
     * @throws IllegalStateException if the right deck has not been assigned
     */
    public synchronized void drawCard() throws EmptyDeckException {
        if (rightDeck == null) {
            throw new IllegalStateException("Right deck not assigned");
        }

        // Take the top card from the right deck
        Card drawn = rightDeck.discardCard();
        hand.add(drawn); // Add the drawn card to the player's hand
        System.out.println("Player " + playerId + " drew card " + drawn.getValue());
    }

    /**
     * Discards one card (the first in hand) to the left deck.
     *
     * @throws EmptyDeckException if the left deck is invalid or inaccessible
     * @throws IllegalStateException if the left deck has not been assigned
     */
    public synchronized void discardCard() throws EmptyDeckException {
        if (leftDeck == null) {
            throw new IllegalStateException("Left deck not assigned");
        }

        // Simple discard rule: always discard the first card in hand
        if (!hand.isEmpty()) {
            Card discarded = hand.remove(0);
            leftDeck.drawCard(discarded); // place at bottom of left deck
            System.out.println("Player " + playerId + " discarded card " + discarded.getValue());
        }
    }

    /**
     * Checks whether all cards in the player's hand have the same value.
     *
     * @return true if all cards are equal, false otherwise
     */
    public synchronized boolean checkIfWon() {
        // Empty hand = not a win
        if (hand.isEmpty()) return false;

        // Get the value of the first card
        int firstValue = hand.get(0).getValue();

        // Compare each card's value to the first
        for (Card c : hand) {
            if (c.getValue() != firstValue) {
                return false;
            }
        }

        // If we reach here, all cards match
        hasWon = true;
        System.out.println("🎉 Player " + playerId + " has won!");
        return true;
    }
    // THREAD BEHAVIOUR
    /**
     * Represents the player's turn logic when running as a thread.
     * The player repeatedly draws and discards cards until they win
     * or the deck becomes empty.
     */
    @Override
    public void run() {
        try {
            while (!hasWon) {
                drawCard();         // draw from right deck
                discardCard();      // discard to left deck

                // check if current hand is all the same value
                if (checkIfWon()) {
                    break;
                }

                // Optional: sleep to simulate time passing between turns
                Thread.sleep(100);
            }
        } catch (EmptyDeckException e) {
            System.err.println("⚠️ Player " + playerId + " encountered an empty deck.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // HELPER METHOD
    /**
     * Returns a string representation of the player's current hand.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Player " + playerId + " hand: ");
        for (Card c : hand) {
            sb.append(c.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
}

