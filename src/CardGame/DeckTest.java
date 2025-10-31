//package CardGame;
import CardGame.Card;
import CardGame.Deck;
import CardGame.EmptyDeckException;
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

public class DeckTest{
    
     @Test
    public void testDeckInitialisation() throws EmptyDeckException {
        List<Card> cards = Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4));
        Deck deck = new Deck(1, cards);
        assertEquals(1, deck.getId());
        assertFalse(deck.isEmpty());
    }

    @Test
    public void testDiscardCardRemovesTopCard() throws EmptyDeckException {
        List<Card> cards = Arrays.asList(new Card(1), new Card(2), new Card(3));
        Deck deck = new Deck(1, cards);
        Card top = deck.discardCard();
        assertEquals(1, top.getValue());
        assertEquals(2, deck.getCards().get(0).getValue());
    }

    @Test
    public void testDrawCardAddsToBottom() throws EmptyDeckException {
        List<Card> cards = Arrays.asList(new Card(1), new Card(2));
        Deck deck = new Deck(1, cards);
        deck.drawCard(new Card(9));
        assertEquals(9, deck.getCards().get(deck.getCards().size() - 1).getValue());
    }

    @Test(expected = EmptyDeckException.class)
    public void testDiscardOnEmptyDeckThrowsException() throws EmptyDeckException {
        List<Card> cards = Arrays.asList(new Card(1));
        Deck deck = new Deck(1, cards);
        deck.discardCard(); // removes only card
        deck.discardCard(); // should throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeckIdThrowsException() throws EmptyDeckException {
        new Deck(-1, Arrays.asList(new Card(1)));
    }
}