/** main 
 * for the entire card play
 * 
 */
import java.io.*;
import java.util.*;

/**
 * controls the setup and execution of game
 * reads input from text file
 * creates players and decks
 * distributes cards
 * starts each player in its own thread
 */

public class CardGame{
    private final List<Player> players= new ArrayList<>();
    private final List<Deck> decks= new ArrayList<>();
    private final List<Card> pack= new ArrayList<>();

    //read and validate pack
    private void readPack(String filename) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line=br.readLine())!= null){
                int value =Integer.parseInt(line.trim());
                pack.add(new Card(value));
            }
        }
    }
     
    /**
     * setting up the game
     * @param numPlayers number of players in game
     */
    private void setUpGame(int numPlayers) throws EmptyDeckException{
        //setting deck
        for (int i=1; i<=numPlayers; i++){
            decks.add(new Deck(i, new ArrayList<>()));
        }
        //setting players
        for (int i=1; i<= numPlayers; i++){
            players.add(new Player(i, new ArrayList<>()));
        }
        //setting left and right decks
        for (int i=0; i < numPlayers; i++){
            Player current= players.get(i);
            Deck left= decks.get(i);
            Deck right= decks.get((i+1)% numPlayers);
            current.setLeftDeck(left);
            current.setRightDeck(right);
        }
        //distribute cards, players then decks
        distributeCards(numPlayers);
    }
    private void distributeCards(int numPlayers) throws EmptyDeckException{
        int cardIndex=0;
        //deal 4 cards to players
        for (int i=0; i< numPlayers; i++){
            for (int j=0; j< 4; j++){
                players.get(i).addCard(pack.get(cardIndex++));
            }
        }
        //deal remaining to deck
        for (int i=0; i< numPlayers; i++){
            List<Card> deckCards=new ArrayList<>();
            for (int j=0; j<4; j++){
                deckCards.add(pack.get(cardIndex++));
            }
            decks.get(i).getCards().addAll(deckCards);
        }
    }

    //create threads for all players
    private void startGame(){
        List<Thread> threads= new ArrayList<>();
        for (Player p: players){
            Thread t= new Thread(p, "Player-" +p.getPlayerId());
            threads.add(t);
            t.start();
        }
        for (Thread t: threads){
            try {
                t.join();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("complete");
    }

    public static void main(String[] args){
        if (args.length<2){
            System.err.println("not enough players");
            return;
        }
        int numPlayers=Integer.parseInt(args[0]);
        String packFile= args[1];
        CardGame game= new CardGame();
        try{
            game.readPack(packFile);
            game.setUpGame(numPlayers);
            game.startGame();
        } catch(Exception e){
            System.err.println("error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}