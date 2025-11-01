package CardGame;

import java.util.*;
import java.io.*;

/**
 * A quick, non-interactive test harness for CardGame.
 * 
 * This is NOT a JUnit test — it's a simple console test to verify
 * that CardGame setup, distribution, and threading work correctly.
 */
public class CardGameQuickTest {
    public static void main(String[] args) {
        System.out.println("=== CardGameQuickTest ===");

        try {
            // Create a new game instance
            CardGame game = new CardGame();

            // Manually build a pack of 16 cards for 2 players
            // (8 cards per player total)
            List<Card> mockPack = new ArrayList<>();
            for (int i = 1; i <= 16; i++) {
                mockPack.add(new Card(i));
            }

            // Write mock pack to a temporary file
            String testPackFile = "test_pack.txt";
            try (FileWriter fw = new FileWriter(testPackFile)) {
                for (Card c : mockPack) {
                    fw.write(c.getValue() + System.lineSeparator());
                }
            }

            // Simulate the main logic manually
            // Equivalent of main() but without user input
            System.out.println("Setting up a 2-player game with mock pack...");

            // Access private methods via reflection (if needed) — or, better:
            // temporarily make them package-private if running inside CardGame package
            CardGame gameInstance = new CardGame();

            // Read pack using reflection call (or just replicate)
            var readPackMethod = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPackMethod.setAccessible(true);
            readPackMethod.invoke(gameInstance, testPackFile);

            // Set up and start game
            var setupMethod = CardGame.class.getDeclaredMethod("setUpGame", int.class);
            setupMethod.setAccessible(true);
            setupMethod.invoke(gameInstance, 2);

            var startMethod = CardGame.class.getDeclaredMethod("startGame");
            startMethod.setAccessible(true);
            startMethod.invoke(gameInstance);

            // Verification summary
            System.out.println("\n--- Verification ---");
            System.out.println("Players created: " + gameInstance.getPlayers().size());
            System.out.println("Decks created: " + gameInstance.getDecks().size());
            for (Player p : gameInstance.getPlayers()) {
                System.out.println(p);
            }
            for (Deck d : gameInstance.getDecks()) {
                System.out.println(d);
            }

            System.out.println("\n CardGameQuickTest completed successfully.");

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
