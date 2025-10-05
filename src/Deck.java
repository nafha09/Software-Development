/**
 * Represents the decks of cards in the game
 * each deck will have an id
 * will then be declared right/left hand for each of the player 
 * List of card objects 
 * must discard cards from top and insert cards to bottom.
 * be assigned left and right
 */
 
public final class Deck{
    private final List<Card> cards; //list to hold the card objects
    private int id; // deck ids 
     
    /**
     * constructor
     * @param id -the integer of card
     * @throws illegalArgumentException is value is negative
     * @throws EmptyDeckException if there are no cards in the deck
     */
    public Deck(int id, List<Card> cards) throws EmptyDeckException {
        if (id<0) { // excluding non-negative numbers  
            throw new IllegalArgumentException ("Deck must be non-negative");

        }
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException("Deck is not empty");
        }
        this.id = id;
        this.cards = new ArrayList<>(cards); //new list
    }
    /// getters
    public int getId() {
        return id;
    }
    /// methods
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    //// game play methods drawcard and discardcard
    public Card discardCard() {
    if(card == null || cards.isEmpty()){
        throw EmptyDeckException("Deck is not empty");
    }
    return cards. remove(0);
    }
    public void drawCard(Card card) {
        if(card == null || cards.isEmpty()){
        throw EmptyDeckException("Deck is not empty");
    }
    cards.add(card);
        }
    }
    // method to check if won
    public boolean sameCards() {
        if(card == null || cards.isEmpty())
        return true;
        // check if the first value matches the value of the other 4
        int card1 = cards.get(0).getValue();
        for (Card c : cards) {
            if (c.getValue() != card1) {
                return false
            }
        }
        return true;
    }

/**
 * assign id
 * get id
 * discard crd from top
 * add card to bottom
 * method to check if empty
 * method to check if all values of cards in the deck are the same
 *
 */

