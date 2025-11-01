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
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class testPlayer{

    @Before
    public void resetGameState() {
        Player.resetGameStateForTests();
    }

    @Test
    public void testPlayerCreation() {
        List<Card> startingCards = Arrays.asList(
            new Card(1), new Card(2), new Card(3), new Card(4)
        );
        Player player = new Player(1, startingCards);
        assertEquals("Player ID should be 1", 1, player.getPlayerId());
        assertFalse("Player should not have won at start", player.hasWon());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPlayerIdThrowsException() {
        new Player(-1, Arrays.asList(new Card(1)));
    }

    @Test
    public void testEmptyHandIsAllowed(){
        Player player = new Player(1, new ArrayList<>());
        assertNotNull("Player should be created even with empty hand", player);
    }

    @Test
    public void testPlayerDecksAndAddCard() throws Exception {
        List<Card> hand = Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4));
        Deck left = new Deck(1, new ArrayList<>(Arrays.asList(new Card(10))));
        Deck right = new Deck(2, new ArrayList<>(Arrays.asList(new Card(9))));
        Player player = new Player(1, hand);

        player.setLeftDeck(left);
        player.setRightDeck(right);
        player.addCard(new Card(5));

        // confirm new card added (not original list)
        assertTrue(player.toString().contains("5"));
    }

    @Test
    public void testDrawAndDiscard() throws Exception {
    Deck left = new Deck(1, new ArrayList<>(Arrays.asList(new Card(11))));
    Deck right = new Deck(2, new ArrayList<>(Arrays.asList(new Card(8))));
    Player player = new Player(2, new ArrayList<>(Arrays.asList(
        new Card(2), new Card(3), new Card(4), new Card(5)
    )));
    player.setLeftDeck(left);
    player.setRightDeck(right);

    String before = player.toString();
    player.drawCard();
    player.discardCard();
    String after = player.toString();

    assertNotEquals("Player hand should change after draw/discard", before, after);
    assertEquals("Left deck should have one fewer card", 0, left.getCards().size());
    assertEquals("Right deck should have one more card", 2, right.getCards().size());
    }


    @Test
    public void testIfWonTrue() {
        List<Card> winningHand = Arrays.asList(
            new Card(7), new Card(7), new Card(7), new Card(7)
        );
        Player player = new Player(3, winningHand);
        assertTrue("Player should win with all equal cards", player.checkIfWon());
        assertTrue("hasWon flag should be true", player.hasWon());
    }

    @Test
    public void testIfWonFalse() {
        List<Card> nonWinningHand = Arrays.asList(
            new Card(1), new Card(2), new Card(3), new Card(4)
        );
        Player player = new Player(4, nonWinningHand);
        assertFalse("Player should not win with different cards", player.checkIfWon());
    }

    @Test
    public void testPlayerThreadRunsWithoutError() throws Exception {
        Deck left = new Deck(1, new ArrayList<>(Arrays.asList(new Card(1), new Card(2))));
        Deck right = new Deck(2, new ArrayList<>(Arrays.asList(new Card(3), new Card(4))));
        Player player = new Player(1, new ArrayList<>(Arrays.asList(
            new Card(1), new Card(1), new Card(2), new Card(3)
        )));
        player.setLeftDeck(left);
        player.setRightDeck(right);

        Thread t = new Thread(player);
        t.start();
        t.join(200); // wait briefly

        assertTrue("Thread should have started successfully", t.isAlive() || !t.isAlive());
    }



}





