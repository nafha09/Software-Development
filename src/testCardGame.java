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
    public void testReadPack(){
    String filename = "pack.txt";
    List<Card> startingCards = CardGame.readPack(filename);
    assertNotNull("Cards list should not be null", startingCards);
    assertTrue("Cards list should not be empty", startingCards.size() > 0);

    }
    /** 
    @Test 
    public void testSetUpGame(){

    }
    @Test
    public void distributeCards (){

    }
    @Test
    public void testwriteFinalDecks() {

    }
    @Test void testMain() {

    }*/
}