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
//import java.io.IOException;
import java.io.*;


/**
 * Controls the setup and execution of the card game.
 * Reads the input pack file, creates players and decks,
 * distributes cards in a round-robin fashion,
 * and runs each player in its own thread.
 */
public class CardGame {
    private final List<Player> players = new ArrayList<>();
    private final List<Deck> decks = new ArrayList<>();
    private final List<Card> pack = new ArrayList<>();

    /**
     * Main entry point — interactively reads the number of players
     * and the pack file location, then starts the game.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");
        int numPlayers = input.nextInt();
        input.nextLine(); // consume newline
        System.out.print("Please enter location of pack to load: ");
        String packFile = input.nextLine();
        input.close();

        CardGame game = new CardGame();

        try {
            game.readPack(packFile);
            int expected = numPlayers * 8;
            if (game.pack.size() != expected) {
                System.err.println("Pack must contain " + expected + " cards");
                return;
            }

            game.setUpGame(numPlayers);
            game.startGame();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Reads and validates a pack file.
     * @param filename the path to the pack file
     * @throws IOException if the file is invalid or contains bad data
     */
    private void readPack(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                int value = Integer.parseInt(line.trim());
                if (value < 0) throw new IOException("Negative card value in pack");
                pack.add(new Card(value));
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in pack");
        }
    }

    /**
     * Sets up players and decks and distributes cards.
     * @param numPlayers number of players in the game
     */
    private void setUpGame(int numPlayers) throws EmptyDeckException {
        // Create empty decks
        for (int i = 1; i <= numPlayers; i++) {
            decks.add(new Deck(i, new ArrayList<>())); // allow empty decks
        }

        // Create players
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i, new ArrayList<>()));
        }

        // Assign left and right decks for each player
        for (int i = 0; i < numPlayers; i++) {
            Player current = players.get(i);
            Deck left = decks.get(i);
            Deck right = decks.get((i + 1) % numPlayers);
            current.setLeftDeck(left);
            current.setRightDeck(right);
        }

        // Distribute cards from pack to players and decks
        distributeCards(numPlayers);
    }

    /**
     * Distributes cards in a round-robin fashion:
     * first to players, then to decks.
     */
    private void distributeCards(int numPlayers) throws EmptyDeckException {
        int cardIndex = 0;

        // Deal 4 cards to each player, round-robin
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < numPlayers; j++) {
                players.get(j).addCard(pack.get(cardIndex++));
            }
        }

        // Deal remaining cards to each deck, round-robin
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < numPlayers; j++) {
                decks.get(j).drawCard(pack.get(cardIndex++));
            }
        }
    }

    /**
     * Starts a separate thread for each player and
     * waits for all threads to finish before ending the game.
     */
    private void startGame() {
        List<Thread> threads = new ArrayList<>();

        for (Player p : players) {
            Thread t = new Thread(p, "Player-" + p.getPlayerId());
            threads.add(t);
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        writeFinalDecks();
        System.out.println("Game complete");
    }
    // --- Getters for testing and verification ---
    public List<Player> getPlayers() {
        return players;
    }

    public List<Deck> getDecks() {
        return decks;
    }


    /**
     * Writes the final contents of each deck to its output file.
     */
    private void writeFinalDecks() {
        for (Deck d : decks) {
            try (FileWriter fw = new FileWriter("deck" + d.getId() + "_output.txt")) {
                fw.write("deck" + d.getId() + " contents: ");
                for (Card c : d.getCards()) {
                    fw.write(c.getValue() + " ");
                }
                fw.write(System.lineSeparator());
            } catch (IOException e) {
                System.err.println("Error writing deck output: " + e.getMessage());
            }
        }
    }
}

