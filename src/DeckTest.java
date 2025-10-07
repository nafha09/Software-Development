import java.util.List;
import java.util.Arrays;

class DectTest{
    private Deck deck;
    private List<Card> initialCards;

    void setUp() throws EmptyDeckException{
        initialCards= Arrays.asList(new Card(1), new Card(2), new Card(3));
        deck= new Deck(1, initialCards);
    }

    void testDeckInitialisation(){
        assertEquals(1, deck.getId(),"Deck ID should match input");
        assertFalse(deck.isEmpty(),"Deck should not be empty after initialisation");
    }

    void testNegativeThrowsException(){
        assertThrows(IllegalArgumentException.class,()->new Deck(-1, initialCards),"Negative deck Id not allowed");
    }

    void testDiscardRemovesTopCard() throws EmptyDeckException{
        Card topCard= deck.discardCard();
        assertEquals(1, topCard.getValue(),"First card removed should have value 1");
        assertEquals(2, deck.discardCard().getValue(), "Next card should have value 2");
    }

    void testDrawAddsCardToBottom() throws EmptyDeckException{
        Card newCard=new Card(9);
        deck.drawCard();
        deck.discardCard();
        deck.discardCard();
        deck.discardCard();
        Card lastCard= deck.discardCard();
        assertEquals(9, lastCard.getValue(), "Newly added card should be at bottom");
    }

    void testSameCardsTrue() throws EmptyDeckException{
        Deck d=new Deck(2, Arrays.asList(new Card(5), new Card(5), new Card(5)));
        assertTruw(d.sameCards(),"should return true");
    }

    void testSameCardsFalse() throws EmptyDeckException{
        assertFalse(deck.sameCards(),"should return false");
    }

    void testDiscardOnEmptyDeckThrowsException() throws EmptyDeckException{
        Deck d=new Deck(3, Arrays.asList(new Card(7)));
        d.discard();
        assertThrows(EmptyDeckException.class, d::discardCard,"should throw exception");
    }

    void testDrawNullCardThrowsException(){
        assertThrows(IllegalAccessException.class, () -> deck.drawCard(null),"should throw exception");
    }
}