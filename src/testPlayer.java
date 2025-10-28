import java.util.List;
import java.util.Arrays;

public class testPlayer{
    public static void main(String[] args){
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

    //*

            //draw top card
            Card removedCard=deck.discardCard();
            System.out.println("Removed top card: "+removedCard.getValue());
            System.out.println("Deck after removing top card "+deck);
            //add new card to bottom of deck
            deck.drawCard(new Card(9));
            System.out.println("Added card with value 9");
            System.out.println("Deck after picking up cark: "+deck);
            //check if all cards are of same value
            boolean allSame= deck.sameCards();
            System.out.println("Are the cards of same value? "+allSame);
            //discarding card to trigger EmptyDeckException
            while(true){
                deck.discardCard();
                System.out.println("After discard: "+deck);
            }
    */
        //}
    }
}