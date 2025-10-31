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
 * Unit tests for the Card class.
 */
public class CardTest {

    @Test
    public void testValidCardCreation() {
        Card card = new Card(1);
        assertEquals("Card value should match the constructor argument", 1, card.getValue());
        assertEquals("toString() should return the card value as a string", "1", card.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCardCreationThrowsException() {
        new Card(-1); // should throw an IllegalArgumentException
    }
}


