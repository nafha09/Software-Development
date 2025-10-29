import java.util.List;
import java.util.Arrays;
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

        //try{ // borrowing the concept from decktest
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
            System.out.println("Player's hand at the start: "+player);
            // junit assetion 
            assertEquals("Player should start with 4 cards", 4, startingCards.size());
            System.out.println("Player's hand at the start: " + startingCards);

    
        }//}}
    }
