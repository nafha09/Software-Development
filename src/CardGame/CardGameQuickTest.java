package CardGame;

import java.util.*;
import java.io.*;

/**
 * A quick manual test (non-JUnit) for verifying
 * pack reading, player/deck setup, and card distribution.
 * Prints detailed debug info about how cards are dealt.
 */

public class CardGameQuickTest {
    public static void main(String[] args) {
        try {
            // --- Create a simple test pack (16 cards for 2 players) ---
            List<Integer> values = Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8,    // first 8 → players
                9, 10, 11, 12, 13, 14, 15, 16 // next 8 → decks
            );

            String testPackFile = "packs/game1.txt";
            new File("packs").mkdirs();
            try (FileWriter fw = new FileWriter(testPackFile)) {
                for (int v : values) fw.write(v + "\n");
            }

            // --- Run the game setup manually ---
            CardGame game = new CardGame();

            // Use reflection to call private methods for testing setup
            java.lang.reflect.Method readPack =
                CardGame.class.getDeclaredMethod("readPack", String.class);
            java.lang.reflect.Method setUpGame =
                CardGame.class.getDeclaredMethod("setUpGame", int.class);
            java.lang.reflect.Method distributeCards =
                CardGame.class.getDeclaredMethod("distributeCards", int.class);
            readPack.setAccessible(true);
            setUpGame.setAccessible(true);
            distributeCards.setAccessible(true);

            readPack.invoke(game, testPackFile);
            setUpGame.invoke(game, 2); // 2 players
            //distributeCards.invoke(game, 2);

            // --- Debug print: which cards went where ---
            System.out.println("=== CARD DISTRIBUTION DEBUG ===");

            List<Player> players = game.getPlayers();
            List<Deck> decks = game.getDecks();

            for (int i = 0; i < players.size(); i++) {
                System.out.println("Player " + (i + 1) + " hand: " + players.get(i));
            }
            for (int i = 0; i < decks.size(); i++) {
                System.out.print("Deck " + (i + 1) + " cards: ");
                for (Card c : decks.get(i).getCards()) {
                    System.out.print(c.getValue() + " ");
                }
                System.out.println();
            }

            System.out.println("CardGameQuickTest completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

