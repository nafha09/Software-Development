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
/** main 
 * for the entire card play
 * 
 */

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


    // the game run 
    public static void main(String[] args){
        // asking for number of players 
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the number of players:");
        int numPlayers = input.nextInt();
        input.nextLine();
        System.out.print("Please enter location of pack to load:");
        input.close();


        if (args.length<2){
            System.err.println("not enough players");
            return;
        }
        //int numPlayers=Integer.parseInt(args[0]);
        int numPlayers;
        String packFile= args[0];
        try{
            numPlayers= Integer.parseInt(args[1]);
            if (numPlayers<=0){
                System.err.println("Number of players must be positive");
                return;
                }
            } catch (NumberFormatException e){
                System.err.println("Invalid n");
                return;
            }

        CardGame game= new CardGame();
        try{
            game.readPack(packFile);
            int expected=numPlayers*8;
            if(game.pack.size()!=expected){
                System.err.println("Pack must contain "+expected+" cards");
                return;
            }
            game.setUpGame(numPlayers);
            game.startGame();
        } catch(Exception e){
            System.err.println("error: "+e.getMessage());
            //e.printStackTrace();
        }
    }
/// extra methods needed for the game run
    //read and validate pack
    private void readPack(String filename) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line=br.readLine())!= null){
                int value =Integer.parseInt(line.trim());
                if (value<0)throw new IOException ("Negative card value in pack");
                pack.add(new Card(value));
            }
        } catch (NumberFormatException e){
            throw new IOException("Invalid number format in pack");
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
            //List<Card> deckCards=new ArrayList<>();
            for (int j=0; j<4; j++){
                decks.get(i).drawCard(pack.get(cardIndex++));
                //deckCards.add(pack.get(cardIndex++));
            }
            //decks.get(i).getCards().addAll(deckCards);
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
        writeFinalDecks();
        System.out.println("Game complete");
    }

    private void writeFinalDecks(){
        for (Deck d: decks){
            try (FileWriter fw= new FileWriter("deck"+d.getId()+"output.txt")){
                fw.write(d.toString());
            } catch (IOException e){
                System.err.println("Error writing deck output "+e.getMessage());
            }
        }
    }
}