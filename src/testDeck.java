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

public class testDeck{
    
    @Test
    public void testDeckMethods() {
        try{
            //create a list of cards for deck
            Card card1=new Card(1);
            Card card2=new Card(2);
            Card card3=new Card(3);
            Card card4=new Card(4);
            List<Card> startCards=Arrays.asList(card1,card2,card3,card4);
            //create deck (id is 1)
            Deck deck=new Deck(1,startCards);
            //display initial contents
            System.out.println("Inital deck: "+deck);
            //draw top card
            Card removedCard=deck.discardCard();
            System.out.println("Removed top card: "+removedCard.getValue());
            System.out.println("Deck after removing top card "+deck);
            // validate the card removed with unit testing
            assertNotNull("Removed card should not be null", removedCard);

            //add new card to bottom of deck
            deck.drawCard(new Card(9));
            System.out.println("Added card with value 9");
            System.out.println("Deck after picking up cark: "+deck);
            //check if all cards are of same value
            boolean allSame= deck.sameCards();
            System.out.println("Are the cards of same value? "+allSame);
            assertFalse("Deck should not have all same value cards", allSame);
            //discarding card to trigger EmptyDeckException
            while(true){
                deck.discardCard();
                System.out.println("After discard: "+deck);
            }
        } catch (EmptyDeckException e){
            System.err.println(e.getMessage());
            assertEquals("Deck is empty!", e.getMessage());
        }
    }
}