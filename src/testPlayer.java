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

public class testPlayer{


    @Test
    public void testPlayerCreation() {
        Card card_one=new Card(1);
        Card card_two=new Card(2);
        Card card_three=new Card(3);            
        Card card_four=new Card(4);
        Card card_five=new Card(5);
        //Card card_five=new Card(-2);
        List<Card> startingCards=Arrays.asList(card_one,card_two,card_three,card_four);
        // checking the credibilities of the card
        System.out.println("Value =" + card_one.getValue());
        System.out.println("String = " +  card_one.toString());
        System.out.println("Value =" + card_two.getValue());
        System.out.println("String = " +  card_two.toString());
        System.out.println("Value =" + card_three.getValue());
        System.out.println("String = " +  card_three.toString());
        System.out.println("Value =" + card_four.getValue());
        System.out.println("String = " +  card_four.toString());
        System.out.println("Value =" + card_five.getValue());
        System.out.println("String = " +  card_five.toString());
        // checking player hand
        Player player=new Player(1,startingCards);
        System.out.println("Player's hand at the start: " + startingCards);
        // junit assetion 
        assertEquals("Player should start with 4 cards", 4, startingCards.size());
    }
    //setting decks
    @Test
    public void testPlayerDecks() throws Exception {
        List<Card> startingCards = Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4));
        Player player1 = new Player(2, startingCards);
        Deck left = new Deck(1, new ArrayList<>());
        Deck right = new Deck(2, new ArrayList<>());
        player1.setLeftDeck(left);
        player1.setRightDeck(right);
        Card addition = new Card(9);
        player1.addCard(addition);
        ///junit assertion
        assertEquals("Player hand should now have 5 cards", 5, startingCards.size());
    }
    // cards discarding and drawing functionality
    @Test
    public void testDeckFunctionality() throws Exception {
        List<Card> startingCards = Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4));
        Player player1 = new Player(2, startingCards);
        Deck left = new Deck(1, new ArrayList<>());
        Deck right = new Deck(2, new ArrayList<>());
        player1.setLeftDeck(left);
        player1.setRightDeck(right);
        player1.drawCard();
        player1.discardCard();
    }

    //testing the win caller
    @Test
    public void testIfWon () throws Exception {
        List<Card> startingCards = Arrays.asList(new Card(1), new Card(1), new Card(1), new Card(1));
        Player player1 = new Player(2, startingCards);
        boolean result = player1.checkIfWon();
        //junit part
        assertTrue("Player should have won with all same cards", result);
        assertTrue("hasWon flag should be true", player1.hasWon());


    }

}





