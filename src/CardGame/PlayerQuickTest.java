package CardGame;

import java.util.*;

public class PlayerQuickTest {
    public static void main(String[] args) throws Exception {
        // --- 1. Setup a simple test environment ---
        // Left deck (player will draw from this)
        List<Card> leftDeckCards = new ArrayList<>();
        leftDeckCards.add(new Card(5)); // top card
        leftDeckCards.add(new Card(9));
        Deck leftDeck = new Deck(1, leftDeckCards);

        // Right deck (player will discard to this)
        List<Card> rightDeckCards = new ArrayList<>();
        rightDeckCards.add(new Card(3));
        rightDeckCards.add(new Card(7));
        Deck rightDeck = new Deck(2, rightDeckCards);

        // Player starts with four cards
        List<Card> hand = Arrays.asList(
                new Card(1),
                new Card(2),
                new Card(3),
                new Card(4)
        );
        Player player = new Player(1, hand);
        player.setLeftDeck(leftDeck);
        player.setRightDeck(rightDeck);

        // --- 2. Show initial state ---
        System.out.println("Initial hand: " + player.toString());
        System.out.println("Left deck before: " + leftDeck);
        System.out.println("Right deck before: " + rightDeck);

        // --- 3. Perform draw and discard ---
        player.drawCard();
        player.discardCard();

        // --- 4. Show final state ---
        System.out.println("\nAfter draw/discard:");
        System.out.println("Player hand: " + player.toString());
        System.out.println("Left deck after: " + leftDeck);
        System.out.println("Right deck after: " + rightDeck);

        // --- 5. Check if hand changed ---
        if (player.toString().contains("5") || player.toString().contains("9")) {
            System.out.println("\n Hand changed after draw/discard — looks good!");
        } else {
            System.out.println("\n Hand did NOT change — check draw/discard logic.");
        }
    }
}
