import java.util.List;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class testCardGame{
    @Test
    void testReadPack(){
    String filename = "pack.txt";
    List<Card> startingCards = CardGame.readPack(filename); // will be fixed with packaging
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
}