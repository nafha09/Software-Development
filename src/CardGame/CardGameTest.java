package CardGame;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.*;
import java.io.*;



/**
 * Unit tests for the CardGame class.
 * These tests verify correct setup, distribution,
 * and safe handling of invalid data.
 */
public class CardGameTest {

    private CardGame game;
    private String validPackFile;
    private String invalidPackFile;

    @Before
    public void setUp() throws Exception {
        game = new CardGame();

        // Create a valid 2-player pack (16 cards)
        validPackFile = "valid_pack.txt";
        try (FileWriter fw = new FileWriter(validPackFile)) {
            for (int i = 1; i <= 16; i++) {
                fw.write(i + System.lineSeparator());
            }
        }

        // Create an invalid pack (non-numeric)
        invalidPackFile = "invalid_pack.txt";
        try (FileWriter fw = new FileWriter(invalidPackFile)) {
            fw.write("A" + System.lineSeparator());
            fw.write("B" + System.lineSeparator());
        }
    }

    @Test
    public void testValidPackLoadsSuccessfully() throws Exception {
        // Use reflection to call private method readPack
        var readPackMethod = CardGame.class.getDeclaredMethod("readPack", String.class);
        readPackMethod.setAccessible(true);
        readPackMethod.invoke(game, validPackFile);

        // Check that 16 cards were loaded
        var packField = CardGame.class.getDeclaredField("pack");
        packField.setAccessible(true);
        List<Card> pack = (List<Card>) packField.get(game);

        assertEquals("Pack should contain 16 cards", 16, pack.size());
        assertEquals("First card should have value 1", 1, pack.get(0).getValue());
    }

    @Test
    public void testInvalidPackThrowsIOException() {
        try {
            var readPackMethod = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPackMethod.setAccessible(true);
            readPackMethod.invoke(game, invalidPackFile);
            fail("Expected IOException for invalid pack file");
        } catch (Exception e) {
            // unwrap InvocationTargetException
            Throwable cause = e.getCause();
            assertTrue("Should throw IOException", cause instanceof IOException);
        }
    }


    @Test
    public void testSetUpGameCreatesCorrectNumberOfPlayersAndDecks() throws Exception {
        // Load valid pack
        var readPackMethod = CardGame.class.getDeclaredMethod("readPack", String.class);
        readPackMethod.setAccessible(true);
        readPackMethod.invoke(game, validPackFile);

        // Set up 2 players
        var setUpGameMethod = CardGame.class.getDeclaredMethod("setUpGame", int.class);
        setUpGameMethod.setAccessible(true);
        setUpGameMethod.invoke(game, 2);

        assertEquals("There should be 2 players", 2, game.getPlayers().size());
        assertEquals("There should be 2 decks", 2, game.getDecks().size());

        // Check each player has 4 cards
        for (Player p : game.getPlayers()) {
            assertEquals("Each player should start with 4 cards", 4, p.toString().split(",").length);
        }

        // Check each deck has 4 cards
        for (Deck d : game.getDecks()) {
            assertEquals("Each deck should start with 4 cards", 4, d.getCards().size());
        }
    }

    @Test
    public void testGameRunsWithoutCrashing() throws Exception {
        var readPackMethod = CardGame.class.getDeclaredMethod("readPack", String.class);
        readPackMethod.setAccessible(true);
        readPackMethod.invoke(game, validPackFile);

        var setUpGameMethod = CardGame.class.getDeclaredMethod("setUpGame", int.class);
        setUpGameMethod.setAccessible(true);
        setUpGameMethod.invoke(game, 2);

        var startGameMethod = CardGame.class.getDeclaredMethod("startGame");
        startGameMethod.setAccessible(true);

        // Run the game
        startGameMethod.invoke(game);

        // Verify final deck files were created
        File deck1 = new File("deck1_output.txt");
        File deck2 = new File("deck2_output.txt");

        assertTrue("deck1_output.txt should exist", deck1.exists());
        assertTrue("deck2_output.txt should exist", deck2.exists());
    }
}

    /* 
    @Test
    void testReadPack(){
    String filename = "pack.txt";
    List<Card> startingCards = readPack(filename); // will be fixed with packaging
    assertNotNull("Cards list should not be null", startingCards);
    assertTrue("Cards list should not be empty", startingCards.size() > 0);

    }
    
    @Test 
    void testSetUpGame(){
        CardGame game = new CardGame();
        int numPlayers = 4;
        game.setUpGame(numPlayers);
        assertEquals(numPlayers, game.getDecks().size());
        assertEquals(numPlayers, game.getPlayers().size());
        assertEquals(game.getDecks().get(0), game.getPlayers().get(0).getLeftDeck());
        assertEquals(game.getDecks().get(1), game.getPlayers().get(0).getRightDeck());
    }
    
    @Test
    public void distributeCards (){
        CardGame game = new CardGame();
        int numPlayers = 4;
        for (int i = 0; i < numPlayers; i++) {
            assertEquals("Player " + i + " should have 4 cards",4, game.getPlayers().get(i).getHand().size());
        }
        for (int i = 0; i < numPlayers; i++) {
            assertEquals("Deck " + i + " should have 4 cards",4, game.getDecks().get(i).getCards().size());
        }
    }
    /**
    @Test
    public void testwriteFinalDecks() {

    }
    @Test void testMain() {

    }*/
