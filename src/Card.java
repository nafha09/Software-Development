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
/**
 * represent a single playing card in the game
 * 
 * should be immutable to be thread-safe
 * cannot be changed
 */
public final class Card{
    private final int value; //immutable
    /**
     * constructor
     * @param value -the integer of card
     * @throws illegalArgumentException is value is negative
     */
     
    public Card(int value){
        if(value<0){
            throw new IllegalArgumentException("Card value must be non-negative");
        }
        this.value=value;
    }

    //Getter
    public int getValue(){
        //@returns value of the card
        return value;
    }

    //Convert to string for easy printing (not sure might remove later)
    //@return the string form of the card
    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
